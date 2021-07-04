package com.zhss.reliable.message.service.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhss.reliable.message.service.constant.MessageStatus;
import com.zhss.reliable.message.service.domain.Message;
import com.zhss.reliable.message.service.mapper.MessageMapper;
import com.zhss.reliable.message.service.rabbitmq.MessageProducer;

@RestController
public class ReliableMessageService implements ReliableMessageApi {

	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private MessageProducer messageProducer;
	
	@Override
	@Transactional
	public Long prepareMessage(@RequestParam("message") String message) {
		Message reliableMessage = new Message();
		reliableMessage.setContent(message); 
		reliableMessage.setStatus(MessageStatus.PREPARED); 
		reliableMessage.setCreatedTime(new Date());  
		
		messageMapper.create(reliableMessage);
		
		Long messageId = reliableMessage.getId(); 
	
		JSONObject messageJSONObject = JSONObject.parseObject(message);
		messageJSONObject.put("_messageId", messageId);
		message = messageJSONObject.toJSONString();
		
		reliableMessage.setContent(message); 
		messageMapper.updateContent(reliableMessage);
		
		System.out.println("步骤2：可靠消息服务保存待确认消息，message=" + message + ", messageId=" + messageId);  
		
		return messageId;
	}
	
	@Override
	@Transactional
	public Boolean confirmMessage(@RequestParam("messageId") Long messageId) {
		Message reliableMessage = messageMapper.findById(messageId);
		reliableMessage.setStatus(MessageStatus.CONFIRMED); 
		reliableMessage.setConfirmedTime(new Date());  
		
		messageMapper.confirm(reliableMessage); 
		messageProducer.send(reliableMessage.getContent());  
		
		System.out.println("步骤5：可靠消息服务确认消息以及投递消息到MQ，messageId=" + messageId); 
		
		return true;
	}

	@Override
	@Transactional
	public Boolean removeMessage(@RequestParam("messageId") Long messageId) {
		Message reliableMessage = messageMapper.findById(messageId);
		reliableMessage.setStatus(MessageStatus.REMOVED); 
		reliableMessage.setRemovedTime(new Date());  
		
		messageMapper.remove(reliableMessage); 
		
		System.out.println("步骤5：可靠消息服务删除消息，messageId=" + messageId); 
		
		return true;
	}

	@Override
	@Transactional
	public Boolean finishMessage(@RequestParam("messageId") Long messageId) {
		Message reliableMessage = messageMapper.findById(messageId);
		reliableMessage.setStatus(MessageStatus.FINISHED); 
		reliableMessage.setFinishedTime(new Date());  
		
		messageMapper.finish(reliableMessage); 
		
		return true;
	}

}
