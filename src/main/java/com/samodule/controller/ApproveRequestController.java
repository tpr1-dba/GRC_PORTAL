package com.samodule.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.samodule.datatable.ControllerUtils;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.datatable.ResultSet;
import com.samodule.datatable.TableParam;
import com.samodule.security.HeapUserDetails;
import com.samodule.service.ApproveRequestManager;
import com.samodule.service.AssignRoleManager;
import com.samodule.service.RequestTcodeManager;
import com.samodule.vo.Confilcted;
import com.samodule.vo.SAPUserRequestVO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRiskFunMappingVO;
import com.samodule.vo.SapAmRolesAssignVO;

@Controller
public class ApproveRequestController {
	private static final Logger logger = LoggerFactory.getLogger(ApproveRequestController.class);

	@Autowired
	private ApproveRequestManager approveRequestManager;
	@Autowired
    private  RequestTcodeManager requestTcodeManager;
	@Autowired
	private AssignRoleManager assignRoleManager;

	@RequestMapping(value = "/approvetcode", method = RequestMethod.GET)
	public ModelAndView approvetcode() {
		System.out.println("approvetcode");
		logger.info("approvetcode");
		return new ModelAndView("approvetcode");
	}

	@RequestMapping(value = "/getUserRequest", method = RequestMethod.GET)
	public @ResponseBody String getUserRequest(@TableParam JQueryDataTableParamModel criteria,
			Authentication authentication) {
		try {
			List<SAPUserRequestVO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					sodUserRequests = approveRequestManager.getUserRequest(criteria, principal);
				}
			}

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			List<String> items = Arrays.asList(criteria.sColumns.split("\\s*,\\s*"));
			logger.info("getTocde   XXXXxX  " + sodUserRequests.size());

			return gson.toJson(ControllerUtils.getWebResultSet(criteria, new ResultSet(sodUserRequests,
					new Long(criteria.iTotalRecords), new Long(criteria.iDisplayLength))));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/chekConfilct", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> chekConfilct(Authentication authentication,
			@RequestBody SapAmRolesAssignVO sapAmRolesAssignVO) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("create Tcode Request " + sapAmRolesAssignVO.toString());

		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());

					List<SapAmRiskFunMappingVO> confilcteds = assignRoleManager.riskFindeByUserId(sapAmRolesAssignVO, principal);
					if (confilcteds != null && confilcteds.size() > 0) {
//						Gson gson = new GsonBuilder().setPrettyPrinting().create();
//						 ObjectMapper mapper = new ObjectMapper();
//					        mapper.enable(SerializationFeature.INDENT_OUTPUT);
						Gson gson = new GsonBuilder().create();
						JsonArray myCustomArray = gson.toJsonTree(confilcteds).getAsJsonArray();
						response.put("isConfilct", "Y");
						response.put("confilct", myCustomArray.toString());
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
					} else {
						response.put("isConfilct", "N");
						response.put("ok", "T-code Saved succesufully");
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("error", "Error while approve t-ocde");
			return new ResponseEntity<Map<String, String>>(HttpStatus.CONFLICT);
		}
		response.put("error", "Try again later");
		return new ResponseEntity<Map<String, String>>(HttpStatus.CONFLICT);

	}
	
	@RequestMapping(value = "/approveRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> approveRole(Authentication authentication,
			@RequestBody SapAmRolesAssignVO sapAmRolesAssignVO) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("create Tcode Request " + sapAmRolesAssignVO.toString());

		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					approveRequestManager.saveCTMApproval(sapAmRolesAssignVO,principal);
					response.put("ok", " Request approved");
					return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("error", "Error while approve request");
			return new ResponseEntity<Map<String, String>>(HttpStatus.CONFLICT);
		}
		response.put("error", "Try again later");
		return new ResponseEntity<Map<String, String>>(HttpStatus.CONFLICT);

	}
	
	@RequestMapping(value = "/updatePlantsPurchegroup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> deletePlantsPurchegroup(Authentication authentication,
			@RequestBody SapAmRequestStatusVO sapAmRequestVO) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("create Tcode Request " + sapAmRequestVO.toString());

		
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					requestTcodeManager.updateplantsPurchegroup(sapAmRequestVO, principal);
					response.put("ok", "Request submited succesufully");
					return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
				}
			}
		} catch (Exception e) {	
			logger.error("error   "+e);
			e.printStackTrace();
			response.put("error", "Error while saving request");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("error", "Try again later");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	@RequestMapping(value = "/updateTcode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> updateTcode(Authentication authentication,
			@RequestBody SapAmRequestStatusVO sapAmRequestVO) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("create Tcode Request " + sapAmRequestVO.toString());

		
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					requestTcodeManager.updateTcodes(sapAmRequestVO, principal);
					response.put("ok", "Request submited succesufully");
					return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
				}
			}
		} catch (Exception e) {	
			logger.error("error   "+e);
			e.printStackTrace();
			response.put("error", "Error while saving request");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("error", "Try again later");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	@RequestMapping(value = "/updateRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> updateRequest(Authentication authentication,
			@RequestBody SapAmRolesAssignVO sapAmRolesAssignVO) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("create Tcode Request " + sapAmRolesAssignVO.toString());

		
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					//approveRequestManager.updateRequest(sapAmRequestVO, principal);
					approveRequestManager.rejectRequestCTM(sapAmRolesAssignVO, principal);
					response.put("ok", "Request submited succesufully");
					return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
				}
			}
		} catch (Exception e) {	
			logger.error("error   "+e);
			e.printStackTrace();
			response.put("error", "Error while saving request");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("error", "Try again later");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@RequestMapping(value = "/updateSapAmUserEmpCodeBySwmRecid", method = RequestMethod.POST)
	public @ResponseBody String updateSapAmUserEmpCodeBySwmRecid(Authentication authentication,
			@RequestParam(name="empCode") String empCode, @RequestParam(name="swmRecid") Long swmRecid) {
		HeapUserDetails principal = null;
		if ((authentication != null)) {
			System.out.println(authentication.toString());
			principal = (HeapUserDetails) authentication.getPrincipal();
			if (principal instanceof HeapUserDetails) {
				return requestTcodeManager.updateSapAmUserEmpCodeBySwmRecid(swmRecid, empCode, principal);
			}
		}
		return null;
	}
	
}
