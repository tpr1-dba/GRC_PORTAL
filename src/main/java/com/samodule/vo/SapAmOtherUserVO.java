package com.samodule.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SapAmOtherUserVO {
	@JsonProperty("saou_recid")
	private BigDecimal saouRecid;
	@JsonProperty("sap_userid")
	private String sapUserid;
	@JsonProperty("user_desc")
	private String userDesc;
	@JsonProperty("status")
	private String status;
	@JsonProperty("created_on")
	private Date createdOn;
	@JsonProperty("created_by")
	private String createdBy;
	@JsonProperty("userid_requester")
	private String useridRequester;
	@JsonProperty("sap_company_code")
	private String sapCompanyCode;
	@JsonProperty("remarks")
	private String remarks;
	@JsonProperty("last_upd_on")
	private Date lastUpdOn;
	@JsonProperty("last_upd_by")
	private String lastUpdBy;

	public BigDecimal getSaouRecid() {
		return saouRecid;
	}

	public void setSaouRecid(BigDecimal saouRecid) {
		this.saouRecid = saouRecid;
	}

	public String getSapUserid() {
		return sapUserid;
	}

	public void setSapUserid(String sapUserid) {
		this.sapUserid = sapUserid;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUseridRequester() {
		return useridRequester;
	}

	public void setUseridRequester(String useridRequester) {
		this.useridRequester = useridRequester;
	}

	public String getSapCompanyCode() {
		return sapCompanyCode;
	}

	public void setSapCompanyCode(String sapCompanyCode) {
		this.sapCompanyCode = sapCompanyCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getLastUpdOn() {
		return lastUpdOn;
	}

	public void setLastUpdOn(Date lastUpdOn) {
		this.lastUpdOn = lastUpdOn;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

}
