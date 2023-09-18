package com.samodule.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.samodule.model.SapAmRiskFunMapping;
import com.samodule.vo.SapAmRiskFunMappingVO;


@Repository("riskFunctionDao")
public class RiskFunctionDaoImpl extends JpaDao<SapAmRiskFunMapping> implements RiskFunctionDao {

	@Override
	public List<SapAmRiskFunMappingVO> getRiskFunction() throws Exception {

		Session session = currentSession();
		StringBuilder queryBuilder = new StringBuilder(
				" SELECT  rm.rfId as rfId,   rm.riskId as riskId, rm.frDesc as frDesc,   ar.riskCode as riskCode, ar.priority as priority,	rm.module as module  ");
		queryBuilder.append(" ,rm.funId as funId, af.funCode as funCode ");
		queryBuilder.append(" FROM  SapAmRiskFunMapping rm, SapAmRisk ar, SapAmFunction af  ");

		StringBuilder sWhere = new StringBuilder(
				" where ar.riskId=rm.riskId and af.funId=rm.funId ");
		queryBuilder.append(sWhere);
		Query query = session.createQuery(queryBuilder.toString()).setResultTransformer(new AliasToBeanResultTransformer (SapAmRiskFunMappingVO.class));
		//query.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmRiskFunMappingVO.class));	AliasToBeanResultTransformer
		return (List<SapAmRiskFunMappingVO>) query.getResultList();
	}

}
