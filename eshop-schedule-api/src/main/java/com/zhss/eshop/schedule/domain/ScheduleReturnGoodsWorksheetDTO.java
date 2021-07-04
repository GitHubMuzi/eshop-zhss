package com.zhss.eshop.schedule.domain;

import com.zhss.eshop.customer.domain.ReturnGoodsWorksheetDTO;
import com.zhss.eshop.order.domain.OrderInfoDTO;

/**
 * 调度服务的退货工单
 * @author zhonghuashishan
 *
 */
public class ScheduleReturnGoodsWorksheetDTO {

	private OrderInfoDTO order;
	private ReturnGoodsWorksheetDTO returnGoodsWorksheet;
	
	public OrderInfoDTO getOrder() {
		return order;
	}
	public void setOrder(OrderInfoDTO order) {
		this.order = order;
	}
	public ReturnGoodsWorksheetDTO getReturnGoodsWorksheet() {
		return returnGoodsWorksheet;
	}
	public void setReturnGoodsWorksheet(ReturnGoodsWorksheetDTO returnGoodsWorksheet) {
		this.returnGoodsWorksheet = returnGoodsWorksheet;
	}
	
}
