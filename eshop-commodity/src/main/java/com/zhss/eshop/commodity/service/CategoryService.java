package com.zhss.eshop.commodity.service;

import java.util.List;

import com.zhss.eshop.commodity.domain.CategoryDTO;

/**
 * 类目管理service组件接口
 * @author zhonghuashishan
 *
 */
public interface CategoryService {

	/**
	 * 查询根类目
	 * @return 根类目集合
	 * @throws Exception
	 */
 	List<CategoryDTO> listRoots() throws Exception;
	
	/**
	 * 查询子类目
	 * @param id 父类目id
	 * @return 子类目集合
	 * @throws Exception
	 */
	List<CategoryDTO> listChildren(Long id) throws Exception;
	
	/**
	 * 新增类目
	 * @param category 类目
	 * @return 处理结果
	 * @throws Exception
	 */
	Boolean save(CategoryDTO category) throws Exception;
	
	/**
	 * 根据id查询类目
	 * @param id 类目id
	 * @return 类目
	 * @throws Exception
	 */
	CategoryDTO getById(Long id) throws Exception;
	
	/**
	 * 更新类目
	 * @param category 类目
	 * @throws Exception
	 */
	void update(CategoryDTO category) throws Exception;
	
	/**
	 * 删除类目
	 * @param id 类目id
	 * @return 处理结果
	 * @throws Exception
	 */
	Boolean remove(Long id) throws Exception;
	
}
