<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>    
 
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>--%>
<%-- <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  --%>
<header class="header">
	<div class="row">
		<div class="row black-bg">

			<div class="sidebar-toggle-box">
				<div class="fa fa-bars tooltips" data-placement="right"
					data-original-title="Toggle Navigation"></div>
			</div>
			<!--logo start
			<a href="employee" class="logo"><b>Employee portal</b></a>-->
			<a href="#" class="logo"><b>Employee portal</b></a>
			<!--logo end-->

			<div class="top-menu">
				<ul class="nav pull-right top-menu">
					<li><a class="logo" href="employee"><i class="fa fa-home"
							style="font-size: 21px"></i> </a></li>
					<!--<li> <a class="logo">${pageContext.request.userPrincipal.name} ${pageContext.request.userPrincipal.principal.username}</a> </li> -->
					<li><a class="logo">
							${pageContext.request.userPrincipal.principal.username}</a></li>
					<!-- <li> <a class="logo">${username} </a> </li> -->
					<li>
						<!-- <a class="logout" href="<!%= request.getContextPath()%>">Logout</a> ${pageContext.request.userPrincipal.name}   ${username}-->
					<li><a class="logout" href="logout">Logout</a></li>
					<!--<li><a class="logout" href="${pageContext.request.contextPath}/logout">Logout</a></li>-->
				</ul>
			</div>



			<div class="col-md-12">
				<div class="tab" role="tabpanel">
					<!-- Nav tabs -->
					<ul class="grid-tab nav nav-tabs" role="tablist">
						<!--<li role="presentation" class="active menulist"><a href="employee"
							role="page" data-toggle="page"><i class="fa fa-user"></i><span>Employee
									Details</span></a></li>
						 <li class="menulist" role="presentation" class="menulist"><a href="contact"><i
								class="fa fa-address-book" role="page" data-toggle="page"></i><span>Contact
									Details</span></a></li>
						<li class="menulist" role="presentation"><a href="family"
							aria-controls="family" role="page" data-toggle="page"><i
								class="fa fa-users "></i><span>Family Details</span></a></li>
						<li class="menulist" role="presentation"><a href="nominee"
							aria-controls="nominee" role="page" data-toggle="page"><i
								class="fa fa-crown"></i><span>Nominee Details</span></a></li> -->
						<li class="active menulist" role="presentation"><a href="qualification"
							aria-controls="qualification" role="page" data-toggle="page"><i
								class="fa fa-graduation-cap"></i><span>Education details</span></a></li>
						<!-- <li class="menulist" role="presentation"><a href="official"
							aria-controls="official" role="page" data-toggle="page"><i
								class="fa fa-building"></i><span>Official Details</span></a></li> -->
						<!-- <li class="menulist" role="presentation"><a href="kra" aria-controls="kra"
							role="page" data-toggle="page"><i class="fa fa-sitemap"></i><span>KRA/LMS
									Details</span></a></li>
						<li class="menulist" role="presentation"><a href="other" aria-controls="other"
							role="page" data-toggle="page"><i class="fa fa-info"></i><span>Other
									Details</span></a></li> -->

					</ul>

				</div>
			</div>
		</div>
	<!-- 	<div class="row">
			<div class="col-md-12">
				<div class="portlet">
					
					<div class="portlet-content">
						<p style="margin-left: 10px;margin-top:2px;">This is to declare that above information provided by me is true and correct.</p>
					</div>
					<div class="portlet-footer">
						<div class="" style="padding-right:10px;padding-left:10px">
							<div class="form-check" style="display: inline">
								<input class="form-check-input" type="checkbox" value=""
									id="flexCheckDefault" name="useraggrement"> <label class="form-check-label"
									for="flexCheckDefault"> Agree </label>
							</div>
							
							<button style="display: inline; float:right;margin-bottom: 1px;" id="hedersbmit" type="button" class="btn btn-info add-new">
								<i class="glyphicon glyphicon-ok"></i> Submit
							</button>

						</div>

					</div>
				</div>
			</div>
		</div> -->
	</div>
</header>

