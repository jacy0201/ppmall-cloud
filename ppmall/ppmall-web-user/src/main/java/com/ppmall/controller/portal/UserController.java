package com.ppmall.controller.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ppmall.common.ServerResponse;

@Controller
@RequestMapping("/")
public class UserController {
	
	@RequestMapping("/test.do")
	@ResponseBody
	public ServerResponse test() {
		return ServerResponse.createSuccess();
	}

}
