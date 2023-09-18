package com.samodule.vo;

import javax.validation.constraints.NotNull;

public class ReportRequestDto {
	
	@NotNull(message = "Not Blank")
	private String fromDate;
	@NotNull(message = "Not Blank")
	private String toDate;
	private String sapCompCode;
	private String module;
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSapCompCode() {
		return sapCompCode;
	}
	public void setSapCompCode(String sapCompCode) {
		this.sapCompCode = sapCompCode;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
}
