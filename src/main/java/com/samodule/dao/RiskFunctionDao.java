package com.samodule.dao;

import java.util.List;

import com.samodule.model.SapAmRiskFunMapping;
import com.samodule.vo.SapAmRiskFunMappingVO;

public interface RiskFunctionDao extends Dao<SapAmRiskFunMapping> {

	public List<SapAmRiskFunMappingVO> getRiskFunction()
			throws Exception;

}
