package com.samodule.dao;

import java.util.List;

import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.model.SapAmWorkflowMaster;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapAmUserRequestApprovalVO;

public interface SapAmUserRequestApprovalDao {
	
	public long save(List<SapAmWorkflowMaster> amWorkflowMasters, SapAmRequestStatusVO sapAmRequestVO,HeapUserDetails principal) throws Exception;

	public long approveRequest(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal,
			List<SapAmWorkflowMaster> sapAMWorkFLowMaster) throws Exception;

	public SapAmUserRequestApproval getApprovelWorkFlowByRoleEmployeCode(SapAmRolesAssignVO sapAmRolesAssignVO,
			HeapUserDetails principal) throws Exception;

	long approveRequest(SapAmUserRequestApproval sapAmUserRequestApproval, HeapUserDetails principal) throws Exception;

	long rejectRequest(SapAmUserRequestApproval sapAmUserRequestApproval, HeapUserDetails principal) throws Exception;

	List<SapAmUserRequestApproval> getApprovelWorkFlowsById(SapAmRolesAssignVO sapAmRolesAssignVO,
			HeapUserDetails principal) throws Exception;

	long approveRequests(List<SapAmUserRequestApproval> sapAmUserRequestApprovals, HeapUserDetails principal,String remarks)
			throws Exception;

	long rejectRequests(List<SapAmUserRequestApproval> sapAmUserRequestApprovals, HeapUserDetails principal,String remarks)
			throws Exception;

	List<String> getStatus(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;

	List<SapAmUserRequestApprovalVO> getWorkFLowByLogin(String loginId) ;

	List<SapAmUserRequestApproval> getApprovelWorkFlowsBySWMRecid(long swmRecid, HeapUserDetails principal)
			throws Exception;
	
	String updateSapAmUserEmpCodeBySwmRecid(long swmRecid, String empCode, HeapUserDetails principal);
	
}
