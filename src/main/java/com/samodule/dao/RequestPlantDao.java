package com.samodule.dao;

import java.util.List;
import java.util.Set;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequestPlant;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.RequestPlantDTO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapAmRolesRequestVO;

public interface RequestPlantDao extends Dao<SapAmUserRequestPlant>{

	long savePlants(List<SapAmUserRequestPlant> amUserRequestPlants, HeapUserDetails principal) throws Exception;

	List<RequestPlantDTO> getRequestedPlant(Long surRecid, Long requestId,JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception;

	long submitRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;
	

	long deleteRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;

	List<RequestPlantDTO> getRequestedPlantForRequest(Long surRecid, Long requestId, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception;

	long updateStatus(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;

	long approveRequestByCTM(Integer surRecid, Integer requestId, HeapUserDetails principal)throws Exception;

	long rejectRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception;

	long updateStatusAll(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;	
	long submitRequestRole(List<SapAmRolesRequestVO> requestTcodeDTOs,String status, HeapUserDetails principal) throws Exception;

	long updateStatusAllRole(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;

	long updateStatusAllRoleEMPHOD(SapAmRolesAssignVO sapAmRolesAssignVO, Set<String> modules,
			HeapUserDetails principal) throws Exception;

	long submitRequestRoleEmpRef(List<SapAmRolesRequestVO> requestTcodeDTOs, SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal)
			throws Exception;
}
