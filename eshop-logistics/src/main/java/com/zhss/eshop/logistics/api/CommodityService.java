package com.zhss.eshop.logistics.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.commodity.api.CommodityApi;

/**
 * 商品服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-commodity")
public interface CommodityService extends CommodityApi {

}
