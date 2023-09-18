package com.samodule.service;

import java.util.List;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmRolesAssignVO;

public interface EmpHodApproveManager {
	public List<SAPUserRequestVO> getUserEHodRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception;

	long approveRequest(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;

	long rejectRequest(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;

	long approveRequestRole(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;

	long rejectRequestRole(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;

	// long saveHODApproval(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails
	// principal) throws Exception;

}
