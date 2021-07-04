package com.zhss.eshop.wms.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.eshop.wms.domain.PurchaseInputOrderDTO;
import com.zhss.eshop.wms.domain.ReturnGoodsInputOrderDTO;
import com.zhss.eshop.wms.domain.SaleDeliveryOrderDTO;
import com.zhss.eshop.wms.domain.WmsSaleDeliveryScheduleResult;

/**
 * WMS中心接口
 * @author zhonghuashishan
 *
 */
@RequestMapping("/wms")  
public interface WmsApi {

	/**
	 * 创建采购入库单
	 * @param purchaseInputOrderDTO 采购入库单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/createPurchaseInputOrder", method = RequestMethod.POST)
	Boolean createPurchaseInputOrder(@RequestBody PurchaseInputOrderDTO purchaseInputOrderDTO);
	
	/**
	 * 创建销售出库单
	 * @param saleDeliveryOrderDTO 销售出库单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/createSaleDeliveryOrder", method = RequestMethod.POST)
	Boolean createSaleDeliveryOrder(@RequestBody SaleDeliveryOrderDTO saleDeliveryOrder);
	
	/**
	 * 创建退货入库单
	 * @param returnGoodsInputOrder 退货入库单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/createReturnGoodsInputOrder", method = RequestMethod.POST)
	Boolean createReturnGoodsInputOrder(@RequestBody ReturnGoodsInputOrderDTO returnGoodsInputOrder);
	
	/**
	 * 通知WMS中心，“提交订单”事件发生了
	 * @param scheduleResult 调度结果
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informSubmitOrderEvent", method = RequestMethod.PUT)
	Boolean informSubmitOrderEvent(@RequestBody WmsSaleDeliveryScheduleResult scheduleResult);
	
	/**
	 * 通知WMS中心，“支付订单”事件发生了
	 * @param scheduleResult 调度结果
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informPayOrderEvent", method = RequestMethod.PUT)
	Boolean informPayOrderEvent(@RequestBody WmsSaleDeliveryScheduleResult scheduleResult);
	
	/**
	 * 通知WMS中心，“取消订单”事件发生了
	 * @param scheduleResult 调度结果
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informCancelOrderEvent", method = RequestMethod.PUT)
	Boolean informCancelOrderEvent(@RequestBody WmsSaleDeliveryScheduleResult scheduleResult);
	
	/**
	 * 获取订单对应的物流单号 
	 * @param orderId 订单id
	 * @return 物流单号
	 */
	@RequestMapping(value = "/getLogisticCode/{orderId}", method = RequestMethod.GET)
	String getLogisticCode(@PathVariable("orderId") Long orderId);
	
	/**
	 * 通知wms中心，“创建采购结算单”事件发生了
	 * @param purchaseInputOrderId 采购入库单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informCreatePurchaseSettlementOrderEvent", method = RequestMethod.PUT)
	Boolean informCreatePurchaseSettlementOrderEvent(@RequestBody Long purchaseInputOrderId);
	
	/**
	 * 通知wms中心，“完成采购结算单”事件发生了
	 * @param purchaseInputOrderId 采购入库单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informFinishedPurchaseSettlementOrderEvent", method = RequestMethod.PUT)
	Boolean informFinishedPurchaseSettlementOrderEvent(@RequestBody Long purchaseInputOrderId);
	
}
