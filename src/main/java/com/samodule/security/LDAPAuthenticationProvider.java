/**
 * MIT License
 *
 * Copyright (c) 2018 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.samodule.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.samodule.dao.SapAMWorkFLowMasterDao;
import com.samodule.dao.SapAmUserRequestApprovalDao;
import com.samodule.model.SapAmWorkflowMaster;
import com.samodule.service.AuthManager;
import com.samodule.service.HrmLoginManager;
import com.samodule.vo.HrmLoginVO;
import com.samodule.vo.SapAmUserRequestApprovalVO;

/**
 * 
 * A custom authentication provider. Uses the UserService to check
 * authentication.
 * 
 * @author aakin
 * 
 */
@Component("ldapAuthenticationProvider")
public class LDAPAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(LDAPAuthenticationProvider.class);
	/*
	 * @Autowired private UserService userService;
	 */

	@Autowired
	@Resource(name = "hrmloginmanager")
	private HrmLoginManager hrmloginmanager;

	@Autowired
	@Resource(name = "authManager")
	private AuthManager authManager;

//	@Autowired
//	RoleManager roleManager;
	@Autowired
	SapAMWorkFLowMasterDao sapAMWorkFLowMasterDao;
	@Autowired
	SapAmUserRequestApprovalDao sapAmUserRequestApprovalDao;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		logger.info(username + "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + password);
		System.out.println(username + "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + password);

		HrmLoginVO hrmLogin = new HrmLoginVO();
		hrmLogin.setEmailId(username + "@dsgroup.com");
		try {
			//if (authManager.authenticate(username, password)) {
				hrmLogin = hrmloginmanager.get(hrmLogin);
				 hrmLogin.setPassword("black");
				//hrmLogin.setPassword(password);

//			} else {
//				hrmLogin = null;
//			}
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HeapUserDetails userDetails = null;

		if (hrmLogin == null)
			throw new BadCredentialsException("User  not found.");
		else {
			userDetails = new HeapUserDetails(hrmLogin);
			// List<Role> roles=null;//
//			List<Role> roles = sapAMWorkFLowMasterDao
//					.getRoleByLogin(hrmLogin.getEmpCode());
			// List<SapAmWorkflowMaster> roles =
			// sapAMWorkFLowMasterDao.getRoleByLogin(hrmLogin.getEmpCode());
			List<SapAmUserRequestApprovalVO> roles = sapAmUserRequestApprovalDao
					.getWorkFLowByLogin(hrmLogin.getEmpCode());

			Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
			// Set<GrantedAuthority> grantedAuthoritiesNot = new HashSet();
			// grantedAuthoritiesNot.add(new SimpleGrantedAuthority("ROLE_USER"));
			// "Y".equalsIgnoreCase(hrmLogin.getIsHodApprover())?grantedAuthoritiesNot.add(new
			// SimpleGrantedAuthority("ROLE_HOD")):grantedAuthoritiesNot.add(new
			// SimpleGrantedAuthority("ROLE_USER"));;
			if (roles != null)
				for (SapAmUserRequestApprovalVO role : roles) {
					System.out.println(role.getWfCode());
					grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getWfCode()));
				}
//			if ("Y".equalsIgnoreCase(hrmLogin.getIsHodApprover()))
//				grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_HOD"));
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//			userDetails
//					.setAuthorities((roles != null && roles.size() > 0) ? grantedAuthorities
//							: ImmutableSet
//									.<GrantedAuthority> of(new SimpleGrantedAuthority(
//											"ROLE_USER")));
			userDetails.setAuthorities(grantedAuthorities);
			userDetails.setRoles(roles);
		}
		return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
