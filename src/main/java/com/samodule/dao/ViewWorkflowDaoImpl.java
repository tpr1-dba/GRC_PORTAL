package com.samodule.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequest;
import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmUserRequestApprovalVO;

@Repository("viewWorkflowDao")
public class ViewWorkflowDaoImpl extends JpaDao<SapAmUserRequest> implements ViewWorkflowDao {
	static final Logger log = Logger.getLogger(ViewWorkflowDaoImpl.class.getName());

	@Transactional
	@Override
	public List<SapAmUserRequestApprovalVO> getUserRequestStatus(Long surRecid, Long requestId,
			JQueryDataTableParamModel criteria, HeapUserDetails principal) {
		// TODO Auto-generated method stub
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select  LISTAGG (rp.company_code, ', ') within  group (  order by rp.request_id) AS companyCode, e.employee_name AS empCode,  ");
			queryBuilder.append(
					" LISTAGG (rp.SAP_MODULE ,', ') within    group (  order by rp.request_id) as sapModule ,  ");
			queryBuilder.append(
					" LISTAGG ( rp.sbu , ', ') within  group (  order by rp.request_id) AS sbu, rp.status AS status, rp.wf_code AS wfCode, rp.wf_level AS wfLevel, ");
			queryBuilder.append(" rp.emp_code AS  userCode,rp.request_id AS requestNo,rp.swm_recid AS sapAmRecId  ");
			queryBuilder
					.append("  FROM sap_am_user_request_approvals rp join hrm_employee E on rp.emp_code=e.emp_code  ");
			// added on 20-06-2023 for and a.wf_code= rp.wf_code
			queryBuilder.append(
					" where rp.request_id=:prequestId and rp.parent_recid =:psurRecid and rp.status ='S' and rp.wf_level= ( select max(a.wf_level) FROM "
							+ "        sap_am_user_request_approvals a where a.emp_code=rp.emp_code   and a.wf_code= rp.wf_code  and a.request_id= rp.request_id )  ");
			// queryBuilder.append(" group by rp.company_code,e.EMPLOYEE_NAME, rp.sbu,
			// rp.wf_level ,rp.status ,rp.wf_code ");
			queryBuilder.append(
					" group by e.EMPLOYEE_NAME,  rp.wf_level ,rp.status ,rp.wf_code,rp.emp_code,rp.request_id,rp.swm_recid  ");
			// queryBuilder.append(" ORDER BY rp.company_code, rp.sbu, rp.wf_level ASC ");
			queryBuilder.append(" ORDER BY  rp.wf_level ASC ");

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("companyCode", StandardBasicTypes.STRING).addScalar("empCode", StandardBasicTypes.STRING)
					.addScalar("sapModule", StandardBasicTypes.STRING).addScalar("sbu", StandardBasicTypes.STRING)
					.addScalar("status", StandardBasicTypes.STRING).addScalar("status", StandardBasicTypes.STRING)
					.addScalar("wfCode", StandardBasicTypes.STRING).addScalar("wfLevel", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("userCode", StandardBasicTypes.STRING)
					.addScalar("requestNo", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("sapAmRecId", StandardBasicTypes.BIG_DECIMAL);

			sqlQuery.setParameter("prequestId", new BigDecimal(requestId));
			sqlQuery.setParameter("psurRecid", new BigDecimal(surRecid));
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = sqlQuery.list().size();

			sqlQuery.setMaxResults(criteria.iDisplayLength);
			sqlQuery.setFirstResult(criteria.iDisplayStart);

			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmUserRequestApprovalVO.class));
			System.out.println("Query Filter by Request No. ---------------->>" + queryBuilder.toString());
			return (List<SapAmUserRequestApprovalVO>) sqlQuery.getResultList();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> ApproveRequestDaoImpl >> getUserRequest", e);
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public List<SAPUserRequestVO> getUserRequestByRequestId(String requestNo, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select  sys.ENTITY_SAP_CODE as entity, sys.COMPANY as company, e.EMP_CODE as hrisCode, e.EMPLOYEE_NAME as employeeName,  ");

			// queryBuilder.append(" HRIS_GET.DEPT_CODE(E.DEPT_CODE) as department, ");
			queryBuilder.append(" E.DEPT_CODE as department, ");
			queryBuilder.append(" HRIS_GET.DESIG_CODE(E.DESIG_CODE) as designation, ");
			queryBuilder.append(" HRIS_GET.LOC_CODE(E.LOC_CODE) as location , ");
			queryBuilder.append(
					" ur.sur_recid as surRecid ,ur.request_id as requestId,ur.sap_company_code as sapCompanyCode, ");
			queryBuilder.append(
					" DECODE (upper(ur.request_type),'T' ,'T-code Request', 'R', 'Role Request','E', 'Refered Employee') as requestType, ");
			queryBuilder.append(
					" ur.sap_user_id as sapUserid , ur.reason as reason, ur.status as status, ur.SAP_MODULE_CODE as sapModuleCode, ur.WF_LEVEL as wfLevel, ur.REQUEST_DATE as appliedOn");
			queryBuilder.append(" from hrm_employee E ");
			queryBuilder.append("  join   hrm_system_parameters sys ");
			queryBuilder.append(" on sys.COMPANY=e.COMPANY ");
			queryBuilder.append(" join   SAP_AM_USER_REQUESTS ur on  e.emp_code = ur.emp_code  ");
			// queryBuilder.append(" and ur.status!='I' ");
			// queryBuilder.append(" where ur.emp_code=:pempcode ");
			queryBuilder.append(" where ur.request_id=:requestNo   ");
			queryBuilder.append("ORDER BY   ur.SUR_RECID  DESC ");
			System.out.println("here the Query----" + queryBuilder.toString());

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("sapUserid", StandardBasicTypes.STRING).addScalar("entity", StandardBasicTypes.STRING)
					.addScalar("company", StandardBasicTypes.STRING).addScalar("hrisCode", StandardBasicTypes.STRING)
					.addScalar("employeeName", StandardBasicTypes.STRING)
					// .addScalar("sapEmpCode", StandardBasicTypes.STRING)
					.addScalar("department", StandardBasicTypes.STRING).addScalar("wfLevel", StandardBasicTypes.STRING)
					.addScalar("appliedOn", StandardBasicTypes.STRING)
					.addScalar("designation", StandardBasicTypes.STRING)
					.addScalar("location", StandardBasicTypes.STRING).addScalar("requestId", BigDecimalType.INSTANCE)
					.addScalar("surRecid", LongType.INSTANCE).addScalar("sapCompanyCode", StringType.INSTANCE)
					.addScalar("requestType", StandardBasicTypes.STRING).addScalar("reason", StandardBasicTypes.STRING)
					.addScalar("status", StandardBasicTypes.STRING)
					.addScalar("sapModuleCode", StandardBasicTypes.STRING);
			//sqlQuery.setParameter("pempcode", principal.getEmpCode());
			sqlQuery.setParameter("requestNo", requestNo);
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = sqlQuery.list().size();

			sqlQuery.setMaxResults(criteria.iDisplayLength);
			sqlQuery.setFirstResult(criteria.iDisplayStart);

			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SAPUserRequestVO.class));
			// session.getTransaction().commit();
			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SAPUserRequestVO>) sqlQuery.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> ApproveRequestDaoImpl >> getUserRequest", e);
			e.printStackTrace();
		}
		return null;

	}

}
