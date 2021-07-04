package com.zhss.eshop.schedule.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.common.util.DateProvider;
import com.zhss.eshop.common.util.ObjectUtils;
import com.zhss.eshop.customer.domain.ReturnGoodsWorksheetDTO;
import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.order.domain.OrderItemDTO;
import com.zhss.eshop.purchase.domain.PurchaseOrderDTO;
import com.zhss.eshop.purchase.domain.PurchaseOrderItemDTO;
import com.zhss.eshop.schedule.constant.StockUpdateEvent;
import com.zhss.eshop.schedule.dao.ScheduleOrderPickingItemDAO;
import com.zhss.eshop.schedule.dao.ScheduleOrderSendOutDetailDAO;
import com.zhss.eshop.schedule.domain.SaleDeliveryScheduleResult;
import com.zhss.eshop.schedule.domain.ScheduleOrderPickingItemDO;
import com.zhss.eshop.schedule.domain.ScheduleOrderSendOutDetailDO;
import com.zhss.eshop.schedule.domain.ScheduleReturnGoodsWorksheetDTO;
import com.zhss.eshop.schedule.mapper.UniqueRecordMapper;
import com.zhss.eshop.schedule.service.SaleDeliveryOrderBuilder;
import com.zhss.eshop.schedule.service.SaleDeliveryScheduler;
import com.zhss.eshop.schedule.service.impl.SaleDeliveryOrderBuilderFactory;
import com.zhss.eshop.schedule.stock.ScheduleStockUpdater;
import com.zhss.eshop.schedule.stock.ScheduleStockUpdaterFactory;
import com.zhss.eshop.schedule.util.BeanConvertUtils;
import com.zhss.eshop.wms.constant.PurchaseInputOrderStatus;
import com.zhss.eshop.wms.domain.PurchaseInputOrderDTO;
import com.zhss.eshop.wms.domain.PurchaseInputOrderItemDTO;
import com.zhss.eshop.wms.domain.ReturnGoodsInputOrderDTO;
import com.zhss.eshop.wms.domain.ReturnGoodsInputOrderItemDTO;
import com.zhss.eshop.wms.domain.SaleDeliveryOrderDTO;

/**
 * 调度中心对外接口service组件
 * @author zhonghuashishan
 *
 */
@RestController
public class ScheduleService implements ScheduleApi {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
	
	/**
	 * 仓储服务
	 */
	@Autowired
	private WmsService wmsService;
	/**
	 * 销售出库单构建器工厂
	 */
	@Autowired
	private SaleDeliveryOrderBuilderFactory saleDeliveryOrderBuilderFactory;
	/**
	 * 库存服务
	 */
	@Autowired
	private InventoryService inventoryService;
	/**
	 * 销售出库调度器
	 */
	@Autowired
	private SaleDeliveryScheduler saleDeliveryScheduler;
	/**
	 * 拣货条目管理DAO组件
	 */
	@Autowired
	private ScheduleOrderPickingItemDAO pickingItemDAO;
	/**
	 * 发货明细管理DAO组件
	 */
	@Autowired
	private ScheduleOrderSendOutDetailDAO sendOutDetailDAO;
	/**
	 * 库存更新组件工厂
	 */
	@Autowired
	private ScheduleStockUpdaterFactory stockUpdaterFactory;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	/**
	 * 订单服务
	 */
	@Autowired
	private OrderService orderService;
	@Autowired
	private UniqueRecordMapper uniqueRecordMapper;
	
	/**
	 * 通知库存中心，“采购入库完成”事件发生了
	 * @param purchaseInputOrderDTO 采购入库单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informPurchaseInputFinished(
			@RequestBody PurchaseInputOrderDTO purchaseInputOrder) {
		try {
			uniqueRecordMapper.insert("informPurchaseInputFinished_" + purchaseInputOrder.getId()); 
			
			ScheduleStockUpdater stockUpdater = stockUpdaterFactory.create(
					StockUpdateEvent.PURCHASE_INPUT, purchaseInputOrder);
			stockUpdater.update();
			
			inventoryService.informPurchaseInputFinished(purchaseInputOrder);
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 通知库存中心，“提交订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informSubmitOrderEvent(@RequestBody OrderInfoDTO order) {
		try {
			for(OrderItemDTO orderItem : order.getOrderItems()) {
				SaleDeliveryScheduleResult scheduleResult = 
						saleDeliveryScheduler.schedule(orderItem);
				
				List<ScheduleOrderPickingItemDO> pickingItems = ObjectUtils.convertList(
						scheduleResult.getPickingItems(), ScheduleOrderPickingItemDO.class);
				List<ScheduleOrderSendOutDetailDO> sendOutDetails = ObjectUtils.convertList(
						scheduleResult.getSendOutDetails(), ScheduleOrderSendOutDetailDO.class);
				
				pickingItemDAO.batchSave(orderItem.getOrderInfoId(), orderItem.getId(), pickingItems); 
				sendOutDetailDAO.batchSave(orderItem.getOrderInfoId(), orderItem.getId(), sendOutDetails);
				
				ScheduleStockUpdater stockUpdater = stockUpdaterFactory.create(
						StockUpdateEvent.SUBMIT_ORDER, scheduleResult);
				stockUpdater.update();
				
				wmsService.informSubmitOrderEvent(
						BeanConvertUtils.convertSaleDeliveryScheduleResult(scheduleResult)); 
			}

			return true;
		} catch (Exception e) {
			logger.error("error", e);
			return false;
		}
	}
	
	/**
	 * 通知库存中心，“取消订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informCancelOrderEvent(@RequestBody OrderInfoDTO order) {
		try {
			for(OrderItemDTO orderItem : order.getOrderItems()) {
				SaleDeliveryScheduleResult scheduleResult = saleDeliveryScheduler
						.getScheduleResult(orderItem);
				
				ScheduleStockUpdater stockUpdater = stockUpdaterFactory.create(
						StockUpdateEvent.CANCEL_ORDER, scheduleResult);
				stockUpdater.update();
				
				pickingItemDAO.removeByOrderItemId(orderItem.getOrderInfoId(), orderItem.getId()); 
				sendOutDetailDAO.removeByOrderItemId(orderItem.getOrderInfoId(), orderItem.getId()); 
				
				wmsService.informCancelOrderEvent(
						BeanConvertUtils.convertSaleDeliveryScheduleResult(scheduleResult));
			}
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 通知库存中心，“支付订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informPayOrderEvent(@RequestBody OrderInfoDTO order) {
		try {
			for(OrderItemDTO orderItem : order.getOrderItems()) {
				SaleDeliveryScheduleResult scheduleResult = saleDeliveryScheduler
						.getScheduleResult(orderItem);
				
				ScheduleStockUpdater stockUpdater = stockUpdaterFactory.create(
						StockUpdateEvent.PAY_ORDER, scheduleResult);
				stockUpdater.update();
				
				wmsService.informPayOrderEvent(
						BeanConvertUtils.convertSaleDeliveryScheduleResult(scheduleResult));
			}
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 通知库存中心，“完成退货入库”事件发生了
	 * @param returnGoodsInputOrderDTO 退货入库单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informReturnGoodsInputFinished(
			@RequestBody ReturnGoodsInputOrderDTO returnGoodsInputOrder) {
		try {
			uniqueRecordMapper.insert("ScheduleService_informReturnGoodsInputFinished_" 
					+ returnGoodsInputOrder.getId());  
			
			ScheduleStockUpdater stockUpdater = stockUpdaterFactory.create(
					StockUpdateEvent.RETURN_GOODS_INPUT, returnGoodsInputOrder);
			stockUpdater.update();
			
			inventoryService.informReturnGoodsInputFinished(returnGoodsInputOrder);
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 调度采购入库
	 * @param purchaseOrderDTO 采购单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean schedulePurchaseInput(@RequestBody PurchaseOrderDTO purchaseOrder) {
		try {
			PurchaseInputOrderDTO purchaseInputOrder = 
					createPurchaseInputOrder(purchaseOrder);
			
			List<PurchaseInputOrderItemDTO> purchaseInputOrderItems = 
					new ArrayList<PurchaseInputOrderItemDTO>();
			for(PurchaseOrderItemDTO purchaseOrderItem : purchaseOrder.getItems()) {
				purchaseInputOrderItems.add(createPurchaseInputOrderItem(purchaseOrderItem)); 
			}
			purchaseInputOrder.setItems(purchaseInputOrderItems);  
			
			wmsService.createPurchaseInputOrder(purchaseInputOrder); 
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 创建一个采购入库单
	 * @param purchaseOrder 采购单
	 * @return 采购入库单
	 * @throws Exception
	 */
	private PurchaseInputOrderDTO createPurchaseInputOrder(
			PurchaseOrderDTO purchaseOrder) throws Exception {
		PurchaseInputOrderDTO purchaseInputOrder = 
				purchaseOrder.clone(PurchaseInputOrderDTO.class);
		
		purchaseInputOrder.setId(null); 
		purchaseInputOrder.setPurchaseOrderId(purchaseOrder.getId()); 
		purchaseInputOrder.setGmtCreate(dateProvider.getCurrentTime());
		purchaseInputOrder.setGmtModified(dateProvider.getCurrentTime()); 
		purchaseInputOrder.setStatus(PurchaseInputOrderStatus.EDITING); 
		purchaseInputOrder.setPurchaseContactor(purchaseOrder.getContactor());
		purchaseInputOrder.setPurchaseContactorPhoneNumber(
				purchaseOrder.getContactorPhoneNumber()); 
		purchaseInputOrder.setPurchaseContactorEmail(purchaseOrder.getContactorEmail()); 
		purchaseInputOrder.setPurchaseOrderRemark(purchaseOrder.getRemark()); 
		
		return purchaseInputOrder;
	}
	
	/**
	 * 创建采购入库单条目
	 * @param purchaseOrderItem
	 * @return
	 * @throws Exception
	 */
	private PurchaseInputOrderItemDTO createPurchaseInputOrderItem(
			PurchaseOrderItemDTO purchaseOrderItem) throws Exception {
		PurchaseInputOrderItemDTO purchaseInputOrderItem = 
				purchaseOrderItem.clone(PurchaseInputOrderItemDTO.class);
		purchaseInputOrderItem.setId(null); 
		purchaseInputOrderItem.setGmtCreate(dateProvider.getCurrentTime()); 
		purchaseInputOrderItem.setGmtModified(dateProvider.getCurrentTime());  
		return purchaseInputOrderItem;
	}

	/**
	 * 调度销售出库
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean scheduleSaleDelivery(@RequestBody OrderInfoDTO order) {
		try {
			SaleDeliveryOrderBuilder saleDeliveryOrderBuilder = 
					saleDeliveryOrderBuilderFactory.get();
			
			SaleDeliveryOrderDTO saleDeliveryOrder = saleDeliveryOrderBuilder
					.setOrderRelatedData(order)
					.createSaleDeliveryOrderItems(order.getOrderItems())
					.createSendOutOrder(order)
					.createLogisticOrder(order)
					.initStatus()
					.initTimes()
					.create();
			
			wmsService.createSaleDeliveryOrder(saleDeliveryOrder);
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 调度退货入库
	 * @param orderDTO 订单DTO
	 * @param returnGoodsWorksheetDTO 退货工单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean scheduleReturnGoodsInput(
			@RequestBody ScheduleReturnGoodsWorksheetDTO scheduleReturnGoodsWorksheet) {
		try {
			OrderInfoDTO order = scheduleReturnGoodsWorksheet.getOrder();
			ReturnGoodsWorksheetDTO returnGoodsWorksheet = scheduleReturnGoodsWorksheet.getReturnGoodsWorksheet();
			
			ReturnGoodsInputOrderDTO returnGoodsInputOrder = order.clone(ReturnGoodsInputOrderDTO.class);
			returnGoodsInputOrder.setId(null); 
			returnGoodsInputOrder.setReturnGoodsWorksheetId(returnGoodsWorksheet.getId()); 
			returnGoodsInputOrder.setOrderId(order.getId());
			returnGoodsInputOrder.setReturnGoodsReason(returnGoodsWorksheet.getReturnGoodsReason()); 
			returnGoodsInputOrder.setReturnGoodsRemark(returnGoodsWorksheet.getReturnGoodsRemark()); 
			returnGoodsInputOrder.setGmtCreate(dateProvider.getCurrentTime()); 
			returnGoodsInputOrder.setGmtModified(dateProvider.getCurrentTime()); 
			
			List<ReturnGoodsInputOrderItemDTO> returnGoodsInputOrderItems = ObjectUtils.convertList(
					order.getOrderItems(), ReturnGoodsInputOrderItemDTO.class);
			for(ReturnGoodsInputOrderItemDTO returnGoodsInputOrderItem : returnGoodsInputOrderItems) {
				returnGoodsInputOrderItem.setId(null);  
				returnGoodsInputOrderItem.setGmtCreate(dateProvider.getCurrentTime()); 
				returnGoodsInputOrderItem.setGmtModified(dateProvider.getCurrentTime()); 
			}
			returnGoodsInputOrder.setItems(returnGoodsInputOrderItems); 
			
			wmsService.createReturnGoodsInputOrder(returnGoodsInputOrder);
			
			return true;
		} catch (Exception e) {
			logger.error("error", e);  
			return false;
		}
	}
	
	/**
	 * 获取调度结果
	 * @param orderInfoId 订单id
	 * @param goodsSkuId 商品sku id
	 * @return 调度结果
	 */
	@Override
	public SaleDeliveryScheduleResult getScheduleResult(
			@RequestParam("orderInfoId") Long orderInfoId, 
			@RequestParam("goodsSkuId") Long goodsSkuId) {
		try {
			OrderInfoDTO order = orderService.getOrderById(orderInfoId);
			OrderItemDTO targetOrderItem = null;
			
			for(OrderItemDTO orderItem : order.getOrderItems()) {
				if(orderItem.getGoodsSkuId().equals(goodsSkuId)) {
					targetOrderItem = orderItem;
					break;
				}
			}
			
			return saleDeliveryScheduler.getScheduleResult(targetOrderItem); 
		} catch (Exception e) {
			logger.error("error", e); 
			return null;
		}
	}
	
}
