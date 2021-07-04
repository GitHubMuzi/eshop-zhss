package com.zhss.eshop.wms.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.eshop.wms.domain.WmsSaleDeliveryScheduleResult;

@RequestMapping("/wms/tcc")  
public interface WmsTccApi {

	/**
	 * 通知WMS中心，“提交订单”事件发生了
	 * @param scheduleResult 调度结果
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informSubmitOrderEvent", method = RequestMethod.PUT)
	Boolean informSubmitOrderEvent(
			@RequestBody WmsSaleDeliveryScheduleResult scheduleResult) throws Exception;
	
	/**
	 * 通知WMS中心，“支付订单”事件发生了
	 * @param scheduleResult 调度结果
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informPayOrderEvent", method = RequestMethod.PUT)
	Boolean informPayOrderEvent(@RequestBody WmsSaleDeliveryScheduleResult scheduleResult) throws Exception;
	
}
