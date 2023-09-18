package com.samodule.dao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmRoleMaster;
import com.samodule.model.SapAmUserRequest;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.util.CopyObjUtil;
import com.samodule.util.DateUtil;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.UserRequestDTO;

@Repository("userRequestDao")
public class UserRequestDaoImpl extends JpaDao<SapAmUserRequest> implements UserRequestDao {
	static final Logger log = Logger.getLogger(UserRequestDaoImpl.class.getName());

	@Override
	public SapAmUserRequest saveRequest(SapAmUserRequest sapAmUserRequest, HeapUserDetails principal) throws Exception {

		Session session = currentSession();
		// Transaction tx = session.beginTransaction();
		try {

			Query sqlQuery = session.createQuery(" from SapAmUserRequest where surRecid=:psurRecid ");
			sqlQuery.setParameter("psurRecid", sapAmUserRequest.getSurRecid());
			Long surRecid = 0L;
			SapAmUserRequest temp = (SapAmUserRequest) sqlQuery.uniqueResult();
			if (temp != null) {
				temp = (SapAmUserRequest) CopyObjUtil.cloneObject(sapAmUserRequest, temp);
				session.saveOrUpdate(temp);
				surRecid = temp.getSurRecid();
			} else {
				log.info(" befor save update $$ " + (sapAmUserRequest.getSurRecid() == 0));
				sapAmUserRequest.setStatus("I");
				sapAmUserRequest.setWfLevel("1");
				sapAmUserRequest.setCreatedOn(new Date());
				sapAmUserRequest.setRequestDate(new Date());
				// sapAmUserRequest.setCreatedBy(principal.getImisLoginId());
				sapAmUserRequest.setCreatedBy(principal.getEmpCode());
				sapAmUserRequest.setSapUserId(principal.getSapUserId());
//			if (sapAmUserRequest.getSapCompanyCode() != null
//					&& sapAmUserRequest.getSapCompanyCode().equalsIgnoreCase("1000,2000,3000"))
//				sapAmUserRequest.setSapCompanyCode("ALL");
//			else if (sapAmUserRequest.getSapCompanyCode() == null)
//				sapAmUserRequest.setSapCompanyCode("ALL");
				if (sapAmUserRequest.getSapCompanyCode() == null)
					sapAmUserRequest.setSapCompanyCode(principal.getSapEntityCode());
				sapAmUserRequest.setEmpCode(principal.getEmpCode());
				if (sapAmUserRequest.getRequestId() == null) {
					long r = this.getRequestIdByDate();
					System.out.println(r);
					sapAmUserRequest.setRequestId(new BigDecimal(r));
				}
				session.saveOrUpdate(sapAmUserRequest);
			}
			// tx.commit();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
//		finally {
//			if (session != null)
//				session.close();
//		}
		return sapAmUserRequest;

	}

	@Override
	public SapAmUserRequest saveRequestEmpRef(SapAmUserRequest sapAmUserRequest, HeapUserDetails principal)
			throws Exception {

		Session session = currentSession();
		// Transaction tx = session.beginTransaction();
		try {
			sapAmUserRequest.setStatus("S");
			sapAmUserRequest.setWfLevel("1");
			sapAmUserRequest.setCreatedOn(new Date());
			sapAmUserRequest.setRequestDate(new Date());
			// sapAmUserRequest.setCreatedBy(principal.getImisLoginId());
			sapAmUserRequest.setCreatedBy(principal.getEmpCode());
			sapAmUserRequest.setSapUserId(principal.getSapUserId());
//			if (sapAmUserRequest.getSapCompanyCode() != null
//					&& sapAmUserRequest.getSapCompanyCode().equalsIgnoreCase("1000,2000,3000"))
//				sapAmUserRequest.setSapCompanyCode("ALL");
//			else if (sapAmUserRequest.getSapCompanyCode() == null)
//				sapAmUserRequest.setSapCompanyCode("ALL");
			sapAmUserRequest.setEmpCode(principal.getEmpCode());
			if (sapAmUserRequest.getRequestId() == null) {
				long r = this.getRequestIdByDate();
				System.out.println(r);
				sapAmUserRequest.setRequestId(new BigDecimal(r));
			}
			session.saveOrUpdate(sapAmUserRequest);
			// tx.commit();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
//		finally {
//			if (session != null)
//				session.close();
//		}
		return sapAmUserRequest;

	}

	@Override
	public long submitRequest(SapAmUserRequest sapAmUserRequest, HeapUserDetails principal) throws Exception {

		Session session = currentSession();
		// Transaction tx = session.beginTransaction();
		try {
			try {
				StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUESTS ur ");
				queryBuilder.append(" SET ur.status = :pstatus ");
				queryBuilder.append(" , ur.reason = :preason");
				if (sapAmUserRequest.getToDate() != null) {
					queryBuilder.append(" , ur.FROM_DATE = :pfromDate");
					queryBuilder.append(" , ur.TO_DATE = :ptoDate");
				}
				// queryBuilder.append(" , ur.status = :pstatus");
				queryBuilder.append(" WHERE ur.SUR_RECID = :psurRecid ");
				queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");
				/*
				 * "reason":"fdfdhfdhdhd","fortime":"temporary","requestId":"21420235",
				 * "fromDate":"2023-04-25","toDate":"2023-04-26"
				 * session.createNativeQuery("UPDATE SAP_AM_USER_REQUESTS pi" +
				 * " JOIN ereturn er ON pi.ereturn = er.id " +
				 * "SET pi.receptacle = :receptacle " + "WHERE pi.returnAction = :returnAction "
				 * + "AND er.destination = :destination " + "AND er.status = 'RECEIVED'")
				 */

				NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
				nativeQuery.setParameter("pstatus", sapAmUserRequest.getStatus());
				nativeQuery.setParameter("preason", sapAmUserRequest.getReason());
				if (sapAmUserRequest.getToDate() != null) {
					nativeQuery.setParameter("pfromDate", sapAmUserRequest.getFromDate());
					nativeQuery.setParameter("ptoDate", sapAmUserRequest.getToDate());
				}
				nativeQuery.setParameter("psurRecid", sapAmUserRequest.getSurRecid());
				nativeQuery.setParameter("prequestId", sapAmUserRequest.getRequestId());
				return nativeQuery.executeUpdate();
				// tx.commit();
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
			// tx.commit();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

	}

	public long getRequestIdByDate() throws Exception {
		Session session = currentSession();
		// session.beginTransaction();
		try {// to_date('"+DateUtil.getFormateDate(voucherDate)+"','DD/MM/YYYY')
			StringBuilder queryBuilder = new StringBuilder( // TO_DATE(FORDATE,'dd/MM/yyyy')>=TO_DATE('11/01/2019','dd/MM/yyyy')
					" select max(REQUEST_ID) from SAP_AM_USER_REQUESTS   where TO_CHAR(CREATED_ON,'dd/MM/yyyy')='"
							+ DateUtil.getStringDateDDMMYYYY(new Date()) + "'");
			Query query = session.createSQLQuery(queryBuilder.toString());
			// session.getTransaction().commit();
			List<BigDecimal> requestIds = (List<BigDecimal>) query.list();

			if (requestIds != null && requestIds.size() > 0)
				System.out.println("requestId  > ::  " + requestIds.get(0));
			long todayseq = Long.parseLong(DateUtil.getDateDDMMYYYY());
			long id = (requestIds != null && requestIds.get(0) != null) ? requestIds.get(0).longValue() : todayseq;
			long remender = id % todayseq;
			System.out.println(id + " % " + todayseq + " = " + remender);
			StringBuilder requestIdBuilder = new StringBuilder(Long.toString(todayseq));
			remender = remender + 1;
			System.out.println("remender = " + remender);
			requestIdBuilder.append(Long.toString(remender));
			return Long.parseLong(requestIdBuilder.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> SAP_AM_USER_REQUESTS >> get", e);
			e.printStackTrace();
			throw e;
		}
//		finally {
//			if (session != null)
//				session.close();
//		}

	}

	@Override
	public long submitRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUESTS ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			// queryBuilder.append(" , ur.status = :pstatus");
			queryBuilder.append(" WHERE ur.SUR_RECID = :psurRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");
			/*
			 * session.createNativeQuery("UPDATE SAP_AM_USER_REQUESTS pi" +
			 * " JOIN ereturn er ON pi.ereturn = er.id " +
			 * "SET pi.receptacle = :receptacle " + "WHERE pi.returnAction = :returnAction "
			 * + "AND er.destination = :destination " + "AND er.status = 'RECEIVED'")
			 */

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
	public long rejectRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();

		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUESTS ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			queryBuilder.append(" , ur.WF_LEVEL = :pwflevel ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			// queryBuilder.append(" , ur.status = :pstatus"); STATUS WF_LEVEL APPROVED_ON
			// APPROVED_BY
			queryBuilder.append(" WHERE  ur.status = 'S' and    ur.SUR_RECID = :psurRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");
			/*
			 * session.createNativeQuery("UPDATE SAP_AM_USER_REQUESTS pi" +
			 * " JOIN ereturn er ON pi.ereturn = er.id " +
			 * "SET pi.receptacle = :receptacle " + "WHERE pi.returnAction = :returnAction "
			 * + "AND er.destination = :destination " + "AND er.status = 'RECEIVED'")
			 */

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRequestVO.getStatus());
			nativeQuery.setParameter("pwflevel", 2);
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

	@Override
	public long approveRequestByCTM(Integer surRecid, Integer requestId, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUESTS ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			queryBuilder.append(" , ur.WF_LEVEL = :pwflevel ");
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
			nativeQuery.setParameter("pwflevel", 2);
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

	@Transactional
	@Override
	public List<UserRequestDTO> getRequestedRolesUserID(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception {
		Session session = currentSession();
		try {
			System.out.println("List" + criteria.toString());
			List<String> columnName = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));

			StringBuilder queryBuilder = new StringBuilder(
					" select distinct rm.ROLE_ID as roleId , rm.ROLE_CODE as roleCode , rm.ROLE_NAME as roleName , rm.plant as plant , ");
			queryBuilder.append(
					" rt.REQUEST_ID as requestId, rt.SURP_RECID as surpRecid,rt.SUR_RECID as surRecid,rt.status ,ur.SAP_COMPANY_CODE as sapCompanyCode,ur.REASON as reason ");
			queryBuilder.append(
					" from sap_am_role_tcode_mapping rm join  SAP_AM_USER_REQUEST_PLANTS rt on rm.ROLE_ID=rt.ROLE_ID and  ");
			queryBuilder.append(
					" rm.ROLE_CODE=rt.ROLE_CODE and  rm.status='A' join SAP_AM_USER_REQUESTS ur on  rt.REQUEST_ID=ur.REQUEST_ID and rt.SUR_RECID=ur.sur_recid ");
			StringBuilder sWhere = new StringBuilder(" where ur.emp_code=:pempCode and  rt.status='I' ");
			queryBuilder.append(sWhere.toString());
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and rt.tcode " + " like UPPER('%" + criteria.sSearch + "%')   ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			System.out.println(queryBuilder.toString() + " ORDER BY SUR_RECID " + criteria.sSortDir_0);
			NativeQuery<UserRequestDTO> query = session
					.createNativeQuery(queryBuilder.toString() + " ORDER BY  rm.ROLE_ID  asc  ");
			// + " ORDER BY " + columnName.get(criteria.iSortCol_0) + " " +
			// criteria.sSortDir_0

			query.addScalar("roleId", LongType.INSTANCE).addScalar("roleCode", StringType.INSTANCE)
					.addScalar("plant", StringType.INSTANCE).addScalar("roleName", StringType.INSTANCE)
					.addScalar("requestId", BigDecimalType.INSTANCE).addScalar("status", StringType.INSTANCE)
					.addScalar("reason", StringType.INSTANCE).addScalar("surRecid", LongType.INSTANCE)
					.addScalar("sapCompanyCode", StringType.INSTANCE).addScalar("surpRecid", LongType.INSTANCE);

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
	public long updateRequestHOD(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();

		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUESTS ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			queryBuilder.append(" , ur.WF_LEVEL = :pwflevel ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			// queryBuilder.append(" , ur.status = :pstatus"); STATUS WF_LEVEL APPROVED_ON
			// APPROVED_BY
			queryBuilder.append(" WHERE   ur.status != 'R' and  ur.SUR_RECID = :psurRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRequestVO.getStatus());
			nativeQuery.setParameter("pwflevel", sapAmRequestVO.getWfLevel());
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

	@Override
	public long updateRequestBasis(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();

		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUESTS ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			queryBuilder.append(" , ur.WF_LEVEL = :pwflevel ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			// queryBuilder.append(" , ur.status = :pstatus"); STATUS WF_LEVEL APPROVED_ON
			// APPROVED_BY
			queryBuilder.append(" WHERE   ur.status != 'R' and  ur.SUR_RECID = :psurRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRequestVO.getStatus());
			nativeQuery.setParameter("pwflevel", 4);
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

	@Override
	public long updateStatusAll(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUESTS ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			queryBuilder.append(" , ur.WF_LEVEL = :pwflevel ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			// queryBuilder.append(" , ur.status = :pstatus"); STATUS WF_LEVEL APPROVED_ON
			// APPROVED_BY
			queryBuilder.append(" WHERE ur.SUR_RECID = :psurRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");
			queryBuilder.append(" and ur.status != 'R' ");
			/*
			 * session.createNativeQuery("UPDATE SAP_AM_USER_REQUESTS pi" +
			 * " JOIN ereturn er ON pi.ereturn = er.id " +
			 * "SET pi.receptacle = :receptacle " + "WHERE pi.returnAction = :returnAction "
			 * + "AND er.destination = :destination " + "AND er.status = 'RECEIVED'")
			 */

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRolesAssignVO.getStatus());
			nativeQuery.setParameter("pwflevel", sapAmRolesAssignVO.getWfLevel());
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
