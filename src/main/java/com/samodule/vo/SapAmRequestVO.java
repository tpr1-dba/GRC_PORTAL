package com.samodule.vo;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.samodule.validator.NotNullIfAnotherFieldHasValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"requestId", "tcodeId", "tcode", "requestType", "sapCompanyCode", "sapModuleCode", "plant",
		"purchaseGroup", "roleId", "roleCode", "refSapUserId", "refEmpCode", "reason","fortime", "fromDate", "toDate" })
//@NotNullIfAnotherFieldHasValue.List({
//	@NotNullIfAnotherFieldHasValue(fieldName = "fortime", fieldValue = "temporary", dependFieldName = "fromDate"), 
//	@NotNullIfAnotherFieldHasValue(fieldName = "fortime", fieldValue = "temporary", dependFieldName = "toDate")})
public class SapAmRequestVO {
	@JsonProperty("requestId")
	private BigDecimal requestId;
	private Long surRecid;
	@JsonProperty("tcodeId")
	private String tcodeId;
	//@NotBlank(message = "Select tcode")
	@JsonProperty("tcode")
	private String tcode;
	@JsonProperty("requestType")
	private String requestType;
	@NotBlank(message = "Select sap company code")
	@JsonProperty("sapCompanyCode")
	private String sapCompanyCode;
	@JsonProperty("sapModuleCode")
	private String sapModuleCode;
	//@NotBlank(message = "Select plant")
	@JsonProperty("plant")
	private String plant;
	@JsonProperty("purchaseGroup")
	private String purchaseGroup;	
	@JsonProperty("reason")
	//@NotBlank(message = "Enter reaseon for t-code access")
	private String reason;
//	@JsonProperty("fortime")
//	private String fortime;
//	@JsonProperty("fromDate")
//	private String fromDate;
//	@JsonProperty("toDate")
//	private String toDate;
	
	// added on 19-07-2023 for get  sapUserid from view;
	private String sapUserid;
	
	@JsonProperty("requestId")
	public BigDecimal getRequestId() {
	return requestId;
	}

	@JsonProperty("requestId")
	public void setRequestId(BigDecimal requestId) {
	this.requestId = requestId;
	}
	
	@JsonProperty("tcodeId")
	public String getTcodeId() {
		return tcodeId;
	}

	@JsonProperty("tcodeId")
	public void setTcodeId(String tcodeId) {
		this.tcodeId = tcodeId;
	}

	@JsonProperty("tcode")
	public String getTcode() {
		return tcode;
	}

	@JsonProperty("tcode")
	public void setTcode(String tcode) {
		this.tcode = tcode;
	}
	
	@JsonProperty("requestType")
	public String getRequestType() {
		return requestType;
	}

	@JsonProperty("requestType")
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	@JsonProperty("sapCompanyCode")
	public String getSapCompanyCode() {
		return sapCompanyCode;
	}

	@JsonProperty("sapCompanyCode")
	public void setSapCompanyCode(String sapCompanyCode) {
		this.sapCompanyCode = sapCompanyCode;
	}

	@JsonProperty("sapModuleCode")
	public String getSapModuleCode() {
		return sapModuleCode;
	}

	@JsonProperty("sapModuleCode")
	public void setSapModuleCode(String sapModuleCode) {
		this.sapModuleCode = sapModuleCode;
	}

	@JsonProperty("plant")
	public String getPlant() {
		return plant;
	}

	@JsonProperty("plant")
	public void setPlant(String plant) {
		this.plant = plant;
	}

	@JsonProperty("purchaseGroup")
	public String getPurchaseGroup() {
		return purchaseGroup;
	}

	@JsonProperty("purchaseGroup")
	public void setPurchaseGroup(String purchaseGroup) {
		this.purchaseGroup = purchaseGroup;
	}
	@JsonProperty("reason")
	public String getReason() {
		return reason;
	}

	@JsonProperty("reason")
	public void setReason(String reason) {
		this.reason = reason;
	}

//	@JsonProperty("fromDate")
//	public String getFromDate() {
//		return fromDate;
//	}
//
//	@JsonProperty("fromDate")
//	public void setFromDate(String fromDate) {
//		this.fromDate = fromDate;
//	}
//
//	@JsonProperty("toDate")
//	public String getToDate() {
//		return toDate;
//	}
//
//	@JsonProperty("toDate")
//	public void setToDate(String toDate) {
//		this.toDate = toDate;
//	}
//	
//	@JsonProperty("fortime")
//	public String getFortime() {
//	return fortime;
//	}
//
//	@JsonProperty("fortime")
//	public void setFortime(String fortime) {
//	this.fortime = fortime;
//	}

	public Long getSurRecid() {
		return surRecid;
	}

	public void setSurRecid(Long surRecid) {
		this.surRecid = surRecid;
	}
	public String getSapUserid() {
		return sapUserid;
	}

	public void setSapUserid(String sapUserid) {
		this.sapUserid = sapUserid;
	}

}