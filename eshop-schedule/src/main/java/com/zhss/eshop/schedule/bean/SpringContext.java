package com.zhss.eshop.schedule.bean;

import org.springframework.context.ApplicationContext;

public class SpringContext {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		SpringContext.context = context;
	}
	
}
