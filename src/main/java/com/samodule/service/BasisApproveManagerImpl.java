package com.samodule.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.ApproveRequestDao;
import com.samodule.dao.BasisApproveDao;
import com.samodule.dao.EmailSenderDao;
import com.samodule.dao.RequestPlantDao;
import com.samodule.dao.RequestTcodeDao;
import com.samodule.dao.SapAmUserRequestApprovalDao;
import com.samodule.dao.UserRequestDao;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserAssignedRole;
import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.AssignedRolesVO;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapAmUserAssignedRoleVO;

@Service("basisApproveManager")
public class BasisApproveManagerImpl implements BasisApproveManager {

	@Autowired
	BasisApproveDao basisApproveDao;
	@Autowired
	UserRequestDao userRequestDao;
	@Autowired
	RequestTcodeDao requestTcodeDao;
	@Autowired
	RequestPlantDao requestPlantDao;
	@Autowired
	ApproveRequestDao approveRequestDao;
	@Autowired
	EmailSenderDao emailSenderDao;
	@Autowired
	SapAmUserRequestApprovalDao sapAmUserRequestApprovalDao;

	@Override
	public List<SAPUserRequestVO> getUserRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception {
		return basisApproveDao.getUserRequest(criteria, principal);
	}

//    @Transactional(rollbackOn = Throwable.class)
//    @Override
//    public long saveCTMApproval(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
//		// TODO Auto-generated method stub
//
//		
//
//    	userRequestDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid() ,sapAmRolesAssignVO.getRequestId(),  principal);
//    	requestTcodeDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid() ,sapAmRolesAssignVO.getRequestId(),  principal);
//    	requestPlantDao.approveRequestByCTM(sapAmRolesAssignVO.getSurRecid() ,sapAmRolesAssignVO.getRequestId(),  principal);
//    	return approveRequestDao.saveCTMApproval(sapAmRolesAssignVO, principal);
//
//	}

	@Override
	public List<SapAmUserAssignedRole> getAssignedRole(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception {
		return basisApproveDao.getAssignedRole(principal, requestId, surRecid, criteria);
	}
	@Override
	public List<SapAmUserAssignedRoleVO> getAssignedRoleWIthSensetive(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception {
		return basisApproveDao.getAssignedRoleWIthSensetive(principal, requestId, surRecid, criteria);
	}

	@Transactional(rollbackOn = Throwable.class)
	@Override
	public long updateRequestBasis(AssignedRolesVO assignedRolesVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub

		SapAmRolesAssignVO sapAmRolesAssignVO = new SapAmRolesAssignVO();
		sapAmRolesAssignVO.setSwmRecid(assignedRolesVO.getSwmRecid());
		sapAmRolesAssignVO.setSurRecid(assignedRolesVO.getSurRecid());
		sapAmRolesAssignVO.setRequestId(assignedRolesVO.getRequestId());

		List<SapAmUserRequestApproval> sapAmUserRequestApprovals = sapAmUserRequestApprovalDao
				.getApprovelWorkFlowsById(sapAmRolesAssignVO, principal);


		sapAmUserRequestApprovalDao.rejectRequests(sapAmUserRequestApprovals, principal,assignedRolesVO.getRemarks());
		sapAmRolesAssignVO.setStatus(assignedRolesVO.getStatus());
		sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel());
		
		userRequestDao.updateStatusAll(sapAmRolesAssignVO, principal);		
		
		SapAmRequestStatusVO sapAmRequestVO = new SapAmRequestStatusVO();
		sapAmRequestVO.setStatus("R");
		sapAmRequestVO.setSurRecid(new Long(assignedRolesVO.getSurRecid()));
		sapAmRequestVO.setRequestId(new BigDecimal(assignedRolesVO.getRequestId()));
	
		long temp= approveRequestDao.updateRequestBasis(sapAmRequestVO, principal);
		emailSenderDao.sentEmail(new BigDecimal(sapAmRolesAssignVO.getRequestId()), "R", "R",
				sapAmUserRequestApprovals.get(0).getWfLevel().intValue(), principal.getEmpCode(),
				sapAmUserRequestApprovals.get(0).getWfLevel().intValue());
		return temp;
	}

	@Transactional(rollbackOn = Throwable.class)
	@Override
	public long updateAssignedRoles(AssignedRolesVO assignedRolesVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		return basisApproveDao.updateAssignedRolesBasis(assignedRolesVO, principal);
	}

	@Transactional(rollbackOn = Throwable.class)
	@Override
	public long saveAssignedRoles(AssignedRolesVO assignedRolesVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub

		SapAmRolesAssignVO sapAmRolesAssignVO = new SapAmRolesAssignVO();
		sapAmRolesAssignVO.setSwmRecid(assignedRolesVO.getSwmRecid());
		sapAmRolesAssignVO.setSurRecid(assignedRolesVO.getSurRecid());
		sapAmRolesAssignVO.setRequestId(assignedRolesVO.getRequestId());

		List<SapAmUserRequestApproval> sapAmUserRequestApprovals = sapAmUserRequestApprovalDao
				.getApprovelWorkFlowsById(sapAmRolesAssignVO, principal);
		
		sapAmUserRequestApprovalDao.approveRequests(sapAmUserRequestApprovals, principal,assignedRolesVO.getRemarks());
		
		sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel());
		//List<String> status= sapAmUserRequestApprovalDao.getStatus(sapAmRolesAssignVO,principal);
//		if(status!=null && status.size()==1 && "A".equalsIgnoreCase(status.get(0))) {
//			sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel().add(new BigDecimal(1)));	
//			sapAmRolesAssignVO.setStatus("C");
//		userRequestDao.updateStatusAll(sapAmRolesAssignVO, principal);}
		
		sapAmRolesAssignVO.setStatus("C");
		userRequestDao.updateStatusAll(sapAmRolesAssignVO, principal);

		long temp=basisApproveDao.saveAssignedRoles(assignedRolesVO, principal);
		
		emailSenderDao.sentEmail(new BigDecimal(sapAmRolesAssignVO.getRequestId()), "C", "A",
				sapAmRolesAssignVO.getWfLevel().intValue(), principal.getEmpCode(),
				sapAmUserRequestApprovals.get(0).getWfLevel().intValue());
		return temp;
	}

}
