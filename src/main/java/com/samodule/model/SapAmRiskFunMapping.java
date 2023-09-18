package com.samodule.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the SAP_AM_RISK_FUN_MAPPING database table.
 * 
 */
@Entity
@Table(name="SAP_AM_RISK_FUN_MAPPING")
@NamedQuery(name="SapAmRiskFunMapping.findAll", query="SELECT s FROM SapAmRiskFunMapping s")
public class SapAmRiskFunMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GenericGenerator(name = "rfId", strategy = "com.samodule.util.CustomIdGenerator")
//	@GeneratedValue(generator = "rfId")
	@Column(name="RF_ID", unique=true, nullable=false, precision=16)
	private Long rfId;

	@Column(name="CREATED_BY", nullable=false, length=10)
	private String createdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="CREATED_ON", nullable=false)
	private Date createdOn;

	@Column(nullable=false, length=5)
	private String entity;

	@Column(name="FR_DESC", nullable=false, length=255)
	private String frDesc;

	@Column(name="FUN_ID", nullable=false, precision=16)
	private Long funId;

	@Column(name="LAST_UPD_BY", length=10)
	private String lastUpdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPD_ON")
	private Date lastUpdOn;

	@Column(name="\"MODULE\"", nullable=false, length=30)
	private String module;

	@Column(name="RISK_ID", nullable=false, precision=16)
	private Long riskId;

	@Column(nullable=false, length=1)
	private String status;

	public SapAmRiskFunMapping() {
	}

	public Long getRfId() {
		return this.rfId;
	}

	public void setRfId(Long rfId) {
		this.rfId = rfId;
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

	public String getFrDesc() {
		return this.frDesc;
	}

	public void setFrDesc(String frDesc) {
		this.frDesc = frDesc;
	}

	public Long getFunId() {
		return this.funId;
	}

	public void setFunId(Long funId) {
		this.funId = funId;
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

	public Long getRiskId() {
		return this.riskId;
	}

	public void setRiskId(Long riskId) {
		this.riskId = riskId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}