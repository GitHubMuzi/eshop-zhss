package com.zhss.reliable.message.service.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.data.refill.center.service.api.DataRefillCenterApi;

@FeignClient("data-refill-center-service")  
public interface DataRefillCenterService extends DataRefillCenterApi {
	
}
