package com.ect.cws.security;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationModel {
	private boolean isValid;
	private int userId;
	private String login;
	private List<String> roles = new ArrayList<String>();

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
