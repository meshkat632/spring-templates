package com.ect.cws.security;

public class GrantedAuthorityModel implements org.springframework.security.core.GrantedAuthority {

	private static final long serialVersionUID = 1L;

	String role = "";

	public GrantedAuthorityModel() {
		this.role = "GUEST";
	}
	
	public GrantedAuthorityModel(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role;
	}

}
