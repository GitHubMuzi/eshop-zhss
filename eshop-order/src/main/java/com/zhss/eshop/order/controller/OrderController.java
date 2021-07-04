package com.zhss.eshop.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhss.eshop.common.util.CloneDirection;
import com.zhss.eshop.common.util.ObjectUtils;
import com.zhss.eshop.order.api.CustomerService;
import com.zhss.eshop.order.api.InventoryService;
import com.zhss.eshop.order.api.PayService;
import com.zhss.eshop.order.api.PromotionService;
import com.zhss.eshop.order.api.ReliableMessageService;
import com.zhss.eshop.order.domain.CalculateCouponeDiscountPriceVO;
import com.zhss.eshop.order.domain.OrderInfoDTO;
import com.zhss.eshop.order.domain.OrderInfoQuery;
import com.zhss.eshop.order.domain.OrderInfoVO;
import com.zhss.eshop.order.domain.OrderItemDTO;
import com.zhss.eshop.order.domain.ReturnGoodsApplyDTO;
import com.zhss.eshop.order.domain.ReturnGoodsApplyVO;
import com.zhss.eshop.order.mapper.UniqueRecordMapper;
import com.zhss.eshop.order.rabbitmq.MessageProducer;
import com.zhss.eshop.order.service.OrderInfoService;
import com.zhss.eshop.order.state.LoggedOrderStateManager;
import com.zhss.eshop.promotion.domain.CouponDTO;
import com.zhss.eshop.promotion.domain.PromotionActivityDTO;

/**
 * 订单管理controller组件
 * @author zhonghuashishan
 *
 */
@RestController
@RequestMapping("/order")  
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	/**
	 * 订单管理service组件
	 */
	@Autowired
	private OrderInfoService orderInfoService;
	/**
	 * 订单状态管理组件
	 */
	@Autowired
	private LoggedOrderStateManager orderStateManager;
	/**
	 * 促销服务
	 */
	@Autowired
	private PromotionService promotionService;
	/**
	 * 库存服务
	 */
	@Autowired
	private InventoryService inventoryService;
	/**
	 * 支付服务
	 */
	@Autowired
	private PayService payService;
	/**
	 * 客服服务
	 */
	@Autowired
	private CustomerService customerService;
	/**
	 * 可靠消息服务
	 */
	@Autowired
	private ReliableMessageService reliableMessageService;
	
	@Autowired
	private MessageProducer messageProducer;
	
	@Autowired
	private UniqueRecordMapper uniqueRecordMapper;
	
	@PutMapping("/test/{id}")
	public Boolean test(@PathVariable("id") Long id) {
		try {
			orderStateManager.confirmReceipt(orderInfoService.getById(id));  
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 计算订单价格
	 * @param order
	 * @param deliveryAddress
	 * @return
	 */
	@PostMapping("/price")  
	public OrderInfoVO calculateOrderPrice(@RequestBody OrderInfoVO orderVO) {
		try {
			OrderInfoDTO order = orderVO.clone(OrderInfoDTO.class, CloneDirection.FORWARD);
			
			// 为每个订单条目查询其对应的促销活动
			List<PromotionActivityDTO> promotionActivities = 
					new ArrayList<PromotionActivityDTO>(order.getOrderItems().size());
			for(OrderItemDTO orderItem : order.getOrderItems()) {
				PromotionActivityDTO promotionActivity = promotionService.getById(
						orderItem.getPromotionActivityId());
				promotionActivities.add(promotionActivity);
			}
			
			
			OrderInfoDTO resultOrder = orderInfoService.calculateOrderPrice(
					order, promotionActivities);  
			
			return resultOrder.clone(OrderInfoVO.class, CloneDirection.OPPOSITE);
		} catch (Exception e) {
			logger.error("error", e); 
			return new OrderInfoVO(); 
		}
	}
	
	/**
	 * 计算优惠券抵扣的金额
	 * @param order 
	 * @param coupon
	 * @return
	 */
	@PostMapping("/coupon")  
	public OrderInfoVO calculateCouponDiscountPrice(
			@RequestBody CalculateCouponeDiscountPriceVO vo) {
		try {
			OrderInfoVO order = vo.getOrder();
			CouponDTO coupon = vo.getCoupon();
			
			OrderInfoDTO resultOrder = orderInfoService.calculateCouponDiscountPrice(
					order.clone(OrderInfoDTO.class, CloneDirection.FORWARD), 
					coupon);
			
			return resultOrder.clone(OrderInfoVO.class, CloneDirection.OPPOSITE);
		} catch (Exception e) {
			logger.error("error", e); 
			return vo.getOrder();
		}
	}
	
	/**
	 * 新建订单
	 * @param order
	 * @return
	 */
	@PostMapping("/")
	public OrderInfoVO save(@RequestBody OrderInfoVO orderVO) {
		try {
			OrderInfoDTO order = orderVO.clone(OrderInfoDTO.class, CloneDirection.FORWARD);
			
			if(order.getCouponId() == null) {
				order.setCouponId(-1L); 
			}
			if(!isStockEnough(order)) {
				return orderVO;
			}
			
			OrderInfoDTO resultOrder = orderInfoService.save(order);
			
			inventoryService.informSubmitOrderEvent(resultOrder); 
			promotionService.useCoupon(resultOrder.getCouponId(), resultOrder.getUserAccountId());
			
			return resultOrder.clone(OrderInfoVO.class, CloneDirection.OPPOSITE);
		} catch (Exception e) {
			logger.error("error", e); 
			return orderVO;
		}
	}
	
	/**
	 * 判断库存是否充足
	 * @param order 订单
	 * @return 库存是否充足
	 * @throws Exception
	 */
	private Boolean isStockEnough(OrderInfoDTO order) throws Exception {
		for(OrderItemDTO orderItem : order.getOrderItems()) {
			Long saleStockQuantity = inventoryService.getSaleStockQuantity(
					orderItem.getGoodsSkuId());
			if(saleStockQuantity < orderItem.getPurchaseQuantity()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 分页查询订单
	 * @param query 查询条件 
	 * @return 订单
	 * @throws Exception
	 */
	@GetMapping("/") 
	public List<OrderInfoVO> listByPage(OrderInfoQuery query) {
		try {
			List<OrderInfoDTO> orders = orderInfoService.listByPage(query);
			return ObjectUtils.convertList(orders, OrderInfoVO.class, CloneDirection.OPPOSITE);
		} catch (Exception e) {
			logger.error("error", e); 
			return new ArrayList<OrderInfoVO>();
		}
	}
	
	/**
	 * 分页查询订单
	 * @param query 查询条件 
	 * @return 订单
	 * @throws Exception
	 */
	@GetMapping("/{id}") 
	public OrderInfoVO getById(@PathVariable("id") Long id) {
		try {
			return orderInfoService.getById(id).clone(OrderInfoVO.class, CloneDirection.OPPOSITE);  
		} catch (Exception e) {
			logger.error("error", e); 
			return new OrderInfoVO();
		}
	}
	
	/**
	 * 取消订单
	 * @param id 订单id
	 * @return 处理结果
	 * @throws Exception
	 */
	@PutMapping("/cancel/{id}")  
	public Boolean cancel(@PathVariable("id") Long id) {
		try {
			Boolean result = orderInfoService.cancel(id);
			inventoryService.informCancelOrderEvent(orderInfoService.getById(id));  
			return result;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 支付订单
	 * @param id 订单id
	 * @return 支付二维码
	 */
	@PutMapping("/pay/{id}")  
	public String pay(@PathVariable("id") Long id) {
		try {
			Boolean canPay = orderInfoService.canPay(id);
			if(canPay) {
				return payService.getQrCode(orderInfoService.getById(id));   
			}
		} catch (Exception e) {
			logger.error("error", e); 
		}
		return "";
	}
	
	/**
	 * 手动确认收货
	 * @param id 订单id
	 * @throws Exception
	 */
	@PutMapping("/confirmReceipt/{id}")
	public Boolean manualConfirmReceipt(@PathVariable("id") Long id) {
		try {
			return orderInfoService.manualConfirmReceipt(id);
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 申请退货
	 * @param apply 退货申请
	 * @return 处理结果
	 * @throws Exception
	 */
	@PutMapping("/applyReturnGoods/{id}")  
	public Boolean applyReturnGoods(@RequestBody ReturnGoodsApplyDTO apply) {
		try {
			Boolean result = orderInfoService.applyReturnGoods(apply);
			
			OrderInfoDTO order = orderInfoService.getById(apply.getOrderInfoId());
			customerService.createReturnGoodsWorksheet(order.getId(), order.getOrderNo(), 
					apply.getReturnGoodsReason(), apply.getReturnGoodsComment());
			
			return result;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 根据订单id查询退货申请 
	 * @param orderInfoId 订单id
	 * @return 退货申请
	 * @throws Exception
	 */
	@GetMapping("/returnGoodsApply/{orderInfoId}")  
	public ReturnGoodsApplyVO getByOrderInfoId(@PathVariable("orderInfoId") Long orderInfoId) {
		try {
			return orderInfoService.getByOrderInfoId(orderInfoId).clone(ReturnGoodsApplyVO.class);
		} catch (Exception e) {
			logger.error("error", e); 
			return null;
		}
	}
	
	/**
	 * 更新退货物流单号
	 * @param orderInfoId 订单id
	 * @param returnGoodsLogisticCode 退货物流单号
	 * @throws Exception
	 */
	@PutMapping("/returnGoodsLogisticCode/{orderInfoId}")
	public Boolean updateReturnGoodsLogisticCode(@PathVariable("orderInfoId") Long orderInfoId, 
			String returnGoodsLogisticCode) {
		try {
			orderInfoService.updateReturnGoodsLogisticCode(orderInfoId, returnGoodsLogisticCode); 
			customerService.syncReturnGoodsLogisticsCode(orderInfoId, returnGoodsLogisticCode);
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	@PutMapping("/asyncLockStock/{orderInfoId}")
	public Boolean asyncLockStock(@PathVariable("orderInfoId") Long orderInfoId) throws Exception {
		uniqueRecordMapper.insert("OrderController_asyncLockStock_" + orderInfoId);  
		
		OrderInfoDTO orderDTO = orderInfoService.getById(orderInfoId);
		
		JSONObject message = new JSONObject();
		message.put("id", UUID.randomUUID().toString().replace("-", ""));   
		message.put("operation", "submit_order");  
		message.put("parameter", orderDTO);  
		
		Long messageId = reliableMessageService.prepareMessage(message.toJSONString()); 
		reliableMessageService.confirmMessage(messageId);
		
		return true;
	}
	
	@PutMapping("/asyncReduceStock/{orderInfoId}")
	public Boolean asyncReduceStock(@PathVariable("orderInfoId") Long orderInfoId) throws Exception {
		uniqueRecordMapper.insert("OrderController_asyncReduceStock_" + orderInfoId);  
		
		OrderInfoDTO orderDTO = orderInfoService.getById(orderInfoId);
		
		JSONObject message = new JSONObject();
		message.put("id", UUID.randomUUID().toString().replace("-", ""));   
		message.put("operation", "pay_order"); 
		message.put("parameter", orderDTO);  
		
		Long messageId = reliableMessageService.prepareMessage(message.toJSONString()); 
		reliableMessageService.confirmMessage(messageId);
		
		return true;
	}
	
	@PutMapping("/asyncScheduleDelivery/{orderInfoId}")
	public Boolean asyncScheduleDelivery(@PathVariable("orderInfoId") Long orderInfoId) throws Exception {
		OrderInfoDTO orderDTO = orderInfoService.getById(orderInfoId);
		
		JSONObject message = new JSONObject();
		message.put("id", UUID.randomUUID().toString().replace("-", ""));   
		message.put("operation", "schedule_delivery");  
		message.put("parameter", orderDTO);  
		
		Long messageId = reliableMessageService.prepareMessage(message.toJSONString()); 
		reliableMessageService.confirmMessage(messageId);
		
		return true;
	}
	
	@PutMapping("/sendMessage/{orderInfoId}") 
	public String sendMessage(@PathVariable("orderInfoId") Long orderInfoId) { 
		// 使用最大努力通知方案来通知消息服务发送短信
		try {
			OrderInfoDTO orderDTO = orderInfoService.getById(orderInfoId);
			
			JSONObject retryRule = new JSONObject();
			retryRule.put("type", 1);
			retryRule.put("retryInterval", 10);
			retryRule.put("maxRetryCount", 3);
			
			JSONObject message = new JSONObject();
			message.put("phoneNumber", orderDTO.getConsigneeCellPhoneNumber());
			message.put("content", "您的订单【" + orderDTO.getOrderNo() + "】已经创建成功，请尽快支付");   
			message.put("retryRule", retryRule); 
			
			messageProducer.send(message.toJSONString());  
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILURE";
		}
		return "SUCCESS";
	}
	
}
