package com.zhss.eshop.order.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.promotion.api.PromotionApi;

/**
 * 促销服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-promotion")
public interface PromotionService extends PromotionApi {

}
