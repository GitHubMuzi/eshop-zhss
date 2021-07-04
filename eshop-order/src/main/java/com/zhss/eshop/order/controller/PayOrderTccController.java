package com.zhss.eshop.order.controller;

import org.bytesoft.compensable.Compensable;
import org.bytesoft.compensable.CompensableCancel;
import org.bytesoft.compensable.CompensableConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.order.api.InventoryTccService;
import com.zhss.eshop.order.api.MembershipTccService;
import com.zhss.eshop.order.constant.OrderStatus;
import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.order.mapper.UniqueRecordMapper;
import com.zhss.eshop.order.service.OrderInfoService;
import com.zhss.eshop.order.service.OrderPayApi;
import com.zhss.eshop.order.state.LoggedOrderStateManager;

@RestController
@RequestMapping("/order/pay")  
@Compensable(interfaceClass = OrderPayApi.class, simplified = true)
public class PayOrderTccController implements OrderPayApi {

	/**
	 * 订单状态管理器
	 */
	@Autowired
	private LoggedOrderStateManager orderStateManager;
	/**
	 * 订单管理service组件
	 */
	@Autowired
	private OrderInfoService orderInfoService;
	/**
	 * 库存中心接口
	 */
	@Autowired
	private InventoryTccService inventoryTccService;
	/**
	 * 会员中心接口
	 */
	@Autowired
	private MembershipTccService membershipTccService;
	
	@Autowired
	private UniqueRecordMapper uniqueRecordMapper;

	@RequestMapping(value = "/informPayOrderSuccessed/{orderInfoId}", method = RequestMethod.PUT)
	@Transactional
	public Boolean informPayOrderSuccessed(
			@PathVariable("orderInfoId") Long orderInfoId) throws Exception {
		uniqueRecordMapper.insert("PayOrderTccController_informPayOrderSuccessed_" + orderInfoId); 
		
		OrderInfoDTO order = orderInfoService.getById(orderInfoId);
		order.setOrderStatus(OrderStatus.UNKNOWN); 
		orderInfoService.update(order); 
		
		inventoryTccService.informPayOrderEvent(order);  
		membershipTccService.informPayOrderEvent(
				order.getUserAccountId(), 
				order.getPayableAmount(),
				order.getId()); 
		
		return true;
	}
	
	@CompensableConfirm
	@Transactional
	public Boolean confirmInformPayOrderSuccessed(Long orderInfoId) throws Exception {
		OrderInfoDTO order = orderInfoService.getById(orderInfoId);
		orderStateManager.pay(order);
		return true;
	}
	
	@CompensableCancel
	@Transactional
	public Boolean cancelInformPayOrderSuccessed(Long orderInfoId) throws Exception {
		OrderInfoDTO order = orderInfoService.getById(orderInfoId);
		orderStateManager.cancel(order);
		return true;
	}

}
