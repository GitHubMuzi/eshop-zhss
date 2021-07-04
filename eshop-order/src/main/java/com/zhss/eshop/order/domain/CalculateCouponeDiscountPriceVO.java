package com.zhss.eshop.order.domain;

import com.zhss.eshop.common.util.AbstractObject;
import com.zhss.eshop.promotion.domain.CouponDTO;

/**
 * 计算优惠券抵扣金额的VO类
 * @author zhonghuashishan
 *
 */
public class CalculateCouponeDiscountPriceVO extends AbstractObject {

	/**
	 * 订单
	 */
	private OrderInfoVO order;
	/**
	 * 优惠券
	 */
	private CouponDTO coupon;
	
	public OrderInfoVO getOrder() {
		return order;
	}
	public void setOrder(OrderInfoVO order) {
		this.order = order;
	}
	public CouponDTO getCoupon() {
		return coupon;
	}
	public void setCoupon(CouponDTO coupon) {
		this.coupon = coupon;
	}
	
	@Override
	public String toString() {
		return "CalculateCouponeDiscountPriceVO [order=" + order + ", coupon=" + coupon + "]";
	}
	
}
