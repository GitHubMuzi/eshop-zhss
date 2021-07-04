package com.zhss.eshop.wms.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhss.eshop.common.json.DateJsonDeserializer;
import com.zhss.eshop.common.json.DateJsonSerializer;
import com.zhss.eshop.common.util.AbstractObject;

/**
 * 采购入库单
 * @author zhonghuashishan
 *
 */
public class PurchaseInputOrderVO extends AbstractObject {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 供应商id
	 */
	private Long supplierId;
	/**
	 * 预期到达时间
	 */
	@JsonSerialize(using=DateJsonSerializer.class)  
	@JsonDeserialize(using=DateJsonDeserializer.class)
	private Date expectArrivalTime;
	/**
	 * 实际到达时间
	 */
	@JsonSerialize(using=DateJsonSerializer.class)  
	@JsonDeserialize(using=DateJsonDeserializer.class)
	private Date actualArrivalTime;
	/**
	 * 采购联系人
	 */
	private String purchaseContactor;
	/**
	 * 采购联系人电话号码
	 */
	private String purchaseContactorPhoneNumber;
	/**
	 * 采购联系人邮箱地址
	 */
	private String purchaseContactorEmail;
	/**
	 * 采购单备注
	 */
	private String purchaseOrderRemark;
	/**
	 * 采购员
	 */
	private String purchaser;
	/**
	 * 采购入库单的状态
	 */
	private Integer status;
	/**
	 * 采购入库单的创建时间
	 */
	private Date gmtCreate;
	/**
	 * 采购入库单的修改时间
	 */
	private Date gmtModified;
	/**
	 * 采购入库单条目集合
	 */
	private List<PurchaseInputOrderItemVO> items;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Date getExpectArrivalTime() {
		return expectArrivalTime;
	}
	public void setExpectArrivalTime(Date expectArrivalTime) {
		this.expectArrivalTime = expectArrivalTime;
	}
	public Date getActualArrivalTime() {
		return actualArrivalTime;
	}
	public void setActualArrivalTime(Date actualArrivalTime) {
		this.actualArrivalTime = actualArrivalTime;
	}
	public String getPurchaseContactor() {
		return purchaseContactor;
	}
	public void setPurchaseContactor(String purchaseContactor) {
		this.purchaseContactor = purchaseContactor;
	}
	public String getPurchaseContactorPhoneNumber() {
		return purchaseContactorPhoneNumber;
	}
	public void setPurchaseContactorPhoneNumber(String purchaseContactorPhoneNumber) {
		this.purchaseContactorPhoneNumber = purchaseContactorPhoneNumber;
	}
	public String getPurchaseContactorEmail() {
		return purchaseContactorEmail;
	}
	public void setPurchaseContactorEmail(String purchaseContactorEmail) {
		this.purchaseContactorEmail = purchaseContactorEmail;
	}
	public String getPurchaseOrderRemark() {
		return purchaseOrderRemark;
	}
	public void setPurchaseOrderRemark(String purchaseOrderRemark) {
		this.purchaseOrderRemark = purchaseOrderRemark;
	}
	public String getPurchaser() {
		return purchaser;
	}
	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public List<PurchaseInputOrderItemVO> getItems() {
		return items;
	}
	public void setItems(List<PurchaseInputOrderItemVO> items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		return "PurchaseInputOrderVO [id=" + id + ", supplierId=" + supplierId + ", expectArrivalTime="
				+ expectArrivalTime + ", actualArrivalTime=" + actualArrivalTime + ", purchaseContactor="
				+ purchaseContactor + ", purchaseContactorPhoneNumber=" + purchaseContactorPhoneNumber
				+ ", purchaseContactorEmail=" + purchaseContactorEmail + ", purchaseOrderRemark=" + purchaseOrderRemark
				+ ", purchaser=" + purchaser + ", status=" + status + ", gmtCreate=" + gmtCreate + ", gmtModified="
				+ gmtModified + ", items=" + items + "]";
	}
	
}
