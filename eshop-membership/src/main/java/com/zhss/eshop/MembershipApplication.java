package com.zhss.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.zhss.eshop.membership.db.DataSourceConfig;

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
@Import(DataSourceConfig.class)
@ImportResource({ "classpath:bytetcc-supports-springcloud.xml" }) 
public class MembershipApplication {
	
	public static void main(String[] args) { 
		SpringApplication.run(MembershipApplication.class, args);
	}
	
}
