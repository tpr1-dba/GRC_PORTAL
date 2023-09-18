package com.samodule.vo;

import java.math.BigDecimal;

public class SapAmUserRequestApprovalVO {

	private String companyCode;

	private String empCode;

	private String remarks;

	private String sapModule;

	private String sbu;

	private String status;

	private String wfCode;

	private BigDecimal wfLevel;
	
	private String userCode;
	
	private BigDecimal requestNo;
	
	private BigDecimal sapAmRecId;
	
	
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSapModule() {
		return sapModule;
	}

	public void setSapModule(String sapModule) {
		this.sapModule = sapModule;
	}

	public String getSbu() {
		return sbu;
	}

	public void setSbu(String sbu) {
		this.sbu = sbu;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWfCode() {
		return wfCode;
	}

	public void setWfCode(String wfCode) {
		this.wfCode = wfCode;
	}

	public BigDecimal getWfLevel() {
		return wfLevel;
	}

	public void setWfLevel(BigDecimal wfLevel) {
		this.wfLevel = wfLevel;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public BigDecimal getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(BigDecimal requestNo) {
		this.requestNo = requestNo;
	}

	public BigDecimal getSapAmRecId() {
		return sapAmRecId;
	}

	public void setSapAmRecId(BigDecimal sapAmRecId) {
		this.sapAmRecId = sapAmRecId;
	}
	
	
}
