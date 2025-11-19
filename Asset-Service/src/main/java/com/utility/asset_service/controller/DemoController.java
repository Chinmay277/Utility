package com.utility.asset_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

	
	@RequestMapping(value ="/hello", method = RequestMethod.POST, produces = "application/json")
	public String hello() {
		return "Hello from Asset Service!";
	}
}
