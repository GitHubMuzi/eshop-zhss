package com.zhss.eshop.schedule.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.eshop.order.domain.OrderInfoDTO;

@RequestMapping("/schedule/tcc")  
public interface ScheduleTccApi {
	
	/**
	 * 通知库存中心，“提交订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informSubmitOrderEvent", method = RequestMethod.PUT)
	Boolean informSubmitOrderEvent(@RequestBody OrderInfoDTO orderDTO) throws Exception;
	
	/**
	 * 通知库存中心，“支付订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informPayOrderEvent", method = RequestMethod.PUT)
	Boolean informPayOrderEvent(@RequestBody OrderInfoDTO orderDTO) throws Exception;
}
