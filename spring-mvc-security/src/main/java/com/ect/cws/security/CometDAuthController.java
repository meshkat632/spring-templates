package com.ect.cws.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CometDAuthController {

	/*
	 @Autowired private 
	 Properties properties;
	 */

	private final Logger logger = LoggerFactory.getLogger(CometDAuthController.class);

	@RequestMapping(value = { "/cometd/auth" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public AuthorizationModel auth(HttpSession session) {
		logger.debug("auth:" + session.getId());
		AuthorizationModel model = new AuthorizationModel();
		model.setValid(false);
		org.springframework.security.core.userdetails.User user = getUserDetails();
		if (user != null) {
			model.setValid(true);
			model.setLogin(user.getUsername());
			List<String> roles = new ArrayList<String>();
			user.getAuthorities().forEach(item -> {
				roles.add(item.getAuthority());
			});
			model.setRoles(roles);
		} else {
			logger.error("invalid user data. ");
		}
		return model;
	}

	@RequestMapping(value = { "/cometd/guest" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public AuthorizationModel manageGuestEntry(HttpSession session) {
		logger.debug("createNew");
		logger.debug("session:" + session.toString() + " session.isNew():" + session.isNew());
		AuthorizationModel model = new AuthorizationModel();
		model.setValid(true);
		if (session.isNew()) {
			session.invalidate();
		}
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE_GUEST");
		model.setRoles(roles);
		return model;

	}

	public org.springframework.security.core.userdetails.User getUserDetails() {
		org.springframework.security.core.context.SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (null == auth)
			return null;
		Object principal = auth.getPrincipal();
		if (principal instanceof org.springframework.security.core.userdetails.User) {
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
			logger.debug("principal.getUsername:" + user.getUsername());
			logger.debug("principal.getAuthorities:" + user.getAuthorities());
			return user;
		}
		return null;
	}

}