package com.samodule.service;

import java.util.List;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserAssignedRole;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.AssignedRolesVO;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmUserAssignedRoleVO;

public interface BasisApproveManager {
	public List<SAPUserRequestVO> getUserRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception;
	List<SapAmUserAssignedRole> getAssignedRole(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception;

	public long updateAssignedRoles(AssignedRolesVO assignedRolesVO, HeapUserDetails principal) throws Exception;
	public long saveAssignedRoles(AssignedRolesVO assignedRolesVO, HeapUserDetails principal)throws Exception;
	long updateRequestBasis(AssignedRolesVO assignedRolesVO, HeapUserDetails principal) throws Exception;
	List<SapAmUserAssignedRoleVO> getAssignedRoleWIthSensetive(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception;

	// long saveHODApproval(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails
	// principal) throws Exception;

}
