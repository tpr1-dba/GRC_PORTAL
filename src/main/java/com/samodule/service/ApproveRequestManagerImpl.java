package com.samodule.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.ApproveRequestDao;
import com.samodule.dao.EmailSenderDao;
import com.samodule.dao.RequestPlantDao;
import com.samodule.dao.RequestTcodeDao;
import com.samodule.dao.RiskApproveDao;
import com.samodule.dao.SapAmUserRequestApprovalDao;
import com.samodule.dao.UserRequestDao;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;

@Service("approveRequestManager")
public class ApproveRequestManagerImpl implements ApproveRequestManager {

	@Autowired
	ApproveRequestDao approveRequestDao;
	@Autowired
	UserRequestDao userRequestDao;
	@Autowired
	RequestTcodeDao requestTcodeDao;
	@Autowired
	RequestPlantDao requestPlantDao;
	@Autowired
	RiskApproveDao riskApproveDao;
//	@Autowired
//	SapAMWorkFLowMasterDao sapAMWorkFLowMasterDao;
	@Autowired
	SapAmUserRequestApprovalDao sapAmUserRequestApprovalDao;
	@Autowired
	EmailSenderDao emailSenderDao;

	@Override
	public List<SAPUserRequestVO> getUserRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception {
		return approveRequestDao.getUserRequest(criteria, principal);
	}

	@Transactional(rollbackOn = Throwable.class)
	@Override
	public long saveCTMApproval(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		if ("Y".equalsIgnoreCase(sapAmRolesAssignVO.getIsSOD()) && sapAmRolesAssignVO.getConfilcteds() != null
				&& sapAmRolesAssignVO.getConfilcteds().size() > 0) {
			riskApproveDao.savAccptedRisk(sapAmRolesAssignVO, principal);
		}
		// List<SapAmWorkflowMaster>
		// SapAMWorkFLowMaster=sapAMWorkFLowMasterDao.getRoleByLogin(principal.getEmpCode());
		// sapAmRolesAssignVO.setWfCode("CTM");
//		SapAmUserRequestApproval sapAmUserRequestApproval = sapAmUserRequestApprovalDao
//				.getApprovelWorkFlowByRoleEmployeCode(sapAmRolesAssignVO, principal);
		// sapAmRolesAssignVO.setStatus("A");
	 	List<SapAmUserRequestApproval> sapAmUserRequestApprovals = sapAmUserRequestApprovalDao
				.getApprovelWorkFlowsById(sapAmRolesAssignVO, principal);
		//sapAmUserRequestApprovalDao.approveRequest(sapAmUserRequestApproval, principal);
	 	sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel());
		SapAmRequestStatusVO sapAmRequestVO=new SapAmRequestStatusVO();
		sapAmRequestVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel());
		sapAmUserRequestApprovalDao.approveRequests(sapAmUserRequestApprovals, principal,sapAmRolesAssignVO.getRemarks());
	 	
	 	
	 	
//		userRequestDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid(), sapAmRolesAssignVO.getRequestId(),
//				principal);
		//sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApproval.getWfLevel());
        boolean mailFlag=false;
		List<String> status = sapAmUserRequestApprovalDao.getStatus(sapAmRolesAssignVO, principal);
		if (status != null && status.size() == 1 && "A".equalsIgnoreCase(status.get(0))) {
			sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel().add(new BigDecimal(1)));
			mailFlag=true;
		}

//		if ("CTMHD".equalsIgnoreCase(sapAmUserRequestApproval.getWfCode())) {
//			sapAmRolesAssignVO.setStatus("B");
//			userRequestDao.updateStatusAll(sapAmRolesAssignVO, principal);
//		} else if ("CTM".equalsIgnoreCase(sapAmUserRequestApproval.getWfCode())) {
//			sapAmRolesAssignVO.setStatus("H");
//			userRequestDao.updateStatusAll(sapAmRolesAssignVO, principal);}
//		requestTcodeDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid(), sapAmRolesAssignVO.getRequestId(),
//				principal);
		// sapAmRolesAssignVO.setStatus("A");
		
		requestTcodeDao.updateStatusAll(sapAmRolesAssignVO, principal);
//		requestPlantDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid(), sapAmRolesAssignVO.getRequestId(),
//				principal);
		 requestPlantDao.updateStatusAll(sapAmRolesAssignVO, principal);
		 approveRequestDao.saveCTMApproval(sapAmRolesAssignVO, principal);
		 long temp= userRequestDao.updateStatusAll(sapAmRolesAssignVO, principal);
		 if(mailFlag)
		 emailSenderDao.sentEmail(new BigDecimal(sapAmRolesAssignVO.getRequestId()), "A", "A", sapAmUserRequestApprovals.get(0).getWfLevel().add(new BigDecimal(1)).intValue(), principal.getEmpCode(),sapAmUserRequestApprovals.get(0).getWfLevel().intValue());
         return temp;
	}

	@Transactional(rollbackOn = Throwable.class)
	@Override
	public long updateRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		requestTcodeDao.rejectRequest(sapAmRequestVO, principal);
		requestPlantDao.rejectRequest(sapAmRequestVO, principal);
		return userRequestDao.rejectRequest(sapAmRequestVO, principal);
	}

	@Transactional(rollbackOn = Throwable.class)
	@Override
	public long rejectRequestCTM(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		sapAmRolesAssignVO.setWfCode("CTM");
//		SapAmUserRequestApproval sapAmUserRequestApproval = sapAmUserRequestApprovalDao
//				.getApprovelWorkFlowByRoleEmployeCode(sapAmRolesAssignVO, principal);
		List<SapAmUserRequestApproval> sapAmUserRequestApprovals = sapAmUserRequestApprovalDao
				.getApprovelWorkFlowsById(sapAmRolesAssignVO, principal);
		
		// sapAmRolesAssignVO.setStatus("A");
		sapAmUserRequestApprovalDao.rejectRequests(sapAmUserRequestApprovals, principal,sapAmRolesAssignVO.getRemarks());
//		userRequestDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid(), sapAmRolesAssignVO.getRequestId(),
//				principal);
		// sapAmRolesAssignVO.setStatus("O");

//		requestTcodeDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid(), sapAmRolesAssignVO.getRequestId(),
//				principal);
		if ("T".equalsIgnoreCase(sapAmRolesAssignVO.getRequestType())) {
			requestTcodeDao.updateStatusAll(sapAmRolesAssignVO, principal);
//		requestPlantDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid(), sapAmRolesAssignVO.getRequestId(),
//				principal);
			requestPlantDao.updateStatusAll(sapAmRolesAssignVO, principal);
		} else if ("R".equalsIgnoreCase(sapAmRolesAssignVO.getRequestType())) {
			requestPlantDao.updateStatusAllRole(sapAmRolesAssignVO, principal);
		}
		long temp= userRequestDao.updateStatusAll(sapAmRolesAssignVO, principal);
		
		emailSenderDao.sentEmail(new BigDecimal(sapAmRolesAssignVO.getRequestId()), "R", "R", sapAmUserRequestApprovals.get(0).getWfLevel().intValue(), principal.getEmpCode(),sapAmUserRequestApprovals.get(0).getWfLevel().intValue());
		
		return temp;
	}
}
