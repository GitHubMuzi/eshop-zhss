package com.zhss.eshop.order.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.membership.api.MembershipApi;

/**
 * δΌεζε‘
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-membership")
public interface MembershipService extends MembershipApi {

}
