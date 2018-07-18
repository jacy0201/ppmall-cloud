package com.ycstudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycstudio.service.IUserService;

@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired
	private IUserService iUserService;
	
	@RequestMapping("/test.do")
	@ResponseBody
	public Object test() {
		return iUserService.test();
	}
}
