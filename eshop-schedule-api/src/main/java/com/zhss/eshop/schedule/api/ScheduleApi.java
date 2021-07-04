package com.zhss.eshop.schedule.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.purchase.domain.PurchaseOrderDTO;
import com.zhss.eshop.schedule.domain.SaleDeliveryScheduleResult;
import com.zhss.eshop.schedule.domain.ScheduleReturnGoodsWorksheetDTO;
import com.zhss.eshop.wms.domain.PurchaseInputOrderDTO;
import com.zhss.eshop.wms.domain.ReturnGoodsInputOrderDTO;

/**
 * 调度中心对外提供的接口
 * @author zhonghuashishan
 *
 */
@RequestMapping("/schedule")  
public interface ScheduleApi {
	
	/**
	 * 通知库存中心，“采购入库完成”事件发生了
	 * @param purchaseInputOrderDTO 采购入库单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informPurchaseInputFinished", method = RequestMethod.PUT)
	Boolean informPurchaseInputFinished(
			@RequestBody PurchaseInputOrderDTO purchaseInputOrderDTO);
	
	/**
	 * 通知库存中心，“提交订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informSubmitOrderEvent", method = RequestMethod.PUT)
	Boolean informSubmitOrderEvent(@RequestBody OrderInfoDTO orderDTO);
	
	/**
	 * 通知库存中心，“支付订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informPayOrderEvent", method = RequestMethod.PUT)
	Boolean informPayOrderEvent(@RequestBody OrderInfoDTO orderDTO);
	
	/**
	 * 通知库存中心，“取消订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informCancelOrderEvent", method = RequestMethod.PUT)
	Boolean informCancelOrderEvent(@RequestBody OrderInfoDTO orderDTO);
	
	/**
	 * 通知库存中心，“完成退货入库”事件发生了
	 * @param returnGoodsInputOrderDTO 退货入库单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informReturnGoodsInputFinished", method = RequestMethod.PUT)
	Boolean informReturnGoodsInputFinished(
			@RequestBody ReturnGoodsInputOrderDTO returnGoodsInputOrderDTO);
	
	/**
	 * 调度采购入库
	 * @param purchaseOrderDTO 采购单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/schedulePurchaseInput", method = RequestMethod.PUT)
	Boolean schedulePurchaseInput(@RequestBody PurchaseOrderDTO purchaseOrderDTO);

	/**
	 * 调度销售出库
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/scheduleSaleDelivery", method = RequestMethod.PUT)
	Boolean scheduleSaleDelivery(@RequestBody OrderInfoDTO orderDTO);
	
	/**
	 * 调度退货入库
	 * @param orderDTO 订单DTO
	 * @param returnGoodsWorksheetDTO 退货工单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/scheduleReturnGoodsInput", method = RequestMethod.PUT)
	Boolean scheduleReturnGoodsInput(
			@RequestBody ScheduleReturnGoodsWorksheetDTO returnGoodsWorksheet);  
	
	/**
	 * 获取调度结果
	 * @param orderInfoId 订单id
	 * @param goodsSkuId 商品sku id
	 * @return 调度结果
	 */
	@RequestMapping(value = "/getScheduleResult", method = RequestMethod.GET)
	SaleDeliveryScheduleResult getScheduleResult(
			@RequestParam("orderInfoId") Long orderInfoId, 
			@RequestParam("goodsSkuId") Long goodsSkuId);
	
}
