package com.samodule.vo;

import java.util.List;

public class SapUserVO {
	private String sapUserid;
	private String hrisCode;
	private String employeeName;
	private String sapEmpCode;
	private String department;
	private String designation;
	private String location;
//	private List<RoleMasterVO> roleMasters;
	private String roleId;
	private String parentRoleId;
	private String roleCode;
	private String roleName;
	private String funId;
	private String funCode;
	private String company;
	private String entity;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getFunId() {
		return funId;
	}

	public void setFunId(String funId) {
		this.funId = funId;
	}

	public String getFunCode() {
		return funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}

	public String getSapUserid() {
		return sapUserid;
	}

	public void setSapUserid(String sapUserid) {
		this.sapUserid = sapUserid;
	}

	public String getHrisCode() {
		return hrisCode;
	}

	public void setHrisCode(String hrisCode) {
		this.hrisCode = hrisCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getSapEmpCode() {
		return sapEmpCode;
	}

	public void setSapEmpCode(String sapEmpCode) {
		this.sapEmpCode = sapEmpCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

//	public List<RoleMasterVO> getRoleMasters() {
//		return roleMasters;
//	}
//
//	public void setRoleMasters(List<RoleMasterVO> roleMasters) {
//		this.roleMasters = roleMasters;
//	}

	public String getRoleId() {
		return roleId;
	}

	public String getParentRoleId() {
		return parentRoleId;
	}

	public void setParentRoleId(String parentRoleId) {
		this.parentRoleId = parentRoleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "SapUserVO [sapUserid=" + sapUserid + ", hrisCode=" + hrisCode + ", employeeName=" + employeeName
				+ ", sapEmpCode=" + sapEmpCode + ", department=" + department + ", designation=" + designation
				+ ", location=" + location + ", roleMasters=" + null + ", roleId=" + roleId + ", roleCode="
				+ roleCode + ", roleName=" + roleName + ", funId=" + funId + ", funCode=" + funCode + "]";
	}

//	public List<SapAmRoleEmpMapping> getRoleEmpMappings() {
//		return roleEmpMappings;
//	}
//
//	public void setRoleEmpMappings(List<SapAmRoleEmpMapping> roleEmpMappings) {
//		this.roleEmpMappings = roleEmpMappings;
//	}

}
