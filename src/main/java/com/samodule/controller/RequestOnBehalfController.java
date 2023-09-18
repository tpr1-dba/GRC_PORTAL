package com.samodule.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samodule.security.HeapUserDetails;
import com.samodule.service.MasterDataService;
import com.samodule.service.RequestTcodeManagerOUser;
import com.samodule.vo.SapAmOtherUserVO;
import com.samodule.vo.SapAmRequestEmpRefVO;
import com.samodule.vo.SapAmRequestRoleVO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRequestVO;
import com.samodule.vo.SapUserVO;

@Controller
public class RequestOnBehalfController {
	static final Logger logger = Logger.getLogger(RequestOnBehalfController.class.getName());

	@Autowired
	private RequestTcodeManagerOUser requestTcodeManagerOUser;

	@RequestMapping(value = "/requestOnBehaf", method = RequestMethod.GET)
	

	public ModelAndView requestOnBehaf() {
		System.out.println("requestOnBehaf");
		logger.info("requestOnBehaf");
		return new ModelAndView("requestOnBehaf");
	}

	@RequestMapping(value = "/createTcodeRequestOnBehalf", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> createTcodeRequestOnBehalf(Authentication authentication,
			@RequestBody @Valid SapAmRequestVO sapAmRequestVO, BindingResult result) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("create Tcode Request " + sapAmRequestVO.toString());

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println(error.getDefaultMessage());
			}

			for (FieldError error : result.getFieldErrors()) {
				System.err.println(error.getField() + "   " + error.getDefaultMessage());
				response.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					principal.setSapUserId(sapAmRequestVO.getSapUserid());
					requestTcodeManagerOUser.createTcodeRequest(sapAmRequestVO, principal);
					response.put("ok", "Request save succesufully");
					return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
				}
			}
		} catch (Exception e) {
			logger.error("error   " + e);
			e.printStackTrace();
			response.put("error", "Error while saving request");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("error", "Try again later");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@RequestMapping(value = "/createEmpRefRequestOnBehalf", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> createEmpRefRequestOnBehalf(Authentication authentication,
			@RequestBody @Valid SapAmRequestEmpRefVO sapAmRequestEmpRefVO, BindingResult result) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("create EmpRef Request " + sapAmRequestEmpRefVO.toString());

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println(error.getDefaultMessage());
			}

			for (FieldError error : result.getFieldErrors()) {
				if ("reason".equalsIgnoreCase(error.getField()))
					response.put("empreason", error.getDefaultMessage());
				else {
					response.put(error.getField(), error.getDefaultMessage());
				}
			}

			return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					principal.setSapUserId("");
					Map<String, String> codes = requestTcodeManagerOUser.submitRequestEmpRef(sapAmRequestEmpRefVO,
							principal);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					if (codes.containsKey("5")) {
						response.put("ok", "Request submited succesufully");
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
					} else if (codes.containsKey("0")) {
						response.put("ok", "Workflow not found, Request not submit!, Connect to respective CTM.");
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
					}
				}
			}
		} catch (Exception e) {
			response.put("error", "Error while saving t-ocde");
			return new ResponseEntity<Map<String, String>>(HttpStatus.CONFLICT);
		}
		response.put("error", "Try again later");
		return new ResponseEntity<Map<String, String>>(HttpStatus.CONFLICT);

	}

	@RequestMapping(value = "/createRoleRequestOnBehalf", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> createRoleRequestOnBehalf(Authentication authentication,
			@RequestBody @Valid SapAmRequestRoleVO sapAmRequestRoleVO, BindingResult result) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("create Role Request " + sapAmRequestRoleVO.toString());

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println(error.getDefaultMessage());
			}

			for (FieldError error : result.getFieldErrors()) {
				System.err.println(error.getField() + "   " + error.getDefaultMessage());
				response.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					principal.setSapUserId(sapAmRequestRoleVO.getSapUserid());
					requestTcodeManagerOUser.createRoleRequest(sapAmRequestRoleVO, principal);
					response.put("ok", "Request for role saved succesufully");
					return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
				}
			}
		} catch (Exception e) {
			logger.error("error   " + e);
			e.printStackTrace();
			response.put("error", "Error while saving request");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("error", "Try again later");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/submitRequestOnBehalf", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> submitRequest(Authentication authentication,
			@RequestBody @Valid SapAmRequestStatusVO sapAmRequestVO, BindingResult result) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("submitRequest " + sapAmRequestVO.toString());

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println(error.getDefaultMessage());
			}

			for (FieldError error : result.getFieldErrors()) {
				System.err.println(error.getField() + "   " + error.getDefaultMessage());
				response.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					principal.setSapUserId(sapAmRequestVO.getSapUserid());
					Map<String, String> codes = requestTcodeManagerOUser.submitRequest(sapAmRequestVO, principal);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					if (codes.containsKey("5")) {
						response.put("ok", "Request submited succesufully");
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
					} else if (codes.containsKey("0")) {
						response.put("ok", "Workflow not found, Request not submit!, Connect to respective CTM.");
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
					} else if (codes.containsKey("role")) {
						codes.remove("role");
						response.put("ok", "Role alredy found for requested tcode, " + codes.keySet() + " : "
								+ codes.values() + " Request not submit!.");
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
					} else if (codes.containsKey("tcode")) {
						codes.remove("tcode");
						response.put("ok", "Role not found for requested tcode and plant, " + codes.keySet() + " : "
								+ codes.values() + " Request not submit!, Connect to Basis Team.");
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
					}
				}

			}
		} catch (Exception e) {
			logger.error("error   " + e);
			e.printStackTrace();
			response.put("error", "Error while saving request");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("error", "Try again later");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@RequestMapping(value = "/submitRequestRoleOnBehalf", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> submitRequestRole(Authentication authentication,
			@RequestBody @Valid SapAmRequestStatusVO sapAmRequestVO, BindingResult result) {
		Map<String, String> response = new HashMap<String, String>();
		System.out.println("submitRequest " + sapAmRequestVO.toString());

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println(error.getDefaultMessage());
			}

			for (FieldError error : result.getFieldErrors()) {
				System.err.println(error.getField() + "   " + error.getDefaultMessage());
				if ("reason".equalsIgnoreCase(error.getField()))
					response.put("reasonrole", error.getDefaultMessage());
				else {
					response.put(error.getField(), error.getDefaultMessage());
				}
			}

			return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					principal.setSapUserId(sapAmRequestVO.getSapUserid());
					Map<String, String> codes = requestTcodeManagerOUser.submitRequestRole(sapAmRequestVO, principal);
					System.out.println(codes.keySet().toString());
					if (codes.containsKey("5")) {
						response.put("ok", "Request submited succesufully");
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
					} else if (codes.containsKey("0")) {
						response.put("ok", "Workflow not found, Request not submit!, Connect to respective CTM.");
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
					} else {
						response.put("nok", "Request not submit");
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}

			}
		} catch (Exception e) {
			logger.error("error   " + e);
			e.printStackTrace();
			response.put("error", "Error while saving request");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("error", "Try again later");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@RequestMapping(value = "/getOtherUsersDetails", headers = "Accept=application/json")
	@ResponseBody  String getOtherUserList() {
		List<SapAmOtherUserVO> ouList = new ArrayList<SapAmOtherUserVO>();

		SapAmOtherUserVO amOtherUserVO = new SapAmOtherUserVO();
		amOtherUserVO.setSaouRecid(new BigDecimal(1));
		amOtherUserVO.setSapUserid("TEST1");
		amOtherUserVO.setSapCompanyCode("1000");
		amOtherUserVO.setUserDesc("Test 1");
		SapAmOtherUserVO amOtherUserVO1 = new SapAmOtherUserVO();
		amOtherUserVO1.setSaouRecid(new BigDecimal(2));
		amOtherUserVO1.setSapUserid("TEST2");
		amOtherUserVO1.setSapCompanyCode("1000");
		amOtherUserVO1.setUserDesc("Test 2");
		SapAmOtherUserVO amOtherUserVO2 = new SapAmOtherUserVO();
		amOtherUserVO2.setSaouRecid(new BigDecimal(3));
		amOtherUserVO2.setSapUserid("TEST3");
		amOtherUserVO2.setSapCompanyCode("2000");
		amOtherUserVO2.setUserDesc("Test 3");
		SapAmOtherUserVO amOtherUserVO3 = new SapAmOtherUserVO();
		amOtherUserVO3.setSaouRecid(new BigDecimal(4));
		amOtherUserVO3.setSapUserid("TEST4");
		amOtherUserVO3.setSapCompanyCode("3000");
		amOtherUserVO3.setUserDesc("Test 4");
		SapAmOtherUserVO amOtherUserVO4 = new SapAmOtherUserVO();
		amOtherUserVO4.setSaouRecid(new BigDecimal(5));
		amOtherUserVO4.setSapUserid("TEST5");
		amOtherUserVO4.setSapCompanyCode("3000");
		amOtherUserVO4.setUserDesc("Test 5");

		ouList.add(amOtherUserVO);
		ouList.add(amOtherUserVO1);
		ouList.add(amOtherUserVO2);
		ouList.add(amOtherUserVO3);
		ouList.add(amOtherUserVO4);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(ouList));
		return gson.toJson(ouList);
	}
	
	@RequestMapping(value = "/searchOtherUsers/{sapUserId}", headers = "Accept=application/json")
	public @ResponseBody String searchOtherUsers(@PathVariable String sapUserId, Authentication authentication) {

		List<SapAmOtherUserVO> sapUserVOs = null;
		if ((authentication != null)) {
			System.out.println(authentication.toString());
			HeapUserDetails principal = (HeapUserDetails) authentication.getPrincipal();
			try {
				sapUserVOs = masterDataService.searchOthUsers(principal, sapUserId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(sapUserVOs);

	}
	
	@Autowired
	private MasterDataService masterDataService;
}
