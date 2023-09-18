package com.samodule.dao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequestPlant;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.vo.RequestPlantDTO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapAmRolesRequestVO;

@Repository("requestPlantDao")
public class RequestPlantDaoImpl extends JpaDao<SapAmUserRequestPlant> implements RequestPlantDao {
	static final Logger log = Logger.getLogger(RequestPlantDaoImpl.class.getName());

	@Override
	public long savePlants(List<SapAmUserRequestPlant> amUserRequestPlants, HeapUserDetails principal)
			throws Exception {
		int batchSize = 10;
		int j = 0;
		Session session = currentSession();
		// Transaction tx = session.beginTransaction();
		try {

			Iterator<SapAmUserRequestPlant> it = amUserRequestPlants.iterator();
			while (it.hasNext()) {
				SapAmUserRequestPlant sapAmUserRequestPlant = it.next();
				sapAmUserRequestPlant.setStatus("I");
				sapAmUserRequestPlant.setCreatedBy(principal.getEmpCode());
				sapAmUserRequestPlant.setCreatedOn(new Date());
				session.saveOrUpdate(sapAmUserRequestPlant);

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
//		} finally {
//			if (session != null)
//				session.close();
//		}
		return j;
	}

	@Transactional
	@Override
	public List<RequestPlantDTO> getRequestedPlant(Long surRecid, Long requestId, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			System.out.println("List" + criteria.toString());
			List<String> columnName = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));

			StringBuilder queryBuilder = new StringBuilder(
					" select rp.SURP_RECID as surpRecid , rp.SUR_RECID as surRecid , ");
			queryBuilder.append(" rp.REQUEST_ID as requestId, rp.PLANT as plant,rp.PURCHASE_GROUP  as purchaseGroup ");
			// queryBuilder.append(" from SAP_AM_USER_REQUEST_TCODES rt join
			// SAP_AM_USER_REQUEST_PLANTS rp ");
			queryBuilder.append(" from SAP_AM_USER_REQUESTS rt join  SAP_AM_USER_REQUEST_PLANTS rp  ");
			queryBuilder.append(" on rt.SUR_RECID=rp.SUR_RECID and rt.REQUEST_ID=rp.REQUEST_ID  ");
			StringBuilder sWhere = new StringBuilder(" where  rp.SUR_RECID=:psurRecid and ");
			sWhere.append(" rp.REQUEST_ID =:prequestId and ");
			sWhere.append(" rp.status='I'  ");
			queryBuilder.append(sWhere.toString());
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and (rp.PLANT like UPPER('%" + criteria.sSearch
						+ "%')  or   rp.PURCHASE_GROUP like UPPER('%" + criteria.sSearch + "%')  ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			NativeQuery<RequestPlantDTO> query = session
					.createNativeQuery(queryBuilder.toString() + "  ORDER BY  rp.SURP_RECID desc   ");

			query.addScalar("surpRecid", LongType.INSTANCE).addScalar("surRecid", LongType.INSTANCE)
					.addScalar("requestId", BigDecimalType.INSTANCE).addScalar("plant", StringType.INSTANCE)
					.addScalar("purchaseGroup", StringType.INSTANCE);
			query.setParameter("psurRecid", surRecid);
			query.setParameter("prequestId", requestId);
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			System.out.println("HHHHHHHHHHHHHHHHH");
			query.setResultTransformer(new AliasToBeanNestedResultTransformer(RequestPlantDTO.class));
			return (List<RequestPlantDTO>) query.list();

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
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_PLANTS ur ");
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
	public long submitRequestRole(List<SapAmRolesRequestVO> requestTcodeDTOs, String status, HeapUserDetails principal)
			throws Exception {
		Session session = currentSession();
		int batchSize = 10;
		int j = 0;

		try {

			Iterator<SapAmRolesRequestVO> it = requestTcodeDTOs.iterator();
			while (it.hasNext()) {
				StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_PLANTS ur ");
				queryBuilder.append(" SET ur.status = :pstatus ");
				queryBuilder.append(" , ur.SAP_MODULE = :pmodule");
				queryBuilder.append(" WHERE ur.SUR_RECID = :psurRecid ");
				queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");
				queryBuilder.append(" and ur.ROLE_ID = :proleId ");
				SapAmRolesRequestVO sapAmRequestVO = it.next();
				NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
				nativeQuery.setParameter("pstatus", status);
				nativeQuery.setParameter("pmodule", sapAmRequestVO.getModule());
				nativeQuery.setParameter("psurRecid", sapAmRequestVO.getSurRecid());
				nativeQuery.setParameter("prequestId", sapAmRequestVO.getRequestId());
				nativeQuery.setParameter("proleId", sapAmRequestVO.getRoleId());
				// nativeQuery.setParameter("prequestId", sapAmRequestVO.getRequestId());
				nativeQuery.executeUpdate();
				j++;

				if (j % batchSize == 0 && j > 0) {
					// Flush A Batch Of Updates & Release Memory
					session.flush();
					session.clear();
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return j;
	}

	@Override
	public long submitRequestRoleEmpRef(List<SapAmRolesRequestVO> requestTcodeDTOs, SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal)
			throws Exception {
		Session session = currentSession();
		int batchSize = 10;
		int j = 0;

		try {

			Iterator<SapAmRolesRequestVO> it = requestTcodeDTOs.iterator();
			while (it.hasNext()) {
				SapAmRolesRequestVO temp=it.next();
				SapAmUserRequestPlant sapAmUserRequestPlant = new SapAmUserRequestPlant();
				//sapAmUserRequestPlant.setStatus(status);
				sapAmUserRequestPlant.setStatus("S");				
				sapAmUserRequestPlant.setRoleId(new BigDecimal(temp.getRoleId()));
				sapAmUserRequestPlant.setRoleCode(temp.getRoleCode());
				sapAmUserRequestPlant.setPlant(temp.getPlant());
				sapAmUserRequestPlant.setRequestId(sapAmRequestVO.getRequestId());
				sapAmUserRequestPlant.setSurRecid(new BigDecimal(sapAmRequestVO.getSurRecid()));
				sapAmUserRequestPlant.setCreatedBy(principal.getEmpCode());
				sapAmUserRequestPlant.setCreatedOn(new Date());
				session.saveOrUpdate(sapAmUserRequestPlant);
				j++;
				if (j % batchSize == 0 && j > 0) {
					// Flush A Batch Of Updates & Release Memory
					session.flush();
					session.clear();
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return j;
	}
	
	
	@Override
	public long updateStatus(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_PLANTS ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			// queryBuilder.append(" , ur.status = :pstatus");
			queryBuilder.append(" WHERE ur.SURP_RECID = :psurpRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRequestVO.getStatus());
			nativeQuery.setParameter("psurpRecid", sapAmRequestVO.getSurpRecid());
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
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_PLANTS ur ");
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
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_PLANTS ur ");
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

	@Override
	public long deleteRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" delete from SAP_AM_USER_REQUEST_PLANTS ur ");
			// queryBuilder.append(" SET ur.status = :pstatus ");
			// queryBuilder.append(" , ur.status = :pstatus");
			queryBuilder.append(" WHERE ur.SURP_RECID = :psurpRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");

			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			// nativeQuery.setParameter("pstatus", sapAmRequestVO.getStatus());
			nativeQuery.setParameter("psurpRecid", sapAmRequestVO.getSurpRecid());
			nativeQuery.setParameter("prequestId", sapAmRequestVO.getRequestId());
			return nativeQuery.executeUpdate();
			// tx.commit();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

	}

	@Transactional
	@Override
	public List<RequestPlantDTO> getRequestedPlantForRequest(Long surRecid, Long requestId,
			JQueryDataTableParamModel criteria, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			System.out.println("List" + criteria.toString());
			List<String> columnName = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));

			StringBuilder queryBuilder = new StringBuilder(
					" select rp.SURP_RECID as surpRecid , rp.SUR_RECID as surRecid , ");
			queryBuilder.append(" rp.REQUEST_ID as requestId, rp.PLANT as plant,rp.PURCHASE_GROUP  as purchaseGroup ");
			// queryBuilder.append(" from SAP_AM_USER_REQUEST_TCODES rt join
			// SAP_AM_USER_REQUEST_PLANTS rp ");
			queryBuilder.append(" from SAP_AM_USER_REQUESTS rt join  SAP_AM_USER_REQUEST_PLANTS rp  ");
			queryBuilder.append(" on rt.SUR_RECID=rp.SUR_RECID and rt.REQUEST_ID=rp.REQUEST_ID  ");
			StringBuilder sWhere = new StringBuilder(" where  rp.SUR_RECID=:psurRecid and ");
			sWhere.append(" rp.REQUEST_ID =:prequestId and ");
			sWhere.append(" rp.status not in ('I','R')  ");
			queryBuilder.append(sWhere.toString());
			StringBuilder criteriaBuilder = new StringBuilder();
			if (criteria.sSearch != null && criteria.sSearch.trim().length() > 0) {
				criteriaBuilder.append(" and (rp.PLANT like UPPER('%" + criteria.sSearch
						+ "%')  or   rp.PURCHASE_GROUP like UPPER('%" + criteria.sSearch + "%')  ) ");
				queryBuilder.append(criteriaBuilder.toString());
			}

			NativeQuery<RequestPlantDTO> query = session
					.createNativeQuery(queryBuilder.toString() + "  ORDER BY  rp.SURP_RECID desc   ");

			query.addScalar("surpRecid", LongType.INSTANCE).addScalar("surRecid", LongType.INSTANCE)
					.addScalar("requestId", BigDecimalType.INSTANCE).addScalar("plant", StringType.INSTANCE)
					.addScalar("purchaseGroup", StringType.INSTANCE);
			query.setParameter("psurRecid", surRecid);
			query.setParameter("prequestId", requestId);
			criteria.iTotalDisplayRecords = criteria.iTotalRecords = query.list().size();
			query.setMaxResults(criteria.iDisplayLength);
			query.setFirstResult(criteria.iDisplayStart);
			System.out.println("HHHHHHHHHHHHHHHHH");
			query.setResultTransformer(new AliasToBeanNestedResultTransformer(RequestPlantDTO.class));
			return (List<RequestPlantDTO>) query.list();

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
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_PLANTS ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			queryBuilder.append(" WHERE   ur.SUR_RECID = :psurRecid ");
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
	
	@Override
	public long updateStatusAllRole(SapAmRolesAssignVO sapAmRolesAssignVO, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_PLANTS ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			queryBuilder.append(" WHERE   ur.SUR_RECID = :psurRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");
			queryBuilder.append(" and ur.SAP_MODULE = :pmodule ");
			queryBuilder.append(" and ur.status != 'R' ");
			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRolesAssignVO.getStatus());
			nativeQuery.setParameter("papprovedOn", new Date());
			nativeQuery.setParameter("papprovedBy", principal.getEmpCode());
			nativeQuery.setParameter("psurRecid", sapAmRolesAssignVO.getSurRecid());
			nativeQuery.setParameter("prequestId", sapAmRolesAssignVO.getRequestId());
			nativeQuery.setParameter("pmodule", sapAmRolesAssignVO.getSapModuleCode());
			return nativeQuery.executeUpdate();
			// tx.commit();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

	}
	
	@Override
	public long updateStatusAllRoleEMPHOD(SapAmRolesAssignVO sapAmRolesAssignVO,Set<String> modules, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			StringBuilder queryBuilder = new StringBuilder(" UPDATE SAP_AM_USER_REQUEST_PLANTS ur ");
			queryBuilder.append(" SET ur.status = :pstatus ");
			queryBuilder.append(" , ur.APPROVED_ON = :papprovedOn ");
			queryBuilder.append(" , ur.APPROVED_BY = :papprovedBy ");
			queryBuilder.append(" WHERE   ur.SUR_RECID = :psurRecid ");
			queryBuilder.append(" and ur.REQUEST_ID = :prequestId ");
			queryBuilder.append(" and ur.SAP_MODULE in :pmodule ");
			queryBuilder.append(" and ur.status != 'R' ");
			NativeQuery nativeQuery = session.createNativeQuery(queryBuilder.toString());
			nativeQuery.setParameter("pstatus", sapAmRolesAssignVO.getStatus());
			nativeQuery.setParameter("papprovedOn", new Date());
			nativeQuery.setParameter("papprovedBy", principal.getEmpCode());
			nativeQuery.setParameter("psurRecid", sapAmRolesAssignVO.getSurRecid());
			nativeQuery.setParameter("prequestId", sapAmRolesAssignVO.getRequestId());
			nativeQuery.setParameter("pmodule", modules);
			return nativeQuery.executeUpdate();
			// tx.commit();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

	}
}
