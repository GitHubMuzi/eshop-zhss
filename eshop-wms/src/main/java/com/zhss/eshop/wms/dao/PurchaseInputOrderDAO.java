package com.zhss.eshop.wms.dao;

import java.util.List;

import com.zhss.eshop.wms.domain.PurchaseInputOrderDO;
import com.zhss.eshop.wms.domain.PurchaseInputOrderQuery;

/**
 * 采购入库单管理DAO接口
 * @author zhonghuashishan
 *
 */
public interface PurchaseInputOrderDAO {

	/**
	 * 新增采购入库单
	 * @param purchaseInputOrder 采购入库单
	 * @return 采购入库单id
	 * @throws Exception
	 */
	Long save(PurchaseInputOrderDO purchaseInputOrder) throws Exception;
	
	/**
	 * 分页查询采购入库单
	 * @param query 查询条件
	 * @return 采购入库单
	 * @throws Exception
	 */
	List<PurchaseInputOrderDO> listByPage(PurchaseInputOrderQuery query) throws Exception;
	
	/**
	 * 根据id查询采购入库单
	 * @param id 采购入库单id
	 * @return 采购入库单
	 * @throws Exception
	 */
	PurchaseInputOrderDO getById(Long id) throws Exception; 
	
	/**
	 * 更新采购入库单
	 * @param purchaseInputOrder 采购入库单
	 * @throws Exception
	 */
	void update(PurchaseInputOrderDO purchaseInputOrder) throws Exception;
	
	/**
	 * 更新采购入库单状态
	 * @param purchaseInputOrder 采购入库单
	 * @throws Exception
	 */
	void updateStatus(PurchaseInputOrderDO purchaseInputOrder) throws Exception;

	/**
	 * 更新采购入库单状态
	 * @param id 采购入库单id
	 * @param status 采购入库单状态
	 * @throws Exception
	 */
	void updateStatus(Long id, Integer status) throws Exception;
	
}
