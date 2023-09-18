package com.samodule.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.ApproveRequestDao;
import com.samodule.dao.EmailSenderDao;
import com.samodule.dao.EmpHodApproveDao;
import com.samodule.dao.RequestPlantDao;
import com.samodule.dao.RequestTcodeDao;
import com.samodule.dao.SapAmUserRequestApprovalDao;
import com.samodule.dao.UserRequestDao;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmRolesAssignVO;

@Service("empHodApproveManager")
public class EmpHodApproveManagerImpl implements EmpHodApproveManager {

	@Autowired
	EmpHodApproveDao empHodApproveDao;
	@Autowired
	UserRequestDao userRequestDao;
	@Autowired
	RequestTcodeDao requestTcodeDao;
	@Autowired
	RequestPlantDao requestPlantDao;
	@Autowired
	ApproveRequestDao approveRequestDao;
	@Autowired
	SapAmUserRequestApprovalDao sapAmUserRequestApprovalDao;
	@Autowired
	EmailSenderDao emailSenderDao;

	@Override
	public List<SAPUserRequestVO> getUserEHodRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception {
		return empHodApproveDao.getUserEHodRequest(criteria, principal);
	}

	@Transactional(rollbackOn = Throwable.class)
	@Override
	public long approveRequest(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		// sapAmRolesAssignVO.setWfCode("HOD");
		List<SapAmUserRequestApproval> sapAmUserRequestApprovals = sapAmUserRequestApprovalDao
				.getApprovelWorkFlowsById(sapAmRolesAssignVO, principal);

		sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel());
		sapAmUserRequestApprovalDao.approveRequests(sapAmUserRequestApprovals, principal, sapAmRolesAssignVO.getRemarks());
		boolean mailFlag = false;
		List<String> status = sapAmUserRequestApprovalDao.getStatus(sapAmRolesAssignVO, principal);
		if (status != null && status.size() == 1 && "A".equalsIgnoreCase(status.get(0))) {
			sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel().add(new BigDecimal(1)));
			mailFlag = true;
		}

		userRequestDao.updateStatusAll(sapAmRolesAssignVO, principal);
		requestTcodeDao.updateStatusAll(sapAmRolesAssignVO, principal);
		long temp = requestPlantDao.updateStatusAll(sapAmRolesAssignVO, principal);
		if(mailFlag)
		emailSenderDao.sentEmail(new BigDecimal(sapAmRolesAssignVO.getRequestId()), "A", "A",
				sapAmRolesAssignVO.getWfLevel().intValue(), principal.getEmpCode(),
				sapAmUserRequestApprovals.get(0).getWfLevel().intValue());
		return temp;

	}

	@Transactional(rollbackOn = Throwable.class)
	@Override
	public long rejectRequest(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		// sapAmRolesAssignVO.setWfCode("HOD");
		List<SapAmUserRequestApproval> sapAmUserRequestApprovals = sapAmUserRequestApprovalDao
				.getApprovelWorkFlowsById(sapAmRolesAssignVO, principal);

		// sapAmRolesAssignVO.setStatus("A");
		// sapAmRolesAssignVO.setSapModuleCode(sapAmUserRequestApproval.getSapModule());

		sapAmUserRequestApprovalDao.rejectRequests(sapAmUserRequestApprovals, principal,sapAmRolesAssignVO.getRemarks());
//		userRequestDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid(), sapAmRolesAssignVO.getRequestId(),
//				principal);
		// sapAmRolesAssignVO.setStatus("O");
		sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel());

//		requestTcodeDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid(), sapAmRolesAssignVO.getRequestId(),
//				principal);
		requestTcodeDao.updateStatusAll(sapAmRolesAssignVO, principal);
//		requestPlantDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid(), sapAmRolesAssignVO.getRequestId(),
//				principal);
		requestPlantDao.updateStatusAll(sapAmRolesAssignVO, principal);
		long temp = userRequestDao.updateStatusAll(sapAmRolesAssignVO, principal);
		// return approveRequestDao.saveCTMApproval(sapAmRolesAssignVO, principal);
		emailSenderDao.sentEmail(new BigDecimal(sapAmRolesAssignVO.getRequestId()), "R", "R",
				sapAmUserRequestApprovals.get(0).getWfLevel().intValue(), principal.getEmpCode(),
				sapAmUserRequestApprovals.get(0).getWfLevel().intValue());
		return temp;
	}

	@Transactional(rollbackOn = Throwable.class)
	@Override
	public long approveRequestRole(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		// sapAmRolesAssignVO.setWfCode("HOD");
//		SapAmUserRequestApproval sapAmUserRequestApproval = sapAmUserRequestApprovalDao
//				.getApprovelWorkFlowByRoleEmployeCode(sapAmRolesAssignVO, principal);
		// sapAmRolesAssignVO.setStatus("A");
		List<SapAmUserRequestApproval> sapAmUserRequestApprovals = sapAmUserRequestApprovalDao
				.getApprovelWorkFlowsById(sapAmRolesAssignVO, principal);

		sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel());
		// sapAmRolesAssignVO.setSapModuleCode(sapAmUserRequestApproval.getSapModule());
		// sapAmUserRequestApprovalDao.approveRequest(sapAmUserRequestApproval,
		// principal);
		Set<String> modules = new HashSet<>();

		for (SapAmUserRequestApproval sapAmRolesRequestVO : sapAmUserRequestApprovals) {
			modules.add(sapAmRolesRequestVO.getSapModule());
			// compenies.add(t.getEntity());
		}

		sapAmUserRequestApprovalDao.approveRequests(sapAmUserRequestApprovals, principal,sapAmRolesAssignVO.getRemarks());
		List<String> status = sapAmUserRequestApprovalDao.getStatus(sapAmRolesAssignVO, principal);
		if (status != null && status.size() == 1 && "A".equalsIgnoreCase(status.get(0)))
			sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel().add(new BigDecimal(1)));
		userRequestDao.updateStatusAll(sapAmRolesAssignVO, principal);
		// sapAmRolesAssignVO.setStatus("O");
		long temp = requestPlantDao.updateStatusAllRoleEMPHOD(sapAmRolesAssignVO, modules, principal);
		emailSenderDao.sentEmail(new BigDecimal(sapAmRolesAssignVO.getRequestId()), "A", "A",
				sapAmRolesAssignVO.getWfLevel().intValue(), principal.getEmpCode(),
				sapAmUserRequestApprovals.get(0).getWfLevel().intValue());
		return temp;
	}

	@Transactional(rollbackOn = Throwable.class)
	@Override
	public long rejectRequestRole(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
//		sapAmRolesAssignVO.setWfCode("HOD");
//		SapAmUserRequestApproval sapAmUserRequestApproval = sapAmUserRequestApprovalDao
//				.getApprovelWorkFlowByRoleEmployeCode(sapAmRolesAssignVO, principal);
		// sapAmRolesAssignVO.setStatus("A");
		// sapAmRolesAssignVO.setSapModuleCode(sapAmUserRequestApproval.getSapModule());

		List<SapAmUserRequestApproval> sapAmUserRequestApprovals = sapAmUserRequestApprovalDao
				.getApprovelWorkFlowsById(sapAmRolesAssignVO, principal);
		sapAmUserRequestApprovalDao.rejectRequests(sapAmUserRequestApprovals, principal,sapAmRolesAssignVO.getRemarks());
		sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel());
		// sapAmUserRequestApprovalDao.rejectRequest(sapAmUserRequestApproval,
		// principal);

		Set<String> modules = new HashSet<>();

		for (SapAmUserRequestApproval sapAmRolesRequestVO : sapAmUserRequestApprovals) {
			modules.add(sapAmRolesRequestVO.getSapModule());
			// compenies.add(t.getEntity());
		}

		requestPlantDao.updateStatusAllRoleEMPHOD(sapAmRolesAssignVO, modules, principal);
		long temp = userRequestDao.updateStatusAll(sapAmRolesAssignVO, principal);
		// return approveRequestDao.saveCTMApproval(sapAmRolesAssignVO, principal);
		emailSenderDao.sentEmail(new BigDecimal(sapAmRolesAssignVO.getRequestId()), "R", "R",
				sapAmUserRequestApprovals.get(0).getWfLevel().intValue(), principal.getEmpCode(),
				sapAmUserRequestApprovals.get(0).getWfLevel().intValue());
		return temp;
	}

}
