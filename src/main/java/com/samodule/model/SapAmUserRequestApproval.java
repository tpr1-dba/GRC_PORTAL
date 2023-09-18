package com.samodule.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the SAP_AM_USER_REQUEST_APPROVALS database table.
 * 
 */
@Entity
@Table(name = "SAP_AM_USER_REQUEST_APPROVALS")
@NamedQuery(name = "SapAmUserRequestApproval.findAll", query = "SELECT s FROM SapAmUserRequestApproval s")
public class SapAmUserRequestApproval implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "SAP_AM_USER_REQUESTS_SEQ", parameters = {
			@org.hibernate.annotations.Parameter(name = "sequence", value = "SAP_AM_USER_REQUESTS_SEQ") }, strategy = "sequence")
	@GeneratedValue(generator = "SAP_AM_USER_REQUESTS_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "SWM_RECID")
	private long swmRecid;

	@Column(name = "APPROVED_BY")
	private String approvedBy;

	//@Temporal(TemporalType.DATE)
	@Column(name = "APPROVED_ON")
	private Date approvedOn;

	@Column(name = "COMPANY_CODE")
	private String companyCode;

	@Column(name = "CREATED_BY")
	private String createdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "EMP_CODE")
	private String empCode;

	@Column(name = "IS_CTM")
	private String isCtm;

	@Column(name = "IS_HOD")
	private String isHod;

	@Column(name = "LAST_UPD_BY")
	private String lastUpdBy;

	//@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPD_ON")
	private Date lastUpdOn;

	@Column(name = "PARENT_RECID")
	private BigDecimal parentRecid;

	@Column(name = "PURCHASING_GROUP")
	private String purchasingGroup;

	private String remarks;

	@Column(name = "REQUEST_ID")
	private BigDecimal requestId;

	@Column(name = "SAP_MODULE")
	private String sapModule;

	private String sbu;

	private String status;

	@Column(name = "WF_CODE")
	private String wfCode;

	@Column(name = "WF_LEVEL")
	private BigDecimal wfLevel;

	public SapAmUserRequestApproval() {
	}

	public long getSwmRecid() {
		return this.swmRecid;
	}

	public void setSwmRecid(long swmRecid) {
		this.swmRecid = swmRecid;
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

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

	public String getIsCtm() {
		return this.isCtm;
	}

	public void setIsCtm(String isCtm) {
		this.isCtm = isCtm;
	}

	public String getIsHod() {
		return this.isHod;
	}

	public void setIsHod(String isHod) {
		this.isHod = isHod;
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

	public BigDecimal getParentRecid() {
		return this.parentRecid;
	}

	public void setParentRecid(BigDecimal parentRecid) {
		this.parentRecid = parentRecid;
	}

	public String getPurchasingGroup() {
		return this.purchasingGroup;
	}

	public void setPurchasingGroup(String purchasingGroup) {
		this.purchasingGroup = purchasingGroup;
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

	public String getSapModule() {
		return this.sapModule;
	}

	public void setSapModule(String sapModule) {
		this.sapModule = sapModule;
	}

	public String getSbu() {
		return this.sbu;
	}

	public void setSbu(String sbu) {
		this.sbu = sbu;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getWfLevel() {
		return this.wfLevel;
	}

	public void setWfLevel(BigDecimal wfLevel) {
		this.wfLevel = wfLevel;
	}

	public String getWfCode() {
		return wfCode;
	}

	public void setWfCode(String wfCode) {
		this.wfCode = wfCode;
	}

}