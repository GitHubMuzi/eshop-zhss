package com.zhss.eshop.schedule.util;

import java.util.List;

import com.zhss.eshop.common.util.ObjectUtils;
import com.zhss.eshop.schedule.domain.SaleDeliveryScheduleResult;
import com.zhss.eshop.wms.domain.WmsSaleDeliveryScheduleResult;
import com.zhss.eshop.wms.domain.WmsScheduleOrderPickingItemDTO;
import com.zhss.eshop.wms.domain.WmsScheduleOrderSendOutDetailDTO;

public class BeanConvertUtils {

	/**
	 * 转换销售出库调度结果
	 * @param saleDeliveryScheduleResult
	 * @return
	 * @throws Exception
	 */
	public static WmsSaleDeliveryScheduleResult convertSaleDeliveryScheduleResult(
			SaleDeliveryScheduleResult result) throws Exception {
		WmsSaleDeliveryScheduleResult targetResult = 
				result.clone(WmsSaleDeliveryScheduleResult.class);
		
		List<WmsScheduleOrderPickingItemDTO> targetPickingItems = 
				ObjectUtils.convertList(result.getPickingItems(), WmsScheduleOrderPickingItemDTO.class);
		targetResult.setPickingItems(targetPickingItems); 
		
		List<WmsScheduleOrderSendOutDetailDTO> targetSendOutDetails = 
				ObjectUtils.convertList(result.getSendOutDetails(), WmsScheduleOrderSendOutDetailDTO.class);
		targetResult.setSendOutDetails(targetSendOutDetails);
		
		return targetResult;
	}
	
}
