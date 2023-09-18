package com.samodule.vo;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.samodule.validator.NotNullIfAnotherFieldHasValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"requestId", "tcodeId", "tcode", "requestType", "sapCompanyCode", "sapModuleCode", "plant",
		"purchaseGroup", "roleId", "roleCode", "refSapUserId", "refEmpCode", "empreason","fortime", "fromDate", "toDate" })
@NotNullIfAnotherFieldHasValue.List({
	@NotNullIfAnotherFieldHasValue(fieldName = "fortime", fieldValue = "temporary", dependFieldName = "fromDate"), 
	@NotNullIfAnotherFieldHasValue(fieldName = "fortime", fieldValue = "temporary", dependFieldName = "toDate")})
public class SapAmRequestEmpRefVO {
	@JsonProperty("requestId")
	private String requestId;	
	@JsonProperty("requestType")
	private String requestType;
	@NotBlank(message = "Select sap company")
	@JsonProperty("sapCompanyCode")
	private String sapCompanyCode;	
	@JsonProperty("refSapUserId")	
	private String refSapUserId;
	@JsonProperty("refEmpCode")
	private String refEmpCode;	
	@JsonProperty("reason")
    @NotBlank(message = "Enter reaseon for access")
	private String reason;
	@JsonProperty("fortime")
	private String fortime;
	@JsonProperty("fromDate")
	private String fromDate;
	@JsonProperty("toDate")
	private String toDate;
	@JsonProperty("requestId")
	public String getRequestId() {
	return requestId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	@JsonProperty("requestId")
	public void setRequestId(String requestId) {
	this.requestId = requestId;
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

	
	
	@JsonProperty("refSapUserId")
	public String getRefSapUserId() {
		return refSapUserId;
	}

	@JsonProperty("refSapUserId")
	public void setRefSapUserId(String refSapUserId) {
		this.refSapUserId = refSapUserId;
	}

	@JsonProperty("refEmpCode")
	public String getRefEmpCode() {
		return refEmpCode;
	}

	@JsonProperty("refEmpCode")
	public void setRefEmpCode(String refEmpCode) {
		this.refEmpCode = refEmpCode;
	}



	@JsonProperty("fromDate")
	public String getFromDate() {
		return fromDate;
	}

	@JsonProperty("fromDate")
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	@JsonProperty("toDate")
	public String getToDate() {
		return toDate;
	}

	@JsonProperty("toDate")
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	@JsonProperty("fortime")
	public String getFortime() {
	return fortime;
	}

	@JsonProperty("fortime")
	public void setFortime(String fortime) {
	this.fortime = fortime;
	}

}