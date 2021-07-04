package com.zhss.eshop.order.controller;

import org.bytesoft.bytetcc.supports.spring.aware.CompensableContextAware;
import org.bytesoft.compensable.Compensable;
import org.bytesoft.compensable.CompensableCancel;
import org.bytesoft.compensable.CompensableConfirm;
import org.bytesoft.compensable.CompensableContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhss.eshop.common.util.CloneDirection;
import com.zhss.eshop.order.api.InventoryTccService;
import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.order.domain.OrderInfoVO;
import com.zhss.eshop.order.mapper.UniqueRecordMapper;
import com.zhss.eshop.order.service.OrderCreateApi;
import com.zhss.eshop.order.service.OrderInfoService;
import com.zhss.eshop.order.state.LoggedOrderStateManager;

@RestController
@RequestMapping("/order/create")  
@Compensable(interfaceClass = OrderCreateApi.class, simplified = true)
public class CreateOrderTccController implements OrderCreateApi, CompensableContextAware {
	
	/**
	 * 订单管理service组件
	 */
	@Autowired
	private OrderInfoService orderInfoService;
	/**
	 * 库存服务
	 */
	@Autowired
	private InventoryTccService inventoryTccService;
	/**
	 * 订单状态管理器
	 */
	@Autowired
	private LoggedOrderStateManager orderStateManager;
	/**
	 * tcc事务上下文
	 */
	private CompensableContext aware;
	@Autowired
	private UniqueRecordMapper uniqueRecordMapper;
	
	/**
	 * 新建订单
	 * @param order
	 * @return
	 */
	@PostMapping("/{requestId}")  
	@Transactional
	public OrderInfoVO save(@RequestBody OrderInfoVO orderVO,  
			@PathVariable("requestId") String requesetId) throws Exception {
		uniqueRecordMapper.insert("CreateOrderTccController_save_" + requesetId); 
		
		OrderInfoDTO order = orderVO.clone(OrderInfoDTO.class, CloneDirection.FORWARD);
		
		if(order.getCouponId() == null) {
			order.setCouponId(-1L); 
		}
		
		OrderInfoDTO resultOrder = orderInfoService.save(order);
		aware.setVariable("orderJSON", JSONObject.toJSONString(resultOrder));    
		inventoryTccService.informSubmitOrderEvent(resultOrder); 
		
		return resultOrder.clone(OrderInfoVO.class, CloneDirection.OPPOSITE);
	}
	
	@CompensableConfirm
	@Transactional
	public void confirmSave(OrderInfoVO orderVO, String requestId) throws Exception {
		String orderJSON = (String) aware.getVariable("orderJSON");
		OrderInfoDTO order = JSONObject.parseObject(orderJSON, OrderInfoDTO.class);  
		orderStateManager.create(order); 
	}
	
	@CompensableCancel
	@Transactional
	public void cancelSave(OrderInfoVO orderVO, String requestId) throws Exception {
		String orderJSON = (String) aware.getVariable("orderJSON");
		OrderInfoDTO order = JSONObject.parseObject(orderJSON, OrderInfoDTO.class);  
		orderStateManager.cancel(order);  
	}

	@Override
	public void setCompensableContext(CompensableContext aware) {
		this.aware = aware;
	}
	
}
