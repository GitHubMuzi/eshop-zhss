package com.zhss.eshop.wms.tcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhss.eshop.wms.api.WmsTccApi;
import com.zhss.eshop.wms.constant.TccType;
import com.zhss.eshop.wms.constant.WmsStockUpdateEvent;
import com.zhss.eshop.wms.domain.WmsSaleDeliveryScheduleResult;
import com.zhss.eshop.wms.stock.PayOrderWmsStockUpdater;
import com.zhss.eshop.wms.stock.SubmitOrderWmsStockUpdater;
import com.zhss.eshop.wms.stock.WmsStockUpdaterFactory;

@Component("wmsTccConfirmService")  
@RequestMapping("/wms/tcc/confirm")  
public class WmsTccConfirmService implements WmsTccApi {
	
	/**
	 * 库存更新组件工厂
	 */
	@Autowired
	private WmsStockUpdaterFactory stockUpdaterFactory;

	@Transactional(rollbackFor = Exception.class) 
	public Boolean informSubmitOrderEvent(
			WmsSaleDeliveryScheduleResult scheduleResult) throws Exception {
		SubmitOrderWmsStockUpdater stockUpdater = (SubmitOrderWmsStockUpdater)
				stockUpdaterFactory.create(WmsStockUpdateEvent.SUBMIT_ORDER, scheduleResult);
		stockUpdater.setTccType(TccType.CONFIRM);  
		stockUpdater.update();
		return true;
	}
	
	@Transactional(rollbackFor = Exception.class) 
	public Boolean informPayOrderEvent(
			@RequestBody WmsSaleDeliveryScheduleResult scheduleResult) throws Exception {
		PayOrderWmsStockUpdater stockUpdater = (PayOrderWmsStockUpdater)
				stockUpdaterFactory.create(WmsStockUpdateEvent.PAY_ORDER, scheduleResult);
		stockUpdater.setTccType(TccType.CONFIRM);  
		stockUpdater.update();
		return true;
	}
	
}
