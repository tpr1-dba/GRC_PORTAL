package com.samodule.vo;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samodule.validator.NotNullIfAnotherFieldHasValue;

@NotNullIfAnotherFieldHasValue.List({
		@NotNullIfAnotherFieldHasValue(fieldName = "fortime", fieldValue = "temporary", dependFieldName = "fromDate"),
		@NotNullIfAnotherFieldHasValue(fieldName = "fortime", fieldValue = "temporary", dependFieldName = "toDate") })
public class SapAmRequestStatusVO {
	private BigDecimal requestId;
	private Long surRecid;
	private Long surtRecid;
	private Long surpRecid;
	private String status;
	@NotBlank(message = "Enter reaseon for access")
	private String reason;
	@JsonProperty("fortime")
	private String fortime;
	@JsonProperty("fromDate")
	private String fromDate;
	@JsonProperty("toDate")
	private String toDate;
	private BigDecimal wfLevel;

	// added on 19-07-2023 for get sapUserid from view;
	private String sapUserid;

	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public Long getSurRecid() {
		return surRecid;
	}

	public void setSurRecid(Long surRecid) {
		this.surRecid = surRecid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSurtRecid() {
		return surtRecid;
	}

	public void setSurtRecid(Long surtRecid) {
		this.surtRecid = surtRecid;
	}

	public Long getSurpRecid() {
		return surpRecid;
	}

	public void setSurpRecid(Long surpRecid) {
		this.surpRecid = surpRecid;
	}

	@JsonProperty("reason")
	public String getReason() {
		return reason;
	}

	@JsonProperty("reason")
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFortime() {
		return fortime;
	}

	public void setFortime(String fortime) {
		this.fortime = fortime;
	}

	public BigDecimal getWfLevel() {
		return wfLevel;
	}

	public void setWfLevel(BigDecimal wfLevel) {
		this.wfLevel = wfLevel;
	}

	public String getSapUserid() {
		return sapUserid;
	}

	public void setSapUserid(String sapUserid) {
		this.sapUserid = sapUserid;
	}
}
