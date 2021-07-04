package com.zhss.reliable.message.service.domain;

import java.util.Date;

public class Message {
	
	private Long id;
	private String content;
	private Integer status;
	private Date createdTime;
	private Date removedTime;
	private Date confirmedTime;
	private Date finishedTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getRemovedTime() {
		return removedTime;
	}
	public void setRemovedTime(Date removedTime) {
		this.removedTime = removedTime;
	}
	public Date getConfirmedTime() {
		return confirmedTime;
	}
	public void setConfirmedTime(Date confirmedTime) {
		this.confirmedTime = confirmedTime;
	}
	public Date getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}
	
}
