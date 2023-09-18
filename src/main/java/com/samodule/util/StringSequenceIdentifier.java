/*package com.samodule.util;

import java.io.Serializable;

import java.util.Properties;

import javax.persistence.metamodel.IdentifiableType;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;


public class StringSequenceIdentifier implements 
IdentifierGenerator, Configurable {
    public static final String SEQUENCE_PREFIX = "sequence_prefix";

	private String sequencePrefix;

	private String sequenceCallSyntax;
	@Override
	public void configure(org.hibernate.type.Type type, Properties params,
			Dialect d) throws MappingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		// TODO Auto-generated method stub
		 if (object instanceof IdentifiableType) {
			 IdentifiableType identifiable = (IdentifiableType) object;
	            Serializable id = identifiable.;

	            if (id != null) {
	                return id;
	            }
	        }

	        long seqValue = ((Number)
	                Session.class.cast(session)
	                .createNativeQuery(sequenceCallSyntax)
	                .uniqueResult()
	        ).longValue();

	        return sequencePrefix + String.format("%011d%s", 0 ,seqValue);
	    }
	}


}
*/