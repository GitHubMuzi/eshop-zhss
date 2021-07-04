package com.zhss.eshop.inventory.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

/**
 * 消息中间件的接口
 * @author zhonghuashishan
 *
 */
public interface MessageService {

	/**
	 * 库存更新消息队列
	 * @return
	 */
	@Output("stock-update-message-queue") 
	SubscribableChannel stockUpdateMessageQueue();
	
}