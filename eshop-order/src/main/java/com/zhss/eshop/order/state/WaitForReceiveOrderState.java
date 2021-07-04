package com.zhss.eshop.order.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhss.eshop.common.util.DateProvider;
import com.zhss.eshop.order.constant.OrderStatus;
import com.zhss.eshop.order.dao.OrderInfoDAO;
import com.zhss.eshop.order.domain.OrderInfoDTO;

/**
 * 待收货状态
 * @author zhonghuashishan
 *
 */
@Component
public class WaitForReceiveOrderState extends AbstractOrderState {

	@Autowired
	public WaitForReceiveOrderState(DateProvider dateProvider, OrderInfoDAO orderInfoDAO) {
		super(dateProvider, orderInfoDAO);
	}

	@Override
	protected Integer getOrderStatus(OrderInfoDTO order) throws Exception {
		return OrderStatus.WAIT_FOR_RECEIVE;
	}
	
	@Override
	public Boolean canConfirmReceipt(OrderInfoDTO order) throws Exception {
		return true;
	}

}
