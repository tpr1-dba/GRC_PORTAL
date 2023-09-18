<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="Dashboard">
<meta name="keyword"
	content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

<title>GRC Portal</title>
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
<style>
/* Hide all steps by default: */
/* .tab {
	display: none;
}

button {
	background-color: #4CAF50;
	color: #ffffff;
	border: none;
	padding: 10px 20px;
	font-size: 17px;
	font-family: Raleway;
	cursor: pointer;
}

button:hover {
	opacity: 0.8;
}

#prevBtn {
	background-color: #bbbbbb;
} */
.login-wrap {
    background-color: rgba(0, 167, 82, 0.83);
}
.form-login h2.form-login-heading {
  margin: 0;
  padding: 25px 20px;
  text-align: center;
 /*  background: #217638; */
  background: #028f47;
  border-radius: 5px 5px 0 0;
  -webkit-border-radius: 5px 5px 0 0;
  color: #fff;
  font-size: 20px;
  text-transform: uppercase;
  font-weight: 300;
}
</style>

</head>

<body>

	<!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->

	<div id="login-page">
		<div class="container">

			<form class="form-login" id="loginForm" action="login" method="POST">
				<h2 class="form-login-heading">sign in now</h2>
				<div class="login-wrap">
					<div class="error-group">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input type="text"
								id="userName" name="j_username" class="form-control"
								placeholder="User ID" autofocus />
						</div>
						<div class="emess"></div>
					</div>
					</br>
					<div class="error-group">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input type="password"
								id="password" name="j_password" class="form-control"
								placeholder="Password" />
						</div>
						<div class="emess"></div>
					</div>
					<div style="color: red">${error}</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<!-- 	<label class="checkbox"> <span class="pull-right"> <a
							data-toggle="modal" href="login.html#myModal"> Reset
								Password?</a>

					</span>
					</label> -->
					</br>
					<button class="btn btn-theme btn-block" type="submit">
						<i class="fa fa-lock"></i> SIGN IN
					</button>
					<!-- <hr>
		            
		           <div class="login-social-link centered">
		            <p>or you can sign in via your social network</p>
		                <button class="btn btn-facebook" type="submit"><i class="fa fa-facebook"></i> Facebook</button>
		                <button class="btn btn-twitter" type="submit"><i class="fa fa-twitter"></i> Twitter</button>
		            </div>
		            <div class="registration">
		                Don't have an account yet?<br/>
		                <a class="" href="#">
		                    Create an account
		                </a>
		            </div>-->

				</div>
			</form>
			<!-- <form id="regForm">
				Modal
				<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
					tabindex="-1" id="myModal" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">Forgot Password ?</h4>
							</div>
							<div class="modal-body tab">
								<p>Enter your Username below to reset your password.</p>
								<div class="login-wrap">
									<div class="error-group">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-lock"></i></span> <input type="text"
												name="username" placeholder="Username" autocomplete="off"
												class="form-control placeholder-no-fix">

										</div>
										<div class="emess"></div>
									</div>

								</div>
							</div>



							<div class="modal-body tab">
								<div class="login-wrap">
									<div class="error-group">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-lock"></i></span> <input type="password"
												id="old_password" name="old_password" class="form-control"
												placeholder="Password" />
										</div>
										<div class="emess"></div>
									</div>
									<br>
									<div class="error-group">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-lock"></i></span> <input type="password"
												id="password" name="password" class="form-control"
												placeholder="Password" />
										</div>
										<div class="emess"></div>
									</div>
									<br>
									<div class="error-group">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-lock"></i></span> <input type="password"
												id="confirm_password" name="confirm_password"
												class="form-control" placeholder="Confirm Password" />
										</div>
										<div class="emess"></div>
									</div>

								</div>
								
							</div>
							<div class="modal-footer">
								<button type="button" id="prevBtn" onclick="nextPrev(-1)">Previous</button>
								<button type="button" id="nextBtn" onclick="nextPrev(1)">Next</button>
							</div>
						</div>
					</div>
				</div>
			</form> -->

		</div>
	</div>

	<!-- js placed at the end of the document so the pages load faster -->
	<script src="resources/assets/js/jquery.js"></script>
	<script src="resources/assets/js/bootstrap.min.js"></script>
	<script src="resources/assets/custom/jquery.validate.min.js"></script>
	<!--BACKSTRETCH-->
	<!-- You can use an image of whatever size. This script will stretch to fit in any screen size.-->
	<script type="text/javascript"
		src="resources/assets/js/jquery.backstretch.min.js"></script>
	<script>
		//  $.backstretch("resources/assets/img/login-bg.jpg", {speed: 500});
	</script>
	<script type="text/javascript">
		/* 	$.validator.setDefaults({
				submitHandler : function() {
					alert("submitted!");
				}
			}); */

		$(document)
				.ready(
						function() {
							$('#loginForm')
									.validate(
											{
												rules : {
													j_username : {
														required : true,
														minlength : 2
													},
													j_password : {
														required : true,
														minlength : 5
													},
													confirm_password : {
														required : true,
														minlength : 5,
														equalTo : "#password"
													}
												},
												messages : {
													j_username : {
														required : "Please enter a username",
														minlength : "Your username must consist of at least 2 characters"
													},
													j_password : {
														required : "Please provide a password",
														minlength : "Your password must be at least 5 characters long"
													},
													confirm_password : {
														required : "Please provide a password",
														minlength : "Your password must be at least 5 characters long",
														equalTo : "Please enter the same password as above"
													}
												},
												errorElement : "em",
												errorPlacement : function(
														error, element) {
													// Add the `help-block` class to the error element
													error
															.addClass("help-block");

													if (element.prop("type") === "checkbox") {
														error
																.insertAfter(element
																		.parent("label"));
													} else {
														//error.insertAfter( element );
														element
																.closest(
																		'.error-group')
																.children(
																		'.emess')
																.html(error);
													}
												},
												highlight : function(element,
														errorClass, validClass) {
													$(element)
															.parents(
																	'.input-group')
															.addClass(
																	"has-error")
															.removeClass(
																	"has-success");
													$(element)
															.closest(
																	'.error-group')
															.children('.emess')
															.addClass(
																	"has-error")
															.removeClass(
																	"has-success");
												},
												unhighlight : function(element,
														errorClass, validClass) {
													$(element)
															.parents(
																	'.input-group')
															.addClass(
																	"has-success")
															.removeClass(
																	"has-error");
													$(element)
															.closest(
																	'.error-group')
															.children('.emess')
															.addClass(
																	"has-success")
															.removeClass(
																	"has-error");
												}
											});
							$('#regForm')
									.validate(
											{
												rules : {
													username : {
														required : true,
														minlength : 2
													},
													old_password : {
														required : true,
														minlength : 5
													},
													password : {
														required : true,
														minlength : 5
													},
													confirm_password : {
														required : true,
														minlength : 5,
														equalTo : "#rpassword"
													}
												},
												messages : {
													username : {
														required : "Please enter a username",
														minlength : "Your username must consist of at least 2 characters"
													},
													old_password : {
														required : "Please provide a old password",
														minlength : "Your password must be at least 5 characters long"
													},
													password : {
														required : "Please provide a password",
														minlength : "Your password must be at least 5 characters long"
													},
													confirm_password : {
														required : "Please provide a password",
														minlength : "Your password must be at least 5 characters long",
														equalTo : "Please enter the same password as above"
													}
												},
												errorElement : "em",
												errorPlacement : function(
														error, element) {
													// Add the `help-block` class to the error element
													error
															.addClass("help-block");

													if (element.prop("type") === "checkbox") {
														error
																.insertAfter(element
																		.parent("label"));
													} else {
														//error.insertAfter( element );
														element
																.closest(
																		'.error-group')
																.children(
																		'.emess')
																.html(error);
													}
												},
												highlight : function(element,
														errorClass, validClass) {
													$(element)
															.parents(
																	'.input-group')
															.addClass(
																	"has-error")
															.removeClass(
																	"has-success");
													$(element)
															.closest(
																	'.error-group')
															.children('.emess')
															.addClass(
																	"has-error")
															.removeClass(
																	"has-success");
												},
												unhighlight : function(element,
														errorClass, validClass) {
													$(element)
															.parents(
																	'.input-group')
															.addClass(
																	"has-success")
															.removeClass(
																	"has-error");
													$(element)
															.closest(
																	'.error-group')
															.children('.emess')
															.addClass(
																	"has-success")
															.removeClass(
																	"has-error");
												}
											});
						});
		var currentTab = 0; // Current tab is set to be the first tab (0)
		showTab(currentTab); // Display the current tab

		function showTab(n) {
			// This function will display the specified tab of the form...
			var x = document.getElementsByClassName("tab");
			x[n].style.display = "block";
			//... and fix the Previous/Next buttons:
			if (n == 0) {
				document.getElementById("prevBtn").style.display = "none";
			} else {
				document.getElementById("prevBtn").style.display = "inline";
			}
			if (n == (x.length - 1)) {
				document.getElementById("nextBtn").innerHTML = "Submit";
			} else {
				document.getElementById("nextBtn").innerHTML = "Next";
			}
			//... and run a function that will display the correct step indicator:
			fixStepIndicator(n)
		}

		function nextPrev(n) {
			// This function will figure out which tab to display
			var x = document.getElementsByClassName("tab");
			if (n == 1 && !$('#regForm').valid())
				return false;
			x[currentTab].style.display = "none";
			// Increase or decrease the current tab by 1:
			currentTab = currentTab + n;
			// if you have reached the end of the form...
			if (currentTab >= x.length) {
				// ... the form gets submitted:
				document.getElementById("regForm").submit();
				return false;
			}
			// Otherwise, display the correct tab:
			showTab(currentTab);
		}

		function fixStepIndicator(n) {
			// This function removes the "active" class of all steps...
			var i, x = document.getElementsByClassName("step");
			for (i = 0; i < x.length; i++) {
				x[i].className = x[i].className.replace(" active", "");
			}
			//... and adds the "active" class on the current step:
			x[n].className += " active";
		}
	</script>

</body>
</html>
