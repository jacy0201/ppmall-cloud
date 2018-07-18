package com.ycstudio.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ppmall-web-user")
public interface IUserService {
	@RequestMapping(value = "/test.do",method = RequestMethod.GET)
    Object test();
}
