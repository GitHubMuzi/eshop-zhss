package com.zhss.eshop.cart.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhss.eshop.cart.dao.ShoppingCartDAO;
import com.zhss.eshop.cart.dao.ShoppingCartItemDAO;
import com.zhss.eshop.cart.domain.ShoppingCartDO;
import com.zhss.eshop.cart.domain.ShoppingCartDTO;
import com.zhss.eshop.cart.domain.ShoppingCartItemDO;
import com.zhss.eshop.cart.domain.ShoppingCartItemDTO;
import com.zhss.eshop.cart.service.ShoppingCartService;
import com.zhss.eshop.common.util.DateProvider;

/**
 * 购物车管理模块的service组件
 * @author zhonghuashishan
 *
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	/**
	 * 购物车管理模块的DAO组件
	 */
	@Autowired
	private ShoppingCartDAO shoppingCartDAO;
	/**
	 * 购物车条目管理模块的DAO组件
	 */
	@Autowired
	private ShoppingCartItemDAO shoppingCartItemDAO;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 添加购物车商品条目
	 * @param userAccountId 用户账号id
	 * @param goodsSkuId 商品sku id
	 * @return 处理结果
	 */
	@Override
	public void addShoppingCartItem(Long userAccountId, Long goodsSkuId) throws Exception {
		Date currentTime = dateProvider.getCurrentTime();
		
		// 先查询一下用户的购物车
		ShoppingCartDO shoppingCartDO = shoppingCartDAO
				.getShoppingCartByUserAccountId(userAccountId);
		
		// 如果购物车不存在，则新增一个购物车
		if(shoppingCartDO == null) {
			shoppingCartDO = new ShoppingCartDO();
			shoppingCartDO.setUserAccountId(userAccountId); 
			shoppingCartDO.setGmtCreate(currentTime);
			shoppingCartDO.setGmtModified(currentTime);  
			
			shoppingCartDAO.saveShoppingCart(shoppingCartDO);
		}
		
		// 查询一下购物车中是否存在这个商品sku对应的条目
		ShoppingCartItemDO shoppingCartItemDO = shoppingCartItemDAO
				.getShoppingCartItemByGoodsSkuId(shoppingCartDO.getId(), goodsSkuId);
		
		// 如果没有这个商品条目，则新增一个商品条目
		if(shoppingCartItemDO == null) {
			shoppingCartItemDO = new ShoppingCartItemDO(); 
			shoppingCartItemDO.setShoppingCartId(shoppingCartDO.getId()); 
			shoppingCartItemDO.setGoodsSkuId(goodsSkuId); 
			shoppingCartItemDO.setPurchaseQuantity(1L); 
			shoppingCartItemDO.setGmtCreate(currentTime);  
			shoppingCartItemDO.setGmtModified(currentTime);  
			
			shoppingCartItemDAO.saveShoppingCartItem(shoppingCartItemDO);
		} 
		// 如果购物车中已经存在这个商品条目了，则对已有的商品条目的购买数量累加1
		else {
			shoppingCartItemDO.setPurchaseQuantity(
					shoppingCartItemDO.getPurchaseQuantity() + 1L); 
			shoppingCartItemDO.setGmtModified(currentTime);  
			shoppingCartItemDAO.updateShoppingCartItem(shoppingCartItemDO);
		}
	}
	
	/**
	 * 查看用户的购物车中的数据
	 * @param userAccountId 用户账号id
	 * @return 购物车DTO对象
	 */
	@Override
	public ShoppingCartDTO getShoppingCartDTOByUserAccountId(Long userAccountId) throws Exception {
		// 根据用户账号id查询一下购物车
		ShoppingCartDO shoppingCartDO = shoppingCartDAO
				.getShoppingCartByUserAccountId(userAccountId);
		if(shoppingCartDO == null) {
			return new ShoppingCartDTO();
		}
		ShoppingCartDTO shoppingCartDTO = shoppingCartDO.clone(ShoppingCartDTO.class);
		
		// 查询购物车条目
		List<ShoppingCartItemDO> shoppingCartItemDOs = shoppingCartItemDAO
				.listShoppingCartItemByCartId(shoppingCartDTO.getId());
		if(shoppingCartItemDOs == null || shoppingCartItemDOs.size() == 0) {
			return shoppingCartDTO;
		}
		
		List<ShoppingCartItemDTO> shoppingCartItemDTOs = new ArrayList<ShoppingCartItemDTO>();
		shoppingCartDTO.setShoppingCartItemDTOs(shoppingCartItemDTOs); 
		
		// 为购物车条目填充相关的数据
		for(ShoppingCartItemDO shoppingCartItemDO : shoppingCartItemDOs) {
			ShoppingCartItemDTO item = shoppingCartItemDO.clone(ShoppingCartItemDTO.class);
			shoppingCartItemDTOs.add(item); 
		}
		
		return shoppingCartDTO;
	}
	
}
