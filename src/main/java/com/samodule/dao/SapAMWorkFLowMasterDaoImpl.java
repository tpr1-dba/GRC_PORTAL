package com.samodule.dao;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.samodule.model.Role;
import com.samodule.model.SapAmWorkflowMaster;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.vo.SAPUserRequestVO;

@Repository("sapAMWorkFLowMasterDao")
public class SapAMWorkFLowMasterDaoImpl extends JpaDao<Role> implements SapAMWorkFLowMasterDao {
	static final Logger log = Logger.getLogger(SapAMWorkFLowMasterDaoImpl.class.getName());

	@Override
	@Transactional
	public List<SapAmWorkflowMaster> getRoleByLogin(String loginId) {
		// TODO Auto-generated method stub
		log.info(loginId);

		Session session = currentSession();
		// session.beginTransaction();
		try {
			log.info("IF");
			Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where empCode=:empCode");
			sqlQuery.setParameter("empCode", loginId);
			return (List<SapAmWorkflowMaster>) sqlQuery.getResultList();
			// session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmLoginDaoJpa >> get", e);
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<SapAmWorkflowMaster> getWorkFLows(Set<String> modules, Set<String> compenies,
			HeapUserDetails principal)throws Exception {
	//public List<SapAmWorkflowMaster> getWorkFLows(List<UserRequestDTO> requestTcodeDTOs, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();
		// session.beginTransaction();
		
		
		try {
			log.info("IF");
//			Set<String> modules=new HashSet<>();		    
//			requestTcodeDTOs.forEach(t-> {
//				modules.add(t.getModule());					
//			});	
//			List<String> compenies= "ALL".equalsIgnoreCase(requestTcodeDTOs.get(0).getSapCompanyCode())?Arrays.asList("1000","2000","3000"):Arrays.asList(requestTcodeDTOs.get(0).getSapCompanyCode());
			Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where companyCode in :pcompanyCode and sbu=:psbu and sapModule in :psapModule ");
			//Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where companyCode in :pcompanyCode  and sapModule in :psapModule ");
			sqlQuery.setParameterList("pcompanyCode", compenies);
			sqlQuery.setParameter("psbu", principal.getSbuCode());
			sqlQuery.setParameterList("psapModule", modules);
			return (List<SapAmWorkflowMaster>) sqlQuery.getResultList();
			// session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmLoginDaoJpa >> get", e);
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<SapAmWorkflowMaster> getWorkFLowsForSBU(Set<String> modules, Set<String> compenies,List<String> sbus,
			HeapUserDetails principal)throws Exception {
	//public List<SapAmWorkflowMaster> getWorkFLows(List<UserRequestDTO> requestTcodeDTOs, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();
		// session.beginTransaction();
		
		
		try {
			log.info("IF");
			Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where companyCode in :pcompanyCode and sbu in :psbu and sapModule in :psapModule ");
			sqlQuery.setParameterList("pcompanyCode", compenies);
			sqlQuery.setParameterList("psbu", sbus);
			sqlQuery.setParameterList("psapModule", modules);
			return (List<SapAmWorkflowMaster>) sqlQuery.getResultList();
			// session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmLoginDaoJpa >> get", e);
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<SapAmWorkflowMaster> getWorkFLowsForSBUSQL(Set<String> modules, Set<String> compenies,List<String> sbus,
			HeapUserDetails principal)throws Exception {
	//public List<SapAmWorkflowMaster> getWorkFLows(List<UserRequestDTO> requestTcodeDTOs, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();
		// session.beginTransaction();
		
		
		try {
			log.info("IF");
			StringBuilder queryBuilder = new StringBuilder(
					"  select distinct emp_code as empCode,COMPANY_CODE as companyCode,sbu as sbu , WF_CODE as wfCode ,SAP_MODULE as sapModule, WF_LEVEL, ");
			queryBuilder.append(" MAX(WF_LEVEL) OVER (PARTITION BY  WF_CODE ORDER BY WF_CODE ) as wfLevel    from SAP_AM_WORKFLOW_MASTER  where COMPANY_CODE  in :pcompanyCode and sbu in :psbu and SAP_MODULE in :psapModule");
			//Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where companyCode in :pcompanyCode and sbu in :psbu and sapModule in :psapModule ");
			//Query sqlQuery = session.createNativeQuery(queryBuilder.toString());
			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("empCode", StandardBasicTypes.STRING)
					.addScalar("companyCode", StandardBasicTypes.STRING)
					.addScalar("sbu", StandardBasicTypes.STRING)
					.addScalar("wfCode", StandardBasicTypes.STRING)
					.addScalar("sapModule", StandardBasicTypes.STRING)
					.addScalar("wfLevel", StandardBasicTypes.BIG_DECIMAL);
			sqlQuery.setParameterList("pcompanyCode", compenies);
			sqlQuery.setParameterList("psbu", sbus);
			sqlQuery.setParameterList("psapModule", modules);
			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmWorkflowMaster.class));
			return (List<SapAmWorkflowMaster>) sqlQuery.getResultList();
			// session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmLoginDaoJpa >> get", e);
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<SapAmWorkflowMaster> getWorkFLowsByRole(Set<String> modules, Set<String> compenies,
			HeapUserDetails principal)throws Exception {

		Session session = currentSession();
		
		
		
		try {
			log.info("IF");
//			Set<String> modules=new HashSet<>();		    
//			requestTcodeDTOs.forEach(t-> {
//				modules.add(t.getModule());					
//			});	
//			List<String> compenies= "ALL".equalsIgnoreCase(requestTcodeDTOs.get(0).getSapCompanyCode())?Arrays.asList("1000","2000","3000"):Arrays.asList(requestTcodeDTOs.get(0).getSapCompanyCode());
			Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where companyCode=:pcompanyCode and sbu=:psbu and sapModule=:psapModule ");
			//Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where companyCode in :pcompanyCode  and sapModule in :psapModule ");
			sqlQuery.setParameterList("pcompanyCode", compenies);
			sqlQuery.setParameter("psbu", principal.getSbuCode());
			sqlQuery.setParameterList("psapModule", modules);
			return (List<SapAmWorkflowMaster>) sqlQuery.getResultList();
			// session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmLoginDaoJpa >> get", e);
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<SapAmWorkflowMaster> getWorkFLowsByEmpCode(Set<String> modules, Set<String> compenies,
			HeapUserDetails principal)throws Exception {
	//public List<SapAmWorkflowMaster> getWorkFLows(List<UserRequestDTO> requestTcodeDTOs, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();
		// session.beginTransaction();
		
		
		try {
			log.info("IF");
//			Set<String> modules=new HashSet<>();		    
//			requestTcodeDTOs.forEach(t-> {
//				modules.add(t.getModule());					
//			});	
//			List<String> compenies= "ALL".equalsIgnoreCase(requestTcodeDTOs.get(0).getSapCompanyCode())?Arrays.asList("1000","2000","3000"):Arrays.asList(requestTcodeDTOs.get(0).getSapCompanyCode());
			Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where companyCode=:pcompanyCode and sbu=:psbu and sapModule=:psapModule ");
			//Query sqlQuery = session.createQuery("from SapAmWorkflowMaster where companyCode in :pcompanyCode  and sapModule in :psapModule ");
			sqlQuery.setParameterList("pcompanyCode", compenies);
			sqlQuery.setParameter("psbu", principal.getSbuCode());
			sqlQuery.setParameterList("psapModule", modules);
			return (List<SapAmWorkflowMaster>) sqlQuery.getResultList();
			// session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmLoginDaoJpa >> get", e);
			e.printStackTrace();
			throw e;
		}
		
	}

}
