package com.samodule.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SAP_AM_TCODES database table.
 * 
 */
@Entity
@Table(name="SAP_AM_TCODES")
@NamedQuery(name="SapAmTcode.findAll", query="SELECT s FROM SapAmTcode s")
public class SapAmTcode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="SAP_AM_TCODES_SATID_GENERATOR" )
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SAP_AM_TCODES_SATID_GENERATOR")
	@GenericGenerator(name = "satId", strategy = "com.samodule.util.CustomIdGenerator")
	@GeneratedValue(generator = "satId")
	@Column(name="SAT_ID", unique=true, nullable=false, precision=16)
	private Long satId;

	@Column(name="CREATED_BY", nullable=false, length=10)
	private String createdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="CREATED_ON", nullable=false)
	private Date createdOn;

	@Column(name="FUN_ID")
	private Long funId;

	@Column(length=255)
	private String functionality;

	@Column(name="LAST_UPD_BY", length=10)
	private String lastUpdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPD_ON")
	private Date lastUpdOn;

	@Column(name="MODULE", nullable=false, length=30)
	private String module;

	@Column(nullable=false, length=1)
	private String standard;

	@Column(nullable=false, length=1)
	private String status;

	@Column(nullable=false, length=10)
	private String stdroles;

	@Column(nullable=false, length=20)
	private String tcode;
	
	@Column(nullable=false, length=1)
	private String sensitive;

	@Column(name="TCODE_DESC", nullable=false, length=100)
	private String tcodeDesc;
	
	public SapAmTcode() {
	}

	public Long getSatId() {
		return this.satId;
	}

	public void setSatId(Long satId) {
		this.satId = satId;
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

	public Long getFunId() {
		return this.funId;
	}

	public void setFunId(Long funId) {
		this.funId = funId;
	}

	public String getFunctionality() {
		return this.functionality;
	}

	public void setFunctionality(String functionality) {
		this.functionality = functionality;
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

	public String getStandard() {
		return this.standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStdroles() {
		return this.stdroles;
	}

	public void setStdroles(String stdroles) {
		this.stdroles = stdroles;
	}

	public String getTcode() {
		return this.tcode;
	}

	public void setTcode(String tcode) {
		this.tcode = tcode;
	}

	public String getTcodeDesc() {
		return this.tcodeDesc;
	}

	public void setTcodeDesc(String tcodeDesc) {
		this.tcodeDesc = tcodeDesc;
	}

	public String getSensitive() {
		return sensitive;
	}

	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}

}