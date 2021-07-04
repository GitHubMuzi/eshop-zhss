package com.zhss.eshop.pay.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.wms.domain.ReturnGoodsInputOrderDTO;

/**
 * 支付中心接口
 * @author zhonghuashishan
 *
 */
@RequestMapping("/pay")  
public interface PayApi {

	/**
	 * 获取支付二维码
	 * @param order 订单
	 * @return 支付二维码
	 */
	@RequestMapping(value = "/getQrCode", method = RequestMethod.POST)
	String getQrCode(@RequestBody OrderInfoDTO order);
	
	/**
	 * 进行退款
	 * @param returnGoodsInputOrder 退货入库单
	 * @return 退款结果
	 */
	@RequestMapping(value = "/v", method = RequestMethod.PUT)
	Boolean refund(@RequestBody ReturnGoodsInputOrderDTO returnGoodsInputOrder);
	
}
