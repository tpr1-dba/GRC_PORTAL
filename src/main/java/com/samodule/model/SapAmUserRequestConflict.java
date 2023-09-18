package com.samodule.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SAP_AM_USER_REQUEST_CONFLICTS database table.
 * 
 */
@Entity
@Table(name="SAP_AM_USER_REQUEST_CONFLICTS")
@NamedQuery(name="SapAmUserRequestConflict.findAll", query="SELECT s FROM SapAmUserRequestConflict s")
public class SapAmUserRequestConflict implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="SAP_AM_USER_REQUESTS_SEQ" ,parameters={ @org.hibernate.annotations.Parameter(name = "sequence", value = "SAP_AM_USER_REQUESTS_SEQ")},strategy="sequence")
	@GeneratedValue(generator="SAP_AM_USER_REQUESTS_SEQ",strategy=GenerationType.SEQUENCE)
	@Column(name="SURC_RECID")
	private long surcRecid;

	@Column(name="ACCEPT_REJECT")
	private String acceptReject;

	@Column(name="ACEEPTANCE_REASON")
	private String aceeptanceReason;

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

	@Column(name="MITIGATION_STEPS")
	private String mitigationSteps;

	@Column(name="REJECTION_REASON")
	private String rejectionReason;

	@Column(name="REQUEST_ID")
	private BigDecimal requestId;

	@Column(name="ROLE_CODE")
	private String roleCode;

	@Column(name="SAP_USER_ID")
	private String sapUserId;

	@Column(name="SOD_RISK_ID")
	private BigDecimal sodRiskId;

	private String tcode;

	@Column(name="TCODE_ID")
	private BigDecimal tcodeId;

	public SapAmUserRequestConflict() {
	}

	public long getSurcRecid() {
		return this.surcRecid;
	}

	public void setSurcRecid(long surcRecid) {
		this.surcRecid = surcRecid;
	}

	public String getAcceptReject() {
		return this.acceptReject;
	}

	public void setAcceptReject(String acceptReject) {
		this.acceptReject = acceptReject;
	}

	public String getAceeptanceReason() {
		return this.aceeptanceReason;
	}

	public void setAceeptanceReason(String aceeptanceReason) {
		this.aceeptanceReason = aceeptanceReason;
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

	public String getMitigationSteps() {
		return this.mitigationSteps;
	}

	public void setMitigationSteps(String mitigationSteps) {
		this.mitigationSteps = mitigationSteps;
	}

	public String getRejectionReason() {
		return this.rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
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

	public String getSapUserId() {
		return this.sapUserId;
	}

	public void setSapUserId(String sapUserId) {
		this.sapUserId = sapUserId;
	}

	public BigDecimal getSodRiskId() {
		return this.sodRiskId;
	}

	public void setSodRiskId(BigDecimal sodRiskId) {
		this.sodRiskId = sodRiskId;
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

}