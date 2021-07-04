package com.zhss.eshop.schedule.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.logistics.api.LogisticsApi;

/**
 * 物流服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-logistics")
public interface LogisticsService extends LogisticsApi {

}
