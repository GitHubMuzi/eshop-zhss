package com.zhss.eshop.logistics.api;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhss.eshop.commodity.domain.GoodsDTO;
import com.zhss.eshop.common.util.DateProvider;
import com.zhss.eshop.logistics.api.thirdparty.CreateEorderRequest;
import com.zhss.eshop.logistics.api.thirdparty.CreateEorderRequestBuilder;
import com.zhss.eshop.logistics.api.thirdparty.CreateEorderResponse;
import com.zhss.eshop.logistics.api.thirdparty.LogisticApi;
import com.zhss.eshop.logistics.domain.FreightTemplateDTO;
import com.zhss.eshop.logistics.service.FreightTemplateService;
import com.zhss.eshop.logistics.service.impl.FreightCalculator;
import com.zhss.eshop.logistics.service.impl.FreightCalculatorFactory;
import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.order.domain.OrderItemDTO;
import com.zhss.eshop.wms.domain.LogisticOrderDTO;

/**
 * 物流中心接口
 * @author zhonghuashishan
 *
 */
@RestController
public class LogisticsService implements LogisticsApi {
	
	private static final Logger logger = LoggerFactory.getLogger(LogisticsApi.class);
	
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	/**
	 * 商品中心接口
	 */
	@Autowired
	private CommodityService commodityService;
	/**
	 * 运费模板管理service组件
	 */
	@Autowired
	private FreightTemplateService freightTemplateService;
	/**
	 * 运费计算器工厂
	 */
	@Autowired
	private FreightCalculatorFactory freightCalculatorFactory;
	/**
	 * 物流api接口
	 */
	@Autowired
	private LogisticApi logisticApi;

	/**
	 * 计算商品sku的运费
	 * @param goodsSkuDTO 商品sku DTO
	 * @return 商品sku的运费
	 */
	@Override
	public Double calculateFreight(@RequestBody OrderInfoDTO order) {
		try {
			// 获取商品对应的运费模板
			OrderItemDTO orderItem = order.getOrderItems().get(0);
			Long goodsId = orderItem.getGoodsId();
			GoodsDTO goods = commodityService.getGoodsById(goodsId);
			FreightTemplateDTO freightTemplate = freightTemplateService.getById(
					goods.getFreightTemplateId());
			
			// 获取运费计算器
			FreightCalculator freightCalculator = freightCalculatorFactory.get(freightTemplate);
			Double freight = freightCalculator.calculate(freightTemplate, order, orderItem);
			
			return freight;
		} catch (Exception e) {
			logger.error("error", e); 
			return 0.0;
		}
	}
	
	/**
	 * 申请物流单
	 * @param order 订单
	 * @return 物流单
	 */
	@Override
	public LogisticOrderDTO applyLogisticOrder(@RequestBody OrderInfoDTO order) {
		try {
			CreateEorderRequest request = CreateEorderRequestBuilder.get()
					.buildOrderRelatedInfo(order)
					.buildReceiver(order) 
					.buildGoodsList(order)
					.buildTotalDataMetric(order)
					.create();
			
			CreateEorderResponse response = logisticApi.createEOrder(request);
			
			LogisticOrderDTO logisticOrder = createLogisticOrder(response);
 			
			return logisticOrder;
		} catch (Exception e) {
			logger.error("error", e); 
			return null;
		}
	}
	
	/**
	 * 创建物流单
	 * @param response 创建电子面单响应结果
	 * @return 物流单
	 * @throws Exception
	 */
	private LogisticOrderDTO createLogisticOrder(
			CreateEorderResponse response) throws Exception {
		LogisticOrderDTO logisticOrder = new LogisticOrderDTO();
		logisticOrder.setLogisticCode(response.getLogisticCode());
		logisticOrder.setContent(response.getLogisticOrderContent()); 
		logisticOrder.setGmtCreate(dateProvider.getCurrentTime()); 
		logisticOrder.setGmtModified(dateProvider.getCurrentTime()); 
		
		return logisticOrder;
	}
	
	/**
	 * 获取订单的签收时间
	 * @param orderId 订单id
	 * @param orderNo 订单编号
	 * @return 签收时间
	 */
	@Override
	public Date getSignedTime(
			@RequestParam("orderId") Long orderId, 
			@RequestParam("orderNo") String orderNo) {
		try {
			return dateProvider.getCurrentTime();
		} catch (Exception e) {
			logger.error("error", e); 
			return null;
		}
	}
	
}
