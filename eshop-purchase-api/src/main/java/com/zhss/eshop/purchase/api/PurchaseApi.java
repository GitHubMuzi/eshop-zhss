package com.zhss.eshop.purchase.api;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhss.eshop.purchase.domain.SupplierDTO;

/**
 * 采购中心接口
 * @author zhonghuashishan
 *
 */
@RequestMapping("/purchase")  
public interface PurchaseApi {
	
	/**
	 * 通知采购中心，“创建采购入库单”事件发生了
	 * @param purcaseOrderId 采购单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informCreatePurchaseInputOrderEvent/{purchaseOrderId}", method = RequestMethod.PUT)
	Boolean informCreatePurchaseInputOrderEvent(@PathVariable("purchaseOrderId") Long purcaseOrderId);
	
	/**
	 * 通知采购中心，“完成采购入库单”事件发生了
	 * @param purcaseOrderId 采购单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informFinishedPurchaseInputOrderEvent/{purchaseOrderId}", method = RequestMethod.PUT)
	Boolean informFinishedPurchaseInputOrderEvent(@PathVariable("purchaseOrderId") Long purcaseOrderId);
	
	/**
	 * 通知采购中心，“创建采购结算单”事件发生了
	 * @param purcaseOrderId 采购单id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informCreatePurchaseSettlementOrderEvent/{purchaseOrderId}", method = RequestMethod.PUT)
	Boolean informCreatePurchaseSettlementOrderEvent(@PathVariable("purchaseOrderId") Long purcaseOrderId);
	
	/**
	 * 通知采购中心，“完成采购结算单”事件发生了
	 * @param purchaseOrderId
	 * @return
	 */
	@RequestMapping(value = "/informFinishedPurchaseSettlementOrderEvent/{purchaseOrderId}", method = RequestMethod.PUT)
	Boolean informFinishedPurchaseSettlementOrderEvent(@PathVariable("purchaseOrderId") Long purchaseOrderId);
	
	/**
	 * 根据结算周期来查询供应商
	 * @param settlementPeriod 结算周期
 	 * @return 供应商
	 */
	@RequestMapping(value = "/listSuppliersBySettlementPeriod", method = RequestMethod.GET)
	List<SupplierDTO> listSuppliersBySettlementPeriod(
			@RequestParam("settlementPeriod") Integer settlementPeriod);
	
}
