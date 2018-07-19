package com.ppmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableEurekaClient
@EnableRedisHttpSession
@MapperScan("com.ppmall.dao")
public class UserWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserWebApplication.class, args);
	}
}
