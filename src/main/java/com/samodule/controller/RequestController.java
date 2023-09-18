package com.samodule.controller;

import java.util.Arrays;
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
import com.samodule.datatable.ControllerUtils;
import com.samodule.datatable.JQueryDataTableParamModel;
import com.samodule.datatable.ResultSet;
import com.samodule.datatable.TableParam;
import com.samodule.security.HeapUserDetails;
import com.samodule.service.RequestTcodeManager;
import com.samodule.vo.RequestPlantDTO;
import com.samodule.vo.SapAmRequestEmpRefVO;
import com.samodule.vo.SapAmRequestRoleVO;
import com.samodule.vo.SapAmRequestStatusVO;
import com.samodule.vo.SapAmRequestVO;
import com.samodule.vo.UserRequestDTO;

@Controller
public class RequestController {
	static final Logger logger = Logger.getLogger(RequestController.class.getName());
	@Autowired
	private RequestTcodeManager requestTcodeManager;

	@RequestMapping(value = "/RequestOld", method = RequestMethod.GET)
	public ModelAndView RequestOld() {
		System.out.println("RequestOld");
		logger.info("RequestOld");
		return new ModelAndView("RequestOld");
	}

	@RequestMapping(value = "/newRequest", method = RequestMethod.GET)
	public ModelAndView newRequest() {
		System.out.println("newRequest");
		logger.info("newRequest");
		return new ModelAndView("newRequest");
	}
	
	
	
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public ModelAndView request() {
		System.out.println("request");
		logger.info("request");
		return new ModelAndView("request");
	}

	@RequestMapping(value = "/requestRole", method = RequestMethod.GET)
	public ModelAndView requestRole() {
		System.out.println("requestRole");
		logger.info("requestRole");
		return new ModelAndView("requestRole");
	}

	@RequestMapping(value = "/requestRefrence", method = RequestMethod.GET)
	public ModelAndView requestRefrence() {
		System.out.println("requestRefrence");
		logger.info("requestRefrence");
		return new ModelAndView("requestRefrence");
	}

	@RequestMapping(value = "/createEmpRefRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> createEmpRefRequest(Authentication authentication,
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
					
					Map<String, String> codes = requestTcodeManager.submitRequestEmpRef(sapAmRequestEmpRefVO, principal);
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

	@RequestMapping(value = "/createRoleRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> createRoleRequest(Authentication authentication,
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

					requestTcodeManager.createRoleRequest(sapAmRequestRoleVO, principal);
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

	@RequestMapping(value = "/createTcodeRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> createTcodeRequest(Authentication authentication,
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
					requestTcodeManager.createTcodeRequest(sapAmRequestVO, principal);
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

	@RequestMapping(value = "/submitRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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
					Map<String, String> codes = requestTcodeManager.submitRequest(sapAmRequestVO, principal);
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

	@RequestMapping(value = "/submitRequestRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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

					Map<String, String> codes = requestTcodeManager.submitRequestRole(sapAmRequestVO, principal);
					System.out.println(codes.keySet().toString());
					if (codes.containsKey("5")) {
						response.put("ok", "Request submited succesufully");
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
					} else if (codes.containsKey("0")) {
						response.put("ok", "Workflow not found, Request not submit!, Connect to respective CTM."); 
						return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
					}
					else {
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

	@RequestMapping(value = "/deletePlantsPurchegroup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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
					requestTcodeManager.deletePlantsPurchegroup(sapAmRequestVO, principal);
					response.put("ok", "Request submited succesufully");
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

	@RequestMapping(value = "/deleteTcode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Map<String, String>> deleteTcode(Authentication authentication,
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
					requestTcodeManager.deleteTcodes(sapAmRequestVO, principal);
					response.put("ok", "Request submited succesufully");
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

	@RequestMapping(value = "/getRequestedByUserID", method = RequestMethod.GET)
	public @ResponseBody String getRequestedByUserID(@TableParam JQueryDataTableParamModel criteria,
			Authentication authentication) {
		try {
			List<UserRequestDTO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					sodUserRequests = requestTcodeManager.getRequestedByUserID(criteria, principal);
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

	@RequestMapping(value = "/getRequestByUser", method = RequestMethod.GET)
	public @ResponseBody String getRequestByUser(Authentication authentication) {
		try {
			List<UserRequestDTO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					sodUserRequests = requestTcodeManager.getRequestedByUserID(principal);
				}
			}

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			logger.info("getTocde   XXXXxX  " + sodUserRequests.size());

			return gson.toJson(sodUserRequests);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getRequestedRolesUserID", method = RequestMethod.GET)
	public @ResponseBody String getRequestedRolesUserID(@TableParam JQueryDataTableParamModel criteria,
			Authentication authentication) {
		try {
			List<UserRequestDTO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					sodUserRequests = requestTcodeManager.getRequestedRolesUserID(criteria, principal);
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

	@RequestMapping(value = "/getRequestedPlant/{surRecid}/{requestId}", method = RequestMethod.GET)
	public @ResponseBody String getRequestedPlant(@PathVariable Long surRecid, @PathVariable Long requestId,
			@TableParam JQueryDataTableParamModel criteria, Authentication authentication) {
		try {
			List<RequestPlantDTO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					sodUserRequests = requestTcodeManager.getRequestedPlant(surRecid, requestId, criteria, principal);
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

	@RequestMapping(value = "/getRequestedPlantForRequest/{surRecid}/{requestId}", method = RequestMethod.GET)
	public @ResponseBody String getRequestedPlantForRequest(@PathVariable Long surRecid, @PathVariable Long requestId,
			@TableParam JQueryDataTableParamModel criteria, Authentication authentication) {
		try {
			List<RequestPlantDTO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					sodUserRequests = requestTcodeManager.getRequestedPlantForRequest(surRecid, requestId, criteria,
							principal);
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

	@RequestMapping(value = "/getRequestedTcode/{surRecid}/{requestId}", method = RequestMethod.GET)
	public @ResponseBody String getRequestedTcode(@PathVariable Long surRecid, @PathVariable Long requestId,
			@TableParam JQueryDataTableParamModel criteria, Authentication authentication) {
		try {
			List<UserRequestDTO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					sodUserRequests = requestTcodeManager.getRequestedTcode(surRecid, requestId, criteria, principal);
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

	@RequestMapping(value = "/getRequestedTcodeByRequestID/{surRecid}/{requestId}", method = RequestMethod.GET)
	public @ResponseBody String getRequestedTcodeByRequestID(@PathVariable Long surRecid, @PathVariable Long requestId,
			@TableParam JQueryDataTableParamModel criteria, Authentication authentication) {
		try {
			List<UserRequestDTO> sodUserRequests = null;
			HeapUserDetails principal = null;
			if ((authentication != null)) {
				System.out.println(authentication.toString());
				principal = (HeapUserDetails) authentication.getPrincipal();
				if (principal instanceof HeapUserDetails) {
					System.out.println("tostring  " + principal.toString());
					sodUserRequests = requestTcodeManager.getRequestedTcodeByRequestID(surRecid, requestId, criteria,
							principal);
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
}
