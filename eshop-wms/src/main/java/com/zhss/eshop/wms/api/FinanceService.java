package com.zhss.eshop.wms.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.finance.api.FinanceApi;

/**
 * 财务服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-finance")
public interface FinanceService extends FinanceApi {

}
