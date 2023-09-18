package com.samodule.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.samodule.model.SapAmTcode;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.vo.SapAmTcodeVO;

@Repository("samTcodeDao")
public class SAMTcodeDaoImpl extends JpaDao<SapAmTcode> implements SAMTcodeDao {
	static final Logger log = Logger.getLogger(SAMTcodeDaoImpl.class.getName());



	
    @Transactional
	@Override
	public List<SapAmTcodeVO> getTocdeByMasterRole(SapAmTcodeVO amTcodeVO) {
		// TODO Auto-generated method stub
		
		Session session = currentSession();
		
		try {

			StringBuilder quBuilder = new StringBuilder(
					" SELECT    st.sat_Id as satId,   st.tcode as tcode, st.tcode_Desc as tcodeDesc, st.sensitive as sensitive FROM SAP_AM_TCODES st join SAP_AM_ROLE_TCODE_MAPPING stm");
			// StringBuilder rowBuilder = new StringBuilder(" SELECT count(satId) FROM
			// SapAmTcode ");
			StringBuilder sWhere = new StringBuilder(
					" on st.sat_Id=stm.tcode_id and st.tcode=stm.tcode where stm.role_Id=:pmrole and st.status='A'  order  by  st.sensitive");
//			StringBuilder sWhere = new StringBuilder(
//					" on  st.tcode=stm.tcode where stm.role_Id=:pmrole and st.status='A' ");

		
			quBuilder.append(sWhere);
			
			System.out.println(quBuilder.toString());
			 System.out.println(amTcodeVO.getRoleId());
			 System.out.println(amTcodeVO.getRoleCode());

			Query sqlQuery = session.createSQLQuery(quBuilder.toString())
					.addScalar("satId", StandardBasicTypes.LONG)					
					.addScalar("tcode", StandardBasicTypes.STRING)					
					.addScalar("sensitive", StandardBasicTypes.STRING)					
					.addScalar("tcodeDesc", StandardBasicTypes.STRING);
			sqlQuery.setParameter("pmrole", amTcodeVO.getRoleId());

			
	
			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(SapAmTcodeVO.class));
			
			return (List<SapAmTcodeVO>) sqlQuery.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> TcodeInfoDaoImpl >> getTcode", e);
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();

		}
		return null;

	}
	
}
