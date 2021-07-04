package com.zhss.eshop.wms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhss.eshop.wms.api.PurchaseService;
import com.zhss.eshop.wms.domain.PurchaseInputOrderDTO;

/**
 * 通知采购中心的handler
 * @author zhonghuashishan
 *
 */
@Component
public class InformPurchaseCenterHandler extends AbstractPurchaseInputOrderHandler {
	
	/**
	 * 采购服务
	 */
	@Autowired
	private PurchaseService purchaseService;
	
	/**
	 * 执行处理逻辑
	 */
	@Override
	public PurchaseInputOrderHandlerResult doExecute(
			PurchaseInputOrderDTO purchaseInputOrder) throws Exception {
		purchaseService.informFinishedPurchaseInputOrderEvent(
				purchaseInputOrder.getPurchaseOrderId());
		return new PurchaseInputOrderHandlerResult(true); 
	}

}
