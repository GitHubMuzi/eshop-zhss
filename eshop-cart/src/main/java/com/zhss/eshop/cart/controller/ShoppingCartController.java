package com.zhss.eshop.cart.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.cart.api.CommodityService;
import com.zhss.eshop.cart.api.InventoryService;
import com.zhss.eshop.cart.api.PromotionService;
import com.zhss.eshop.cart.domain.AddShoppingCartItemQuery;
import com.zhss.eshop.cart.domain.ShoppingCartDTO;
import com.zhss.eshop.cart.domain.ShoppingCartItemDTO;
import com.zhss.eshop.cart.domain.ShoppingCartItemVO;
import com.zhss.eshop.cart.domain.ShoppingCartVO;
import com.zhss.eshop.cart.service.ShoppingCartService;
import com.zhss.eshop.commodity.domain.GoodsSkuDTO;
import com.zhss.eshop.promotion.domain.PromotionActivityDTO;

/**
 * 购物车管理模块的controller组件
 * @author zhonghuashishan
 *
 */
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

	/**
	 * 购物车管理模块的service组件
	 */
	@Autowired
	private ShoppingCartService shoppingCartService;
	/**
	 * 商品服务
	 */
	@Autowired
	private CommodityService commodityService;
	/**
	 * 库存服务
	 */
	@Autowired
	private InventoryService inventoryService;
	/**
	 * 促销服务
	 */
	@Autowired
	private PromotionService promotionService;
	
	/**
	 * 添加购物车商品条目
	 * @param userAccountId 用户账号id
	 * @param goodsSkuId 商品sku id
	 * @return 处理结果
	 */
	@PostMapping("/item/add") 
	public Boolean addShoppingCartItem(@RequestBody AddShoppingCartItemQuery query) {
		try {
			shoppingCartService.addShoppingCartItem(query.getUserAccountId(), 
					query.getGoodsSkuId()); 
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 查看购物车
	 * @param userAccountId 用户账号id
	 * @return
	 */
	@GetMapping("/{userAccountId}")   
	public ShoppingCartVO getShoppingCartVO(
			@PathVariable("userAccountId") Long userAccountId) {
		try {
			ShoppingCartDTO shoppingCartDTO = shoppingCartService
					.getShoppingCartDTOByUserAccountId(userAccountId);
			
			ShoppingCartVO shoppingCartVO = shoppingCartDTO.clone(ShoppingCartVO.class);
			
			List<ShoppingCartItemVO> shoppingCartItemVOs = new ArrayList<ShoppingCartItemVO>();
			shoppingCartVO.setShoppingCartItemVOs(shoppingCartItemVOs); 
			
			for(ShoppingCartItemDTO shoppingCartItemDTO : shoppingCartDTO.getShoppingCartItemDTOs()) {
				setGoodsRelatedData(shoppingCartItemDTO);
				setStockRelatedData(shoppingCartItemDTO);
				setPromotionRelatedData(shoppingCartItemDTO); 
				shoppingCartItemVOs.add(shoppingCartItemDTO.clone(ShoppingCartItemVO.class));  
			}
			
			return shoppingCartVO;
		} catch (Exception e) {
			logger.error("error", e);  
			return new ShoppingCartVO();
		}
	}
	
	/**
	 * 给购物车条目设置商品相关的数据
	 * @throws Exception
	 */
	private void setGoodsRelatedData(ShoppingCartItemDTO item) throws Exception {
		GoodsSkuDTO goodsSkuDTO = commodityService.getGoodsSkuById(
				item.getGoodsSkuId());
		
		item.setGoodsId(goodsSkuDTO.getGoodsId());  
		item.setGoodsHeight(goodsSkuDTO.getGoodsHeight()); 
		item.setGoodsLength(goodsSkuDTO.getGoodsLength()); 
		item.setGoodsName(goodsSkuDTO.getGoodsName()); 
		item.setGoodsSkuCode(goodsSkuDTO.getGoodsSkuCode()); 
		item.setGoodsWidth(goodsSkuDTO.getGoodsWidth()); 
		item.setGrossWeight(goodsSkuDTO.getGrossWeight()); 
		item.setSalePrice(goodsSkuDTO.getSalePrice()); 
		item.setSaleProperties(goodsSkuDTO.getSaleProperties());
	}
	
	/**
	 * 给购物车条目设置库存相关的数据 
	 * @param item 购物车条目
	 * @throws Exception
	 */
	private void setStockRelatedData(ShoppingCartItemDTO item) throws Exception {
		Long saleStockQuantity = inventoryService.getSaleStockQuantity(
				item.getGoodsSkuId());
		item.setSaleStockQuantity(saleStockQuantity);
	}
	
	/**
	 * 给购物车条目设置促销相关的数据
	 * @param item 购物车条目
	 * @throws Exception
	 */
	private void setPromotionRelatedData(ShoppingCartItemDTO item) throws Exception {
		List<PromotionActivityDTO> promotionActivityDTOs = promotionService
				.listByGoodsId(item.getGoodsId());
		item.setPromotionActivityDTOs(promotionActivityDTOs); 
	}
	
}
