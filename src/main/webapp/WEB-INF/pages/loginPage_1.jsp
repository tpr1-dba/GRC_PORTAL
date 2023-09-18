<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="Dashboard">
<meta name="keyword"
	content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

<title>SAP Roles and Authorization Module</title>
<link rel="shortcut icon" type="image/x-icon"
	href="resources/assets/images/ic_menu_ds.png" />
<!-- Bootstrap core CSS -->
<link href="resources/assets/css/bootstrap.css" rel="stylesheet">
<!--external css-->
<link href="resources/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet" />

<!-- Custom styles for this template -->
<link href="resources/assets/css/style.css" rel="stylesheet">
<link href="resources/assets/css/style-responsive.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->

	<div id="login-page">
		<div class="container">
			<form action="http://reim.dsgroup.com:9090/GRCPortal/login" method="POST">				
				 <input type="hidden"
								id="userName" name="j_username"  value="ved.prakash"/>						
				 <input type="hidden"
								id="password" name="j_password"  value="12345"/>						
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />		
					<button class="btn btn-theme btn-block" type="submit">
						<i class="fa fa-lock"></i> SIGN IN
					</button>	
			</form>
		</div>
	</div>
	<script src="resources/assets/js/jquery.js"></script>
	<script src="resources/assets/js/bootstrap.min.js"></script>

</body>
</html>
