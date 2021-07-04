package com.zhss.eshop.customer.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 客服中心对外提供的接口
 * @author zhonghuashishan
 *
 */
@RequestMapping("/customer")  
public interface CustomerApi {

	/**
	 * 创建退货工单
	 * @param orderId 订单id
	 * @param orderNo 订单编号
	 * @param returnGoodsReason 退货原因
	 * @param returnGoodsRemark 退货备注
	 * @return 处理结果
	 */
	@RequestMapping(value = "/createReturnGoodsWorksheet", method = RequestMethod.POST)
	Boolean createReturnGoodsWorksheet(
			@RequestParam("orderId") Long orderId, 
			@RequestParam("orderNo") String orderNo, 
			@RequestParam("returnGoodsReason") Integer returnGoodsReason, 
			@RequestParam("returnGoodsRemark") String returnGoodsRemark);
	
	/**
	 * 同步物流单号
	 * @param orderInfoId 订单id
	 * @param returnGoodsLogisticsCode 退货物流单号
	 * @return 处理结果
	 */
	@RequestMapping(value = "/syncReturnGoodsLogisticsCode", method = RequestMethod.PUT)
	Boolean syncReturnGoodsLogisticsCode(
			@RequestParam("orderInfoId") Long orderInfoId, 
			@RequestParam("returnGoodsLogisticsCode") String returnGoodsLogisticsCode);
	
	/**
	 * 通知客服中心，“完成退货入库”事件发生了
	 * @param returnGoodsWorksheetId 退货工单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informReturnGoodsInputFinishedEvent/{returnGoodsWorksheetId}", method = RequestMethod.PUT)
	Boolean informReturnGoodsInputFinishedEvent(@PathVariable("returnGoodsWorksheetId") Long returnGoodsWorksheetId);
	
	/**
	 * 通知客服中心，“完成退款”事件发生了
	 * @param returnGoodsWorkwheetId 退货工单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informRefundFinishedEvent/{returnGoodsWorksheetId}", method = RequestMethod.PUT)
	Boolean informRefundFinishedEvent(@PathVariable("returnGoodsWorksheetId") Long returnGoodsWorkwheetId);
	
}
