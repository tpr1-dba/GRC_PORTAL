package com.samodule.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.samodule.validator.Sex;


/**
 * The persistent class for the SAP_AM_MASTER_TYPE_DETAILS database table.
 * 
 */
@Entity
@Table(name="SAP_AM_MASTER_TYPE_DETAILS")
@NamedQuery(name="SapAmMasterTypeDetail.findAll", query="SELECT s FROM SapAmMasterTypeDetail s")
public class SapAmMasterTypeDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GenericGenerator(name="SAP_MASTER_TYPES_DETAILS_SEQ" ,parameters={ @org.hibernate.annotations.Parameter(name = "sequence", value = "SAP_MASTER_TYPES_DETAILS_SEQ")},strategy="sequence")
//	@GeneratedValue(generator="SAP_MASTER_TYPES_DETAILS_SEQ",strategy=GenerationType.SEQUENCE)
	@GenericGenerator(name = "samtdRecid", strategy = "com.samodule.util.CustomIdGenerator")
	@GeneratedValue(generator = "samtdRecid") 
	@Column(name="SAMTD_RECID", unique=true, nullable=false, precision=16)
	private long samtdRecid;

	@Column(name="CREATED_BY", nullable=false, length=10)
	private String createdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="CREATED_ON", nullable=false)
	private Date createdOn;
	@NotNull (message = "description cannot be empty")
	@Length (min = 6, max = 255, message = "description length must be 6-255 character")
	@Column(nullable=false, length=255)
	private String description;

	@Column(name="LAST_UPD_BY", length=10)
	private String lastUpdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPD_ON")
	private Date lastUpdOn;
	@NotNull (message = "Master Id cannot be empty")
	@Length (min = 1, max = 5, message = "Master Id length must be 2-5 character")
	@Column(name="MASTER_ID", nullable=false, length=5)
	private String masterId;
	@NotNull (message = "Master Id code cannot be empty")
	@Length (min = 2, max = 50, message = "Master Id code length must be 2-50 character")
	@Column(name="MASTER_ID_CODE", nullable=false, length=50)
	private String masterIdCode;
	
	@NotNull (message = "Select status")
	@Sex
	@Column(nullable=false, length=1)
	private String status;

	public SapAmMasterTypeDetail() {
	}

	public long getSamtdRecid() {
		return this.samtdRecid;
	}

	public void setSamtdRecid(long samtdRecid) {
		this.samtdRecid = samtdRecid;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getMasterId() {
		return this.masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String getMasterIdCode() {
		return this.masterIdCode;
	}

	public void setMasterIdCode(String masterIdCode) {
		this.masterIdCode = masterIdCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}