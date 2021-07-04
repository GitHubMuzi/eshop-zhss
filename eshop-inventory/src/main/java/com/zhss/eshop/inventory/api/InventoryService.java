package com.zhss.eshop.inventory.api;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.inventory.constant.GoodsStockUpdateOperation;
import com.zhss.eshop.inventory.dao.GoodsStockDAO;
import com.zhss.eshop.inventory.stock.CancelOrderStockUpdaterFactory;
import com.zhss.eshop.inventory.stock.PayOrderStockUpdaterFactory;
import com.zhss.eshop.inventory.stock.PurchaseInputStockUpdaterFactory;
import com.zhss.eshop.inventory.stock.ReturnGoodsInputStockUpdaterFactory;
import com.zhss.eshop.inventory.stock.StockUpdateMessage;
import com.zhss.eshop.inventory.stock.StockUpdater;
import com.zhss.eshop.inventory.stock.SubmitOrderStockUpdaterFactory;
import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.wms.domain.PurchaseInputOrderDTO;
import com.zhss.eshop.wms.domain.ReturnGoodsInputOrderDTO;
import com.alibaba.fastjson.JSONObject;
import com.zhss.eshop.common.util.DateProvider;
import com.zhss.eshop.inventory.api.InventoryApi;
import com.zhss.eshop.inventory.domain.GoodsStockDO;
import com.zhss.eshop.inventory.mapper.UniqueRecordMapper;
import com.zhss.eshop.inventory.mq.MessageService;

/**
 * 库存服务
 * @author zhonghuashishan
 *
 */
@SuppressWarnings("rawtypes")
@RestController
public class InventoryService implements InventoryApi {
	
	private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
	
	/**
	 * 采购入库库存更新命令工厂
	 */
	@Autowired
	private PurchaseInputStockUpdaterFactory<PurchaseInputOrderDTO> 
			purchaseInputStockUpdateCommandFactory;
	/**
	 * 退货入库库存更新命令工厂
	 */
	@Autowired
	private ReturnGoodsInputStockUpdaterFactory<ReturnGoodsInputOrderDTO> 
			returnGoodsInputStockUpdateCommandFactory;
	/**
	 * 提交订单库存更新组件工厂
	 */
	@Autowired
	private SubmitOrderStockUpdaterFactory<OrderInfoDTO> 
			submitOrderStockUpdaterFactory;
	/**
	 * 支付订单库存更新组件工厂
	 */
	@Autowired
	private PayOrderStockUpdaterFactory<OrderInfoDTO> 
			payOrderStockUpdaterFactory;
	/**
	 * 取消订单库存更新组件工厂
	 */
	@Autowired
	private CancelOrderStockUpdaterFactory<OrderInfoDTO> 
			cancelOrderStockUpdaterFactory;
	/**
	 * 商品库存管理模块DAO组件
	 */
	@Autowired
	private GoodsStockDAO goodsStockDAO;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	/**
	 * 消息服务
	 */
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UniqueRecordMapper uniqueRecordMapper;
	
	/**
	 * 通知库存中心，“采购入库完成”事件发生了
	 * @param purchaseInputOrderDTO 采购入库单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informPurchaseInputFinished(
			@RequestBody PurchaseInputOrderDTO purchaseInputOrderDTO) {
		try {
			uniqueRecordMapper.insert("informPurchaseInputFinished_" + purchaseInputOrderDTO.getId()); 
			
			StockUpdater goodsStockUpdateCommand = 
					purchaseInputStockUpdateCommandFactory.create(purchaseInputOrderDTO);
			goodsStockUpdateCommand.updateGoodsStock();
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 通知库存中心，“提交订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informSubmitOrderEvent(@RequestBody OrderInfoDTO orderDTO) {
		try {
			// 更新本地库存
			StockUpdater goodsStockUpdateCommand = 
					submitOrderStockUpdaterFactory.create(orderDTO); 
			goodsStockUpdateCommand.updateGoodsStock();
			
			// 发送异步消息到队列
			StockUpdateMessage message = new StockUpdateMessage();
			message.setId(UUID.randomUUID().toString().replace("-", ""));   
			message.setOperation(GoodsStockUpdateOperation.SUBMIT_ORDER);
			message.setParameter(orderDTO);  
			
			String messageJson = JSONObject.toJSONString(message);
			Message msg = MessageBuilder.withPayload(messageJson.getBytes()).build();
			
			messageService.stockUpdateMessageQueue().send(msg);
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 通知库存中心，“支付订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informPayOrderEvent(@RequestBody OrderInfoDTO orderDTO) {
		try {
			// 更新本地库存
			StockUpdater goodsStockUpdateCommand = 
					payOrderStockUpdaterFactory.create(orderDTO); 
			goodsStockUpdateCommand.updateGoodsStock();
			
			// 发送异步消息到内存队列
			StockUpdateMessage message = new StockUpdateMessage();
			message.setId(UUID.randomUUID().toString().replace("-", ""));
			message.setOperation(GoodsStockUpdateOperation.PAY_ORDER);
			message.setParameter(orderDTO);  
			
			String messageJson = JSONObject.toJSONString(message);
			Message msg = MessageBuilder.withPayload(messageJson.getBytes()).build();
			
			messageService.stockUpdateMessageQueue().send(msg);
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 通知库存中心，“取消订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informCancelOrderEvent(@RequestBody OrderInfoDTO orderDTO) {
		try {
			// 更新本地库存
			StockUpdater goodsStockUpdateCommand = 
					cancelOrderStockUpdaterFactory.create(orderDTO); 
			goodsStockUpdateCommand.updateGoodsStock();
			
			// 发送异步消息到内存队列
			StockUpdateMessage message = new StockUpdateMessage();
			message.setId(UUID.randomUUID().toString().replace("-", ""));
			message.setOperation(GoodsStockUpdateOperation.CANCEL_ORDER);
			message.setParameter(orderDTO);  

			String messageJson = JSONObject.toJSONString(message);
			Message msg = MessageBuilder.withPayload(messageJson.getBytes()).build();
			
			messageService.stockUpdateMessageQueue().send(msg);
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 通知库存中心，“完成退货入库”事件发生了
	 * @param returnGoodsInputOrderDTO 退货入库单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informReturnGoodsInputFinished(
			@RequestBody ReturnGoodsInputOrderDTO returnGoodsInputOrderDTO) {
		try {
			uniqueRecordMapper.insert("InventoryService_informReturnGoodsInputFinished_" 
					+ returnGoodsInputOrderDTO.getId());  
			StockUpdater goodsStockUpdateCommand = 
					returnGoodsInputStockUpdateCommandFactory.create(returnGoodsInputOrderDTO);
			goodsStockUpdateCommand.updateGoodsStock();
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 查询商品sku的库存
	 * @param goodsSkuId 商品sku id
	 * @return 商品sku的库存
	 */
	@Override
	public Long getSaleStockQuantity(@PathVariable("goodsSkuId") Long goodsSkuId) {
		try {
			GoodsStockDO goodsStockDO = goodsStockDAO
					.getGoodsStockBySkuId(goodsSkuId);
			if(goodsStockDO == null) {
				return 0L;
			}
			
			return goodsStockDO.getSaleStockQuantity();
		} catch (Exception e) {
			logger.error("error", e); 
		}
		return 0L;
	}
	
	/**
	 * 设置销售库存
	 * @param goodsSkuId 商品sku id
	 * @param saleStockQuantity 销售库存
	 * @return 处理结果
	 */
	@Override
	public Boolean setSaleStockQuantity(
			@RequestParam("goodsSkuId") Long goodsSkuId, 
			@RequestParam("saleStockQuantity") Long saleStockQuantity) {
		try {
			GoodsStockDO goodsStock = goodsStockDAO.getGoodsStockBySkuId(goodsSkuId);
			
			if(goodsStock == null) {
				goodsStock = new GoodsStockDO();
				goodsStock.setGoodsSkuId(goodsSkuId); 
				goodsStock.setSaleStockQuantity(saleStockQuantity); 
				goodsStock.setLockedStockQuantity(0L); 
				goodsStock.setSaledStockQuantity(0L);  
				goodsStock.setStockStatus(saleStockQuantity > 0L ? 1 : 0); 
				goodsStock.setGmtCreate(dateProvider.getCurrentTime()); 
				goodsStock.setGmtModified(dateProvider.getCurrentTime()); 
				goodsStockDAO.saveGoodsStock(goodsStock);
			} else {
				goodsStock.setSaleStockQuantity(saleStockQuantity); 
				goodsStockDAO.updateGoodsStock(goodsStock);
			}
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
}
