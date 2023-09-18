package com.samodule.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.samodule.model.SapAmUserRequest;
import com.samodule.security.HeapUserDetails;
import com.samodule.util.AliasToBeanNestedResultTransformer;
import com.samodule.util.DateUtil;
import com.samodule.vo.ReportDataDto;
import com.samodule.vo.ReportRequestDto;

@Repository("ReportDataDao")
public class ReportDataDaoImpl extends JpaDao<SapAmUserRequest> implements ReportDataDao {

	static final Logger log = Logger.getLogger(ReportDataDaoImpl.class.getName());

	@Transactional
	@Override
	public List<ReportDataDto> getReportDataList(ReportRequestDto reqDto, HeapUserDetails principal) throws Exception {
		Session session = currentSession();
		try {
			
			StringBuilder queryBuilder = new StringBuilder("select ur.request_id as requestId, "
					+ "(select EMPLOYEE_NAME from hrm_employee where emp_code= ur.emp_code) as requesterName, "
					+ "ur.SAP_USER_ID as requesterSapId, "
					+ "(select EMPLOYEE_NAME from hrm_employee where emp_code= ra.APPROVED_BY) as finalApprovedBasis, "
					+ "ra.Remarks as approvalRemarks, ur.reason as requestReason, rt.tcode as requestTcode, null as metigationYn,  "
					//+ "DECODE(ra.is_hod, 'Y', 'Yes','N', 'No') as metigationYn,  "
					+ "DECODE(ur.status, 'S', 'Pending','A', 'Approved','C', 'Closed','R','Rejected','Initiated') as status,  "
					+ "(CASE WHEN ur.status ='S' THEN (trunc(sysdate) - TO_DATE(UR.created_on, 'DD-MM-YY')) ELSE 0 END) as pendingDueDays  "
					+ "from SAP_AM_USER_REQUESTS ur join SAP_AM_USER_REQUEST_APPROVALS ra on ur.request_id=ra.request_id "
					+ "join SAP_AM_USER_REQUEST_TCODES rt on ur.request_id=rt.request_id ");

			 queryBuilder.append("where ur.status ='S' ");

			if (!StringUtils.isEmpty(reqDto.getFromDate()) && !StringUtils.isEmpty(reqDto.getToDate())) {
				queryBuilder.append("where TO_DATE(ur.created_on, 'DD-MM-YY') between TO_DATE(:fromDate, 'DD-MM-YY') AND TO_DATE(:toDate, 'DD-MM-YY') ");
			}

			if (!StringUtils.isEmpty(reqDto.getModule())) {
				queryBuilder.append("and ur.sap_module_code=:module ");
			}

			if (!StringUtils.isEmpty(reqDto.getSapCompCode())) {
				queryBuilder.append("and ur.sap_company_code=:sapCompCode ");
			}
			
			//if (!StringUtils.isEmpty(reqDto.getRequestId())) {
			//	queryBuilder.append("and ur.sap_company_code=:requestId ");
			//}

			queryBuilder.append("ORDER BY ur.created_on DESC");
			System.out.println(queryBuilder.toString());

			Query sqlQuery = session.createSQLQuery(queryBuilder.toString())
					.addScalar("requestId", StandardBasicTypes.LONG)
					.addScalar("requesterName", StandardBasicTypes.STRING)
					.addScalar("requesterSapId", StandardBasicTypes.STRING)
					.addScalar("finalApprovedBasis", StandardBasicTypes.STRING)
					.addScalar("approvalRemarks", StandardBasicTypes.STRING)
					.addScalar("requestReason", StandardBasicTypes.STRING)
					.addScalar("requestTcode", StandardBasicTypes.STRING)
					.addScalar("metigationYn", StandardBasicTypes.STRING)
					.addScalar("status", StandardBasicTypes.STRING)
					.addScalar("pendingDueDays", StandardBasicTypes.INTEGER);

			if (!StringUtils.isEmpty(reqDto.getFromDate()) && !StringUtils.isEmpty(reqDto.getToDate())) {
				sqlQuery.setParameter("fromDate", DateUtil.reportDateConvert(reqDto.getFromDate()));
				sqlQuery.setParameter("toDate",  DateUtil.reportDateConvert(reqDto.getToDate()));
			}

			if (!StringUtils.isEmpty(reqDto.getModule())) {
				sqlQuery.setParameter("module", reqDto.getModule());
			}

			if (!StringUtils.isEmpty(reqDto.getSapCompCode())) {
				sqlQuery.setParameter("sapCompCode", reqDto.getSapCompCode());
			}

			sqlQuery.setResultTransformer(new AliasToBeanNestedResultTransformer(ReportDataDto.class));
			return (List<ReportDataDto>) sqlQuery.getResultList();

		} catch (Exception e) {
			// TODO: handle exception
			log.error("ERROR >> ReportDataDaoImpl >> getReportDataList", e);
			e.printStackTrace();
		}
		return null;

	}
}
