package com.samodule;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
//import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration

@EnableTransactionManagement
//@EnableJpaRepositories("com.samodule.repository")
@PropertySource("classpath:samdatabase.properties")
public class SAModuleDataBaseConfig {

	private final String PROPERTY_DRIVER = "db.driver";
	private final String PROPERTY_URL = "db.url";
	private final String PROPERTY_USERNAME = "db.username";
	private final String PROPERTY_PASSWORD = "db.password";
	private final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
	private final String PROPERTY_DIALECT = "hibernate.dialect";
	private final String PROPERTY_FORMAT_SQL = "hibernate.format_sql";
	private final String PROPERTY_HBM2DDL = "hibernate.hbm2ddl.auto";
	private final String PROPERTY_GENERATE_STATISTICS = "hibernate.generate_statistics";
	private final String PROPERTY_SESSION_CONTEXT_CLASS = "hibernate.current_session_context_class";
	private final String PROPERTY_AUTOCOMMIT = "hibernate.connection.autocommit";

	private final String PROPERTY_USEUNICODE = "connection.useUnicode";
	private final String PROPERTY_CHARACTERENCODING = "connection.characterEncoding";
	@Autowired
	Environment environment;


	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lfb = new LocalContainerEntityManagerFactoryBean();
		lfb.setDataSource(dataSource());
		lfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lfb.setPackagesToScan("com.samodule.model");
		lfb.setJpaProperties(hibernateProps());
		System.out.println("1 ====================================================");
		return lfb;
	}

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl(environment.getProperty(PROPERTY_URL));
		ds.setUsername(environment.getProperty(PROPERTY_USERNAME));
		ds.setPassword(environment.getProperty(PROPERTY_PASSWORD));
		ds.setDriverClassName(environment.getProperty(PROPERTY_DRIVER));
		System.out.println(
				"2 ==================================================== " + environment.getProperty(PROPERTY_USERNAME));
		System.out.println(
				"2 ==================================================== " + environment.getProperty(PROPERTY_PASSWORD));
		return ds;
	}



	Properties hibernateProps() {
		Properties properties = new Properties();
		properties.setProperty(PROPERTY_DIALECT, environment.getProperty(PROPERTY_DIALECT));
		properties.setProperty(PROPERTY_SHOW_SQL, environment.getProperty(PROPERTY_SHOW_SQL));
		properties.setProperty(PROPERTY_FORMAT_SQL, environment.getProperty(PROPERTY_FORMAT_SQL));
		properties.setProperty(PROPERTY_HBM2DDL, environment.getProperty(PROPERTY_HBM2DDL));
		properties.setProperty(PROPERTY_GENERATE_STATISTICS, environment.getProperty(PROPERTY_GENERATE_STATISTICS));
		properties.setProperty(PROPERTY_SESSION_CONTEXT_CLASS, environment.getProperty(PROPERTY_SESSION_CONTEXT_CLASS));
		properties.setProperty(PROPERTY_AUTOCOMMIT, environment.getProperty(PROPERTY_AUTOCOMMIT));
		// properties.setProperty(PROPERTY_SESSION_FACTORY_NAME,
		// environment.getProperty(PROPERTY_SESSION_FACTORY_NAME));
		properties.setProperty(PROPERTY_USEUNICODE, environment.getProperty(PROPERTY_USEUNICODE));
		properties.setProperty(PROPERTY_CHARACTERENCODING, environment.getProperty(PROPERTY_CHARACTERENCODING));
		properties.put("hibernate.jdbc.batch_size", String.valueOf("10"));
	//	properties.put("hibernate.order_inserts", "true");
		//properties.put("hibernate.order_updates", "true");
		System.out.println(
				"3 ==================================================== " + environment.getProperty(PROPERTY_DIALECT));
		return properties;
	}


	@Bean
	JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		System.out.println("4 ====================================================");
		return transactionManager;
	}



}
