package com.samodule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.samodule.dao.RequestHistoryDao;
import com.samodule.dao.SapAmUserRequestApprovalDao;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmUserRequestApprovalVO;

@Service("requestHistoryManager")
public class RequestHistoryManagerImpl implements RequestHistoryManager {

	@Autowired
	RequestHistoryDao requestHistoryDao;

	@Autowired
	SapAmUserRequestApprovalDao sapAmUserRequestApprovalDao;
	
	@Override
	public List<SAPUserRequestVO> getUserRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception {
		return requestHistoryDao.getUserRequest(criteria, principal);
	}
	
	@Override
	public List<SapAmUserRequestApprovalVO> getUserRequestStatus(Long surRecid,@PathVariable Long requestId,JQueryDataTableParamModel criteria, HeapUserDetails principal) {
		// TODO Auto-generated method stub
		 return requestHistoryDao.getUserRequestStatus(surRecid,requestId,criteria, principal);
	}
	
}
