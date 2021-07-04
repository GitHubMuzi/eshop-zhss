package com.zhss.eshop.schedule.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.wms.api.WmsApi;

/**
 * 仓储服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-wms")
public interface WmsService extends WmsApi {

}
