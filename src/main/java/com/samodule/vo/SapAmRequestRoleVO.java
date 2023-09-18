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
public class SapAmRequestRoleVO {
	@JsonProperty("requestId")
	private BigDecimal requestId;
	@JsonProperty("surRecid")
	private Long surRecid;	
	@JsonProperty("requestType")
	private String requestType;
	@JsonProperty("roleId")
	private String roleId;
	@JsonProperty("roleCode")
	@NotBlank(message = "Select roles")
	private String roleCode;	
	@JsonProperty("reasonrole")
   // @NotBlank(message = "Enter reaseon for role access")
	private String reasonrole;
//	@JsonProperty("fortime")
//	private String fortime;
//	@JsonProperty("fromDate")
//	private String fromDate;
//	@JsonProperty("toDate")
//	private String toDate;
	// added on 19-07-2023 for get  sapUserid from view;
	private String sapUserid;
	
	@JsonProperty("requestType")
	public String getRequestType() {
		return requestType;
	}

	@JsonProperty("requestType")
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	@JsonProperty("roleId")
	public String getRoleId() {
		return roleId;
	}

	@JsonProperty("roleId")
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@JsonProperty("roleCode")
	public String getRoleCode() {
		return roleCode;
	}

	@JsonProperty("roleCode")
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
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

	public String getReasonrole() {
		return reasonrole;
	}

	public void setReasonrole(String reasonrole) {
		this.reasonrole = reasonrole;
	}

	public String getSapUserid() {
		return sapUserid;
	}

	public void setSapUserid(String sapUserid) {
		this.sapUserid = sapUserid;
	}

}