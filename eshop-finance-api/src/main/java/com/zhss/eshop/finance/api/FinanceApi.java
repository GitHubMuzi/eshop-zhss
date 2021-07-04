package com.zhss.eshop.finance.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhss.eshop.wms.domain.PurchaseInputOrderDTO;
import com.zhss.eshop.wms.domain.SaleDeliveryOrderDTO;

/**
 * 财务中心对外提供的接口
 * @author zhonghuashishan
 *
 */
@RequestMapping("/finance")  
public interface FinanceApi {

	/**
	 * 创建采购结算单
	 * @param purchaseInputOrderDTO 采购入库单DTO
	 * @return 处理结果
	 */
	@RequestMapping(value = "/createPurchaseSettlementOrder", method = RequestMethod.POST)
	Boolean createPurchaseSettlementOrder(@RequestBody PurchaseInputOrderDTO purchaseInputOrder);
	
	/**
	 * 给物流公司打款
	 * @param saleDeliveryOrderDTO 销售出库单
	 * @return 处理结果
	 */
	@RequestMapping(value = "/payForLogisticsCompany", method = RequestMethod.PUT)
	Boolean payForLogisticsCompany(@RequestBody SaleDeliveryOrderDTO saleDeliveryOrder);
	
}
