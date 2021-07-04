package com.zhss.eshop.schedule.tcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.order.domain.OrderItemDTO;
import com.zhss.eshop.schedule.api.ScheduleTccApi;
import com.zhss.eshop.schedule.constant.StockUpdateEvent;
import com.zhss.eshop.schedule.constant.TccType;
import com.zhss.eshop.schedule.domain.SaleDeliveryScheduleResult;
import com.zhss.eshop.schedule.service.SaleDeliveryScheduler;
import com.zhss.eshop.schedule.stock.PayOrderScheduleStockUpdater;
import com.zhss.eshop.schedule.stock.ScheduleStockUpdaterFactory;
import com.zhss.eshop.schedule.stock.SubmitOrderScheduleStockUpdater;

@Component("scheduleTccConfirmService")  
@RequestMapping("/schedule/tcc/confirm")  
public class ScheduleTccConfirmService implements ScheduleTccApi {

	/**
	 * 销售出库调度器
	 */
	@Autowired
	private SaleDeliveryScheduler saleDeliveryScheduler;
	/**
	 * 库存更新组件工厂
	 */
	@Autowired
	private ScheduleStockUpdaterFactory stockUpdaterFactory;
	
	@Transactional(rollbackFor = Exception.class)  
	public Boolean informSubmitOrderEvent(OrderInfoDTO order) throws Exception {
		for(OrderItemDTO orderItem : order.getOrderItems()) {
			SaleDeliveryScheduleResult scheduleResult = 
					saleDeliveryScheduler.schedule(orderItem);
			SubmitOrderScheduleStockUpdater stockUpdater = (SubmitOrderScheduleStockUpdater) 
					stockUpdaterFactory.create(StockUpdateEvent.SUBMIT_ORDER, scheduleResult);
			stockUpdater.setTccType(TccType.CONFIRM);  
			stockUpdater.update(); 
		}

		return true;
	}
	
	@Transactional(rollbackFor = Exception.class)  
	public Boolean informPayOrderEvent(OrderInfoDTO order) throws Exception {
		for(OrderItemDTO orderItem : order.getOrderItems()) {
			SaleDeliveryScheduleResult scheduleResult = saleDeliveryScheduler
					.getScheduleResult(orderItem);
			
			PayOrderScheduleStockUpdater stockUpdater = (PayOrderScheduleStockUpdater)
					stockUpdaterFactory.create(StockUpdateEvent.PAY_ORDER, scheduleResult);
			stockUpdater.setTccType(TccType.CONFIRM);   
			stockUpdater.update();
		}
		
		return true;
	}
	
}
