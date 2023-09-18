package com.samodule.dao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmRisk;
import com.samodule.model.SapAmUserAssignedRole;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.vo.AssignedRolesVO;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapAmRolesRequestVO;
import com.samodule.vo.SapAmUserAssignedRoleVO;

@Repository("hodApproveDao")
public class HodApproveDaoImpl extends JpaDao<SapAmUserAssignedRole> implements HodApproveDao {
	static final Logger log = Logger.getLogger(HodApproveDaoImpl.class.getName());

	@Transactional
	@Override
	public List<SAPUserRequestVO> getUserRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception {
		Session session = currentSession();
    	try {
    		StringBuilder queryBuilder = new StringBuilder(
					" select sys.ENTITY_SAP_CODE as entity, sys.COMPANY as company, e.EMP_CODE as hrisCode, e.EMPLOYEE_NAME as employeeName, ");

			queryBuilder.append(" E.DEPT_CODE as department, ");
			queryBuilder.append(" HRIS_GET.DESIG_CODE(E.DESIG_CODE) as designation, ");
			queryBuilder.append(" HRIS_GET.LOC_CODE(E.LOC_CODE) as location , ");
			queryBuilder.append(" ur.sur_recid as surRecid ,ur.request_id as requestId,ur.sap_company_code as sapCompanyCode, ");
			queryBuilder.append(" DECODE (upper(ur.request_type),'T' ,'T-code Request', 'R', 'Role Request','E', 'Refered Employee') as requestType, ");
			queryBuilder.append(" ur.sap_user_id as sapUserid , ur.reason as reason, ");
			queryBuilder.append(" LISTAGG (ra.SAP_MODULE , ', ') within group (  order by ur.request_id) as sapModuleCode , ur.WF_LEVEL as wfLevel, ur.REQUEST_DATE as appliedOn,");	       
		    queryBuilder.append(" LISTAGG (ra.SWM_RECID , ', ') within group (  order by ur.request_id) as swmRecid  ");
			queryBuilder.append(" from hrm_employee E ");
//			boolean flag = principal.getAuthorities() != null
//					&& principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
			queryBuilder.append("  join   hrm_system_parameters sys ");
			queryBuilder.append(" on sys.COMPANY=e.COMPANY ");			
			queryBuilder.append(" join SAP_AM_USER_REQUESTS ur on  e.emp_code = ur.emp_code  ");			
			queryBuilder.append(" and  ur.status!='R'   ");			
			queryBuilder.append(" join SAP_AM_USER_REQUEST_APPROVALS ra on   ");			
			queryBuilder.append(" ur.REQUEST_ID=ra.REQUEST_ID and ur.SUR_RECID= ra.PARENT_RECID and ur.WF_LEVEl=ra.WF_LEVEl  ");		
			//queryBuilder.append(" join  SAP_AM_WORKFLOW_MASTER wm on ra.WF_CODE =wm.WF_CODE and  ra.WF_LEVEL=wm.WF_LEVEl and  ra.emp_code=wm.emp_code where wm.emp_code='4O02514'   ");			
		//	queryBuilder.append(" join  SAP_AM_WORKFLOW_MASTER wm on ra.WF_CODE =wm.WF_CODE and  ra.WF_LEVEL=wm.WF_LEVEl and  ra.emp_code=wm.emp_code where wm.emp_code=:pempcode   ");			
			// StringBuilder sWhere = new StringBuilder("  where e.status in ('C','P','N') and e.SAP_USERID is not null ");
			 queryBuilder.append(" where ra.emp_code=:pempcode and  ra.IS_CTM='H' and ra.status='S' ");
			 queryBuilder.append(" group by sys.ENTITY_SAP_CODE, sys.COMPANY, e.EMP_CODE,  e.EMPLOYEE_NAME, E.DEPT_CODE, E.DESIG_CODE, E.LOC_CODE, E.CD_CODE  ,ur.sur_recid  , ur.request_id , ur.sap_company_code, ur.sap_user_id, ur.reason,ur.request_type, ur.WF_LEVEL ,ur.REQUEST_DATE ");
			
			queryBuilder.append("ORDER BY   ur.SUR_RECID  DESC ");
			// System.out.println(queryBuilder.toString());

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("sapUserid", StandardBasicTypes.STRING)
					.addScalar("swmRecid", StandardBasicTypes.STRING)
					.addScalar("entity", StandardBasicTypes.STRING)
					.addScalar("company", StandardBasicTypes.STRING)
					.addScalar("hrisCode", StandardBasicTypes.STRING)
					.addScalar("employeeName", StandardBasicTypes.STRING)
					//.addScalar("sapEmpCode", StandardBasicTypes.STRING)
					.addScalar("wfLevel", StandardBasicTypes.STRING)
					.addScalar("appliedOn", StandardBasicTypes.STRING)
					.addScalar("department", StandardBasicTypes.STRING)
					.addScalar("designation", StandardBasicTypes.STRING)
					.addScalar("location", StandardBasicTypes.STRING)
					.addScalar("requestId", BigDecimalType.INSTANCE)
					.addScalar("surRecid", LongType.INSTANCE)
					.addScalar("sapCompanyCode", StringType.INSTANCE)
					.addScalar("requestType", StandardBasicTypes.STRING)
					.addScalar("reason", StandardBasicTypes.STRING)
					.addScalar("sapModuleCode", StandardBasicTypes.STRING);
			sqlQuery.setParameter("pempcode", principal.getEmpCode());
             criteria.iTotalDisplayRecords = criteria.iTotalRecords = sqlQuery.list().size();
			
			sqlQuery.setMaxResults(criteria.iDisplayLength);
			sqlQuery.setFirstResult(criteria.iDisplayStart);

			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SAPUserRequestVO.class));
		//	session.getTransaction().commit();
			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SAPUserRequestVO>) sqlQuery.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> ApproveRequestDaoImpl >> getUserRequest", e);
			e.printStackTrace();
		} 
//		finally {
//			if (session != null)
//				session.close();
//		}
		return null;
		

	}

	@Override
	public long saveCTMApproval(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		int batchSize = 10;
		int j = 0;
		Session session = currentSession();

		try {

			Iterator<SapAmRolesRequestVO> it = sapAmRolesAssignVO.getSapAmRolesRequestVO().iterator();
			while (it.hasNext()) {
				SapAmRolesRequestVO sapAmRolesRequestVO = it.next();
				SapAmUserAssignedRole sapAmUserAssignedRole = new SapAmUserAssignedRole();
				sapAmUserAssignedRole.setApprovedBy(principal.getEmpCode());
				sapAmUserAssignedRole.setApprovedOn(new Date());
				sapAmUserAssignedRole.setIsConflicted(sapAmRolesAssignVO.getIsSOD());
				sapAmUserAssignedRole.setPlant(sapAmRolesRequestVO.getPlant());
				// sapAmUserAssignedRole.setPurchaseGroup(sapAmRolesRequestVO.getPlant());
				sapAmUserAssignedRole.setReason(sapAmRolesAssignVO.getReason());
				sapAmUserAssignedRole.setRemarks(sapAmRolesAssignVO.getReason());
				sapAmUserAssignedRole.setRequestId(new BigDecimal(sapAmRolesAssignVO.getRequestId()));
				// sapAmUserAssignedRole.setR
				sapAmUserAssignedRole.setRoleCode(sapAmRolesRequestVO.getRoleCode());
				sapAmUserAssignedRole.setRoleId(new BigDecimal(sapAmRolesRequestVO.getRoleId()));
				sapAmUserAssignedRole.setSapCompanyCode(sapAmRolesAssignVO.getSapCompanyCode());
				sapAmUserAssignedRole.setSapModuleCode(sapAmRolesAssignVO.getSapModuleCode());
				sapAmUserAssignedRole.setSapUserId(sapAmRolesAssignVO.getSapUserid());
				sapAmUserAssignedRole.setStatus(sapAmRolesAssignVO.getStatus());
				sapAmUserAssignedRole.setSurRecid(sapAmRolesRequestVO.getSurRecid());
				sapAmUserAssignedRole.setTcode(sapAmRolesRequestVO.getTcode());
				sapAmUserAssignedRole.setTcodeId(new BigDecimal(sapAmRolesRequestVO.getTcodeId()));
				sapAmUserAssignedRole.setCreatedBy(principal.getEmpCode());
				sapAmUserAssignedRole.setCreatedOn(new Date());
				session.saveOrUpdate(sapAmUserAssignedRole);
				j++;

				if (j % batchSize == 0 && j > 0) {
					// Flush A Batch Of Updates & Release Memory
					session.flush();
					session.clear();
				}
			}
			// tx.commit();
		} catch (Exception e) {
			// if (null != tx) {
			// tx.rollback();
			// }
			e.printStackTrace();
			throw e;
		}

		return j;
	}

	@Transactional
	@Override
	public List<SapAmUserAssignedRole> getAssignedRole(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {

			StringBuilder queryBuilder = new StringBuilder(
					" from SapAmUserAssignedRole ar where ar.status in (:pstatus) and ar.surRecid=:psurRecid and ar.requestId=:prequestId");

			Query query = session.createQuery(queryBuilder.toString());
			// Query query=session.createQuery(queryBuilder.toString(), SapAmRisk.class);
			query.setParameterList("pstatus", Arrays.asList("A","O"));
			query.setParameter("psurRecid", new BigDecimal(surRecid));
			query.setParameter("prequestId", new BigDecimal(requestId));
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.getResultList().size();
			System.out.println(requestId + "      " + surRecid + "  " + criteria.iTotalRecords);
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			return (List<SapAmUserAssignedRole>) query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmUserAssignedRole >> get", e);
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional
	@Override
	public List<SapAmUserAssignedRoleVO> getAssignedRoleWIthSensetive(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {

			StringBuilder queryBuilder = new StringBuilder(" select   suar.SUAR_RECID as suarRecid,  suar.plant as plant, suar.PURCHASE_GROUP as purchaseGroup, suar.reason as reason, ");

		
			queryBuilder.append("   suar.remarks as remarks,  suar.REQUEST_ID as requestId, suar.ROLE_CODE as roleCode,suar.ROLE_ID as roleId, ");
			queryBuilder.append("   suar.SAP_COMPANY_CODE as sapCompanyCode, suar.SAP_MODULE_CODE as sapModuleCode, suar.SAP_USER_ID as sapUserId, ");
			queryBuilder.append("  suar.status as status,  suar.SUR_RECID as surRecid, suar.tcode as tcode, suar.TCODE_ID as tcodeId,  suar.TYPE_OF_RIGHT as typeOfRight, ");
			queryBuilder.append("  (select at.SENSITIVE from SAP_AM_TCODES  at join SAP_AM_ROLE_TCODE_MAPPING tm on  tm.tcode_id = AT.sat_id  AND tm.tcode = AT.tcode ");
			queryBuilder.append("  where   tm.role_id =suar.role_id and  tm.role_code =suar.role_code and AT.SENSITIVE='Y' and  ROWNUM <= 1 ) as sensitive  ");
			queryBuilder.append("  from  SAP_AM_USER_ASSIGNED_ROLES suar ");
			//queryBuilder.append(" where  suar.status in ( 'O','A' )  and suar.REQUEST_ID=30520231 ; ");
			queryBuilder.append(" where suar.status in (:pstatus) and suar.sur_recid=:psurRecid and suar.REQUEST_ID=:prequestId ");
		
	

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("suarRecid", StandardBasicTypes.LONG)
					.addScalar("plant", StandardBasicTypes.STRING)
					.addScalar("purchaseGroup", StandardBasicTypes.STRING)
					.addScalar("reason", StandardBasicTypes.STRING)
					.addScalar("remarks", StandardBasicTypes.STRING)
					.addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("roleCode", StandardBasicTypes.STRING)
					.addScalar("roleId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("sapCompanyCode", StandardBasicTypes.STRING)
					.addScalar("sapModuleCode", StandardBasicTypes.STRING)
					.addScalar("sapUserId", StandardBasicTypes.STRING)
					.addScalar("status", StandardBasicTypes.STRING)
					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("tcode", StandardBasicTypes.STRING)
					.addScalar("tcodeId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("typeOfRight", StandardBasicTypes.STRING)
			       .addScalar("sensitive", StandardBasicTypes.STRING);
					
			
			sqlQuery.setParameterList("pstatus", Arrays.asList("A","O"));
			sqlQuery.setParameter("psurRecid", new BigDecimal(surRecid));
			sqlQuery.setParameter("prequestId", new BigDecimal(requestId));
			
             criteria.iTotalDisplayRecords = criteria.iTotalRecords = sqlQuery.list().size();
			
			sqlQuery.setMaxResults(criteria.iDisplayLength);
			sqlQuery.setFirstResult(criteria.iDisplayStart);

			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmUserAssignedRoleVO.class));
	
			return (List<SapAmUserAssignedRoleVO>) sqlQuery.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmUserAssignedRole >> get", e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long updateAssignedRolesHOD(AssignedRolesVO assignedRolesVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();

		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_ASSIGNED_ROLES ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			//queryBuilder.append(" , ur.WF_LEVEL = :pwflevel ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			// queryBuilder.append(" , ur.status = :pstatus"); STATUS WF_LEVEL APPROVED_ON
			// APPROVED_BY
			queryBuilder.append(" WHERE   ur.status !='R' and  ur.SUAR_RECID = :psuarRecid ");
			queryBuilder.append(" and  ur.SUR_RECID = :psurRecid and ur.REQUEST_ID = :prequestId ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", assignedRolesVO.getStatus());
			//nativeQuery.setParameter("pwflevel", 3);
			nativeQuery.setParameter("papprovedOn", new Date());
			nativeQuery.setParameter("papprovedBy", principal.getEmpCode());
			nativeQuery.setParameter("psuarRecid", assignedRolesVO.getSuarRecid());
			nativeQuery.setParameter("psurRecid", assignedRolesVO.getSurRecid());			
			nativeQuery.setParameter("prequestId", assignedRolesVO.getRequestId());
			return nativeQuery.executeUpdate();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}
	
	@Override
	public long saveAssignedRoles(AssignedRolesVO assignedRolesVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();

		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_ASSIGNED_ROLES ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			//queryBuilder.append(" , ur.WF_LEVEL = :pwflevel ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			// queryBuilder.append(" , ur.status = :pstatus"); STATUS WF_LEVEL APPROVED_ON
			// APPROVED_BY
			queryBuilder.append(" WHERE   ur.status != 'R'  ");
			queryBuilder.append(" and  ur.SUR_RECID = :psurRecid and ur.REQUEST_ID = :prequestId ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", assignedRolesVO.getStatus());
			//nativeQuery.setParameter("pwflevel", 3);
			nativeQuery.setParameter("papprovedOn", new Date());
			nativeQuery.setParameter("papprovedBy", principal.getEmpCode());
			//nativeQuery.setParameter("psuarRecid", assignedRolesVO.getSuarRecid());
			nativeQuery.setParameter("psurRecid", assignedRolesVO.getSurRecid());			
			nativeQuery.setParameter("prequestId", assignedRolesVO.getRequestId());
			return nativeQuery.executeUpdate();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	@Override
	public long saveAssignedRoles(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();

		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_ASSIGNED_ROLES ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			//queryBuilder.append(" , ur.WF_LEVEL = :pwflevel ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			// queryBuilder.append(" , ur.status = :pstatus"); STATUS WF_LEVEL APPROVED_ON
			// APPROVED_BY
			queryBuilder.append(" WHERE   ur.status != 'R'  ");
			queryBuilder.append(" and  ur.SUR_RECID = :psurRecid and ur.REQUEST_ID = :prequestId ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRolesAssignVO.getStatus());
			//nativeQuery.setParameter("pwflevel", 3);
			nativeQuery.setParameter("papprovedOn", new Date());
			nativeQuery.setParameter("papprovedBy", principal.getEmpCode());
			//nativeQuery.setParameter("psuarRecid", assignedRolesVO.getSuarRecid());
			nativeQuery.setParameter("psurRecid", sapAmRolesAssignVO.getSurRecid());			
			nativeQuery.setParameter("prequestId", sapAmRolesAssignVO.getRequestId());
			return nativeQuery.executeUpdate();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}
	
	
	

}
