package com.zhss.eshop.inventory.stock;

import java.util.List;
import java.util.Map;

import com.zhss.eshop.inventory.constant.TccType;
import com.zhss.eshop.inventory.dao.GoodsStockDAO;
import com.zhss.eshop.common.util.DateProvider;
import com.zhss.eshop.inventory.domain.GoodsStockDO;
import com.zhss.eshop.order.domain.OrderItemDTO;

/**
 * 提交订单库存更新组件
 * @author zhonghuashishan
 *
 */
public class SubmitOrderStockUpdater extends AbstractStockUpdater {

	/**
	 * 订单条目DTO对象集合
	 */
	private Map<Long, OrderItemDTO> orderItemDTOMap;
	/**
	 * tcc操作类型
	 */
	private Integer tccType;
	
	/**
	 * 构造函数
	 * @param goodsStockDOs 商品库存DO对象集合
	 * @param goodsStockDAO 商品库存管理模块DAO组件 
	 * @param dateProvider 日期辅助组件
	 */
	public SubmitOrderStockUpdater(
			List<GoodsStockDO> goodsStockDOs, 
			GoodsStockDAO goodsStockDAO,
			DateProvider dateProvider,
			Map<Long, OrderItemDTO> orderItemDTOMap) {  
		super(goodsStockDOs, goodsStockDAO, dateProvider);
		this.orderItemDTOMap = orderItemDTOMap;
	}

	/**
	 * 更新销售库存
	 */
	@Override
	protected void updateSaleStockQuantity() throws Exception {
		for(GoodsStockDO goodsStockDO : goodsStockDOs) {
			OrderItemDTO orderItemDTO = orderItemDTOMap.get(goodsStockDO.getGoodsSkuId());
			if(TccType.TRY.equals(tccType)) {
				goodsStockDO.setSaleStockQuantity(goodsStockDO.getSaleStockQuantity() 
						- orderItemDTO.getPurchaseQuantity());  
			}
			if(TccType.CANCEL.equals(tccType)) {
				goodsStockDO.setSaleStockQuantity(goodsStockDO.getSaleStockQuantity() 
						+ orderItemDTO.getPurchaseQuantity());  
			}
		}
	}
	
	/**
	 * 更新锁定库存
	 */
	@Override
	protected void updateLockedStockQuantity() throws Exception {
		for(GoodsStockDO goodsStockDO : goodsStockDOs) {
			OrderItemDTO orderItemDTO = orderItemDTOMap.get(goodsStockDO.getGoodsSkuId());
			if(TccType.CONFIRM.equals(tccType)) {
				goodsStockDO.setLockedStockQuantity(goodsStockDO.getLockedStockQuantity() 
						+ orderItemDTO.getPurchaseQuantity()); 
			}
		}
	}
	
	/**
	 * 更新已销售库存
	 */
	@Override
	protected void updateSaledStockQuantity() throws Exception {
		
	}

	public void setTccType(Integer tccType) {
		this.tccType = tccType;
	}
	
}
