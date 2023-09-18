package com.samodule.service;

import java.util.List;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;

public interface ApproveRequestManager {
	public List<SAPUserRequestVO> getUserRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception;

	long saveCTMApproval(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;

	long updateRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;

	long rejectRequestCTM(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;

	
}
