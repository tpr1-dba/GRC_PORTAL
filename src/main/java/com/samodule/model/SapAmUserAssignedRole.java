package com.samodule.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SAP_AM_USER_ASSIGNED_ROLES database table.
 * 
 */
@Entity
@Table(name="SAP_AM_USER_ASSIGNED_ROLES")
@NamedQuery(name="SapAmUserAssignedRole.findAll", query="SELECT s FROM SapAmUserAssignedRole s")
public class SapAmUserAssignedRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="SAP_AM_USER_REQUESTS_SEQ" ,parameters={ @org.hibernate.annotations.Parameter(name = "sequence", value = "SAP_AM_USER_REQUESTS_SEQ")},strategy="sequence")
	@GeneratedValue(generator="SAP_AM_USER_REQUESTS_SEQ",strategy=GenerationType.SEQUENCE)
	@Column(name="SUAR_RECID")
	private long suarRecid;

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

	@Column(name="IS_CONFLICTED")
	private String isConflicted;

	private String plant;

	@Column(name="PURCHASE_GROUP")
	private String purchaseGroup;

	private String reason;

	private String remarks;

	@Column(name="REQUEST_ID")
	private BigDecimal requestId;

	@Column(name="ROLE_CODE")
	private String roleCode;

	@Column(name="ROLE_ID")
	private BigDecimal roleId;

	@Column(name="SAP_COMPANY_CODE")
	private String sapCompanyCode;

	@Column(name="SAP_MODULE_CODE")
	private String sapModuleCode;

	@Column(name="SAP_USER_ID")
	private String sapUserId;

	private String status;

	@Column(name="SUR_RECID")
	private BigDecimal surRecid;

	private String tcode;

	@Column(name="TCODE_ID")
	private BigDecimal tcodeId;

	@Column(name="TYPE_OF_RIGHT")
	private String typeOfRight;

	public SapAmUserAssignedRole() {
	}

	public long getSuarRecid() {
		return this.suarRecid;
	}

	public void setSuarRecid(long suarRecid) {
		this.suarRecid = suarRecid;
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

	public String getIsConflicted() {
		return this.isConflicted;
	}

	public void setIsConflicted(String isConflicted) {
		this.isConflicted = isConflicted;
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

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
		return this.roleId;
	}

	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}

	public String getSapCompanyCode() {
		return this.sapCompanyCode;
	}

	public void setSapCompanyCode(String sapCompanyCode) {
		this.sapCompanyCode = sapCompanyCode;
	}

	public String getSapModuleCode() {
		return this.sapModuleCode;
	}

	public void setSapModuleCode(String sapModuleCode) {
		this.sapModuleCode = sapModuleCode;
	}

	public String getSapUserId() {
		return this.sapUserId;
	}

	public void setSapUserId(String sapUserId) {
		this.sapUserId = sapUserId;
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

	public String getTcode() {
		return this.tcode;
	}

	public void setTcode(String tcode) {
		this.tcode = tcode;
	}

	public BigDecimal getTcodeId() {
		return this.tcodeId;
	}

	public void setTcodeId(BigDecimal tcodeId) {
		this.tcodeId = tcodeId;
	}

	public String getTypeOfRight() {
		return this.typeOfRight;
	}

	public void setTypeOfRight(String typeOfRight) {
		this.typeOfRight = typeOfRight;
	}

}