package com.samodule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.MasterDataDao;
import com.samodule.dao.RequestTcodeDao;
import com.samodule.dao.RiskApproveDao;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmMasterTypeDetail;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.CompanyVO;
import com.samodule.vo.HrmLoginVO;
import com.samodule.vo.PlantVO;
import com.samodule.vo.SapAmOtherUserVO;
import com.samodule.vo.SapAmRiskFunMappingVO;
import com.samodule.vo.SapAmRolesRequestVO;
import com.samodule.vo.SapAmTcodeVO;
import com.samodule.vo.SapUserVO;
import com.samodule.vo.UserRequestDTO;

@Service("masterDataService")
public class MasterDataServiceImpl implements MasterDataService{
	@Autowired
	private MasterDataDao masterDataDao;
	@Autowired
	RequestTcodeDao requestTcodeDao;
	
	@Autowired
	RiskApproveDao riskApproveDao;
	
	@Autowired
	HrmLoginManager hrmLoginManager;
	
	@Override
	public Map<String, List<?>> listHeaderData(String requestType,HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		Map<String, List<?>> headerMap = new HashMap<>();
		List<CompanyVO> companyVOs= masterDataDao.getSapCompany();		
		List<UserRequestDTO> sodUserRequests=requestTcodeDao.getRequestedByUserID(requestType,principal);
		CompanyVO companyVO =new CompanyVO();
		companyVO.setCompanyName("1000,2000,3000");
		companyVO.setCompanyCode("ALL");
		companyVOs.add(companyVO);
		headerMap.put("sapCompany",companyVOs);
		//headerMap.put("userCompany",principal.getCompany());
		headerMap.put("sodUserRequests", sodUserRequests);
		headerMap.put("sapModule", masterDataDao.getSapModule("1"));
		
		return headerMap;
	}
	@Override
	public List<PlantVO> getSapPlants(String companies){
		return masterDataDao.getSapPlants( companies);
	}
	@Override
	public List<PlantVO> getPlantByCompany(String sapCompany, JQueryDataTableParamModel criteria){
		return masterDataDao.getPlantByCompany( sapCompany,  criteria);
	}
	
	@Override
	public List<SapAmTcodeVO> getTcodeByModule(String pmodule) {
		// TODO Auto-generated method stub
		return masterDataDao.getTcodeByModule(pmodule);
	}
	
	@Override
	public List<SapAmTcodeVO> getTcodeByModule(String pmodule, JQueryDataTableParamModel criteria) {
		// TODO Auto-generated method stub
		return masterDataDao.getTcodeByModule(pmodule, criteria);
	}
	@Override
	public List<SapAmMasterTypeDetail> getPGroup() throws Exception {
		// TODO Auto-generated method stub
		return masterDataDao.getPGroup();
	}
	@Override
	public List<SapAmMasterTypeDetail> getPGroupJson(JQueryDataTableParamModel criteria) throws Exception {
		// TODO Auto-generated method stub
		return masterDataDao.getPGroupJson(criteria);
	}
	@Override
	public List<SapAmRolesRequestVO> getRoles(JQueryDataTableParamModel criteria)
			throws Exception {
		// TODO Auto-generated method stub
		return masterDataDao.getRoles(criteria);
	}
	
	@Override
	public List<SapUserVO> getUsersDetails(HeapUserDetails principal) throws Exception {
		return masterDataDao.getUsersDetails(principal);
	}
	@Override
	public List<SapUserVO> searchUsers(HeapUserDetails principal,String sapUserId) throws Exception {
		return masterDataDao.searchUsers(principal,sapUserId);
	}
	@Override
	public List<SapAmRiskFunMappingVO> getRiskFunctionByRequestId(String requestId) throws Exception {
		// TODO Auto-generated method stub
		return riskApproveDao.getRiskFunctionByRequestId(requestId);
	}
	@Override
	@Transactional
	public List<SapAmOtherUserVO> searchOthUsers(HeapUserDetails principal, String sapUserId) throws Exception {
		// TODO Auto-generated method stub
		return  masterDataDao.searchOthUsers(principal,sapUserId);
	}
	
	@Override
	@Transactional
	public List<HrmLoginVO> getAllEmployee(boolean isRefresh) {
		// TODO Auto-generated method stub
		return hrmLoginManager.getAllEmployee(isRefresh);
	}
	
	@PostConstruct
	public void initService() {
		getAllEmployee(false);
	}
}
