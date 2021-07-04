package com.zhss.eshop.inventory.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.schedule.api.ScheduleTccApi;

@FeignClient("eshop-schedule")
public interface ScheduleTccService extends ScheduleTccApi {

}
