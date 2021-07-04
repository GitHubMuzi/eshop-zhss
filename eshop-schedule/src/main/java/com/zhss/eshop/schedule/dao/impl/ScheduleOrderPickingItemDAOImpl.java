package com.zhss.eshop.schedule.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhss.eshop.common.util.DateProvider;
import com.zhss.eshop.schedule.dao.ScheduleOrderPickingItemDAO;
import com.zhss.eshop.schedule.domain.ScheduleOrderPickingItemDO;
import com.zhss.eshop.schedule.mapper.ScheduleOrderPickingItemMapper;

/**
 * 拣货条目管理DAO组件
 * @author zhonghuashishan
 *
 */
@Repository
public class ScheduleOrderPickingItemDAOImpl implements ScheduleOrderPickingItemDAO {

	/**
	 * 拣货条目管理mapper组件
	 */
	@Autowired
	private ScheduleOrderPickingItemMapper pickingItemMapper;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 批量插入拣货条目
	 * @param orderItem 订单条目 
	 * @param pickingItems 拣货条目
	 * @throws Exception
	 */
	@Override
	public void batchSave(Long orderInfoId, Long orderItemId,
			List<ScheduleOrderPickingItemDO> pickingItems) throws Exception {
		for(ScheduleOrderPickingItemDO pickingItem : pickingItems) {
			pickingItem.setOrderInfoId(orderInfoId); 
			pickingItem.setOrderItemId(orderItemId); 
			pickingItem.setGmtCreate(dateProvider.getCurrentTime()); 
			pickingItem.setGmtModified(dateProvider.getCurrentTime());  
			pickingItemMapper.save(pickingItem);   
		}
	}
	
	/**
	 * 根据订单id和订单条目id查询拣货条目
	 * @param orderInfoId 订单id
	 * @param orderItemId 订单条目id
	 * @return
	 */
	@Override
	public List<ScheduleOrderPickingItemDO> listByOrderItemId(
			Long orderInfoId, Long orderItemId) throws Exception {
		return pickingItemMapper.listByOrderItemId(orderInfoId, orderItemId);
	}
	
	/**
	 * 根据订单条目id删除拣货条目
	 * @param orderInfoId 订单id
	 * @param orderItemId 订单条目id
	 */
	@Override
	public void removeByOrderItemId(Long orderInfoId, 
			Long orderItemId) throws Exception {
		pickingItemMapper.removeByOrderItemId(orderInfoId, orderItemId);
	}
	
}
