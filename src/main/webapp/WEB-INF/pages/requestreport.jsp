
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%-- <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="listByBrandCode" value="/listByBrandCode" />

<script>
	$(document)
			.ready(
					function() {

						$('#barndCode')
								.change(
										function() {
											$
													.getJSON(
															'${listByBrandCode}',
															{
																brandCode : $(
																		this)
																		.val(),
																ajax : 'true'
															},
															function(data) {
																//console.log(JSON.stringify(data));
																var html = '<option value="">Select Product</option>';
																//var html = '';
																var len = data.length;
																for (var i = 0; i < len; i++) {
																	html += '<option value="' + data[i].productCode + '">'
																			+ data[i].productName
																			+ '</option>';
																}
																html += '</option>';
																$(
																		'#productCode')
																		.html(
																				html);
															});
										});

						$
								.ajax({
									type : 'GET',
									url : "<c:url value="/listDistributer"/>",
									dataType : "json",
									async : false,
									success : handleData,
									error : function(xhRequest, ErrorText,
											thrownError) {
										//alert("Not Save");
										//console.log(xhRequest.status);
										//console.log(ErrorText);
										//console.log(thrownError);
									}
								});
						function handleData(data) {
							//console.log(JSON.stringify(data));
							OUcodeList = data.map(function(item) {
								// console.log(item.distributer_NAME);
								return {
									"text" : item.login_ID,
									"value" : item.distributer_NAME
								};
							});
							$('#distributerCode')
									.immybox(
											{
												choices : OUcodeList,
												defaultSelectedValue : '',
												maxResults : 2000,
												formatChoice : function(query) {
													const
													reg = new RegExp("("
															+ query + ")", "i");
													//return choice => return ();
													return function(choice) {
														return ("<div class='mdl-grid mdl-grid--no-spacing'>"
																+ "<div class='mdl-cell mdl-cell--6-col'>"
																+ [
																		choice.text
																				.replace(
																						reg,
																						"<u>$1</u>"),
																		choice.value ]
																		.join(", ")
																+ "</div>" + "</div>");
													}
												}
											});
						}

						function getWLevel(sel) {
							alert(sel.value);
						}
						var table;
						var param = "ved55";
						$(document).ajaxStart(function() {
							$("#wait").css("display", "block");
						});
						$(document).ajaxComplete(function() {
							$("#wait").css("display", "none");
						});

						function downloadFile() {
							var req = new XMLHttpRequest();
							req.open("GET", 'generateReport', true);
							req.responseType = "blob";
							req.onload = function(event) {
								var blob = req.response;
								var fileName = req
										.getResponseHeader("fileName") //if you have the fileName header available
								var link = document.createElement('a');
								link.href = window.URL.createObjectURL(blob);
								link.download = fileName;
								link.click();
							};

							req.send();
						}

						/*		table = $("#example").DataTable({
									                   select: {
						                                style: 'single'
						                                },
						                                "bFilter" : false,               
						                                "bLengthChange": false,
														"responsive" : true,
														"bProcessing" : true,
														"bServerSide" : true,
														//Default: Page display length
														"iDisplayLength" : 10,
														//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
														"iDisplayStart" : 0,
														"sAjaxSource" : "workflow/"+param,
						                                "fnServerParams": function (aoData) {
						                                                                    aoData.push({ "name": "firstCriteria", "value": param });},											
														"aaSorting" : [ [ 1, "asc" ]],											
														"columnDefs" : [
														         {
																	"name" : "sdai_RECID",
																	"targets" : 0,
																	"visible": false,
																	"searchable" : false,
																	"orderable" : false                                                       														
																},
																{
																	"name" : "created_ON",
																	"targets" : 1,
																	"searchable" : false,
																	"orderable" : false,
																	"className" : 'dt-body-center'
																}, {
																	"name" : "created_BY",
																	"targets" : 2,
																	"searchable" : false,
																	"orderable" : false,
																	"className" : 'dt-body-center'
																} , {
																	"name" : "status",
																	"targets" : 3,
																	"searchable" : false,
																	"orderable" : false,
																	"className" : 'dt-body-center'
																}, {
																	"name" : "barcode",
																	"targets" : 4,
																	"searchable" : false,
																	"orderable" : false,
																	"className" : 'dt-body-center'
																},
																{
																	"name" : "scan_TYPE",
																	"targets" : 5,
																	"searchable" : false,
																	"orderable" : false,
																	"className" : 'dt_col_hide'
																}],
														"aoColumns" : [
						                                {
															"mData" : "sdai_RECID"
														},{
															"mData" : "created_ON"
														}, {
															"mData" : "created_BY"
														}, {
															"mData" : "status"
														},  {
															"mData" : "barcode"
														},{
															"mData" : "scan_TYPE"
														}]
													});*/

						$('#fromdate').datepicker(
								{
									dateFormat : 'dd/mm/yy',
									weekStart : 0,
									startDate : "0d",
									todayBtn : "linked",
									changeYear : true,
									language : "it",
									autoclose : true,
									maxDate : '0',
									todayHighlight : true,
									onSelect : function(selected) {
										$("#todate").removeAttr('disabled');
										$("#todate").datepicker("option",
												"minDate", selected)
									}
								});

						$('#todate').datepicker(
								{
									dateFormat : 'dd/mm/yy',
									weekStart : 0,
									startDate : "0d",
									todayBtn : "linked",
									changeYear : true,
									language : "it",
									autoclose : true,
									maxDate : '0',
									todayHighlight : true,
									onSelect : function(selected) {
										$("#fromdate").datepicker("option",
												"maxDate", selected)
									}
								});
					});
</script>
<section class="wrapper site-min-height">



	<div class="col-lg-12 column">
		<div class="portlet">
			<div class="portlet-header">
				<span class="ui-text"> Date-wise distributer-wise scan-in/out
					details for outer cartons:<mark>(Case-wise details of scanned in/out cases) </mark></span>
				<!-- <sec:authorize access="isAuthenticated()">
                              YES, you are logged in!
               </sec:authorize> -->
			</div>
			<div class="portlet-content form-group">
				<div id="wait"
					style="display: none; width: 69px; height: 89px; border: 1px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
					<img src='resources/assets/img/ajax-loader.gif' width="64"
						height="64" /><br>Loading..
				</div>
				<!--<form id="employee_form">-->
				<c:set var="root" value="${pageContext.request.contextPath}" />
				<form:form method="POST" action="${root}/generateReport"
					modelAttribute="jasperInputForm" target="_blank">
					<div class="showback">
						<!--	<button class="btn btn-theme02" type="button" id="cnwf" onclick="downloadFile();">
							<i class="glyphicon glyphicon-ok"></i> Search 
						</button>-->
						<button class="btn btn-theme02" type="submit" id="cnwf">
							<i class="glyphicon glyphicon-ok"></i> Download Report
						</button>
						<button class="btn btn-theme02" type="reset" id="cnwf">
							<i class="glyphicon glyphicon-ok"></i> Reset Form
						</button>
					</div>

					<table class="table table-bordered table-striped  table-condensed "
						id="newemp" style="">
						<%-- <tr>
							<td colspan="4"><form:errors path="noofYears"
									cssClass="error" /></td>
						</tr> --%>
						<tr style="width: 100%">
							<td style="width: 15%">Scan Type</td>
							<td colspan="3" class="radio_parent"><form:radiobuttons
									path="reportType" items="${jasperRptTypes}" /></td>
						</tr>
						<tr style="width: 100%">
							<td style="width: 15%">Report Output</td>
							<td class="radio_parent" style="width: 35%"><form:radiobuttons
									path="rptFmt" items="${jasperRptFormats}" /></td>
							<td style="width: 15%">Select Distributer</td>
							<td class="parent_width" style="width: 35%"><input
								id="distributerCode" name="distributerCode"
								placeholder="Select Distributer Code"
								class="form-control immybox immybox_witharrow incolor"
								type="text"> <%-- <form:select path="distributerCode">
							<form:option value="" label="Select Distributer" />
									<form:options 
									items="${allDisributors}" itemValue="LOGIN_ID"
									itemLabel="DISTRIBUTER_NAME" />
									</form:select> --%></td>
						</tr>
						<tr style="width: 100%">
							<td style="width: 15%">Select Brand</td>
							<td class="parent_width" style="width: 35%"><form:select
									id="barndCode" path="barndCode">
									<form:option value="" label="Select Brand" />
									<form:options items="${allBrands}" itemLabel="brandCode"
										itemValue="brandCode" />
								</form:select>
								<input type="hidden" name="reportName"
								value="qtyingroup">
								</td>

							<td style="width: 15%">Select Product</td>
							<td class="parent_width" style="width: 35%"><form:select
									id="productCode" path="productCode">
									<form:option value="" label="Select Product" />
									<%-- <form:options items="${allProducts}" itemValue="productCode" itemLabel="productName" /> --%>
								</form:select></td>

						</tr>
						<tr style="width: 100%">
							<td style="width: 15%">From Date</td>
							<td style="width: 35%"><div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"> </i>
									</div>
									<input class="form-control datepopup" id="fromdate"
										name="startDate" placeholder="dd/MM/yyyy" type="text" readonly />
								</div></td>
							<td style="width: 15%">To Date</td>
							<td style="width: 35%"><div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"> </i>
									</div>
									<input class="form-control datepopup" id="todate"
										name="endDate" placeholder="dd/MM/yyyy" type="text" readonly
										disabled />
								</div> <!--<input class="form-control datepopup" type="text" />--></td>
						</tr>

					</table>
				</form:form>
			</div>
		</div>
		<!-- /content-panel -->
	</div>

	<div class="row mt"></div>


</section>




