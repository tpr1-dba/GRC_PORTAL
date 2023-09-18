package com.samodule.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequestApproval;
import com.samodule.model.SapAmUserRequestTcode;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapAmRolesRequestVO;
import com.samodule.vo.UserRequestDTO;

@Repository("requestTcodeDao")
public class RequestTcodeDaoImpl extends JpaDao<SapAmUserRequestTcode> implements RequestTcodeDao {
	static final Logger log = Logger.getLogger(RequestTcodeDaoImpl.class.getName());

	@Override
	public long saveTcodes(List<SapAmUserRequestTcode> amUserRequestTcodes, HeapUserDetails principal)
			throws Exception {
		int batchSize = 10;
		int j = 0;
		Session session = currentSession();
		// Transaction tx = session.beginTransaction();
		try {

			Iterator<SapAmUserRequestTcode> it = amUserRequestTcodes.iterator();
			while (it.hasNext()) {
				SapAmUserRequestTcode sapAmUserRequestTcode = it.next();
				sapAmUserRequestTcode.setStatus("I");
				sapAmUserRequestTcode.setCreatedBy(principal.getEmpCode());
				sapAmUserRequestTcode.setCreatedOn(new Date());
				session.saveOrUpdate(sapAmUserRequestTcode);

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
//		finally {
//			if (session != null)
//				session.close();
//		}
		return j;
	}

	@Transactional
	@Override
	public List<UserRequestDTO> getRequestedByUserID(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception {
		Session session = currentSession();
		try {
			System.out.println("List" + criteria.toString());
			List<String> columnName = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));

			StringBuilder queryBuilder = new StringBuilder(
					" select at.sat_id as tcodeId,at.tcode as tcode, at.tcode_desc as tcodeDesc ,  ");
			queryBuilder.append(
					" at.module , rt.REQUEST_ID as requestId,rt.SURT_RECID as surtRecid,rt.SUR_RECID as surRecid,rt.status ,rt.REMARKS as reason ,ur.SAP_COMPANY_CODE as sapCompanyCode ");
			queryBuilder.append(
					" from SAP_AM_TCODES at join  SAP_AM_USER_REQUEST_TCODES rt on at.SAT_ID=rt.tcode_id and  ");
			queryBuilder.append(
					" at.tcode=rt.tcode join SAP_AM_USER_REQUESTS ur on  rt.REQUEST_ID=ur.REQUEST_ID and rt.SUR_RECID=ur.sur_recid ");
			StringBuilder sWhere = new StringBuilder(" where ur.emp_code=:pempCode and  rt.status='I' ");
			queryBuilder.append(sWhere.toString());
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and rt.tcode " + " like UPPER('%" + criteria.sSearch + "%')   ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString() + " ORDER BY SUR_RECID " + criteria.sSortDir_0);
			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  rt.SURT_RECID  desc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("tcodeId", LongType.INSTANCE).addScalar("tcode", StringType.INSTANCE)
					.addScalar("tcodeDesc", StringType.INSTANCE).addScalar("module", StringType.INSTANCE)
					.addScalar("requestId", BigDecimalType.INSTANCE).addScalar("status", StringType.INSTANCE)
					.addScalar("reason", StringType.INSTANCE).addScalar("surRecid", LongType.INSTANCE)
					.addScalar("sapCompanyCode", StringType.INSTANCE).addScalar("surtRecid", LongType.INSTANCE);

			query.setParameter("pempCode", principal.getEmpCode());
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			System.out.println("HHHHHHHHHHHHHHHHH");
			query.setResultTransformer(new AliasToBeanNestedResultTransformer(UserRequestDTO.class));
			return (List<UserRequestDTO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public long submitRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_TCODES ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			// queryBuilder.append(" , ur.status = :pstatus");
			queryBuilder.append(" WHERE ur.SUR_RECID = :psurRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRequestVO.getStatus());
			nativeQuery.setParameter("psurRecid", sapAmRequestVO.getSurRecid());
			nativeQuery.setParameter("prequestId", sapAmRequestVO.getRequestId());
			return nativeQuery.executeUpdate();
			// tx.commit();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

	}

	@Override
	public long submitRequests(List<UserRequestDTO> temp, SapAmRequestStatusVO sapAmRequestVO,
			HeapUserDetails principal) throws Exception {
		int batchSize = 10;
		int j = 0;
		Session session = currentSession();

		try {
			Iterator<UserRequestDTO> it = temp.iterator();
			while (it.hasNext()) {
				UserRequestDTO userRequestDTO = it.next();
				StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_TCODES ur ");
				queryBuilder.append(" SET ur.status = :pstatus ");
				queryBuilder.append(" , ur.SAP_MODULE = :pmodule ");
				queryBuilder.append(" WHERE ur.SUR_RECID = :psurRecid ");
				queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");
				queryBuilder.append(" and ur.SURT_RECID = :psurtRecid ");

				NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
				nativeQuery.setParameter("pstatus", sapAmRequestVO.getStatus());
				nativeQuery.setParameter("pmodule", userRequestDTO.getModule());
				nativeQuery.setParameter("psurtRecid", userRequestDTO.getSurtRecid());
				nativeQuery.setParameter("psurRecid", userRequestDTO.getSurRecid());
				nativeQuery.setParameter("prequestId", userRequestDTO.getRequestId());
				nativeQuery.executeUpdate();
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
	public long updateStatus(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_TCODES ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			// queryBuilder.append(" , ur.status = :pstatus");
			queryBuilder.append(" WHERE ur.SURT_RECID = :psurtRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRequestVO.getStatus());
			nativeQuery.setParameter("psurtRecid", sapAmRequestVO.getSurtRecid());
			nativeQuery.setParameter("prequestId", sapAmRequestVO.getRequestId());
			return nativeQuery.executeUpdate();
			// tx.commit();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

	}

	@Override
	public long deleteRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" delete from SAP_AM_USER_REQUEST_TCODES ur ");
			// queryBuilder.append(" SET ur.status = :pstatus ");
			// queryBuilder.append(" , ur.status = :pstatus");
			queryBuilder.append(" WHERE ur.SURT_RECID = :psurtRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			// nativeQuery.setParameter("pstatus", sapAmRequestVO.getStatus());
			nativeQuery.setParameter("psurtRecid", sapAmRequestVO.getSurtRecid());
			nativeQuery.setParameter("prequestId", sapAmRequestVO.getRequestId());
			return nativeQuery.executeUpdate();
			// tx.commit();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	@Override
	public long approveRequestByCTM(Integer surRecid, Integer requestId, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_TCODES ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			// queryBuilder.append(" SET ur.WF_LEVEL = :pwflevel ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			// queryBuilder.append(" , ur.status = :pstatus"); STATUS WF_LEVEL APPROVED_ON
			// APPROVED_BY
			queryBuilder.append(" WHERE ur.status = 'S' and ur.SUR_RECID = :psurRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");
			/*
			 * session.createNativeQuery("UPDATE SAP_AM_USER_REQUESTS pi" +
			 * " JOIN ereturn er ON pi.ereturn = er.id " +
			 * "SET pi.receptacle = :receptacle " + "WHERE pi.returnAction = :returnAction "
			 * + "AND er.destination = :destination " + "AND er.status = 'RECEIVED'")
			 */

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", "O");
			// nativeQuery.setParameter("pwflevel", 2);
			nativeQuery.setParameter("papprovedOn", new Date());
			nativeQuery.setParameter("papprovedBy", principal.getEmpCode());
			nativeQuery.setParameter("psurRecid", surRecid);
			nativeQuery.setParameter("prequestId", requestId);
			return nativeQuery.executeUpdate();
			// tx.commit();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

	}

	@Override
	public long rejectRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();

		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_TCODES ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			// queryBuilder.append(" , ur.WF_LEVEL = :pwflevel ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			// queryBuilder.append(" , ur.status = :pstatus"); STATUS WF_LEVEL APPROVED_ON
			// APPROVED_BY
			queryBuilder.append(" WHERE   ur.SUR_RECID = :psurRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRequestVO.getStatus());

			nativeQuery.setParameter("papprovedOn", new Date());
			nativeQuery.setParameter("papprovedBy", principal.getEmpCode());
			nativeQuery.setParameter("psurRecid", sapAmRequestVO.getSurRecid());
			nativeQuery.setParameter("prequestId", sapAmRequestVO.getRequestId());
			return nativeQuery.executeUpdate();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	@Transactional
	@Override
	public List<UserRequestDTO> getRequestedTcode(Long surRecid, Long requestId, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			System.out.println("List" + criteria.toString());
			List<String> columnName = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));

			StringBuilder queryBuilder = new StringBuilder(
					" select at.sat_id as tcodeId,at.tcode as tcode, at.tcode_desc as tcodeDesc ,at.sensitive as sensitive , ");
			queryBuilder.append(
					" at.module , rt.REQUEST_ID as requestId,rt.SURT_RECID as surtRecid,rt.SUR_RECID as surRecid,rt.status ,ur.SAP_COMPANY_CODE as sapCompanyCode ");
			queryBuilder.append(
					" from SAP_AM_TCODES at join  SAP_AM_USER_REQUEST_TCODES rt on at.SAT_ID=rt.tcode_id and  ");
			queryBuilder.append(
					" at.tcode=rt.tcode join SAP_AM_USER_REQUESTS ur on  rt.REQUEST_ID=ur.REQUEST_ID and rt.SUR_RECID=ur.sur_recid ");
			// StringBuilder sWhere = new StringBuilder(" where ur.emp_code=:pempCode and
			// ");
			StringBuilder sWhere = new StringBuilder(" where ");
			sWhere.append(" rt.SUR_RECID=:psurRecid and ");
			sWhere.append(" rt.REQUEST_ID=:prequestId and ");
			sWhere.append(" rt.status not in ('I','R')  ");
			queryBuilder.append(sWhere.toString());
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and rt.tcode " + " like UPPER('%" + criteria.sSearch + "%')   ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString() + " ORDER BY SUR_RECID " + criteria.sSortDir_0);
			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  rt.SURT_RECID  desc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("tcodeId", LongType.INSTANCE).addScalar("tcode", StringType.INSTANCE)
					.addScalar("tcodeDesc", StringType.INSTANCE).addScalar("module", StringType.INSTANCE)
					.addScalar("sensitive", StringType.INSTANCE)
					.addScalar("requestId", BigDecimalType.INSTANCE).addScalar("status", StringType.INSTANCE)
					.addScalar("surRecid", LongType.INSTANCE).addScalar("sapCompanyCode", StringType.INSTANCE)
					.addScalar("surtRecid", LongType.INSTANCE);

			// query.setParameter("pempCode", principal.getEmpCode());
			query.setParameter("psurRecid", surRecid);
			query.setParameter("prequestId", requestId);
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			System.out.println("HHHHHHHHHHHHHHHHH");
			query.setResultTransformer(new AliasToBeanNestedResultTransformer(UserRequestDTO.class));
			return (List<UserRequestDTO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	@Override
	public List<UserRequestDTO> getRequestedTcodeByRequestID(Long surRecid, Long requestId, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			System.out.println("List" + criteria.toString());
			List<String> columnName = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));

			StringBuilder queryBuilder = new StringBuilder(
					" select distinct at.sat_id as tcodeId,at.tcode as tcode, at.tcode_desc as tcodeDesc ,  at.sensitive as sensitive, ");
			queryBuilder.append(
					" at.module , rt.REQUEST_ID as requestId,rt.SURT_RECID as surtRecid,rt.SUR_RECID as surRecid,rt.status ,ur.SAP_COMPANY_CODE as sapCompanyCode ");
			queryBuilder.append(
					" from SAP_AM_TCODES at join  SAP_AM_USER_REQUEST_TCODES rt on at.SAT_ID=rt.tcode_id and  ");
			queryBuilder.append(
					" at.tcode=rt.tcode join SAP_AM_USER_REQUESTS ur on  rt.REQUEST_ID=ur.REQUEST_ID and rt.SUR_RECID=ur.sur_recid ");
			queryBuilder.append(
					"  join  SAP_AM_USER_REQUEST_APPROVALS ra on  rt.REQUEST_ID=ra.REQUEST_ID  and rt.SUR_RECID=ra.PARENT_RECID and rt.SAP_MODULE=ra.SAP_MODULE ");
			

			StringBuilder sWhere = new StringBuilder(" where ");
			sWhere.append(" ra.PARENT_RECID=:psurRecid and ");
			sWhere.append(" ra.REQUEST_ID=:prequestId and ");
			sWhere.append(" ra.emp_code=:pempCode and ");
			sWhere.append(" rt.status not in ('I','R')  ");

			
			queryBuilder.append(sWhere.toString());
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and rt.tcode " + " like UPPER('%" + criteria.sSearch + "%')   ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString() + " ORDER BY rt.SURT_RECID " + criteria.sSortDir_0);
			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  rt.SURT_RECID  desc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("tcodeId", LongType.INSTANCE).addScalar("tcode", StringType.INSTANCE)
					.addScalar("tcodeDesc", StringType.INSTANCE)					
					.addScalar("module", StringType.INSTANCE).addScalar("sensitive", StandardBasicTypes.STRING)	
					.addScalar("requestId", BigDecimalType.INSTANCE).addScalar("status", StringType.INSTANCE)
					.addScalar("surRecid", LongType.INSTANCE).addScalar("sapCompanyCode", StringType.INSTANCE)
					.addScalar("surtRecid", LongType.INSTANCE);

			// query.setParameter("pempCode", principal.getEmpCode());
			query.setParameter("psurRecid", surRecid);
			query.setParameter("prequestId", requestId);
			query.setParameter("pempCode", principal.getEmpCode());

			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			System.out.println("HHHHHHHHHHHHHHHHH");
			query.setResultTransformer(new AliasToBeanNestedResultTransformer(UserRequestDTO.class));
			return (List<UserRequestDTO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;

	}
	
	@Transactional
	@Override
	public List<UserRequestDTO> getRequestedTcode(Long surRecid, Long requestId) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select at.sat_id as tcodeId,at.tcode as tcode, at.tcode_desc as tcodeDesc ,  ");
			queryBuilder.append(
					" at.module , rt.REQUEST_ID as requestId,rt.SURT_RECID as surtRecid,rt.SUR_RECID as surRecid,rt.status  ");
			queryBuilder.append(
					" from SAP_AM_TCODES at join  SAP_AM_USER_REQUEST_TCODES rt on at.SAT_ID=rt.tcode_id and  ");
			queryBuilder.append(" at.tcode=rt.tcode ");
			StringBuilder sWhere = new StringBuilder(" where rt.SUR_RECID=:psurRecid and ");
			sWhere.append(" rt.REQUEST_ID=:prequestId and ");
			sWhere.append(" rt.status='S'  ");
			queryBuilder.append(sWhere.toString());
			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  rt.SURT_RECID  desc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("tcodeId", LongType.INSTANCE).addScalar("tcode", StringType.INSTANCE)
					.addScalar("tcodeDesc", StringType.INSTANCE).addScalar("module", StringType.INSTANCE)
					.addScalar("requestId", BigDecimalType.INSTANCE).addScalar("status", StringType.INSTANCE)
					.addScalar("surRecid", LongType.INSTANCE)
					// .addScalar("sapCompanyCode", StringType.INSTANCE)
					.addScalar("surtRecid", LongType.INSTANCE);

			query.setParameter("psurRecid", surRecid);
			query.setParameter("prequestId", requestId);

			query.setResultTransformer(new AliasToBeanNestedResultTransformer(UserRequestDTO.class));
			return (List<UserRequestDTO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<UserRequestDTO> getRequestedTcodes(Long surRecid, Long requestId) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select at.sat_id as tcodeId,at.tcode as tcode, at.tcode_desc as tcodeDesc ,  ");
			queryBuilder.append(
					" at.module , rt.REQUEST_ID as requestId,rt.SURT_RECID as surtRecid,rt.SUR_RECID as surRecid,rt.status ,ur.SAP_COMPANY_CODE as sapCompanyCode ");
			queryBuilder.append(
					" from SAP_AM_TCODES at join  SAP_AM_USER_REQUEST_TCODES rt on at.SAT_ID=rt.tcode_id and  ");
			queryBuilder.append(
					" at.tcode=rt.tcode  join SAP_AM_USER_REQUESTS ur on  rt.REQUEST_ID=ur.REQUEST_ID and rt.SUR_RECID=ur.sur_recid ");
			StringBuilder sWhere = new StringBuilder(" where rt.SUR_RECID=:psurRecid and ");
			sWhere.append(" rt.REQUEST_ID=:prequestId and ");
			sWhere.append(" rt.status='I'  ");
			queryBuilder.append(sWhere.toString());
			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  rt.SURT_RECID  desc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("tcodeId", LongType.INSTANCE).addScalar("tcode", StringType.INSTANCE)
					.addScalar("tcodeDesc", StringType.INSTANCE).addScalar("module", StringType.INSTANCE)
					.addScalar("requestId", BigDecimalType.INSTANCE).addScalar("status", StringType.INSTANCE)
					.addScalar("surRecid", LongType.INSTANCE).addScalar("sapCompanyCode", StringType.INSTANCE)
					.addScalar("surtRecid", LongType.INSTANCE);

			query.setParameter("psurRecid", surRecid);
			query.setParameter("prequestId", requestId);

			query.setResultTransformer(new AliasToBeanNestedResultTransformer(UserRequestDTO.class));
			return (List<UserRequestDTO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public List<UserRequestDTO> getRequestedTcodesWithPlant(Long surRecid, Long requestId) throws Exception {
		List<String> compenies=this.getCompanies(surRecid, requestId);
		List<Long> tempSurRecids= this.getTocde9999Plant(requestId);
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select at.sat_id as tcodeId,at.tcode as tcode, at.tcode_desc as tcodeDesc ,  ");
			
		
			queryBuilder.append(" tm.role_code      AS roleCode, ");
			queryBuilder.append("  tm.role_id        AS roleId, ");		
			queryBuilder.append(
					" at.module , rt.REQUEST_ID as requestId,rt.SURT_RECID as surtRecid,rt.SUR_RECID as surRecid,rt.status ,rp.plant ,ur.SAP_COMPANY_CODE as sapCompanyCode ");
		
			queryBuilder.append(
					" ,  rem.ROLE_CODE as empRoleCode,  rem.ROLE_ID as empRoleId ");
			queryBuilder.append(
					" from SAP_AM_TCODES at join  SAP_AM_USER_REQUEST_TCODES rt on at.SAT_ID=rt.tcode_id and  ");

			queryBuilder.append(
					" at.tcode=rt.tcode join   SAP_AM_USER_REQUESTS ur  on  rt.REQUEST_ID=ur.REQUEST_ID  and rt.SUR_RECID=ur.sur_recid ");
			queryBuilder.append(
					" join   SAP_AM_USER_REQUEST_PLANTS rp   on  rp.REQUEST_ID=ur.REQUEST_ID and rp.SUR_RECID=ur.sur_recid  ");
			queryBuilder
			 .append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=at.sat_id and    tm.TCODE=at.tcode ");
			queryBuilder
			.append("    and   tm.entity in (:psapCompanyCode) ");
			queryBuilder
			.append("   and   (   ( rp.plant=tm.plant   or rp.PURCHASE_GROUP=tm.PURCHASE_GROUP   )        ");
			
			queryBuilder
			.append("    or (    tm.plant='9999'  and  rt.SURT_RECID in (  :pSurtRecids    )  ) )          ");
			queryBuilder
			.append("    and tm.status ='A'      ");
			
			
			
			//.append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=at.sat_id and    tm.TCODE=at.tcode   and tm.MODULE=at.MODULE ");
			//.append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on    tm.TCODE=at.tcode   and tm.MODULE=at.MODULE ");
//			queryBuilder
//			.append("    and   (rp.plant=tm.plant or rp.PURCHASE_GROUP=tm.PURCHASE_GROUP )  and tm.status ='A' ");
			queryBuilder
			.append("    left join SAP_AM_ROLE_EMP_MAPPING rem on rem.emp_code=ur.emp_code and rem.role_id= tm.role_id  and rem.status='A'  ");
			StringBuilder sWhere = new StringBuilder(" where rt.SUR_RECID=:psurRecid and ");
			sWhere.append(" rt.REQUEST_ID=:prequestId and ");
			sWhere.append(" rt.status='I'  ");
			sWhere.append(" and rp.status='I'  ");
			queryBuilder.append(sWhere.toString());
			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  rt.SURT_RECID  desc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("tcodeId", LongType.INSTANCE).addScalar("tcode", StringType.INSTANCE)
			.addScalar("roleCode", StandardBasicTypes.STRING).addScalar("roleId", StandardBasicTypes.LONG)
			.addScalar("empRoleCode", StandardBasicTypes.STRING).addScalar("empRoleId", StandardBasicTypes.LONG)
					.addScalar("plant", StringType.INSTANCE).addScalar("tcodeDesc", StringType.INSTANCE)
					.addScalar("module", StringType.INSTANCE).addScalar("requestId", BigDecimalType.INSTANCE)
					.addScalar("status", StringType.INSTANCE).addScalar("surRecid", LongType.INSTANCE)
					.addScalar("sapCompanyCode", StringType.INSTANCE).addScalar("surtRecid", LongType.INSTANCE);
			//sapAmRequestVO.getSurRecid(), sapAmRequestVO.getRequestId().longValue()
			//List<String> compenies= "ALL".equalsIgnoreCase(sapAmRequestVO.get())?Arrays.asList("1000","2000","3000"):Arrays.asList( sapAmRolesRequestVO.getEntity());
			query.setParameterList("pSurtRecids", tempSurRecids);
			query.setParameterList("psapCompanyCode", compenies);
			query.setParameter("psurRecid", surRecid);
			query.setParameter("prequestId", requestId);

			query.setResultTransformer(new AliasToBeanNestedResultTransformer(UserRequestDTO.class));
			return (List<UserRequestDTO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
			throw e;
		}

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
	 public List<Long> getTocde9999Plant(
			 Long requestId) throws Exception {
			Session session = currentSession();

			try {
				
				
				StringBuilder queryBuilder = new StringBuilder("  SELECT    distinct  rt.SURT_RECID   ");
				// queryBuilder.append(" rt.tcode AS tcode,  ");
				

				queryBuilder.append(
						"  from    SAP_AM_USER_REQUEST_TCODES  rt  join SAP_AM_USER_REQUEST_PLANTS rp on   rt.REQUEST_ID = rp.REQUEST_ID  ");
				queryBuilder
						.append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=rt.TCODE_ID and    tm.TCODE=rt.TCODE   ");
				queryBuilder.append(
						"    and  ( rp.plant=tm.plant or rp.PURCHASE_GROUP=tm.PURCHASE_GROUP ) and tm.status ='A'   ");
				queryBuilder.append(
						" where  rt.REQUEST_ID in (:requestId) and rt.STATUS NOT IN ('R')and  rp.STATUS NOT IN ('R')  ");
				StringBuilder sWhere = new StringBuilder(
						"  and tm.ROLE_CODE is null  ");
				queryBuilder.append(sWhere);
				Query query = session.createSQLQuery(queryBuilder.toString());
				query.setParameter("requestId", requestId);		
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

	//@Override
	public List<UserRequestDTO> getRequestedTcodesWithPlantDD(Long surRecid, Long requestId) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select at.sat_id as tcodeId,at.tcode as tcode, at.tcode_desc as tcodeDesc ,  ");
			
			queryBuilder.append(
					" CASE   WHEN NVL (tm.role_code, '%%%') != '%%%'   THEN tm.role_code   ELSE (SELECT DISTINCT tim.role_code ");
			queryBuilder.append(
					" FROM sap_am_role_tcode_mapping tim   WHERE tim.tcode_id = AT.sat_id   AND tim.tcode = AT.tcode ");
			
			queryBuilder.append(
					" AND (   tim.plant = '9999'  OR rp.purchase_group = tim.purchase_group ) ");
			
			queryBuilder.append(
					" AND ur.sap_company_code = tim.entity  AND tim.role_code IS NOT NULL ");
			
			queryBuilder.append(
					" AND tim.status = 'A'   AND ROWNUM <= 1) END AS roleCode, ");
			
			queryBuilder.append(
					" CASE  WHEN NVL (tm.role_id, '0') != '0'  THEN tm.role_id ELSE (SELECT DISTINCT tim.role_id ");
			
			queryBuilder.append(
					" FROM sap_am_role_tcode_mapping tim WHERE tim.tcode_id = AT.sat_id  AND tim.tcode = AT.tcode ");
			
			queryBuilder.append(
					" AND (   tim.plant = '9999' OR rp.purchase_group = tim.purchase_group ) ");
			queryBuilder.append(
					" AND ur.sap_company_code = tim.entity AND tim.role_code IS NOT NULL ");
			queryBuilder.append(
					" AND tim.status = 'A'  AND ROWNUM <= 1)   END AS roleId, ");
			
			queryBuilder.append(
					" at.module , rt.REQUEST_ID as requestId,rt.SURT_RECID as surtRecid,rt.SUR_RECID as surRecid,rt.status ,rp.plant ,ur.SAP_COMPANY_CODE as sapCompanyCode ");
		
			queryBuilder.append(
					" ,  rem.ROLE_CODE as empRoleCode,  rem.ROLE_ID as empRoleId ");
			queryBuilder.append(
					" from SAP_AM_TCODES at join  SAP_AM_USER_REQUEST_TCODES rt on at.SAT_ID=rt.tcode_id and  ");

			queryBuilder.append(
					" at.tcode=rt.tcode join   SAP_AM_USER_REQUESTS ur  on  rt.REQUEST_ID=ur.REQUEST_ID  and rt.SUR_RECID=ur.sur_recid ");
			queryBuilder.append(
					" join   SAP_AM_USER_REQUEST_PLANTS rp   on  rp.REQUEST_ID=ur.REQUEST_ID and rp.SUR_RECID=ur.sur_recid  ");
			queryBuilder
			 .append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=at.sat_id and    tm.TCODE=at.tcode ");
			 //.append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=at.sat_id and    tm.TCODE=at.tcode   and tm.MODULE=at.MODULE ");
			//.append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on    tm.TCODE=at.tcode   and tm.MODULE=at.MODULE ");
			queryBuilder
			.append("    and   (rp.plant=tm.plant or rp.PURCHASE_GROUP=tm.PURCHASE_GROUP )  and tm.status ='A' ");
			queryBuilder
			.append("    left join SAP_AM_ROLE_EMP_MAPPING rem on rem.emp_code=ur.emp_code and rem.role_id= tm.role_id  and rem.status='A'  ");
			StringBuilder sWhere = new StringBuilder(" where rt.SUR_RECID=:psurRecid and ");
			sWhere.append(" rt.REQUEST_ID=:prequestId and ");
			sWhere.append(" rt.status='I'  ");
			sWhere.append(" and rp.status='I'  ");
			queryBuilder.append(sWhere.toString());
			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  rt.SURT_RECID  desc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("tcodeId", LongType.INSTANCE).addScalar("tcode", StringType.INSTANCE)
			.addScalar("roleCode", StandardBasicTypes.STRING).addScalar("roleId", StandardBasicTypes.LONG)
			.addScalar("empRoleCode", StandardBasicTypes.STRING).addScalar("empRoleId", StandardBasicTypes.LONG)
					.addScalar("plant", StringType.INSTANCE).addScalar("tcodeDesc", StringType.INSTANCE)
					.addScalar("module", StringType.INSTANCE).addScalar("requestId", BigDecimalType.INSTANCE)
					.addScalar("status", StringType.INSTANCE).addScalar("surRecid", LongType.INSTANCE)
					.addScalar("sapCompanyCode", StringType.INSTANCE).addScalar("surtRecid", LongType.INSTANCE);

			query.setParameter("psurRecid", surRecid);
			query.setParameter("prequestId", requestId);

			query.setResultTransformer(new AliasToBeanNestedResultTransformer(UserRequestDTO.class));
			return (List<UserRequestDTO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
			throw e;
		}

	}

	
	//@Override
	public List<UserRequestDTO> getRequestedTcodesWithPlantD(Long surRecid, Long requestId) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select at.sat_id as tcodeId,at.tcode as tcode, at.tcode_desc as tcodeDesc ,tm.role_code AS roleCode, tm.role_id AS roleId,  ");
			queryBuilder.append(
					" at.module , rt.REQUEST_ID as requestId,rt.SURT_RECID as surtRecid,rt.SUR_RECID as surRecid,rt.status ,rp.plant ,ur.SAP_COMPANY_CODE as sapCompanyCode ");
			queryBuilder.append(
					" ,  rem.ROLE_CODE as empRoleCode,  rem.ROLE_ID as empRoleId ");
			queryBuilder.append(
					" from SAP_AM_TCODES at join  SAP_AM_USER_REQUEST_TCODES rt on at.SAT_ID=rt.tcode_id and  ");

			queryBuilder.append(
					" at.tcode=rt.tcode join   SAP_AM_USER_REQUESTS ur  on  rt.REQUEST_ID=ur.REQUEST_ID  and rt.SUR_RECID=ur.sur_recid ");
			queryBuilder.append(
					" join   SAP_AM_USER_REQUEST_PLANTS rp   on  rp.REQUEST_ID=ur.REQUEST_ID and rp.SUR_RECID=ur.sur_recid  ");
			queryBuilder
			 .append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=at.sat_id and    tm.TCODE=at.tcode ");
			 //.append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on tm.TCODE_ID=at.sat_id and    tm.TCODE=at.tcode   and tm.MODULE=at.MODULE ");
			//.append("   left join SAP_AM_ROLE_TCODE_MAPPING tm on    tm.TCODE=at.tcode   and tm.MODULE=at.MODULE ");
			queryBuilder
			.append("    and   (rp.plant=tm.plant or rp.PURCHASE_GROUP=tm.PURCHASE_GROUP )  and tm.status ='A' ");
			queryBuilder
			.append("    left join SAP_AM_ROLE_EMP_MAPPING rem on rem.emp_code=ur.emp_code and rem.role_id= tm.role_id  and rem.status='A'  ");
			StringBuilder sWhere = new StringBuilder(" where rt.SUR_RECID=:psurRecid and ");
			sWhere.append(" rt.REQUEST_ID=:prequestId and ");
			sWhere.append(" rt.status='I'  ");
			sWhere.append(" and rp.status='I'  ");
			queryBuilder.append(sWhere.toString());
			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  rt.SURT_RECID  desc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("tcodeId", LongType.INSTANCE).addScalar("tcode", StringType.INSTANCE)
			.addScalar("roleCode", StandardBasicTypes.STRING).addScalar("roleId", StandardBasicTypes.LONG)
			.addScalar("empRoleCode", StandardBasicTypes.STRING).addScalar("empRoleId", StandardBasicTypes.LONG)
					.addScalar("plant", StringType.INSTANCE).addScalar("tcodeDesc", StringType.INSTANCE)
					.addScalar("module", StringType.INSTANCE).addScalar("requestId", BigDecimalType.INSTANCE)
					.addScalar("status", StringType.INSTANCE).addScalar("surRecid", LongType.INSTANCE)
					.addScalar("sapCompanyCode", StringType.INSTANCE).addScalar("surtRecid", LongType.INSTANCE);

			query.setParameter("psurRecid", surRecid);
			query.setParameter("prequestId", requestId);

			query.setResultTransformer(new AliasToBeanNestedResultTransformer(UserRequestDTO.class));
			return (List<UserRequestDTO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
			throw e;
		}

	}

	@Transactional
	@Override
	public List<UserRequestDTO> getTcodesWithWithoutFunction(Long surRecid, Long requestId) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					" select at.sat_id as tcodeId,at.tcode as tcode, at.tcode_desc as tcodeDesc ,  ");
			queryBuilder.append(
					" at.module , rt.REQUEST_ID as requestId,rt.SURT_RECID as surtRecid,rt.SUR_RECID as surRecid,rt.status , fun.fun_id as funId  ");
			queryBuilder.append(
					" from SAP_AM_TCODES at left join SAP_AM_TCODE_FUN_MAPPING fun  on at.SAT_ID=fun.TCODE_ID    join  SAP_AM_USER_REQUEST_TCODES rt on at.SAT_ID=rt.tcode_id and  ");
			queryBuilder.append(" at.tcode=rt.tcode ");
			StringBuilder sWhere = new StringBuilder(" where rt.SUR_RECID=:psurRecid and ");
			sWhere.append(" rt.REQUEST_ID=:prequestId and ");
			sWhere.append(" rt.status='S'  ");
			queryBuilder.append(sWhere.toString());
			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  rt.SURT_RECID  desc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("tcodeId", LongType.INSTANCE).addScalar("tcode", StringType.INSTANCE)
					.addScalar("funId", StringType.INSTANCE).addScalar("tcodeDesc", StringType.INSTANCE)
					.addScalar("module", StringType.INSTANCE).addScalar("requestId", BigDecimalType.INSTANCE)
					.addScalar("status", StringType.INSTANCE).addScalar("surRecid", LongType.INSTANCE)
					.addScalar("surtRecid", LongType.INSTANCE);

			query.setParameter("psurRecid", surRecid);
			query.setParameter("prequestId", requestId);

			query.setResultTransformer(new AliasToBeanNestedResultTransformer(UserRequestDTO.class));
			return (List<UserRequestDTO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	@Override
	public List<UserRequestDTO> getRequestedByUserID(String requestType, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			System.out.println(requestType);

			StringBuilder queryBuilder = new StringBuilder(
					"select   ur.REQUEST_ID as requestId,ur.SUR_RECID as surRecid, ur.status, ur.request_type as requestType, ur.SAP_MODULE_CODE as module ,ur.SAP_COMPANY_CODE as sapCompanyCode ,ur.reason as reason , ur.SAP_USER_ID as sapUserId ");
			queryBuilder.append(" from   SAP_AM_USER_REQUESTS ur   ");
			StringBuilder sWhere = new StringBuilder(
					" where ur.emp_code=:pempCode and ur.request_type=:prequestType and  ur.status='I' ");
			queryBuilder.append(sWhere.toString());
			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  ur.SUR_RECID  desc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("requestId", BigDecimalType.INSTANCE).addScalar("status", StringType.INSTANCE)
			.addScalar("reason", StringType.INSTANCE)
					.addScalar("requestType", StringType.INSTANCE).addScalar("surRecid", LongType.INSTANCE)
					.addScalar("sapCompanyCode", StringType.INSTANCE)
					.addScalar("sapUserId", StringType.INSTANCE)
			.addScalar("module", StringType.INSTANCE);

			query.setParameter("prequestType", requestType);
			query.setParameter("pempCode", principal.getEmpCode());

			System.out.println("HHHHHHHHHHHHHHHHH");
			query.setResultTransformer(new AliasToBeanNestedResultTransformer(UserRequestDTO.class));
			return (List<UserRequestDTO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	@Override
	public List<UserRequestDTO> getRequestedByUserID(HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(
					"select   ur.REQUEST_ID as requestId,ur.SUR_RECID as surRecid,ur.status,ur.SAP_MODULE_CODE as module ,ur.SAP_COMPANY_CODE as sapCompanyCode ");
			queryBuilder.append(" from   SAP_AM_USER_REQUESTS ur   ");
			StringBuilder sWhere = new StringBuilder(" where ur.emp_code=:pempCode  and  ur.status='I' ");
			queryBuilder.append(sWhere.toString());

			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  ur.SUR_RECID  desc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("requestId", BigDecimalType.INSTANCE).addScalar("status", StringType.INSTANCE)
					.addScalar("surRecid", LongType.INSTANCE).addScalar("sapCompanyCode", StringType.INSTANCE)
					.addScalar("module", StringType.INSTANCE);
			query.setParameter("pempCode", principal.getEmpCode());

			System.out.println("HHHHHHHHHHHHHHHHH");
			query.setResultTransformer(new AliasToBeanNestedResultTransformer(UserRequestDTO.class));
			return (List<UserRequestDTO>) query.list();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SapAmRoleMaster >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public long updateStatusAll(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_TCODES ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");

			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			queryBuilder.append(" WHERE  ur.SUR_RECID = :psurRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");
			queryBuilder.append(" and ur.status != 'R' ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRolesAssignVO.getStatus());
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
}
