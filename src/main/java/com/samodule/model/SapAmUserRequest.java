package com.samodule.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.samodule.util.StringDateDeSerializer;


/**
 * The persistent class for the SAP_AM_USER_REQUESTS database table.
 * 
 */
@Entity
@Table(name="SAP_AM_USER_REQUESTS")
@NamedQuery(name="SapAmUserRequest.findAll", query="SELECT s FROM SapAmUserRequest s")
public class SapAmUserRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="SAP_AM_USER_REQUESTS_SEQ" ,parameters={ @org.hibernate.annotations.Parameter(name = "sequence", value = "SAP_AM_USER_REQUESTS_SEQ")},strategy="sequence")
	@GeneratedValue(generator="SAP_AM_USER_REQUESTS_SEQ",strategy=GenerationType.SEQUENCE)	
	@Column(name="SUR_RECID")
	private long surRecid;

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

	@Column(name="EMP_CODE")
	private String empCode;

	//@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = StringDateDeSerializer.class)
	//@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name="FROM_DATE")
	private Date fromDate;

	private String reason;

	@Column(name="REF_EMP_CODE")
	private String refEmpCode;

	@Column(name="REF_SAP_USER_ID")
	private String refSapUserId;

	private String remarks;

	//@Temporal(TemporalType.DATE)
	@Column(name="REQUEST_DATE")
	private Date requestDate;

	@Column(name="REQUEST_ID")
	private BigDecimal requestId;

	@Column(name="REQUEST_TYPE")
	private String requestType;

	@Column(name="RISK_ACEEPTANCE_REASON")
	private String riskAceeptanceReason;

	@Column(name="RISK_MITIGATION_STEPS")
	private String riskMitigationSteps;

	@Column(name="RISK_REJECTION_REASON")
	private String riskRejectionReason;

	@Column(name="SAP_COMPANY_CODE")
	private String sapCompanyCode;

	@Column(name="SAP_MODULE_CODE")
	private String sapModuleCode;

	@Column(name="SAP_USER_ID")
	private String sapUserId;

	private String status;

//	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = StringDateDeSerializer.class)
	//@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name="TO_DATE")
	private Date toDate;

	@Column(name="TYPE_OF_RIGHT")
	private String typeOfRight;

	@Column(name="WF_LEVEL")
	private String wfLevel;

	public SapAmUserRequest() {
	}

	public long getSurRecid() {
		return this.surRecid;
	}

	public void setSurRecid(long surRecid) {
		this.surRecid = surRecid;
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

	public String getEmpCode() {
		return this.empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public Date getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRefEmpCode() {
		return this.refEmpCode;
	}

	public void setRefEmpCode(String refEmpCode) {
		this.refEmpCode = refEmpCode;
	}

	public String getRefSapUserId() {
		return this.refSapUserId;
	}

	public void setRefSapUserId(String refSapUserId) {
		this.refSapUserId = refSapUserId;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public BigDecimal getRequestId() {
		return this.requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRiskAceeptanceReason() {
		return this.riskAceeptanceReason;
	}

	public void setRiskAceeptanceReason(String riskAceeptanceReason) {
		this.riskAceeptanceReason = riskAceeptanceReason;
	}

	public String getRiskMitigationSteps() {
		return this.riskMitigationSteps;
	}

	public void setRiskMitigationSteps(String riskMitigationSteps) {
		this.riskMitigationSteps = riskMitigationSteps;
	}

	public String getRiskRejectionReason() {
		return this.riskRejectionReason;
	}

	public void setRiskRejectionReason(String riskRejectionReason) {
		this.riskRejectionReason = riskRejectionReason;
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

	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getTypeOfRight() {
		return this.typeOfRight;
	}

	public void setTypeOfRight(String typeOfRight) {
		this.typeOfRight = typeOfRight;
	}

	public String getWfLevel() {
		return this.wfLevel;
	}

	public void setWfLevel(String wfLevel) {
		this.wfLevel = wfLevel;
	}

}