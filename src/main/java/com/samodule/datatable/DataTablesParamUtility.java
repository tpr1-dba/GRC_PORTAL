/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samodule.datatable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

public class DataTablesParamUtility  implements WebArgumentResolver{

	public Object resolveArgument(MethodParameter mParam, NativeWebRequest request) throws Exception	  {
	    TableParam tableParamAnnotation = mParam.getParameterAnnotation(TableParam.class);	 
	    //if (tableParamAnnotation!=null)
	    	HttpServletRequest httpRequest = (HttpServletRequest) request.getNativeRequest();
        if (httpRequest.getParameter("sEcho") != null && httpRequest.getParameter("sEcho") != "") {
            JQueryDataTableParamModel param = new JQueryDataTableParamModel();
            param.sEcho = httpRequest.getParameter("sEcho");
            param.sSearch = httpRequest.getParameter("sSearch");
            param.sColumns = httpRequest.getParameter("sColumns");
            param.iDisplayStart = Integer.parseInt(httpRequest.getParameter("iDisplayStart"));
            param.iDisplayLength = Integer.parseInt(httpRequest.getParameter("iDisplayLength"));
            param.iColumns = Integer.parseInt(httpRequest.getParameter("iColumns"));
            param.iSortingCols = Integer.parseInt(httpRequest.getParameter("iSortingCols"));
            param.iSortColumnIndex = Integer.parseInt(httpRequest.getParameter("iSortCol_0"));
            param.sSortDirection = httpRequest.getParameter("sSortDir_0");
            return param;
        } else {
        	return WebArgumentResolver.UNRESOLVED;
        }
    }
}