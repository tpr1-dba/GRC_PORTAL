package com.samodule.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SAP_AM_ROLE_TCODE_MAPPING database table.
 * 
 */
@Entity
@Table(name="SAP_AM_ROLE_TCODE_MAPPING")
@NamedQuery(name="SapAmRoleTcodeMapping.findAll", query="SELECT s FROM SapAmRoleTcodeMapping s")
public class SapAmRoleTcodeMapping implements Serializable {
	public SapAmRoleTcodeMapping() {}
	public SapAmRoleTcodeMapping(String tcode,BigDecimal parentTcodeRecid,BigDecimal parentRoleId) {
		super();
		this.parentRoleId = parentRoleId;
		this.parentTcodeRecid = parentTcodeRecid;
		this.tcode = tcode;
	}

	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="SAP_AM_ROLE_TCODE_MAPPING_SARTMID_GENERATOR" )
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SAP_AM_ROLE_TCODE_MAPPING_SARTMID_GENERATOR")
	
//	@GenericGenerator(name = "sartmId", strategy = "com.samodule.util.CustomIdGenerator")
//	@GeneratedValue(generator = "sartmId")
	@GenericGenerator(name="SAP_MASTER_TYPES_SEQ" ,parameters={ @org.hibernate.annotations.Parameter(name = "sequence", value = "SAP_MASTER_TYPES_SEQ")},strategy="sequence")
	@GeneratedValue(generator="SAP_MASTER_TYPES_SEQ",strategy=GenerationType.SEQUENCE)
	@Column(name="SARTM_ID", unique=true, nullable=false, precision=16)
	private long sartmId;

	@Column(name="CREATED_BY", nullable=false, length=10)
	private String createdBy;


	@Column(name="CREATED_ON", nullable=false)
	private Date createdOn;

	@Column(name="LAST_UPD_BY", length=10)
	private String lastUpdBy;

	
	@Column(name="LAST_UPD_ON")
	private Date lastUpdOn;

	@Column(name="PARENT_ROLE_ID", nullable=false, precision=16)
	private BigDecimal parentRoleId;

	@Column(name="PARENT_TCODE_RECID", nullable=false, precision=16)
	private BigDecimal parentTcodeRecid;

	/*@Column(name="ROLE_CODE", nullable=false, length=50)
	private String roleCode;*/

	@Column(nullable=false, length=1)
	private String status;

	@Column(nullable=false, length=30)
	private String tcode;

//	public SapAmRoleTcodeMapping() {
//	}

	public long getSartmId() {
		return this.sartmId;
	}

	public void setSartmId(long sartmId) {
		this.sartmId = sartmId;
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

	public BigDecimal getParentTcodeRecid() {
		return this.parentTcodeRecid;
	}

	public void setParentTcodeRecid(BigDecimal parentTcodeRecid) {
		this.parentTcodeRecid = parentTcodeRecid;
	}

	/*public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}*/

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTcode() {
		return this.tcode;
	}

	public void setTcode(String tcode) {
		this.tcode = tcode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((parentRoleId == null) ? 0 : parentRoleId.hashCode());
		result = prime
				* result
				+ ((parentTcodeRecid == null) ? 0 : parentTcodeRecid.hashCode());
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
		SapAmRoleTcodeMapping other = (SapAmRoleTcodeMapping) obj;
		if (parentRoleId == null) {
			if (other.parentRoleId != null)
				return false;
		} else if (!parentRoleId.equals(other.parentRoleId))
			return false;
		if (parentTcodeRecid == null) {
			if (other.parentTcodeRecid != null)
				return false;
		} else if (!parentTcodeRecid.equals(other.parentTcodeRecid))
			return false;
		return true;
	}

}