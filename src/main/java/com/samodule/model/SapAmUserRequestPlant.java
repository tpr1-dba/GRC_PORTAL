package com.samodule.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SAP_AM_USER_REQUEST_PLANTS database table.
 * 
 */
@Entity
@Table(name="SAP_AM_USER_REQUEST_PLANTS")
@NamedQuery(name="SapAmUserRequestPlant.findAll", query="SELECT s FROM SapAmUserRequestPlant s")
public class SapAmUserRequestPlant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="SAP_AM_USER_REQUEST_PLANTS_SEQ" ,parameters={ @org.hibernate.annotations.Parameter(name = "sequence", value = "SAP_AM_USER_REQUEST_PLANTS_SEQ")},strategy="sequence")
	@GeneratedValue(generator="SAP_AM_USER_REQUEST_PLANTS_SEQ",strategy=GenerationType.SEQUENCE)
//	@GenericGenerator(name = "surpRecid", strategy = "com.samodule.util.CustomIdGenerator")
//	@GeneratedValue(generator = "surpRecid")
	@Column(name="SURP_RECID")
	private long surpRecid;

	@Column(name="APPROVED_BY")
	private String approvedBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="APPROVED_ON")
	private Date approvedOn;

	@Column(name="CREATED_BY")
	private String createdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name="CREATED_ON")
	private Date createdOn;

	private String plant;

	@Column(name="PURCHASE_GROUP")
	private String purchaseGroup;

	private String remarks;

	@Column(name="REQUEST_ID")
	private BigDecimal requestId;
	
	@Column(name="ROLE_ID")
	private BigDecimal roleId;
	
	@Column(name="ROLE_CODE")
	private String roleCode;

	private String status;

	@Column(name="SUR_RECID")
	private BigDecimal surRecid;

	public SapAmUserRequestPlant(BigDecimal requestId, BigDecimal roleId, String roleCode, BigDecimal surRecid,String remarks) {
		super();
		this.requestId = requestId;
		this.roleId = roleId;
		this.roleCode = roleCode;
		this.surRecid = surRecid;
		this.remarks = remarks;
	}

	public SapAmUserRequestPlant(String plant, String purchaseGroup, BigDecimal requestId, BigDecimal surRecid,String remarks) {
		super();
		this.plant = plant;
		this.purchaseGroup = purchaseGroup;
		this.requestId = requestId;
		this.surRecid = surRecid;
		this.remarks = remarks;
	}

	public SapAmUserRequestPlant() {
	}

	public long getSurpRecid() {
		return this.surpRecid;
	}

	public void setSurpRecid(long surpRecid) {
		this.surpRecid = surpRecid;
	}

	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedOn() {
		return this.approvedOn;
	}

	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
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

	public String getPlant() {
		return this.plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getPurchaseGroup() {
		return this.purchaseGroup;
	}

	public void setPurchaseGroup(String purchaseGroup) {
		this.purchaseGroup = purchaseGroup;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getRequestId() {
		return this.requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public BigDecimal getRoleId() {
		return roleId;
	}

	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getSurRecid() {
		return this.surRecid;
	}

	public void setSurRecid(BigDecimal surRecid) {
		this.surRecid = surRecid;
	}

}