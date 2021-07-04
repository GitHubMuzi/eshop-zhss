package com.zhss.best.effort.inform.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.zhss.best.effort.inform.service.db.DataSourceConfig;

@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
@Import(DataSourceConfig.class)
public class BestEffortInformServiceApplication {
	
	public static void main(String[] args) { 
		SpringApplication.run(BestEffortInformServiceApplication.class, args);
	}

}
