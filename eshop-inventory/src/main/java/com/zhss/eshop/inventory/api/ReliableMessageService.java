package com.zhss.eshop.inventory.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.reliable.message.service.api.ReliableMessageApi;

@FeignClient("reliable-message-service")  
public interface ReliableMessageService extends ReliableMessageApi {

}
