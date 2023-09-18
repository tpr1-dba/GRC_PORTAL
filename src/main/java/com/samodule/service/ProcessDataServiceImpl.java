package com.samodule.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.model.SapAmFunction;
import com.samodule.model.SapAmRisk;
import com.samodule.service.FunctionManager;
import com.samodule.service.RiskFunctionManager;
import com.samodule.service.RiskManager;
import com.samodule.vo.Confilcted;
import com.samodule.vo.RoleMasterVO;
import com.samodule.vo.SapAmRiskFunMappingVO;

@Service("processDataService")
public class ProcessDataServiceImpl implements ProcessDataService {
	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private FunctionManager functionManager;
	@Autowired
	private RiskFunctionManager riskFunctionManager;
	@Autowired
	private RiskManager riskManager;

	@Autowired
	private RiskFinderManager riskFinderManager;


	@Override
	public  List<SapAmRiskFunMappingVO>  getSourceData(Set<String> rolesfunctions ) throws Exception {
		// TODO Auto-generated method stub
		List<SapAmFunction> functions = functionManager.getFunction();
		List<SapAmRisk> risks = riskManager.getRisk();
		List<SapAmRiskFunMappingVO> riskFunMappings = riskFunctionManager.getRiskFunction();

	
		log.info("FunMappings  " + functions.size() + " Risk  " + risks.size() + " Risk FunMappings "
				+ riskFunMappings.size() + " Staging Datas " + 0);

		MakeGroupSet riskFunctionSet = this.makeRiskFunctionSet();
		Map<String, Set<String>> riskFunctionData = riskFunctionSet.groupBy(riskFunMappings);

		
		
//		MakeGroup functionGroupByRoleCode = this.functionGroupByRoleCode();
//		Map<String, List<String>> functionGroupByRoleCodeData = functionGroupByRoleCode.groupBy(userRoles);
		//List<Confilcted> confilctedsRoleCode = riskFinderManager.riskFind(functionGroupByRoleCodeData, riskFunctionData);
		List<Confilcted> confilctedsRoleCode = riskFinderManager.riskFindForFunction(rolesfunctions, riskFunctionData);
	    
		log.info("confilctedsTcode size  "+confilctedsRoleCode.size());
		return this.createSharedListViaStream(confilctedsRoleCode,riskFunMappings);
       //return confilctedsRoleCode;  
	}

	public  List<SapAmRiskFunMappingVO> createSharedListViaStream(List<Confilcted> listOne, List<SapAmRiskFunMappingVO> listTwo)
	{
	    // We create a stream of elements from the first list.
	    List<SapAmRiskFunMappingVO> listOneList = listTwo.stream()
	    // We select any elements such that in the stream of elements from the second list
	    .filter(two -> listOne.stream()
	    // there is an element that has the same name and school as this element,
	        .anyMatch(one -> one.getRiskCode().equals(two.getRiskCode())))
	    // and collect all matching elements from the first list into a new list.
	    .collect(Collectors.toList());
	    // We return the collected list.
	    return listOneList;
	}
	
	
	public MakeGroup functionGroupByRoleCode() {
		MakeGroup functionGroupByTcode = (list) -> {
//			return list.stream().collect(Collectors.groupingBy(SapAmStagingData::getTcode,
//					Collectors.mapping(SapAmStagingData::getFunCode, Collectors.toList())));
			return list.stream().collect(Collectors.groupingBy(p -> p.getRoleCode(),
					Collectors.mapping(e -> e.getFunCode(), Collectors.toList())));
		};
		return functionGroupByTcode;
	}





	
	public MakeGroupSet makeRiskFunctionSet() {
		MakeGroupSet makeRiskFunctionSet = (list) -> {
			Map<String,Set<String>>map = list.stream()
					.collect(Collectors.groupingBy(sapAmRiskFunMappingVO -> sapAmRiskFunMappingVO.getRiskCode(),
							Collectors.mapping(sapAmRiskFunMappingVO -> sapAmRiskFunMappingVO.getFunCode(),
									Collectors.toSet())));
			
			return	map.entrySet() .stream().sorted((e1,e2)->e2.getValue().size()-e1.getValue().size()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e,e1)->e,LinkedHashMap::new));
			
//			return list.stream().collect(Collectors.groupingBy(SapAmRiskFunMappingVO::getFunCode,
//					Collectors.mapping(SapAmRiskFunMappingVO::getRiskCode, Collectors.toSet())));
		};
		return makeRiskFunctionSet;
	}

}
