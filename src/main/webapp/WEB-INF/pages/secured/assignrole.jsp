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
						
						var userList = [];
						var userdata = null;
						var roleuser = null;
						var assignroles = [];
						$
								.ajax({
									type : 'GET',
									url : 'getUsersDetails',
									dataType : "json",
									async : false,
									success : handleDataUsers,
									error : function(xhRequest, ErrorText,
											thrownError) {
										//alert("Not Save");
										console.log(xhRequest.status);
										console.log(ErrorText);
										console.log(thrownError);
									}
								});
						$('#users').on(
								'keyup',
								function() {
									if (isEmpty(this.value))
										return;
									$.ajax({
										type : 'GET',
										url : 'searchUsers/' + this.value,
										dataType : "json",
										async : false,
										success : handleDataUsers,
										error : function(xhRequest, ErrorText,
												thrownError) {
											//alert("Not Save");
											console.log(xhRequest.status);
											console.log(ErrorText);
											console.log(thrownError);
										}
									});
								});						
						function handleDataUsers(data) {
							
						 userdata = data;
							userList = userdata.map(function(code) {
								return {
									"text" : code.employeeName + ", "
											+ code.sapUserid,
									"value" : code.sapUserid
								};
							});
							$('.users')
									.immybox(
											{
												choices : userList,
												defaultSelectedValue : "",
												formatChoice : function(query) {
													const reg = new RegExp("("
															+ query + ")", "i");
													//return choice => return ();
													return function(choice) {
														return ("<div class='mdl-grid mdl-grid--no-spacing'>"
																+ "<div class='mdl-cell mdl-cell--6-col'>"
																+ [

																choice.text
																		.replace(
																				reg,
																				"<u>$1</u>") ]
																		.join(", ")
																+ "</div>" + "</div>");
													}
												}
											});
							console.log(JSON.stringify(userdata));
							$('.users').immybox('setChoices', userList);

							//console.log(JSON.stringify(userdata));
						}
						;
						$('#users').on('update',
								function(element, newValue) {	
							//console.log(JSON.stringify(userdata));
							var selecteduser=userdata.filter( e => (e.sapUserid!=null )&& (e.sapUserid === newValue));
							$("#UserRoleTables tbody").empty();
							$("#UserDetailsTables tbody").empty();	
							var assignroles = [];
							console.log(JSON.stringify(selecteduser));
							roleuser = selecteduser[0];
								if(!isEmpty(roleuser) && !isEmpty(newValue) && roleuser.sapUserid==newValue){
								
									getUserRoleAjax(newValue);
							
							// userDataTable(userRoles);
							 
							}
						   updateUserTable(selecteduser);
						});
						
						function getUserRoleAjax(newValue){
							$.ajax({
								type : 'GET',
								url : 'searchRoleByUserID/' + newValue,
								dataType : "json",
								async : false,
								success : updateUserRole,
								error : function(xhRequest, ErrorText,
										thrownError) {
									//alert("Not Save");
									console.log(xhRequest.status);
									console.log(ErrorText);
									console.log(thrownError);
								}
							});
						}
						
						function updateUserRole(data){					
							
							// $('#UserRoleTables').dataTable().fnClearTable();
							 if(!isEmpty(data)){
							// $('#UserRoleTables').dataTable().fnAddData(data);
							 UserRoleTables(data);
							 assignroles=data;
							 
							 }
						}
						$('#users_old').on('update',
								function(element, newValue) {	
							//console.log(JSON.stringify(userdata));
							var selecteduser=userdata.filter( e => (e.sapUserid!=null )&& (e.sapUserid === newValue));
							$("#UserRoleTables tbody").empty();
							$("#UserDetailsTables tbody").empty();
							
							console.log(JSON.stringify(selecteduser));
							roleuser = selecteduser[0];
								if(!isEmpty(roleuser) && !isEmpty(roleuser.roleId)){
									//console.log(selecteduser.roleId);
									var	proleId=	roleuser.roleId.split(',');
									var	proleCode=	roleuser.roleCode.split(',');
									var	proleName=	roleuser.roleName.split(',');
									var	pfunId=	roleuser.funId.split(',');
									var	pfunCode=	roleuser.funCode.split(',');
							 
							 var userRoles=[];
							 $.each(proleId, function(i, item) {
								 var role={};							 
								 role.roleId=proleId[i];
								 role.roleCode=proleCode[i];
								 role.roleName=proleName[i];
								 role.funId=pfunId[i];
								 role.funCode=pfunCode[i];												 
								 userRoles
									.push(role);
							 });
							 
							
							// userDataTable(userRoles);
							// $('#UserRoleTables').dataTable().fnClearTable();
							 //$('#UserRoleTables').dataTable().fnAddData(userRoles);
							    UserRoleTables(userRoles);
								}
								updateUserTable(selecteduser);
						});
						
						//function userDataTable(userRoles){
					/*	var UserRoleTables=	$('#UserRoleTables').DataTable({
							"select" : {
								style : 'single'
							},

							"stripeClasses" : [ 'odd-row',
									'even-row' ],
							"dom" : 'lfrtip',						
							    "data": [],
							    "columns": [
							      { "data": "roleCode" },
							      { "data": "roleName" },
							      { "data": "funCode" }
							    ]
							});*/
					//	};
					    function UserRoleTables(userRoles) {
							
							var table = document
									.getElementById("UserRoleTables");
							
							var tableBody = document
									.createElement('tbody');
							 console.log(JSON.stringify(userRoles));
							 
						$.each(userRoles, function(i, item) {
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
						      //  var cell4 = document.createElement('td');
				               // cell4.className = "dt-body-center"; 
								//class="dt-body-center"
								//console.log(users[i].roleCode);
								cell1.appendChild(document
												.createTextNode(userRoles[i].roleCode));// tcodeRecid ,tcodeName, tcodeDesc
								cell2.appendChild(document
												.createTextNode(userRoles[i].roleName));
								cell3.appendChild(document
										.createTextNode(userRoles[i].funCode));
							   // cell4.appendChild(document.createTextNode(userRoles[i].entity)); 
								row.appendChild(cell1);
								row.appendChild(cell2);
								row.appendChild(cell3);
								//row.appendChild(cell4);
                                
								tableBody.appendChild(row);

									});
                           // console.log(tableBody);
							table.appendChild(tableBody);
							
						// console.log(JSON.stringify(selecteduser));
						};

						function updateUserTable(users) {
							
							var table = document
									.getElementById("UserDetailsTables");
							
							var tableBody = document
									.createElement('tbody');
							 console.log(JSON.stringify(users));
							 
						$.each(users, function(i, item) {
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
								//class="dt-body-center"
								//console.log(users[i].roleCode);
								cell1.appendChild(document
												.createTextNode(users[i].hrisCode));// tcodeRecid ,tcodeName, tcodeDesc
								cell2.appendChild(document
												.createTextNode(users[i].employeeName));
								cell3.appendChild(document
										.createTextNode(users[i].company));
								 cell4.appendChild(document
										.createTextNode(users[i].entity)); 
								row.appendChild(cell1);
								row.appendChild(cell2);
								row.appendChild(cell3);
								row.appendChild(cell4);
                                
								tableBody.appendChild(row);

									});
                           // console.log(tableBody);
							table.appendChild(tableBody);
							
						// console.log(JSON.stringify(selecteduser));
						};
						
						function isEmpty(val) {
							return (val === undefined || val == null || val.length <= 0) ? true
									: false;
						}
						;
				$.ajax({
							type : 'GET',
							url : 'getTocdeByName/A',
							dataType : "json",
							async : false,
							success : handleDataTcode,
							error : function(xhRequest, ErrorText,
									thrownError) {
								//alert("Not Save");
								console.log(xhRequest.status);
								console.log(ErrorText);
								console.log(thrownError);
							}
						});
				$('#tcode').on(
						'keyup',
						function() {							
							if (isEmpty(this.value))
								return;
								$.ajax({
								type : 'GET',
								url : 'getTocdeByName/' + this.value,
								dataType : "json",
								async : false,
								success : handleDataTcode,
								error : function(xhRequest, ErrorText,
										thrownError) {
									//alert("Not Save");
									console.log(xhRequest.status);
									console.log(ErrorText);
									console.log(thrownError);
								}
							});
						});
				var tcodeList = [];
				function handleDataTcode(data) {
					console.log(JSON.stringify());
					tcodedata = data;

					tcodeList = tcodedata
							.map(function(code) {
								return {
									"text" : code.tcode+ ", "
											+ code.tcodeDesc,
									"value" : code.tcode
								};
							});
					$('.tcode')
							.immybox(
									{
										choices : tcodeList,
										defaultSelectedValue : "",
										formatChoice : function(query) {
											const reg = new RegExp("("
													+ query + ")", "i");
											//return choice => return ();
											return function(choice) {
												return ("<div class='mdl-grid mdl-grid--no-spacing'>"
														+ "<div class='mdl-cell mdl-cell--6-col'>"
														+ [

														choice.text
																.replace(
																		reg,
																		"<u>$1</u>") ]
																.join(", ")
														+ "</div>" + "</div>");
											}
										}
									});
					//console.log(JSON.stringify(instituteList));
					$('.tcode').immybox('setChoices', tcodeList);

				};
				
				var assignrolestable = $("#assignrolestable")
							.DataTable(
									{

										select : {
											style : 'single'
										},

										"stripeClasses" : [ 'odd-row',
												'even-row' ],
										"dom" : 'lf<"toolbars">rtip',
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
				$("div.toolbars").html($('#toolbar').html());
				$('#tcode').on('update',
						function(element, newValue) {	
					//console.log(JSON.stringify(userdata));
					//if(!isEmpty(newValue) && newValue==$("#tcode").immybox('getValue')){
							assignrolestable.ajax
								.url("getRolesByTcode/"+encodeURIComponent(newValue))
								.load();
					//}
				});
						function getRoles(tcode){
						//	assignrolestable.ajax
					//		.url("getRolesByTcode/"+tcode)
						//	.load();
						if (isEmpty(tcode))
										return;
							 $.ajax({
						            url: "getRolesByTcode/"+tcode,
						            type: "get"
						        //    data: { searchText: searchText }
						        }).done(function (result) {
						        	assignrolestable.clear().draw();
						        	assignrolestable.rows.add(result).draw();
						            }).fail(function (jqXHR, textStatus, errorThrown) { 
						                  // needs to implement if it fails
						            });

						};
						
						
						
						$('#btroles').click(function() {

							if (!$("#tcode").val()) {
								swal("Select tcode",
										"Select tcode for search.");
								return;
							};					  
							
							getRoles($("#tcode").immybox('getValue'));

						});
						var riskFunction = [];
						$
								.ajax({
									type : 'GET',
									url : "getRiskFunction/0",
									dataType : "json",
									async : false,
									success : preparDependent,
									error : function(xhRequest, ErrorText,
											thrownError) {

									}
								});

						function preparDependent(data) {
							riskFunction = data;
							//console.log(JSON.stringify(riskFunction));
						}
						;
						
						
						/*$('#assignrolestable thead input[name="select_all"]')
						.on(
								'click',
								function(e) {
									if (this.checked) {
										$(
												'#assignrolestable tbody input[type="checkbox"]:not(:checked)')
												.trigger('click');
									} else {
										$(
												'#assignrolestable tbody input[type="checkbox"]:checked')
												.trigger('click');
									}
									e.stopPropagation();
								});*/
		

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
												 rows_selected.push({"roleId": roleId,"roleCode": roleCode,"roleName": roleName, "funId": funId, "funCode": funCode });
												// rows_selected.push({"funId":funId,"roleId":roleId,"roleCode":roleCode,"funCode":funCode,"roleName":roleName});
												console.log(JSON.stringify(rows_selected));
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
					 rows_selected=[];
					//updateDataTableSelectAllCtrl(assignrolestable);

				}); 
						
						
						

						$('#assignrolestable_wrapper #btassignRoles').on('click',
								function(e) {
							//var selecteduser=userdata.filter( e => (e.sapUserid!=null )&& (e.sapUserid === newValue));
							//console.log($("#users").immybox('getValue'));
							if(isEmpty($("#users").immybox('getValue')[0])){								
								swal("Select user",
								"Select user .");
								return;
							   }
						/*	if(isEmpty(rows_selected)){								
								swal("Select role",
								"Select row for assign role.");
								return;
							   }*/
							console.log(JSON.stringify(roleuser));
							        $('#roleCode').val();
									$('#parentRoleId').val();
									$("#empCode").val(roleuser.hrisCode);
									$('#sapEmpCode').val(roleuser.sapEmpCode);
									var lroleCode = [];
									var lroleId = [];
									var temp=[];
									 var userRoles = [];
									 userRoles= isEmpty(assignroles)?[]:assignroles;
									 if(!isEmpty(rows_selected[0])){
										console.log(JSON.stringify(rows_selected[0]));
										 userRoles.push(rows_selected[0]);
								   
								     lroleId.push(rows_selected[0].roleId);										
									 lroleCode.push(rows_selected[0].roleCode);
									 }
									 console.log(JSON.stringify(userRoles));
									 userRoles.forEach(function(rowData) {
										
										//rows_selected.push({"tcodeRecid":rowData.parentTcodeRecid,"tcodeName":rowData.tcode});
										temp=temp.concat(riskFunction.filter( e => (e.riskCode!=null )&& (e.funId === rowData.funId)));
										//console.log("==========$$$$$$$$$$$$$$$$$$$$$$$$=========");
									    console.log(JSON.stringify(rowData));
									}); 
								//	lroleId.push(rows_selected[0].roleId);										
								//    lroleCode.push(rows_selected[0].roleCode);
									console.log("==========$$$$$$$$$$$$$$$$$$$$$$$$=========");
									//console.log(lroleId);
									console.log(JSON.stringify(temp));								
									var max=0;
                  if(temp.length > 0){
	                var result = temp.reduce(function (r, a) {
					r[a.riskId] = r[a.riskId] || [];
					console.log("===================");
					console.log(JSON.stringify(a));
					r[a.riskId].push(a);					
					max=r[a.riskId].length>max?r[a.riskId].length:max;
					console.log(max);
					return r;
				}, Object.create(null));
				if(max>1){    
					openConflictedModel(result);
					console.log(JSON.stringify(result));
				return;}
			}
									
									
									
									$('#roleCode').val(lroleCode);
									$('#parentRoleId').val(lroleId);
									console.log(lroleCode);
									
									
									//$('#assignrolesmodel').modal('hide');                                 
									
								});
                    
				
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

	<div class="col-lg-12 column" style="width: 50%">
		<div class="portlet">
			<div class="portlet-header">
				<span class="ui-text">Search employee for assign roles</span>
				<!--  <mark></mark> -->
			</div>
			<form id="search_user_form">
				<div class="portlet-content form-group">
					<table class="table table-bordered table-striped  table-condensed "
						id="" style="">
						<tr style="width: 100%">
							<td style="width: 30%"><label for="users">Search
									Users</label></td>
							<td style="width: 70%" colspan="4"><input id="users"
								name="users" placeholder="Select users"
								class="form-control immybox immybox_witharrow users" type="text"
								value=""></td>
						</tr>
					</table>
				</div>
			</form>
		</div>

		<div class="portlet">
			<div
				style="font-weight: 100 !important; color: #fbf6f6; font-size: 14px !important; padding: 5px !important; background-color: #0480be;">
				<span class="ui-text">User Details</span>
				<!--  <mark></mark> -->
			</div>
			<div class="portlet-content form-group">
				<table class="table table-bordered table-striped  table-condensed "
					id="UserDetailsTables" style="">
					<thead>
						<tr
							style="width: 100%; font-weight: 100 !important; color: #fbf6f6; font-size: 14px !important; padding: 5px !important; background-color: #0480be;">
							<th style="width: 20%">Emp code</th>
							<th style="width: 40%">Emp name</th>
							<th style="width: 20%">Company</th>
							<th style="width: 20%">SAP Entity</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<!-- /content-panel -->
		<div class="portlet">
			<div
				style="font-weight: 100 !important; color: #fbf6f6; font-size: 14px !important; padding: 5px !important; background-color: #0480be;">
				<span class="ui-text">Role assign to user</span>
				<!--  <mark></mark> -->
			</div>

			<div class="portlet-content form-group">
				<table class="table table-bordered table-striped  table-condensed "
					id="UserRoleTables" style="width: 100%">
					<thead>
						<tr
							style="font-weight: 100 !important; color: #fbf6f6; font-size: 14px !important; padding: 5px !important; background-color: #0480be;">
							<th style="width: 35%">Role code</th>
							<th style="width: 40%">Role name</th>
							<th style="width: 25%">FUN Code</th>
						</tr>
					</thead>

				</table>
			</div>

		</div>
		<!-- /content-panel -->
	</div>
	<div class="col-lg-12 column" style="width: 50%">
		<div class="portlet">
			<div class="portlet-header">
				<span class="ui-text">Search roles by tcode</span>
				<!--  <mark></mark> -->
			</div>

			<div class="portlet-content form-group">
				<div class="showback">
					<button id="btsave" type="button" class="btn btn-success">
						<i class="fa fa-save"></i> Save roles
					</button>

				</div>
				<form id="assgin_role_form">
					<input type="hidden" id="empCode" name="empCode"> <input
						type="hidden" id="sapEmpCode" name="sapEmpCode"> <input
						type="hidden" id="parentRoleId" name="parentRoleId"> <input
						type="hidden" id="roleCode" name="roleCode">
				</form>
				<table class="table table-bordered table-striped  table-condensed "
					id="" style="">


					<tr style="width: 100%">
						<td style="width: 30%"><label for="tcode">Tcode</label></td>
						<td style="width: 80%" colspan="4"><input id="tcode"
							name="tcode" placeholder="Select tcode"
							class="form-control immybox immybox_witharrow tcode" type="text"
							value=""></td>

						<!-- <td style="width: 10%"><button id="btroles" type="button"
									class="btn btn-info ">
									<i class="fa get-pocket "></i> Get Roles
								</button></td> -->

					</tr>

				</table>
			</div>

		</div>
		<div class="portlet">
			<div
				style="font-weight: 100 !important; color: #fbf6f6; font-size: 14px !important; padding: 5px !important; background-color: #0480be;">
				<span class="ui-text">Sap Roles</span>

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
	<div id="toolbar" style="display: none">
		<span class="showback"> <!-- <button id="addRow" type="button" class="btn btn-success">
				<i class="fa fa-plus"></i> Add New
			</button> -->
			<button id="btassignRoles" class="btn btn-info" type="button">
				<i class="glyphicon glyphicon-edit"></i> Assign role
			</button> <!-- <button id="delete" type="button" class="btn btn-danger ">
				<i class="fa fa-trash "></i> Delete
			</button> -->
		</span>
	</div>

</section>
