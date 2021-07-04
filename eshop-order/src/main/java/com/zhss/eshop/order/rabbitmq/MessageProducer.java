package com.zhss.eshop.order.rabbitmq;

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
				RabbitConfig.EXCHANGE_BEST_EFFORT_INFORM, 
				RabbitConfig.ROUTINGKEY_BEST_EFFORT_INFORM, 
				message, 
				correlationId);
	}
	
}