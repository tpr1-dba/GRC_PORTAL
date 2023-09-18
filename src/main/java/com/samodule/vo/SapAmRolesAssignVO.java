
package com.samodule.vo;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "hrisCode", "employeeName", "department", "designation", "location", "company", "entity",
		"requestId", "surRecid", "requestType", "sapCompanyCode", "sapModuleCode", "reason", "sapAmRolesRequestVO" })

public class SapAmRolesAssignVO {
	private String swmRecid;
	@JsonProperty("hrisCode")
	private String hrisCode;
	@JsonProperty("employeeName")
	private String employeeName;
	@JsonProperty("department")
	private String department;
	@JsonProperty("designation")
	private String designation;
	@JsonProperty("location")
	private String location;
	@JsonProperty("company")
	private String company;
	@JsonProperty("entity")
	private String entity;
	@JsonProperty("requestId")
	private Integer requestId;
	@JsonProperty("surRecid")
	private Integer surRecid;
	@JsonProperty("requestType")
	private String requestType;
	@JsonProperty("sapCompanyCode")
	private String sapCompanyCode;
	@JsonProperty("sapModuleCode")
	private String sapModuleCode;
	@JsonProperty("reason")
	private String reason;
	//@JsonProperty("reason")
	private String isSOD;
	private String wfCode;
	private BigDecimal wfLevel;
	private String sapUserid;
	private String status;
	@JsonProperty("sapAmRolesRequestVO")
	//@Valid
	private List<SapAmRolesRequestVO> sapAmRolesRequestVO;
	private List<SapAmRiskFunMappingVO> confilcteds;
	private String remarks;
	@JsonProperty("hrisCode")
	public String getHrisCode() {
		return hrisCode;
	}

	@JsonProperty("hrisCode")
	public void setHrisCode(String hrisCode) {
		this.hrisCode = hrisCode;
	}

	@JsonProperty("employeeName")
	public String getEmployeeName() {
		return employeeName;
	}

	@JsonProperty("employeeName")
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@JsonProperty("department")
	public String getDepartment() {
		return department;
	}

	@JsonProperty("department")
	public void setDepartment(String department) {
		this.department = department;
	}

	@JsonProperty("designation")
	public String getDesignation() {
		return designation;
	}

	@JsonProperty("designation")
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@JsonProperty("location")
	public String getLocation() {
		return location;
	}

	@JsonProperty("location")
	public void setLocation(String location) {
		this.location = location;
	}

	@JsonProperty("company")
	public String getCompany() {
		return company;
	}

	@JsonProperty("company")
	public void setCompany(String company) {
		this.company = company;
	}

	@JsonProperty("entity")
	public String getEntity() {
		return entity;
	}

	@JsonProperty("entity")
	public void setEntity(String entity) {
		this.entity = entity;
	}

	@JsonProperty("requestId")
	public Integer getRequestId() {
		return requestId;
	}

	@JsonProperty("requestId")
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	@JsonProperty("surRecid")
	public Integer getSurRecid() {
		return surRecid;
	}

	@JsonProperty("surRecid")
	public void setSurRecid(Integer surRecid) {
		this.surRecid = surRecid;
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

	@JsonProperty("reason")
	public String getReason() {
		return reason;
	}

	public String getWfCode() {
		return wfCode;
	}

	public void setWfCode(String wfCode) {
		this.wfCode = wfCode;
	}

	@JsonProperty("reason")
	public void setReason(String reason) {
		this.reason = reason;
	}

	@JsonProperty("sapAmRolesRequestVO")
	public List<SapAmRolesRequestVO> getSapAmRolesRequestVO() {
		return sapAmRolesRequestVO;
	}

	public String getIsSOD() {
		return isSOD;
	}

	public void setIsSOD(String isSOD) {
		this.isSOD = isSOD;
	}

	@JsonProperty("sapAmRolesRequestVO")
	public void setSapAmRolesRequestVO(List<SapAmRolesRequestVO> sapAmRolesRequestVO) {
		this.sapAmRolesRequestVO = sapAmRolesRequestVO;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(SapAmRolesAssignVO.class.getName()).append('@')
				.append(Integer.toHexString(System.identityHashCode(this))).append('[');
		sb.append("hrisCode");
		sb.append('=');
		sb.append(((this.hrisCode == null) ? "<null>" : this.hrisCode));
		sb.append(',');
		sb.append("employeeName");
		sb.append('=');
		sb.append(((this.employeeName == null) ? "<null>" : this.employeeName));
		sb.append(',');
		sb.append("department");
		sb.append('=');
		sb.append(((this.department == null) ? "<null>" : this.department));
		sb.append(',');
		sb.append("designation");
		sb.append('=');
		sb.append(((this.designation == null) ? "<null>" : this.designation));
		sb.append(',');
		sb.append("location");
		sb.append('=');
		sb.append(((this.location == null) ? "<null>" : this.location));
		sb.append(',');
		sb.append("company");
		sb.append('=');
		sb.append(((this.company == null) ? "<null>" : this.company));
		sb.append(',');
		sb.append("entity");
		sb.append('=');
		sb.append(((this.entity == null) ? "<null>" : this.entity));
		sb.append(',');
		sb.append("requestId");
		sb.append('=');
		sb.append(((this.requestId == null) ? "<null>" : this.requestId));
		sb.append(',');
		sb.append("surRecid");
		sb.append('=');
		sb.append(((this.surRecid == null) ? "<null>" : this.surRecid));
		sb.append(',');
		sb.append("requestType");
		sb.append('=');
		sb.append(((this.requestType == null) ? "<null>" : this.requestType));
		sb.append(',');
		sb.append("sapCompanyCode");
		sb.append('=');
		sb.append(((this.sapCompanyCode == null) ? "<null>" : this.sapCompanyCode));
		sb.append(',');
		sb.append("sapModuleCode");
		sb.append('=');
		sb.append(((this.sapModuleCode == null) ? "<null>" : this.sapModuleCode));
		sb.append(',');
		sb.append("reason");
		sb.append('=');
		sb.append(((this.reason == null) ? "<null>" : this.reason));
		sb.append(',');
		sb.append("sapAmRolesRequestVO");
		sb.append('=');
		sb.append(((this.sapAmRolesRequestVO == null) ? "<null>" : this.sapAmRolesRequestVO));
		sb.append(',');
		if (sb.charAt((sb.length() - 1)) == ',') {
			sb.setCharAt((sb.length() - 1), ']');
		} else {
			sb.append(']');
		}
		return sb.toString();
	}

	public String getSapUserid() {
		return sapUserid;
	}

	public void setSapUserid(String sapUserid) {
		this.sapUserid = sapUserid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SapAmRiskFunMappingVO> getConfilcteds() {
		return confilcteds;
	}

	public BigDecimal getWfLevel() {
		return wfLevel;
	}

	public void setWfLevel(BigDecimal wfLevel) {
		this.wfLevel = wfLevel;
	}

	public void setConfilcteds(List<SapAmRiskFunMappingVO> confilcteds) {
		this.confilcteds = confilcteds;
	}

	public String getSwmRecid() {
		return swmRecid;
	}

	public void setSwmRecid(String swmRecid) {
		this.swmRecid = swmRecid;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}