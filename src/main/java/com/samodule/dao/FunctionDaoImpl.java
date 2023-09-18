package com.samodule.dao;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.samodule.model.SapAmFunction;

@Repository("functionDao")
public class FunctionDaoImpl extends JpaDao<SapAmFunction> implements FunctionDao {

	@Override
	public List<SapAmFunction> getFunction() throws Exception {

		Session session = currentSession();
		StringBuilder queryBuilder = new StringBuilder("from SapAmFunction");
		Query query = session.createQuery(queryBuilder.toString());

		return (List<SapAmFunction>) query.getResultList();

	}
    @Transactional
	@Override
	public List<String> getFunctionByRoles(Set<String> roles) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();

		try {

			StringBuilder queryBuilder = new StringBuilder(
					// f.fun_id,
					" select distinct f.fun_code from SAP_AM_FUNctions f join  ");

			queryBuilder
					.append(" SAP_AM_TCODE_FUN_MAPPING m   on f.fun_id=m.fun_id   join  SAP_AM_ROLE_TCODE_MAPPING t  ");
			queryBuilder.append("  on t.tcode_id=m.tcode_id where t.role_code in :proles  ");
			Query query = session.createSQLQuery(queryBuilder.toString());
			query.setParameterList("proles", roles);

			return (List<String>) query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			// log.error("ERROR >> FunctionDaoImpl >> getRisk", e);
			e.printStackTrace();
			throw e;
		}

	}
    
    @Transactional
   	@Override
   	public List<String> getFunctionByRole(String role) throws Exception {
   		// TODO Auto-generated method stub
   		Session session = currentSession();

   		try {

   			StringBuilder queryBuilder = new StringBuilder(
   					// f.fun_id,
   					" select distinct f.fun_code from SAP_AM_FUNctions f join  ");

   			queryBuilder
   					.append(" SAP_AM_TCODE_FUN_MAPPING m   on f.fun_id=m.fun_id   join  SAP_AM_ROLE_TCODE_MAPPING t  ");
   			queryBuilder.append("  on t.tcode_id=m.tcode_id where t.role_code =:proles  ");
   			Query query = session.createSQLQuery(queryBuilder.toString());
   			query.setParameter("proles", role);

   			return (List<String>) query.getResultList();
   		} catch (Exception e) {
   			// TODO: handle exception
   			// log.error("ERROR >> FunctionDaoImpl >> getRisk", e);
   			e.printStackTrace();
   			throw e;
   		}

   	}
}
