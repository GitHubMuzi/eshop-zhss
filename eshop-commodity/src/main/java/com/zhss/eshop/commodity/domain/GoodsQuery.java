package com.zhss.eshop.commodity.domain;

/**
 * 商品查询条件
 * @author zhonghuashishan
 *
 */
public class GoodsQuery {

	/**
	 * 分页查询起始位置
	 */
	private Integer offset;
	/**
	 * 每页显示的记录数量
	 */
	private Integer size;
	/**
	 * 类目id
	 */
	private Long categoryId;
	/**
	 * 品牌id
	 */
	private Long brandId;
	/**
	 * 商品编号
	 */
	private String code;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品副名称
	 */
	private String subName;
	/**
	 * 商品状态
	 */
	private Integer status;

	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
