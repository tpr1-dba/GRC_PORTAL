
package com.samodule.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tcodeId",
    "tcode",
    "module",
    "sapCompanyCode",
    "requestId",
    "surRecid",
    "tcodeDesc",
    "surtRecid",
    "status",
    "roleId",
    "roleCode",
    "roleName"
})

public class UserRequestDTO {
	private long surtRecid;
    @JsonProperty("tcodeId")
    private long tcodeId;   
    @JsonProperty("tcode")   
    private String tcode;
    @JsonProperty("tcodeDesc")   
    private String tcodeDesc; 
	@JsonProperty("module")
    private String module;    
    @JsonProperty("sapCompanyCode")
    private String sapCompanyCode;
    @JsonProperty("surRecid")
    private long surRecid;
    @JsonProperty("requestId")
    private BigDecimal requestId;
    @JsonProperty("status")
    private String status;    
    @JsonProperty("roleId")
    private Long roleId;   
    @JsonProperty("roleCode")   
    private String roleCode;
    private String requestType;
    @JsonProperty("roleName")   
    private String roleName; 
    private String funId; 
    private long surpRecid;
    private String plant;
    private String reason;
    @JsonProperty("empRoleId")
    private Long empRoleId;   
    @JsonProperty("empRoleCode")   
    private String empRoleCode;
    private String sensitive;
    @JsonProperty("sapUserId")   
    private String sapUserId;
    public Long getEmpRoleId() {
		return empRoleId;
	}

	public void setEmpRoleId(Long empRoleId) {
		this.empRoleId = empRoleId;
	}

	public String getEmpRoleCode() {
		return empRoleCode;
	}

	public void setEmpRoleCode(String empRoleCode) {
		this.empRoleCode = empRoleCode;
	}

	@JsonProperty("module")
    public String getModule() {
        return module;
    }

    @JsonProperty("module")
    public void setModule(String module) {
        this.module = module;
    }

    @JsonProperty("sapCompanyCode")
    public String getSapCompanyCode() {
        return sapCompanyCode;
    }

    @JsonProperty("sapCompanyCode")
    public void setSapCompanyCode(String sapCompanyCode) {
        this.sapCompanyCode = sapCompanyCode;
    }

    public String getTcode() {
		return tcode;
	}

	public void setTcode(String tcode) {
		this.tcode = tcode;
	}

	public long getSurtRecid() {
		return surtRecid;
	}

	public void setSurtRecid(long surtRecid) {
		this.surtRecid = surtRecid;
	}

	public long getTcodeId() {
		return tcodeId;
	}

	public void setTcodeId(long tcodeId) {
		this.tcodeId = tcodeId;
	}

	public String getTcodeDesc() {
		return tcodeDesc;
	}

	public void setTcodeDesc(String tcodeDesc) {
		this.tcodeDesc = tcodeDesc;
	}

	public long getSurRecid() {
		return surRecid;
	}

	public void setSurRecid(long surRecid) {
		this.surRecid = surRecid;
	}

	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public long getSurpRecid() {
		return surpRecid;
	}

	public String getSapUserId() {
		return sapUserId;
	}

	public void setSapUserId(String sapUserId) {
		this.sapUserId = sapUserId;
	}

	public void setSurpRecid(long surpRecid) {
		this.surpRecid = surpRecid;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getFunId() {
		return funId;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public void setFunId(String funId) {
		this.funId = funId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSensitive() {
		return sensitive;
	}

	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}

}
