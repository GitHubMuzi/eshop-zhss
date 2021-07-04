package com.zhss.eshop.wms.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhss.eshop.common.json.DateJsonDeserializer;
import com.zhss.eshop.common.json.DateJsonSerializer;
import com.zhss.eshop.common.util.AbstractObject;

/**
 * 退货入库单
 * @author zhonghuashishan
 *
 */
public class ReturnGoodsInputOrderVO extends AbstractObject {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 退货工单id
	 */
	private Long returnGoodsWorksheetId;
	/**
	 * 用户账号ID
	 */
	private Long userAccountId;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 退货入库单的状态
	 */
	private Integer status;
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
	 * 折扣金额
	 */
	private Double discountAmount;
	/**
	 * 优惠券抵扣金额
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
	 * 退货原因
	 */
	private Integer returnGoodsReason;
	/**
	 * 退货备注
	 */
	private String returnGoodsRemark;
	/**
	 * 退货的实际到货时间
	 */
	@JsonSerialize(using=DateJsonSerializer.class)  
	@JsonDeserialize(using=DateJsonDeserializer.class)
	private Date arrivalTime;
	/**
	 * 销售出库单的创建时间
	 */
	private Date gmtCreate;
	/**
	 * 销售出库单的修改时间
	 */
	private Date gmtModified;
	/**
	 * 退货入库单条目DTO集合
	 */
	private List<ReturnGoodsInputOrderItemVO> items;
	
	public Long getReturnGoodsWorksheetId() {
		return returnGoodsWorksheetId;
	}
	public void setReturnGoodsWorksheetId(Long returnGoodsWorksheetId) {
		this.returnGoodsWorksheetId = returnGoodsWorksheetId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserAccountId() {
		return userAccountId;
	}
	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Integer getReturnGoodsReason() {
		return returnGoodsReason;
	}
	public void setReturnGoodsReason(Integer returnGoodsReason) {
		this.returnGoodsReason = returnGoodsReason;
	}
	public String getReturnGoodsRemark() {
		return returnGoodsRemark;
	}
	public void setReturnGoodsRemark(String returnGoodsRemark) {
		this.returnGoodsRemark = returnGoodsRemark;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
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
	public List<ReturnGoodsInputOrderItemVO> getItems() {
		return items;
	}
	public void setItems(List<ReturnGoodsInputOrderItemVO> items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		return "ReturnGoodsInputOrderVO [id=" + id + ", returnGoodsWorksheetId=" + returnGoodsWorksheetId
				+ ", userAccountId=" + userAccountId + ", orderId=" + orderId + ", orderNo=" + orderNo + ", status="
				+ status + ", consignee=" + consignee + ", deliveryAddress=" + deliveryAddress
				+ ", consigneeCellPhoneNumber=" + consigneeCellPhoneNumber + ", freight=" + freight + ", payType="
				+ payType + ", totalAmount=" + totalAmount + ", discountAmount=" + discountAmount + ", couponAmount="
				+ couponAmount + ", payableAmount=" + payableAmount + ", invoiceTitle=" + invoiceTitle + ", taxpayerId="
				+ taxpayerId + ", orderComment=" + orderComment + ", returnGoodsReason=" + returnGoodsReason
				+ ", returnGoodsRemark=" + returnGoodsRemark + ", arrivalTime=" + arrivalTime + ", gmtCreate="
				+ gmtCreate + ", gmtModified=" + gmtModified + ", items=" + items + "]";
	}

}
