package com.zhss.eshop.inventory.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.schedule.api.ScheduleApi;

@FeignClient("eshop-schedule") 
public interface ScheduleService extends ScheduleApi {

}
