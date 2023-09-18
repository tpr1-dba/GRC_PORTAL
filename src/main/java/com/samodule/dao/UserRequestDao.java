package com.samodule.dao;

import java.util.List;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequest;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.UserRequestDTO;

public interface UserRequestDao extends Dao<SapAmUserRequest>{

	public SapAmUserRequest saveRequest(SapAmUserRequest sapAmUserRequest, HeapUserDetails principal) throws Exception;
	long submitRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;
	List<UserRequestDTO> getRequestedRolesUserID(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception;
	long submitRequest(SapAmUserRequest sapAmUserRequest, HeapUserDetails principal) throws Exception;
	public long approveRequestByCTM(Integer surRecid,Integer requestId, HeapUserDetails principal) throws Exception;
	long rejectRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;
	public long updateRequestHOD(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;
	public long updateRequestBasis(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal)throws Exception;
	long updateStatusAll(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;
	SapAmUserRequest saveRequestEmpRef(SapAmUserRequest sapAmUserRequest, HeapUserDetails principal) throws Exception;	
	
}
