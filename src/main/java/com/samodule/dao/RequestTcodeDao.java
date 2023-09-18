package com.samodule.dao;

import java.util.List;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequestTcode;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.UserRequestDTO;

public interface RequestTcodeDao extends Dao<SapAmUserRequestTcode>{

	long saveTcodes(List<SapAmUserRequestTcode> amUserRequestTcodes, HeapUserDetails principal) throws Exception;

	List<UserRequestDTO> getRequestedByUserID(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception;

	long submitRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;

	long deleteRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;

	List<UserRequestDTO> getRequestedTcode(Long surRecid, Long requestId, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception;

	List<UserRequestDTO> getRequestedTcode(Long surRecid, Long requestId) throws Exception;

	List<UserRequestDTO> getRequestedByUserID(HeapUserDetails principal) throws Exception;
	List<UserRequestDTO> getRequestedByUserID(String requestType,HeapUserDetails principal) throws Exception;

	long updateStatus(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;

	List<UserRequestDTO> getTcodesWithWithoutFunction(Long surRecid, Long requestId) throws Exception;

	long approveRequestByCTM(Integer surRecid, Integer requestId, HeapUserDetails principal)throws Exception;

	long rejectRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;

	List<UserRequestDTO> getRequestedTcodes(Long surRecid, Long requestId) throws Exception;

	long updateStatusAll(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;

	List<UserRequestDTO> getRequestedTcodesWithPlant(Long surRecid, Long requestId) throws Exception;

	long submitRequests(List<UserRequestDTO> temp, SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal)
			throws Exception;

	List<UserRequestDTO> getRequestedTcodeByRequestID(Long surRecid, Long requestId, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception;

}
