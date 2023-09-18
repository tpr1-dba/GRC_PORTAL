package com.samodule.dao;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;

import com.samodule.model.SapAmRoleMaster;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.vo.RoleMasterVO;
import com.samodule.vo.SapAmRequestEmpRefVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapAmRolesRequestVO;

@Service("assignRoleDao")
public class AssignRoleDaoImpl extends JpaDao<SapAmRoleMaster> implements AssignRoleDao {
	static final Logger log = Logger.getLogger(AssignRoleDaoImpl.class.getName());

	@Transactional
	@Override
	public List<RoleMasterVO> searchRoleByUserID(SapAmRolesAssignVO sapAmRolesAssignVO) throws Exception {
		Session session = currentSession();
		// session.beginTransaction();
		try {
			StringBuilder queryBuilder = new StringBuilder(" select  ");
			queryBuilder.append(" srm.ROLE_ID  as roleId ,");
			queryBuilder.append(" srm.ROLE_CODE as roleCode ,");
			queryBuilder.append(" srm.ROLE_NAME as roleName ,");
			queryBuilder.append(" fun.FUN_ID  as funId ,");
			queryBuilder.append(" fun.FUN_CODE  as funCode ");
			queryBuilder.append(
					" from SAP_AM_ROLE_MASTER srm  left join SAP_AM_ROLE_EMP_MAPPING srem on srm.ROLE_ID=srem.ROLE_ID ");
			queryBuilder.append(" left JOIN sap_am_functions fun ON fun.fun_id = srm.fun_id ");

			StringBuilder sWhere = new StringBuilder("  where srem.EMP_CODE like  UPPER('%"
					+ sapAmRolesAssignVO.getHrisCode() + "%') and srm.status in ('A')    ");
			// " where srem.EMP_CODE like UPPER('%5501205%') and srm.status in ('A') ");
			// and srm.ENTITY=:pentity
			queryBuilder.append(sWhere);

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("roleId", StandardBasicTypes.LONG).addScalar("roleCode", StandardBasicTypes.STRING)
					.addScalar("roleName", StandardBasicTypes.STRING).addScalar("funId", StandardBasicTypes.LONG)
					.addScalar("funCode", StandardBasicTypes.STRING);
//			if (!flag) {
//				sqlQuery.setParameter("pentity", principal.getSapEntityCode());
//			}

			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(RoleMasterVO.class));
			// session.getTransaction().commit();
			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<RoleMasterVO>) sqlQuery.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmEmpLoanDaoJpa >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	// @Transactional
	// @Override
	public List<String> searchFunctionByUserID2(SapAmRolesAssignVO sapAmRolesAssignVO) {
		Session session = currentSession();
		// session.beginTransaction();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select distinct srm.fun_code  from  SAP_AM_ROLE_MASTER srm left join SAP_AM_ROLE_EMP_MAPPING srem on srm.ROLE_ID=srem.ROLE_ID   ");

			StringBuilder sWhere = new StringBuilder("  where srem.EMP_CODE like  UPPER('%"
					+ sapAmRolesAssignVO.getHrisCode() + "%') and srm.status in ('A')    ");
			// " where srem.EMP_CODE like UPPER('%5501205%') and srm.status in ('A') ");
			// and srm.ENTITY=:pentity
			queryBuilder.append(sWhere);

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString());

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<String>) sqlQuery.getResultList();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmEmpLoanDaoJpa >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	@Override
	public List<String> searchFunctionByUserID(SapAmRolesAssignVO sapAmRolesAssignVO) {
		Session session = currentSession();
		// session.beginTransaction();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					"  select distinct f.fun_id,f.fun_code from SAP_AM_FUNctions f join  SAP_AM_TCODE_FUN_MAPPING m   on f.fun_id=m.fun_id  ");
			queryBuilder.append(
					" join  SAP_AM_ROLE_TCODE_MAPPING t  on t.tcode_id=m.tcode_id   join SAP_AM_ROLE_EMP_MAPPING srem on t.ROLE_CODE=srem.ROLE_CODE ");
			StringBuilder sWhere = new StringBuilder("  where srem.EMP_CODE like  UPPER('%"
					+ sapAmRolesAssignVO.getHrisCode() + "%') and srem.status in ('A')    ");
			// " where srem.EMP_CODE like UPPER('%5501205%') and srm.status in ('A') ");
			// and srm.ENTITY=:pentity
			queryBuilder.append(sWhere);

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString());

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<String>) sqlQuery.getResultList();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmEmpLoanDaoJpa >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	@Override
	public List<String> searchFunctionByRequestId(SapAmRolesRequestVO sapAmRolesRequestVO) throws Exception {
		Session session = currentSession();
		// session.beginTransaction();
		try {// f.fun_id,
			StringBuilder queryBuilder = new StringBuilder(
					" select distinct f.fun_code from SAP_AM_FUNctions f join  SAP_AM_TCODE_FUN_MAPPING m   on f.fun_id=m.fun_id ");
			queryBuilder.append(
					" join  SAP_AM_ROLE_TCODE_MAPPING t  on t.tcode_id=m.tcode_id   join SAP_AM_ROLE_EMP_MAPPING srem on t.ROLE_CODE=srem.ROLE_CODE  ");
			queryBuilder.append("  join SAP_AM_USER_REQUESTS ur  on  srem.emp_code=ur.emp_code and t.status='A'  ");
			StringBuilder sWhere = new StringBuilder(
					"   where ur.REQUEST_ID =:prequestId and   ur.SUR_RECID=:psurRecid and srem.status in ('A') ");
			// " where srem.EMP_CODE like UPPER('%5501205%') and srm.status in ('A') ");
			// and srm.ENTITY=:pentity
			queryBuilder.append(sWhere);

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString());
			sqlQuery.setParameter("prequestId", sapAmRolesRequestVO.getRequestId());
			sqlQuery.setParameter("psurRecid", sapAmRolesRequestVO.getSurRecid());

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<String>) sqlQuery.getResultList();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmEmpLoanDaoJpa >> get", e);
			e.printStackTrace();
			throw e;
		}

	}

	// @Transactional
	@Override
	public List<SapAmRolesRequestVO> searchRolesByUserID(SapAmRequestEmpRefVO sapAmRequestEmpRefVO) {
		Session session = currentSession();
		// session.beginTransaction();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select distinct m.MODULE AS module , m.ROLE_ID as roleId , m.ROLE_CODE AS roleCode , m.entity as entity , m.plant as plant from  ");
			queryBuilder.append(
					"  SAP_AM_ROLE_TCODE_MAPPING m   join SAP_AM_ROLE_EMP_MAPPING srem on m.ROLE_CODE=srem.ROLE_CODE  and m.ROLE_ID=srem.ROLE_ID");//");
			StringBuilder sWhere = new StringBuilder("  where srem.SAP_USERID =  UPPER('"
					+sapAmRequestEmpRefVO.getRefSapUserId() +"') and srem.status in ('A')    ");

			queryBuilder.append(sWhere);

			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString()).addScalar("roleId", StandardBasicTypes.LONG)
					.addScalar("entity", StandardBasicTypes.STRING).addScalar("module", StandardBasicTypes.STRING)
					.addScalar("plant", StandardBasicTypes.STRING)
					.addScalar("roleCode", StandardBasicTypes.STRING);

			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmEmpLoanDaoJpa >> get", e);
			e.printStackTrace();
			throw e;
		}

	}

}
