package com.zhss.eshop.wms.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhss.eshop.wms.domain.WmsGoodsAllocationStockDO;

/**
 * 商品sku库存管理模块的mapper组件
 * @author zhonghuashishan
 *
 */
@Mapper
public interface WmsGoodsAllocationStockMapper {

	/**
	 * 根据商品sku id查询商品库存
	 * @param goodsAllocationId 货位id
	 * @param goodsSkuId 商品sku id
	 * @return 商品库存
	 */
	@Select("SELECT "
				+ "id,"
				+ "goods_allocation_id,"
				+ "goods_sku_id,"
				+ "available_stock_quantity,"
				+ "locked_stock_quantity,"
				+ "output_stock_quantity,"
				+ "gmt_create,"
				+ "gmt_modified "
			+ "FROM wms_goods_allocation_stock "
			+ "WHERE goods_sku_id=#{goodsSkuId} "
			+ "AND goods_allocation_id=#{goodsAllocationId}")  
	@Results({
		@Result(column = "id", property = "id", id = true),
		@Result(column = "goods_sku_id", property = "goodsSkuId"),
		@Result(column = "goods_allocation_id", property = "goodsAllocationId"),
		@Result(column = "available_stock_quantity", property = "availableStockQuantity"),
		@Result(column = "locked_stock_quantity", property = "lockedStockQuantity"),
		@Result(column = "output_stock_quantity", property = "outputStockQuantity"),
		@Result(column = "gmt_create", property = "gmtCreate"),
		@Result(column = "gmt_modified", property = "gmtModified")
	})
	WmsGoodsAllocationStockDO getBySkuId(
			@Param("goodsAllocationId") Long goodsAllocationId,
			@Param("goodsSkuId") Long goodsSkuId);
	
	/**
	 * 新增商品库存
	 * @param goodsAllocationStock 商品库存DO对象
	 */
	@Insert("INSERT INTO wms_goods_allocation_stock ("
				+ "goods_allocation_id,"
				+ "goods_sku_id,"
				+ "available_stock_quantity,"
				+ "locked_stock_quantity,"
				+ "output_stock_quantity,"
				+ "gmt_create,"
				+ "gmt_modified"
			+ ") VALUES("
				+ "#{goodsAllocationId},"
				+ "#{goodsSkuId},"
 				+ "#{availableStockQuantity},"
				+ "#{lockedStockQuantity},"
				+ "#{outputStockQuantity},"
				+ "#{gmtCreate},"
				+ "#{gmtModified}"
			+ ")")  
	@Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true) 
	void save(WmsGoodsAllocationStockDO goodsAllocationStock);
	
	/**
	 * 更新商品库存
	 * @param goodsAllocationStock 商品库存DO对象
	 */
	@Update("UPDATE wms_goods_allocation_stock SET "
				+ "available_stock_quantity=#{availableStockQuantity},"
				+ "locked_stock_quantity=#{lockedStockQuantity},"
				+ "output_stock_quantity=#{outputStockQuantity},"
				+ "gmt_modified=#{gmtModified} "
			+ "WHERE id=#{id}") 
	void update(WmsGoodsAllocationStockDO goodsAllocationStock);
	
}
