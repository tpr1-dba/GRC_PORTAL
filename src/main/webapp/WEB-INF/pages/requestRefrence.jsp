<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script>
	$(document)
			.ready(
					function() {
						$(function() {
						
							$('.menulist').each(
									function() {
										if (window.location.href.indexOf($(this).find(
												'a:first').attr('href')) > -1) {
											$(this).addClass('active').siblings()
													.removeClass('active');
										}
									});
						});
		
						/*$("#wait").css("display", "block");
						$.blockUI({
							css : {
								border : 'none',
								padding : '15px',
								backgroundColor : '#000',
								'-webkit-border-radius' : '10px',
								'-moz-border-radius' : '10px',
								opacity : .5,
								color : '#fff'
							}
						});*/
						
						$('select[multiple]').multiselect({
							columns : 1
						});

						
						$('input[type=radio][name=fortime]').change(function() {
							 
						    if (this.value == "temporary") {

						    	// $(".vplant").hide();
								   $("#fortime").show();	

						    }else if (this.value == "allTime") {

						    	 $("#fortime").hide();
								//  $(".vpgroup").show();	

						    }

						});
						$('#fromdate').datepicker({
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
								$("#todate").datepicker("option", "minDate", selected)
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
										$("#fromdate").datepicker("option", "maxDate",
												selected)
									}
								});
					
				
						$(document).ajaxStart(function() {
							//alert("ajaxStart");
							$("#wait").css("display", "block");
							$.blockUI({
								css : {
									border : 'none',
									padding : '15px',
									backgroundColor : '#000',
									'-webkit-border-radius' : '10px',
									'-moz-border-radius' : '10px',
									opacity : .5,
									color : '#fff'
								}
							});
						});
						$(document).ajaxComplete(function() {
							$.unblockUI();
							$("#wait").css("display", "none");
						});
						$(document).ajaxStop($.unblockUI());
					});
</script>
<section class="wrapper site-min-height"  style="padding:0px;">
<div class="col-md-12" style="padding-right: 0px;  padding-left: 0px;">
				<div class="tab" role="tabpanel" >
					<!-- Nav tabs -->
						<ul class="grid-tab nav nav-tabs" role="tablist">
						<li class="active menulist" role="presentation"><a href="request"
							aria-controls="request" role="page" data-toggle="page"><i
								class="fa fa-New"></i><span>New Request T-code</span></a></li>
						 <li class="menulist" role="presentation"><a href="requestRole"
							aria-controls="requestRole" role="page" data-toggle="page"><i
								class="fa fa-New"></i><span>New Request Role</span></a></li> 
						 <li class="menulist" role="presentation"><a href="requestRefrence" aria-controls="requestRefrence"
							role="page" data-toggle="page"><i class="fa fa-New"></i><span>Employee Reference</span></a></li>				
					</ul>

				</div>
			</div>
	<div id="wait" class="uiblock"
		style="display: none; width: 169px; height: 189px; border: 1px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
		<img src='resources/assets/img/ajax-loader.gif' width="164"
			height="164" /><br>
	</div>
	<div class="col-lg-12 column" style="width: 100%">
		<div class="portlet">
			<div class="portlet-header">
				<span class="ui-text">New request for Role</span>
				<!--  <mark></mark> -->
			</div>
			<form id="requestForm">
				<div class="portlet-content form-group">

					<input type="hidden" id="tcodeId" name="tcodeId"> <input
						type="hidden" id="tcode" name="tcode">


					<table class="table table-bordered table-striped  table-condensed "
						id="" style="">

						<tr style="width: 100%">							
							<td style="width: 10%">SAP Compnay</td>
							<td style="width: 40%"><select id="sapCompanyCode"
								name="sapCompanyCode[]" multiple="multiple" class="4colactive">
							</select></td>
							<td style="width: 10%"><label for="emp_code">Employee</label></td>

							<td style="width: 40%"><input id="emp_code" name="emp_code"
								placeholder="Select employee"
								class="form-control immybox immybox_witharrow tcode" type="text"
								value=""></td>					
						</tr>
					
					<!--  	<tr style="width: 100%;display: none;" id="fortime">
							<td style="width: 10%">From Date</td>
							<td style="width: 40%"><div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"> </i>
									</div>
									<input class="form-control datepopup" id="fromdate"
										name="startDate" placeholder="dd/MM/yyyy" type="text" readonly />
								</div></td>
							<td style="width: 10%">To Date</td>
							<td style="width: 40%"><div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"> </i>
									</div>
									<input class="form-control datepopup" id="todate"
										name="endDate" placeholder="dd/MM/yyyy" type="text" readonly
										disabled />
								</div> 
						</tr>-->


						<tr style="width: 100%">
							<td style="width: 10%">Reason for role access</td>
							<td style="width: 30%" colspan="3"><input type="text"
								name="reason" class="form-control" id="reason"
								placeholder="Enter reason for role access"></td>
						</tr>
					</table>
					<div class="showback">
						<!--  <button id="getTocdeBtn" type="button" class="btn btn-success">
							<i class="fa fa-save"></i> Add role
						</button>-->
						<button id="requestTocdeBtn" type="button" class="btn btn-success">
							<i class="fa fa-save"></i> Final Submit
						</button>
					</div>


				</div>
			</form>
		</div>

		<!-- /content-panel -->
	</div>
</section>