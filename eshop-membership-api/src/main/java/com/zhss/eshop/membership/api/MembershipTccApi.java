package com.zhss.eshop.membership.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/membership/tcc")  
public interface MembershipTccApi {
	
	/**
	 * 通知会员中心，“支付订单”事件发生了
	 * @param userAccountId 用户账号id
	 * @param totalOrderAmount 订单总金额
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informPayOrderEvent/{userAccountId}", method = RequestMethod.PUT)
	Boolean informPayOrderEvent(
			@PathVariable("userAccountId") Long userAccountId, 
			@RequestParam("totalOrderAmount") Double totalOrderAmount,
			@RequestParam("orderId") Long orderId) throws Exception;

}
