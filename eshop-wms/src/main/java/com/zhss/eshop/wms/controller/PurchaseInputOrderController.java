package com.zhss.eshop.wms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.common.util.CloneDirection;
import com.zhss.eshop.common.util.ObjectUtils;
import com.zhss.eshop.wms.constant.PurchaseInputOrderApproveResult;
import com.zhss.eshop.wms.constant.PurchaseInputOrderStatus;
import com.zhss.eshop.wms.domain.PurchaseInputOrderDTO;
import com.zhss.eshop.wms.domain.PurchaseInputOrderPutOnItemDTO;
import com.zhss.eshop.wms.domain.PurchaseInputOrderPutOnItemVO;
import com.zhss.eshop.wms.domain.PurchaseInputOrderQuery;
import com.zhss.eshop.wms.domain.PurchaseInputOrderVO;
import com.zhss.eshop.wms.service.PurchaseInputOrderService;
import com.zhss.eshop.wms.service.impl.PurchaseInputOrderHandler;
import com.zhss.eshop.wms.service.impl.PurchaseInputOrderHandlerFactory;

/**
 * 采购入库单管理controller组件
 * @author zhonghuashishan
 *
 */
@RestController
@RequestMapping("/wms/purchaseInputOrder")   
public class PurchaseInputOrderController {

	private static final Logger logger = LoggerFactory.getLogger(
			PurchaseInputOrderController.class);
	
	/**
	 * 采购入库单管理service组件
	 */
	@Autowired
	private PurchaseInputOrderService purchaseInputOrderService;
	/**
	 * 采购入库单handler工厂
	 */
	@Autowired
	private PurchaseInputOrderHandlerFactory handlerFactory;
	
	/**
	 * 分页查询采购入库单
	 * @return 采购入库单
	 * @throws Exception
	 */
	@GetMapping("/")  
	public List<PurchaseInputOrderVO> listByPage(PurchaseInputOrderQuery query) {
		try { 
			return ObjectUtils.convertList(
					purchaseInputOrderService.listByPage(query), 
					PurchaseInputOrderVO.class); 
		} catch (Exception e) {
			logger.error("error", e); 
			return null;
		}
	}
	
	/**
	 * 根据id查询采购入库单
	 * @return 采购入库单
	 * @throws Exception
	 */
	@GetMapping("/{id}")
	public PurchaseInputOrderVO getById(@PathVariable("id")  Long id) {  
		try {
			return purchaseInputOrderService.getById(id).clone(
					PurchaseInputOrderVO.class, CloneDirection.OPPOSITE); 
		} catch (Exception e) {
			logger.error("error", e); 
			return null;
		}
	}
	
	/**
	 * 更新采购入库单
	 * @param purchaseInputOrder 采购入库单
	 * @throws Exception
	 */
	@PutMapping("/{id}")  
	public Boolean update(@RequestBody PurchaseInputOrderVO purchaseInputOrder) {
		try {
			purchaseInputOrderService.update(purchaseInputOrder.clone(
					PurchaseInputOrderDTO.class, CloneDirection.FORWARD));  
			return true;
		} catch (Exception e) {
			logger.error("error", e);
			return false;
		}
	}
	
	/**
	 * 批量新增采购入库单的上架条目
	 * @param putOnItems 上架条目
	 * @throws Exception
	 */
	@PutMapping("/putOnShelves/{id}")    
	public Boolean batchSavePutOnItems(@RequestBody List<PurchaseInputOrderPutOnItemVO> putOnItems) {
		try {
			purchaseInputOrderService.batchSavePutOnItems(ObjectUtils.convertList(
					putOnItems, PurchaseInputOrderPutOnItemDTO.class));   
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 对采购入库单提交审核
	 * @param id 采购入库单id
	 * @throws Exception 
	 */
	@PutMapping("/submitApprove/{id}") 
	public Boolean submitApprove(@PathVariable("id") Long id) throws Exception {
		try {
			purchaseInputOrderService.submitApprove(id);
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 对采购入库单进行审核
	 * @param id 采购入库单id
	 * @throws Exception 
	 */
	@PutMapping("/approve/{id}") 
	public Boolean submitApprove(@PathVariable("id") Long id, Integer approveResult) throws Exception {
		try {
			// 只有处于待审核的时候，其实才能去执行后面的这套逻辑
			PurchaseInputOrderDTO purchaseInputOrder = purchaseInputOrderService.getById(id);
			if(!PurchaseInputOrderStatus.WAIT_FOR_APPROVE.equals(purchaseInputOrder.getStatus())) {
				return false;
			}
			
			purchaseInputOrderService.approve(id, approveResult); 
			
			if(PurchaseInputOrderApproveResult.PASSED.equals(approveResult)) {
				PurchaseInputOrderHandler handlerChain = handlerFactory.getHandlerChain();
				handlerChain.execute(purchaseInputOrder);
			}
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
}
