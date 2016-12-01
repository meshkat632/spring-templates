package com.ect.cws.controllers;

import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrivatePageController {
	
	
	@Autowired
	private Properties properties;

	private final Logger logger = LoggerFactory.getLogger(PrivatePageController.class);

	@RequestMapping(value = { "/main**" }, method = RequestMethod.GET)
	public ModelAndView mainView(HttpSession session) {
		logger.info("main");
		logger.info("session:" + session.toString());
		logger.info("applicationName:"+properties.getProperty("comet.url"));
		
		/*
		System.out.println("getCreationTime:"+session.getCreationTime());
		session.setMaxInactiveInterval(10);
		*/
		
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Hello World");
		model.addObject("message", "This is protected page - main Page!");
		model.setViewName("main");

		return model;

	}

}