package com.zhss.eshop.order.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.inventory.api.InventoryTCCApi;

@FeignClient("eshop-inventory")
public interface InventoryTccService extends InventoryTCCApi {

}
