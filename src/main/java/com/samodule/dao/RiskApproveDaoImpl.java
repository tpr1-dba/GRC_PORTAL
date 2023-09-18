package com.samodule.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Query;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmRoleMaster;
import com.samodule.model.SapAmUserRequestConflict;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.vo.SapAmRiskFunMappingVO;
import com.samodule.vo.SapAmRolesAssignVO;
import com.samodule.vo.SapAmTcodeVO;

@Repository("riskApproveDao")
public class RiskApproveDaoImpl extends JpaDao<SapAmUserRequestConflict> implements RiskApproveDao {
	static final Logger log = Logger.getLogger(RiskApproveDaoImpl.class.getName());

	
   

	@Override
	public long savAccptedRisk(SapAmRolesAssignVO sapAmRolesAssignVO,  HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		int batchSize = 10;
		int j = 0;
		Session session = currentSession();
		
		try {
			Set<Long> riskIds = sapAmRolesAssignVO.getConfilcteds().stream()
				    .map(SapAmRiskFunMappingVO::getRiskId)
				    .collect(Collectors.toSet());
			//Iterator<SapAmRiskFunMappingVO> it = sapAmRolesAssignVO.getConfilcteds().iterator();
			Iterator<Long> it =riskIds.iterator();
			while (it.hasNext()) {
				//SapAmRiskFunMappingVO sapAmRiskFunMappingVO = it.next();
				Long riskId =it.next();
				SapAmUserRequestConflict temp=new SapAmUserRequestConflict();
				temp.setApprovedBy(principal.getEmpCode());
				temp.setApprovedOn(new Date());
				//temp.setIsConflicted(sapAmRolesAssignVO.getIsSOD());
			//	temp.setPlant(sapAmRiskFunMappingVO.getPlant());
			//	temp.setPurchaseGroup(sapAmRiskFunMappingVO.getPlant());
				temp.setAceeptanceReason(sapAmRolesAssignVO.getReason());
				//temp.setRemarks(sapAmRolesAssignVO.getReason());
				temp.setRequestId(new BigDecimal(sapAmRolesAssignVO.getRequestId()));
				//temp.setR
				//temp.setSodRiskId(new BigDecimal(sapAmRiskFunMappingVO.getRiskId()));
				temp.setSodRiskId(new BigDecimal(riskId));
			//	temp.setRoleCode(sapAmRiskFunMappingVO.getRoleCode());
			//	temp.setRoleId(new BigDecimal(sapAmRiskFunMappingVO.getRoleId()));
			//	temp.setSapCompanyCode(sapAmRolesAssignVO.getSapCompanyCode());
			//	temp.setSapModuleCode(sapAmRolesAssignVO.getSapModuleCode());
				temp.setSapUserId(sapAmRolesAssignVO.getSapUserid());
			//	temp.setStatus(sapAmRolesAssignVO.getStatus());
			//	temp.setSurRecid(sapAmRiskFunMappingVO.getSurRecid());
				
				temp.setMitigationSteps("Mitigations methods are followed and maintained by CTM HOD");
				temp.setCreatedBy(principal.getEmpCode());
				temp.setCreatedOn(new Date());
				session.saveOrUpdate(temp);
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

		return j;
	}



    @Transactional
	@Override
	public List<SapAmRiskFunMappingVO> getRiskFunctionByRequestId(String requestId) throws Exception {
		// TODO Auto-generated method stub
		Session session = currentSession();
		StringBuilder queryBuilder = new StringBuilder(
				" SELECT distinct rm.rfId as rfId,   rm.riskId as riskId, rm.frDesc as frDesc,   ar.riskCode as riskCode, ar.priority as priority,	rm.module as module  ");
		queryBuilder.append(" ,rm.funId as funId, af.funCode as funCode ");
		queryBuilder.append(" FROM  SapAmRiskFunMapping rm, SapAmRisk ar, SapAmFunction af , SapAmUserRequestConflict con ");

		StringBuilder sWhere = new StringBuilder(
				" where ar.riskId=rm.riskId and af.funId=rm.funId  and rm.riskId=con.sodRiskId and con.requestId='"+requestId+"'" );
		queryBuilder.append(sWhere.toString());
		Query sqlQuery = session.createQuery(queryBuilder.toString());
		//sqlQuery.setParameter("prequestId", requestId);
		
		

		//criteria.iTotalDisplayRecords = criteria.iTotalRecords = sqlQuery.list().size();
		//sqlQuery.setMaxResults(criteria.iDisplayLength);
	//	sqlQuery.setFirstResult(criteria.iDisplayStart);
		sqlQuery.setResultTransformer(new AliasToBeanResultTransformer (SapAmRiskFunMappingVO.class));
		//query.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmRiskFunMappingVO.class));	AliasToBeanResultTransformer
		return (List<SapAmRiskFunMappingVO>) sqlQuery.getResultList();
	}


	
}
