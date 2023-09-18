package com.samodule.vo;

import java.util.Map;

public class SapAmTcodeVO {
	@Override
	public String toString() {
		return "SapAmTcodeVO [satId=" + satId + ", funId=" + funId + ", roleId=" + roleId + ", roleCode=" + roleCode
				+ ", tcode=" + tcode + ", tcodeDesc=" + tcodeDesc + ", stdroles=" + stdroles + ", funCode=" + funCode
				+ ", module=" + module + ", functios=" + functios + "]";
	}

	//{"roleId":101232,"roleCode":"ZC_FI_APREPORT_DISP_D_2903","tcode":"FBL1N"}
	private Long satId;
	private Long funId;
	private Long roleId;
	private String roleCode;
	private String tcode;
	private String tcodeDesc;
	private String stdroles;
	private String funCode;
	private String module;
	private String sensitive;
	private Map<Long, String> functios;

	public Long getSatId() {
		return satId;
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

	public Map<Long, String> getFunctios() {
		return functios;
	}

	public void setFunctios(Map<Long, String> functios) {
		this.functios = functios;
	}

	public void setSatId(Long satId) {
		this.satId = satId;
	}

//	public Long getFunId() {
//		return funId;
//	}
//
//	public void setFunId(Long funId) {
//		this.funId = funId;
//	}

	public String getTcode() {
		return tcode;
	}

	public void setTcode(String tcode) {
		this.tcode = tcode;
	}

	public String getTcodeDesc() {
		return tcodeDesc;
	}

	public void setTcodeDesc(String tcodeDesc) {
		this.tcodeDesc = tcodeDesc;
	}

//	public String getFunCode() {
//		return funCode;
//	}
//
//	public void setFunCode(String funCode) {
//		this.funCode = funCode;
//	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
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

	public String getStdroles() {
		return stdroles;
	}

	public void setStdroles(String stdroles) {
		this.stdroles = stdroles;
	}

	public String getSensitive() {
		return sensitive;
	}

	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}
}
