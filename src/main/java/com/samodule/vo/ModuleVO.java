package com.samodule.vo;

public class ModuleVO {
	
	private Long samtdRecid;
	private String masterIdCode;
	private String description;
	public String getMasterIdCode() {
		return masterIdCode;
	}
	public void setMasterIdCode(String masterIdCode) {
		this.masterIdCode = masterIdCode;
	}
	public Long getSamtdRecid() {
		return samtdRecid;
	}
	public void setSamtdRecid(Long samtdRecid) {
		this.samtdRecid = samtdRecid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
