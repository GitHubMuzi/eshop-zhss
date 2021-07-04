package com.zhss.eshop.schedule.stock;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhss.eshop.common.util.ObjectUtils;
import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.order.domain.OrderItemDTO;
import com.zhss.eshop.schedule.api.WmsService;
import com.zhss.eshop.schedule.constant.StockUpdateEvent;
import com.zhss.eshop.schedule.dao.ScheduleOrderPickingItemDAO;
import com.zhss.eshop.schedule.dao.ScheduleOrderSendOutDetailDAO;
import com.zhss.eshop.schedule.domain.SaleDeliveryScheduleResult;
import com.zhss.eshop.schedule.domain.ScheduleOrderPickingItemDO;
import com.zhss.eshop.schedule.domain.ScheduleOrderSendOutDetailDO;
import com.zhss.eshop.schedule.service.SaleDeliveryScheduler;
import com.zhss.eshop.schedule.util.BeanConvertUtils;

/**
 * 库存更新事件的处理器
 * @author zhonghuashishan
 *
 */
@Component
public class StockUpdateProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(StockUpdateProcessor.class);
	
	/**
	 * 仓储服务
	 */
	@Autowired
	private WmsService wmsService;
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
	 * 通知库存中心，“提交订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	public Boolean informSubmitOrderEvent(OrderInfoDTO order) {
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
	public Boolean informCancelOrderEvent(OrderInfoDTO order) {
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
	public Boolean informPayOrderEvent(OrderInfoDTO order) {
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
	
}
