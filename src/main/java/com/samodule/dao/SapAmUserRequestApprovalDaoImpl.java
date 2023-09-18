package com.samodule.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.model.SapAmWorkflowMaster;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapAmUserRequestApprovalVO;

@Repository("sapAmUserRequestApprovalDao")
public class SapAmUserRequestApprovalDaoImpl extends JpaDao<SapAmUserRequestApproval>
		implements SapAmUserRequestApprovalDao {
	static final Logger log = Logger.getLogger(SapAmUserRequestApprovalDaoImpl.class.getName());

	@Override
	public long save(List<SapAmWorkflowMaster> amWorkflowMasters, SapAmRequestStatusVO sapAmRequestVO,
			HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub

		int batchSize = 10;
		int j = 0;
		Session session = currentSession();

		try {

			Iterator<SapAmWorkflowMaster> it = amWorkflowMasters.iterator();
			while (it.hasNext()) {
				SapAmWorkflowMaster sapAmWorkflowMaster = it.next();
				SapAmUserRequestApproval sapAmUserRequestApproval = new SapAmUserRequestApproval();
				sapAmUserRequestApproval.setParentRecid(new BigDecimal(sapAmRequestVO.getSurRecid()));
				sapAmUserRequestApproval.setRequestId(sapAmRequestVO.getRequestId());
				sapAmUserRequestApproval.setCompanyCode(sapAmWorkflowMaster.getCompanyCode());
				sapAmUserRequestApproval.setSapModule(sapAmWorkflowMaster.getSapModule());
				sapAmUserRequestApproval.setSbu(sapAmWorkflowMaster.getSbu());
				sapAmUserRequestApproval.setWfCode(sapAmWorkflowMaster.getWfCode());
				sapAmUserRequestApproval.setWfLevel(sapAmWorkflowMaster.getWfLevel());
				String empCode = (sapAmWorkflowMaster.getWfCode() != null
						&& sapAmWorkflowMaster.getWfCode().equalsIgnoreCase("HOD")) ? principal.getFuncReportg()
								: sapAmWorkflowMaster.getEmpCode();
				String isHOD = (sapAmWorkflowMaster.getWfCode() != null
						&& sapAmWorkflowMaster.getWfCode().equalsIgnoreCase("HOD")) ? "Y" : "N";

				if (sapAmWorkflowMaster.getWfCode() != null
						&& sapAmWorkflowMaster.getWfCode().equalsIgnoreCase("CTM")) {
					sapAmUserRequestApproval.setIsCtm("C");
				}

				else if (sapAmWorkflowMaster.getWfCode() != null
						&& sapAmWorkflowMaster.getWfCode().equalsIgnoreCase("CTMHD")) {
					sapAmUserRequestApproval.setIsCtm("H");
				} else if (sapAmWorkflowMaster.getWfCode() != null
						&& sapAmWorkflowMaster.getWfCode().equalsIgnoreCase("BAS")) {
					sapAmUserRequestApproval.setIsCtm("B");
				} else {
					sapAmUserRequestApproval.setIsCtm("N");
				}

				sapAmUserRequestApproval.setIsHod(isHOD);
				sapAmUserRequestApproval.setEmpCode(empCode);
				sapAmUserRequestApproval.setStatus("S");
				sapAmUserRequestApproval.setCreatedBy(principal.getEmpCode());
				sapAmUserRequestApproval.setCreatedOn(new Date());
				session.saveOrUpdate(sapAmUserRequestApproval);
				j++;
				if (j % batchSize == 0 && j > 0) {
					// Flush A Batch Of Updates & Release Memory
					session.flush();
					session.clear();
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}

		return j;

	}

	@Override
	public long approveRequest(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal,
			List<SapAmWorkflowMaster> sapAMWorkFLowMaster) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_APPROVALS ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			// queryBuilder.append(" , ur.WF_LEVEL = :pwflevel ");
			queryBuilder.append(" , ur.LAST_UPD_ON = :papprovedOn ");
			queryBuilder.append(" , ur.LAST_UPD_BY = :papprovedBy ");
			// queryBuilder.append(" , ur.status = :pstatus"); STATUS WF_LEVEL APPROVED_ON
			// APPROVED_BY
			queryBuilder.append(" WHERE ur.status = 'S' and ur.PARENT_RECID = :psurRecid ");
			queryBuilder.append(
					" and ur.REQUEST_ID = :prequestId and EMP_CODE =:pwfcode and WF_CODE =:pempCode and WF_LEVEL=:pwflevel and SAP_MODULE=:psapModule ");

			/*
			 * @Column(name = "SAP_MODULE") private String sapModule;
			 * 
			 * private String sbu;
			 * 
			 * private String status;
			 * 
			 * @Column(name = "WF_CODE") private String wfCode;
			 * 
			 * @Column(name = "WF_LEVEL") private BigDecimal wfLevel;
			 */
			SapAmWorkflowMaster sapAmWorkflowMaster = sapAMWorkFLowMaster.get(0);
			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", "A");
			nativeQuery.setParameter("pempCode", principal.getEmpCode());
			nativeQuery.setParameter("pwflevel", sapAmWorkflowMaster.getWfLevel());
			nativeQuery.setParameter("pwfcode", sapAmWorkflowMaster.getWfCode());
			nativeQuery.setParameter("psapModule", sapAmWorkflowMaster.getSapModule());
			nativeQuery.setParameter("papprovedOn", new Date());
			nativeQuery.setParameter("papprovedBy", principal.getEmpCode());
			nativeQuery.setParameter("psurRecid", sapAmRolesAssignVO.getSurRecid());
			nativeQuery.setParameter("prequestId", sapAmRolesAssignVO.getRequestId());
			return nativeQuery.executeUpdate();
			// tx.commit();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

	}

	@Override
	public SapAmUserRequestApproval getApprovelWorkFlowByRoleEmployeCode(SapAmRolesAssignVO sapAmRolesAssignVO,
			HeapUserDetails principal) throws Exception {
		Session session = currentSession();

		try {
			log.info("IF");
			Query sqlQuery = session.createQuery(
					"from SapAmUserRequestApproval where swmRecid =:pswmRecid and requestId=:prequestId and parentRecid=:parentRecid and empCode=:pempCode   ");
			// Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where
			// companyCode in :pcompanyCode and sapModule in :psapModule ");
			System.out.println("getSwmRecid " + sapAmRolesAssignVO.getSwmRecid() + " RequestId "
					+ sapAmRolesAssignVO.getRequestId() + " SurRecid  " + sapAmRolesAssignVO.getSurRecid()
					+ " EMP Code  " + principal.getEmpCode() + " WF Code " + sapAmRolesAssignVO.getWfCode());
			log.info("RequestId " + sapAmRolesAssignVO.getRequestId() + " SurRecid  " + sapAmRolesAssignVO.getSurRecid()
					+ " EMP Code  " + principal.getEmpCode() + " WF Code " + sapAmRolesAssignVO.getWfCode());

			sqlQuery.setParameter("pswmRecid", Long.parseLong(sapAmRolesAssignVO.getSwmRecid()));// BigDecimal
			sqlQuery.setParameter("prequestId", new BigDecimal(sapAmRolesAssignVO.getRequestId()));// BigDecimal
			sqlQuery.setParameter("parentRecid", new BigDecimal(sapAmRolesAssignVO.getSurRecid()));// BigDecimal
			sqlQuery.setParameter("pempCode", principal.getEmpCode());
			// sqlQuery.setParameter("pwfCode", sapAmRolesAssignVO.getWfCode());
			return (SapAmUserRequestApproval) sqlQuery.getSingleResult();
			// session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmUserRequestApproval >> get", e);
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public List<SapAmUserRequestApproval> getApprovelWorkFlowsById(SapAmRolesAssignVO sapAmRolesAssignVO,
			HeapUserDetails principal) throws Exception {
		Session session = currentSession();

		try {
			log.info("IF");
			Query sqlQuery = session.createQuery("from SapAmUserRequestApproval where swmRecid in :pswmRecid ");
			// Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where
			// companyCode in :pcompanyCode and sapModule in :psapModule ");
			System.out.println("getSwmRecid " + sapAmRolesAssignVO.getSwmRecid() + " RequestId "
					+ sapAmRolesAssignVO.getRequestId() + " SurRecid  " + sapAmRolesAssignVO.getSurRecid()
					+ " EMP Code  " + principal.getEmpCode() + " WF Code " + sapAmRolesAssignVO.getWfCode());
			log.info("RequestId " + sapAmRolesAssignVO.getRequestId() + " SurRecid  " + sapAmRolesAssignVO.getSurRecid()
					+ " EMP Code  " + principal.getEmpCode() + " WF Code " + sapAmRolesAssignVO.getWfCode());

			List<Long> ints = Arrays.stream(sapAmRolesAssignVO.getSwmRecid().split(","))
					.map(a -> Long.parseLong(a.trim())).collect(Collectors.toList());

			sqlQuery.setParameterList("pswmRecid", ints);

			return (List<SapAmUserRequestApproval>) sqlQuery.getResultList();
			// session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmUserRequestApproval >> get", e);
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public long approveRequest(SapAmUserRequestApproval sapAmUserRequestApproval, HeapUserDetails principal)
			throws Exception {
		Session session = currentSession();
		try {
			sapAmUserRequestApproval.setStatus("A");
			sapAmUserRequestApproval.setApprovedBy(principal.getEmpCode());
			sapAmUserRequestApproval.setApprovedOn(new Date());
			session.saveOrUpdate(sapAmUserRequestApproval);
			return 1;
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

	}

	@Override
	public List<String> getStatus(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();

		try {

			StringBuilder queryBuilder = new StringBuilder(
					" select distinct  ra.status  from SAP_AM_USER_REQUEST_APPROVALS ra where ra.REQUEST_ID =:prequestId and  ra.WF_LEVEL=:plevel ");
			Query sqlQuery = session.createSQLQuery(queryBuilder.toString());
			sqlQuery.setParameter("prequestId", sapAmRolesAssignVO.getRequestId());
			sqlQuery.setParameter("plevel", sapAmRolesAssignVO.getWfLevel());
			return (List<String>) sqlQuery.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >>  >> getStatus", e);
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public long approveRequests(List<SapAmUserRequestApproval> sapAmUserRequestApprovals, HeapUserDetails principal,String remarks)
			throws Exception {
		int batchSize = 10;
		int j = 0;
		Session session = currentSession();

		try {
			Iterator<SapAmUserRequestApproval> it = sapAmUserRequestApprovals.iterator();
			while (it.hasNext()) {
				SapAmUserRequestApproval sapAmUserRequestApproval = it.next();
				sapAmUserRequestApproval.setStatus("A");
				sapAmUserRequestApproval.setApprovedBy(principal.getEmpCode());
				sapAmUserRequestApproval.setApprovedOn(new Date());
				sapAmUserRequestApproval.setRemarks((remarks!=null && remarks.trim().length()>0)?  remarks.substring(0, Math.min(remarks.length(), 1999)) :"");
				session.saveOrUpdate(sapAmUserRequestApproval);
				j++;
				if (j % batchSize == 0 && j > 0) {
					// Flush A Batch Of Updates & Release Memory
					session.flush();
					session.clear();
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}

		return j;

	}

	@Override
	public long rejectRequests(List<SapAmUserRequestApproval> sapAmUserRequestApprovals, HeapUserDetails principal,String remarks)
			throws Exception {
		int batchSize = 10;
		int j = 0;
		Session session = currentSession();

		try {
			Iterator<SapAmUserRequestApproval> it = sapAmUserRequestApprovals.iterator();
			while (it.hasNext()) {
				SapAmUserRequestApproval sapAmUserRequestApproval = it.next();
				sapAmUserRequestApproval.setStatus("R");
				sapAmUserRequestApproval.setApprovedBy(principal.getEmpCode());
				sapAmUserRequestApproval.setApprovedOn(new Date());
				sapAmUserRequestApproval.setRemarks((remarks!=null && remarks.trim().length()>0)?  remarks.substring(0, Math.min(remarks.length(), 1999)):"");
				session.saveOrUpdate(sapAmUserRequestApproval);
				j++;
				if (j % batchSize == 0 && j > 0) {
					// Flush A Batch Of Updates & Release Memory
					session.flush();
					session.clear();
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}

		return j;

	}

	@Override
	public long rejectRequest(SapAmUserRequestApproval sapAmUserRequestApproval, HeapUserDetails principal)
			throws Exception {
		Session session = currentSession();
		try {
			sapAmUserRequestApproval.setStatus("R");
			sapAmUserRequestApproval.setApprovedBy(principal.getEmpCode());
			sapAmUserRequestApproval.setApprovedOn(new Date());
			session.saveOrUpdate(sapAmUserRequestApproval);
			return 1;
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

	}

	@Transactional
	@Override
	public List<SapAmUserRequestApprovalVO> getWorkFLowByLogin(String loginId)  {
		// TODO Auto-generated method stub
		log.info(loginId);

		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" select distinct rp.company_code AS companyCode, ");
			queryBuilder.append(" rp.SAP_MODULE  as sapModule , rp.EMP_CODE as empCode ");
			queryBuilder.append(" ,rp.sbu AS sbu, rp.status AS status, rp.wf_code AS wfCode, rp.wf_level AS wfLevel  ");
			queryBuilder.append("  FROM sap_am_user_request_approvals rp ");
			queryBuilder.append(" where rp.EMP_CODE=:pempCode and rp.status='S' ");

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("companyCode", StandardBasicTypes.STRING).addScalar("empCode", StandardBasicTypes.STRING)
					.addScalar("sapModule", StandardBasicTypes.STRING).addScalar("sbu", StandardBasicTypes.STRING)
					.addScalar("status", StandardBasicTypes.STRING).addScalar("wfCode", StandardBasicTypes.STRING)
					.addScalar("wfLevel", StandardBasicTypes.BIG_DECIMAL);

			sqlQuery.setParameter("pempCode", loginId);
			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmUserRequestApprovalVO.class));
			// session.getTransaction().commit();
			System.out.println("ROLESSSSSSSSSSSSS : "+Arrays.toString(sqlQuery.getResultList().toArray()));
			return (List<SapAmUserRequestApprovalVO>) sqlQuery.getResultList();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> ApproveRequestDaoImpl >> getUserRequest", e);
			e.printStackTrace();
			//throw e;
		}
		return null;

	}
	
	
	@Override
	@Transactional
	public List<SapAmUserRequestApproval> getApprovelWorkFlowsBySWMRecid(long swmRecid,
			HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			Query sqlQuery = session.createQuery("from SapAmUserRequestApproval where swmRecid = :swmRecid ");
			// Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where
			// companyCode in :pcompanyCode and sapModule in :psapModule ");
			log.info("swmRecid " + swmRecid + " EMP Code  " + principal.getEmpCode() );
			sqlQuery.setLong("swmRecid", swmRecid);

			return (List<SapAmUserRequestApproval>) sqlQuery.getResultList();
			// session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmUserRequestApproval >> getApprovelWorkFlowsBySWMRecid", e);
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	@Transactional
	public String updateSapAmUserEmpCodeBySwmRecid(long swmRecid, String empCode, HeapUserDetails principal) {
		String oldEmpCode="NA";
		try {
			Session session = currentSession();
			List<SapAmUserRequestApproval> userList = getApprovelWorkFlowsBySWMRecid(swmRecid, principal);
			if(!CollectionUtils.isEmpty(userList)) {
				SapAmUserRequestApproval existSapUser = userList.get(0);
				oldEmpCode=existSapUser.getEmpCode();
				existSapUser.setEmpCode(empCode);
				if(Objects.nonNull(session.save(existSapUser)))
					log.info(empCode+ " saved Successfully on "+ swmRecid);
				oldEmpCode="Updated Successfully";
			}
		} catch (Exception e) {
			log.error("ERROR >> SapAmUserRequestApproval >> updateSapAmUserEmpCodeBySwmRecid", e);
			e.printStackTrace();
		}
		return oldEmpCode;
	}
	
}
