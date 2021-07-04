package com.zhss.reliable.message.service.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zhss.reliable.message.service.api.DataRefillCenterService;
import com.zhss.reliable.message.service.constant.MessageStatus;
import com.zhss.reliable.message.service.domain.Message;
import com.zhss.reliable.message.service.mapper.MessageMapper;
import com.zhss.reliable.message.service.rabbitmq.MessageProducer;

@Component
public class ScanPreparedMessageTask {

	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private DataRefillCenterService dataRefillCenterService;
	@Autowired
	private MessageProducer messageProducer;
	
	@Scheduled(fixedRate = 10 * 60 * 1000)
	public void execute() {
		List<Message> messages = messageMapper.findByStatus(MessageStatus.PREPARED);  
		
		for(Message message : messages) {
			try {
				checkPreparedMessage(message); 
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}
	}
	
	@Transactional
	private void checkPreparedMessage(Message message) {
		long createdTime = message.getCreatedTime().getTime();
		long currentTime = new Date().getTime();
		
		if(currentTime - createdTime > 10 * 60 * 1000) {
			System.out.println("发现一条待确认超过10分钟的消息，messageId=" + message.getId());  
			
			Boolean operationStatus = dataRefillCenterService.queryOperationStatus(
					message.getContent()); 
			System.out.println("回查流量充值服务，消息对应的操作状态为：" + operationStatus + ", messageId=" + message.getId());   
			
			if(operationStatus) {
				message.setStatus(MessageStatus.CONFIRMED); 
				message.setConfirmedTime(new Date());  
				
				messageMapper.confirm(message); 
				messageProducer.send(message.getContent());  
				
				System.out.println("操作已经执行过，再次确认消息以及投递消息，messageId=" + message.getId());  
			} else {
				message.setStatus(MessageStatus.REMOVED); 
				message.setRemovedTime(new Date());  
				
				messageMapper.remove(message); 
				
				System.out.println("操作没有执行过，删除消息，messageId=" + message.getId());  
			}
		}
	}
	
}
