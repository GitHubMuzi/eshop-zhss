package com.zhss.eshop.order.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitConfig {

	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.port}")
	private int port;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;

	public static final String EXCHANGE_BEST_EFFORT_INFORM = 
			"my-mq-exchange_BEST_EFFORT_INFORM";
	public static final String QUEUE_BEST_EFFORT_INFORM = 
			"QUEUE_BEST_EFFORT_INFORM";
	public static final String ROUTINGKEY_BEST_EFFORT_INFORM = 
			"spring-boot-routingKey_BEST_EFFORT_INFORM";

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = 
				new CachingConnectionFactory(host,port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost("/");
		return connectionFactory;
	}
  
	@Bean
	@Scope("prototype")  
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		return template;
	}

	@Bean
	public DirectExchange defaultExchange() {
		return new DirectExchange(EXCHANGE_BEST_EFFORT_INFORM);
	}

	@Bean
	public Queue queueReliableMessage() {
		return new Queue(QUEUE_BEST_EFFORT_INFORM, true); 
	}  

	@Bean
	public Binding binding() {
		return BindingBuilder
				.bind(queueReliableMessage())
				.to(defaultExchange())
				.with(RabbitConfig.ROUTINGKEY_BEST_EFFORT_INFORM);
	}  
 
}