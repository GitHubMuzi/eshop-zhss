package com.zhss.best.effort.inform.service.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zhss.best.effort.inform.service.api.MessageService;
import com.zhss.best.effort.inform.service.constant.MessageStatus;
import com.zhss.best.effort.inform.service.constant.RetryType;
import com.zhss.best.effort.inform.service.domain.Message;
import com.zhss.best.effort.inform.service.mapper.MessageMapper;

@Component
public class BestEffortInformTask {

	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private MessageService messageService;
	
	@Scheduled(fixedRate = 5 * 1000)
	public void execute() {
		List<Message> messages = messageMapper.findByStatus(MessageStatus.UNSENT);
		
		for(Message message : messages) {
			JSONObject retryRule = JSONObject.parseObject(message.getRetryRule());
			
			Integer type = retryRule.getInteger("type"); 
			if(RetryType.FIXED_RATE.equals(type)) {
				fixedRateTry(message, retryRule); 
			} else if(RetryType.ACCUMULATED_RATE.equals(type)) {
				accumulatedRateTry(message, retryRule); 
			}
		}
	}
	
	private void fixedRateTry(Message message, JSONObject retryRule) {
		Long retryInterval = retryRule.getLong("retryInterval");
		Long retryIntervalMs = retryInterval * 1000;
		
		Long latestSendTime = message.getLatestSendTime().getTime();
		Long currentTime = new Date().getTime();
		if(currentTime - latestSendTime < retryIntervalMs) {
			return;
		}
		
		doRetry(message, retryRule); 
	}
	
	private void accumulatedRateTry(Message message, JSONObject retryRule) {
		Integer currentRetryCount = message.getCurrentRetryCount();
		
		Long retryInterval = retryRule.getLong("retryInterval");
		Long retryIntervalMs = retryInterval * 1000;
		Long currentRetryIntervalMs = retryIntervalMs * (currentRetryCount + 1);
		
		// 当前重试次数是0，重试间隔是10秒，第一次重试，就是应该是10秒 * （0 + 1） = 10秒
		// 当前重试次数是1，重试间隔是10秒，第二次重试，就是应该是10秒 * （1 + 1） = 20秒
		
		Long latestSendTime = message.getLatestSendTime().getTime();
		Long currentTime = new Date().getTime();
		if(currentTime - latestSendTime < currentRetryIntervalMs) {
			return;
		}
		
		doRetry(message, retryRule); 
	}
	
	private void doRetry(Message message, JSONObject retryRule) {
		Integer currentRetryCount = message.getCurrentRetryCount();
		Integer maxRetryCount = retryRule.getInteger("maxRetryCount"); 
		
		if(currentRetryCount < maxRetryCount) {
			Boolean result = false;
			try {
				result = messageService.send(message.getPhoneNumber(), 
						message.getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			currentRetryCount += 1;
			messageMapper.increaseCurrentRetryCount(message.getId()); 
			messageMapper.updateLatestSendTime(message.getId(), new Date());  
			
			if(result) {
				messageMapper.updateStatus(message.getId(), 
						MessageStatus.SUCCESS); 
				return;
			} else {
				if(currentRetryCount == maxRetryCount) {
					messageMapper.updateStatus(message.getId(), 
							MessageStatus.FAILURE);  
				}
			}
		}
	}
	
}
