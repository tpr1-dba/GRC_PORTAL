package com.samodule.service;

import javax.security.auth.login.LoginException;

public interface AuthManager {
	
	public boolean authenticate(String username, String password) throws LoginException;
}
