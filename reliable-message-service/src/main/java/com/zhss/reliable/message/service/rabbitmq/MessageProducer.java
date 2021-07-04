package com.zhss.reliable.message.service.rabbitmq;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

	private RabbitTemplate rabbitTemplate;

	@Autowired
	public MessageProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void send(String message) {
		CorrelationData correlationId = new CorrelationData(
				UUID.randomUUID().toString());
		
		rabbitTemplate.convertAndSend(
				RabbitConfig.EXCHANGE_RELIABLE_MESSAGE, 
				RabbitConfig.ROUTINGKEY_RELIABLE_MESSAGE, 
				message, 
				correlationId);
	}
	
}