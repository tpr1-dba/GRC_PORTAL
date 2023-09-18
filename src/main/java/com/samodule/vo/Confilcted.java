package com.samodule.vo;
public class Confilcted {
	private String funCode;
	private String riskCode;
	private String key;
	private Integer conCount;

	public Confilcted() {
		super();
	}
	public Confilcted(String funCode, String riskCode,Integer conCount) {
		// TODO Auto-generated constructor stub
		
		this.funCode=funCode;
		this.riskCode=riskCode;
		this.conCount=conCount;
	}
	public Confilcted(String key,String funCode, String riskCode,Integer conCount) {
		// TODO Auto-generated constructor stub
		this.key=key;
		this.funCode=funCode;
		this.riskCode=riskCode;
		this.conCount=conCount;
	}

	public String getFunCode() {
		return funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getConCount() {
		return conCount;
	}

	public void setConCount(Integer conCount) {
		this.conCount = conCount;
	}
}
