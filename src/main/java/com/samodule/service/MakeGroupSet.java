package com.samodule.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.samodule.vo.SapAmRiskFunMappingVO;

@FunctionalInterface
interface MakeGroupSet {
	public Map<String, Set<String>> groupBy(List<SapAmRiskFunMappingVO> riskFunMappings);
}