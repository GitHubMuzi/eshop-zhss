package com.zhss.eshop.membership.api;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhss.eshop.membership.domain.UserAccountDTO;

/**
 * 会员中心对外提供的接口
 * @author zhonghuashishan
 *
 */
@RequestMapping("/membership")  
public interface MembershipApi {

	/**
	 * 通知会员中心，“用户今日第一次登录”事件发生了
	 * @param userAccountId 用户账号ID
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informFirstLoginDailyEvent/{userAccountId}", method = RequestMethod.PUT)
	Boolean informFirstLoginDailyEvent(@PathVariable("userAccountId") Long userAccountId);
	
	/**
	 * 通知会员中心，“支付订单”事件发生了
	 * @param userAccountId 用户账号id
	 * @param totalOrderAmount 订单总金额
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informPayOrderEvent/{userAccountId}", method = RequestMethod.PUT)
	Boolean informPayOrderEvent(
			@PathVariable("userAccountId")Long userAccountId, 
			@RequestParam("totalOrderAmount") Double totalOrderAmount);
	
	/**
	 * 通知会员中心，“完成退货”事件发生了
	 * @param userAccountId 用户账号id
	 * @param totalOrderAmount 订单总金额
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informFinishReturnGoodsEvent/{userAccountId}", method = RequestMethod.PUT)
	Boolean informFinishReturnGoodsEvent(
			@PathVariable("userAccountId")Long userAccountId, 
			@RequestParam("totalOrderAmount") Double totalOrderAmount);
	
	/**
	 * 通知会员中心，“发表评论”事件发生了
	 * @param userAccountId 用户账号id
	 * @param showPictures 是否晒图
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informPublishCommentEvent/{userAccountId}", method = RequestMethod.PUT)
	Boolean informPublishCommentEvent(
			@PathVariable("userAccountId") Long userAccountId, 
			@RequestParam("showPictures") Boolean showPictures);
	
	/**
	 * 通知会员中心，“删除评论”事件发生了
	 * @param userAccountId 用户账号id
	 * @param showPictures 是否晒图
	 * @return 处理结果
	 */
	@RequestMapping(value = "/informRemoveCommentEvent/{userAccountId}", method = RequestMethod.PUT)
	Boolean informRemoveCommentEvent(
			@PathVariable("userAccountId") Long userAccountId, 
			@RequestParam("showPictures") Boolean showPictures);
	
	/**
	 * 查询所有的用户账户
	 * @return
	 */
	@RequestMapping(value = "/listAllUserAccounts", method = RequestMethod.GET)
	List<UserAccountDTO> listAllUserAccounts();
	
}
