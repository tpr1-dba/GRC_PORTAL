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
 * The persistent class for the SAP_AM_FUNCTIONS database table.
 * 
 */
@Entity
@Table(name = "SAP_AM_FUNCTIONS")
@NamedQuery(name = "SapAmFunction.findAll", query = "SELECT s FROM SapAmFunction s")
public class SapAmFunction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GenericGenerator(name = "funId", strategy = "com.samodule.util.CustomIdGenerator")
//	@GeneratedValue(generator = "funId")
	@Column(name = "FUN_ID", unique = true, nullable = false, precision = 16)
	private Long funId;

	@Column(name = "CREATED_BY", nullable = false, length = 10)
	private String createdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;

	@Column(name = "FUN_CODE", nullable = false, length = 20)
	private String funCode;

	@Column(name = "FUN_DESC", nullable = false, length = 100)
	private String funDesc;

	@Column(name = "LAST_UPD_BY", length = 10)
	private String lastUpdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPD_ON")
	private Date lastUpdOn;

	@Column(name = "MODULE", length = 30)
	private String module;

	private String status;

	public SapAmFunction() {
	}

	public Long getFunId() {
		return this.funId;
	}

	public void setFunId(Long funId) {
		this.funId = funId;
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

	public String getFunCode() {
		return this.funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}

	public String getFunDesc() {
		return this.funDesc;
	}

	public void setFunDesc(String funDesc) {
		this.funDesc = funDesc;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}