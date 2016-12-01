package com.ect.cws.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	private final Logger logger = LoggerFactory.getLogger(LoginController.class);	
	
	
	@RequestMapping(value = { "/",  "/index**" }, method = RequestMethod.GET)
	public ModelAndView indexView() {
		logger.info("index");
		return new ModelAndView("redirect:main");
	}	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(			
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, 
			HttpSession session) {
		    logger.info("session:"+session.toString());
		
			logger.info("login"+SecurityContextHolder.getContext().getAuthentication().isAuthenticated());		
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth != null){
				if (!(auth instanceof AnonymousAuthenticationToken)) {				    
				    return new ModelAndView("forward:/main");
				}	
			}			
			
			ModelAndView model = new ModelAndView();
			if (error != null) {
				model.addObject("error", "Invalid username and password!");
			}

			if (logout != null) {
				model.addObject("msg", "You've been logged out successfully.");
			}
			model.setViewName("login");

			return model;

		}
	@RequestMapping(value = { "/logout" ,"/j_spring_security_logout"}, method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
	        logger.info("logout");	        
	        try{
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // concern you
	        SecurityContextLogoutHandler ctxLogOut = new SecurityContextLogoutHandler(); // concern you
	        ctxLogOut.logout(request, response, auth); // concern you
	        }catch (Exception e) {
				e.printStackTrace();
			}
	        
			ModelAndView model = new ModelAndView();
			model.setViewName("login");
			return model;

		}
}