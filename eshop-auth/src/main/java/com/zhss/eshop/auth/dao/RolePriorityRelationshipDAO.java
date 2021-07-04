package com.zhss.eshop.auth.dao;

import java.util.List;

import com.zhss.eshop.auth.domain.RolePriorityRelationshipDO;

/**
 * 角色和权限关系管理模块的DAO组件接口
 * @author zhonghuashishan
 *
 */
public interface RolePriorityRelationshipDAO {

	/**
	 * 新增角色和权限的关联关系
	 * @param rolePriorityRelationshipDO
	 * @throws Exception
	 */
	void save(RolePriorityRelationshipDO rolePriorityRelationshipDO) throws Exception;
	
	/**
	 * 根据权限id查询记录数
	 * @param priorityId 权限id
	 * @return 记录数
	 * @throws Exception
	 */
	Long countByPriorityId(Long priorityId) throws Exception;
	
	/**
	 * 根据角色id查询角色和权限的关系
	 * @param roleId 角色id
	 * @return 角色权限关系DO对象集合
	 * @throws Exception
	 */
	List<RolePriorityRelationshipDO> listByRoleId(Long roleId) throws Exception;
	
	/**
	 * 根据角色id删除角色权限关联关系
	 * @param roleId 角色id
	 * @throws Exception
	 */
	void removeByRoleId(Long roleId) throws Exception;
	
}
