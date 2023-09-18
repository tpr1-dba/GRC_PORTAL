package com.samodule.service;

import java.util.List;

import com.samodule.security.HeapUserDetails;
import com.samodule.vo.Confilcted;
import com.samodule.vo.SapAmRiskFunMappingVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapUserVO;

public interface AssignRoleManager {	
	
	List<SapAmRiskFunMappingVO> riskFindeByUserId(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal)
			throws Exception;
}
