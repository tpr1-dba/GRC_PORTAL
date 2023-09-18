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
 * The persistent class for the SAP_AM_RISKS database table.
 * 
 */
@Entity
@Table(name = "SAP_AM_RISKS")
@NamedQuery(name = "SapAmRisk.findAll", query = "SELECT s FROM SapAmRisk s")
public class SapAmRisk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GenericGenerator(name = "riskId", strategy = "com.samodule.util.CustomIdGenerator")
//	@GeneratedValue(generator = "riskId")
	@Column(name = "RISK_ID", unique = true, nullable = false, precision = 16)
	private Long riskId;

	@Column(name = "CREATED_BY", nullable = false, length = 10)
	private String createdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;

	@Column(name = "LAST_UPD_BY", length = 10)
	private String lastUpdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPD_ON")
	private Date lastUpdOn;

	@Column(name = "MODULE", nullable = false, length = 30)
	private String module;

	@Column(name = "RISK_CODE", nullable = false, length = 20)
	private String riskCode;

	@Column(name = "RISK_DESC", nullable = false, length = 100)
	private String riskDesc;

	@Column(nullable = false, length = 1)
	private String status;
	
	@Column(name = "PRIORITY")	
	private String priority;	
	

	public SapAmRisk() {
	}

	public Long getRiskId() {
		return this.riskId;
	}

	public void setRiskId(Long riskId) {
		this.riskId = riskId;
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

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getRiskCode() {
		return this.riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getRiskDesc() {
		return this.riskDesc;
	}

	public void setRiskDesc(String riskDesc) {
		this.riskDesc = riskDesc;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}