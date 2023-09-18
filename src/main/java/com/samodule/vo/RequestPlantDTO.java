
package com.samodule.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "surpRecid", "surRecid", "requestId", "plant", "purchaseGroup"})

public class RequestPlantDTO {
	@JsonProperty("surpRecid")
	private long surpRecid;
	@JsonProperty("surRecid")
	private long surRecid;
	@JsonProperty("requestId")
	private BigDecimal requestId;
	@JsonProperty("plant")
	private String plant;
	@JsonProperty("purchaseGroup")
	private String purchaseGroup;
	
	public long getSurRecid() {
		return surRecid;
	}

	public void setSurRecid(long surRecid) {
		this.surRecid = surRecid;
	}

	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public long getSurpRecid() {
		return surpRecid;
	}

	public void setSurpRecid(long surpRecid) {
		this.surpRecid = surpRecid;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getPurchaseGroup() {
		return purchaseGroup;
	}

	public void setPurchaseGroup(String purchaseGroup) {
		this.purchaseGroup = purchaseGroup;
	}

	

}
