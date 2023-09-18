package com.samodule.dao;

import java.util.List;
import java.util.Set;

import com.samodule.model.SapAmRoleMaster;
import com.samodule.vo.RoleMasterVO;
import com.samodule.vo.SapAmRequestEmpRefVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapAmRolesRequestVO;

public interface AssignRoleDao extends Dao<SapAmRoleMaster>{	

	public List<RoleMasterVO> searchRoleByUserID(SapAmRolesAssignVO sapAmRolesAssignVO) throws Exception;

	public List<String> searchFunctionByUserID(SapAmRolesAssignVO sapAmRolesAssignVO);

	List<String> searchFunctionByRequestId(SapAmRolesRequestVO sapAmRolesRequestVO) throws Exception;

	List<SapAmRolesRequestVO> searchRolesByUserID(SapAmRequestEmpRefVO sapAmRequestEmpRefVO) throws Exception;
}
