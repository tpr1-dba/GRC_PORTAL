package com.samodule.dao;

import java.util.List;
import java.util.Set;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmMasterTypeDetail;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.CompanyVO;
import com.samodule.vo.ModuleVO;
import com.samodule.vo.PlantVO;
import com.samodule.vo.SapAmOtherUserVO;
import com.samodule.vo.SapAmRolesRequestVO;
import com.samodule.vo.SapAmTcodeVO;
import com.samodule.vo.SapUserVO;

public interface MasterDataDao {

	public List<CompanyVO> getSapCompany();
	public List<ModuleVO> getSapModule(String masterId);
	public List<PlantVO> getSapPlants(String companies);
	public List<PlantVO> getPlantByCompany(String sapCompany, JQueryDataTableParamModel criteria);
	public List<SapAmTcodeVO> getTcodeByModule(String pmodule);
	public List<SapAmTcodeVO> getTcodeByModule(String pmodule, JQueryDataTableParamModel criteria);
	public List<SapAmMasterTypeDetail> getPGroup() throws Exception;
	public List<SapAmMasterTypeDetail> getPGroupJson(JQueryDataTableParamModel criteria);
	public List<SapAmRolesRequestVO> getRoles(JQueryDataTableParamModel criteria) throws Exception;
	public List<SapUserVO> searchUsers(HeapUserDetails principal, String sapUserId);
	public List<SapUserVO> getUsersDetails(HeapUserDetails principal);
	public List<PlantVO> getSapPlantSBU(Set<String> plants)throws Exception;
	public List<String> getSapPlantSbus(Set<String> plants) throws Exception;
	public List<SapAmOtherUserVO> searchOthUsers(HeapUserDetails principal, String sapUserId)throws Exception;

}
