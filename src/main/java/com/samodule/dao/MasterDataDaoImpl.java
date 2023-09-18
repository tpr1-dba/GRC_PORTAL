package com.samodule.dao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmMasterTypeDetail;
import com.samodule.model.SapAmRoleMaster;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.vo.CompanyVO;
import com.samodule.vo.ModuleVO;
import com.samodule.vo.PlantVO;
import com.samodule.vo.SapAmOtherUserVO;
import com.samodule.vo.SapAmRolesRequestVO;
import com.samodule.vo.SapAmTcodeVO;
import com.samodule.vo.SapUserVO;

@Repository("masterDataDao")
public class MasterDataDaoImpl extends JpaDao<Object> implements MasterDataDao {
	private static final Logger logger = LoggerFactory.getLogger(MasterDataDaoImpl.class);

	@Override
	@Transactional
	public List<CompanyVO> getSapCompany() {
		Session session = currentSession();
		// session.beginTransaction();
		try {

			StringBuilder queryBuilder = new StringBuilder(
					" select  distinct a.COMPANY_NAME as companyName, a.ENTITY_SAP_CODE as companyCode from  HRM_SYSTEM_PARAMETERS a where a.status='A'  order by a.ENTITY_SAP_CODE  ");
			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())

					.addScalar("companyName", StringType.INSTANCE).addScalar("companyCode", StringType.INSTANCE);

			// sqlQuery.setParameterList("status", new String[]{"C","P","N"});
			sqlQuery.setResultTransformer(new AliasToBeanResultTransformer(CompanyVO.class));

			// session.getTransaction().commit();

			return (List<CompanyVO>) sqlQuery.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> getSapCompany", e);
			e.printStackTrace();
		}
//		finally {
//			if (session != null)
//				session.close();
//		}
		return null;
	}

	@Transactional
	@Override
	public List<ModuleVO> getSapModule(String masterId) {
		Session session = currentSession();
		// session.beginTransaction();
		try {
			System.out.println("getSapModule" + masterId);
			Query sqlQuery = session.createQuery(
					"select samtdRecid as samtdRecid, masterIdCode as masterIdCode ,description as description from SapAmMasterTypeDetail  where status=:status and masterId=:pmasterId");
			sqlQuery.setParameter("status", "A");
			sqlQuery.setParameter("pmasterId", masterId);
			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(ModuleVO.class));
			// session.getTransaction().commit();
			System.out.println("session.getTransaction().commit()" + masterId);
			return (List<ModuleVO>) sqlQuery.list();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("ERROR >> MasterDataDaoImpl >> getSapModule", e);

		}
//		finally {
//			if (session != null)
//				session.close();
//		}
		return null;
	}

	@Override
	@Transactional
	public List<PlantVO> getSapPlants(String companies) {
		Session session = currentSession();
		// session.beginTransaction();
		try {

			StringBuilder queryBuilder = new StringBuilder(
					" select  distinct a.SAP_OU_CODE as plantCode ,a.SAP_ENTITY_CODE  as sapEntityCode, a.DESCRIPTION as description from OC_OPERATING_UNIT a ");
			queryBuilder.append(
					" WHERE  a.status='A' and a.SAP_ENTITY_CODE is not null and a.SAP_OU_CODE is not null order by a.SAP_ENTITY_CODE, a.SAP_OU_CODE ,a.DESCRIPTION ");
			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())

					.addScalar("plantCode", StringType.INSTANCE).addScalar("sapEntityCode", StringType.INSTANCE)
					.addScalar("description", StringType.INSTANCE);

			// sqlQuery.setParameterList("status", new String[]{"C","P","N"});
			sqlQuery.setResultTransformer(new AliasToBeanResultTransformer(PlantVO.class));

			// session.getTransaction().commit();

			return (List<PlantVO>) sqlQuery.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> getSapPlants", e);
			e.printStackTrace();
		}
//		finally {
//			if (session != null)
//				session.close();
//		}
		return null;
	}

	@Override
	public List<PlantVO> getSapPlantSBU(Set<String> plants) throws Exception {
		Session session = currentSession();
		// session.beginTransaction();
		try {

			StringBuilder queryBuilder = new StringBuilder(
					" select distinct  b.SBU_CODE as sbuCode, a.SAP_OU_CODE as plantCode ,a.SAP_ENTITY_CODE  as sapEntityCode, a.DESCRIPTION as description from OC_OPERATING_UNIT a ");
			queryBuilder.append(
					"  join oc_SBU_Mapping b on  a.OU_CODE = b.OU_CODE WHERE a.status='A' and a.SAP_ENTITY_CODE is not null and a.SAP_OU_CODE in =:plants ");
			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())

					.addScalar("sbuCode", StringType.INSTANCE).addScalar("plantCode", StringType.INSTANCE)
					.addScalar("sapEntityCode", StringType.INSTANCE).addScalar("description", StringType.INSTANCE);

			sqlQuery.setParameterList("plants", plants);
			sqlQuery.setResultTransformer(new AliasToBeanResultTransformer(PlantVO.class));

			// session.getTransaction().commit();

			return (List<PlantVO>) sqlQuery.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> getSapPlants", e);
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public List<String> getSapPlantSbus(Set<String> plants) throws Exception {
		Session session = currentSession();

		try {

			StringBuilder queryBuilder = new StringBuilder(
					" select distinct  b.SBU_CODE as sbuCode from OC_OPERATING_UNIT a ");
			queryBuilder.append(
					"  join oc_SBU_Mapping b on  a.OU_CODE = b.OU_CODE WHERE a.status='A' and a.SAP_ENTITY_CODE is not null and a.SAP_OU_CODE in :plants ");
			Query sqlQuery = session.createSQLQuery(queryBuilder.toString());
			sqlQuery.setParameterList("plants", plants);
			return (List<String>) sqlQuery.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> getSapPlants", e);
			e.printStackTrace();
			throw e;
		}

	}

	@Transactional
	@Override
	public List<PlantVO> getPlantByCompany(String sapCompany, JQueryDataTableParamModel criteria) {
		// TODO Auto-generated method stub
		Session session = currentSession();
		// session.beginTransaction();
		List<ModuleVO> resultList = null;
		try {
			System.out.println("List" + criteria.toString());
			List<String> columnName = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));

			StringBuilder queryBuilder = new StringBuilder(
					" select  distinct a.SAP_OU_CODE as plantCode ,a.SAP_ENTITY_CODE  as sapEntityCode, a.DESCRIPTION as description from OC_OPERATING_UNIT a ");
			queryBuilder.append(
					" WHERE  a.status='A' and a.SAP_ENTITY_CODE in :psapEntityCode  and a.SAP_OU_CODE is not null  ");

			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  a.SAP_OU_CODE like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  a.DESCRIPTION  like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			queryBuilder.append(" order by a.SAP_ENTITY_CODE, a.SAP_OU_CODE ,a.DESCRIPTION ");
			System.out.println(queryBuilder.toString() + " ORDER BY a.SAP_OU_CODE ");
			// sapAmUserRequest.getSapCompanyCode() != null &&
			// sapAmUserRequest.getSapCompanyCode().equalsIgnoreCase("1000,2000,3000")
			List<String> companies = "ALL".equalsIgnoreCase(sapCompany)
					? Arrays.stream("1000,2000,3000".split(",")).map(a -> a.toString()).collect(Collectors.toList())
					: Arrays.asList(sapCompany);

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())

					.addScalar("plantCode", StringType.INSTANCE).addScalar("sapEntityCode", StringType.INSTANCE)
					.addScalar("description", StringType.INSTANCE);
			sqlQuery.setParameterList("psapEntityCode", companies);
			sqlQuery.setResultTransformer(new AliasToBeanResultTransformer(PlantVO.class));
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = sqlQuery.list().size();

			sqlQuery.setMaxResults(criteria.iDisplayLength);
			sqlQuery.setFirstResult(criteria.iDisplayStart);
			System.out.println("HHHHHHHHHHHHHHHHH");
			// session.getTransaction().commit();

			return (List<PlantVO>) sqlQuery.list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> getPlantByCompany", e);
			e.printStackTrace();
		}
//		finally {
//			if (session != null)
//				session.close();
//		}
		return null;
	}

	@Transactional
	@Override
	public List<SapAmTcodeVO> getTcodeByModule(String pmodule) {
		// TODO Auto-generated method stub
		Session session = currentSession();
		// session.beginTransaction();
		try {

			StringBuilder quBuilder = new StringBuilder(
					" SELECT    st.satId as satId,   st.tcode as tcode, st.tcodeDesc as tcodeDesc,st.module as module, stdroles as stdroles FROM SapAmTcode st");

			StringBuilder sWhere = new StringBuilder(" where  st.status='A' ");
			if (false) {
				sWhere.append(" and af.module=:pmodule");
			}

			quBuilder.append(sWhere);
			System.out.println(quBuilder.toString());
			// System.out.println(rowBuilder.toString());

			Query sqlQuery = session.createQuery(quBuilder.toString());
			if (false)
				sqlQuery.setParameter("module", pmodule);

			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmTcodeVO.class));
			// session.getTransaction().commit();
			return (List<SapAmTcodeVO>) sqlQuery.list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> getTcode", e);
			e.printStackTrace();
		}
//		finally {
//			if (session != null)
//				session.close();
//
//		}
		return null;
	}

	@Transactional
	@Override
	public List<SapAmTcodeVO> getTcodeByModule(String pmodule, JQueryDataTableParamModel criteria) {

		System.out.println("List" + criteria.toString());
		List<String> columnName = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
		Session session = currentSession();
		// session.beginTransaction();
		try {

			StringBuilder quBuilder = new StringBuilder(
					" SELECT    st.satId as satId,   st.tcode as tcode, stdroles as stdroles ,st.tcodeDesc as tcodeDesc,st.sensitive as sensitive, st.module as module FROM SapAmTcode st");
			// quBuilder.append(" join SapAmMasterTypeDetail d on st.module=d.masterIdCode
			// ");
			StringBuilder sWhere = new StringBuilder(" where  st.status='A' ");
			boolean flag = (pmodule != null && pmodule.equalsIgnoreCase("false")) ? false : true;

			if (flag) {
				sWhere.append(" and st.module=:pmodule");
			}

			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				// query.add(Expression.like("A%"));
				Iterator<String> coulmnIt = columnName.iterator();
				coulmnIt.next();
				sWhere.append(" and ( ");
				while (coulmnIt.hasNext()) {
					sWhere.append(coulmnIt.next() + " like UPPER('%" + criteria.sSearch + "%')  or ");
				}
				sWhere.delete(sWhere.length() - 3, sWhere.length());
				sWhere.append(" ) ");

			}
			quBuilder.append(sWhere);
			// rowBuilder.append(sWhere);
			System.out.println(quBuilder.toString());
			// System.out.println(rowBuilder.toString());

			Query sqlQuery = session.createQuery(quBuilder.toString());
			if (flag)
				sqlQuery.setParameter("pmodule", pmodule);

			// List<String> stdrolesList =
			// Arrays.stream(srole.split(",")).map(s->s).collect(Collectors.toList());

			// Stream.of(funIds).map(a -> Long.parseLong(a)).collect(Collectors.toList());
			// System.out.println(stdrolesList.toArray());
			// if(flag)
			// sqlQuery.setParameterList("pstdroles", stdrolesList);

			criteria.iTotalDisplayRecords = criteria.iTotalRecords = sqlQuery.list().size();
			sqlQuery.setMaxResults(criteria.iDisplayLength);
			sqlQuery.setFirstResult(criteria.iDisplayStart);
			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmTcodeVO.class));
			// session.getTransaction().commit();
			return (List<SapAmTcodeVO>) sqlQuery.list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> getTcode", e);
			e.printStackTrace();
		}
//		finally {
//			if (session != null)
//				session.close();
//
//		}
		return null;
	}

	@Transactional
	@Override
	public List<SapAmMasterTypeDetail> getPGroup() throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();
		// session.beginTransaction();
		try {

			Query sqlQuery = session
					.createQuery(" from SapAmMasterTypeDetail where masterId=:masterId  and status=:status ");
			sqlQuery.setParameter("masterId", "7");
			// sqlQuery.setParameter("masterIdCode", "M");
			// sqlQuery.setParameter("masterIdCode", ""+ role.substring(0,1) + "%");
			// System.out.println( role.substring(0,1));
			sqlQuery.setParameter("status", "A");
			// session.getTransaction().commit();
			return (List<SapAmMasterTypeDetail>) sqlQuery.list();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> getPGroup", e);
			e.printStackTrace();
			throw new Exception(e);
		}
//		finally {
//			if (session != null)
//				session.close();
//		}

	}

	@Override
	@Transactional
	public List<SapAmMasterTypeDetail> getPGroupJson(JQueryDataTableParamModel criteria) {

		Session session = currentSession();
		// session.beginTransaction();
		try {

			System.out.println("List" + criteria.toString());
			List<String> columnName = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));

			StringBuilder queryBuilder = new StringBuilder(" from SapAmMasterTypeDetail ");
//			StringBuilder countRecord = new StringBuilder(
//					"select count(c.samtdRecid) from SapAmMasterTypeDetail c ");
			StringBuilder sWhere = new StringBuilder(" where masterId=:masterId  and status=:status ");
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
//				 "description": "General Materials",
//			      "masterId": "7",
//			      "masterIdCode": "333",
				sWhere.append(" and ( ");
				sWhere.append("  masterIdCode like UPPER('%" + criteria.sSearch + "%')  or ");
				sWhere.append("  description  like ('%" + criteria.sSearch + "%')  or ");
				sWhere.delete(sWhere.length() - 3, sWhere.length());
				sWhere.append(" ) ");
			}
			queryBuilder.append(sWhere);
			System.out.println(queryBuilder.toString() + " ORDER BY masterIdCode");
//			System.out.println(countRecord.toString());
			Query query = session.createQuery(queryBuilder.toString() + " ORDER BY masterIdCode");
			query.setParameter("masterId", "7");
			query.setParameter("status", "A");
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();

			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			System.out.println("HHHHHHHHHHHHHHHHH");
			// session.getTransaction().commit();

			return (List<SapAmMasterTypeDetail>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> getPGroupJson", e);
			e.printStackTrace();
		}
//		finally {
//			if (session != null)
//				session.close();
//		}
		return null;
	}

//	@Transactional
//	@Override
	public List<SapAmRoleMaster> getRoles2(JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();
		// session.beginTransaction();
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
			StringBuilder sWhere = new StringBuilder(" where  ROLE.role_type = 'D' ");
			// sWhere.append(" and role.entity=:pentity ");
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
			// query.setParameter("pentity", entity);
			// query.setParameter("pentity", "2000");
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmRoleMaster.class));

			// session.getTransaction().commit();

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRoleMaster>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> getRoles", e);
			e.printStackTrace();
		}
//		finally {
//			if (session != null)
//				session.close();
//		}
		return resultList;

	}

	// @Transactional
	// @Override
	public List<SapAmRolesRequestVO> getRolesD(JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {

			System.out.println("List" + criteria.toString());
			StringBuilder queryBuilder = new StringBuilder(
					"select module AS module, role_id AS roleId, role_code AS roleCode,  entity AS entity, is_conflicted AS isConflicted, role_name AS roleName, ");

			queryBuilder.append("   role_type AS roleType, funId , funCode   ");

			queryBuilder.append(
					" FROM (SELECT m.module, m.role_id, m.role_code, m.entity,   m.is_conflicted , m.role_name ,   m.role_type ,   f.fun_id AS funId, f.fun_code AS funCode  ");

			queryBuilder.append("   FROM sap_am_role_master m JOIN sap_am_functions f     ON f.fun_id = m.fun_id  ");
			queryBuilder.append("   UNION ALL ");
			queryBuilder.append(
					"   SELECT m.module, m.role_id, m.role_code, m.entity, 'N' ,  m.role_name , m.role_type , null AS funId , '' AS funCode    ");
			queryBuilder.append("   FROM sap_am_role_tcode_mapping m     )  ");
			StringBuilder sWhere = new StringBuilder(" WHERE role_type = 'D'  ");
			// " where ROLE.role_type = 'D' and p.REQUEST_ID=:prequestId and
			// p.SUR_RECID=:psurRecid ");
			queryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  entity like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  funCode like UPPER('%" + criteria.sSearch + "%')  or ");
				// criteriaBuilder.append(" ROLE.\"MODULE\" like UPPER('%" + criteria.sSearch +
				// "%') or ");
				criteriaBuilder.append("  role_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  role_name like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString() + "  ORDER BY role_id")
					.addScalar("roleId", StandardBasicTypes.LONG).addScalar("module", StandardBasicTypes.STRING)
					.addScalar("entity", StandardBasicTypes.STRING).addScalar("funId", StandardBasicTypes.LONG)
					// .addScalar("requestId", StandardBasicTypes.BIG_DECIMAL)
					// .addScalar("surRecid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("funCode", StandardBasicTypes.STRING)
					// .addScalar("parentRoleId", StandardBasicTypes.LONG)
					.addScalar("roleCode", StandardBasicTypes.STRING).addScalar("roleName", StandardBasicTypes.STRING)
					.addScalar("roleType", StandardBasicTypes.STRING)
					.addScalar("isConflicted", StandardBasicTypes.STRING);

			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	@Override
	public List<SapAmRolesRequestVO> getRoles(JQueryDataTableParamModel criteria) throws Exception {
		Session session = currentSession();

		try {

			System.out.println("List" + criteria.toString());
			StringBuilder queryBuilder = new StringBuilder(
					"select distinct m.module AS module, m.role_id AS roleId, m.role_code AS roleCode,  m.entity AS entity, m.role_name AS roleName, ");

			queryBuilder.append("   m.role_type AS roleType, m.plant AS plant   ");
			//queryBuilder.append("  ,   SESETIVEROLE(m.role_CODE)  sensitive   ");

			queryBuilder.append("   FROM sap_am_role_tcode_mapping m      ");
			StringBuilder sWhere = new StringBuilder(" WHERE m.role_type = 'D'  and m.status = 'A' and plant!='9999' ");

			queryBuilder.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and ( ");
				criteriaBuilder.append("  m.entity like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  m.role_code like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  m.role_name like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.append("  m.plant like UPPER('%" + criteria.sSearch + "%')  or ");
				criteriaBuilder.delete(criteriaBuilder.length() - 3, criteriaBuilder.length());
				criteriaBuilder.append(" ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString());
			Query query = session.createSQLQuery(queryBuilder.toString() + "  ORDER BY m.role_id")
					.addScalar("roleId", StandardBasicTypes.LONG).addScalar("module", StandardBasicTypes.STRING)
					.addScalar("entity", StandardBasicTypes.STRING).addScalar("roleCode", StandardBasicTypes.STRING)
					.addScalar("plant", StandardBasicTypes.STRING).addScalar("roleName", StandardBasicTypes.STRING)
					//.addScalar("sensitive", StandardBasicTypes.STRING)
					.addScalar("roleType", StandardBasicTypes.STRING);

			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			query.setResultTransformer(new AliasToBeanResultTransformer(SapAmRolesRequestVO.class));

			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmRolesRequestVO>) query.list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	@Override
	public List<SapUserVO> getUsersDetails(HeapUserDetails principal) {
		Session session = currentSession();
		// session.beginTransaction();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select e.SAP_USERID as sapUserid, sys.ENTITY_SAP_CODE as entity, sys.COMPANY as company, e.EMP_CODE as hrisCode, e.EMPLOYEE_NAME as employeeName, e.SAP_EMP_CODE as sapEmpCode, ");

			queryBuilder.append(" HRIS_GET.DEPT_CODE(E.DEPT_CODE) as department, ");
			queryBuilder.append("	HRIS_GET.DESIG_CODE(E.DESIG_CODE) as designation, ");
			queryBuilder.append(" HRIS_GET.LOC_CODE(E.LOC_CODE) as location  ");
//			queryBuilder.append(
//					"  ,srm.ROLE_ID as \"roleMasters.roleId\" , srm.ROLE_CODE as \"roleMasters.roleCode\" , srm.FUN_ID as \"roleMasters.funId\" ");
//			queryBuilder.append("  LISTAGG( srm.ROLE_ID, ', ')  within group ( order by srm.ROLE_ID)  as roleId ,");
//			queryBuilder.append("  LISTAGG( srm.ROLE_CODE, ', ') within group ( order by srm.ROLE_ID) as roleCode ,");
//			queryBuilder.append("  LISTAGG( srm.ROLE_NAME, ', ') within group ( order by srm.ROLE_ID) as roleName ,");
//			queryBuilder.append(
//					"  LISTAGG (srm.role_id ,', ') within  group (  order by  srm.ROLE_ID) as parentRoleId  ,");
//			queryBuilder.append("  LISTAGG (fun.FUN_ID , ', ') within group (  order by srm.ROLE_ID) as funId ,");
//			queryBuilder.append("  LISTAGG (fun.FUN_CODE , ', ') within group (  order by srm.ROLE_ID) as funCode ");
			queryBuilder.append(" from hrm_employee E ");
			boolean flag = principal.getAuthorities() != null
					&& principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
			queryBuilder.append("  join   hrm_system_parameters sys ");
			queryBuilder.append(" on sys.COMPANY=e.COMPANY ");
			if (!flag) {
				queryBuilder.append(" and sys.ENTITY_SAP_CODE=:pentity ");
			}
			// queryBuilder.append(" left join SAP_AM_ROLE_EMP_MAPPING srem on
			// srem.EMP_CODE=e.EMP_CODE ");
			// queryBuilder.append(" left join SAP_AM_ROLE_MASTER srm on
			// srm.ROLE_ID=srem.ROLE_ID ");
			// queryBuilder.append(" and srm.ENTITY=:pentity ");
			// queryBuilder.append(" left JOIN sap_am_functions fun ON fun.fun_id =
			// srm.fun_id ");
			StringBuilder sWhere = new StringBuilder("  where e.status in ('C','P','N') and e.SAP_USERID is not null ");

			queryBuilder.append(sWhere);
			// countRecord.append(sWhere);
			StringBuilder criteriaBuilder = new StringBuilder();

//			queryBuilder.append(
//					"  group by e.SAP_USERID,sys.ENTITY_SAP_CODE ,sys.COMPANY,e.EMP_CODE, e.SAP_EMP_CODE , e.EMPLOYEE_NAME, E.DEPT_CODE, E.DESIG_CODE, E.LOC_CODE, E.CD_CODE ");
			queryBuilder.append(" ORDER BY  E.DEPT_CODE, E.CD_CODE ");
			// System.out.println(queryBuilder.toString());

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("sapUserid", StandardBasicTypes.STRING).addScalar("entity", StandardBasicTypes.STRING)
					.addScalar("company", StandardBasicTypes.STRING).addScalar("hrisCode", StandardBasicTypes.STRING)
					.addScalar("employeeName", StandardBasicTypes.STRING)
					.addScalar("sapEmpCode", StandardBasicTypes.STRING)
					.addScalar("department", StandardBasicTypes.STRING)
					.addScalar("designation", StandardBasicTypes.STRING)
					.addScalar("location", StandardBasicTypes.STRING);
//					.addScalar("roleId", StandardBasicTypes.STRING)
//					.addScalar("roleCode", StandardBasicTypes.STRING)
//					.addScalar("parentRoleId", StandardBasicTypes.STRING)
//					.addScalar("roleName", StandardBasicTypes.STRING).addScalar("funId", StandardBasicTypes.STRING)
//					.addScalar("funCode", StandardBasicTypes.STRING)
			// .addScalar("roleMasters.roleId", StandardBasicTypes.LONG)
			// .addScalar("roleMasters.roleCode", StandardBasicTypes.STRING)
			;
			if (!flag) {
				sqlQuery.setParameter("pentity", principal.getSapEntityCode());
			}

			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SapUserVO.class));
			// session.getTransaction().commit();
			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapUserVO>) sqlQuery.list();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> getUsersDetails", e);
			e.printStackTrace();
		}
//		finally {
//			if (session != null)
//				session.close();
//		}
		return null;

	}

	@Transactional
	@Override
	public List<SapUserVO> searchUsers(HeapUserDetails principal, String sapUserId) {
		Session session = currentSession();
		// session.beginTransaction();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select e.SAP_USERID as sapUserid,sys.ENTITY_SAP_CODE as entity,sys.COMPANY as company, e.EMP_CODE as hrisCode, e.EMPLOYEE_NAME as employeeName, e.SAP_EMP_CODE as sapEmpCode, ");

			queryBuilder.append(" HRIS_GET.DEPT_CODE(E.DEPT_CODE) as department, ");
			queryBuilder.append("	HRIS_GET.DESIG_CODE(E.DESIG_CODE) as designation, ");
			queryBuilder.append(" HRIS_GET.LOC_CODE(E.LOC_CODE) as location  ");
			queryBuilder.append(" from hrm_employee E ");
			boolean flag = principal.getAuthorities() != null
					&& principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
			queryBuilder.append("  join   hrm_system_parameters sys ");
			queryBuilder.append(" on sys.COMPANY=e.COMPANY  ");
			if (!flag) {
				queryBuilder.append(" and sys.ENTITY_SAP_CODE=:pentity ");
			}
			StringBuilder sWhere = new StringBuilder(
					"  where e.status in ('C','P','N') and e.SAP_USERID like  UPPER('%" + sapUserId + "%')  ");

			queryBuilder.append(sWhere);

			queryBuilder.append(
					"  group by e.SAP_USERID,sys.ENTITY_SAP_CODE ,sys.COMPANY, e.EMP_CODE, e.SAP_EMP_CODE , e.EMPLOYEE_NAME, E.DEPT_CODE, E.DESIG_CODE, E.LOC_CODE, E.CD_CODE ");
			queryBuilder.append(" ORDER BY  E.DEPT_CODE, E.CD_CODE ");
			// System.out.println(queryBuilder.toString());

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("sapUserid", StandardBasicTypes.STRING).addScalar("hrisCode", StandardBasicTypes.STRING)
					.addScalar("entity", StandardBasicTypes.STRING).addScalar("company", StandardBasicTypes.STRING)
					.addScalar("employeeName", StandardBasicTypes.STRING)
					.addScalar("sapEmpCode", StandardBasicTypes.STRING)
					.addScalar("department", StandardBasicTypes.STRING)
					.addScalar("designation", StandardBasicTypes.STRING)
					.addScalar("location", StandardBasicTypes.STRING);
			if (!flag) {
				sqlQuery.setParameter("pentity", principal.getSapEntityCode());
			}

			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SapUserVO.class));
			// session.getTransaction().commit();
			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapUserVO>) sqlQuery.list();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> searchUsers", e);
			e.printStackTrace();
		}
//		finally {
//			if (session != null)
//				session.close();
//		}
		return null;
	}

	@Override
	public List<SapAmOtherUserVO> searchOthUsers(HeapUserDetails principal, String sapUserId) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();
		// session.beginTransaction();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select e.SAP_USERID as sapUserid, e.SAP_COMPANY_CODE as sapCompanyCode, e.USER_DESC as userDesc ");

			queryBuilder.append(" from SAP_AM_OTHER_USER E ");
            if(sapUserId !=null && !sapUserId .equals("null") && sapUserId.trim().length()>0) {
            	queryBuilder.append(" where e.SAP_USERID like  UPPER('%" + sapUserId + "%') ");
            }

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("sapUserid", StandardBasicTypes.STRING).addScalar("sapCompanyCode", StandardBasicTypes.STRING)
					.addScalar("userDesc", StandardBasicTypes.STRING);
			
			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmOtherUserVO.class));
			// session.getTransaction().commit();
			System.out.println("HHHHHHHHHHHHHHHHH");
			return (List<SapAmOtherUserVO>) sqlQuery.list();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR >> MasterDataDaoImpl >> searchOthUsers", e);
			e.printStackTrace();
		}
//		finally {
//			if (session != null)
//				session.close();
//		}
		return null;
	}
}
