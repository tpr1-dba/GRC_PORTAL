package com.samodule.service;

import java.util.List;
import java.util.Set;

import com.samodule.vo.Confilcted;
import com.samodule.vo.SapAmRiskFunMappingVO;

public interface  ProcessDataService {

	//public List<Confilcted> getSourceData(Set<String> rolesfunctions ) throws Exception;
	public  List<SapAmRiskFunMappingVO>  getSourceData(Set<String> rolesfunctions ) throws Exception;
}
