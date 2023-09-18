package com.samodule.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.samodule.model.HrmLogin;
import com.samodule.model.Role;
@Repository("roleDao")
public class RoleDaoImpl extends JpaDao<Role> implements RoleDao {
	static final Logger log = Logger.getLogger(RoleDaoImpl.class.getName());
	@Override
	 @Transactional
	public List<Role> getRoleByLogin(String loginId) {
		// TODO Auto-generated method stub
		log.info(loginId);		
		List<Role> roles = null;		
		Session session = currentSession();
		//session.beginTransaction();
		try {
			log.info("IF");
			Query sqlQuery = session.createQuery("from Role where empCode=:empCode");
			sqlQuery.setParameter("empCode", loginId);
			roles = (List<Role>) sqlQuery.list();
		//	session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> HrmLoginDaoJpa >> get", e);
			e.printStackTrace();
		}
//		finally{			
//			if(session!=null)
//			 session.close();
//		 }
		
		
		return roles;
	}

}
