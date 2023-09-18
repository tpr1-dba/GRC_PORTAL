package com.samodule.security;

//import java.io.IOException;

//import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.RequestMatcher;

//public class CustomUsernamePasswordAuthFilter extends AbstractAuthenticationProcessingFilter  {
public class CustomUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter  {
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException {
		String username=req.getParameter("username");
		String password= req.getParameter("password");
		System.out.println("=============================================================");
		System.out.println(username+"   "+password);
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,password);
		return this.getAuthenticationManager().authenticate(token);
	}

}
