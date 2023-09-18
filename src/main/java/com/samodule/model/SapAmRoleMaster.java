package com.samodule.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the SAP_AM_ROLE_MASTER database table.
 * 
 */
@Entity
@Table(name="SAP_AM_ROLE_MASTER")
@NamedQuery(name="SapAmRoleMaster.findAll", query="SELECT s FROM SapAmRoleMaster s")
public class SapAmRoleMaster implements com.samodule.util.Entity<Serializable> {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="SAP_AM_ROLE_MASTER_ROLEID_GENERATOR" )
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SAP_AM_ROLE_MASTER_ROLEID_GENERATOR")
	@GenericGenerator(name = "roleId", strategy = "com.samodule.util.CustomIdGenerator")
	@GeneratedValue(generator = "roleId") 
	@Column(name="ROLE_ID", unique=true, nullable=false, precision=3)
	private Long roleId;

	@Column(name="CREATED_BY", nullable=false, length=10)
	private String createdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="CREATED_ON", nullable=false)
	private Date createdOn;

	@Column(nullable=false, length=10)
	private String entity;

	@Column(name="LAST_UPD_BY", length=10)
	private String lastUpdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPD_ON")
	private Date lastUpdOn;

	@Column(name="\"MODULE\"", nullable=false, length=10)
	private String module;

	@Column(name="ROLE_CODE", nullable=false, length=50)
	private String roleCode;

	@Column(name="ROLE_NAME", nullable=false, length=150)
	private String roleName;

	@Column(nullable=false, length=1)
	private String status;
	
	@Column(name="TCODE", nullable=true, length=30)
	private String tcode;
	
	@Transient
	private String funCode  ;
	@Transient
	private String funDesc;
	@Column(name="ROLE_TYPE",  length=1)
	private String roleType;
	
	@Column(name="PARENT_ROLE_ID")
	private Long parentRoleId;
	
	@Column(name="FUN_ID", unique=true, nullable=false, precision=16)
	private Long funId;
	

	public SapAmRoleMaster() {
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getEntity() {
		return this.entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdOn() {
		return this.lastUpdOn;
	}

	public void setLastUpdOn(Date lastUpdOn) {
		this.lastUpdOn = lastUpdOn;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTcode() {
		return tcode;
	}

	public void setTcode(String tcode) {
		this.tcode = tcode;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}



	public Long getParentRoleId() {
		return parentRoleId;
	}

	public String getFunCode() {
		return funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}

	public void setParentRoleId(Long parentRoleId) {
		this.parentRoleId = parentRoleId;
	}
	public Long getFunId() {
		return funId;
	}

	public void setFunId(Long funId) {
		this.funId = funId;
	}

	@Override
	public Serializable getId() {
		// TODO Auto-generated method stub
		System.out.println("getId(roleId)");
		return roleId;
	}

	@Override
	public void setId(Serializable id) {
		// TODO Auto-generated method stub
		System.out.println("setId(Serializable id)");
		roleId=(Long) id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		System.out.println("setId(String id)");
		roleId= Long.parseLong(id);
	}

	public String getFunDesc() {
		return funDesc;
	}

	public void setFunDesc(String funDesc) {
		this.funDesc = funDesc;
	}


}