package com.zhss.eshop.commodity.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.commodity.domain.GoodsDTO;
import com.zhss.eshop.commodity.domain.GoodsSkuDTO;
import com.zhss.eshop.commodity.service.GoodsService;
import com.zhss.eshop.commodity.service.GoodsSkuService;

/**
 * 商品服务
 * @author zhonghuashishan
 *
 */
@RestController
public class CommodityService implements CommodityApi {
	
	private static final Logger logger = LoggerFactory.getLogger(CommodityService.class);
	
	/**
	 * 商品sku管理service组件
	 */
	@Autowired
	private GoodsSkuService goodsSkuService;
	/**
	 * 商品管理service组件
	 */
	@Autowired
	private GoodsService goodsService;
	/**
	 * 库存中心接口
	 */
	@Autowired
	private InventoryService inventoryService;
	
	/**
	 * 根据id查询商品sku
	 * @param goodsSkuId 商品sku id
	 * @return 商品sku DTO
	 */
	@Override
	public GoodsSkuDTO getGoodsSkuById(@PathVariable("goodsSkuId") Long goodsSkuId) {
		try {
			GoodsSkuDTO goodsSku = goodsSkuService.getById(goodsSkuId);
			goodsSku.setSaleStockQuantity(
					inventoryService.getSaleStockQuantity(goodsSku.getId()));  
			return goodsSku;
		} catch (Exception e) {
			logger.error("error", e); 
			return null;
		}
	}
	
	/**
	 * 根据id查询商品
	 * @param goodsId 商品id
	 * @return 商品
	 */
	@Override
	public GoodsDTO getGoodsById(@PathVariable("goodsId") Long goodsId) {
		try {
			return goodsService.getById(goodsId); 
		} catch (Exception e) {
			logger.error("error", e); 
			return null;
		}
	}
	
}
