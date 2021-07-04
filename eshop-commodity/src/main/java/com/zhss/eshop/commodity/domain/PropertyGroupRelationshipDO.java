package com.zhss.eshop.commodity.domain;

import java.util.Date;

import com.zhss.eshop.common.util.AbstractObject;

/**
 * 属性分组和属性关系DO
 * @author zhonghuashishan
 *
 */
public class PropertyGroupRelationshipDO extends AbstractObject {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 属性分组id
	 */
	private Long propertyGroupId;
	/**
	 * 属性id
	 */
	private Long propertyId;
	/**
	 * 属性是否必填
	 */
	private Integer required;
	/**
	 * 属性类型
	 */
	private String propertyTypes;
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
	public Long getPropertyGroupId() {
		return propertyGroupId;
	}
	public void setPropertyGroupId(Long propertyGroupId) {
		this.propertyGroupId = propertyGroupId;
	}
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public Integer getRequired() {
		return required;
	}
	public void setRequired(Integer required) {
		this.required = required;
	}
	public String getPropertyTypes() {
		return propertyTypes;
	}
	public void setPropertyTypes(String propertyTypes) {
		this.propertyTypes = propertyTypes;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((propertyGroupId == null) ? 0 : propertyGroupId.hashCode());
		result = prime * result + ((propertyId == null) ? 0 : propertyId.hashCode());
		result = prime * result + ((propertyTypes == null) ? 0 : propertyTypes.hashCode());
		result = prime * result + ((required == null) ? 0 : required.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PropertyGroupRelationshipDO other = (PropertyGroupRelationshipDO) obj;
		if (gmtCreate == null) {
			if (other.gmtCreate != null) {
				return false;
			}
		} else if (!gmtCreate.equals(other.gmtCreate)) {
			return false;
		}
		if (gmtModified == null) {
			if (other.gmtModified != null) {
				return false;
			}
		} else if (!gmtModified.equals(other.gmtModified)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (propertyGroupId == null) {
			if (other.propertyGroupId != null) {
				return false;
			}
		} else if (!propertyGroupId.equals(other.propertyGroupId)) {
			return false;
		}
		if (propertyId == null) {
			if (other.propertyId != null) {
				return false;
			}
		} else if (!propertyId.equals(other.propertyId)) {
			return false;
		}
		if (propertyTypes == null) {
			if (other.propertyTypes != null) {
				return false;
			}
		} else if (!propertyTypes.equals(other.propertyTypes)) {
			return false;
		}
		if (required == null) {
			if (other.required != null) {
				return false;
			}
		} else if (!required.equals(other.required)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "PropertyGroupRelationshipDO [id=" + id + ", propertyGroupId=" + propertyGroupId + ", propertyId="
				+ propertyId + ", required=" + required + ", propertyTypes=" + propertyTypes + ", gmtCreate="
				+ gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
	
}
