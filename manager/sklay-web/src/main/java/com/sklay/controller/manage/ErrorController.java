package com.sklay.controller.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class) ; 
	@RequestMapping("/default")
	public String errorDefault(){
		
		LOGGER.debug("error.default") ;
		return "error.default";
	}
	
	@RequestMapping("/404")
	public String error404(){
		return "error.404";
	}
}
