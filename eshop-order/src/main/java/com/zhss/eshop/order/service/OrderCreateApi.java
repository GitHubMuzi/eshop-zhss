package com.zhss.eshop.order.service;

import com.zhss.eshop.order.domain.OrderInfoVO;

public interface OrderCreateApi {
	
	public OrderInfoVO save(OrderInfoVO orderVO, String requestId) throws Exception;

}
