package com.samodule.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.jackson.JsonNodeValueReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samodule.dao.AssignRoleDao;
import com.samodule.dao.EmailSenderDao;
import com.samodule.dao.MasterDataDao;
import com.samodule.dao.RequestPlantDao;
import com.samodule.dao.RequestTcodeDao;
import com.samodule.dao.SapAMWorkFLowMasterDao;
import com.samodule.dao.SapAmUserRequestApprovalDao;
import com.samodule.dao.UserRequestDao;
import com.samodule.dao.UserRolesDao;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.model.SapAmUserRequest;
import com.samodule.model.SapAmUserRequestPlant;
import com.samodule.model.SapAmUserRequestTcode;
import com.samodule.model.SapAmWorkflowMaster;
import com.samodule.security.HeapUserDetails;
import com.samodule.vo.RequestPlantDTO;
import com.samodule.vo.SapAmRequestEmpRefVO;
import com.samodule.vo.SapAmRequestRoleVO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRequestVO;
import com.samodule.vo.SapAmRolesRequestVO;
import com.samodule.vo.UserRequestDTO;

@Service("requestTcodeManager")
public class RequestTcodeManagerImpl implements RequestTcodeManager {
	@Autowired
	UserRequestDao userRequestDao;
	@Autowired
	RequestTcodeDao requestTcodeDao;
	@Autowired
	RequestPlantDao requestPlantDao;
	@Autowired
	SapAmUserRequestApprovalDao sapAmUserRequestApprovalDao;
	@Autowired
	SapAMWorkFLowMasterDao sapAMWorkFLowMasterDao;
	@Autowired
	UserRolesDao userRolesDao;
	@Autowired
	MasterDataDao masterDataDao;
	@Autowired
	EmailSenderDao emailSenderDao;
	@Autowired
	AssignRoleDao assignRoleDao;

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public long createTcodeRequest(SapAmRequestVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		SapAmUserRequest sapAmUserRequest = modelMapper.map(sapAmRequestVO, SapAmUserRequest.class);
		userRequestDao.saveRequest(sapAmUserRequest, principal);
		sapAmRequestVO.setRequestId(sapAmUserRequest.getRequestId());
		sapAmRequestVO.setSurRecid(sapAmUserRequest.getSurRecid());
		this.saveTocdeRequest(sapAmRequestVO, principal);
		return 0;
	}

	public long saveTocdeRequest(SapAmRequestVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub

		if (sapAmRequestVO.getTcode() != null && sapAmRequestVO.getTcode().trim().length() > 0) {
			List<SapAmUserRequestTcode> amUserRequestTcodes = this.prepareTcodeModel(sapAmRequestVO);
			requestTcodeDao.saveTcodes(amUserRequestTcodes, principal);
		}
		if ((sapAmRequestVO.getPlant() != null && sapAmRequestVO.getPlant().trim().length() > 3)
				&& ((sapAmRequestVO.getPurchaseGroup() != null
						&& !sapAmRequestVO.getPurchaseGroup().trim().isEmpty()))) {
			List<String> plants = this.preparePlantModel(sapAmRequestVO);
			List<String> pgroups = this.preparePurchaseGroupModel(sapAmRequestVO);
			List<SapAmUserRequestPlant> amUserRequestPlants = this.zipPlantPGroup(plants, pgroups, sapAmRequestVO);
			requestPlantDao.savePlants(amUserRequestPlants, principal);
		} else if (sapAmRequestVO.getPlant() != null && sapAmRequestVO.getPlant().trim().length() > 3) {
			List<String> plants = this.preparePlantModel(sapAmRequestVO);
			// List<String> pgroups = this.preparePurchaseGroupModel(sapAmRequestVO);
			List<SapAmUserRequestPlant> amUserRequestPlants = this.zipPlantPGroup(plants, null, sapAmRequestVO);
			requestPlantDao.savePlants(amUserRequestPlants, principal);
		} else if (sapAmRequestVO.getPurchaseGroup() != null && !sapAmRequestVO.getPurchaseGroup().trim().isEmpty()) {

			List<String> pgroups = this.preparePurchaseGroupModel(sapAmRequestVO);
			List<SapAmUserRequestPlant> amUserRequestPlants = this.zipPlantPGroup(null, pgroups, sapAmRequestVO);
			requestPlantDao.savePlants(amUserRequestPlants, principal);
		}
//		if ((plants != null && plants.size() > 0) && (pgroups != null && pgroups.size() > 0)) {
//               
//		} else {
//			if ((pgroups != null && pgroups.size() > 0)) {
//
//			}
//			if ((plants != null && plants.size() > 0)) {
//
//			}
//		}

		return 0;

	}

	@Override
	@Transactional
	public long createRoleRequest(SapAmRequestRoleVO sapAmRequestRoleVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		SapAmUserRequest sapAmUserRequest = modelMapper.map(sapAmRequestRoleVO, SapAmUserRequest.class);
		sapAmUserRequest.setReason(sapAmRequestRoleVO.getReasonrole());
		userRequestDao.saveRequest(sapAmUserRequest, principal);
		sapAmRequestRoleVO.setRequestId(sapAmUserRequest.getRequestId());
		sapAmRequestRoleVO.setSurRecid(sapAmUserRequest.getSurRecid());
		this.saveRoleRequest(sapAmRequestRoleVO, principal);
		return 0;
	}

	public long saveRoleRequest(SapAmRequestRoleVO sapAmRequestRoleVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub

		List<SapAmUserRequestPlant> sapAmUserRequestPlant = this.prepareRoleModel(sapAmRequestRoleVO);
		// requestTcodeDao.saveTcodes(SapAmUserRequestTcodes, principal);
		requestPlantDao.savePlants(sapAmUserRequestPlant, principal);
		return 0;

	}

	public List<String> preparePlantModel(SapAmRequestVO sapAmRequestVO) throws Exception {
		// TODO Auto-generated method stub

		Supplier<List<String>> supplier = () -> new LinkedList<String>();
		if (sapAmRequestVO.getPlant() != null && !sapAmRequestVO.getPlant().trim().isEmpty()) {
			return Arrays.stream(sapAmRequestVO.getPlant().split(",")).map(a -> a.toString())
					.collect(Collectors.toCollection(LinkedList<String>::new));
			// List<String> companies =
			// Arrays.stream(sapCompany.split(",")).map(a->a.toString()).collect(Collectors.toList());
		}

		return null;

	}

	public List<String> preparePurchaseGroupModel(SapAmRequestVO sapAmRequestVO) throws Exception {
		// TODO Auto-generated method stub

		Supplier<List<String>> supplier = () -> new LinkedList<String>();

		if (sapAmRequestVO.getPurchaseGroup() != null && !sapAmRequestVO.getPurchaseGroup().trim().isEmpty()) {
			return Arrays.stream(sapAmRequestVO.getPurchaseGroup().split(",")).map(a -> a.toString())
					.collect(Collectors.toCollection(supplier));
		}

		return null;

	}

	public List<SapAmUserRequestTcode> prepareTcodeModel(SapAmRequestVO sapAmRequestVO) throws Exception {
		// TODO Auto-generated method stub
		Supplier<List<String>> supplier = () -> new LinkedList<String>();
		if (sapAmRequestVO.getTcodeId() != null && !sapAmRequestVO.getTcodeId().trim().isEmpty()) {
			List<String> tcodeId = Arrays.stream(sapAmRequestVO.getTcodeId().split(",")).map(a -> a.toString())
					.collect(Collectors.toCollection(supplier));
			List<String> tcode = Arrays.stream(sapAmRequestVO.getTcode().split(",")).map(a -> a.toString())
					.collect(Collectors.toCollection(supplier));
			return this.zipJavaTocde(tcodeId, tcode, sapAmRequestVO);
		}
		return null;
	}

	public List<SapAmUserRequestPlant> prepareRoleModel(SapAmRequestRoleVO sapAmRequestRoleVO) throws Exception {
		// TODO Auto-generated method stub

		Supplier<List<String>> supplier = () -> new LinkedList<String>();
		if (sapAmRequestRoleVO.getRoleId() != null && !sapAmRequestRoleVO.getRoleId().trim().isEmpty()) {
			List<String> roleId = Arrays.stream(sapAmRequestRoleVO.getRoleId().split(",")).map(a -> a.toString())
					.collect(Collectors.toCollection(supplier));
			List<String> roleCode = Arrays.stream(sapAmRequestRoleVO.getRoleCode().split(",")).map(a -> a.toString())
					.collect(Collectors.toCollection(supplier));
			return this.zipJavaRole(roleId, roleCode, sapAmRequestRoleVO);
		}
		return null;
	}
//	public List<SapAmUserRequestPlant> prepareRoleModelv2(SapAmRequestRoleVO sapAmRequestRoleVO) throws Exception {
//		// TODO Auto-generated method stub
//
//		Supplier<List<String>> supplier = () -> new LinkedList<String>();
//		if (sapAmRequestRoleVO.getRoleId() != null && !sapAmRequestRoleVO.getRoleId().trim().isEmpty()) {
//			List<String> roleId = Arrays.stream(sapAmRequestRoleVO.getRoleId().split(",")).map(a -> a.toString())
//					.collect(Collectors.toCollection(supplier));
//			List<String> roleCode = Arrays.stream(sapAmRequestRoleVO.getRoleCode().split(",")).map(a -> a.toString())
//					.collect(Collectors.toCollection(supplier));
//			return this.zipJavaRole(roleId, roleCode, sapAmRequestRoleVO);
//		}
//		return null;
//	}

	public long saveRequest(SapAmRequestVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub

		// SapAmRoleMaster amRoleMaster =
		// this.createsapMasterRoleDefination(sapamrolemastervo);
		// long roleId = masterRoleDao.createsapMasterRole(amRoleMaster, principal);

//		if (roleId > 0 && (sapamrolemastervo.getSatIds() != null && !sapamrolemastervo.getSatIds().trim().isEmpty())
//				&& (sapamrolemastervo.getTcodes() != null && !sapamrolemastervo.getTcodes().trim().isEmpty())) {
//
//			String[] tcodeName = sapamrolemastervo.getTcodes().split(",");
//			String[] tcodeRecids = sapamrolemastervo.getSatIds().split(",");
//			List<SapAmRoleTcodeMapping> amRoleTcodeMappings = this.zipJava8(Arrays.asList(tcodeName),
//					Arrays.asList(tcodeRecids), roleId);
//			return tcodeInfoDao.saveRoleTcodeMappings(amRoleTcodeMappings, principal, "M", new BigDecimal(roleId));
//
//		}
		return 0;

	}

	// .getRequestId(),sapAmRequestVO.getSurRecid()
	private List<SapAmUserRequestTcode> zipJavaTocde(List<String> tcodeIds, List<String> tcodes,
			SapAmRequestVO sapAmRequestVO) {
		return IntStream.range(0, Math.min(tcodes.size(), tcodeIds.size()))
				.mapToObj(i -> new SapAmUserRequestTcode(sapAmRequestVO.getRequestId(),
						new BigDecimal(sapAmRequestVO.getSurRecid()), tcodes.get(i), new BigDecimal(tcodeIds.get(i)),
						sapAmRequestVO.getReason()))
				.collect(Collectors.toList());
	}

	private List<SapAmUserRequestPlant> zipJavaRole(List<String> roleIds, List<String> roleCodes,
			SapAmRequestRoleVO sapAmRequestRoleVO) {
		return IntStream.range(0, Math.min(roleIds.size(), roleCodes.size()))
				// BigDecimal requestId, BigDecimal roleId, String roleCode, BigDecimal surRecid
				.mapToObj(i -> new SapAmUserRequestPlant(sapAmRequestRoleVO.getRequestId(),
						new BigDecimal(roleIds.get(i)), roleCodes.get(i),
						new BigDecimal(sapAmRequestRoleVO.getSurRecid()), sapAmRequestRoleVO.getReasonrole()))
				.collect(Collectors.toList());
	}

	private List<SapAmUserRequestPlant> zipPlantPGroup(List<String> plants, List<String> pgroups,
			SapAmRequestVO sapAmRequestVO) {
		// String plant, String purchaseGroup, BigDecimal requestId, BigDecimal surRecid
		return IntStream
				.range(0,
						Math.max((plants != null && plants.size() > 0) ? plants.size() : 0,
								(pgroups != null && pgroups.size() > 0) ? pgroups.size() : 0))
				// BigDecimal requestId, BigDecimal roleId, String roleCode, BigDecimal surRecid
				.mapToObj(i -> new SapAmUserRequestPlant((plants != null && i < plants.size()) ? plants.get(i) : null,
						(pgroups != null && i < pgroups.size()) ? pgroups.get(i) : null, sapAmRequestVO.getRequestId(),
						new BigDecimal(sapAmRequestVO.getSurRecid()), sapAmRequestVO.getReason()))
				.collect(Collectors.toList());
	}

	@Override
	public List<UserRequestDTO> getRequestedByUserID(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception {
		return requestTcodeDao.getRequestedByUserID(criteria, principal);
	}

	@Override
	public List<UserRequestDTO> getRequestedByUserID(HeapUserDetails principal) throws Exception {
		return requestTcodeDao.getRequestedByUserID(principal);
	}

	@Override
	public List<UserRequestDTO> getRequestedRolesUserID(JQueryDataTableParamModel criteria, HeapUserDetails principal)
			throws Exception {
		return userRequestDao.getRequestedRolesUserID(criteria, principal);
	}

	@Override
	public List<RequestPlantDTO> getRequestedPlant(Long surRecid, Long requestId, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception {
		return requestPlantDao.getRequestedPlant(surRecid, requestId, criteria, principal);
	}

	@Override
	public List<RequestPlantDTO> getRequestedPlantForRequest(Long surRecid, Long requestId,
			JQueryDataTableParamModel criteria, HeapUserDetails principal) throws Exception {
		return requestPlantDao.getRequestedPlantForRequest(surRecid, requestId, criteria, principal);
	}

	@Override
	public List<UserRequestDTO> getRequestedTcode(Long surRecid, Long requestId, JQueryDataTableParamModel criteria,
			HeapUserDetails principal) throws Exception {
		return requestTcodeDao.getRequestedTcode(surRecid, requestId, criteria, principal);
	}

	@Override
	public List<UserRequestDTO> getRequestedTcodeByRequestID(Long surRecid, Long requestId,
			JQueryDataTableParamModel criteria, HeapUserDetails principal) throws Exception {
		return requestTcodeDao.getRequestedTcodeByRequestID(surRecid, requestId, criteria, principal);
	}

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public Map<String, String> submitRequest(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal)
			throws Exception {
		// TODO Auto-generated method stub

		// userRequestDao.submitRequest(sapAmRequestVO, principal);
		ModelMapper modelMapper = new ModelMapper();
		// modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
				.addValueReader(new JsonNodeValueReader());
		SapAmUserRequest sapAmUserRequest = modelMapper.map(sapAmRequestVO, SapAmUserRequest.class);
		List<UserRequestDTO> requestTcodeDTOs = requestTcodeDao
				.getRequestedTcodesWithPlant(sapAmRequestVO.getSurRecid(), sapAmRequestVO.getRequestId().longValue());

		Set<String> modules = new HashSet<>();
		Set<String> plants = new HashSet<>();
		Set<String> tcodes = new HashSet<>();
		Map<String, String> tcodeMap = new HashMap<>();
		Map<String, String> empRoleTodeMap = new HashMap<>();
		requestTcodeDTOs.forEach(t -> {
			modules.add(t.getModule());
			plants.add(t.getPlant());
			if (t.getRoleCode() == null && t.getPlant() != null) {
				// if (t.getRoleCode() == null) {
				tcodes.add(t.getTcode());
			}
			if (t.getEmpRoleCode() != null && !t.getEmpRoleCode().trim().isEmpty()) {
				if (empRoleTodeMap.containsKey(t.getTcode())) {
					StringBuilder roles = new StringBuilder(empRoleTodeMap.get(t.getTcode()));
					roles.append(", ");
					roles.append(t.getEmpRoleCode());
					empRoleTodeMap.put(t.getTcode(), roles.toString());
				} else {
					empRoleTodeMap.put(t.getTcode(), t.getEmpRoleCode());
				}
			}
		});
//temprory 05062023
//		if (empRoleTodeMap != null && empRoleTodeMap.size() > 0) {
////			String resultt = StringUtils.join(tcodes, ",");
////			String resultp = StringUtils.join(plants, ",");
////			tcodeMap.put(resultt, resultp);
//			empRoleTodeMap.put("role", "");
//			return empRoleTodeMap;
//		}

		if (tcodes != null && tcodes.size() > 0) {
			String resultt = StringUtils.join(tcodes, ",");
			String resultp = StringUtils.join(plants, ",");
			tcodeMap.put(resultt, resultp);
			tcodeMap.put("tcode", "");
			return tcodeMap;
		}
		List<UserRequestDTO> temp = requestTcodeDTOs.stream().filter(distinctByKey(UserRequestDTO::getSurtRecid))
				.collect(Collectors.toList());
		List<String> compenies = "ALL".equalsIgnoreCase(requestTcodeDTOs.get(0).getSapCompanyCode())
				? Arrays.asList("1000", "2000", "3000")
				: Arrays.asList(requestTcodeDTOs.get(0).getSapCompanyCode());
		System.out.println(plants.toString());
		List<String> sbus = masterDataDao.getSapPlantSbus(plants);
		System.out.println(sbus);
		List<SapAmWorkflowMaster> amWorkflowMasters = sapAMWorkFLowMasterDao.getWorkFLowsForSBUSQL(modules,
				new HashSet<>(compenies), sbus, principal);
		System.out.println(amWorkflowMasters);

		if (amWorkflowMasters != null && amWorkflowMasters.size() > 0) {
			sapAmUserRequestApprovalDao.save(amWorkflowMasters, sapAmRequestVO, principal);
			// requestTcodeDao.submitRequests(sapAmRequestVO, principal);
			requestTcodeDao.submitRequests(temp, sapAmRequestVO, principal);
			requestPlantDao.submitRequest(sapAmRequestVO, principal);
			userRequestDao.submitRequest(sapAmUserRequest, principal);
			emailSenderDao.sentEmail(sapAmRequestVO.getRequestId(), "S", "S", 1, principal.getEmpCode(), 1);
			tcodeMap.put("5", "");
			return tcodeMap;
		} else {
			tcodeMap.put("0", "");
			return tcodeMap;
		}
	}

	public static List<SapAmWorkflowMaster> findByCodeIsIn(Collection<SapAmWorkflowMaster> listCarnet, String codeIsIn,BigDecimal wfLevel) {
	   // return listCarnet.stream().filter(carnet -> codeIsIn.equals(carnet.getCodeIsin())).findFirst().orElse(null);
	    return listCarnet.stream().filter(carnet -> codeIsIn.equals(carnet.getEmpCode())).map(a->{a.setWfLevel(wfLevel);return a;}).collect(Collectors.toList());
	}
	
	public static Map<String, SapAmWorkflowMaster> finMaxWFlevel(Collection<SapAmWorkflowMaster> listCarnet){
		Map<String, SapAmWorkflowMaster> maxWfLevel = listCarnet.stream()
			    .collect(Collectors.toMap(SapAmWorkflowMaster::getEmpCode, Function.identity(),
			        BinaryOperator.maxBy(Comparator.comparing(SapAmWorkflowMaster::getWfLevel))));
//		Map<String, BigDecimal> maxWfLevel = listCarnet.stream()
//				.collect(Collectors.toMap(a->(String)a.getEmpCode(), a->(String)a.getWfLevel(),
//						BinaryOperator.maxBy(Comparator.comparing(SapAmWorkflowMaster::getWfLevel))));
			        //  mostExpensives.forEach((make,car) -> System.out.println(make+" "+car));
	  return maxWfLevel;
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public Map<String, String> submitRequestRole(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal)
			throws Exception {
		// TODO Auto-generated method stub

		// userRequestDao.submitRequest(sapAmRequestVO, principal);
		ModelMapper modelMapper = new ModelMapper();
		// modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
				.addValueReader(new JsonNodeValueReader());
		SapAmUserRequest sapAmUserRequest = modelMapper.map(sapAmRequestVO, SapAmUserRequest.class);

		List<SapAmRolesRequestVO> requestTcodeDTOs = userRolesDao.getRolesModules(sapAmRequestVO);// requestTcodeDao.getRequestedTcodes(sapAmRequestVO.getSurRecid(),sapAmRequestVO.getRequestId().longValue());
		Map<String, String> tcodeMap = new HashMap<>();
		Set<String> modules = new HashSet<>();
		// Set<String> compenies = new HashSet<>(Arrays.asList("1000", "2000", "3000"));
		Set<String> compenies = new HashSet<>();
		Set<String> plants = new HashSet<>();
		for (SapAmRolesRequestVO sapAmRolesRequestVO : requestTcodeDTOs) {
			modules.add(sapAmRolesRequestVO.getModule());
			compenies.add(sapAmRolesRequestVO.getEntity());
			plants.add(sapAmRolesRequestVO.getPlant());
		}
		List<String> sbus = masterDataDao.getSapPlantSbus(plants);
		System.out.println(sbus);
		List<SapAmWorkflowMaster> amWorkflowMasters = sapAMWorkFLowMasterDao.getWorkFLowsForSBUSQL(modules,
				new HashSet<>(compenies), sbus, principal);
//		List<SapAmWorkflowMaster> amWorkflowMasters = sapAMWorkFLowMasterDao.getWorkFLows(modules, compenies,
//				principal);
		System.out.println("EBFORE IF");
		if (amWorkflowMasters != null && amWorkflowMasters.size() > 0) {
			sapAmUserRequestApprovalDao.save(amWorkflowMasters, sapAmRequestVO, principal);
			userRequestDao.submitRequest(sapAmUserRequest, principal);
			// sapAMWorkFLowMasterDao.getWorkFLows();
			// requestTcodeDao.submitRequest(sapAmRequestVO, principal);

		    requestPlantDao.submitRequestRole(requestTcodeDTOs, sapAmRequestVO.getStatus(), principal);
			emailSenderDao.sentEmail(sapAmRequestVO.getRequestId(), "S", "S", 1, principal.getEmpCode(), 1);
			tcodeMap.put("5", "");
			return tcodeMap;
		} else {
			System.out.println("ELSE");
			tcodeMap.put("0", "");
			return tcodeMap;
		}
	}

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public long deleteTcodes(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub

		// userRequestDao.submitRequest( sapAmRequestVO, principal);

		requestTcodeDao.deleteRequest(sapAmRequestVO, principal);

		return 0;

	}

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public long deletePlantsPurchegroup(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal)
			throws Exception {
		// TODO Auto-generated method stub

		requestPlantDao.deleteRequest(sapAmRequestVO, principal);
		return 0;

	}

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public long updateTcodes(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal) throws Exception {
		// TODO Auto-generated method stub

		// userRequestDao.submitRequest( sapAmRequestVO, principal);

		requestTcodeDao.updateStatus(sapAmRequestVO, principal);

		return 0;

	}

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public long updateplantsPurchegroup(SapAmRequestStatusVO sapAmRequestVO, HeapUserDetails principal)
			throws Exception {
		// TODO Auto-generated method stub

		requestPlantDao.updateStatus(sapAmRequestVO, principal);
		return 0;

	}

	@Override
	@Transactional(rollbackOn = Throwable.class)
	public  Map<String, String> submitRequestEmpRef(SapAmRequestEmpRefVO sapAmRequestEmpRefVO, HeapUserDetails principal)
			throws Exception {
		// TODO Auto-generated method stub

		// userRequestDao.submitRequest(sapAmRequestVO, principal);
		Map<String, String> tcodeMap = new HashMap<>();
		List<SapAmRolesRequestVO> requestTcodeDTOs = assignRoleDao.searchRolesByUserID(sapAmRequestEmpRefVO);// requestTcodeDao.getRequestedTcodes(sapAmRequestVO.getSurRecid(),sapAmRequestVO.getRequestId().longValue());
		Set<String> modules = new HashSet<>();
		Set<String> plants = new HashSet<>();
		List<String> compenies = "ALL".equalsIgnoreCase(sapAmRequestEmpRefVO.getSapCompanyCode())
				? Arrays.asList("1000", "2000", "3000")
				: Arrays.asList(sapAmRequestEmpRefVO.getSapCompanyCode());
		for (SapAmRolesRequestVO sapAmRolesRequestVO : requestTcodeDTOs) {
			modules.add(sapAmRolesRequestVO.getModule());
			plants.add(sapAmRolesRequestVO.getPlant());
		}
		System.out.println("SIZE     ::    " + requestTcodeDTOs.size());
		List<String> sbus = masterDataDao.getSapPlantSbus(plants);
		System.out.println(sbus);
		List<SapAmWorkflowMaster> amWorkflowMasters = sapAMWorkFLowMasterDao.getWorkFLowsForSBUSQL(modules,
				new HashSet<>(compenies), sbus, principal);
		if (amWorkflowMasters != null && amWorkflowMasters.size() > 0) {
			ModelMapper modelMapper = new ModelMapper();
			// modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
					.addValueReader(new JsonNodeValueReader());
			SapAmUserRequest sapAmUserRequest = modelMapper.map(sapAmRequestEmpRefVO, SapAmUserRequest.class);

			// sapAmUserRequest.setReason(sapAmRequestEmpRefVO.getReason());
			userRequestDao.saveRequestEmpRef(sapAmUserRequest, principal);
			SapAmRequestStatusVO sapAmRequestVO = new SapAmRequestStatusVO();
			sapAmRequestVO.setRequestId(sapAmUserRequest.getRequestId());
			sapAmRequestVO.setSurRecid(sapAmUserRequest.getSurRecid());
			sapAmRequestVO.setStatus("S");
			// sapAmRequestVO.setReason(sapAmRequestEmpRefVO.getReason());

			sapAmUserRequestApprovalDao.save(amWorkflowMasters, sapAmRequestVO, principal);
			// userRequestDao.submitRequest(sapAmUserRequest, principal);
			// sapAMWorkFLowMasterDao.getWorkFLows();
			// requestTcodeDao.submitRequest(sapAmRequestVO, principal);

			requestPlantDao.submitRequestRoleEmpRef(requestTcodeDTOs, sapAmRequestVO, principal);
			emailSenderDao.sentEmail(sapAmRequestVO.getRequestId(), "S", "S", 1, principal.getEmpCode(), 1);
			tcodeMap.put("5", "");
			return tcodeMap;
		} else {
			System.out.println("ELSE");
			tcodeMap.put("0", "");
			return tcodeMap;
		}
	}
	
	@Override
	public String updateSapAmUserEmpCodeBySwmRecid(long swmRecid, String empCode, HeapUserDetails principal) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(empCode)) {
			return "Invalid Empcode";
		}
		if(!StringUtils.isNumeric(String.valueOf(swmRecid)) || swmRecid<1) {
			return "Invalid SwmRecid";
		}
		return sapAmUserRequestApprovalDao.updateSapAmUserEmpCodeBySwmRecid(swmRecid, empCode, principal);
	}
}
