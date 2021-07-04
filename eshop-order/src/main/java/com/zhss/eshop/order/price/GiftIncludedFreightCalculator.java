package com.zhss.eshop.order.price;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhss.eshop.order.api.LogisticsService;
import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.order.domain.OrderItemDTO;

/**
 * 含赠品的运费计算组件
 * @author zhonghuashishan
 *
 */
@Component
public class GiftIncludedFreightCalculator implements FreightCalculator {
	
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
		
		Double freight = 0.0;
		
		freight += logisticsService.calculateFreight(order);  
		
		for(OrderItemDTO giftItem : result.getOrderItems()) {
			List<OrderItemDTO> giftItems = new ArrayList<OrderItemDTO>();
			giftItems.add(giftItem);
			order.setOrderItems(giftItems);
			freight += logisticsService.calculateFreight(order);
		}
		
		return freight;
	}

}
