package com.samodule.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the SAP_AM_TCODE_FUN_MAPPING database table.
 * 
 */
@Entity
@Table(name="SAP_AM_TCODE_FUN_MAPPING")
@NamedQuery(name="SapAmTcodeFunMapping.findAll", query="SELECT s FROM SapAmTcodeFunMapping s")
public class SapAmTcodeFunMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY) 
//	@GenericGenerator(name = "tfId", strategy = "com.samodule.util.CustomIdGenerator")
	@Column(name="TF_ID")
	@GenericGenerator(name="SAP_TCODE_FUN_MAPPING_SEQ" ,parameters={ @org.hibernate.annotations.Parameter(name = "sequence", value = "SAP_TCODE_FUN_MAPPING_SEQ")},strategy="sequence")
	@GeneratedValue(generator="SAP_TCODE_FUN_MAPPING_SEQ",strategy=GenerationType.SEQUENCE)
	private Long tfId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_ON")
	private Date createdOn;

	private String entity;

	@Column(name="FUN_ID")
	private Long funId;

	@Column(name="LAST_UPD_BY")
	private String lastUpdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPD_ON")
	private Date lastUpdOn;

	private String module;

	private String status;

	@Column(name="TCODE_ID")
	private Long tcodeId;

	public SapAmTcodeFunMapping() {
	}

	public Long getTfId() {
		return this.tfId;
	}

	public void setTfId(Long tfId) {
		this.tfId = tfId;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTcodeId() {
		return this.tcodeId;
	}

	public void setTcodeId(Long tcodeId) {
		this.tcodeId = tcodeId;
	}

}