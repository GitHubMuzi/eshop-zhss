package com.zhss.eshop.wms.api;

import org.bytesoft.compensable.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.wms.constant.TccType;
import com.zhss.eshop.wms.constant.WmsStockUpdateEvent;
import com.zhss.eshop.wms.domain.WmsSaleDeliveryScheduleResult;
import com.zhss.eshop.wms.mapper.UniqueRecordMapper;
import com.zhss.eshop.wms.stock.PayOrderWmsStockUpdater;
import com.zhss.eshop.wms.stock.SubmitOrderWmsStockUpdater;
import com.zhss.eshop.wms.stock.WmsStockUpdaterFactory;

@RestController
@Compensable(interfaceClass = WmsTccApi.class, 
				confirmableKey = "wmsTccConfirmService", 
				cancellableKey = "wmsTccCancelService")
public class WmsTccService implements WmsTccApi {

	/**
	 * 库存更新组件工厂
	 */
	@Autowired
	private WmsStockUpdaterFactory stockUpdaterFactory;
	
	@Autowired
	private UniqueRecordMapper uniqueRecordMapper;
	
	/**
	 * 通知WMS中心，“提交订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Transactional(rollbackFor = Exception.class) 
	public Boolean informSubmitOrderEvent(
			@RequestBody WmsSaleDeliveryScheduleResult scheduleResult) throws Exception {
		uniqueRecordMapper.insert("WmsTccService_informSubmitOrderEvent_" + scheduleResult.getOrderItem().getId()); 
		SubmitOrderWmsStockUpdater stockUpdater = (SubmitOrderWmsStockUpdater)
				stockUpdaterFactory.create(WmsStockUpdateEvent.SUBMIT_ORDER, scheduleResult);
		stockUpdater.setTccType(TccType.TRY);  
		stockUpdater.update();
		return true;
	}

	/**
	 * 通知WMS中心，“支付订单”事件发生了
	 * @param scheduleResult 调度结果
	 * @return 处理结果
	 */
	@Transactional(rollbackFor = Exception.class) 
	public Boolean informPayOrderEvent(
			@RequestBody WmsSaleDeliveryScheduleResult scheduleResult) throws Exception {
		uniqueRecordMapper.insert("WmsTccService_informPayOrderEvent_" + scheduleResult.getOrderItem().getId()); 
		PayOrderWmsStockUpdater stockUpdater = (PayOrderWmsStockUpdater)
				stockUpdaterFactory.create(WmsStockUpdateEvent.PAY_ORDER, scheduleResult);
		stockUpdater.setTccType(TccType.TRY);  
		stockUpdater.update();
		return true;
	}

}
