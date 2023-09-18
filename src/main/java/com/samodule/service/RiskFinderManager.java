package com.samodule.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.samodule.vo.Confilcted;

public interface RiskFinderManager {

	List<Confilcted> getConflicte(Map<String, Set<String>> temp);

	List<Confilcted> riskFind(Map<String, List<String>> groupByData, Map<String, Set<String>> riskFunctionData);

	List<Confilcted> riskFindForFunction(Set<String> functions, Map<String, Set<String>> riskFunctionData);

}
