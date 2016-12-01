package com.ect.cws.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PublicPageController {

	private final Logger logger = LoggerFactory.getLogger(PublicPageController.class);

	@RequestMapping(value = { "/public" }, method = RequestMethod.GET)
	public ModelAndView manageGuestEntry(HttpSession session) {
		logger.info("createNew");	
		logger.info("session:"+session.toString());
		logger.info("login"+SecurityContextHolder.getContext().getAuthentication().isAuthenticated());		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			if (!(auth instanceof AnonymousAuthenticationToken)) {				    
			    return new ModelAndView("forward:/main");
			}	
		}		
		ModelAndView model = new ModelAndView();				
		model.setViewName("public");
		return model;
	}
	

}