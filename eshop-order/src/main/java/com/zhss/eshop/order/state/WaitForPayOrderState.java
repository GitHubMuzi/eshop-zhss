package com.zhss.eshop.order.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhss.eshop.common.util.DateProvider;
import com.zhss.eshop.order.constant.OrderStatus;
import com.zhss.eshop.order.dao.OrderInfoDAO;
import com.zhss.eshop.order.domain.OrderInfoDTO;

/**
 * 待付款状态
 * @author zhonghuashishan
 *
 */
@Component
public class WaitForPayOrderState extends AbstractOrderState {

	@Autowired
	public WaitForPayOrderState(DateProvider dateProvider, OrderInfoDAO orderInfoDAO) {
		super(dateProvider, orderInfoDAO);
	}

	@Override
	protected Integer getOrderStatus(OrderInfoDTO order) throws Exception {
		return OrderStatus.WAIT_FOR_PAY;
	}
	
	@Override
	public Boolean canPay(OrderInfoDTO order) throws Exception {
		return true;
	}
	
	@Override
	public Boolean canCancel(OrderInfoDTO order) throws Exception {
		return true;
	}

}
