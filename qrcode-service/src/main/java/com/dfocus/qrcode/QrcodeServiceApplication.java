package com.dfocus.qrcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QrcodeServiceApplication {
	private final Logger logger = LoggerFactory.getLogger(QrcodeServiceApplication.class);
	@Bean
	RabbitManagementTemplate rabbitManagementTemplate() {
		logger.info("=======初始化rabbit管理========");
		logger.debug("=======初始化rabbit管理========");
		return new RabbitManagementTemplate("http://localhost:15672/api/queues");
	}
	public static void main(String[] args) {
		SpringApplication.run(QrcodeServiceApplication.class, args);
	}
}
