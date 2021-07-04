package com.zhss.eshop.schedule.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.order.api.OrderApi;

/**
 * 订单服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-order")
public interface OrderService extends OrderApi {

}
