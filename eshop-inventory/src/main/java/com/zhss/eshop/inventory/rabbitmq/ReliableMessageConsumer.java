package com.zhss.eshop.inventory.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zhss.eshop.inventory.api.ReliableMessageService;
import com.zhss.eshop.inventory.api.ScheduleService;
import com.zhss.eshop.inventory.api.ScheduleTccService;
import com.zhss.eshop.inventory.mapper.DataRefillHistoryMapper;
import com.zhss.eshop.order.domain.OrderInfoDTO;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_RELIABLE_MESSAGE)
public class ReliableMessageConsumer {
	
	@Autowired
	private ReliableMessageService reliableMessageService;
	@Autowired
	private DataRefillHistoryMapper dataRefillHistoryMapper;
	@Autowired
	private ScheduleTccService scheduleTccService;
	@Autowired
	private ScheduleService scheduleService;
	
	@RabbitHandler
	public void process(String message) {
		JSONObject messageJSONObject = JSONObject.parseObject(message);
		String dataRefillNo = messageJSONObject.getString("id");  
		String operation = messageJSONObject.getString("operation"); 
		
		try {
			try {
				dataRefillHistoryMapper.create(dataRefillNo);
			} catch (Exception e) {
				Long messageId = messageJSONObject.getLong("_messageId"); 
				reliableMessageService.finishMessage(messageId);
				e.printStackTrace(); 
				return;
			}
			
			// 这块其实是在调用调度服务的接口，完成异步化的库存锁定
			Boolean result = false;
			
			try {
				if("submit_order".equals(operation)) {
					JSONObject orderInfoJsonObject = messageJSONObject.getJSONObject("parameter"); 
					OrderInfoDTO orderDTO = orderInfoJsonObject.toJavaObject(OrderInfoDTO.class);
					result = scheduleTccService.informSubmitOrderEvent(orderDTO);
				} else if("pay_order".equals(operation)) {
					JSONObject orderInfoJsonObject = messageJSONObject.getJSONObject("parameter"); 
					OrderInfoDTO orderDTO = orderInfoJsonObject.toJavaObject(OrderInfoDTO.class);
					result = scheduleTccService.informPayOrderEvent(orderDTO);
				} else if("schedule_delivery".equals(operation)) {
					JSONObject orderInfoJsonObject = messageJSONObject.getJSONObject("parameter"); 
					OrderInfoDTO orderDTO = orderInfoJsonObject.toJavaObject(OrderInfoDTO.class);
					result = scheduleService.scheduleSaleDelivery(orderDTO);
				}
			} catch (Exception e) {
				result = false;
				e.printStackTrace();
			}
			
			if(result) {
				try { 
					Long messageId = messageJSONObject.getLong("_messageId"); 
					reliableMessageService.finishMessage(messageId);
				} catch (Exception e) {
					e.printStackTrace();  
				}
			} else {
				dataRefillHistoryMapper.remove(dataRefillNo); 
			}
		} catch (Exception e) {
			dataRefillHistoryMapper.remove(dataRefillNo); 
			e.printStackTrace();  
		}
	}

}