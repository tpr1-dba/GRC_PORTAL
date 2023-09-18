package com.samodule.dao;

import java.util.List;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequest;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.AssignedRolesVO;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;

public interface ApproveRequestDao extends Dao<SapAmUserRequest>{

	public List<SAPUserRequestVO> getUserRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception;

	public long saveCTMApproval(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal)throws Exception ;

	public long updateRequestHOD(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal)throws Exception;

	public long updateRequestBasis(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;



}
