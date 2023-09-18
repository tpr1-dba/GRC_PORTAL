package com.samodule.service;

import java.util.List;
import java.util.Map;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.RequestPlantDTO;
import com.samodule.vo.SapAmRequestEmpRefVO;
import com.samodule.vo.SapAmRequestRoleVO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRequestVO;
import com.samodule.vo.UserRequestDTO;

public interface RequestTcodeManagerOUser {

	long createTcodeRequest(SapAmRequestVO sapAmRequestVO, HeapUserDetails principal) throws Exception;
	long createRoleRequest(SapAmRequestRoleVO sapAmRequestRoleVO, HeapUserDetails principal) throws Exception;
	List<UserRequestDTO> getRequestedByUserID(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception;
	List<RequestPlantDTO> getRequestedPlant( Long surRecid, Long requestId,JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception;
	Map<String, String> submitRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;
	long deleteTcodes(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;
	long deletePlantsPurchegroup(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;
	List<UserRequestDTO> getRequestedTcode(Long surRecid, Long requestId, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception;
	List<UserRequestDTO> getRequestedRolesUserID(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception;
	List<RequestPlantDTO> getRequestedPlantForRequest(Long surRecid, Long requestId, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception;
	List<UserRequestDTO> getRequestedByUserID(HeapUserDetails principal) throws Exception;
	long updateTcodes(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;
	long updateplantsPurchegroup(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;
	Map<String, String> submitRequestRole(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;
	List<UserRequestDTO> getRequestedTcodeByRequestID(Long surRecid, Long requestId, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception;
	 Map<String, String> submitRequestEmpRef(SapAmRequestEmpRefVO sapAmRequestEmpRefVO, HeapUserDetails principal) throws Exception;
	SapAmUserRequestApproval getApprovelWorkFlowsBySWMRecid(long swmRecid, HeapUserDetails principal);

}
