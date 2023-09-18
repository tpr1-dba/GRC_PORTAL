<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"
	contentType="text/html;charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="Dashboard">
<meta name="keyword"
	content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
<meta http-equiv="refresh"
	content="<%=session.getMaxInactiveInterval()%>;url=https://intranet.dsgroup.com/intranet/home.aspx" />
<title>GRC Portal</title>
<link rel="shortcut icon" type="image/x-icon"
	href="resources/assets/images/ic_menu_ds.png" />
<!-- Bootstrap core CSS -->
<link href="resources/assets/css/bootstrap.css" rel="stylesheet">
<link href="resources/assets/jquery-ui-1.11.4/jquery-ui.css"
	rel="stylesheet">

<!--external css -->
<link
	href="resources/assets/font-awesome-4.4.0/css/font-awesome.min.css"
	rel="stylesheet" />
<link href="resources/assets/fontawesome-5.15/css/all.min.css"
	rel="stylesheet" />

<!-- Custom styles for this template -->
<link href="resources/assets/css/style.css" rel="stylesheet">
<link href="resources/assets/css/style-responsive.css" rel="stylesheet"
	rel="stylesheet">
<!--  <link rel="stylesheet"
	href="resources/assets/media/css/jquery.dataTables.css" type="text/css"> 
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/v/bs4/dt-1.11.3/datatables.min.css" />-->
<link rel="stylesheet"
	href="resources/assets/online/css/datatables.min.css"
	type="text/css">
<link rel="stylesheet"
	href="resources/assets/media/css/dataTables.responsive.css"
	type="text/css">
<link rel="stylesheet" href="resources/assets/autocomplete/immybox.css"
	type="text/css">

<!--<link rel="stylesheet" type="text/css" media="all" href="resources/assets/protlet/style/protlet.css" />-->
<link rel="stylesheet" type="text/css" media="all"
	href="resources/assets/custom/styles/theme.css" />
<link rel="stylesheet" type="text/css"
	href="resources/assets/custom/styles/multiselect.css" />
<link rel="stylesheet"
	href="resources/assets/jquery-ui-1.11.4/smoothness/jquery-ui.css">
<link rel="stylesheet"
	href="resources/assets/online/css/sweetalert2.min.css">
<!--  <link rel="stylesheet"
	href="resources/assets/sweetalert/lib/sweet-alert.css">

<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.2.0/sweetalert2.min.css">-->
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<style type="text/css">
.modal-backdrop {
	/* bug fix - no overlay */
	display: none;
}

.modal-body {
	max-height: calc(100vh - 110px);
	overflow-y: auto;
}

.dataTables_scroll {
	overflow: auto;
}

.red-star {
	color: red;
}

.portlet-header {
	background-image: url("bg_table_th.gif");
	/* background-color: #2C9E4B; // 2c4a34 */
	background-color: #18a65d;
	/* margin: 0.3em; */
	height: 40px;
	padding-top: 5px;
	padding-bottom: 8px;
	padding-left: 0.2em;
	border: 1px solid #ccc;
	border-bottom-left-radius: 0px;
	border-bottom-right-radius: 0;
}

table.dataTable thead>tr>th {
	
}

.table-striped>tbody>tr:nth-child(2n+1)>td, .table-striped>tbody>tr:nth-child(2n+1)>th
	{
	background-color: #A6DFC1;
}

table.dataTable tbody tr.duplicatrole {
	background-color: #abff32;
	content: "Selected dplicate role for  code and plant";
}

.table-duplicatrole>tbody>tr:nth-child(2n+1)>td {
	background-color: #abff32;
	content: "Selected dplicate role for  code and plant";
}

table.dataTable tbody tr.selected {
	background-color: #b0bed9;
}

div.dataTables_filter, div.dataTables_length {
	display: inline-block;
	margin-left: 1em;
	margin-top: 5px;
	margin-bottom: 0;
}
/* .dataTables_length,.dataTables_filter {
    margin-left: 10px;
    float: right;
} */
div.dataTables_length {
	float: left;
}

element.style {
	width: 35%;
}

.table>caption+thead>tr:first-child>th, .table>colgroup+thead>tr:first-child>th,
	.table>thead:first-child>tr:first-child>th, .table>caption+thead>tr:first-child>td,
	.table>colgroup+thead>tr:first-child>td, .table>thead:first-child>tr:first-child>td
	{
	border-top: 0;
}

.table-condensed>thead>tr>th, .table-condensed>tbody>tr>th,
	.table-condensed>tfoot>tr>th, .table-condensed>thead>tr>td,
	.table-condensed>tbody>tr>td, .table-condensed>tfoot>tr>td {
	padding: 5px;
}

.table-bordered>thead>tr>th, .table-bordered>thead>tr>td {
	border-bottom-width: 2px;
}

.table-bordered>thead>tr>th, .table-bordered>tbody>tr>th,
	.table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td,
	.table-bordered>tbody>tr>td, .table-bordered>tfoot>tr>td {
	border: 1px solid #ddd;
}

.table-condensed>thead>tr>th, .table-condensed>tbody>tr>th,
	.table-condensed>tfoot>tr>th, .table-condensed>thead>tr>td,
	.table-condensed>tbody>tr>td, .table-condensed>tfoot>tr>td {
	padding: 5px;
}

.table>thead>tr>th {
	vertical-align: bottom;
	border-bottom: 2px solid #ddd;
}

.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td,
	.table>tbody>tr>td, .table>tfoot>tr>td {
	padding: 8px;
	line-height: 1.42857143;
	vertical-align: top;
	border-top: 1px solid #ddd;
}

th {
	text-align: left;
}

td, th {
	padding: 0;
}

* {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

user agent stylesheet
th {
	display: table-cell;
	vertical-align: inherit;
	font-weight: bold;
	text-align: -internal-center;
}

table {
	border-spacing: 0;
	border-collapse: collapse;
}

user agent stylesheet
table {
	border-collapse: separate;
	text-indent: initial;
	border-spacing: 2px;
}

.ui-widget {
	font-family: Trebuchet MS, Tahoma, Verdana, Arial, sans-serif;
	font-size: 1.1em;
}

div.toolbars {
	float: right;
}
/*smaller size filter text box*/
/*smaller size filter text box*/
.dataTables_filter>label>input {
	line-height: normal !important;
}

/*entry length text size changed*/
.dataTables_length>label {
	font-weight: normal !important;
	font-size: 13px !important;
}
/*filter label text size changed*/
.dataTables_filter>label {
	font-weight: normal !important;
	font-size: 15px !important;
}

table.dataTable>thead>tr>th>input {
	font-weight: normal !important;
	font-size: 15px !important;
	color: black;
}
/*table header size changed*/
table.dataTable>thead>tr>th {
	font-weight: 100 !important;
	color: #fbf6f6;
	font-size: 14px !important;
	padding: 5px !important;
	background-color: #797979;
}

.modal-header {
	padding: 9px 15px;
	border-bottom: 1px solid #eee;
	background-color: #18a65d;
	-webkit-border-top-left-radius: 5px;
	-webkit-border-top-right-radius: 5px;
	-moz-border-radius-topleft: 5px;
	-moz-border-radius-topright: 5px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
}

/*table data size changed*/
table.dataTable>tbody>tr>td {
	font-weight: 100 !important;
	padding: 4px !important;
	font-size: 13px !important;
}

/*table paging size changed*/
.dataTables_paginate>span>a {
	margin-bottom: 0px !important;
	padding: 2px 10px !important;
}
/*table paging size changed*/
.dataTables_paginate>a {
	margin-bottom: 0px !important;
	padding: 2px 10px !important;
}

/*table paging float right*/
.dataTables_paginate {
	text-align: right !important;
	margin-top: -10px !important;
}

/*fixed sorting arrow 'after'*/
table.dataTable thead .sorting-icons:after {
	content: "\e114";
	font-weight: lighter;
	font-size: x-small;
	display: list-item;
	list-style: none;
	margin-left: -1px;
	border: 0px solid white !important;
	padding: 0 !important;
	line-height: 0.5em !important;
	margin-top: 0px !important;
	background: none !important;
}
/*fixed sorting arrow 'before'*/
table.dataTable thead .sorting-icons:before {
	content: "\e113";
	font-weight: lighter;
	font-size: x-small;
	display: list-item;
	list-style: none;
	margin-left: -0.4px;
	border: 0px solid white !important;
	padding: 0 !important;
	line-height: 0.5em !important;
	margin-top: 0px !important;
	background: none !important;
}

/*fixed sorting arrows position*/
.sorting-cnt {
	float: right;
	margin-right: 10px;
	display: block;
}

/*fixed sorting arrows position*/
.sorting-cnt {
	float: right;
	margin-right: 10px;
	display: block;
}

/* .dataTables_length label,
.dataTables_filter label {
  color: #ffffff;
} */
.error {
	color: #ff0000;
	font-size: 12px;
	margin-top: 5px;
	margin-bottom: 0;
}

.inputTxtError {
	border: 1px solid #ff0000;
	color: #0e0e0e;
}

table.dataTable.select tbody tr, table.dataTable thead th:first-child {
	cursor: pointer;
}

.customcheck {
	position: relative;
}

.customcheck input {
	display: none;
}

.customcheck input                          ~.checkmark {
	background: #ee0b0b;
	width: 25px;
	display: inline-block;
	position: relative;
	height: 25px;
	border-radius: 2px;
	vertical-align: middle;
	/*  margin-right:10px; */
}

.customcheck input                          ~.checkmark:after,
	.customcheck input        
	                 ~.checkmark:before {
	content: '';
	position: absolute;
	width: 2px;
	height: 16px;
	background: #fff;
	left: 12px;
	top: 4px;
}

.customcheck input                          ~.checkmark:after {
	transform: rotate(-45deg);
	z-index: 1;
}

.customcheck input                          ~.checkmark:before {
	transform: rotate(45deg);
	z-index: 1;
}

.customcheck input:checked                          ~.checkmark {
	background: #3d8a00;
	width: 25px;
	display: inline-block;
	position: relative;
	height: 25px;
	border-radius: 2px;
}

.customcheck input:checked                          ~.checkmark:after {
	display: none;
}

.customcheck input:checked                          ~.checkmark:before {
	background: none;
	border: 2px solid #fff;
	width: 6px;
	top: 2px;
	left: 9px;
	border-top: 0;
	border-left: 0;
	height: 13px;
	top: 2px;
}

.showback {
	background: #ffffff;
	margin-bottom: 3px;
	/*  box-shadow: 0px 3px 2px #aab2bd; */
	padding: 1px;
	float: right;
}

/* .error {
	border-color: #f50000;
} */
.panel-body {
	padding: 0px;
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 11px;
	line-height: 1.42857143;
}

table.dataTable tbody th.dt-body-center, table.dataTable tbody td.dt-body-center
	{
	text-align: left;
	padding-left: 18px;
}

.table-condensed>thead>tr>th, .table-condensed>tbody>tr>th,
	.table-condensed>tfoot>tr>th, .table-condensed>thead>tr>td,
	.table-condensed>tbody>tr>td, .table-condensed>tfoot>tr>td {
	padding: 5px;
}

table.dataTable thead th, table.dataTable thead td {
	padding: 0px 18px;
}

table.dataTable tbody th, table.dataTable tbody td {
	padding: 5px 0px;
}

/* .table-striped>tbody>tr:nth-child(odd)>td, 
.table-striped>tbody>tr:nth-child(odd)>th {
   background-color: red; // Choose your own color here
 }
  */
.table-striped>tbody>tr:nth-child(2n+1)>td, .table-striped>tbody>tr:nth-child(2n+1)>th
	{
	background-color: #A6DFC1;
}

.column {
	width: 100%;
	float: left;
	padding-bottom: 30px;
}

.portlet-header { //
	background-image: url("bg_table_th.gif"); //
	margin: 0.3em;
	height: 40px;
	padding-top: 5px;
	padding-bottom: 8px;
	padding-left: 0.2em;
	border: 1px solid #ccc;
	border-bottom-left-radius: 0px;
	border-bottom-right-radius: 0;
}

.portlet-header .ui-icon {
	float: right;
	margin-top: 8px;
}

#newemp .portlet table tr td {
	width: 33%;
	padding-top: 2px;
	padding-bottom: 2px;
}

.form-control {
	height: 30px;
}

/* #employee_form label.error {
	color: #FB3A3A;
	margin: 4px 0 5px 12px;
	padding: 0;
	text-align: left;
} */
.form-group .required:after {
	content: "*";
	color: red;
}

.mt {
	/* margin-top: 80px; */
	
}

/* .site-min-height {
	min-height: calc(100vh -     100px);
	padding: 0px;
} */
.fa-check {
	color: green;
	padding: 5px;
	font-size: 24px;
}

.fa-times {
	color: red;
	padding: 3px;
	font-size: 24px;
}

.fa-edit {
	padding: 3px;
	color: #0f7965;
	font-size: 24px;
}

.fa-undo {
	padding: 3px;
	color: #E71D34;
	font-size: 22px;
}

.dt_col_hide {
	display: none;
}

.radio_parent>* {
	padding-right: 10px;
}

.parent_width>* {
	width: 100%;
}

select {
	width: 100%;
	height: 30px;
	padding: 5px;
	color: green;
}

select option {
	color: black;
}

select option:first-child {
	color: green;
}

.incolor {
	color: green;
}

//
Today
.datepicker table tr td.today {
	color: #000;
	background-color: greenyellow;
	border-color: #FFF; &:
	hover: hover{                             
    background-color:                              #EEE;
}

}
//
Active  (selected day                          
	   ) .datepicker table tr td.active.active {
	background-color: green;
	border-color: #FFF;
	&:
	hover
	{
	background-color
	:
	green;
}

}
a:hover, a:focus {
	outline: none;
	text-decoration: none;
}

.tab .nav-tabs {
	padding-left: 15px;
	border-bottom: 4px solid #692f6c;
}

.tab .nav-tabs li a {
	color: #fff;
	padding: 10px 20px;
	margin-right: 10px;
	background: #692f6c;
	text-shadow: 1px 1px 2px #000;
	border: none;
	border-radius: 0;
	opacity: 0.5;
	position: relative;
	transition: all 0.3s ease 0s;
}

.tab .nav-tabs li a:hover {
	background: #692f6c;
	opacity: 0.8;
}

.tab .nav-tabs li.active a {
	opacity: 1;
}

.tab .nav-tabs li.active a, .tab .nav-tabs li.active a:hover, .tab .nav-tabs li.active a:focus
	{
	color: #fff;
	background: #692f6c;
	border: none;
	border-radius: 0;
}

.tab .nav-tabs li a:before, .tab .nav-tabs li a:after {
	content: "";
	border-top: 42px solid transparent;
	position: absolute;
	top: -2px;
}

.tab .nav-tabs li a:before {
	border-right: 15px solid #692f6c;
	left: -15px;
}

.tab .nav-tabs li a:after {
	border-left: 15px solid #692f6c;
	right: -15px;
}

.tab .nav-tabs li a i, .tab .nav-tabs li.active a i {
	display: inline-block;
	padding-right: 5px;
	font-size: 15px;
	text-shadow: none;
}

.tab .nav-tabs li a span {
	display: inline-block;
	font-size: 14px;
	letter-spacing: -9px;
	opacity: 0;
	transition: all 0.3s ease 0s;
}

.tab .nav-tabs li a:hover span, .tab .nav-tabs li.active a span {
	letter-spacing: 1px;
	opacity: 1;
	transition: all 0.3s ease 0s;
}

.tab .tab-content {
	padding: 30px;
	background: #fff;
	font-size: 16px;
	color: #6c6c6c;
	line-height: 25px;
}

.tab .tab-content h3 {
	font-size: 24px;
	margin-top: 0;
}

@media only screen and (max-width: 479px) {
	.tab .nav-tabs li {
		width: 100%;
		margin-bottom: 5px;
		text-align: center;
	}
	.tab .nav-tabs li a span {
		letter-spacing: 1px;
		opacity: 1;
	}
}

.site-min-height {
	min-height: calc(100vh - 100px);
	padding-top: 10px;
	padding-left: 2px;
	padding-right: 2px background-color: white;
}

.col-lg-12 {
	position: relative;
	min-height: 1px;
	padding-right: 2px;
	padding-left: 2px;
}

.tdtext {
	background: #cccccc
		url("images/ui-bg_highlight-soft_75_cccccc_1x100.png") 50% 50%
		repeat-x;
	color: #222222;
	font-weight: bold;
	padding-right: 10px;
	color: #222222;
}

.tdborder {
	border-style: hidden !important;
}

.no-bottom-border {
	border-bottom: hidden !important;
}

.no-top-border {
	border-top: hidden !important;
}

.uiblock {
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	background-color: #000;
	opacity: .75;
	z-index: 9999999;
}

.modal-lg {
	max-width: 100% !important;
}

.modal-tc {
	max-width: 200px;
}

.modal-dialog {
	position: relative;
	display: table; /* This is important */
	overflow-y: auto;
	overflow-x: auto;
	width: 80% !important;
	min-width: 500px;
}

thead input {
	width: 100%;
}

.centerblk {
	display: block;
	margin-left: auto;
	margin-right: auto;
	width: 50%;
}
</style>
<!--  <script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>-->
	<script src="resources/assets/online/js/cloudflare_jquery.min.js"></script>
	<script src="resources/assets/online/js/googleapis_jquery.min.js"></script>
<script src="resources/assets/custom/jquery.multiselect.js"></script>
<script type="text/javascript">
	//Array holding selected row IDs
	var rows_selected = [];
	var selected_roles = [];
	// zero padding? Not really rounding. You'll have to convert it to a string since numbers don't make sense with leading zeros. Something like this...
	/*function resetDate() {
		$("#todate").attr('disabled', 'disabled');
		$("#fromdate").datepicker("option", "maxDate", null);
	}*/
	function pad(num, size) {
		var s = num + "";
		while (s.length < size)
			s = "0" + s;
		return s;
	}

	// know you'd never be using more than X number of zeros this might be better. This assumes you'd never want more than 10 digits.

	function pad(num, size) {
		var s = "000000000" + num;
		return s.substr(s.length - size);
	}
	function isParam(param, sbu) {
		console.log();
		return param === sbu;
	}
	function downloadFile(urlToSend) {
		var req = new XMLHttpRequest();
		req.open("GET", 'generateReport', true);
		req.responseType = "blob";
		req.onload = function(event) {
			var blob = req.response;
			var fileName = req.getResponseHeader("fileName"); //if you have the fileName header available
			var link = document.createElement('a');
			link.href = window.URL.createObjectURL(blob);
			link.download = fileName;
			link.click();
		};

		req.send();
	}
	function updateDataTableSelectAllCtrl(table) {
		var $table = table.table().node();
		var $chkbox_all = $('tbody input[type="checkbox"]', $table);
		var $chkbox_checked = $('tbody input[type="checkbox"]:checked', $table);
		var chkbox_select_all = $('thead input[name="select_all"]', $table)
				.get(0);
		console.log(JSON.stringify($chkbox_all) + " <<||>> "
				+ JSON.stringify($chkbox_checked) + "  <<||>> "
				+ JSON.stringify(chkbox_select_all));
		// If none of the checkboxes are checked
		//	return;
		/*     if(chkbox_select_all.checked){
		   	 $('#tcodetable tbody input[type="checkbox"]:not(:checked)')
				.trigger('click');return;
		     } */
		if ($chkbox_checked.length === 0) {
			chkbox_select_all.checked = false;
			if ('indeterminate' in chkbox_select_all) {
				chkbox_select_all.indeterminate = false;
			}

			// If all of the checkboxes are checked
		} else if ($chkbox_checked.length === $chkbox_all.length) {
			chkbox_select_all.checked = true;
			if ('indeterminate' in chkbox_select_all) {
				chkbox_select_all.indeterminate = false;
			}

			// If some of the checkboxes are checked
		} else {
			chkbox_select_all.checked = true;
			if ('indeterminate' in chkbox_select_all) {
				chkbox_select_all.indeterminate = true;
			}
		}
	}

	$(document).ready(function() {
		$(".ui-widget-content").css('background', 'transparent');
		$(".column").sortable({
		//	connectWith : ".column",
			handle : ".portlet-header",
		//	cancel : ".portlet-toggle",
		///	placeholder : "portlet-placeholder ui-corner-all"
		});
		$('.modal-dialog').draggable({
			handle : ".modal-header",
			containment : 'window' //or another element/container
		// containment: '#container'
		});

	});
	$(".tab-item").on('click', function(e) {
		alert($(this).parent());
		$(this).parent().addClass("active") // Add active-page class to the clicked link.
		.siblings().parent() // Select clicked link brothers.
		.removeClass("active"); // Remove active-page class if exist from clicked link brothers.
	});
	/* 	$(".grid-tab > li > a").click(function(){
			
			  $(".grid-tab > li").removeClass('active');
			  if(!$(this).hasClass('active')){
				  alert("grid-tab 11" );
			    
			  }
			  $(this).parent().addClass('active');
			  alert("grid-tab");
			});
	 */
</script>
</head>

<body>

	<section id="container">
		<!-- **********************************************************************************************************************************************************
      TOP BAR CONTENT & NOTIFICATIONS
      *********************************************************************************************************************************************************** -->
		<!--header start-->
		<jsp:include page="common/layout/header.jsp" />
		<!--header end-->

		<!-- **********************************************************************************************************************************************************
      MAIN SIDEBAR MENU
      *********************************************************************************************************************************************************** -->
		<!--sidebar start-->
		<jsp:include page="common/layout/navigation_vertical.jsp" />
		<!--sidebar end-->

		<!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
		<!--main content start-->
		<section id="main-content">
			<!--/wrapper start -->
			<jsp:include page="${partial}" />
			<!--/wrapper  end-->
		</section>
		<!-- /MAIN CONTENT -->

		<!--main content end-->
		<!--footer start
		<jsp:include page="common/layout/footer.jsp" />-->
		<!--footer end-->
	</section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!-- <script src="resources/assets/js/jquery.js"></script> -->


	<script src="resources/assets/js/bootstrap.min.js"></script>

	<script src="resources/assets/js/jquery.ui.touch-punch.min.js"></script>
	<script class="include" type="text/javascript"
		src="resources/assets/js/jquery.dcjqaccordion.2.7.js"></script>
	<script src="resources/assets/js/jquery.scrollTo.min.js"></script>
	<script src="resources/assets/js/jquery.blockUI.js"></script>

	<script src="resources/assets/js/jquery.nicescroll.js"
		type="text/javascript"></script>
	<!-- 	<script src="resources/assets/media/js/jquery.dataTables.js"></script>  
	<script type="text/javascript"
		src="https://cdn.datatables.net/v/bs4/dt-1.11.3/datatables.min.js"></script>-->
	<script src="resources/assets/online/js/datatables.min.js"></script>
	<script src="resources/assets/media/js/dataTables.responsive.js"></script>
	<script type="text/javascript"
		src="resources/assets/protlet/protlet.js"></script>
	<script type="text/javascript"
		src="resources/assets/autocomplete/jquery.immybox.min.js"></script>

	<script src="resources/assets/jquery-ui-1.11.4/jquery-ui.js"></script>
	<!--common script for all pages-->
	<script src="resources/assets/js/common-scripts.js"></script>
	<script src="resources/assets/custom/jquery.validate.min.js"></script>
	<script src="resources/assets/online/js/sweetalert2.all.min.js"></script>
	<!--  <script src="resources/assets/sweetalert/lib/sweet-alert.js"></script>-->
	
	<!-- SweetAlert2
	
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.2.0/sweetalert2.all.min.js"></script> -->
	<script type="text/javascript">
		$(function() {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				//Disable submit button
				$(this).prop('disabled', true);

				var form = document.forms[0];
				var formData = new FormData(form);

				// Ajax call for file uploaling
				var ajaxReq = $.ajax({
					url : 'uploadfile',
					type : 'POST',
					data : formData,
					cache : false,
					contentType : false,
					processData : false,
					xhr : function() {
						//Get XmlHttpRequest object
						var xhr = $.ajaxSettings.xhr();
						$("#wait").css("display", "block");
						//Set onprogress event handler 
						/*xhr.upload.onprogress = function(
								event) {
							var perc = Math
									.round((event.loaded / event.total) * 100);
							$('#progressBar').text(
									perc + '%');
							$('#progressBar')
									.css('width',
											perc + '%');
						};*/
						return xhr;
					},
					beforeSend : function(xhr) {
						//Reset alert message and progress bar
						$('#alertMsg').text('');
						$('#progressBar').text('');
						$('#progressBar').css('width', '0%');
					}
				});

				// Called on success of file upload
				ajaxReq.done(function(msg) {
					$('#alertMsg').css('color', 'green');
					$("#wait").css("display", "none");
					$('#alertMsg').text(msg);
					$('input[type=file]').val('');
					$('button[type=submit]').prop('disabled', false);
				});
				//+ '('+ jqXHR.status + ' - '
				//				+ jqXHR.statusText + ')'
				// Called on failure of file upload
				ajaxReq.fail(function(jqXHR) {
					$('#alertMsg').css('color', 'red');
					$("#wait").css("display", "none");
					$('#alertMsg').text(jqXHR.responseText);
					$('button[type=submit]').prop('disabled', false);
					$('#progressBar').text('0%');
					$('#progressBar').css('width', '0%');
				});
			});
		});
	</script>

	<!--script for this page-->



</body>
</html>
