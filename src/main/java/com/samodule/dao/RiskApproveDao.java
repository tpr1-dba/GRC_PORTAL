package com.samodule.dao;

import java.util.List;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequestConflict;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SapAmRiskFunMappingVO;
import com.samodule.vo.SapAmRolesAssignVO;

public interface RiskApproveDao extends Dao<SapAmUserRequestConflict>{

	public long savAccptedRisk(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception;

	public List<SapAmRiskFunMappingVO> getRiskFunctionByRequestId(String requestId)throws Exception;



}
