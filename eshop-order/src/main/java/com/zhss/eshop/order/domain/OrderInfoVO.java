package com.zhss.eshop.order.domain;

import java.util.Date;
import java.util.List;

import com.zhss.eshop.common.util.AbstractObject;

/**
 * 订单信息DTO
 * @author zhonghuashishan
 *
 */
public class OrderInfoVO extends AbstractObject {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 用户账号id
	 */
	private Long userAccountId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 收货地址
	 */
	private String deliveryAddress;
	/**
	 * 收货人手机号码
	 */
	private String consigneeCellPhoneNumber;
	/**
	 * 运费
	 */
	private Double freight;
	/**
	 * 支付方式
	 */
	private Integer payType;
	/**
	 * 订单总金额
	 */
	private Double totalAmount;
	/**
	 * 促销活动减免金额
	 */
	private Double discountAmount;
	/**
	 * 优惠券减免金额
	 */
	private Double couponAmount;
	/**
	 * 应付金额
	 */
	private Double payableAmount;
	/**
	 * 发票抬头
	 */
	private String invoiceTitle;
	/**
	 * 纳税人识别号
	 */
	private String taxpayerId;
	/**
	 * 订单备注
	 */
	private String orderComment;
	/**
	 * 是否发表了评论
	 */
	private Integer publishedComment;
	/**
	 * 确认收货时间
	 */
	private Date confirmReceiptTime;
	/**
	 * 订单使用的优惠券id
	 */
	private Long couponId;
	/**
	 * 销售出库单的创建时间
	 */
	private Date gmtCreate;
	/**
	 * 销售出库单的修改时间
	 */
	private Date gmtModified;
	/**
	 * 订单包含的订单条目
	 */
	private List<OrderItemVO> orderItems;
	/**
	 * 订单操作日志
	 */
	private List<OrderOperateLogVO> logs;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Long getUserAccountId() {
		return userAccountId;
	}
	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getConsigneeCellPhoneNumber() {
		return consigneeCellPhoneNumber;
	}
	public void setConsigneeCellPhoneNumber(String consigneeCellPhoneNumber) {
		this.consigneeCellPhoneNumber = consigneeCellPhoneNumber;
	}
	public Double getFreight() {
		return freight;
	}
	public void setFreight(Double freight) {
		this.freight = freight;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Double getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}
	public Double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getTaxpayerId() {
		return taxpayerId;
	}
	public void setTaxpayerId(String taxpayerId) {
		this.taxpayerId = taxpayerId;
	}
	public String getOrderComment() {
		return orderComment;
	}
	public void setOrderComment(String orderComment) {
		this.orderComment = orderComment;
	}
	public Integer getPublishedComment() {
		return publishedComment;
	}
	public void setPublishedComment(Integer publishedComment) {
		this.publishedComment = publishedComment;
	}
	public Date getConfirmReceiptTime() {
		return confirmReceiptTime;
	}
	public void setConfirmReceiptTime(Date confirmReceiptTime) {
		this.confirmReceiptTime = confirmReceiptTime;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public List<OrderItemVO> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemVO> orderItems) {
		this.orderItems = orderItems;
	}
	public List<OrderOperateLogVO> getLogs() {
		return logs;
	}
	public void setLogs(List<OrderOperateLogVO> logs) {
		this.logs = logs;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	
	@Override
	public String toString() {
		return "OrderInfoVO [id=" + id + ", orderNo=" + orderNo + ", userAccountId=" + userAccountId + ", username="
				+ username + ", orderStatus=" + orderStatus + ", consignee=" + consignee + ", deliveryAddress="
				+ deliveryAddress + ", consigneeCellPhoneNumber=" + consigneeCellPhoneNumber + ", freight=" + freight
				+ ", payType=" + payType + ", totalAmount=" + totalAmount + ", discountAmount=" + discountAmount
				+ ", couponAmount=" + couponAmount + ", payableAmount=" + payableAmount + ", invoiceTitle="
				+ invoiceTitle + ", taxpayerId=" + taxpayerId + ", orderComment=" + orderComment + ", publishedComment="
				+ publishedComment + ", confirmReceiptTime=" + confirmReceiptTime + ", couponId=" + couponId
				+ ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", orderItems=" + orderItems + ", logs="
				+ logs + "]";
	}
	
}
