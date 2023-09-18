package com.samodule.service;

import java.util.List;

import com.samodule.vo.SapAmRiskFunMappingVO;

public interface RiskFunctionManager {	
	public List<SapAmRiskFunMappingVO> getRiskFunction()throws Exception;

}
