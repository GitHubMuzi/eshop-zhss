package com.zhss.eshop.inventory.tcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.eshop.inventory.api.InventoryTCCApi;
import com.zhss.eshop.inventory.constant.TccType;
import com.zhss.eshop.inventory.stock.PayOrderStockUpdater;
import com.zhss.eshop.inventory.stock.PayOrderStockUpdaterFactory;
import com.zhss.eshop.inventory.stock.SubmitOrderStockUpdater;
import com.zhss.eshop.inventory.stock.SubmitOrderStockUpdaterFactory;
import com.zhss.eshop.order.domain.OrderInfoDTO;

@Component("inventoryTccConfirmService")
@RequestMapping("/inventory/tcc/confirm")  
public class InventoryTccConfirmService implements InventoryTCCApi {

	/**
	 * 提交订单库存更新组件工厂
	 */
	@Autowired
	private SubmitOrderStockUpdaterFactory<OrderInfoDTO> 
			submitOrderStockUpdaterFactory;
	/**
	 * 支付订单库存更新组件工厂
	 */
	@Autowired
	private PayOrderStockUpdaterFactory<OrderInfoDTO> 
			payOrderStockUpdaterFactory;
	
	@Transactional
	public Boolean informSubmitOrderEvent(OrderInfoDTO orderDTO) {
		SubmitOrderStockUpdater goodsStockUpdateCommand = 
				(SubmitOrderStockUpdater) submitOrderStockUpdaterFactory.create(orderDTO); 
		goodsStockUpdateCommand.setTccType(TccType.CONFIRM);  
		goodsStockUpdateCommand.updateGoodsStock();
		return true;
	}
	
	@Transactional
	public Boolean informPayOrderEvent(@RequestBody OrderInfoDTO orderDTO) {
		PayOrderStockUpdater goodsStockUpdateCommand = 
				(PayOrderStockUpdater) payOrderStockUpdaterFactory.create(orderDTO); 
		goodsStockUpdateCommand.setTccType(TccType.CONFIRM);   
		goodsStockUpdateCommand.updateGoodsStock();
		return true;
	}
	
}
