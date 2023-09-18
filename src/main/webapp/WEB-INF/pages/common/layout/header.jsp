<script type="text/javascript">
	function theFunction() {
		console.log("logout");
		//function preventBack() {
			window.history.forward();
			//window.history.foward(1)
		//}
		setTimeout("preventBack()", 0);
		window.onunload = function() {
			null
		};
		var backlen = history.length;
		//history.go(-backlen);
		history.go(-(history.length - 1));
		window.location
				.replace("https://intranet.dsgroup.com/intranet/home.aspx");
	}
</script>
<header class="header green-bg">
	<div class="sidebar-toggle-box">
		<div class="fa fa-bars tooltips labelText" data-placement="right"
			data-original-title="Toggle Navigation"></div>
	</div>
	<!--logo start-->
	<a href="newRequest" class="logo labelText "><b>GRC Tool</b></a>
	<!--logo end-->
	<div class="nav notify-row" id="top_menu">
		<!--  notification start -->
		<ul class="nav top-menu">
			<!-- settings start -->

		</ul>
		<!--  notification end -->
	</div>
	<div class="top-menu">
		<ul class="nav pull-right top-menu">
			<li><a class="logo" href="newRequest"><i class="fa fa-home"
					style="font-size: 21px"></i> </a></li>
			<!--<li> <a class="logo">${pageContext.request.userPrincipal.name} ${pageContext.request.userPrincipal.principal.username}</a> </li> -->
			<li><a class="logo labelText">
					${pageContext.request.userPrincipal.principal.username}</a></li>
			<!-- <li> <a class="logo">${username} </a> </li> -->
			<li>
				<!-- <a class="logout" href="<!%= request.getContextPath()%>">Logout</a> ${pageContext.request.userPrincipal.name}   ${username}-->
			<!--<li><a class="logout" href="#" onclick="return theFunction();">Logout</a></li>-->
			<li><a class="logout" href="${pageContext.request.contextPath}/logout">Logout</a></li>
		</ul>
	</div>



	<!-- <a data-role="none" href="#" onclick="doLogout"></a> -->
</header>