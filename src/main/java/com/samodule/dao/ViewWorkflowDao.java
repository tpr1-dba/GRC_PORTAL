package com.samodule.dao;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequest;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmUserRequestApprovalVO;

public interface ViewWorkflowDao extends Dao<SapAmUserRequest>{

	List<SapAmUserRequestApprovalVO> getUserRequestStatus(Long surRecid,@PathVariable Long requestId,JQueryDataTableParamModel criteria, HeapUserDetails principal);

	List<SAPUserRequestVO> getUserRequestByRequestId(String requestNo, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception;

}
