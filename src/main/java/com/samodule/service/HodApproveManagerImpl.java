package com.samodule.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.ApproveRequestDao;
import com.samodule.dao.EmailSenderDao;
import com.samodule.dao.HodApproveDao;
import com.samodule.dao.RequestPlantDao;
import com.samodule.dao.RequestTcodeDao;
import com.samodule.dao.RiskApproveDao;
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

@Service("hodApproveManager")
public class HodApproveManagerImpl implements HodApproveManager {
	
    @Autowired
    HodApproveDao hodApproveDao;
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
	RiskApproveDao riskApproveDao;
    @Autowired
    EmailSenderDao emailSenderDao;
    @Override
	public List<SAPUserRequestVO> getUserRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception{
		return hodApproveDao.getUserRequest(criteria, principal);
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
			JQueryDataTableParamModel criteria) throws Exception{
    	return hodApproveDao.getAssignedRole( principal,  requestId,  surRecid, criteria);
    }
    @Override
    public List<SapAmUserAssignedRoleVO> getAssignedRoleWIthSensetive(HeapUserDetails principal, long requestId, long surRecid,
    		JQueryDataTableParamModel criteria) throws Exception{
    	return hodApproveDao.getAssignedRoleWIthSensetive( principal,  requestId,  surRecid, criteria);
    }
    
    @Transactional(rollbackOn = Throwable.class)
	@Override
	public long updateRequestHOD(AssignedRolesVO assignedRolesVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub	
    	SapAmRolesAssignVO sapAmRolesAssignVO=new SapAmRolesAssignVO();
    	sapAmRolesAssignVO.setSwmRecid(assignedRolesVO.getSwmRecid());
    	sapAmRolesAssignVO.setSurRecid(assignedRolesVO.getSurRecid());
    	sapAmRolesAssignVO.setRequestId(assignedRolesVO.getRequestId());
    	
//    	SapAmUserRequestApproval sapAmUserRequestApproval = sapAmUserRequestApprovalDao
//				.getApprovelWorkFlowByRoleEmployeCode(sapAmRolesAssignVO, principal);
		// sapAmRolesAssignVO.setStatus("A");
		//sapAmUserRequestApprovalDao.approveRequest(sapAmUserRequestApproval, principal);
    	List<SapAmUserRequestApproval> sapAmUserRequestApprovals = sapAmUserRequestApprovalDao
				.getApprovelWorkFlowsById(sapAmRolesAssignVO, principal);
    	sapAmUserRequestApprovalDao.rejectRequests(sapAmUserRequestApprovals, principal,assignedRolesVO.getRemarks());
    	
    	
    	
    	SapAmRequestStatusVO sapAmRequestVO=new SapAmRequestStatusVO();
    	sapAmRequestVO.setStatus("R");
    	sapAmRequestVO.setSurRecid(new Long (assignedRolesVO.getSurRecid()));
    	sapAmRequestVO.setRequestId(new BigDecimal(assignedRolesVO.getRequestId())); 
    	sapAmRequestVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel());
    	userRequestDao.updateRequestHOD(sapAmRequestVO,  principal);
    	assignedRolesVO.setStatus("R");
    	long temp= hodApproveDao.saveAssignedRoles(assignedRolesVO, principal);
    	emailSenderDao.sentEmail(new BigDecimal(sapAmRolesAssignVO.getRequestId()), "R", "R",
				sapAmUserRequestApprovals.get(0).getWfLevel().intValue(), principal.getEmpCode(),
				sapAmUserRequestApprovals.get(0).getWfLevel().intValue());
    	return temp;
    }
	
    
    
    @Transactional(rollbackOn = Throwable.class)
	@Override
	public long updateAssignedRoles(AssignedRolesVO assignedRolesVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		return hodApproveDao.updateAssignedRolesHOD(assignedRolesVO, principal);
	}
    
    
    
    @Transactional(rollbackOn = Throwable.class)
	@Override
	//public long saveAssignedRoles(AssignedRolesVO assignedRolesVO, HeapUserDetails principal) throws Exception {
    public long saveAssignedRoles(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
    	//swmRecid=:pswmRecid and requestId=:prequestId and parentRecid=:parentRecid and empCode=:pempCode
//    	SapAmRolesAssignVO sapAmRolesAssignVO=new SapAmRolesAssignVO();
//    	sapAmRolesAssignVO.setSwmRecid(assignedRolesVO.getSwmRecid());
//    	sapAmRolesAssignVO.setSurRecid(assignedRolesVO.getSurRecid());
//    	sapAmRolesAssignVO.setRequestId(assignedRolesVO.getRequestId());
    	if ("Y".equalsIgnoreCase(sapAmRolesAssignVO.getIsSOD()) && sapAmRolesAssignVO.getConfilcteds() != null
				&& sapAmRolesAssignVO.getConfilcteds().size() > 0) {
			riskApproveDao.savAccptedRisk(sapAmRolesAssignVO, principal);
		}
//    	SapAmUserRequestApproval sapAmUserRequestApproval = sapAmUserRequestApprovalDao
//				.getApprovelWorkFlowByRoleEmployeCode(sapAmRolesAssignVO, principal);
		// sapAmRolesAssignVO.setStatus("A");
		//sapAmUserRequestApprovalDao.approveRequest(sapAmUserRequestApproval, principal);
    	boolean mailFlag = false;
    	List<SapAmUserRequestApproval> sapAmUserRequestApprovals = sapAmUserRequestApprovalDao
				.getApprovelWorkFlowsById(sapAmRolesAssignVO, principal);
		
		sapAmRolesAssignVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel());
		SapAmRequestStatusVO sapAmRequestVO=new SapAmRequestStatusVO();
		sapAmRequestVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel());
		sapAmUserRequestApprovalDao.approveRequests(sapAmUserRequestApprovals, principal,sapAmRolesAssignVO.getRemarks());
		List<String> status= sapAmUserRequestApprovalDao.getStatus(sapAmRolesAssignVO,principal);
		if(status!=null && status.size()==1 && "A".equalsIgnoreCase(status.get(0))) {
			sapAmRequestVO.setWfLevel(sapAmUserRequestApprovals.get(0).getWfLevel().add(new BigDecimal(1)));   	
			mailFlag = true;
		}
    	sapAmRequestVO.setStatus(sapAmRolesAssignVO.getStatus());
    	sapAmRequestVO.setSurRecid(new Long (sapAmRolesAssignVO.getSurRecid()));
    	sapAmRequestVO.setRequestId(new BigDecimal(sapAmRolesAssignVO.getRequestId()));     	
    	userRequestDao.updateRequestHOD(sapAmRequestVO,  principal);    	
    	long temp= hodApproveDao.saveAssignedRoles(sapAmRolesAssignVO, principal);
    	if(mailFlag)
    		emailSenderDao.sentEmail(new BigDecimal(sapAmRolesAssignVO.getRequestId()), "A", "A",
    				sapAmRequestVO.getWfLevel().intValue(), principal.getEmpCode(),
    				sapAmUserRequestApprovals.get(0).getWfLevel().intValue());
    	return temp;
    	
	}
    
    

}
