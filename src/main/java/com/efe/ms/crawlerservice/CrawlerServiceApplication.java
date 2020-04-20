package com.efe.ms.crawlerservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * 邮件服务应用启动入口
 * @author TianLong Liu
 * @date 2019年11月6日 下午4:21:09
 */

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.efe.ms.**.dao")
@EnableScheduling
@ServletComponentScan
public class CrawlerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerServiceApplication.class, args);
	}

}
