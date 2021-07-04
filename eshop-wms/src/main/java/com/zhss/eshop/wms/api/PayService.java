package com.zhss.eshop.wms.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.pay.api.PayApi;

/**
 * 支付服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-pay")
public interface PayService extends PayApi {

}
