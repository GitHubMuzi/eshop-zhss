package com.zhss.eshop.order.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.membership.api.MembershipTccApi;

@FeignClient("eshop-membership") 
public interface MembershipTccService extends MembershipTccApi {

}
