
package com.samodule.vo;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tcodeId",
    "tcode",
    "module",
    "sapCompanyCode",
    "plant",
    "purchaseGroup",
    "typeOfRight",
    "reason"
})

public class SodUserRequestVO {
	//@NotBlank(message = "Select tcode")
    @JsonProperty("tcodeId")
    private String tcodeId;
    @NotBlank(message = "Select tcode")
    @JsonProperty("tcode")   
    private String tcode;
	@JsonProperty("module")
    private String module;
    @NotBlank(message = "Select sap company code")
    @JsonProperty("sapCompanyCode")
    private String sapCompanyCode;
    @JsonProperty("plant")
    private String plant;
    @JsonProperty("purchaseGroup")
    private String purchaseGroup;
    @JsonProperty("typeOfRight")
    private String typeOfRight;
    @JsonProperty("reason")
    @NotBlank(message = "Enter reaseon for tcode")
    private String reason;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("tcodeId")
    public String gettcodeId() {
        return tcodeId;
    }

    @JsonProperty("tcodeId")
    public void settcodeId(String tcodeId) {
        this.tcodeId = tcodeId;
    }



    @JsonProperty("module")
    public String getModule() {
        return module;
    }

    @JsonProperty("module")
    public void setModule(String module) {
        this.module = module;
    }

    @JsonProperty("sapCompanyCode")
    public String getSapCompanyCode() {
        return sapCompanyCode;
    }

    @JsonProperty("sapCompanyCode")
    public void setSapCompanyCode(String sapCompanyCode) {
        this.sapCompanyCode = sapCompanyCode;
    }

    @JsonProperty("plant")
    public String getPlant() {
        return plant;
    }

    @JsonProperty("plant")
    public void setPlant(String plant) {
        this.plant = plant;
    }

    @JsonProperty("purchaseGroup")
    public String getPurchaseGroup() {
        return purchaseGroup;
    }

    @JsonProperty("purchaseGroup")
    public void setPurchaseGroup(String purchaseGroup) {
        this.purchaseGroup = purchaseGroup;
    }

    @JsonProperty("typeOfRight")
    public String getTypeOfRight() {
        return typeOfRight;
    }

    @JsonProperty("typeOfRight")
    public void setTypeOfRight(String typeOfRight) {
        this.typeOfRight = typeOfRight;
    }

    @JsonProperty("reason")
    public String getReason() {
        return reason;
    }

    @JsonProperty("reason")
    public void setReason(String reason) {
        this.reason = reason;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    
    public String getTcode() {
		return tcode;
	}

	public void setTcode(String tcode) {
		this.tcode = tcode;
	}

}
