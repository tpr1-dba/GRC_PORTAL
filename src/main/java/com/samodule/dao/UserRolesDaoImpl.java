package com.samodule.dao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmRoleEmpMapping;
import com.samodule.model.SapAmRoleMaster;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.util.AliasToBeanNestedResultTransformerDTO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesRequestVO;
import com.samodule.vo.SapUserVO;

@Repository("userRolesDao")
public class UserRolesDaoImpl extends JpaDao<SapAmRoleEmpMapping> implements UserRolesDao {
	static final Logger log = Logger.getLogger(UserRolesDaoImpl.class.getName());

	@Transactional
	@Override
	public List<SapUserVO> list(JQueryDataTableParamModel criteria) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();

		try {

			System.out.println("List" + criteria.toString());
			List<String> columnName = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
			StringBuilder queryBuilder = new StringBuilder(
					" select e.SAP_USERID as sapUserid, e.EMP_CODE as hrisCode, e.EMPLOYEE_NAME as employeeName, e.SAP_EMP_CODE as sapEmpCode, ");

			queryBuilder.append(" HRIS_GET.DEPT_CODE(E.DEPT_CODE) department, ");
			queryBuilder.append("	HRIS_GET.DESIG_CODE(E.DESIG_CODE) designation, ");
			queryBuilder.append(" HRIS_GET.LOC_CODE(E.LOC_CODE) location ");
			queryBuilder.append(
					"  ,srm.ROLE_ID as \"roleMasters.roleId\" , srm.ROLE_CODE as \"roleMasters.roleCode\" , srm.FUN_ID as \"roleMasters.funId\" ");
			queryBuilder.append(" from hrm_employee E ");
			queryBuilder.append(" left join SAP_AM_ROLE_EMP_MAPPING srem   on srem.EMP_CODE=e.EMP_CODE ");
			queryBuilder.append(" left join SAP_AM_ROLE_MASTER srm on  srm.ROLE_ID=srem.ROLE_ID ");

			StringBuilder sWhere = new StringBuilder("  where e.status in ('C','P','N') and e.SAP_USERID is not null ");

			queryBuilder.append(sWhere);
			// countRecord.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				// query.add(Expression.like("A%"));
				Iterator<String> coulmnIt = columnName.iterator();
				coulmnIt.next();
				criteriaBuilder.append(" and ( ");

				criteriaBuilder.append(" e.SAP_USERID like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append(" e.EMP_CODE like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append(" e.EMPLOYEE_NAME like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append(" e.SAP_EMP_CODE like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder
						.append(" HRIS_GET.DEPT_CODE(E.DEPT_CODE) like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder
						.append(" HRIS_GET.DESIG_CODE(E.DESIG_CODE) like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append(" HRIS_GET.LOC_CODE(E.LOC_CODE) like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");

				queryBuilder.append(criteriaBuilder);
				// countRecord.append(criteriaBuilder);
			}
			queryBuilder.append(" ORDER BY  E.DEPT_CODE, E.CD_CODE ");
			System.out.println(queryBuilder.toString());

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("sapUserid", StandardBasicTypes.STRING).addScalar("hrisCode", StandardBasicTypes.STRING)
					.addScalar("employeeName", StandardBasicTypes.STRING)
					.addScalar("sapEmpCode", StandardBasicTypes.STRING)
					.addScalar("department", StandardBasicTypes.STRING)
					.addScalar("designation", StandardBasicTypes.STRING)
					.addScalar("location", StandardBasicTypes.STRING)
					.addScalar("roleMasters.roleId", StandardBasicTypes.LONG)
					.addScalar("roleMasters.roleCode", StandardBasicTypes.STRING);
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = sqlQuery.list().size();

			sqlQuery.setMaxResults(criteria.iDisplayLength);
			sqlQuery.setFirstResult(criteria.iDisplayStart);
			System.out.println("HHHHHHHHHHHHHHHHH");
			AliasToBeanNestedResultTransformerDTO alias = new AliasToBeanNestedResultTransformerDTO(SapUserVO.class);
			sqlQuery.setResultTransformer(alias);
			// sqlQuery.unwrap(org.hibernate.query.Query.class)
			// .setResultTransformer(Transformers.aliasToBean(SapUserVO.class));
			// .getResultList();

			return (List<SapUserVO>) alias.cleanList(sqlQuery.getResultList());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmEmpLoanDaoJpa >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	@Override
	public List<SapUserVO> list(HeapUserDetails principal, JQueryDataTableParamModel criteria) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();

		try {

			System.out.println("List" + criteria.toString());
			List<String> columnName = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
			StringBuilder queryBuilder = new StringBuilder(
					" select e.SAP_USERID as sapUserid, e.EMP_CODE as hrisCode, e.EMPLOYEE_NAME as employeeName, e.SAP_EMP_CODE as sapEmpCode, ");

			queryBuilder.append(" HRIS_GET.DEPT_CODE(E.DEPT_CODE) as department, ");
			queryBuilder.append("	HRIS_GET.DESIG_CODE(E.DESIG_CODE) as designation, ");
			queryBuilder.append(" HRIS_GET.LOC_CODE(E.LOC_CODE) as location , ");
//			queryBuilder.append(
//					"  ,srm.ROLE_ID as \"roleMasters.roleId\" , srm.ROLE_CODE as \"roleMasters.roleCode\" , srm.FUN_ID as \"roleMasters.funId\" ");
			queryBuilder.append("  LISTAGG( srm.ROLE_ID, ', ')  within group ( order by srm.ROLE_ID)  as roleId ,");
			queryBuilder.append("  LISTAGG( srm.ROLE_CODE, ', ') within group ( order by srm.ROLE_ID) as roleCode ,");
			queryBuilder.append("  LISTAGG( srm.ROLE_NAME, ', ') within group ( order by srm.ROLE_ID) as roleName ,");
			queryBuilder
					.append("  LISTAGG (srm.role_id ,', ') within  group (  order by  srm.ROLE_ID) as parentRoleId  ,");
			queryBuilder.append("  LISTAGG (fun.FUN_ID , ', ') within group (  order by srm.ROLE_ID) as funId ,");
			queryBuilder.append("  LISTAGG (fun.FUN_CODE , ', ') within group (  order by srm.ROLE_ID) as funCode ");
			queryBuilder.append(" from hrm_employee E ");
			boolean flag = principal.getAuthorities() != null
					&& principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
			if (!flag) {
				queryBuilder.append("  join   hrm_system_parameters sys ");
				queryBuilder.append(" on sys.COMPANY=e.COMPANY and sys.ENTITY_SAP_CODE=:pentity ");
			}
			queryBuilder.append(" left join SAP_AM_ROLE_EMP_MAPPING srem   on srem.EMP_CODE=e.EMP_CODE ");
			queryBuilder.append(" left join SAP_AM_ROLE_MASTER srm on  srm.ROLE_ID=srem.ROLE_ID ");
			// queryBuilder.append(" and srm.ENTITY=:pentity ");
			queryBuilder.append(" left  JOIN sap_am_functions fun  ON fun.fun_id = srm.fun_id ");
			StringBuilder sWhere = new StringBuilder("  where e.status in ('C','P','N') and e.SAP_USERID is not null ");

			queryBuilder.append(sWhere);
			// countRecord.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				// query.add(Expression.like("A%"));
				Iterator<String> coulmnIt = columnName.iterator();
				coulmnIt.next();
				criteriaBuilder.append(" and ( ");

				criteriaBuilder.append(" e.SAP_USERID like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append(" e.EMP_CODE like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append(" e.EMPLOYEE_NAME like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append(" e.SAP_EMP_CODE like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder
						.append(" HRIS_GET.DEPT_CODE(E.DEPT_CODE) like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder
						.append(" HRIS_GET.DESIG_CODE(E.DESIG_CODE) like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append(" HRIS_GET.LOC_CODE(E.LOC_CODE) like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");

				queryBuilder.append(criteriaBuilder);
				// countRecord.append(criteriaBuilder);

			}
			queryBuilder.append(
					"  group by e.SAP_USERID,e.EMP_CODE, e.SAP_EMP_CODE , e.EMPLOYEE_NAME, E.DEPT_CODE, E.DESIG_CODE, E.LOC_CODE, E.CD_CODE ");
			queryBuilder.append(" ORDER BY  E.DEPT_CODE, E.CD_CODE ");
			// System.out.println(queryBuilder.toString());

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("sapUserid", StandardBasicTypes.STRING).addScalar("hrisCode", StandardBasicTypes.STRING)
					.addScalar("employeeName", StandardBasicTypes.STRING)
					.addScalar("sapEmpCode", StandardBasicTypes.STRING)
					.addScalar("department", StandardBasicTypes.STRING)
					.addScalar("designation", StandardBasicTypes.STRING)
					.addScalar("location", StandardBasicTypes.STRING).addScalar("roleId", StandardBasicTypes.STRING)
					.addScalar("roleCode", StandardBasicTypes.STRING)
					.addScalar("parentRoleId", StandardBasicTypes.STRING)
					.addScalar("roleName", StandardBasicTypes.STRING).addScalar("funId", StandardBasicTypes.STRING)
					.addScalar("funCode", StandardBasicTypes.STRING)
			// .addScalar("roleMasters.roleId", StandardBasicTypes.LONG)
			// .addScalar("roleMasters.roleCode", StandardBasicTypes.STRING)
			;
			if (!flag) {
				sqlQuery.setParameter("pentity", principal.getSapEntityCode());
			}
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = sqlQuery.list().size();
			sqlQuery.setMaxResults(criteria.iDisplayLength);
			sqlQuery.setFirstResult(criteria.iDisplayStart);
			System.out.println("HHHHHHHHHHHHHHHHH");
			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SapUserVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapUserVO>) sqlQuery.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmEmpLoanDaoJpa >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	@Override
	public List<SapAmRoleMaster> getRoles(String entity, JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		List<SapAmRoleMaster> resultList = null;
		try {

			System.out.println("List" + criteria.toString());
			StringBuilder queryBuilder = new StringBuilder(
					" SELECT ROLE.role_id AS roleId, ROLE.entity AS entity, fun.FUN_ID as funId,  fun.fun_code AS funCode, ");
			queryBuilder.append("  ROLE.\"MODULE\" AS module, ROLE.parent_role_id AS parentRoleId, ");
			queryBuilder.append("  ROLE.role_code AS roleCode, ROLE.role_name AS roleName, ");
			queryBuilder.append("  ROLE.role_type AS roleType ");
			queryBuilder.append(" FROM sap_am_role_master ROLE JOIN sap_am_functions fun ");
			queryBuilder.append(" ON fun.fun_id = ROLE.fun_id ");
			StringBuilder sWhere = new StringBuilder(" where  ROLE.role_type = 'D' and  role.entity=:pentity ");
			queryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  ROLE.entity like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  fun.fun_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  ROLE.\"MODULE\" like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  ROLE.role_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  ROLE.role_name like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString() + " ORDER BY role.FUN_ID asc ");
			Query query = session.createSQLQuery(queryBuilder.toString() + " ORDER BY  role.FUN_ID asc")
					.addScalar("roleId", StandardBasicTypes.LONG).addScalar("entity", StandardBasicTypes.STRING)
					.addScalar("funId", StandardBasicTypes.LONG).addScalar("funCode", StandardBasicTypes.STRING)
					.addScalar("module", StandardBasicTypes.STRING).addScalar("parentRoleId", StandardBasicTypes.LONG)
					.addScalar("roleCode", StandardBasicTypes.STRING).addScalar("roleName", StandardBasicTypes.STRING)
					.addScalar("roleType", StandardBasicTypes.STRING);
			query.setParameter("pentity", entity);
			// query.setParameter("pentity", "2000");
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmRoleMaster.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRoleMaster>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return resultList;

	}

	@Transactional
	@Override
	public List<SapAmRoleMaster> getRolesByTcode(String entity, SapAmRolesRequestVO sapAmRolesRequestVO,
			JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		List<SapAmRoleMaster> resultList = null;
		try {

			System.out.println("List" + criteria.toString());
			StringBuilder queryBuilder = new StringBuilder(
					" SELECT ROLE.role_id AS roleId, ROLE.entity AS entity, fun.FUN_ID as funId,  fun.fun_code AS funCode, ");
			queryBuilder.append("  ROLE.\"MODULE\" AS module, ROLE.parent_role_id AS parentRoleId, ");
			queryBuilder.append("  ROLE.role_code AS roleCode, ROLE.role_name AS roleName, ");
			queryBuilder.append("  ROLE.role_type AS roleType ");
			queryBuilder.append("  from SAP_AM_TCODES st  join  SAP_AM_TCODE_FUN_MAPPING fm ");
			queryBuilder.append(" on fm.TCODE_ID =st.sat_id   and fm.module=st.module ");
			queryBuilder.append(" JOIN  sap_am_functions fun  ON fun.fun_id = fm.fun_id ");
			queryBuilder.append(" join  sap_am_role_master ROLE  on  role.FUN_ID=fun.FUN_ID ");
			StringBuilder sWhere = new StringBuilder(
					" where  st.TCODE=:ptocde and   role.entity=:pentity and ROLE.role_type = 'D'  ");
			queryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  ROLE.entity like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  fun.fun_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  ROLE.\"MODULE\" like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  ROLE.role_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  ROLE.role_name like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString() + " ORDER BY role.FUN_ID asc ");
			Query query = session.createSQLQuery(queryBuilder.toString() + " ORDER BY  role.FUN_ID asc")
					.addScalar("roleId", StandardBasicTypes.LONG).addScalar("entity", StandardBasicTypes.STRING)
					.addScalar("funId", StandardBasicTypes.LONG).addScalar("funCode", StandardBasicTypes.STRING)
					.addScalar("module", StandardBasicTypes.STRING).addScalar("parentRoleId", StandardBasicTypes.LONG)
					.addScalar("roleCode", StandardBasicTypes.STRING).addScalar("roleName", StandardBasicTypes.STRING)
					.addScalar("roleType", StandardBasicTypes.STRING);
			// System.out.println(tcode+" :: "+entity);
			query.setParameter("ptocde", "F-37");
			query.setParameter("pentity", entity);
			// query.setParameter("pentity", "2000");
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmRoleMaster.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRoleMaster>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return resultList;
	}

	// @Transactional
	// @Override
	public List<SapAmRolesRequestVO> getRolesByTcodesD(HeapUserDetails principal, String viewName,
			SapAmRolesRequestVO sapAmRolesRequestVO, JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {
			//
			System.out.println("List" + criteria.toString());
			StringBuilder queryBuilder = new StringBuilder(
					" SELECT ROLE.roleid AS roleId, ROLE.entity AS entity, ROLE.FUN_ID as funId,  ");
			// queryBuilder.append(" ROLE.\"MODULE\" AS module, ");
			queryBuilder.append("  af.FUN_CODE AS funCode, ");
			queryBuilder.append("  ROLE.tcode AS tcode, ");
			queryBuilder.append("  ROLE.TCODE_ID AS tcodeId, ");
			queryBuilder.append("  ROLE.plant AS plant, ");
			queryBuilder.append(
					"  ROLE.REQUEST_ID as requestId,       ROLE.SUR_RECID as surRecid,   ROLE.SURT_RECID as surtRecid,");
			queryBuilder.append("  ROLE.rolecode AS roleCode, ROLE.rolename AS roleName, ");
			queryBuilder.append("  ROLE.roletype AS roleType , ROLE.IS_CONFLICTED as isConflicted ");
			// queryBuilder.append(" from "+viewName+" ROLE join SAP_AM_FUNCTIONS af on
			// af.fun_id=role.fun_id ");
			queryBuilder
					.append("  from VW_SAP_AM_ROLES ROLE  left join SAP_AM_FUNCTIONS af  on  af.fun_id=role.fun_id  ");
			StringBuilder sWhere = new StringBuilder(
					" where  ROLE.SURT_RECID in (:psurtRecids) and ROLE.roletype = 'D' order by   ROLE.TCODE, ROLE.PLANT,  ROLE.ROLECODE  ");
			queryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  ROLE.entity like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  ROLE.fun_id like UPPER('%" + criteria.sSearch + "%')  or ");
				// criteriaBuilder.append(" ROLE.\"MODULE\" like UPPER('%" + criteria.sSearch +
				// "%') or ");
				criteriaBuilder.append("  ROLE.rolecode like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  ROLE.rolename like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}
//+ " ORDER BY role.FUN_ID asc " + " ORDER BY  role.FUN_ID asc"
			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString()).addScalar("roleId", StandardBasicTypes.LONG)
					.addScalar("entity", StandardBasicTypes.STRING).addScalar("funId", StandardBasicTypes.LONG)
					.addScalar("tcode", StandardBasicTypes.STRING).addScalar("tcodeId", StandardBasicTypes.LONG)
					.addScalar("plant", StandardBasicTypes.STRING)
					.addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surtRecid", StandardBasicTypes.LONG).addScalar("funCode", StandardBasicTypes.STRING)
					// .addScalar("module", StandardBasicTypes.STRING).
					// addScalar("parentRoleId", StandardBasicTypes.LONG)
					.addScalar("roleCode", StandardBasicTypes.STRING).addScalar("roleName", StandardBasicTypes.STRING)
					.addScalar("roleType", StandardBasicTypes.STRING)
					.addScalar("isConflicted", StandardBasicTypes.STRING);
			// System.out.println(tcode+" :: "+entity);
			query.setParameterList("psurtRecids", sapAmRolesRequestVO.getSurtRecids());
			// query.setParameter("pentity", entity);
			// query.setParameter("pentity", "2000");
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;
	}

    @Transactional
	@Override
	public List<SapAmRolesRequestVO> getRolesByTcodes(HeapUserDetails principal, String viewName,
			SapAmRolesRequestVO sapAmRolesRequestVO, JQueryDataTableParamModel criteria) throws Exception {
    	List<String> compenies=this.getCompanies(sapAmRolesRequestVO.getSurRecid().longValue(), sapAmRolesRequestVO.getRequestId().longValue());
    	List<Long> tempSurRecids= this.getTocde9999Plant(principal, "", sapAmRolesRequestVO,criteria);
		Session session = currentSession();
            
		try {
			//
			System.out.println("List" + criteria.toString());
			StringBuilder queryBuilder = new StringBuilder(" SELECT distinct tm.role_id AS roleId, tm.entity AS entity,   ");
			// queryBuilder.append(" ROLE.\"MODULE\" AS module, ");
			// ROLE.role_code AS roleCode, ROLE.role_name AS roleName, ");
			// queryBuilder.append(" ROLE.role_type
			queryBuilder.append("  tm.tcode AS tcode, at.module as module, ");
			queryBuilder.append("  tm.TCODE_ID AS tcodeId, ");
			queryBuilder.append("  tm.plant AS plant, ");
			queryBuilder.append(
					"  rt.REQUEST_ID as requestId,       rt.SUR_RECID as surRecid,   rt.SURT_RECID as surtRecid,");
			queryBuilder.append("  tm.role_code AS roleCode, tm.role_name AS roleName, ");
			queryBuilder.append("  tm.role_type AS roleType  ");
			queryBuilder.append(" , nvl( (select 'Y' from SAP_AM_USER_ASSIGNED_ROLES aar where aar.REQUEST_ID = rt.REQUEST_ID and aar.role_id=tm.role_id ),'N')   as roleAssined  ");
			//queryBuilder.append(" , SESETIVEROLE(tm.role_CODE) sensitive   ");
			queryBuilder.append(
					"  from SAP_AM_USER_REQUESTS ur join  SAP_AM_USER_REQUEST_TCODES  rt on   ur.REQUEST_ID = rt.REQUEST_ID join  sap_am_tcodes at on at.sat_id=rt.tcode_id   join SAP_AM_USER_REQUEST_PLANTS rp on   rt.REQUEST_ID = rp.REQUEST_ID  ");
			queryBuilder.append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=rt.TCODE_ID and    tm.TCODE=rt.TCODE   ");
			
			queryBuilder.append(
					"    and   tm.entity in (:psapCompanyCode) and   (( rp.plant=tm.plant  or rp.PURCHASE_GROUP=tm.PURCHASE_GROUP)  ");
//			queryBuilder.append(
//					"    and   tm.entity=ur.SAP_COMPANY_CODE and   (( rp.plant=tm.plant  or rp.PURCHASE_GROUP=tm.PURCHASE_GROUP)  ");
			queryBuilder.append(
					"     or ( tm.plant='9999'and  rt.SURT_RECID in (:ptsurtRecids)))  and tm.status ='A' ");
			queryBuilder.append(
					" where  rt.SURT_RECID in (:psurtRecids) and rt.STATUS NOT IN ('I','R')and  rp.STATUS NOT IN ('I','R')  ");
			StringBuilder sWhere = new StringBuilder(
					//" order by   tm.TCODE, tm.PLANT,  tm.ROLE_CODE  ");
		           " and tm.role_type = 'D' ");
			queryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  tm.entity like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  tm.role_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  tm.role_name like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
				
			}
			queryBuilder.append("  order by   tm.TCODE, tm.PLANT,  tm.ROLE_CODE  ");
//+ " ORDER BY role.FUN_ID asc " + " ORDER BY  role.FUN_ID asc"
			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString()).addScalar("roleId", StandardBasicTypes.LONG)
					.addScalar("entity", StandardBasicTypes.STRING).addScalar("tcode", StandardBasicTypes.STRING)
					.addScalar("tcodeId", StandardBasicTypes.LONG)
					.addScalar("plant", StandardBasicTypes.STRING)
					.addScalar("roleAssined", StandardBasicTypes.STRING)
					.addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surtRecid", StandardBasicTypes.LONG).addScalar("roleCode", StandardBasicTypes.STRING)
					.addScalar("roleName", StandardBasicTypes.STRING)
					.addScalar("module", StandardBasicTypes.STRING)
					//.addScalar("sensitive", StandardBasicTypes.STRING)
					.addScalar("roleType", StandardBasicTypes.STRING);
			// System.out.println(tcode+" :: "+entity);
			//List<String> compenies= "ALL".equalsIgnoreCase(sapAmRolesRequestVO.getEntity())?Arrays.asList("1000","2000","3000"):Arrays.asList( sapAmRolesRequestVO.getEntity());
			query.setParameterList("psapCompanyCode", compenies);
			query.setParameterList("psurtRecids", sapAmRolesRequestVO.getSurtRecids());
			query.setParameterList("ptsurtRecids",tempSurRecids );
			// query.setParameter("pentity", entity);
			// query.setParameter("pentity", "2000");
			criteria.iTotalDisplayRecords = criteria.iTotalRecords=query.list().size();		 
		    query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;
	}
    
 List<String> getCompanies(Long surRecid, Long requestId){
		
		
		Session session = currentSession();

		try {

			StringBuilder queryBuilder = new StringBuilder(
					" select distinct ur.sap_company_code  from SAP_AM_USER_REQUESTS ur ");
			StringBuilder sWhere = new StringBuilder(" where ur.SUR_RECID=:psurRecid and ");
			sWhere.append(" ur.REQUEST_ID=:prequestId and ");
			sWhere.append(" ur.status not in 'R'  ");
			queryBuilder.append(sWhere);
					Query sqlQuery = session.createSQLQuery(queryBuilder.toString());
					sqlQuery.setParameter("psurRecid", surRecid);
					sqlQuery.setParameter("prequestId", requestId);
					
				String temp=	(String) sqlQuery.uniqueResult();
			return "ALL".equalsIgnoreCase(temp)?Arrays.asList("1000","2000","3000"):Arrays.asList(temp);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> MasterDataDaoImpl >> getSapPlants", e);
			e.printStackTrace();
			throw e;
		}
		
	}
   
	public List<Long> getTocde9999Plant(HeapUserDetails principal, String viewName,
			SapAmRolesRequestVO sapAmRolesRequestVO, JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {
			//
			System.out.println("List" + criteria.toString());
			StringBuilder queryBuilder = new StringBuilder("  SELECT    distinct  rt.SURT_RECID   ");
			// queryBuilder.append(" rt.tcode AS tcode,  ");
			

			queryBuilder.append(
					"  from    SAP_AM_USER_REQUEST_TCODES  rt  join SAP_AM_USER_REQUEST_PLANTS rp on   rt.REQUEST_ID = rp.REQUEST_ID  ");
			queryBuilder
					.append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=rt.TCODE_ID and    tm.TCODE=rt.TCODE   ");
			queryBuilder.append(
					"    and  ( rp.plant=tm.plant or rp.PURCHASE_GROUP=tm.PURCHASE_GROUP ) and tm.status ='A'   ");
			queryBuilder.append(
					" where  rt.SURT_RECID in (:psurtRecids) and rt.STATUS NOT IN ('I','R')and  rp.STATUS NOT IN ('I','R')  ");
			StringBuilder sWhere = new StringBuilder(
					"  and tm.ROLE_CODE is null  ");
			queryBuilder.append(sWhere);
			Query query = session.createSQLQuery(queryBuilder.toString());
			query.setParameterList("psurtRecids", sapAmRolesRequestVO.getSurtRecids());		
			//query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));
			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<Long>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;
	}

    
   // @Transactional
	@Override
	public List<SapAmRolesRequestVO> getRoles9999Plant(HeapUserDetails principal, String viewName,
			Set<Long> surtRecids, JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {
			//
			System.out.println("List" + criteria.toString());
			StringBuilder queryBuilder = new StringBuilder(" SELECT  distinct tm.role_id AS roleId, tm.entity AS entity,   ");
			// queryBuilder.append(" ROLE.\"MODULE\" AS module, ");
			// ROLE.role_code AS roleCode, ROLE.role_name AS roleName, ");
			// queryBuilder.append(" ROLE.role_type
			queryBuilder.append("  tm.tcode AS tcode, ");
			queryBuilder.append("  tm.TCODE_ID AS tcodeId, ");
			queryBuilder.append("  tm.plant AS plant, ");
			queryBuilder.append(
					"  rt.REQUEST_ID as requestId,  rt.SUR_RECID as surRecid,   rt.SURT_RECID as surtRecid,");
			queryBuilder.append("  tm.role_code AS roleCode, tm.role_name AS roleName, ");
			queryBuilder.append("  tm.role_type AS roleType  ");

			queryBuilder.append(
					"  from   SAP_AM_USER_REQUESTS ur join    SAP_AM_USER_REQUEST_TCODES  rt on   ur.REQUEST_ID = rt.REQUEST_ID      join SAP_AM_USER_REQUEST_PLANTS rp on   rt.REQUEST_ID = rp.REQUEST_ID  ");
			queryBuilder
					// .append(" left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=rt.TCODE_ID
					// and tm.TCODE=rt.TCODE and tm.MODULE=rt.SAP_MODULE and
					// tm.MODULE=rt.SAP_MODULE");
					.append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=rt.TCODE_ID and    tm.TCODE=rt.TCODE   ");
			queryBuilder.append(
					"    and  tm.entity=ur.SAP_COMPANY_CODE and  ( tm.plant='9999' or rp.PURCHASE_GROUP=tm.PURCHASE_GROUP ) and tm.status ='A'   ");
			queryBuilder.append(
					" where  rt.SURT_RECID in (:psurtRecids) and rt.STATUS NOT IN ('I','R')and  rp.STATUS NOT IN ('I','R')  ");
			StringBuilder sWhere = new StringBuilder(
					" and tm.role_type = 'D' order by   tm.TCODE, tm.PLANT,  tm.ROLE_CODE  ");
			queryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  tm.entity like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  tm.role_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  tm.role_name like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}
            //+ " ORDER BY role.FUN_ID asc " + " ORDER BY  role.FUN_ID asc"
			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString()).addScalar("roleId", StandardBasicTypes.LONG)
					.addScalar("entity", StandardBasicTypes.STRING).addScalar("tcode", StandardBasicTypes.STRING)
					.addScalar("tcodeId", StandardBasicTypes.LONG).addScalar("plant", StandardBasicTypes.STRING)
					.addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surtRecid", StandardBasicTypes.LONG).addScalar("roleCode", StandardBasicTypes.STRING)
					.addScalar("roleName", StandardBasicTypes.STRING).addScalar("roleType", StandardBasicTypes.STRING);
			// System.out.println(tcode+" :: "+entity);
			query.setParameterList("psurtRecids", surtRecids);
			// query.setParameter("pentity", entity);
			// query.setParameter("pentity", "2000");
			//criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			//query.setMaxResults(criteria.iDisplayLength);
			//query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;
	}

    
    
	//@Transactional
	//@Override
	public List<SapAmRolesRequestVO> getRolesByTcodesdd(HeapUserDetails principal, String viewName,
			SapAmRolesRequestVO sapAmRolesRequestVO, JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {
			//
			System.out.println("List" + criteria.toString());
			
			StringBuilder queryBuilder = new StringBuilder(" SELECT   CASE   WHEN NVL (tm.entity, '%%%') != '%%%'   THEN tm.entity  ");
			queryBuilder.append("  ELSE (SELECT    DISTINCT tim.entity   FROM    sap_am_role_tcode_mapping tim ");
			queryBuilder.append("  WHERE   tim.tcode_id = rt.tcode_id AND tim.tcode = RT.tcode ");
			queryBuilder.append("  AND ( tim.plant = '9999'  OR rp.purchase_group = tim.purchase_group ) ");
			queryBuilder.append("  AND tim.entity IS NOT NULL   AND tim.status = 'A' ");
			//queryBuilder.append("   AND ROWNUM <= 1 group by tim.entity,tim.tcode,tm.role_ID HAVING count(tim.tcode)>1 )  END AS entity, ");
			queryBuilder.append("   AND ROWNUM <= 1) END AS entity, ");
			
			
			queryBuilder.append("  rt.tcode AS tcode, ");
			queryBuilder.append("  rt.TCODE_ID AS tcodeId, ");
			queryBuilder.append(
					" CASE   WHEN NVL (tm.role_code, '%%%') != '%%%'   THEN tm.role_code   ELSE (SELECT DISTINCT tim.role_code ");
			queryBuilder.append(
					" FROM sap_am_role_tcode_mapping tim   WHERE tim.tcode_id = rt.tcode_id   AND tim.tcode = RT.tcode ");
//tm.TCODE_ID=rt.TCODE_ID and    tm.TCODE=rt.TCODE
			queryBuilder.append(" AND (   tim.plant = '9999'  OR rp.purchase_group = tim.purchase_group ) ");

			//queryBuilder.append(" AND ur.sap_company_code = tim.entity  AND tim.role_code IS NOT NULL ");
			queryBuilder.append("   AND tim.role_code IS NOT NULL ");

			queryBuilder.append(" AND tim.status = 'A'   AND ROWNUM <= 1) END AS roleCode, ");

			queryBuilder.append(
					" CASE  WHEN NVL (tm.role_id, '0') != '0'  THEN tm.role_id ELSE (SELECT DISTINCT tim.role_id ");

			queryBuilder.append(
					" FROM sap_am_role_tcode_mapping tim WHERE tim.tcode_id = rt.tcode_id   AND tim.tcode = RT.tcode ");

			queryBuilder.append(" AND (   tim.plant = '9999' OR rp.purchase_group = tim.purchase_group ) ");
			queryBuilder.append("  AND tim.role_code IS NOT NULL ");
			queryBuilder.append(" AND tim.status = 'A'  AND ROWNUM <= 1)   END AS roleId, ");
			
			
			queryBuilder.append(
					" CASE   WHEN NVL (tm.role_name, '%%%') != '%%%'   THEN tm.role_name   ELSE (SELECT DISTINCT tim.role_name ");
			queryBuilder.append(
					" FROM sap_am_role_tcode_mapping tim   WHERE tim.tcode_id = rt.tcode_id   AND tim.tcode = RT.tcode ");

			queryBuilder.append(" AND (   tim.plant = '9999'  OR rp.purchase_group = tim.purchase_group ) ");

			queryBuilder.append("  AND tim.role_name IS NOT NULL ");

			queryBuilder.append(" AND tim.status = 'A'   AND ROWNUM <= 1) END AS roleName, ");
			
			queryBuilder.append(" CASE       WHEN NVL (tm.plant, '%%%') != '%%%'      THEN tm.plant     ELSE '9999'    END AS plant, ");

			queryBuilder.append(
					"  rt.REQUEST_ID as requestId,       rt.SUR_RECID as surRecid,   rt.SURT_RECID as surtRecid ");
			// queryBuilder.append(" tm.role_code AS roleCode, tm.role_name AS roleName, ");
			 queryBuilder.append(" , nvl( (select 'Y' from SAP_AM_USER_ASSIGNED_ROLES aar where aar.REQUEST_ID = rt.REQUEST_ID and aar.role_id=tm.role_id ),'N')   as roleAssined  ");
			// queryBuilder.append(" , tm.role_type AS roleType  ");

			queryBuilder.append(
					"  from   SAP_AM_USER_REQUEST_TCODES  rt    join SAP_AM_USER_REQUEST_PLANTS rp on   rt.REQUEST_ID = rp.REQUEST_ID  ");
			queryBuilder
					// .append(" left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=rt.TCODE_ID
					// and tm.TCODE=rt.TCODE and tm.MODULE=rt.SAP_MODULE and
					// tm.MODULE=rt.SAP_MODULE");
					.append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=rt.TCODE_ID and    tm.TCODE=rt.TCODE   ");
			queryBuilder.append(
					"    and   (rp.plant=tm.plant or rp.PURCHASE_GROUP=tm.PURCHASE_GROUP )  and tm.status ='A' ");
			queryBuilder.append(
					" where  rt.SURT_RECID in (:psurtRecids) and rt.STATUS NOT IN ('I','R')and  rp.STATUS NOT IN ('I','R')  ");
//			StringBuilder sWhere = new StringBuilder(
//					" and tm.role_type = 'D' order by   tm.TCODE, tm.PLANT,  tm.ROLE_CODE  ");
			StringBuilder sWhere = new StringBuilder(
					" order by   tm.TCODE, tm.PLANT,  tm.ROLE_CODE  ");
			queryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  tm.entity like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  tm.role_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  tm.role_name like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}
//+ " ORDER BY role.FUN_ID asc " + " ORDER BY  role.FUN_ID asc"
			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString()).addScalar("roleId", StandardBasicTypes.LONG)
					.addScalar("entity", StandardBasicTypes.STRING).addScalar("tcode", StandardBasicTypes.STRING)
					.addScalar("tcodeId", StandardBasicTypes.LONG).addScalar("plant", StandardBasicTypes.STRING)
					.addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surtRecid", StandardBasicTypes.LONG)
					.addScalar("roleCode", StandardBasicTypes.STRING)
					.addScalar("roleAssined", StandardBasicTypes.STRING)
					.addScalar("roleName", StandardBasicTypes.STRING);
					//.addScalar("roleType", StandardBasicTypes.STRING);
			// System.out.println(tcode+" :: "+entity);
			query.setParameterList("psurtRecids", sapAmRolesRequestVO.getSurtRecids());
			// query.setParameter("pentity", entity);
			// query.setParameter("pentity", "2000");
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public List<SapAmRoleEmpMapping> getRoleByUser(String sapEmpCode) {
		// TODO Auto-generated method stub
		Session session = currentSession();

		try {

			Query query = session.createQuery(
					"  from SapAmRoleEmpMapping em where  em.sapEmpCode=:sapEmpCode and em.status=:status ");
			query.setParameter("sapEmpCode", sapEmpCode);
			query.setParameter("status", "A");
			// query.setResultTransformer(new
			// AliasToBeanNestedResultTransformer(SapAmRoleEmpMapping.class));

			return (List<SapAmRoleEmpMapping>) query.list();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return null;
	}

//	@Transactional
//	@Override
	public List<SapAmRolesRequestVO> getRolesByRequestOld(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {

			System.out.println("List" + criteria.toString());
			StringBuilder queryBuilder = new StringBuilder(
					"select module AS module, role_id AS roleId, role_code AS roleCode,       entity AS entity, is_conflicted AS isConflicted, role_name AS roleName, ");

			queryBuilder.append(
					"   role_type AS roleType, sur_recid AS surRecid, request_id AS requestId ,surp_recid as surpRecid , funId , funCode   ");

			queryBuilder.append(
					" FROM (SELECT m.module, m.role_id, m.role_code, m.entity,   m.is_conflicted , m.role_name ,   m.role_type , p.sur_recid, p.request_id, p.surp_recid,   f.fun_id AS funId, f.fun_code AS funCode , p.status ");

			queryBuilder.append(
					"   FROM sap_am_role_master m JOIN sap_am_functions f     ON f.fun_id = m.fun_id       JOIN sap_am_user_request_plants p   ON m.role_id = p.role_id AND m.role_code = p.role_code  ");
			queryBuilder.append("   UNION ALL ");
			queryBuilder.append(
					"   SELECT m.module, m.role_id, m.role_code, m.entity, 'N' ,  m.role_name , m.role_type , p.sur_recid, p.request_id,  p.surp_recid, null AS funId , '' AS funCode , p.status   ");
			queryBuilder.append(
					"   FROM sap_am_role_tcode_mapping m JOIN sap_am_user_request_plants p  ON m.role_id = p.role_id AND m.role_code = p.role_code     )  ");
			StringBuilder sWhere = new StringBuilder(
					" WHERE request_id =:prequestId AND sur_recid =:psurRecid AND status NOT IN ('I', 'R')");
			// " where ROLE.role_type = 'D' and p.REQUEST_ID=:prequestId and
			// p.SUR_RECID=:psurRecid ");
			queryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  entity like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  funId like UPPER('%" + criteria.sSearch + "%')  or ");
				// criteriaBuilder.append(" ROLE.\"MODULE\" like UPPER('%" + criteria.sSearch +
				// "%') or ");
				criteriaBuilder.append("  roleCode like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  roleName like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString()).addScalar("roleId", StandardBasicTypes.LONG)
					.addScalar("entity", StandardBasicTypes.STRING).addScalar("surpRecid", StandardBasicTypes.LONG)
					.addScalar("funId", StandardBasicTypes.LONG).addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("funCode", StandardBasicTypes.STRING)
					// .addScalar("parentRoleId", StandardBasicTypes.LONG)
					.addScalar("roleCode", StandardBasicTypes.STRING).addScalar("roleName", StandardBasicTypes.STRING)
					.addScalar("roleType", StandardBasicTypes.STRING)
					.addScalar("isConflicted", StandardBasicTypes.STRING);
			log.info("requestId" + requestId + "surRecid" + surRecid);
			query.setParameter("prequestId", requestId);
			query.setParameter("psurRecid", surRecid);
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public List<SapAmRolesRequestVO> getRolesByRequest(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {

			System.out.println("List" + criteria.toString());
			StringBuilder queryBuilder = new StringBuilder(
					"select distinct m.module AS module, m.role_id AS roleId, m.role_code AS roleCode,   m.entity AS entity,   m.role_name AS roleName, m.plant AS plant,");

			queryBuilder.append(
					"   m.role_type AS roleType, p.sur_recid AS surRecid, p.request_id AS requestId ,p.surp_recid as surpRecid, p.status as status  ");
			//queryBuilder.append(" ,  SESETIVEROLE(m.role_CODE)as  sensitive  ");

			queryBuilder.append(
					"   FROM sap_am_role_tcode_mapping m JOIN sap_am_user_request_plants p  ON m.role_id = p.role_id AND m.role_code = p.role_code  and m.status='A' ");

			queryBuilder.append(
					" join SAP_AM_USER_REQUEST_APPROVALS   ur   on   p.request_id =ur.REQUEST_ID AND p.sur_recid =ur.PARENT_RECID");// and p.SAP_MODULE=ur.SAP_MODULE ");
			StringBuilder sWhere = new StringBuilder(
					" WHERE p.request_id =:prequestId AND p.sur_recid =:psurRecid  AND p.status NOT IN ('I', 'R')");
			// " where ROLE.role_type = 'D' and p.REQUEST_ID=:prequestId and and
			// ur.emp_code=:pempCode
			// p.SUR_RECID=:psurRecid ");
			// " WHERE p.request_id =:prequestId AND p.sur_recid =:psurRecid AND p.status
			// NOT IN ('I', 'R') and and ur.emp_code=:pempCode ");
			queryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  m.entity like UPPER('%" + criteria.sSearch + "%')  or ");

				criteriaBuilder.append("  m.role_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  m.role_name like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString() + " ORDER BY  m.ROLE_ID  asc ")
					.addScalar("roleId", StandardBasicTypes.LONG).addScalar("entity", StandardBasicTypes.STRING)
					.addScalar("module", StandardBasicTypes.STRING).addScalar("plant", StandardBasicTypes.STRING)
					.addScalar("surpRecid", StandardBasicTypes.LONG)
					.addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("roleCode", StandardBasicTypes.STRING).addScalar("roleName", StandardBasicTypes.STRING)
					//.addScalar("sensitive", StandardBasicTypes.STRING)
					.addScalar("roleType", StandardBasicTypes.STRING);
			log.info("requestId" + requestId + "surRecid" + surRecid);
			query.setParameter("prequestId", requestId);
			query.setParameter("psurRecid", surRecid);
			// query.setParameter("pempCode", principal.getEmpCode());
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public List<SapAmRolesRequestVO> getRolesByRequestEmpCode(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {

			System.out.println("List" + criteria.toString());
			StringBuilder queryBuilder = new StringBuilder(
					"select distinct m.module AS module, m.role_id AS roleId, m.role_code AS roleCode,   m.entity AS entity,   m.role_name AS roleName, m.plant AS plant,");

			queryBuilder.append(
					"   m.role_type AS roleType, p.sur_recid AS surRecid, p.request_id AS requestId ,p.surp_recid as surpRecid, p.status as status  ");
			queryBuilder.append(" , nvl( (select 'Y' from SAP_AM_USER_ASSIGNED_ROLES aar where aar.REQUEST_ID = p.request_id and aar.role_id=m.role_id ),'N')   as roleAssined  ");
			//queryBuilder.append(" ,  SESETIVEROLE(m.role_CODE) as sensitive ");
			queryBuilder.append(
					"   FROM sap_am_role_tcode_mapping m JOIN sap_am_user_request_plants p  ON m.role_id = p.role_id AND m.role_code = p.role_code  and m.status='A' ");

			queryBuilder.append(
					" join SAP_AM_USER_REQUEST_APPROVALS   ur   on   p.request_id =ur.REQUEST_ID AND p.sur_recid =ur.PARENT_RECID ");//and p.SAP_MODULE=ur.SAP_MODULE 
			StringBuilder sWhere = new StringBuilder(
					// " WHERE p.request_id =:prequestId AND p.sur_recid =:psurRecid AND p.status
					// NOT IN ('I', 'R')");
					// " where ROLE.role_type = 'D' and p.REQUEST_ID=:prequestId and and
					// ur.emp_code=:pempCode
					// p.SUR_RECID=:psurRecid ");
					" WHERE p.request_id =:prequestId AND p.sur_recid =:psurRecid and ur.emp_code=:pempCode AND p.status NOT IN ('I', 'R')  ");
			queryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  m.entity like UPPER('%" + criteria.sSearch + "%')  or ");

				criteriaBuilder.append("  m.role_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  m.role_name like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString() + " ORDER BY  m.ROLE_ID  asc ")
					.addScalar("roleId", StandardBasicTypes.LONG).addScalar("entity", StandardBasicTypes.STRING)
					.addScalar("module", StandardBasicTypes.STRING).addScalar("plant", StandardBasicTypes.STRING)
					.addScalar("surpRecid", StandardBasicTypes.LONG)
					.addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("roleCode", StandardBasicTypes.STRING)
					//.addScalar("sensitive", StandardBasicTypes.STRING)
					.addScalar("roleName", StandardBasicTypes.STRING)
					.addScalar("roleAssined", StandardBasicTypes.STRING)
					.addScalar("roleType", StandardBasicTypes.STRING);
			log.info("requestId" + requestId + "surRecid" + surRecid);
			query.setParameter("prequestId", requestId);
			query.setParameter("psurRecid", surRecid);
			query.setParameter("pempCode", principal.getEmpCode());
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public List<SapAmRolesRequestVO> getRolesByRequestEHOD(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {

			System.out.println("List" + criteria.toString());
			StringBuilder countQueryBuilder = new StringBuilder(" select count(*) from sap_am_role_tcode_mapping m ");
			StringBuilder queryBuilder = new StringBuilder(
					"select m.module AS module, m.role_id AS roleId, m.role_code AS roleCode,       m.entity AS entity,  m.role_name AS roleName, m.plant AS plant,");

			queryBuilder.append(
					"   m.role_type AS roleType, p.sur_recid AS surRecid, p.request_id AS requestId ,p.surp_recid as surpRecid, p.status as status  ");
			//queryBuilder.append(" ,  SESETIVEROLE(m.role_CODE)  sensitive ");

			queryBuilder.append(
					"   FROM sap_am_role_tcode_mapping m JOIN sap_am_user_request_plants p  ON m.role_id = p.role_id AND m.role_code = p.role_code  and m.status='A' ");
			StringBuilder sWhere = new StringBuilder(
					" WHERE p.request_id =:prequestId AND p.sur_recid =:psurRecid AND p.status ='S' ");
			// " where ROLE.role_type = 'D' and p.REQUEST_ID=:prequestId and
			// p.SUR_RECID=:psurRecid ");
			queryBuilder.append(sWhere);
			countQueryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  m.entity like UPPER('%" + criteria.sSearch + "%')  or ");

				criteriaBuilder.append("  m.role_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  m.role_name like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString()).addScalar("roleId", StandardBasicTypes.LONG)
					.addScalar("entity", StandardBasicTypes.STRING).addScalar("plant", StandardBasicTypes.STRING)
					.addScalar("surpRecid", StandardBasicTypes.LONG)
					.addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("roleCode", StandardBasicTypes.STRING).addScalar("roleName", StandardBasicTypes.STRING)
					//.addScalar("sensitive", StandardBasicTypes.STRING)
					.addScalar("roleType", StandardBasicTypes.STRING);
			log.info("requestId" + requestId + "surRecid" + surRecid);
			
			Query countQuery = session.createSQLQuery(countQueryBuilder.toString());
			countQuery.setParameter("prequestId", requestId);
			countQuery.setParameter("psurRecid", surRecid);
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = countQuery.list().size();		
			
			query.setParameter("prequestId", requestId);
			query.setParameter("psurRecid", surRecid);			
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;
	}

	// @Transactional
	// @Override
//	public List<SapAmRolesRequestVO> getRolesByRequest@(HeapUserDetails principal, long requestId, long surRecid,
//			JQueryDataTableParamModel criteria) throws Exception {
//		Session session = currentSession();
//
//		try {
//
//			System.out.println("List" + criteria.toString());
//			StringBuilder queryBuilder = new StringBuilder(
//					" SELECT ROLE.role_id AS roleId,        ROLE.entity AS entity,        fun.FUN_ID as funId,        fun.fun_code AS funCode,  ROLE.IS_CONFLICTED as isConflicted , ");
//
//			queryBuilder.append(
//					"  ROLE.role_code AS roleCode,        ROLE.role_name AS roleName,        ROLE.role_type AS roleType   ");
//
//			queryBuilder.append(" , p.REQUEST_ID as requestId,       p.SUR_RECID as surRecid");
//
//			queryBuilder.append("  from sap_am_role_master ROLE   JOIN SAP_AM_USER_REQUEST_PLANTS p on  ");
//			queryBuilder.append("  ROLE.role_id=p.role_id and   ROLE.role_code=p.role_code ");
//			queryBuilder.append("   JOIN  sap_am_functions fun ON fun.fun_id = ROLE.fun_id   ");
//			queryBuilder.append("   JOIN  sap_am_functions fun ON fun.fun_id = ROLE.fun_id   ");
//			StringBuilder sWhere = new StringBuilder(
//					" where ROLE.role_type = 'D'  and p.REQUEST_ID=:prequestId and  p.SUR_RECID=:psurRecid  ");
//			queryBuilder.append(sWhere);
//			StringBuilder criteriaBuilder = new StringBuilder();
//			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
//				criteriaBuilder.append(" and ( ");
//				criteriaBuilder.append("  ROLE.entity like UPPER('%" + criteria.sSearch + "%')  or ");
//				criteriaBuilder.append("  ROLE.fun_id like UPPER('%" + criteria.sSearch + "%')  or ");
//				// criteriaBuilder.append(" ROLE.\"MODULE\" like UPPER('%" + criteria.sSearch +
//				// "%') or ");
//				criteriaBuilder.append("  ROLE.rolecode like UPPER('%" + criteria.sSearch + "%')  or ");
//				criteriaBuilder.append("  ROLE.rolename like UPPER('%" + criteria.sSearch + "%')  or ");
//				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
//				criteriaBuilder.append(" ) ");
//				queryBuilder.append(criteriaBuilder.toString());
//			}
//
//			System.out.println(queryBuilder.toString());
//			Query query = session.createSQLQuery(queryBuilder.toString()).addScalar("roleId", StandardBasicTypes.LONG)
//					.addScalar("entity", StandardBasicTypes.STRING).addScalar("funId", StandardBasicTypes.LONG)
//					.addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
//					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
//					.addScalar("funCode", StandardBasicTypes.STRING)
//					// .addScalar("parentRoleId", StandardBasicTypes.LONG)
//					.addScalar("roleCode", StandardBasicTypes.STRING).addScalar("roleName", StandardBasicTypes.STRING)
//					.addScalar("roleType", StandardBasicTypes.STRING)
//					.addScalar("isConflicted", StandardBasicTypes.STRING);
//
//			query.setParameter("prequestId", requestId);
//			query.setParameter("psurRecid", surRecid);
//			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
//			query.setMaxResults(criteria.iDisplayLength);
//			query.setFirstResult(criteria.iDisplayStart);
//			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));
//
//			System.out.println("HHHHHHHHHHHHHHHHH");
//			return (List<SapAmRolesRequestVO>) query.list();
//		} catch (Exception e) {
//			// TODO: handle exception
//			log.error("ERROR >> SapAmRoleMaster >> get", e);
//			e.printStackTrace();
//		}
//		return null;
//	}

	// @Override
	public List<SapAmRolesRequestVO> getRolesModulesD(SapAmRequestStatusVO sapAmRequestVO) throws Exception {
		Session session = currentSession();

		try {
			//

			StringBuilder queryBuilder = new StringBuilder(
					" select MODULE AS module ,ROLE_ID as roleId, ROLE_CODE AS roleCode, entity as entity, SUR_RECID as surRecid,REQUEST_ID as requestId  ");
			queryBuilder.append(
					"  from (select m.MODULE ,m.ROLE_ID, m.ROLE_CODE, m.entity, p.SUR_RECID,p.REQUEST_ID ,p.status from SAP_AM_ROLE_MASTER m  join  SAP_AM_USER_REQUEST_PLANTS  p on m.ROLE_ID= p.ROLE_ID and   m.ROLE_CODE=p.ROLE_CODE ");
			queryBuilder.append(
					"    union all   select m.MODULE ,m.ROLE_ID, m.ROLE_CODE, '', p.SUR_RECID,p.REQUEST_ID, p.status from      SAP_AM_ROLE_TCODE_MAPPING m join  SAP_AM_USER_REQUEST_PLANTS  p on m.ROLE_ID= p.ROLE_ID and   m.ROLE_CODE=p.ROLE_CODE )");

			StringBuilder sWhere = new StringBuilder(
					" where REQUEST_ID=:prequestId and SUR_RECID =:psurRecid  and STATUS ='I'   ");
			queryBuilder.append(sWhere);

			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString()).addScalar("roleId", StandardBasicTypes.LONG)
					.addScalar("entity", StandardBasicTypes.STRING)
					.addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("module", StandardBasicTypes.STRING).addScalar("roleCode", StandardBasicTypes.STRING);
			// .addScalar("roleName", StandardBasicTypes.STRING);
			// .addScalar("roleType", StandardBasicTypes.STRING);

			query.setParameter("psurRecid", sapAmRequestVO.getSurRecid());
			query.setParameter("prequestId", sapAmRequestVO.getRequestId().longValue());
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SapAmRolesRequestVO> getRolesModules(SapAmRequestStatusVO sapAmRequestVO) throws Exception {
		Session session = currentSession();

		try {
			//

			StringBuilder queryBuilder = new StringBuilder(
					" select m.MODULE AS module , m.ROLE_ID as roleId , m.ROLE_CODE AS roleCode , m.entity as entity ,m.plant as plant, p.SUR_RECID as surRecid, p.REQUEST_ID as requestId, p.status from      SAP_AM_ROLE_TCODE_MAPPING m join  SAP_AM_USER_REQUEST_PLANTS  p on m.ROLE_ID= p.ROLE_ID and   m.ROLE_CODE=p.ROLE_CODE ");

			StringBuilder sWhere = new StringBuilder(
					" where p.REQUEST_ID=:prequestId and p.SUR_RECID =:psurRecid  and p.STATUS ='I'   ");
			queryBuilder.append(sWhere);

			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString()).addScalar("roleId", StandardBasicTypes.LONG)
					.addScalar("entity", StandardBasicTypes.STRING)
					.addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("module", StandardBasicTypes.STRING).addScalar("plant", StandardBasicTypes.STRING)
					.addScalar("roleCode", StandardBasicTypes.STRING);
			// .addScalar("roleName", StandardBasicTypes.STRING);
			// .addScalar("roleType", StandardBasicTypes.STRING);

			query.setParameter("psurRecid", sapAmRequestVO.getSurRecid());
			query.setParameter("prequestId", sapAmRequestVO.getRequestId().longValue());
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;
	}

}
