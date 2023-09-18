package com.samodule.dao;

import java.util.List;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserAssignedRole;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.AssignedRolesVO;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmRolesAssignVO;

public interface EmpHodApproveDao extends Dao<SapAmUserAssignedRole>{


	List<SAPUserRequestVO> getUserEHodRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception;



}
