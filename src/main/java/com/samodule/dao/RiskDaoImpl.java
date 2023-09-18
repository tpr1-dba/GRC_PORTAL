package com.samodule.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.samodule.model.SapAmRisk;

@Repository("riskDao")
public class RiskDaoImpl extends JpaDao<SapAmRisk> implements RiskDao {
	
	@Override
	public List<SapAmRisk> getRisk() throws Exception {

		Session session = currentSession();

		StringBuilder queryBuilder = new StringBuilder("from SapAmRisk");

		Query query = session.createQuery(queryBuilder.toString());
		//Query query=session.createQuery(queryBuilder.toString(), SapAmRisk.class);
		return (List<SapAmRisk>) query.getResultList();
	}

}
