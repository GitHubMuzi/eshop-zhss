package com.zhss.eshop.order.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.customer.api.CustomerApi;

/**
 * 客服服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-customer")
public interface CustomerService extends CustomerApi {

}
