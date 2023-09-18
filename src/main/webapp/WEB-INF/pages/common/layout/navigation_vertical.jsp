<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<aside>
	<div id="sidebar" class="nav-collapse ">
		<!-- sidebar menu start  SAP_USER_ROLES -->
		<ul class="sidebar-menu" id="nav-accordion">
		
			<sec:authorize access="hasRole('ROLE_USER')">		
				<li class="sub-menu"><a href="newRequest"> <i
						class="fa fa-bar-chart"></i> <span>New Request </span>
				</a></li>
				<li class="sub-menu"><a href="requestHistory"> <i
						class="fa fa-bar-chart"></i> <span>My Requests</span>
				</a></li>				
			</sec:authorize>
			
	
			<sec:authorize access="hasRole('ROLE_HOD')">
			
				<li class="sub-menu"><a href="approveEmpHodRequest"> <i
						class="fa fa-bar-chart"></i> <span>HOD approvals</span>
				</a></li>				
			</sec:authorize>
		<!--  	<sec:authorize access="hasAnyRole('ROLE_CTM','ROLE_CTMHD')">		
				<li class="sub-menu"><a href="newRequest"> <i
						class="fa fa-bar-chart"></i> <span>New Request </span>
				</a></li>			
				<li class="sub-menu"><a href="requestHistory"> <i
						class="fa fa-bar-chart"></i> <span>Show Request</span>
				</a></li>
				<li class="sub-menu"><a href="approvetcode"> <i
						class="fa fa-bar-chart"></i> <span>Approve By CTM</span>
				</a></li>				
			</sec:authorize>-->
			<sec:authorize access="hasRole('ROLE_CTM')">
				<li class="sub-menu"><a href="approvetcode"> <i
						class="fa fa-bar-chart"></i> <span>CTM approvals</span>
				</a></li>				
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_CTMHD')">	
			<li class="sub-menu"><a href="approvetrequest"> <i
						class="fa fa-bar-chart"></i> <span>CTM HOD approvals</span>
			</a></li>			
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_BAS')">			
				<li class="sub-menu"><a href="approveBasis"> <i
						class="fa fa-bar-chart"></i> <span>My Pending approvals</span>
				</a></li>
				<li class="sub-menu"><a href="requestOnBehaf"> <i
						class="fa fa-bar-chart"></i> <span>Request On Behalf</span>
				</a></li>
				<li class="sub-menu"><a href="viewWorkflow"> <i
						class="fa fa-bar-chart"></i> <span>View Workflow</span>
				</a></li>
				<li class="sub-menu"><a href="reports"> <i
						class="fa fa-bar-chart"></i> <span>Reports</span>
				</a></li>
			</sec:authorize>
			
			
		</ul>
		<!-- sidebar menu end-->
	</div>
</aside>