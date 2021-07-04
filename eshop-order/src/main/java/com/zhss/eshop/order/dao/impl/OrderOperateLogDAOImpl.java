package com.zhss.eshop.order.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhss.eshop.order.dao.OrderOperateLogDAO;
import com.zhss.eshop.order.domain.OrderOperateLogDO;
import com.zhss.eshop.order.mapper.OrderOperateLogMapper;

/**
 * 订单操作日志管理DAO组件
 * @author zhonghuashishan
 *
 */
@Repository
public class OrderOperateLogDAOImpl implements OrderOperateLogDAO {

	/**
	 * 订单操作日志管理mapper组件
	 */
	@Autowired
	private OrderOperateLogMapper logMapper;
	
	/**
	 * 新增订单操作日志
	 * @param log 订单操作日志
	 */
	@Override
	public void save(OrderOperateLogDO log) throws Exception {
		logMapper.save(log); 
	}
	
	/**
	 * 查询订单操作日志
	 * @param orderInfoId 订单id
	 * @return 订单操作日志
	 */
	@Override
	public List<OrderOperateLogDO> listByOrderInfoId(Long orderInfoId) throws Exception {
		return logMapper.listByOrderInfoId(orderInfoId);
	}
	
}
