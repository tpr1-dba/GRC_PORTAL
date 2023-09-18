package com.samodule.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="HRM_EMPLOYEE ")
public class HrmLogin implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="EMP_RECID")	
	private long empRecid;
	@Column(name="EMP_CODE")	
	private String empCode;
	@Column(name="EMAIL_ID")	
	private String emailId;
	@Column(name="EMPLOYEE_NAME")	
	private String employeeName;
	@Column(name="EUIN_CODE")	
	private String euinCode;
	@Column(name="STATUS")
	private String status;
	@Column(name="COMPANY")
	private String company;
	@Column(name="IMIS_LOGIN_ID")
	private String imisLoginId;
	
	public long getEmpRecid() {
		return empRecid;
	}
	public void setEmpRecid(long empRecid) {
		this.empRecid = empRecid;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEuinCode() {
		return euinCode;
	}
	public void setEuinCode(String euinCode) {
		this.euinCode = euinCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
//	@Transient
//	private Set<Role> roles;
	
	@Transient
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getImisLoginId() {
		return imisLoginId;
	}
	public void setImisLoginId(String imisLoginId) {
		this.imisLoginId = imisLoginId;
	}
	
	
}
