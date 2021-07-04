package com.zhss.eshop.schedule.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 消息服务
 * @author zhonghuashishan
 *
 */
public interface MessageService {

	/**
	 * 库存更新消息队列
	 * @return
	 */
	@Input("stock-update-message-queue")
	SubscribableChannel stockUpdateMessageQueue();
	
}