package com.samodule.dao;

import java.util.List;
import java.util.Set;

import com.samodule.model.SapAmWorkflowMaster;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.UserRequestDTO;

public interface SapAMWorkFLowMasterDao {
	public List<SapAmWorkflowMaster> getRoleByLogin(String loginId);
	//public List<SapAmWorkflowMaster> getWorkFLows(List<UserRequestDTO> requestTcodeDTOs, HeapUserDetails principal) throws Exception;
	public List<SapAmWorkflowMaster> getWorkFLows(Set<String> modules, Set<String> compenies,
			HeapUserDetails principal)throws Exception;
	public List<SapAmWorkflowMaster> getWorkFLowsByEmpCode(Set<String> modules, Set<String> compenies,
			HeapUserDetails principal) throws Exception;
	public List<SapAmWorkflowMaster> getWorkFLowsByRole(Set<String> modules, Set<String> compenies,
			HeapUserDetails principal) throws Exception;
	List<SapAmWorkflowMaster> getWorkFLowsForSBU(Set<String> modules, Set<String> compenies, List<String> sbus,
			HeapUserDetails principal) throws Exception;	
	List<SapAmWorkflowMaster> getWorkFLowsForSBUSQL(Set<String> modules, Set<String> compenies, List<String> sbus,
			HeapUserDetails principal) throws Exception;	
}
