package com.ppmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableAutoConfiguration
@SpringBootApplication
@EnableEurekaClient
@EnableRedisHttpSession
@ComponentScan
@MapperScan("com.ppmall.dao")
public class ShippingWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShippingWebApplication.class, args);
	}

}
