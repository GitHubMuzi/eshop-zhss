package com.zhss.best.effort.inform.service.rabbitmq;

import java.util.Date;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zhss.best.effort.inform.service.api.MessageService;
import com.zhss.best.effort.inform.service.constant.MessageStatus;
import com.zhss.best.effort.inform.service.domain.Message;
import com.zhss.best.effort.inform.service.mapper.MessageMapper;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_BEST_EFFORT_INFORM)
public class BestEffortInformConsumer {
 
	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageMapper messageMapper;
	
	@RabbitHandler
	public void process(String message) {
		JSONObject messageJSONObject = JSONObject.parseObject(message);
		String phoneNumber = messageJSONObject.getString("phoneNumber"); 
		String content = messageJSONObject.getString("content"); 
		
		Boolean result = false;
		try {
			result = messageService.send(phoneNumber, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		if(!result) {
			createMessage(messageJSONObject); 
		}
	}
	
	private void createMessage(JSONObject messageJSONObject) {
		Message message = new Message();
		message.setPhoneNumber(messageJSONObject.getString("phoneNumber")); 
		message.setContent(messageJSONObject.getString("content")); 
		message.setRetryRule(messageJSONObject.getString("retryRule"));  
		message.setStatus(MessageStatus.UNSENT);  
		message.setLatestSendTime(new Date());
		message.setCurrentRetryCount(0);  
		
		messageMapper.create(message); 
	}

}