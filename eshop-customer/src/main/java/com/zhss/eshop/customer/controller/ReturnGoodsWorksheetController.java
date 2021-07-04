package com.zhss.eshop.customer.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.common.util.ObjectUtils;
import com.zhss.eshop.customer.api.OrderService;
import com.zhss.eshop.customer.api.ScheduleService;
import com.zhss.eshop.customer.constant.ReturnGoodsWorksheetApproveResult;
import com.zhss.eshop.customer.domain.ReturnGoodsWorksheetDTO;
import com.zhss.eshop.customer.domain.ReturnGoodsWorksheetQuery;
import com.zhss.eshop.customer.domain.ReturnGoodsWorksheetVO;
import com.zhss.eshop.customer.service.ReturnGoodsWorksheetService;
import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.schedule.domain.ScheduleReturnGoodsWorksheetDTO;

/**
 * 退货工单管理controller组件
 * @author zhonghuashishan
 *
 */
@RestController
@RequestMapping("/customer/return/goods/worksheet")  
public class ReturnGoodsWorksheetController {
	
	private static final Logger logger = LoggerFactory.getLogger(
			ReturnGoodsWorksheetController.class);

	/**
	 * 退货工单管理service组件
	 */
	@Autowired
	private ReturnGoodsWorksheetService returnGoodsWorksheetService;
	/**
	 * 订单服务
	 */
	@Autowired
	private OrderService orderService;
	/**
	 * 调度中心接口
	 */
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 分页查询退货工单
	 * @param query 查询条件
	 * @return 退货工单
	 */
	@GetMapping("/") 
	public List<ReturnGoodsWorksheetVO> listByPage(ReturnGoodsWorksheetQuery query) {
		try {
			return ObjectUtils.convertList(
					returnGoodsWorksheetService.listByPage(query), 
					ReturnGoodsWorksheetVO.class);
		} catch (Exception e) {
			logger.error("error", e); 
			return new ArrayList<ReturnGoodsWorksheetVO>();
		}
	}
	
	/**
	 * 根据id查询退货工单
	 * @param id 退货工单id
	 * @return 退货工单
	 */
	@GetMapping("/{id}")  
	public ReturnGoodsWorksheetVO getById(@PathVariable("id") Long id) {
		try {
			return returnGoodsWorksheetService.getById(id)
					.clone(ReturnGoodsWorksheetVO.class);
		} catch (Exception e) {
			logger.error("error", e); 
			return new ReturnGoodsWorksheetVO();
		}
	}
	
	/**
	 * 审核退货工单
	 * @param id 退货工单id
	 * @param approveResult 审核结果
	 * @throws Exception
	 */
	@PutMapping("/approve/{id}")
	public Boolean approve(@PathVariable("id") Long id, Integer approveResult) {
		try {
			returnGoodsWorksheetService.approve(id, approveResult); 
			ReturnGoodsWorksheetDTO worksheet = returnGoodsWorksheetService.getById(id);
			
			if(ReturnGoodsWorksheetApproveResult.PASSED.equals(approveResult)) {
				orderService.informReturnGoodsWorsheetApprovedEvent(worksheet.getOrderInfoId());
			} else if(ReturnGoodsWorksheetApproveResult.REJECTED.equals(approveResult)) {
				orderService.informReturnGoodsWorksheetRejectedEvent(worksheet.getOrderInfoId()); 
			}
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 确认退货工单已经收到了退货商品
	 * @param id 退货工单id
	 * @throws Exception
	 */
	@PutMapping("/confirmReceivedReturnGoods/{id}")
	public Boolean confirmReceivedReturnGoods(@PathVariable("id") Long id) {
		try {
			// 更新退货工单状态为：退货商品待入库
			returnGoodsWorksheetService.confirmReceivedReturnGoods(id);
			// 查询退货工单
			ReturnGoodsWorksheetDTO worksheet = returnGoodsWorksheetService.getById(id);
			
			// 通知订单服务：退货商品已收货
			orderService.informReturnGoodsReceivedEvent(worksheet.getOrderInfoId());
			
			// 从订单中心查询订单
			OrderInfoDTO order = orderService.getOrderById(worksheet.getOrderInfoId());
			// 将订单和退货工单封装为一个domain类
			ScheduleReturnGoodsWorksheetDTO scheduleReturnGoodsWorksheet = 
					new ScheduleReturnGoodsWorksheetDTO();
			scheduleReturnGoodsWorksheet.setOrder(order); 
			scheduleReturnGoodsWorksheet.setReturnGoodsWorksheet(worksheet); 
			// 调用调度服务的接口：调度退货入库
			scheduleService.scheduleReturnGoodsInput(scheduleReturnGoodsWorksheet); 
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
}
