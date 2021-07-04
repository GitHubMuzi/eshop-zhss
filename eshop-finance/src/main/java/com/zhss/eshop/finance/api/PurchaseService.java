package com.zhss.eshop.finance.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.purchase.api.PurchaseApi;

/**
 * 采购服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-purchase")
public interface PurchaseService extends PurchaseApi {

}
