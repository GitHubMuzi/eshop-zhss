package com.zhss.eshop.promotion.api;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhss.eshop.promotion.domain.CouponDTO;
import com.zhss.eshop.promotion.domain.PromotionActivityDTO;

/**
 * 促销中心对外提供的接口
 * @author zhonghuashishan
 *
 */
@RequestMapping("/promotion")  
public interface PromotionApi {
	
	/**
	 * 根据商品id查询促销活动
	 * @param goodsId 商品id
	 * @return 促销活动
	 */
	@RequestMapping(value = "/listByGoodsId/{goodsId}", method = RequestMethod.GET)
	List<PromotionActivityDTO> listByGoodsId(@PathVariable("goodsId") Long goodsId);
	
	/**
	 * 根据id查询促销活动
	 * @param id 促销活动id
	 * @return 促销活动
	 */
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
	PromotionActivityDTO getById(@PathVariable("id") Long id);
	
	/**
	 * 查询用户当前可以使用的有效优惠券
	 * @param userAccountId 用户账号id
	 * @return 有效优惠券
	 */
	@RequestMapping(value = "/listValidByUserAccountId/{userAccountId}", method = RequestMethod.GET)
	List<CouponDTO> listValidByUserAccountId(@PathVariable("userAccountId" )Long userAccountId);
	
	/**
	 * 使用优惠券
	 * @param couponId 优惠券id
	 * @param userAccountId 用户账号id
	 * @return 处理结果
	 */
	@RequestMapping(value = "/useCoupon", method = RequestMethod.PUT)
	Boolean useCoupon(
			@RequestParam("couponId") Long couponId, 
			@RequestParam("userAccountId") Long userAccountId);
	
}
