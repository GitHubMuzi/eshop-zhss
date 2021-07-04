package com.zhss.eshop.schedule.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 系统初始化监听器
 * @author zhonghuashishan
 *
 */
@WebListener
public class SystemInitContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent event) {

	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}
  
}