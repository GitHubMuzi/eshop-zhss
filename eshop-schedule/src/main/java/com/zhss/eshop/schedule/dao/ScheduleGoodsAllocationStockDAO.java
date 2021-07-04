package com.zhss.eshop.schedule.dao;

import com.zhss.eshop.schedule.domain.ScheduleGoodsAllocationStockDO;

/**
 * 货位库存管理模块的DAO组件接口
 * @author zhonghuashishan
 *
 */
public interface ScheduleGoodsAllocationStockDAO {

	/**
	 * 根据商品sku id查询货位库存
	 * @param goodsAllocationId 货位id
	 * @param goodsSkuId 商品sku id
	 * @return 货位库存
	 * @throws Exception
	 */
	ScheduleGoodsAllocationStockDO getBySkuId(
			Long goodsAllocationId, Long goodsSkuId) throws Exception;
	
	/**
	 * 新增货位库存
	 * @param goodsAllocationStockDO 货位库存DO对象
	 * @throws Exception
	 */
	void save(ScheduleGoodsAllocationStockDO goodsAllocationStock) throws Exception;
	
	/**
	 * 更新货位库存
	 * @param goodsAllocationStockDO 货位库存DO对象
	 * @throws Exception
	 */
	void update(ScheduleGoodsAllocationStockDO goodsAllocationStock) throws Exception;
	
}
