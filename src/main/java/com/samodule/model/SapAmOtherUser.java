package com.samodule.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SAP_AM_OTHER_USER database table.
 * 
 */
@Entity
@Table(name="SAP_AM_OTHER_USER")
@NamedQuery(name="SapAmOtherUser.findAll", query="SELECT s FROM SapAmOtherUser s")
public class SapAmOtherUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SAOU_RECID")
	private long saouRecid;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_ON")
	private Date createdOn;

	@Column(name="LAST_UPD_BY")
	private String lastUpdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPD_ON")
	private Date lastUpdOn;

	private String remarks;

	@Column(name="SAP_COMPANY_CODE")
	private String sapCompanyCode;

	@Column(name="SAP_USERID")
	private String sapUserid;

	private String status;

	@Column(name="USER_DESC")
	private String userDesc;

	@Column(name="USERID_REQUESTER")
	private String useridRequester;

	public SapAmOtherUser() {
	}

	public long getSaouRecid() {
		return this.saouRecid;
	}

	public void setSaouRecid(long saouRecid) {
		this.saouRecid = saouRecid;
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

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSapCompanyCode() {
		return this.sapCompanyCode;
	}

	public void setSapCompanyCode(String sapCompanyCode) {
		this.sapCompanyCode = sapCompanyCode;
	}

	public String getSapUserid() {
		return this.sapUserid;
	}

	public void setSapUserid(String sapUserid) {
		this.sapUserid = sapUserid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserDesc() {
		return this.userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getUseridRequester() {
		return this.useridRequester;
	}

	public void setUseridRequester(String useridRequester) {
		this.useridRequester = useridRequester;
	}

}