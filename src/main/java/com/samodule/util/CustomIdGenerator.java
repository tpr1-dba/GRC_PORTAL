package com.samodule.util;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

public class CustomIdGenerator extends IdentityGenerator {

	@Override
//	public Serializable generate(SessionImplementor session, Object object)
//			throws HibernateException {
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Map.Entry<Object, EntityEntry>[] entities = ((SessionImplementor) session).getPersistenceContext()
				.reentrantSafeEntityEntries();
		Table table = object.getClass().getAnnotation(Table.class);
		String tableName = table.name();
		System.out.println("tableName          " + tableName);
		Connection connection = session.connection();

		try {

			Statement statement = connection.createStatement();

			ResultSet rs = statement
					.executeQuery(" select max(" + this.getPKColumnName(object.getClass()) + ")  from  " + tableName);

			Long id = 0L;
			if (rs.next()) {
				System.out.println("ID  +++++++++++++++++++++++++++   " + rs.getInt(1));
				id = rs.getLong(1);
			}
			super.generate(session, id + 1);
			return id + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	public static String getPKColumnName(Class<?> pojo) {

		if (pojo == null)
			return null;

		String name = null;

		for (Field f : pojo.getDeclaredFields()) {

			Id id = null;
			Column column = null;

			Annotation[] as = f.getAnnotations();
			for (Annotation a : as) {
				if (a.annotationType() == Id.class)
					id = (Id) a;
				else if (a.annotationType() == Column.class)
					column = (Column) a;
			}

			if (id != null && column != null) {
				name = column.name();
				break;
			}
		}

		if (name == null && pojo.getSuperclass() != Object.class)
			name = getPKColumnName(pojo.getSuperclass());

		return name;
	}

}
