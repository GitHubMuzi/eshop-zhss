package com.zhss.eshop.order.api;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.eshop.order.domain.OrderInfoDTO;

/**
 * 订单中心接口
 * @author zhonghuashishan
 *
 */
@RequestMapping("/order") 
public interface OrderApi {

	/**
	 * 通知订单中心，“商品完成发货”事件发生了
	 * @param orderId 订单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informGoodsDeliveryFinishedEvent/{orderId}", method = RequestMethod.PUT)
	Boolean informGoodsDeliveryFinishedEvent(@PathVariable("orderId") Long orderId);
	
	/**
	 * 通知订单中心，“退货工单审核不通过”事件发生了
	 * @param orderId 订单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informReturnGoodsWorksheetRejectedEvent/{orderId}", method = RequestMethod.PUT)
	Boolean informReturnGoodsWorksheetRejectedEvent(@PathVariable("orderId") Long orderId);
	
	/**
	 * 通知订单中心，“退货工单审核通过”事件发生了
	 * @param orderId 订单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informReturnGoodsWorsheetApprovedEvent/{orderId}", method = RequestMethod.PUT)
	Boolean informReturnGoodsWorsheetApprovedEvent(@PathVariable("orderId") Long orderId);
	
	/**
	 * 通知订单中心，“确认收到退货商品”事件发生了
	 * @param orderId 订单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informReturnGoodsReceivedEvent/{orderId}", method = RequestMethod.PUT)
	Boolean informReturnGoodsReceivedEvent(@PathVariable("orderId") Long orderId);
	
	/**
	 * 通知订单中心，“退货入库单审核通过”事件发生了
	 * @param orderId 订单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informReturnGoodsInputOrderApprovedEvent/{orderId}", method = RequestMethod.PUT)
	Boolean informReturnGoodsInputOrderApprovedEvent(@PathVariable("orderId") Long orderId);
	
	/**
	 * 通知订单中心，“完成退款”事件发生了
	 * @param orderId 订单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informRefundFinishedEvent/{orderId}", method = RequestMethod.PUT)
	Boolean informRefundFinishedEvent(@PathVariable("orderId") Long orderId);
	
	/**
	 * 通知订单中心，“订单发表评论”事件发生了
	 * @param orderId 订单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informPublishCommentEvent/{orderId}", method = RequestMethod.PUT)
	Boolean informPublishCommentEvent(@PathVariable("orderId") Long orderId);
	
	/**
	 * 从订单中心获取，确认收货时间超过了7天，而且还没有发表评论的订单
	 * @return 订单信息DTO集合
	 */
	@RequestMapping(value = "/listNotPublishedCommentOrders", method = RequestMethod.GET)
	List<OrderInfoDTO> listNotPublishedCommentOrders();
	
	/**
	 * 通知订单中心，“订单批量发表评论”事件发生了
	 * @param orderIds 订单id集合
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informBatchPublishCommentEvent", method = RequestMethod.PUT)
	Boolean informBatchPublishCommentEvent(@RequestBody List<Long> orderIds);
	
	/**
	 * 通知订单中心，“支付订单成功了”
	 * @param orderInfoId 订单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informPayOrderSuccessed/{orderInfoId}", method = RequestMethod.PUT)
	Boolean informPayOrderSuccessed(@PathVariable("orderInfoId") Long orderInfoId);
	
	/**
	 * 根据id查询订单
	 * @param orderInfoId 订单id
	 * @return 订单
	 */
	@RequestMapping(value = "/getOrderById/{orderInfoId}", method = RequestMethod.GET)
	OrderInfoDTO getOrderById(@PathVariable("orderInfoId") Long orderInfoId);
	
}
