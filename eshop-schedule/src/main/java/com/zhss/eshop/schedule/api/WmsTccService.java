package com.zhss.eshop.schedule.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.wms.api.WmsTccApi;

@FeignClient("eshop-wms")
public interface WmsTccService extends WmsTccApi {

}
