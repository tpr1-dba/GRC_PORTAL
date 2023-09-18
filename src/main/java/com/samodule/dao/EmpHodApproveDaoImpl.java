package com.samodule.dao;

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
import com.samodule.model.SapAmUserAssignedRole;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.vo.SAPUserRequestVO;

@Repository("empHodApproveDao")
public class EmpHodApproveDaoImpl extends JpaDao<SapAmUserAssignedRole> implements EmpHodApproveDao {
	static final Logger log = Logger.getLogger(EmpHodApproveDaoImpl.class.getName());

	
	
	@Transactional
    @Override
    public List<SAPUserRequestVO> getUserEHodRequest(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception{
    	Session session = currentSession();
    	try {
			StringBuilder queryBuilder = new StringBuilder(
					" select sys.ENTITY_SAP_CODE as entity, sys.COMPANY as company, e.EMP_CODE as hrisCode, e.EMPLOYEE_NAME as employeeName, ");

			//queryBuilder.append(" HRIS_GET.DEPT_CODE(E.DEPT_CODE) as department, ");
			queryBuilder.append(" E.DEPT_CODE as department , ");
			queryBuilder.append(" HRIS_GET.DESIG_CODE(E.DESIG_CODE) as designation, ");
			queryBuilder.append(" HRIS_GET.LOC_CODE(E.LOC_CODE) as location , ");
			queryBuilder.append(" ur.sur_recid as surRecid ,ur.request_id as requestId,ur.sap_company_code as sapCompanyCode, ");
			queryBuilder.append(" DECODE (upper(ur.request_type),'T' ,'T-code Request', 'R', 'Role Request','E', 'Refered Employee') as requestType, ");
			queryBuilder.append(" ur.sap_user_id as sapUserid , ur.reason as reason, ");
			queryBuilder.append(" LISTAGG (ra.SAP_MODULE , ', ') within group (  order by ur.request_id) as sapModuleCode , ur.WF_LEVEL as wfLevel, ur.REQUEST_DATE as appliedOn, ");	       
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
			//queryBuilder.append(" join  SAP_AM_WORKFLOW_MASTER wm on ra.WF_CODE =wm.WF_CODE and  ra.WF_LEVEL=wm.WF_LEVEl and  ra.emp_code=wm.emp_code where wm.emp_code=:pempcode   ");			
			// StringBuilder sWhere = new StringBuilder("  where e.status in ('C','P','N') and e.SAP_USERID is not null ");
			 queryBuilder.append(" where ra.emp_code=:pempcode and ra.IS_HOD='Y' and ra.status='S' ");
			 //queryBuilder.append(" where ra.emp_code=:pempcode and ra.IS_HOD='Y' and ra.status='S' ");
			 queryBuilder.append(" group by sys.ENTITY_SAP_CODE, sys.COMPANY, e.EMP_CODE,  e.EMPLOYEE_NAME, E.DEPT_CODE, E.DESIG_CODE, E.LOC_CODE, E.CD_CODE  ,ur.sur_recid  , ur.request_id , ur.sap_company_code, ur.sap_user_id, ur.reason,ur.request_type, ur.WF_LEVEL ,ur.REQUEST_DATE ");
			//boolean flag =principal.getRoles()!=null && principal.getRoles().size()>0?true:false;
			
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



}
