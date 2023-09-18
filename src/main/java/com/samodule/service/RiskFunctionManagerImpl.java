package com.samodule.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.RiskFunctionDao;
import com.samodule.vo.SapAmRiskFunMappingVO;



@Service("riskFunctionManager")
public class RiskFunctionManagerImpl implements RiskFunctionManager {
	@Autowired
	private RiskFunctionDao riskFunctionDao;
	
	@Transactional
	@Override
	public List<SapAmRiskFunMappingVO> getRiskFunction() throws Exception{
		return riskFunctionDao.getRiskFunction();
	}
	
}
