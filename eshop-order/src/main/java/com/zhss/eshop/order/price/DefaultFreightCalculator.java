package com.zhss.eshop.order.price;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhss.eshop.order.api.LogisticsService;
import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.order.domain.OrderItemDTO;

/**
 * 默认的运费计算组件
 * @author zhonghuashishan
 *
 */
@Component
public class DefaultFreightCalculator implements FreightCalculator {

	/**
	 * 物流服务
	 */
	@Autowired
	private LogisticsService logisticsService;
	
	/**
	 * 计算运费
	 */
	@Override
	public Double calculate(OrderInfoDTO order, OrderItemDTO orderItem, 
			PromotionActivityResult result) throws Exception {
		List<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();
		orderItems.add(orderItem);
		order.setOrderItems(orderItems); 
		
		return logisticsService.calculateFreight(order); 
	}
	
}
