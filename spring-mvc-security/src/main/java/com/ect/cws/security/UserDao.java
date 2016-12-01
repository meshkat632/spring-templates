package com.ect.cws.security;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserDao implements UserDetailsService  {
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("Getting access details from employee dao :"+username);		
		String userName = username;
		String password = "ect";
		
		//String password = "123456";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new com.ect.cws.security.GrantedAuthorityModel("ROLE_ADMIN"));
		authorities.add(new com.ect.cws.security.GrantedAuthorityModel("ROLE_USER"));
		UserDetails user = new org.springframework.security.core.userdetails.User(userName, hashedPassword, authorities);
        return user;		
	} 
}
