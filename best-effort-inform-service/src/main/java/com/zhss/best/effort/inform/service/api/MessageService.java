package com.zhss.best.effort.inform.service.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.data.refill.center.message.api.MessageApi;

@FeignClient("data-refill-center-message") 
public interface MessageService extends MessageApi {

}
