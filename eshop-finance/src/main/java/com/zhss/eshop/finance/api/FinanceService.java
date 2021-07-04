package com.zhss.eshop.finance.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.finance.domain.PurchaseSettlementOrderDTO;
import com.zhss.eshop.finance.domain.PurchaseSettlementOrderItemDTO;
import com.zhss.eshop.finance.service.PurchaseSettlementOrderService;
import com.zhss.eshop.wms.domain.PurchaseInputOrderDTO;
import com.zhss.eshop.wms.domain.PurchaseInputOrderItemDTO;
import com.zhss.eshop.wms.domain.SaleDeliveryOrderDTO;

/**
 * 财务中心接口
 * @author zhonghuashishan
 *
 */
@RestController
public class FinanceService implements FinanceApi {
	
	private static final Logger logger = LoggerFactory.getLogger(FinanceApi.class);

	/**
	 * 采购结算单service组件
	 */
	@Autowired
	private PurchaseSettlementOrderService purchaseSettlementOrderService;
	/**
	 * wms服务
	 */
	@Autowired
	private WmsService wmsService;
	
	/**
	 * 创建采购结算单
	 * @param purchaseInputOrderDTO 采购入库单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean createPurchaseSettlementOrder(
			@RequestBody PurchaseInputOrderDTO purchaseInputOrder) {
		try {
			// 将采购入库单的数据拷贝到采购结算中去
			PurchaseSettlementOrderDTO purchaseSettlementOrder = 
					purchaseInputOrder.clone(PurchaseSettlementOrderDTO.class);
			purchaseSettlementOrder.setId(null); 
			purchaseSettlementOrder.setStatus(null); 
			purchaseSettlementOrder.setGmtCreate(null); 
			purchaseSettlementOrder.setGmtModified(null); 
			purchaseSettlementOrder.setPurchaseInputOrderId(purchaseInputOrder.getId()); 
			
			// 将采购入库单条目的数据拷贝到采购结算单条目中去
			List<PurchaseSettlementOrderItemDTO> items = new ArrayList<PurchaseSettlementOrderItemDTO>();
			
			for(PurchaseInputOrderItemDTO purchaseInputOrderItem : purchaseInputOrder.getItems()) {
				PurchaseSettlementOrderItemDTO item = purchaseInputOrderItem.clone(PurchaseSettlementOrderItemDTO.class);
				item.setId(null); 
				item.setGmtCreate(null);
				item.setGmtModified(null);
				
				items.add(item);
			}
			
			purchaseSettlementOrder.setItems(items); 
			
			// 完成采购结算单的新增
			purchaseSettlementOrderService.save(purchaseSettlementOrder); 
			
			// 通知wms中心采购结算单创建了
			wmsService.informCreatePurchaseSettlementOrderEvent(
					purchaseSettlementOrder.getPurchaseInputOrderId());
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 给物流公司打款
	 * @param saleDeliveryOrderDTO 销售出库单
	 * @return 处理结果
	 */
	@Override
	public Boolean payForLogisticsCompany(
			@RequestBody SaleDeliveryOrderDTO saleDeliveryOrder) {
		// 就是将销售出库单中的运费取出来
		// 将运费对应的款项打到物流公司的账号里面去
		return true;
	}
	
}
