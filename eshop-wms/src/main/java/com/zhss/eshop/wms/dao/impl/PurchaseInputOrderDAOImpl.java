package com.zhss.eshop.wms.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhss.eshop.common.util.DateProvider;
import com.zhss.eshop.wms.dao.PurchaseInputOrderDAO;
import com.zhss.eshop.wms.domain.PurchaseInputOrderDO;
import com.zhss.eshop.wms.domain.PurchaseInputOrderQuery;
import com.zhss.eshop.wms.mapper.PurchaseInputOrderMapper;

/**
 * 采购入库单管理DAO组件
 * @author zhonghuashishan
 *
 */
@Repository
public class PurchaseInputOrderDAOImpl implements PurchaseInputOrderDAO {

	/**
	 * 采购入库单管理mapper组件
	 */
	@Autowired
	private PurchaseInputOrderMapper purchaseInputOrderMapper;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 新增采购入库单
	 * @param purchaseInputOrder 采购入库单
	 */
	@Override
	public Long save(PurchaseInputOrderDO purchaseInputOrder) throws Exception {
		purchaseInputOrderMapper.save(purchaseInputOrder); 
		return purchaseInputOrder.getId();
	}
	
	/**
	 * 分页查询采购入库单
	 * @param query 查询条件
	 * @return 采购入库单
	 */
	@Override
	public List<PurchaseInputOrderDO> listByPage(
			PurchaseInputOrderQuery query) throws Exception {
		return purchaseInputOrderMapper.listByPage(query);
	}
	
	/**
	 * 根据id查询采购入库单
	 * @param id 采购入库单id
	 * @return 采购入库单
	 */
	@Override
	public PurchaseInputOrderDO getById(Long id) throws Exception {
		return purchaseInputOrderMapper.getById(id);
	}
	
	/**
	 * 更新采购入库单
	 * @param purchaseInputOrder 采购入库单
	 */
	@Override
	public void update(PurchaseInputOrderDO purchaseInputOrder) throws Exception {
		purchaseInputOrder.setGmtModified(dateProvider.getCurrentTime()); 
		purchaseInputOrderMapper.update(purchaseInputOrder); 
	}
	
	/**
	 * 更新采购入库单状态
	 * @param purchaseInputOrder 采购入库单
	 */
	@Override
	public void updateStatus(PurchaseInputOrderDO purchaseInputOrder) throws Exception {
		purchaseInputOrder.setGmtModified(dateProvider.getCurrentTime()); 
		purchaseInputOrderMapper.updateStatus(purchaseInputOrder);
	}
	
	/**
	 * 更新采购入库单状态
	 * @param id 采购入库单id
	 * @param status 采购入库单状态
	 * @throws Exception
	 */
	@Override
	public void updateStatus(Long id, Integer status) throws Exception {
		PurchaseInputOrderDO purchaseInputOrder = getById(id);
		purchaseInputOrder.setStatus(status);   
		updateStatus(purchaseInputOrder);  
	}
	
}
