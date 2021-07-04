package com.zhss.best.effort.inform.service.domain;

import java.util.Date;

public class Message {
	
	private Long id;
	private String phoneNumber;
	private String content;
	private String retryRule;
	private Integer status;
	private Date latestSendTime;
	private Integer currentRetryCount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRetryRule() {
		return retryRule;
	}
	public void setRetryRule(String retryRule) {
		this.retryRule = retryRule;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getLatestSendTime() {
		return latestSendTime;
	}
	public void setLatestSendTime(Date latestSendTime) {
		this.latestSendTime = latestSendTime;
	}
	public Integer getCurrentRetryCount() {
		return currentRetryCount;
	}
	public void setCurrentRetryCount(Integer currentRetryCount) {
		this.currentRetryCount = currentRetryCount;
	}
	
}
