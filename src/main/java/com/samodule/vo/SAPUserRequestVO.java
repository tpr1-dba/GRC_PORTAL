package com.samodule.vo;

import java.math.BigDecimal;

public class SAPUserRequestVO {
	private String swmRecid;
	private String sapUserid;
	private String hrisCode;
	private String employeeName;
	private String sapEmpCode;
	private String department;
	private String designation;
	private String location;
	private String company;
	private String entity;
	private BigDecimal requestId;
	private Long surRecid;
	private String requestType;
	private String sapCompanyCode;
	private String sapModuleCode;
	private String reason;
	private String appliedOn;
	public String getAppliedOn() {
		return appliedOn;
	}

	public void setAppliedOn(String appliedOn) {
		this.appliedOn = appliedOn;
	}

	private String wfLevel;
	public String getWfLevel() {
		return wfLevel;
	}

	public void setWfLevel(String wfLevel) {
		this.wfLevel = wfLevel;
	}

	private String status;

	public String getSapUserid() {
		return sapUserid;
	}

	public void setSapUserid(String sapUserid) {
		this.sapUserid = sapUserid;
	}

	public String getHrisCode() {
		return hrisCode;
	}

	public void setHrisCode(String hrisCode) {
		this.hrisCode = hrisCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getSapEmpCode() {
		return sapEmpCode;
	}

	public void setSapEmpCode(String sapEmpCode) {
		this.sapEmpCode = sapEmpCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public Long getSurRecid() {
		return surRecid;
	}

	public void setSurRecid(Long surRecid) {
		this.surRecid = surRecid;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getSapCompanyCode() {
		return sapCompanyCode;
	}

	public void setSapCompanyCode(String sapCompanyCode) {
		this.sapCompanyCode = sapCompanyCode;
	}

	public String getSapModuleCode() {
		return sapModuleCode;
	}

	public void setSapModuleCode(String sapModuleCode) {
		this.sapModuleCode = sapModuleCode;
	}

	public String getReason() {
		return reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSwmRecid() {
		return swmRecid;
	}

	public void setSwmRecid(String swmRecid) {
		this.swmRecid = swmRecid;
	}

}
