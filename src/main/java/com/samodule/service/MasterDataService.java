package com.samodule.service;

import java.util.List;
import java.util.Map;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmMasterTypeDetail;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.HrmLoginVO;
import com.samodule.vo.PlantVO;
import com.samodule.vo.SapAmOtherUserVO;
import com.samodule.vo.SapAmRiskFunMappingVO;
import com.samodule.vo.SapAmRolesRequestVO;
import com.samodule.vo.SapAmTcodeVO;
import com.samodule.vo.SapUserVO;

public interface MasterDataService {
	public Map<String, List<?>> listHeaderData(String requestType,HeapUserDetails principal) throws Exception ;
	public List<PlantVO> getSapPlants(String companies);
	public List<PlantVO> getPlantByCompany(String sapCompany, JQueryDataTableParamModel criteria);
	public List<SapAmTcodeVO> getTcodeByModule(String pmodule);
	public List<SapAmTcodeVO> getTcodeByModule(String pmodule, JQueryDataTableParamModel criteria);
	public List<SapAmMasterTypeDetail> getPGroup() throws Exception;
	public List<SapAmMasterTypeDetail> getPGroupJson(JQueryDataTableParamModel criteria) throws Exception;
	public List<SapAmRolesRequestVO> getRoles(JQueryDataTableParamModel criteria) throws Exception;
	public List<SapUserVO> getUsersDetails(HeapUserDetails principal) throws Exception;
	public List<SapUserVO> searchUsers(HeapUserDetails principal, String sapUserId) throws Exception;
//	public List<SapAmRiskFunMappingVO> getRiskFunctionByRequestId(String requestId,JQueryDataTableParamModel criteria)throws Exception;
	public List<SapAmRiskFunMappingVO> getRiskFunctionByRequestId(String requestId)throws Exception;
	public List<SapAmOtherUserVO> searchOthUsers(HeapUserDetails principal, String sapUserId)throws Exception;
	List<HrmLoginVO> getAllEmployee(boolean isRefresh);

}
