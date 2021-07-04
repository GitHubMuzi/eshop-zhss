package com.zhss.eshop.wms.constant;

/**
 * 库存更新事件
 * @author zhonghuashishan
 *
 */
public class WmsStockUpdateEvent {

	/**
	 * 采购入库
	 */
	public static final Integer PURCHASE_INPUT = 1;
	/**
	 * 退货入库
	 */
	public static final Integer RETURN_GOODS_INPUT = 2;
	/**
	 * 提交订单
	 */
	public static final Integer SUBMIT_ORDER = 3;
	/**
	 * 支付订单
	 */
	public static final Integer PAY_ORDER = 4;
	/**
	 * 取消订单
	 */
	public static final Integer CANCEL_ORDER = 5;
	
	private WmsStockUpdateEvent() {
		
	}
	
}
