package com.samodule.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samodule.datatable.ControllerUtils;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.datatable.ResultSet;
import com.samodule.datatable.TableParam;
import com.samodule.model.SapAmMasterTypeDetail;
import com.samodule.security.HeapUserDetails;
import com.samodule.service.MasterDataService;
import com.samodule.vo.HrmLoginVO;
import com.samodule.vo.PlantVO;
import com.samodule.vo.SapAmRiskFunMappingVO;
import com.samodule.vo.SapAmRolesRequestVO;
import com.samodule.vo.SapAmTcodeVO;
import com.samodule.vo.SapUserVO;

@Controller
public class MasterDataController {
	private static final Logger logger = LoggerFactory.getLogger(MasterDataController.class);
	@Autowired
	private MasterDataService masterDataService;

	@RequestMapping(value = "/listHeaderData/{requestType}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody

			String listHeaderData(@PathVariable("requestType") String requestType,Authentication authentication) {
		Map<String, List<?>> headerMap = new HashMap<>();
		System.out.println("listHeaderData");
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					headerMap = masterDataService.listHeaderData(requestType,principal);
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					return gson.toJson(headerMap);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getPlantByEntity/{sapCompany}", method = RequestMethod.GET)
	public @ResponseBody String getPlantByEntity(@PathVariable("sapCompany") String sapCompany,
			@TableParam JQueryDataTableParamModel criteria, HttpServletRequest request, HttpServletResponse response) {

		System.out.println("   XXXXxX  ");
		System.out.println("   XXXXxX  " + sapCompany);

		// SapAmRoleMaster sapAmRoleMaster
		List<PlantVO> amMasterTypeDetails = null;
		try {
			amMasterTypeDetails = masterDataService.getPlantByCompany(sapCompany, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(amMasterTypeDetails));
//		final int sortColumnIndex = criteria.iSortColumnIndex;
//		final int sortDirection = criteria.sSortDirection.equals("asc") ? -1 : 1;
//		List<String> items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
//		logger.info("masterroledata   XXXXxX");
//		System.out.println(sortDirection + " v  " + sortColumnIndex + " v " + criteria.sColumns + "  vv  "
//				+ items.get(sortColumnIndex));
		return gson.toJson(ControllerUtils.getWebResultSet(criteria, new ResultSet(amMasterTypeDetails,
				new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));
	}

	@RequestMapping(value = "/getTcodeByModule/{pmodule}", method = RequestMethod.GET)
	public @ResponseBody String getTcodeByModule(@PathVariable("pmodule") String pmodule,
			@TableParam JQueryDataTableParamModel criteria) {
		try {

			List<SapAmTcodeVO> tcodeInfos = null;
			try {
				tcodeInfos = masterDataService.getTcodeByModule(pmodule, criteria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			// : 1;
			List<String> items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
			logger.info("getTocde   XXXXxX  " + tcodeInfos.size());
			// System.out.println(sortDirection + " v " + sortColumnIndex +
			// " v "
			// + criteria.sColumns + " vv " + items.get(sortColumnIndex));
			return gson.toJson(ControllerUtils.getWebResultSet(criteria,
					new ResultSet(tcodeInfos, new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getPGroup", method = RequestMethod.GET)
	public @ResponseBody List<?> getPGroup() {
		System.out.println("getPGroup");
		try {
			return masterDataService.getPGroup();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getPGroupJson", method = RequestMethod.GET)
	public @ResponseBody String getPGroupJson(@TableParam JQueryDataTableParamModel criteria,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("getPGroupJson");
		List<SapAmMasterTypeDetail> amMasterTypeDetails = null;
		try {
			amMasterTypeDetails = masterDataService.getPGroupJson(criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		return gson.toJson(ControllerUtils.getWebResultSet(criteria, new ResultSet(amMasterTypeDetails,
				new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));

	}

	@RequestMapping(value = "/getRoles", headers = "Accept=application/json")
	public @ResponseBody String getRoles(Authentication authentication, @TableParam JQueryDataTableParamModel criteria,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("SapAmRoleMaster   " + criteria.toString());
		System.out.println("   XXXXxX  " + criteria.sColumns);

		// SapAmRoleMaster sapAmRoleMaster
		List<SapAmRolesRequestVO> amRoleMasters = null;
		if ((authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();

			try {

				amRoleMasters = masterDataService.getRoles(criteria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		return gson.toJson(ControllerUtils.getWebResultSet(criteria,
				new ResultSet(amRoleMasters, new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));
	}

	@RequestMapping(value = "/getUsersDetails", headers = "Accept=application/json")
	public @ResponseBody String getUsersDetails(Authentication authentication) {

		List<SapUserVO> sapUserVOs = null;
		if ((authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();
			try {
				sapUserVOs = masterDataService.getUsersDetails(principal);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		return gson.toJson(sapUserVOs);

	}

	@RequestMapping(value = "/searchUsers/{sapUserId}", headers = "Accept=application/json")
	public @ResponseBody String searchUsers(@PathVariable String sapUserId, Authentication authentication) {

		List<SapUserVO> sapUserVOs = null;
		if ((authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();
			try {
				sapUserVOs = masterDataService.searchUsers(principal, sapUserId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(sapUserVOs);

	}
	
	@RequestMapping(value = "/getConfilctByRequestID/{requestId}", headers = "Accept=application/json")
	public @ResponseBody String getConfilctByRequestID(@PathVariable String requestId,Authentication authentication,
			HttpServletRequest request, HttpServletResponse response) {
	

		// SapAmRoleMaster sapAmRoleMaster
		List<SapAmRiskFunMappingVO> riskFunMappings = null;
		if ((authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();

			try {

				 riskFunMappings = masterDataService.getRiskFunctionByRequestId(requestId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(riskFunMappings);
		
	}
	
	@RequestMapping(value = "/getAllHRMEmployees", method = RequestMethod.GET)
	public @ResponseBody String getAllHRMEmployees(@RequestParam(name = "isRefresh") boolean isRefresh, Authentication authentication) {
		List<HrmLoginVO> hrmUserVOs = null;
		if ((authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();
			try {
				hrmUserVOs = masterDataService.getAllEmployee(isRefresh);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(hrmUserVOs);

	}
	
}
