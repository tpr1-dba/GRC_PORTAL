package com.samodule.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import com.samodule.dao.SapAmUserRequestApprovalDao;
import com.samodule.dao.ViewWorkflowDao;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmUserRequestApprovalVO;

@Service("viewWorkflowManager")
public class ViewWorkflowManagerImpl implements ViewWorkflowManager {

	@Autowired
	ViewWorkflowDao viewWorkflowDao;
	
	@Autowired
	private RequestTcodeManagerOUser requestTcodeManagerOUser;

	@Override
	public List<SapAmUserRequestApprovalVO> getUserRequestStatus(Long surRecid,@PathVariable Long requestId,JQueryDataTableParamModel criteria, HeapUserDetails principal) {
		// TODO Auto-generated method stub
		if(surRecid>0 && requestId>0) {
			 return viewWorkflowDao.getUserRequestStatus(surRecid,requestId,criteria, principal);
		}
		return new ArrayList<>();
	}
	
	@Override
	public List<SAPUserRequestVO> getUserRequestByRequestId(String requestNo, JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception {
		if(!StringUtils.isEmpty(requestNo)) {
			return viewWorkflowDao.getUserRequestByRequestId(requestNo, criteria, principal);
		}
		return new ArrayList<>();
	}
	
	@Override
	public SapAmUserRequestApproval getApprovelWorkFlowsBySWMRecid(long swmRecid,
			HeapUserDetails principal) {
		if(swmRecid > 0) {
			return requestTcodeManagerOUser.getApprovelWorkFlowsBySWMRecid(swmRecid, principal);
		}
		return null;
	}

	
}
