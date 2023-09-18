package com.samodule.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.base.Preconditions;
import com.samodule.vo.HrmLoginVO;
import com.samodule.vo.SapAmUserRequestApprovalVO;

/**
 * Spring Security User Details Interface Implementation.
 *
 * @author Ibragimov Ruslan
 */
public class HeapUserDetails implements UserDetails {

	private String secret;
	private String password;
	private Long userId;
	private String username;
	private String empCode;
	private String company;
	private String status;
	private String imisLoginId;
	private String sapUserId;
	private String sapEntityCode;
	private String sbuCode;
	private String funcReportg;
	private String isHodApprover;
	private Set<GrantedAuthority> authorities;
	private List<SapAmUserRequestApprovalVO> roles;
	
	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * Creates an instance of spring security user class which implements
	 * UserDetails interface.
	 */
	// public HeapUserDetails(){}
	public HeapUserDetails(final HrmLoginVO hrmLogin) {
		Preconditions.checkNotNull(hrmLogin, "User Id should be not null.");
		Preconditions.checkNotNull(hrmLogin.getEmpRecid(), "User Id should be not null.");
		Preconditions.checkNotNull(hrmLogin.getPassword(), "Password should be not null.");
		Preconditions.checkNotNull(hrmLogin.getStatus(), "Status should be vailid.");
		// Preconditions.checkNotNull(hrmLogin.getEmailId(), "User login id should be
		// set.");
		Preconditions.checkNotNull(hrmLogin.getEmployeeName(), "Distributer name should be set.");
		Preconditions.checkNotNull(hrmLogin.getEmpCode(), "Empployee Code should be set.");
		// Preconditions.checkNotNull(hrmLogin.getUSER_LOGIN_ID(), "User email should be
		// set.");
		System.out.println("HeapUserDetails hrmLogin.getEmpCode()" + hrmLogin.getEmpCode());
		// this.authorities = ImmutableSet.<GrantedAuthority>of(new
		// SimpleGrantedAuthority("ROLE_USER"));
		this.password = hrmLogin.getPassword();
		this.username = hrmLogin.getEmployeeName();
		this.secret = hrmLogin.getEmailId();
		this.userId = hrmLogin.getEmpRecid();
		this.status = hrmLogin.getStatus();
		this.empCode = hrmLogin.getEmpCode();
		this.company = hrmLogin.getCompany();
		this.sapEntityCode = hrmLogin.getSapEntityCode();
		this.imisLoginId = hrmLogin.getImisLoginId();
		this.sapUserId = hrmLogin.getSapUserId();
		this.sbuCode = hrmLogin.getSbuCode();
		this.funcReportg = hrmLogin.getFuncReportg();
		this.isHodApprover = hrmLogin.getIsHodApprover();	}

	public String getSecret() {
		return this.secret;
	}

	public Long getUserId() {
		return this.userId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void eraseCredentials() {
		this.secret = null;
		this.password = null;
	}

	public String getStatus() {
		return status;
	}

	public String getEmpCode() {
		return empCode;
	}

//	public void setDistributerName(String distributerName) {
//		this.distributerName = distributerName;
//	}

	public String getCompany() {
		return company;
	}

//	public void setCompany(String company) {
//		this.company = company;
//	}

	public String getImisLoginId() {
		return imisLoginId;
	}

	@Override
	public String toString() {
		return "HeapUserDetails [secret=" + secret + ", password=" + password + ", userId=" + userId + ", username="
				+ username + ", empCode=" + empCode + ", status=" + status + ", imisLoginId=" + imisLoginId
				+ ", authorities=" + authorities + "]";
	}

	public String getSapEntityCode() {
		return sapEntityCode;
	}

	public void setSapEntityCode(String sapEntityCode) {
		this.sapEntityCode = sapEntityCode;
	}

	public String getSapUserId() {
		return sapUserId;
	}

	public void setSapUserId(String sapUserId) {
		this.sapUserId = sapUserId;
	}

	public String getSbuCode() {
		return sbuCode;
	}

	public void setSbuCode(String sbuCode) {
		this.sbuCode = sbuCode;
	}

	public String getFuncReportg() {
		return funcReportg;
	}

	public void setFuncReportg(String funcReportg) {
		this.funcReportg = funcReportg;
	}

	public List<SapAmUserRequestApprovalVO> getRoles() {
		return roles;
	}

	public void setRoles(List<SapAmUserRequestApprovalVO> roles) {
		this.roles = roles;
	}

	public String getIsHodApprover() {
		return isHodApprover;
	}

	public void setIsHodApprover(String isHodApprover) {
		this.isHodApprover = isHodApprover;
	}

//	public void setImisLoginId(String imisLoginId) {
//		this.imisLoginId = imisLoginId;
//	}

//	public void setStatus(String status) {
//		this.status = status;
//	}
}
