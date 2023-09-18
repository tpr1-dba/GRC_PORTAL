package com.samodule.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SAP_AM_ROLE_EMP_MAPPING database table.
 * 
 */
@Entity
@Table(name="SAP_AM_ROLE_EMP_MAPPING")
@NamedQuery(name="SapAmRoleEmpMapping.findAll", query="SELECT s FROM SapAmRoleEmpMapping s")
public class SapAmRoleEmpMapping implements Serializable {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((parentRoleId == null) ? 0 : parentRoleId.hashCode());
		result = prime * result
				+ ((sapEmpCode == null) ? 0 : sapEmpCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SapAmRoleEmpMapping other = (SapAmRoleEmpMapping) obj;
		if (parentRoleId == null) {
			if (other.parentRoleId != null)
				return false;
		} else if (!parentRoleId.equals(other.parentRoleId))
			return false;
		if (sapEmpCode == null) {
			if (other.sapEmpCode != null)
				return false;
		} else if (!sapEmpCode.equals(other.sapEmpCode))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="SAP_AM_ROLE_EMP_MAPPING_SAREMID_GENERATOR" )
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SAP_AM_ROLE_EMP_MAPPING_SAREMID_GENERATOR")
	@GenericGenerator(name="SAP_MASTER_TYPES_SEQ" ,parameters={ @org.hibernate.annotations.Parameter(name = "sequence", value = "SAP_MASTER_TYPES_SEQ")},strategy="sequence")
	@GeneratedValue(generator="SAP_MASTER_TYPES_SEQ",strategy=GenerationType.SEQUENCE)
	@Column(name="SAREM_ID", unique=true, nullable=false, precision=16)
	private long saremId;

	@Column(name="CREATED_BY", nullable=false, length=10)
	private String createdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="CREATED_ON", nullable=false)
	private Date createdOn;

	@Column(name="EMP_CODE", nullable=false, length=10)
	private String empCode;

	@Column(name="LAST_UPD_BY", length=10)
	private String lastUpdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPD_ON")
	private Date lastUpdOn;

	@Column(name="ROLE_ID", nullable=false, precision=16)
	private BigDecimal parentRoleId;

	@Column(name="ROLE_CODE", nullable=false, length=30)
	@Transient
	private String roleCode;

	@Column(name="SAP_EMP_CODE", nullable=false, length=30)
	private String sapEmpCode;

	@Column(nullable=false, length=1)
	private String status;

	@Column(nullable=false, length=10)
	private String entity;
	@Column(name="FUN_CODE", nullable=false, length=20)
	private String funCode  ;
	@Column(name="FUN_ID", unique=true, nullable=false, precision=16)
	private Long funId;
	@Column(name="ROLE_TYPE",  length=2)
	private String roleType;
	
	
	public SapAmRoleEmpMapping() {
	}

	public long getSaremId() {
		return this.saremId;
	}

	public void setSaremId(long saremId) {
		this.saremId = saremId;
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

	public String getEmpCode() {
		return this.empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
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

	public BigDecimal getParentRoleId() {
		return this.parentRoleId;
	}

	public void setParentRoleId(BigDecimal parentRoleId) {
		this.parentRoleId = parentRoleId;
	}

//	public String getRoleCode() {
//		return this.roleCode;
//	}
//
//	public void setRoleCode(String roleCode) {
//		this.roleCode = roleCode;
//	}

	public String getSapEmpCode() {
		return this.sapEmpCode;
	}

	public void setSapEmpCode(String sapEmpCode) {
		this.sapEmpCode = sapEmpCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getFunCode() {
		return funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}

	public Long getFunId() {
		return funId;
	}

	public void setFunId(Long funId) {
		this.funId = funId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}