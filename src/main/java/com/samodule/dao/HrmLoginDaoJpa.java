package com.samodule.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.samodule.model.HrmLogin;
import com.samodule.vo.HrmLoginVO;

/**
 * 
 * @author ved.prakash
 * @date 2015-9-2
 * 
 */
@Repository("hrmLoginDao")
public class HrmLoginDaoJpa extends JpaDao<HrmLogin> implements HrmLoginDao {
	static final Logger log = Logger.getLogger(HrmLoginDaoJpa.class.getName());
	@Transactional
	@Override
	public HrmLoginVO get(HrmLoginVO hrmLogin) {
		log.info(hrmLogin.getEmailId());		
		HrmLogin hrmLogins = null;		
		Session session = currentSession();
		//session.beginTransaction();
		try {
			log.info("IF");
			
			
			StringBuilder queryBuilder=new StringBuilder("  select   hrm.EMP_RECID as empRecid,     hrm.COMPANY as company, hrm.FUNC_REPORTG as funcReportg,  sys.ENTITY_SAP_CODE as sapEntityCode, oc.ENTITY_CODE as sbuCode,  hrm.EMAIL_ID as emailId, ");
			queryBuilder.append("  hrm.EMP_CODE as empCode,  hrm.EMPLOYEE_NAME as employeeName,  hrm.EUIN_CODE as euinCode,  hrm.IMIS_LOGIN_ID as imisLoginId, hrm.STATUS as status ,hrm.SAP_USERID as sapUserId , ");
			//queryBuilder.append("  from   HRM_EMPLOYEE hrm join hrm_system_parameters sys on sys.COMPANY=hrm.COMPANY ");
			queryBuilder.append(" (select  decode(count(*), 0, 'N', 'Y')  from  hrm_employee e   where   e.FUNC_REPORTG=hrm.emp_code) as isHodApprover ");
			queryBuilder.append("  from HRM_EMPLOYEE hrm left join hrm_system_parameters sys on sys.COMPANY=hrm.COMPANY left join oc_operating_unit oc on oc.sap_entity_code=sys.entity_sap_code and oc.ou_code=hrm.COMPANY ");
			queryBuilder.append("  where  UPPER(hrm.EMAIL_ID)=:emailId and  hrm.status in (:status) ");

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("empRecid", LongType.INSTANCE)
					.addScalar("company", StringType.INSTANCE)
					.addScalar("company", StringType.INSTANCE)
					.addScalar("sapEntityCode", StringType.INSTANCE)
					.addScalar("sbuCode", StringType.INSTANCE)
					.addScalar("funcReportg", StringType.INSTANCE)
					.addScalar("emailId", StringType.INSTANCE)
					.addScalar("empCode", StringType.INSTANCE)
					.addScalar("employeeName", StringType.INSTANCE)
					.addScalar("isHodApprover", StringType.INSTANCE)
					.addScalar("euinCode", StringType.INSTANCE)
					.addScalar("imisLoginId", StringType.INSTANCE)
					.addScalar("status", StringType.INSTANCE)		
					.addScalar("sapUserId", StringType.INSTANCE);
			sqlQuery.setParameter("emailId", hrmLogin.getEmailId().trim().toUpperCase());
			sqlQuery.setParameterList("status", new String[]{"C","P","N"});
			sqlQuery.setResultTransformer(new AliasToBeanResultTransformer(HrmLoginVO.class));
			
			//session.getTransaction().commit();
			System.out.println("VED  "+hrmLogins);
			return  (HrmLoginVO) sqlQuery.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmLoginDaoJpa >> get", e);
			e.printStackTrace();
		}
//		finally{			
//			if(session!=null)
//			 session.close();
//		 }
		
		
		return null;
	}
	
	@Transactional
	@Override
	public List<HrmLoginVO> getAllEmployee() {
		 List<HrmLoginVO> hrmLogins = null;		
		Session session = currentSession();
		try {
			log.info("IF");
			
			StringBuilder queryBuilder=new StringBuilder("  select   hrm.EMP_RECID as empRecid,     hrm.COMPANY as company, hrm.FUNC_REPORTG as funcReportg,  sys.ENTITY_SAP_CODE as sapEntityCode, oc.ENTITY_CODE as sbuCode,  hrm.EMAIL_ID as emailId, ");
			queryBuilder.append("  hrm.EMP_CODE as empCode,  hrm.EMPLOYEE_NAME as employeeName,  hrm.EUIN_CODE as euinCode,  hrm.IMIS_LOGIN_ID as imisLoginId, hrm.STATUS as status ,hrm.SAP_USERID as sapUserId , ");
			queryBuilder.append(" (select  decode(count(*), 0, 'N', 'Y')  from  hrm_employee e   where   e.FUNC_REPORTG=hrm.emp_code) as isHodApprover ");
			queryBuilder.append("  from HRM_EMPLOYEE hrm left join hrm_system_parameters sys on sys.COMPANY=hrm.COMPANY left join oc_operating_unit oc on oc.sap_entity_code=sys.entity_sap_code and oc.ou_code=hrm.COMPANY ");
			queryBuilder.append("  where hrm.status in (:status) ");
			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("empRecid", LongType.INSTANCE)
					.addScalar("company", StringType.INSTANCE)
					.addScalar("company", StringType.INSTANCE)
					.addScalar("sapEntityCode", StringType.INSTANCE)
					.addScalar("sbuCode", StringType.INSTANCE)
					.addScalar("funcReportg", StringType.INSTANCE)
					.addScalar("emailId", StringType.INSTANCE)
					.addScalar("empCode", StringType.INSTANCE)
					.addScalar("employeeName", StringType.INSTANCE)
					.addScalar("isHodApprover", StringType.INSTANCE)
					.addScalar("euinCode", StringType.INSTANCE)
					.addScalar("imisLoginId", StringType.INSTANCE)
					.addScalar("status", StringType.INSTANCE)		
					.addScalar("sapUserId", StringType.INSTANCE);
			sqlQuery.setResultTransformer(new AliasToBeanResultTransformer(HrmLoginVO.class));
			sqlQuery.setParameterList("status", new String[]{"C","P","N"});
			return  (List<HrmLoginVO>) sqlQuery.getResultList();
		} catch (Exception e) {
			log.error("ERROR >> HrmLoginDaoJpa >> get", e);
			e.printStackTrace();
		}
		return null;
	}

}
