package com.zhss.eshop.schedule.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zhss.eshop.order.domain.OrderItemDTO;
import com.zhss.eshop.schedule.constant.TccType;
import com.zhss.eshop.schedule.dao.ScheduleGoodsAllocationStockDAO;
import com.zhss.eshop.schedule.dao.ScheduleGoodsAllocationStockDetailDAO;
import com.zhss.eshop.schedule.dao.ScheduleGoodsStockDAO;
import com.zhss.eshop.schedule.domain.SaleDeliveryScheduleResult;
import com.zhss.eshop.schedule.domain.ScheduleGoodsAllocationStockDO;
import com.zhss.eshop.schedule.domain.ScheduleGoodsAllocationStockDetailDO;
import com.zhss.eshop.schedule.domain.ScheduleGoodsStockDO;
import com.zhss.eshop.schedule.domain.ScheduleOrderPickingItemDTO;
import com.zhss.eshop.schedule.domain.ScheduleOrderSendOutDetailDTO;

/**
 * 支付订单库存更新组件
 * @author zhonghuashishan
 *
 */
@Component
@Scope("prototype")  
@Transactional(rollbackFor = Exception.class)
public class PayOrderScheduleStockUpdater extends AbstractScheduleStockUpdater { 

	/**
	 * 商品库存管理的DAO组件
	 */
	@Autowired
	private ScheduleGoodsStockDAO goodsStockDAO;
	/**
	 * 货位库存管理的DAO组件
	 */
	@Autowired
	private ScheduleGoodsAllocationStockDAO goodsAllocationStockDAO;
	/**
	 * 货位库存明细管理的DAO组件
	 */
	@Autowired
	private ScheduleGoodsAllocationStockDetailDAO stockDetailDAO;
	
	/**
	 * 销售出库调度结果
	 */
	private SaleDeliveryScheduleResult scheduleResult;
	
	private Integer tccType;
	
	/**
	 * 更新商品库存
	 */
	@Override
	protected void updateGoodsStock() throws Exception {
		OrderItemDTO orderItem = scheduleResult.getOrderItem();
		ScheduleGoodsStockDO goodsStock = goodsStockDAO.getBySkuId(orderItem.getGoodsSkuId());
		if(TccType.TRY.equals(tccType)) {
			goodsStock.setLockedStockQuantity(goodsStock.getLockedStockQuantity()  
					- orderItem.getPurchaseQuantity()); 
		}
		if(TccType.CONFIRM.equals(tccType)) {
			goodsStock.setOutputStockQuantity(goodsStock.getOutputStockQuantity()
					+ orderItem.getPurchaseQuantity()); 
		}
		if(TccType.CANCEL.equals(tccType)) {
			goodsStock.setLockedStockQuantity(goodsStock.getLockedStockQuantity()  
					+ orderItem.getPurchaseQuantity()); 
		}
		goodsStockDAO.update(goodsStock); 
	}
	
	/**
	 * 更新货位库存
	 */
	@Override
	protected void updateGoodsAllocationStock() throws Exception {
		List<ScheduleOrderPickingItemDTO> pickingItems = scheduleResult.getPickingItems();
		for(ScheduleOrderPickingItemDTO pickingItem : pickingItems) {
			ScheduleGoodsAllocationStockDO goodsAllocationStock = goodsAllocationStockDAO.getBySkuId(
					pickingItem.getGoodsAllocationId(), pickingItem.getGoodsSkuId());   
			if(TccType.TRY.equals(tccType)) {
				goodsAllocationStock.setLockedStockQuantity(goodsAllocationStock.getLockedStockQuantity()
						- pickingItem.getPickingCount()); 
			}
			if(TccType.CONFIRM.equals(tccType)) {
				goodsAllocationStock.setOutputStockQuantity(goodsAllocationStock.getOutputStockQuantity()
						+ pickingItem.getPickingCount());  
			}
			if(TccType.TRY.equals(tccType)) {
				goodsAllocationStock.setLockedStockQuantity(goodsAllocationStock.getLockedStockQuantity()
						+ pickingItem.getPickingCount()); 
			}
			goodsAllocationStockDAO.update(goodsAllocationStock); 
		}
	}
	
	/**
	 * 更新货位库存明细
	 */
	@Override
	protected void updateGoodsAllocationStockDetail() throws Exception {
		List<ScheduleOrderSendOutDetailDTO> sendOutDetails = scheduleResult.getSendOutDetails();
		for(ScheduleOrderSendOutDetailDTO sendOutDetail : sendOutDetails) {
			ScheduleGoodsAllocationStockDetailDO stockDetail = stockDetailDAO.getById(
					sendOutDetail.getGoodsAllocationStockDetailId());
			if(TccType.TRY.equals(tccType)) {
				stockDetail.setLockedStockQuantity(stockDetail.getLockedStockQuantity()
						- sendOutDetail.getSendOutCount());
			}
			if(TccType.CANCEL.equals(tccType)) {
				stockDetail.setLockedStockQuantity(stockDetail.getLockedStockQuantity()
						+ sendOutDetail.getSendOutCount());
			}
			stockDetailDAO.update(stockDetail); 
			
			// 上架数量
			// 当前数量
			// 锁定数量
			// 上架数量 - 当前数量 - 锁定数量 = 这个库存明细已经被发掉的库存数量
		}
	}
	
	/**
	 * 设置库存更新组件需要的参数
	 */
	@Override
	public void setParameter(Object parameter) {
		this.scheduleResult = (SaleDeliveryScheduleResult) parameter;
	}

	public void setTccType(Integer tccType) {
		this.tccType = tccType;
	}
	
}
