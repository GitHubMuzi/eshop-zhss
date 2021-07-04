package com.zhss.eshop.schedule.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zhss.eshop.inventory.constant.GoodsStockUpdateOperation;
import com.zhss.eshop.order.domain.OrderInfoDTO;

/**
 * 库存更新消息消费者
 * @author zhonghuashishan
 *
 */
@Component
public class StockUpdateMessageConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(
			StockUpdateMessageConsumer.class);
	
	@Autowired
	private StockUpdateProcessor stockUpdateProcessor;

	/**
	 * 消费库存更新消息
	 */
	public void consume(String messageJson) { 
		try {
			StockUpdateMessage message = JSONObject.parseObject(
					messageJson, StockUpdateMessage.class);
			if(!isOrderRelatedMessage(message)) {
				return;
			}
			OrderInfoDTO order = getOrderFromMessage(message);
			processMessage(message, order);
		} catch (Exception e) {
			logger.error("error", e); 
		}
	}
	
	/**
	 * 是否是订单相关的操作
	 * @param message 消息
	 * @return 是否是订单相关的操作
	 * @throws Exception
	 */
	private Boolean isOrderRelatedMessage(StockUpdateMessage message) throws Exception {
		return GoodsStockUpdateOperation.SUBMIT_ORDER.equals(message.getOperation())
				|| GoodsStockUpdateOperation.CANCEL_ORDER.equals(message.getOperation()) 
				|| GoodsStockUpdateOperation.PAY_ORDER.equals(message.getOperation());
	}
	
	/**
	 * 从消息中获取订单
	 * @param message 消息
	 * @return 订单
	 * @throws Exception
	 */
	private OrderInfoDTO getOrderFromMessage(StockUpdateMessage message) throws Exception {
		JSONObject orderInfoJsonObject = (JSONObject) message.getParameter();
		return orderInfoJsonObject.toJavaObject(OrderInfoDTO.class);
	}
	
	/**
	 * 处理消息
	 * @param order 订单 
	 * @return 处理结果
	 * @throws Exception
	 */
	private void processMessage(StockUpdateMessage message, 
			OrderInfoDTO order) throws Exception {
		if(GoodsStockUpdateOperation.SUBMIT_ORDER.equals(message.getOperation())) {
			stockUpdateProcessor.informSubmitOrderEvent(order);
		} else if(GoodsStockUpdateOperation.CANCEL_ORDER.equals(message.getOperation())) {
			stockUpdateProcessor.informCancelOrderEvent(order);
		} else if(GoodsStockUpdateOperation.PAY_ORDER.equals(message.getOperation())) {
			stockUpdateProcessor.informPayOrderEvent(order);
		}
	}
	
}
