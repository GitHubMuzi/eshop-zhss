package com.zhss.eshop.order.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhss.eshop.common.util.DateProvider;
import com.zhss.eshop.order.constant.OrderStatus;
import com.zhss.eshop.order.dao.OrderInfoDAO;
import com.zhss.eshop.order.domain.OrderInfoDTO;

/**
 * 退货商品待收货状态
 * @author zhonghuashishan
 *
 */
@Component
public class WaitForReceiveReturnGoodsOrderState extends AbstractOrderState {

	@Autowired
	public WaitForReceiveReturnGoodsOrderState(DateProvider dateProvider, OrderInfoDAO orderInfoDAO) {
		super(dateProvider, orderInfoDAO);
	}

	@Override
	protected Integer getOrderStatus(OrderInfoDTO order) throws Exception {
		return OrderStatus.WAIT_FOR_RECEIVE_RETURN_GOODS;
	}

}
