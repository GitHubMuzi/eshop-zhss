package com.zhss.eshop.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhss.eshop.common.util.ObjectUtils;
import com.zhss.eshop.customer.constant.ReturnGoodsWorksheetApproveResult;
import com.zhss.eshop.customer.constant.ReturnGoodsWorksheetStatus;
import com.zhss.eshop.customer.dao.ReturnGoodsWorksheetDAO;
import com.zhss.eshop.customer.domain.ReturnGoodsWorksheetDO;
import com.zhss.eshop.customer.domain.ReturnGoodsWorksheetDTO;
import com.zhss.eshop.customer.domain.ReturnGoodsWorksheetQuery;
import com.zhss.eshop.customer.service.ReturnGoodsWorksheetService;

/**
 * 退货工单管理service组件
 * @author zhonghuashishan
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReturnGoodsWorksheetServiceImpl implements ReturnGoodsWorksheetService {

	/**
	 * 退货工单管理DAO组件
	 */
	@Autowired
	private ReturnGoodsWorksheetDAO returnGoodsWorksheetDAO;
	
	/**
	 * 分页查询退货工单
	 * @param query 查询条件
	 * @return 退货工单
	 */
	@Override
	public List<ReturnGoodsWorksheetDTO> listByPage(
			ReturnGoodsWorksheetQuery query) throws Exception {
		return ObjectUtils.convertList(returnGoodsWorksheetDAO.listByPage(query), 
				ReturnGoodsWorksheetDTO.class);
	}
	
	/**
	 * 根据id查询退货工单
	 * @param id 退货工单id
	 * @return 退货工单
	 */
	@Override
	public ReturnGoodsWorksheetDTO getById(Long id) throws Exception {
		return returnGoodsWorksheetDAO.getById(id).clone(ReturnGoodsWorksheetDTO.class);
	}
	
	/**
	 * 审核退货工单
	 * @param id 退货工单id
	 * @param approveResult 审核结果
	 * @throws Exception
	 */
	@Override
	public void approve(Long id, Integer approveResult) throws Exception {
		ReturnGoodsWorksheetDO worksheet = returnGoodsWorksheetDAO.getById(id);
		if(ReturnGoodsWorksheetApproveResult.PASSED.equals(approveResult)) {
			worksheet.setStatus(ReturnGoodsWorksheetStatus.WAIT_FOR_SEND_OUT_RETURN_GOODS);
		} else if(ReturnGoodsWorksheetApproveResult.REJECTED.equals(approveResult)) {
			worksheet.setStatus(ReturnGoodsWorksheetStatus.APPROVE_REJECTED);  
		}
		returnGoodsWorksheetDAO.updateStatus(worksheet); 
	}
	
	/**
	 * 确认退货工单已经收到了退货商品
	 * @param id 退货工单id
	 * @throws Exception
	 */
	@Override
	public void confirmReceivedReturnGoods(Long id) throws Exception {
		ReturnGoodsWorksheetDO worksheet = returnGoodsWorksheetDAO.getById(id);
		worksheet.setStatus(ReturnGoodsWorksheetStatus.WAIT_FOR_RETURN_GOODS_INPUT);  
		returnGoodsWorksheetDAO.updateStatus(worksheet); 
	}
	
}
