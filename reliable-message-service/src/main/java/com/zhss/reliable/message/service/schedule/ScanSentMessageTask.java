package com.zhss.reliable.message.service.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zhss.reliable.message.service.constant.MessageStatus;
import com.zhss.reliable.message.service.domain.Message;
import com.zhss.reliable.message.service.mapper.MessageMapper;
import com.zhss.reliable.message.service.rabbitmq.MessageProducer;

@Component
public class ScanSentMessageTask {

	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private MessageProducer messageProducer;
	
	@Scheduled(fixedRate = 10 * 60 * 1000)
	public void execute() {
		List<Message> messages = messageMapper.findByStatus(MessageStatus.CONFIRMED); 
		
		for(Message message : messages) {
			try {
				checkConfirmedMessage(message); 
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}
	}
	
	@Transactional
	private void checkConfirmedMessage(Message message) {
		long confirmedTime = message.getConfirmedTime().getTime();
		long currentTime = new Date().getTime();
		
		if(currentTime - confirmedTime > 10 * 60 * 1000) {
			messageProducer.send(message.getContent());  
			System.out.println("发现一条已发送超过10分钟的消息，messageId=" + message.getId() + "，再次投递消息到MQ");   
		}
	}
	
}
