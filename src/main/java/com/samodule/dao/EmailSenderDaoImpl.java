package com.samodule.dao;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.samodule.model.Role;
@Repository("emailSenderDao")
public class EmailSenderDaoImpl extends JpaDao<Role> implements EmailSenderDao {
	static final Logger log = Logger.getLogger(EmailSenderDaoImpl.class.getName());
	@Override
	 //@Transactional (8520233,'A','A',2,'5500189',1
	public void sentEmail(BigDecimal requestId,String status1,String status2,int cwfLevel, String empCode,int wfLevel) throws Exception{
		// TODO Auto-generated method stub
		log.info(requestId);		
				
		Session session = currentSession();
		//session.beginTransaction();
		try {
			StringBuilder queryBuilder=new StringBuilder("CALL SAP_AM_MAIL_PACKAGE.SAP_AM_FILLED_ALERT ");// 
	          
	           queryBuilder.append(" ("+requestId+",'"+status1+"','"+status2+"',"+cwfLevel+",'"+empCode +"',"+wfLevel+")");
	                           //  queryBuilder.append(" ('"+claimBillDTO.getCOMPANY()+"','"+claimBillDTO.getHRC_RECID()+"')"); 
	          SQLQuery query= session.createSQLQuery(queryBuilder.toString());	                  
	          int exRows = query.executeUpdate(); 
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmLoginDaoJpa >> get", e);
			e.printStackTrace();
			throw e;
		}

	}

}
