package com.samodule.vo;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "suarRecid", "approvedBy", "approvedOn", "createdBy", "createdOn", "isConflicted", "reason",
		"remarks", "requestId", "roleCode", "roleId", "sapCompanyCode", "sapUserId", "status", "surRecid" })
@Generated("jsonschema2pojo")
public class AssignedRolesVO {
//{"status":"A","requestId":2520231,"surRecid":1460,"swmRecid":1465}
	@JsonProperty("suarRecid")
	private Integer suarRecid;
	@JsonProperty("approvedBy")
	private String approvedBy;
	@JsonProperty("approvedOn")
	private String approvedOn;
	@JsonProperty("createdBy")
	private String createdBy;
	@JsonProperty("createdOn")
	private String createdOn;
	@JsonProperty("isConflicted")
	private String isConflicted;
	@JsonProperty("reason")
	private String reason;
	@JsonProperty("remarks")
	private String remarks;
	@JsonProperty("requestId")
	private Integer requestId;
	@JsonProperty("roleCode")
	private String roleCode;
	@JsonProperty("roleId")
	private Integer roleId;
	@JsonProperty("sapCompanyCode")
	private String sapCompanyCode;
	@JsonProperty("sapUserId")
	private String sapUserId;
	@JsonProperty("status")
	private String status;
	@JsonProperty("surRecid")
	private Integer surRecid;
	private String swmRecid;

	@JsonProperty("suarRecid")
	public Integer getSuarRecid() {
		return suarRecid;
	}

	@JsonProperty("suarRecid")
	public void setSuarRecid(Integer suarRecid) {
		this.suarRecid = suarRecid;
	}

	@JsonProperty("approvedBy")
	public String getApprovedBy() {
		return approvedBy;
	}

	@JsonProperty("approvedBy")
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@JsonProperty("approvedOn")
	public String getApprovedOn() {
		return approvedOn;
	}

	@JsonProperty("approvedOn")
	public void setApprovedOn(String approvedOn) {
		this.approvedOn = approvedOn;
	}

	@JsonProperty("createdBy")
	public String getCreatedBy() {
		return createdBy;
	}

	@JsonProperty("createdBy")
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@JsonProperty("createdOn")
	public String getCreatedOn() {
		return createdOn;
	}

	@JsonProperty("createdOn")
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@JsonProperty("isConflicted")
	public String getIsConflicted() {
		return isConflicted;
	}

	@JsonProperty("isConflicted")
	public void setIsConflicted(String isConflicted) {
		this.isConflicted = isConflicted;
	}

	@JsonProperty("reason")
	public String getReason() {
		return reason;
	}

	@JsonProperty("reason")
	public void setReason(String reason) {
		this.reason = reason;
	}

	@JsonProperty("remarks")
	public String getRemarks() {
		return remarks;
	}

	@JsonProperty("remarks")
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@JsonProperty("requestId")
	public Integer getRequestId() {
		return requestId;
	}

	@JsonProperty("requestId")
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	@JsonProperty("roleCode")
	public String getRoleCode() {
		return roleCode;
	}

	@JsonProperty("roleCode")
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@JsonProperty("roleId")
	public Integer getRoleId() {
		return roleId;
	}

	@JsonProperty("roleId")
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@JsonProperty("sapCompanyCode")
	public String getSapCompanyCode() {
		return sapCompanyCode;
	}

	@JsonProperty("sapCompanyCode")
	public void setSapCompanyCode(String sapCompanyCode) {
		this.sapCompanyCode = sapCompanyCode;
	}

	@JsonProperty("sapUserId")
	public String getSapUserId() {
		return sapUserId;
	}

	@JsonProperty("sapUserId")
	public void setSapUserId(String sapUserId) {
		this.sapUserId = sapUserId;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("surRecid")
	public Integer getSurRecid() {
		return surRecid;
	}

	@JsonProperty("surRecid")
	public void setSurRecid(Integer surRecid) {
		this.surRecid = surRecid;
	}

	public String getSwmRecid() {
		return swmRecid;
	}

	public void setSwmRecid(String swmRecid) {
		this.swmRecid = swmRecid;
	}

}