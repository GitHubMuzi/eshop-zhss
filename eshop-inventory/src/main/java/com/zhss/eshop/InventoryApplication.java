package com.zhss.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.zhss.eshop.inventory.db.DataSourceConfig;
import com.zhss.eshop.inventory.mq.MessageService;

/**
 * 系统启动类
 * @author zhonghuashishan
 *
 */
@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients
@EnableBinding(MessageService.class)
@Import(DataSourceConfig.class)
@ImportResource({ "classpath:bytetcc-supports-springcloud.xml" }) 
public class InventoryApplication {
	
	public static void main(String[] args) { 
		SpringApplication.run(InventoryApplication.class, args);
	}
	
}
