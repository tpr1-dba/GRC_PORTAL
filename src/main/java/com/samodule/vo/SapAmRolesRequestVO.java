
package com.samodule.vo;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({ "hrisCode", "surtRecid", "surRecid", "requestId" })

public class SapAmRolesRequestVO {

	private String hrisCode;
	private List<Long> surtRecids;
	private BigDecimal surRecid;
	private BigDecimal requestId;
	private long surtRecid;
	private long surpRecid;
	private Long tcodeId;
	private String tcode;
	private String plant;
	private Long funId;
	private String funCode;
	private Long roleId;
	private String entity;
	private String roleCode;
	private String roleName;
	private String module;
	private String roleType;
	private String sensitive;
	private String isConflicted;
	private String roleAssined;
	
	public String getRoleAssined() {
		return roleAssined;
	}

	public void setRoleAssined(String roleAssined) {
		this.roleAssined = roleAssined;
	}

	private int confilctIncrese;

	public List<Long> getSurtRecids() {
		return surtRecids;
	}

	public void setSurtRecids(List<Long> surtRecids) {
		this.surtRecids = surtRecids;
	}

	public long getSurtRecid() {
		return surtRecid;
	}

	public void setSurtRecid(long surtRecid) {
		this.surtRecid = surtRecid;
	}

	public Long getTcodeId() {
		return tcodeId;
	}

	public void setTcodeId(Long tcodeId) {
		this.tcodeId = tcodeId;
	}

	public String getTcode() {
		return tcode;
	}

	public void setTcode(String tcode) {
		this.tcode = tcode;
	}

	public Long getFunId() {
		return funId;
	}

	public void setFunId(Long funId) {
		this.funId = funId;
	}

	public String getFunCode() {
		return funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
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

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@JsonProperty("hrisCode")
	public String getHrisCode() {
		return hrisCode;
	}

	@JsonProperty("hrisCode")
	public void setHrisCode(String hrisCode) {
		this.hrisCode = hrisCode;
	}

	@JsonProperty("surRecid")
	public BigDecimal getSurRecid() {
		return surRecid;
	}

	@JsonProperty("surRecid")
	public void setSurRecid(BigDecimal surRecid) {
		this.surRecid = surRecid;
	}

	@JsonProperty("requestId")
	public BigDecimal getRequestId() {
		return requestId;
	}
	
	@JsonProperty("requestId")
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public String getPlant() {
		return plant;
	}

	public int getConfilctIncrese() {
		return confilctIncrese;
	}

	public void setConfilctIncrese(int confilctIncrese) {
		this.confilctIncrese = confilctIncrese;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getIsConflicted() {
		return isConflicted;
	}

	public void setIsConflicted(String isConflicted) {
		this.isConflicted = isConflicted;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(SapAmRolesRequestVO.class.getName()).append('@')
				.append(Integer.toHexString(System.identityHashCode(this))).append('[');
		sb.append("surRecid");
		sb.append('=');
		sb.append(((this.surRecid == null) ? "<null>" : this.surRecid));
		sb.append(',');
		sb.append("requestId");
		sb.append('=');
		sb.append(((this.requestId == null) ? "<null>" : this.requestId));
		sb.append(',');
		sb.append("surtRecid");
		sb.append('=');
		sb.append(((this.surtRecid == 0) ? 0 : this.surtRecid));
		sb.append(',');
		sb.append("tcodeId");
		sb.append('=');
		sb.append(((this.tcodeId == null) ? "<null>" : this.tcodeId));
		sb.append(',');
		sb.append("tcode");
		sb.append('=');
		sb.append(((this.tcode == null) ? "<null>" : this.tcode));
		sb.append(',');
		sb.append("plant");
		sb.append('=');
		sb.append(((this.plant == null) ? "<null>" : this.plant));
		sb.append(',');
		sb.append("roleId");
		sb.append('=');
		sb.append(((this.roleId == null) ? "<null>" : this.roleId));
		sb.append(',');
		sb.append("entity");
		sb.append('=');
		sb.append(((this.entity == null) ? "<null>" : this.entity));
		sb.append(',');
		sb.append("roleCode");
		sb.append('=');
		sb.append(((this.roleCode == null) ? "<null>" : this.roleCode));
		sb.append(',');
		sb.append("roleName");
		sb.append('=');
		sb.append(((this.roleName == null) ? "<null>" : this.roleName));
		sb.append(',');
		sb.append("roleType");
		sb.append('=');
		sb.append(((this.roleType == null) ? "<null>" : this.roleType));
		sb.append(',');
		sb.append("isConflicted");
		sb.append('=');
		sb.append(((this.isConflicted == null) ? "<null>" : this.isConflicted));
		sb.append(',');
		if (sb.charAt((sb.length() - 1)) == ',') {
			sb.setCharAt((sb.length() - 1), ']');
		} else {
			sb.append(']');
		}
		return sb.toString();
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public long getSurpRecid() {
		return surpRecid;
	}

	public void setSurpRecid(long surpRecid) {
		this.surpRecid = surpRecid;
	}

	public String getSensitive() {
		return sensitive;
	}

	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}
}