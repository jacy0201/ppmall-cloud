package com.ycstudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.ycstudio.filter.SecurityFilter;

@EnableAutoConfiguration
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableRedisHttpSession
public class ServiceZuulApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceZuulApplication.class, args);
		
	}
	@Bean
    public SecurityFilter securityFilter(){
        return new SecurityFilter();
    }
}
