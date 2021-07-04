package com.zhss.eshop.order.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.inventory.api.InventoryApi;

/**
 * 库存服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-inventory")
public interface InventoryService extends InventoryApi {

}
