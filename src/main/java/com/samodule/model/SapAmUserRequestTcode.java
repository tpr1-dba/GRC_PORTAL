package com.samodule.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SAP_AM_USER_REQUEST_TCODES database table.
 * 
 */
@Entity
@Table(name="SAP_AM_USER_REQUEST_TCODES")
@NamedQuery(name="SapAmUserRequestTcode.findAll", query="SELECT s FROM SapAmUserRequestTcode s")
public class SapAmUserRequestTcode implements Serializable {
	
	
	public SapAmUserRequestTcode() {
		super();
	}

	public SapAmUserRequestTcode(BigDecimal requestId, BigDecimal surRecid, String tcode, BigDecimal tcodeId,String remarks) {
		super();
		this.requestId = requestId;
		this.surRecid = surRecid;
		this.tcode = tcode;
		this.tcodeId = tcodeId;
		this.remarks = remarks;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="SAP_AM_USER_REQUEST_TCODES_SEQ" ,parameters={ @org.hibernate.annotations.Parameter(name = "sequence", value = "SAP_AM_USER_REQUEST_TCODES_SEQ")},strategy="sequence")
	@GeneratedValue(generator="SAP_AM_USER_REQUEST_TCODES_SEQ",strategy=GenerationType.SEQUENCE)
//	@GenericGenerator(name = "surtRecid", strategy = "com.samodule.util.CustomIdGenerator")
//	@GeneratedValue(generator = "surtRecid")
	@Column(name="SURT_RECID")
	private long surtRecid;

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

	private String remarks;

	@Column(name="REQUEST_ID")
	private BigDecimal requestId;

	@Column(name="ROLE_CODE")
	private String roleCode;
	
	@Column(name="ROLE_ID")
	private BigDecimal roleId;	

	private String status;

	@Column(name="SUR_RECID")
	private BigDecimal surRecid;

	private String tcode;

	@Column(name="TCODE_ID")
	private BigDecimal tcodeId;
	
	public long getSurtRecid() {
		return this.surtRecid;
	}

	public void setSurtRecid(long surtRecid) {
		this.surtRecid = surtRecid;
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

	public BigDecimal getRoleId() {
		return roleId;
	}

	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}

}