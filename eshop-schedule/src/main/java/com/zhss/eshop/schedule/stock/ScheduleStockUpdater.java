package com.zhss.eshop.schedule.stock;

/**
 * 商品库存更新命令的接口
 * @author zhonghuashishan
 *
 */
public interface ScheduleStockUpdater { 

	/**
	 * 更新商品库存
	 * @return 处理结果
	 * @throws Exception
	 */
	Boolean update() throws Exception;
	
	/**
	 * 设置这个更新组件需要的参数
	 * @param parameter 参数
	 * @throws Exception
	 */
	void setParameter(Object parameter);
	
}
