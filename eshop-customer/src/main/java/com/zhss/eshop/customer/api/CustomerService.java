package com.zhss.eshop.customer.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.customer.constant.ReturnGoodsWorksheetStatus;
import com.zhss.eshop.customer.dao.ReturnGoodsWorksheetDAO;
import com.zhss.eshop.customer.domain.ReturnGoodsWorksheetDO;

/**
 * 客服服务
 * @author zhonghuashishan
 *
 */
@RestController
public class CustomerService implements CustomerApi {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	/**
	 * 退货工单管理DAO组件
	 */
	@Autowired
	private ReturnGoodsWorksheetDAO returnGoodsWorksheetDAO;
	/**
	 * 订单服务
	 */
	@Autowired
	private OrderService orderService;
	
	/**
	 * 创建退货工单
	 * @param orderId 订单id
	 * @param orderNo 订单编号
	 * @param returnGoodsReason 退货原因
	 * @param returnGoodsRemark 退货备注
	 * @return 处理结果
	 */
	@Override
	public Boolean createReturnGoodsWorksheet(
			@RequestParam("orderId") Long orderId, 
			@RequestParam("orderNo") String orderNo, 
			@RequestParam("returnGoodsReason") Integer returnGoodsReason, 
			@RequestParam("returnGoodsRemark") String returnGoodsRemark) {
		try {
			ReturnGoodsWorksheetDO worksheet = new ReturnGoodsWorksheetDO();
			worksheet.setOrderInfoId(orderId); 
			worksheet.setOrderNo(orderNo); 
			worksheet.setReturnGoodsReason(returnGoodsReason); 
			worksheet.setReturnGoodsRemark(returnGoodsRemark); 
			worksheet.setStatus(ReturnGoodsWorksheetStatus.WAIT_FOR_APPROVE);  
			
			returnGoodsWorksheetDAO.save(worksheet); 
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 同步物流单号
	 * @param orderInfoId 订单id
	 * @param returnGoodsLogisticsCode 退货物流单号
	 * @return 处理结果
	 */
	@Override
	public Boolean syncReturnGoodsLogisticsCode(
			@RequestParam("orderInfoId") Long orderInfoId, 
			@RequestParam("returnGoodsLogisticsCode") String returnGoodsLogisticsCode) {
		try {
			ReturnGoodsWorksheetDO worksheet = returnGoodsWorksheetDAO.getByOrderInfoId(orderInfoId);
			worksheet.setReturnGoodsLogisticsCode(returnGoodsLogisticsCode); 
			returnGoodsWorksheetDAO.updateReturnGoodsLogisticsCode(worksheet); 
			
			worksheet.setStatus(ReturnGoodsWorksheetStatus.WAIT_FOR_RECEIVE_RETURN_GOODS);  
			returnGoodsWorksheetDAO.updateStatus(worksheet); 
			
			return true;
		} catch (Exception e) {
			logger.error("error", e);
			return false;
		}
	}
	
	/**
	 * 通知客服中心，“完成退货入库”事件发生了
	 * @param returnGoodsWorksheetId 退货工单id
	 * @return 处理结果
	 */
	@Override
	public Boolean informReturnGoodsInputFinishedEvent(
			@PathVariable("returnGoodsWorksheetId") Long returnGoodsWorksheetId) {
		try {
			ReturnGoodsWorksheetDO worksheet = returnGoodsWorksheetDAO
					.getById(returnGoodsWorksheetId);
			worksheet.setStatus(ReturnGoodsWorksheetStatus.FINISH_RETURN_GOODS_INPUT); 
			returnGoodsWorksheetDAO.updateStatus(worksheet); 
			
			orderService.informReturnGoodsInputOrderApprovedEvent(
					worksheet.getOrderInfoId());
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 通知客服中心，“完成退款”事件发生了
	 * @param returnGoodsWorkwheetId 退货工单id
	 * @return 处理结果
	 */
	@Override
	public Boolean informRefundFinishedEvent(
			@PathVariable("returnGoodsWorksheetId") Long returnGoodsWorkwheetId) {
		try {
			ReturnGoodsWorksheetDO worksheet = returnGoodsWorksheetDAO
					.getById(returnGoodsWorkwheetId);
			worksheet.setStatus(ReturnGoodsWorksheetStatus.FINISH_RETURN_GOODS_REFUND); 
			returnGoodsWorksheetDAO.updateStatus(worksheet); 
			
			orderService.informRefundFinishedEvent(worksheet.getOrderInfoId());
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
}
