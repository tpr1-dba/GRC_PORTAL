package com.samodule.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.AssignRoleDao;
import com.samodule.dao.FunctionDao;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SapAmRiskFunMappingVO;
import com.samodule.vo.SapAmRolesAssignVO;

@Service("assignRoleManager")
public class AssignRoleManagerImpl implements AssignRoleManager {
	@Autowired
	private AssignRoleDao assignRoleDao;
	@Autowired
	private FunctionDao functionDao;
	@Autowired
	// @Qualifier(value = "processDataService")
	private ProcessDataService processDataService;

	@Override
	public List<SapAmRiskFunMappingVO> riskFindeByUserId(SapAmRolesAssignVO sapAmRolesAssignVO,
			HeapUserDetails principal) throws Exception {
		// List<RoleMasterVO> userRoles=assignRoleDao.searchRoleByUserID(
		// sapAmRolesAssignVO);

		// Set<String> function = new HashSet<String>();
		Set<String> rolCodes = new HashSet<String>();
		List<SapAmRiskFunMappingVO> confilcteds = new ArrayList<>();
		sapAmRolesAssignVO.getSapAmRolesRequestVO().forEach(vo -> {
//			RoleMasterVO roleMasterVO=new RoleMasterVO();
//			roleMasterVO.setFunCode(vo.getFunCode());
//			roleMasterVO.setFunId(vo.getFunId());
//			roleMasterVO.setRoleCode(vo.getRoleCode());
//			roleMasterVO.setRoleId(vo.getRoleId());
//			roleMasterVO.setRoleName(vo.getRoleName());
//			if (vo.getFunCode() != null)
//				function.add(vo.getFunCode());
			if (vo.getRoleCode() != null)
				rolCodes.add(vo.getRoleCode());
		});
		
		Set<String> roleFunctions = new HashSet<>( functionDao.getFunctionByRoles(rolCodes)); 
		//Set<String> functions = processDataService.getSourceData(function);
		if (roleFunctions != null && roleFunctions.size() > 0) {
			List<String> functions = assignRoleDao.searchFunctionByUserID(sapAmRolesAssignVO);
			roleFunctions.addAll(functions);
			confilcteds.addAll(processDataService.getSourceData(roleFunctions));
		}
		return confilcteds;
	}
}
