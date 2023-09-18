<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script>
	$(document)
			.ready(
					function() {
						
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
						
						var userRequestDataTable=null;
						userRequestDataTable = $("#userRequestDataTable").DataTable({
							//stateSave: true,
							select : {
								'style' : 'single'
							},
							
							"stripeClasses" : [ 'odd-row', 'even-row' ],
							"dom" : 'lf<"toolbars">rtip',
							"responsive" : true,
							"bProcessing" : true,
							"bServerSide" : true,
							//Default: Page display length
							"iDisplayLength" : 10,
							//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
							"iDisplayStart" : 0,
							"sAjaxSource" : "getUserRequest",
							"aaSorting" : [ [ 0, "asc" ] ],	
							"aaSorting" : [],
							"columnDefs" : [ {
								"orderable" : false,
								"targets" : [ 0 ]
								}, {
									'targets' : 0,
									"searchable" : false,
									"orderable" : false,
									"className" : 'dt-body-center',
									"render" : function(data, type, full, meta){
							               if(type === 'display'){
								                  data = '<input type="radio" name="id" value="' + data.surRecid + '">';      
								               }

								               return data;
								            }


								},	
								{
								"name" : "surRecid",
								"targets" : 1,
								"defaultContent" : "",
								"searchable" : false,
								"orderable" : false,
								"className" : 'dt_col_hide'
								
							},							
							{
								"name" : "tcode",
								"targets" : 2,
								"defaultContent" : "",
								"searchable" : false,
								"orderable" : false,
								"className" : 'dt-body-center'
								}, {
								"name" : "status",
								"targets" : 3,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "sapCompanyCode",
								"targets" : 4,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "empCode",
								"targets" : 5,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "plant",
								"targets" : 6,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "approvedBy",
								"targets" : 7,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "reason",
								"targets" : 8,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "typeOfRight",
								"targets" : 9,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							} ],
							"aoColumns" : [  {
								"mData" : "surRecid"
								},{
								"mData" : "surRecid"
								},{
								"mData" : "tcode"
								},{
								"mData" : "status"
								},{
								"mData" : "sapCompanyCode"
								},{
								"mData" : "empCode"
								},{
								"mData" : "plant"
								},{
								"mData" : "approvedBy"
								},{
								"mData" : "reason"
								}, {
								"mData" : "typeOfRight"
							} ],	
							"rowCallback" : function(row,
							data, dataIndex) {									
								console
								.log("row "
								+ JSON
								.stringify(data)
								
								
								+ "  "
								+ dataIndex
								+ "  "
								+ JSON
								.stringify(data.functios));
								
								
							}
						});
						$("div.toolbars").html($('#toolbar').html());
					
						$('#userRequestDataTable').on('draw', function() {
							// Update state of "Select all" control
							console.log('userRequestDataTable Change 1');
							$('#assgin_role_form').trigger("reset");
							$("#parentRoleId").val("");
							$("#roleCode").val("");
							$("#empCode").val("");
							$("#funCode").val("");
							$("#funId").val("");
							$('#sapEmpCode').val("");
							 rows_selected=[];
							//updateDataTableSelectAllCtrl(assignrolestable);
						});
						$('#userRequestDataTable').on('draw.dt', function() {
							console.log('userRequestDataTable Change 2');
							$('#assgin_role_form').trigger("reset");
							$("#parentRoleId").val("");
							$("#roleCode").val("");
							$("#empCode").val("");
							$('#sapEmpCode').val("");
							$("#funCode").val("");
							$("#funId").val("");
							 rows_selected=[];
							//updateDataTableSelectAllCtrl(assignrolestable);

						}); 
                         var requestedtcode=null;
						$('#userRequestDataTable tbody').on(
								'click',
								'tr',
								function() {
									/* if($(this).hasClass('odd-row')){
									
										$(this).find('td').css('background-color', 'transparent');
										}  */
										var $row = $(this).closest('tr');
										var data = userRequestDataTable.row($row).data();
									
									    requestedtcode=null;
									if ($(this).hasClass('selected')) {												
										$(this).removeClass('selected');
										$(this).find('input:radio').prop('checked', false);
										if ($(this).hasClass('odd-row')) {
											$(this).find('td').css(
													'background-color',
													'#cfe2ff');
										}
									} else {
										userRequestDataTable.$('tr.selected')
												.removeClass('selected');

										userRequestDataTable.$('tr.odd-row').each(
												function() {
													$(this).find('td').css(
															"background-color",
															"#cfe2ff");
												});
										if ($(this).hasClass('odd-row')) {

											$(this).find('td').css(
													'background-color',
													'transparent');
										}
										//roleuser = roletousertable.row(this).data();
										requestedtcode=data;
										// rows_selected.push({"roleId": roleId,"roleCode": roleCode,"roleName": roleName, "funId": funId, "funCode": funCode });
										// rows_selected.push({"funId":funId,"roleId":roleId,"roleCode":roleCode,"funCode":funCode,"roleName":roleName});
										console.log(JSON.stringify(requestedtcode));
									//	roleuserseleted = true;
										$(this).addClass('selected');
										$(this).find('input:radio').prop('checked', true);
									}
								});
						$('#userRequestDataTable_wrapper #btnGetRoles').on('click',
								function(e) {				
						   // alert("Hello "+requestedtcode);
							if(isEmpty(requestedtcode)){								
								swal("Select Request tocde for get roles",
								"Select row for get roles for tcode.");
								return;
							   }
							getRoles(requestedtcode);	
																		
						});
						function getRoles(requestedtcode){
							//	assignrolestable.ajax
						//		.url("getRolesByTcode/"+tcode)
							//	.load();
							if (isEmpty(requestedtcode.tcode))
											return;
							assignrolestable.ajax
							.url("getRolesByTcode/"+encodeURIComponent(requestedtcode.tcode))
							.load();

							};
							function isEmpty(val) {
								return (val === undefined || val == null || val.length <= 0) ? true
										: false;
							};		
				var assignrolestable = $("#assignrolestable")
							.DataTable(
									{

										select : {
											style : 'single'
										},

										"stripeClasses" : [ 'odd-row',
												'even-row' ],
										"dom" : 'lfrtip',
										"responsive" : true,
										"bProcessing" : true,
										"bServerSide" : true,
										//Default: Page display length
										"iDisplayLength" : 10,
										//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
										"iDisplayStart" : 0,
										"sAjaxSource" : "getRolesByTcode/null",
										"aaSorting" : [ [ 1, "asc" ] ],
										"data": [],
										"columnDefs" : [
											
												{
													'targets' : 0,
													"searchable" : false,
													"orderable" : false,
													"className" : 'dt-body-center',
													"render" : function(data, type, full, meta){
											               if(type === 'display'){
												                  data = '<input type="radio" name="id" value="' + data.roleId + '">';      
												               }

												               return data;
												            }


												},
												{
						                           "name" : "roleId",
						                           "targets" : 1,
						                           "defaultContent" : "",
						                           "searchable" : false,
						                           "orderable" : false,
						                           "className" : 'dt_col_hide'
					                            },
												
												{
													"name" : "roleCode",
													"targets" : 2,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												},
												
												{
													"name" : "funCode",
													"targets" : 3,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												},
												{
													"name" : "module",
													"targets" : 4,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												},
												{
													"name" : "entity",
													"targets" : 5,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												} ],
										"aoColumns" : [ {
											"mData" : "roleId"
										}, {
											"mData" : "roleId"
										},{
											"mData" : "roleCode"
										}, {
											"mData" : "funCode"
										}, {
											"mData" : "module"
										}, {
											"mData" : "entity"
										}]
									});
			//	$("div.toolbarb").html($('#toolbarrole').html());
				$('#tcode').on('update',
						function(element, newValue) {	
					//console.log(JSON.stringify(userdata));
					//if(!isEmpty(newValue) && newValue==$("#tcode").immybox('getValue')){
							assignrolestable.ajax
								.url("getRolesByTcode/"+encodeURIComponent(newValue))
								.load();
					//}
				});
				
						
						
						
						
						
						
						
						
		

				/*$('#assignrolestable').on(
						'click',
						'tbody input[type="checkbox"]',
						function(e) {
							var $row = $(this).closest('tr');
							var data = assignrolestable.row($row).data();
						    var roleCode = data.roleCode;
							var roleId = data.roleId;
							var funId = data.funId;
							var funCode = data.funCode;
							var roleName = data.roleName;
						    var index = rows_selected.findIndex(row => (row.funId===funId) && (row.roleId===roleId) && (row.roleCode===roleCode));							
							if (this.checked && index === -1) {								
                                 rows_selected.push({"funId":funId,"roleId":roleId,"roleCode":roleCode,"funCode":funCode,"roleName":roleName});								
							} else if (!this.checked && index !== -1) {
								rows_selected.splice(index, 1);								
							}
							if (this.checked) {
								$row.addClass('selected');
							} else {
								$row.removeClass('selected');
							}							
							updateDataTableSelectAllCtrl(assignrolestable);
							e.stopPropagation();
						});*/
                                 var selectedrole=null;
								$('#assignrolestable tbody').on(
										'click',
										'tr',
										function() {
											/* if($(this).hasClass('odd-row')){
											
												$(this).find('td').css('background-color', 'transparent');
												}  */
												var $row = $(this).closest('tr');
												var data = assignrolestable.row($row).data();
											    var roleCode = data.roleCode;
												var roleId = data.roleId;
												var funId = data.funId;
												var funCode = data.funCode;
												var roleName = data.roleName;																		
											    rows_selected=[];
											    selectedrole=null;
											if ($(this).hasClass('selected')) {												
												$(this).removeClass('selected');
												$(this).find('input:radio').prop('checked', false);
												if ($(this).hasClass('odd-row')) {
													$(this).find('td').css(
															'background-color',
															'#cfe2ff');
												}
											} else {
												assignrolestable.$('tr.selected')
														.removeClass('selected');

												assignrolestable.$('tr.odd-row').each(
														function() {
															$(this).find('td').css(
																	"background-color",
																	"#cfe2ff");
														});
												if ($(this).hasClass('odd-row')) {

													$(this).find('td').css(
															'background-color',
															'transparent');
												}
												//roleuser = roletousertable.row(this).data();
												// rows_selected.push({"roleId": roleId,"roleCode": roleCode,"roleName": roleName, "funId": funId, "funCode": funCode });
												// rows_selected.push({"funId":funId,"roleId":roleId,"roleCode":roleCode,"funCode":funCode,"roleName":roleName});
												selectedrole=data;
												console.log(JSON.stringify(selectedrole));
											//	roleuserseleted = true;
												$(this).addClass('selected');
												$(this).find('input:radio').prop('checked', true);
											}
										});

			
								
								
				$('#assignrolestable').on('draw', function() {
					// Update state of "Select all" control
					console.log('assignrolestable Change 1');
					$('#assgin_role_form').trigger("reset");
					$("#parentRoleId").val("");
					$("#roleCode").val("");
					$("#empCode").val("");
					$("#funCode").val("");
					$("#funId").val("");
					$('#sapEmpCode').val("");
					 rows_selected=[];
					//updateDataTableSelectAllCtrl(assignrolestable);
				});
				$('#assignrolestable').on('draw.dt', function() {
					console.log('assignrolestable Change 2');
					$('#assgin_role_form').trigger("reset");
					$("#parentRoleId").val("");
					$("#roleCode").val("");
					$("#empCode").val("");
					$('#sapEmpCode').val("");
					$("#funCode").val("");
					$("#funId").val("");
					 rows_selected=[];
					//updateDataTableSelectAllCtrl(assignrolestable);

				}); 
						
						
				function jsonConcat(o1, o2) {
					 for (var key in o2) {
					  o1[key] = o2[key];
					 }
					 return o1;
					}

						$('#btassignRoles').on('click',
								function(e) {				
						
							if(isEmpty(selectedrole)){								
								swal("Select role",
								"Select row for assign role.");
								return;
							   }
							resetErrors();
							
							var sdata=	jsonConcat(selectedrole,requestedtcode);
							sdata.status="F";
							delete	sdata.createdOn;
							delete	sdata.requestDate;
							console.log(JSON.stringify(sdata));
							saveRoles(sdata);
						});
                    
						$('#btRejectRoles').on('click',
								function(e) {
							
							if(isEmpty(requestedtcode)){								
								swal("Select tcode ",
								"Select tocde  for reject");
								return;
							   }
							resetErrors();
						var sdata=	jsonConcat(selectedrole,requestedtcode);
						console.log(JSON.stringify(sdata));
						sdata.status="R";
							saveRoles(sdata);
							
						});		
						
						function	saveRoles(data){
							$.ajax({
								contentType : 'application/json; charset=utf-8',
								type : 'POST',
								url : 'approveTcodeRequest',
								data : JSON
								          .stringify(data),
								dataType : 'json',
								async : false,
								success : function(data) {
									console
											.log(JSON
													.stringify(data));

									swal("Success!",
											data.ok,
											"success");									
									$("#UserRoleTables tbody").empty();
									getUserRoleAjax(roleuser.sapEmpCode);
								},
								error : function(
										xhRequest,
										ErrorText,
										thrownError) {
									console
											.log(JSON
													.stringify(xhRequest));
								
									if (xhRequest.status == 400) {
										var responseText = JSON
												.parse(xhRequest.responseText);
										console
												.log(responseText);

										validateForm(responseText);
										return false;
									}

									swal({
										title : "Error",
										text : xhRequest.responseText,
										type : "error",
										showCancelButton : false,
										confirmButtonClass : 'btn btn-theme04',
										confirmButtonText : 'Danger!'
									});
								}
							});

						}
						function	openConflictedModel(data){
							var tableData = data;
							//function createTable(tableData) {
							/* var table = document.
							.createElement('table'); */
							console.log(JSON.stringify(tableData));
							var thead = '<thead><tr style="font-weight:100 !important; color: #fbf6f6;font-size:14px !important;padding:5px !important;background-color: #0480be;">'
							+ '<th style="width: 10%">Seq</th>'
							+ '<th style="width: 10%">Risk Id</th>'
							+ '<th style="width: 20%">Risk Confliction</th>'
							+ '<th style="width: 25%">Rsik</th>'
							+ '<th style="width: 35%">Risk Description</th>'
							+ '</tr></thead>';
							//{"rfId":null,"entity":null,"frDesc":null,"funId":28,"funCode":"AP01","funDesc":"AP01 - AP Payments","module":"FICO","riskCode":null,"riskId":null,"status":null}
							var tagOpen='<span style="color:blue;font-weight:bold;">';
							var redOpen='<span style="color:red;font-weight:bold;">';
							var tagClosed='</span>';
							$('#showttables').html(thead);
							var table = document
							.getElementById("showttables");
							var tableBody = document
							.createElement('tbody');
							var i = 1;
							//	tableData.forEach(function(rowData) {
							//for (let [key, value] of  map.entries()) {	of Object.entries(object)) {		
							for (let [key, value] of Object.entries(tableData)) {		
								//console.log(JSON.stringify(rowData));
								if(value.length>1)	{		
									var row = document
									.createElement('tr');
									var cell1 = document
									.createElement('td');
									cell1.className = "dt-body-center";
									var cell2 = document
									.createElement('td');
									cell2.className = "dt-body-center";
									var cell3 = document
									.createElement('td');
									cell3.className = "dt-body-center";
									var cell4 = document
									.createElement('td');
									cell4.className = "dt-body-center";
									var cell5 = document
									.createElement('td');
									cell5.className = "dt-body-center";
									//class="dt-body-center"
									cell1.appendChild(document
									.createTextNode(i++));// satId ,tcode, tcodeDesc
									cell2.appendChild(document
									.createTextNode(value[0].riskCode));
									cell3.appendChild(document
									.createTextNode(value[0].funCode+" vs "+value[1].funCode));
									cell4.appendChild(document
									.createTextNode(value[0].funDesc+" vs "+value[1].funDesc));
									cell5.appendChild(document
									.createTextNode(value[0].frDesc));
									row.appendChild(cell1);
									row.appendChild(cell2);
									row.appendChild(cell3);
									row.appendChild(cell4);
									row.appendChild(cell5);
									tableBody.appendChild(row);
								}
							}
							//});
							
							table.appendChild(tableBody);
							//document.body.appendChild(table);
							/* document
							.getElementById("showttable").innerHTML = tableBody; */
							$('#showttcodemodal').modal('show');
							$('#showttcodemodal').find('.modal-title').text("SOD :: Assigned  confillcting role to user")
						};		
						
						/*$(document).on('show.bs.modal', '.modal', function (event) {
							var zIndex = 1040 + (10 * $('.modal:visible').length);
							$(this).css('z-index', zIndex);
							setTimeout(function() {
								$('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
							}, 0);
						});	*/	  
						
						
						$('#showttcodemodal').on('hidden.bs.modal', function() {
							//$("#showttable > body").empty()
							$("#showttables").empty();
							//table.html("");
						});
										
						
						

						$('#btsave')
						.click(
								function() {
									
									console
											.log(JSON
													.stringify(getFormData($("#assgin_role_form"))));
									resetErrors();
									$
											.ajax({
												contentType : 'application/json; charset=utf-8',
												type : 'POST',
												url : 'assignRole',
												data : JSON
														.stringify(getFormData($("#assgin_role_form"))),
												dataType : 'json',
												async : false,
												success : function(data) {
													console
															.log(JSON
																	.stringify(data));

													swal("Success!",
															data.ok,
															"success");									
													$("#UserRoleTables tbody").empty();
													
												   // UserRoleTables.ajax.url("searchRoleByUserID/"+encodeURIComponent(roleuser.sapEmpCode)).load();
													//$('#UserRoleTables').dataTable().fnClearTable();
													//$('#UserRoleTables').dataTable().fnAddData(userRoles);
													getUserRoleAjax(roleuser.sapEmpCode);

												},
												error : function(
														xhRequest,
														ErrorText,
														thrownError) {
													console
															.log(JSON
																	.stringify(xhRequest));
												
													if (xhRequest.status == 400) {
														var responseText = JSON
																.parse(xhRequest.responseText);
														console
																.log(responseText);

														validateForm(responseText);
														return false;
													}

													swal({
														title : "Error",
														text : xhRequest.responseText,
														type : "error",
														showCancelButton : false,
														confirmButtonClass : 'btn btn-theme04',
														confirmButtonText : 'Danger!'
													});
												}
											});
									return false;
								});


						
						function resetErrors() {
							$('form input, form select').removeClass(
									'inputTxtError');
							$('label.error').remove();
						}
						;

						function validateForm(responseText) {
							$.each(responseText, function(i, v) {
								console.log(i + " => " + v); // view in console for error messages
								var msg = '<label class="error" for="'+i+'">'
										+ v + '</label>';
								$(
										'input[name="' + i
												+ '"], select[name="' + i
												+ '"]').addClass(
										'inputTxtError').after(msg);
							});
							var keys = Object.keys(responseText);
							$('input[name="' + keys[0] + '"]').focus();
						}
						;

						function getFormData($form) {
							var unindexed_array = $form.serializeArray();
							var indexed_array = {};

							$.map(unindexed_array, function(n, i) {
								indexed_array[n['name']] = n['value'];
							});

							return indexed_array;
						}
						;
				
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
<section class="wrapper site-min-height">

	<div id="wait" class="uiblock"
		style="display: none; width: 169px; height: 189px; border: 1px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
		<img src='resources/assets/img/ajax-loader.gif' width="164"
			height="164" /><br>
	</div>
	<div class="col-lg-12 column" style="width: 100%">
		<div class="portlet">
			<div class="portlet-header">
				<span class="ui-text"> Requested Tocde for approval </span>
				<!--  <mark></mark> -->
			</div>
			<div class="portlet-content form-group">
				<div class="showback"></div>
				
				<table id="userRequestDataTable"
				class="table table-bordered table-striped  table-condensed "
				cellspacing="0" width="100%">
					<thead>
						<tr>	
						    <th style="width: 5%"></th>					   
							<th class="dt_col_hide"></th>
							<th style="width: 10%">T-code name</th>
							<th style="width: 5%">Status</th>
							<th style="width: 10%">SAP Company</th>
							<th style="width: 15%">Employee Name</th>
							<th style="width: 5%">Plant/Purchage GRP</th>
							<th style="width: 10%">Approved By</th>
							<th style="width: 20%">Reason</th>
							<th style="width: 5%">Type Of Right</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<!-- /content-panel -->
	</div>

		
	<div class="col-lg-12 column" style="width: 100%">
	

		<div class="portlet">
			<div class="portlet-header">
				<span class="ui-text">Sap Roles</span>

			</div>
			<div class="portlet-content form-group">			 
			 <form id="assgin_role_form">
					<input type="hidden" id="empCode" name="empCode"> 
					<input 
						type="hidden" id="sapEmpCode" name="sapEmpCode"> <input
						type="hidden" id="roleId" name="roleId"> <input
						type="hidden" id="roleCode" name="roleCode"> <input
						type="hidden" id="funId" name="funId"> <input
						type="hidden" id="funCode" name="funCode">
				
		    <input type="text"
								name="remarks" class="form-control" id="remarks"
								placeholder="Enter remarks">
								</form>
								<div class="showback">
			<button id="btassignRoles" class="btn btn-info" type="button">
				<i class="glyphicon glyphicon-edit"></i> Assign role
			</button> 
			 <button id="btRejectRoles" type="button" class="btn btn-danger ">
				<i class="fa fa-trash "></i> Reject
			</button></div>
		</div>
			
			<div class="portlet-content form-group">
				<div class="showback"></div>
                    

				<table id="assignrolestable"
					class="table table-bordered table-striped  table-condensed "
					cellspacing="0" width="100%">
					<thead>
						<tr>
							<!--  style="background-color: #d9edf7; height: 30px;" -->
							<th style="width: 5%"></th>
							<th class="dt_col_hide"></th>
							<th style="width: 50%">Role code</th>
							<th style="width: 25%">FUN Code</th>
							<th style="width: 10%">Module</th>
							<th style="width: 15%">Entity</th>
						</tr>
					</thead>
				</table>

			</div>
		</div>
		<!-- /content-panel -->
	</div>
	<div class="modal top fade" id="showttcodemodal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		data-backdrop="false" data-keyboard="false">
		<div class="modal-dialog modal-side modal-top-right modal-lg">
			<div class="modal-content">
				<div class="modal-header text-white">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">Tocde</h5>
				</div>
				<div class="modal-body">
					<table id="showttables"
						class="table table-bordered table-striped  table-condensed "
						cellspacing="0">
					</table>
				</div>
			</div>

		</div>
	</div>
	<div id="toolbarrole" style="display: none">
		<span class="showback"> 
		    <input type="text"
								name="remarks" class="form-control" id="remarks"
								placeholder="Enter remarks">
			<button id="btassignRoles" class="btn btn-info" type="button">
				<i class="glyphicon glyphicon-edit"></i> Assign role
			</button> 
			 <button id="delete" type="button" class="btn btn-danger ">
				<i class="fa fa-trash "></i> Reject
			</button>
		</span>
	</div>
	
<div id="toolbar" style="display: none">
		<span class="showback"> <!-- <button id="addRow" type="button" class="btn btn-success">
				<i class="fa fa-plus"></i> Add New
			</button> -->
			<button id="btnGetRoles" class="btn btn-info" type="button">
				<i class="glyphicon glyphicon-edit"></i> Get Role
			</button> <!-- <button id="delete" type="button" class="btn btn-danger ">
				<i class="fa fa-trash "></i> Delete
			</button> -->
		</span>
	</div>
	
</section>
