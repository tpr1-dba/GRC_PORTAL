package com.samodule.dao;

import java.util.List;
import java.util.Set;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmRoleEmpMapping;
import com.samodule.model.SapAmRoleMaster;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesRequestVO;
import com.samodule.vo.SapUserVO;

public interface UserRolesDao extends Dao<SapAmRoleEmpMapping>{	
	public List<SapUserVO> list(JQueryDataTableParamModel criteria) throws Exception;
	public List<SapUserVO> list(HeapUserDetails principal,JQueryDataTableParamModel criteria) throws Exception;

	public List<SapAmRoleMaster> getRoles(String entity,JQueryDataTableParamModel criteria)
			throws Exception;
	public List<SapAmRoleMaster> getRolesByTcode(String entity,SapAmRolesRequestVO sapAmRolesRequestVO, JQueryDataTableParamModel criteria) throws Exception;	

	public List<SapAmRoleEmpMapping> getRoleByUser(String sapEmpCode) throws Exception;
	public List<SapAmRolesRequestVO> getRolesByTcodes(HeapUserDetails principal,String viewName,
			SapAmRolesRequestVO sapAmRolesRequestVO, JQueryDataTableParamModel criteria)throws Exception;
	public List<SapAmRolesRequestVO> getRolesByRequest(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria)  throws Exception;
	List<SapAmRolesRequestVO> getRolesModules(SapAmRequestStatusVO sapAmRequestVO) throws Exception;
	List<SapAmRolesRequestVO> getRolesByRequestEHOD(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception;
	List<SapAmRolesRequestVO> getRolesByRequestEmpCode(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception;
	List<SapAmRolesRequestVO> getRoles9999Plant(HeapUserDetails principal, String viewName,
			Set<Long> surtRecids, JQueryDataTableParamModel criteria) throws Exception;
	
}
