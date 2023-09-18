package com.samodule.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
public class JpaDao<T> implements Dao<T>, Serializable {

	/**
	 * 
	 */
	protected static final long serialVersionUID = 346863361982163894L;
	private static Logger log = LoggerFactory.getLogger(JpaDao.class);

	/**
	 * entity class
	 */
	protected Class<T> entityClass;
	

//	@Autowired
//	private SessionFactory sessionFactory;
	@PersistenceContext
	EntityManager entityManager;
	
//	private HibernateTemplate hibernateTemplate;
//
//	
//	@Autowired
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		hibernateTemplate = new HibernateTemplate(sessionFactory);
//	}
	
	public Session currentSession() {
		//System.out.println(sessionFactory.getEntityManagerFactoryName());
		//Session session=sessionFactory.getCurrentSession();
		Session session = entityManager.unwrap(Session.class);
		//getAuth(session);
		return session;
    }
//	public HibernateTemplate getHibernateTemplate() {
//		return hibernateTemplate;
//	}
	@Override
	public void create(Collection<T> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(Collection<T> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(T t) {
		// TODO Auto-generated method stub

	}

	@Override
	public int update(String idName, Object idValue, String property,
			Object originalValue, Object newValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Collection<T> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public T read(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T read(Serializable identity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T readWithLock(Serializable identity) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void delete(T t) {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public void delete(Serializable identity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Collection<T> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<T> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(T t) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public Long save(T t) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
	protected void getAuth(Session session){
		 String V_STATUS = null;//"BEGIN MY_SCHEMA.HELLOWORLD2(?1); END;
		 StringBuilder queryBuilder=new StringBuilder("call dbms_application_info.set_client_info('JAVA-DSGAPP^ACTMY^MOBILEAPP')");
		 SQLQuery query= session.createSQLQuery(queryBuilder.toString());
		 int exRows = query.executeUpdate(); 
		log.info("getAuth>>>>>>>>>>>>>>");
	}

@Override
public void save(T t) throws Exception {
	// TODO Auto-generated method stub
	
}

@Override
public long delete(T t) {
	// TODO Auto-generated method stub
	return 0;
}
}
