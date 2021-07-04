package com.zhss.eshop.membership.domain;

import java.util.Date;

import com.zhss.eshop.common.util.AbstractObject;

/**
 * 用户详细信息
 * @author zhonghuashishan
 *
 */
public class UserDetailDTO extends AbstractObject {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户账号id
	 */
	private Long userAccountId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 身份证号
	 */
	private String idNumber;
	/**
	 * 住址
	 */
	private String address;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	
	@Override
	public String toString() {
		return "UserDetailDTO [id=" + id + ", userAccountId=" + userAccountId + ", name=" + name + ", gender=" + gender
				+ ", birthday=" + birthday + ", idNumber=" + idNumber + ", address=" + address + ", gmtCreate="
				+ gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
	
}
