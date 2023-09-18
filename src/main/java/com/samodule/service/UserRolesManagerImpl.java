package com.samodule.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.AssignRoleDao;
import com.samodule.dao.FunctionDao;
import com.samodule.dao.RequestTcodeDao;
import com.samodule.dao.UserRolesDao;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmRoleMaster;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.SapAmRiskFunMappingVO;
import com.samodule.vo.SapAmRolesRequestVO;
import com.samodule.vo.SapUserVO;
import com.samodule.vo.UserRequestDTO;

@Service("userRolesManager")
public class UserRolesManagerImpl implements UserRolesManager {
	@Autowired
	private UserRolesDao userRolesDao;
	@Autowired
	private RequestTcodeDao requestTcodeDao;
	@Autowired
	private FunctionDao functionDao;
	@Autowired
	private AssignRoleDao assignRoleDao;

	@Autowired
	// @Qualifier(value = "processDataService")
	private ProcessDataService processDataService;

	public List<SapUserVO> list(JQueryDataTableParamModel criteria) throws Exception {

		List<SapUserVO> sapUserVOs = userRolesDao.list(criteria);
		return sapUserVOs;
	}

	public List<SapUserVO> list(HeapUserDetails principal, JQueryDataTableParamModel criteria) throws Exception {
		List<SapUserVO> sapUserVOs = userRolesDao.list(principal, criteria);
		System.out.println(sapUserVOs.toString());
		return sapUserVOs;
	}

	@Override
	public List<SapAmRoleMaster> getRoles(String entity, JQueryDataTableParamModel criteria) throws Exception {
		// TODO Auto-generated method stub
		return userRolesDao.getRoles(entity, criteria);
	}

	@Override
	public List<SapAmRoleMaster> getRolesByTcode(String entity, SapAmRolesRequestVO sapAmRolesRequestVO,
			JQueryDataTableParamModel criteria) throws Exception {
		// TODO Auto-generated method stub

		return userRolesDao.getRolesByTcode(entity, sapAmRolesRequestVO, criteria);
	}

//	public List<SapAmRolesRequestVO> getRolesByTcodes(HeapUserDetails principal,
//			SapAmRolesRequestVO sapAmRolesRequestVO, JQueryDataTableParamModel criteria) throws Exception {
//		List<SapAmRolesRequestVO> roles = new LinkedList<>();
//		List<UserRequestDTO> tcodeDtos = requestTcodeDao.getTcodesWithWithoutFunction(
//				sapAmRolesRequestVO.getSurRecid().longValue(), sapAmRolesRequestVO.getRequestId().longValue());
//		if (tcodeDtos != null && tcodeDtos.size() > 0) {
//			Map<Boolean, List<UserRequestDTO>> mapRequestTcodeByFun = this.gourpBYFunctionId(tcodeDtos);
//
//			for (Map.Entry<Boolean, List<UserRequestDTO>> entry : mapRequestTcodeByFun.entrySet()) {
//				
//				System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
//				if (entry.getKey()) {
//					sapAmRolesRequestVO.setSurtRecids(	makeGroup(entry.getValue()));
//					roles.addAll(
//							userRolesDao.getRolesByTcodes(principal, "VW_SAP_AM_ROLES", sapAmRolesRequestVO, criteria));}
//				else {
//					sapAmRolesRequestVO.setSurtRecids(	makeGroup(entry.getValue()));
//					roles.addAll(userRolesDao.getRolesByTcodes(principal, "VW_SAP_AM_ROLESN", sapAmRolesRequestVO,
//							criteria));}
//			}
//
//		}
//		System.out.println("=======================================");
//		return roles;
//	}

	public List<SapAmRolesRequestVO> getRolesByTcodes(HeapUserDetails principal,
			SapAmRolesRequestVO sapAmRolesRequestVO, JQueryDataTableParamModel criteria) throws Exception {

		// List<UserRequestDTO> tcodeDto
		// =requestTcodeDao.getRequestedTcode(sapAmRolesRequestVO.getSurtRecid(),sapAmRolesRequestVO.getRequestId().longValue());

		System.out.println("=======================================");
		List<SapAmRolesRequestVO> rolesForTocdes = userRolesDao.getRolesByTcodes(principal, "", sapAmRolesRequestVO,
				criteria);
//		List<SapAmRolesRequestVO> roleCopyList = new ArrayList<>(rolesForTocdes);
//		Set<String> tcodes = new HashSet<>();
//		Set<Long> surtRecids = new HashSet<>();
//		Set<String> roles = new HashSet<>();
//		Set<String> roleFunctions = new HashSet<>();
//		Set<String> userAssignedfunctions = new HashSet<>();

//		userAssignedfunctions.addAll(assignRoleDao.searchFunctionByRequestId(sapAmRolesRequestVO));
//		roleFunctions.addAll(userAssignedfunctions);
//		rolesForTocdes.forEach(v -> {
//			if (v.getRoleCode() == null) {
//				tcodes.add(v.getTcode());
//				surtRecids.add(v.getSurtRecid());
//				System.out.println(v.getTcode() + " %%%%%%%%%%%%%%%%%%  " + v.getSurtRecid());
//				roleCopyList.remove(v);
//				// criteria.iTotalRecords=
//				return;
//			} else {
//				roles.add(v.getRoleCode());
//			}
//
//			try {
//			roleFunctions.addAll(functionDao.getFunctionByRole(v.getRoleCode()));	
//			List<SapAmRiskFunMappingVO> confilcteds = new ArrayList<>();
//			confilcteds.addAll(processDataService.getSourceData(roleFunctions));
//				// confilcteds.size()
//			v.setConfilctIncrese(confilcteds.size());
//				//v.setConfilctIncrese(0);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//		if (surtRecids != null && surtRecids.size() > 0) {
//			List<SapAmRolesRequestVO> rolesFor9999 = userRolesDao.getRoles9999Plant(principal, "", surtRecids,
//					criteria);
//			roleCopyList.addAll(rolesFor9999);
//		}
	
		//System.out.println(roleCopyList.size() + "  <<<<<<>>>>>>>>  " + rolesForTocdes.size());
		return  rolesForTocdes;//userRolesDao.getRolesByTcodes(principal, "", sapAmRolesRequestVO, criteria);
	}

	public List<SapAmRolesRequestVO> getRolesByRequest(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception {
		return userRolesDao.getRolesByRequest(principal, requestId, surRecid, criteria);
	}

	@Override
	public List<SapAmRolesRequestVO> getRolesByRequestEHOD(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception {
		return userRolesDao.getRolesByRequestEHOD(principal, requestId, surRecid, criteria);
	}

	@Override
	public List<SapAmRolesRequestVO> getRolesByRequestEmpCode(HeapUserDetails principal, long requestId, long surRecid,
			JQueryDataTableParamModel criteria) throws Exception {
		return userRolesDao.getRolesByRequestEmpCode(principal, requestId, surRecid, criteria);
	}

	private Map<Boolean, List<UserRequestDTO>> gourpBYFunctionId(List<UserRequestDTO> tcodeDtos) {
		Map<Boolean, List<UserRequestDTO>> groupedFunid = tcodeDtos.stream()
				.collect(Collectors.partitioningBy(a -> a.getFunId() != null));

		return groupedFunid;
	}

	private List<Long> makeGroup(List<UserRequestDTO> dtos) {
		List<Long> surtRecid = dtos.stream().map(UserRequestDTO::getSurtRecid).collect(Collectors.toList());
		return surtRecid;
	}
}
