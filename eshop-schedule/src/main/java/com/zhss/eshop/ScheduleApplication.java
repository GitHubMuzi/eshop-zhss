package com.zhss.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.zhss.eshop.schedule.bean.SpringContext;
import com.zhss.eshop.schedule.db.DataSourceConfig;
import com.zhss.eshop.schedule.mq.MessageService;
import com.zhss.eshop.schedule.stock.StockUpdateMessageConsumer;

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
public class ScheduleApplication {
	
	public static void main(String[] args) { 
		ApplicationContext context = SpringApplication.run(ScheduleApplication.class, args);
		SpringContext.setContext(context); 
	}
	
	@StreamListener("stock-update-message-queue")
	public void receive(byte[] message) {
		StockUpdateMessageConsumer consumer = 
				SpringContext.getContext().getBean(StockUpdateMessageConsumer.class);
		consumer.consume(new String(message));  
	}
	
}
