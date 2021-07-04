package com.zhss.eshop.inventory.api;

import org.bytesoft.compensable.Compensable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.inventory.constant.TccType;
import com.zhss.eshop.inventory.mapper.UniqueRecordMapper;
import com.zhss.eshop.inventory.stock.PayOrderStockUpdater;
import com.zhss.eshop.inventory.stock.PayOrderStockUpdaterFactory;
import com.zhss.eshop.inventory.stock.SubmitOrderStockUpdater;
import com.zhss.eshop.inventory.stock.SubmitOrderStockUpdaterFactory;
import com.zhss.eshop.order.domain.OrderInfoDTO;

@RestController
@Compensable(interfaceClass = InventoryTCCApi.class, 
				confirmableKey = "inventoryTccConfirmService", 
				cancellableKey = "inventoryTccCancelService")
public class InventoryTccService implements InventoryTCCApi {
	
	/**
	 * 提交订单库存更新组件工厂
	 */
	@Autowired
	private SubmitOrderStockUpdaterFactory<OrderInfoDTO> submitOrderStockUpdaterFactory;
	/**
	 * 支付订单库存更新组件工厂
	 */
	@Autowired
	private PayOrderStockUpdaterFactory<OrderInfoDTO> payOrderStockUpdaterFactory;
	@Autowired
	private UniqueRecordMapper uniqueRecordMapper;
	
	@Override
	@Transactional
	public Boolean informSubmitOrderEvent(@RequestBody OrderInfoDTO orderDTO) {
		uniqueRecordMapper.insert("InventoryTccService_informSubmitOrderEvent_" + orderDTO.getId());  
		SubmitOrderStockUpdater goodsStockUpdateCommand = 
				(SubmitOrderStockUpdater) submitOrderStockUpdaterFactory.create(orderDTO); 
		goodsStockUpdateCommand.setTccType(TccType.TRY);  
		goodsStockUpdateCommand.updateGoodsStock();
//		throw new IllegalStateException("error");
		return true;
	}

	@Override
	@Transactional
	public Boolean informPayOrderEvent(@RequestBody OrderInfoDTO orderDTO) {
		uniqueRecordMapper.insert("InventoryTccService_informPayOrderEvent_" + orderDTO.getId());  
		PayOrderStockUpdater goodsStockUpdateCommand = 
				(PayOrderStockUpdater) payOrderStockUpdaterFactory.create(orderDTO); 
		goodsStockUpdateCommand.setTccType(TccType.TRY);  
		goodsStockUpdateCommand.updateGoodsStock();
		return true;
	}
	
}
