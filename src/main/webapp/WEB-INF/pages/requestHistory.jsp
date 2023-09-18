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
							"bFilter" : false,
							"responsive" : true,
							"bProcessing" : true,
							"bServerSide" : true,
							//Default: Page display length
							"iDisplayLength" : 10,
							//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
							"iDisplayStart" : 0,
							"sAjaxSource" : "getUserRequestHistory",
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
								"name" : "requestId",
								"targets" : 1,
								"defaultContent" : "",
								"searchable" : false,
								"orderable" : false,								
								
							},							
							 {
								"name" : "employeeName",
								"targets" : 2,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							}
							,{
								"name" : "entity",
								"targets" : 3,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "department",
								"targets" : 4,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},
							{
								"name" : "requestType",
								"targets" : 5,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "appliedOn",
								"targets" : 6,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "status",
								"targets" : 7,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center',
								"render" : function(data, type, full, meta){
							               return getStatusTextHeader(data);
							            }
							} ,{
								"name" : "reason",
								"targets" : 8,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							} ],	
							"aoColumns" : [  {
								"mData" : "surRecid"
								},{
								"mData" : "requestId"
								},{
								"mData" : "employeeName"
								},{
								"mData" : "entity"
								},{
								"mData" : "department"
								},{
								"mData" : "requestType"
								},{
								"mData" : "appliedOn"
								},{
								"mData" : "status"
								},
								{
								"mData" : "reason"
								}],	
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
						//$("div.toolbars").html($('#toolbar').html());
						function onlyUnique(value, index, array) {
                                 return array.indexOf(value) === index;
                        }
						
						function stringToArray(value) {
							console.log(array);
							//var array = value.split(",")
							var array = value.split(",").map(function(item) {
                                        return item.trim();
                            });
							console.log(array);
                            return  array.filter(onlyUnique);
                        }
						function getStatusTextDetails(status){
							var stext="Initiated";
							switch(status) {
							  case 'S':
							  stext="Submmited"; 
							    break;
							  case 'R':
								  stext="Rejected"; 								  
							    break;
							  case 'A':
								  stext="Approved";   
							  default:
								  stext="In process"
							}
							return stext;
						};
						function getStatusTextHeader(status){
							var stext="Initiated";
							switch(status) {
							  case 'S':
							  stext="Submmited"; 
							    break;
							  case 'R':
								  stext="Rejected"; 
							    break;
							  case 'C':
								  stext="Closed"; 
							    break; 
							  default:
								  stext="In process"
							}
							return stext;
						};
						 
						//seletedrequest
						function getStatusTextWorkFlow(data){
							var status=data.status;
							var stext="Initiated";
							console.log(seletedrequest.wfLevel);
							
							switch(status) {
							  case 'A':
								  /*console.log(seletedrequest.wfLevel+"   "+data.wfLevel+"  "+seletedrequest.status);
								  if(data.status ==='A' && seletedrequest.status==='C'){
										 stext="Closed";
									} else{}*/
							  stext="Approved";  
							    break;
							  case 'R':
								  stext="Rejected"; 
							    break;
							  case 'C':
								  stext="Closed"; 
							    break; 
							  case 'S':
								  if(seletedrequest.wfLevel==data.wfLevel){
										 stext="Pending";
									}else{
								  stext="In process"; }
								  if(seletedrequest.status==='C'){
										 stext="";
									} 
							    break;   							 
							}
							return stext;
						};
						var cwfLevel=null;
						function getStatusTextWorkFlowLevel(status){
							cwfLevel=status;
							console.log(JSON.stringify(status));							
						};
						
						
						$('#userRequestDataTable').on('draw', function() {
							// Update state of "Select all" control
							console.log('userRequestDataTable Change 1');
							 $("#showRequestType").hide();
							
						});
						$('#userRequestDataTable').on('draw.dt', function() {
							console.log('userRequestDataTable Change 2');
							 $("#showRequestType").hide();
						}); 
						 var seletedrequest=null;
						$('#userRequestDataTable tbody').on(
								'click',
								'tr',
								function() {
									 rows_selected=[];
										var $row = $(this).closest('tr');
										var data = userRequestDataTable.row($row).data();
									
									    seletedrequest=null;
									if ($(this).hasClass('selected')) {												
										$(this).removeClass('selected');
										 $("#showRequestType").hide();
										$(this).find('input:radio').prop('checked', false);
										if ($(this).hasClass('odd-row')) {
											$(this).find('td').css(
													'background-color',
													'##A6DFC1');
										}
									} else {
										userRequestDataTable.$('tr.selected')
												.removeClass('selected');

										userRequestDataTable.$('tr.odd-row').each(
												function() {
													$(this).find('td').css(
															"background-color",
															"##A6DFC1");
												});
										if ($(this).hasClass('odd-row')) {

											$(this).find('td').css(
													'background-color',
													'transparent');
										}
										
										seletedrequest=data;
										//getTocdePlantRequstSelected(seletedrequest);
										 if(seletedrequest.requestType=="T-code Request"){
											 $("#showRequestType").show();	
											 $('#showRequestTypeRole').hide();
											 getTocdePlantRequstSelected(seletedrequest);
										      
										 }else if(seletedrequest.requestType=="Role Request" || seletedrequest.requestType=="Refered Employee"){
											 $("#showRequestType").hide();
											 $('#showRequestTypeRole').show();
											 getRolesByRequest(seletedrequest);
										 }
										
										
										console.log(JSON.stringify(seletedrequest));
									//	roleuserseleted = true;
										$(this).addClass('selected');
										$(this).find('input:radio').prop('checked', true);
									}
								});
						
						
							function isEmpty(val) {
								return (val === undefined || val == null || val.length <= 0) ? true
										: false;
							};
						var assignrolestable =null;
					
						assignrolestable = $("#assignrolestable")
							.DataTable(
									{
										select : {
												'style' : 'multi'
											},
											
									    "stripeClasses" : [ 'odd-row', 'even-row' ],
									    "dom" : 'l<"tocdetoolbar">frtip',
										"pageLength": 100,
										 "bFilter" : false,
										"lengthMenu": [100, 50, 25,10],
										"responsive" : true,
										"bProcessing" : true,
										"bServerSide" : true,
										//Default: Page display length
										"iDisplayLength" : 100,
										//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
										"iDisplayStart" : 0,
										"sAjaxSource" : "getRolesByRequest/"+0+"/"+0,
		                                "aaSorting" : [],
										//"data": [],
										"columnDefs" : [
											
											
												{
						                           "name" : "roleId",
						                           "targets" : 0,
						                           "defaultContent" : "",
						                           "searchable" : false,
						                           "orderable" : false,
						                           
					                            },
												
												{
													"name" : "roleCode",
													"targets" : 1,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												},
												
												{
													"name" : "roleName",
													"targets" : 2,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												},
												{
													"name" : "roleType",
													"targets" : 3,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												},
												{
													"name" : "entity",
													"targets" : 4,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												},
												{
													"name" : "plant",
													"targets" : 5,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												}],
										"aoColumns" : [  {
											"mData" : "roleId"
										},{
											"mData" : "roleCode"
										}, {
											
											"mData" : "roleName"
										}, {
											"mData" : "roleType"
										}, {
											"mData" : "entity"
										}, {
											"mData" : "plant"
										}]
									});
				
				
						function getRolesByRequest(seletedrequest){						     
							
							var tempRequest={};
							//assignrolestable.clear().destroy();
							//$('#assignrolestable').dataTable().fnDestroy();
							
							tempRequest.surRecid=seletedrequest.surRecid;
							tempRequest.requestId=seletedrequest.requestId;
							console.log(JSON.stringify(tempRequest));							
							//assignrolestable.ajax.url("getRolesByTcode").load();
							//"sAjaxSource" : "getRolesByRequest/"+tempRequest.requestId+"/"+tempRequest.surRecid,requestId
							 assignrolestable.ajax.url("getRolesByRequest/"+seletedrequest.requestId+"/"+seletedrequest.surRecid).load();
							 approvalDetailsTables.ajax.url("getUserRequestStatus/"+seletedrequest.surRecid+"/"+seletedrequest.requestId).load();
				   };
							
						
			
		

				
						
						
			
						$('#btassignRoles').on('click',
								function(e) {
							console.log(JSON.stringify(seletedrequest));
							if(isEmpty(seletedrequest)){								
								swal("Select row",
								"Select Request and t-codes for get .");
								return;
							   }
							
							if(isEmpty(selected_roles)){								
								swal("Select role",
								"Select row for assign role.");
								return;
							   }
							resetErrors();
							
							//var sdata=	jsonConcat(seletedrequest,selected_roles);
							//sdata.status="F";
							seletedrequest.sapAmRolesRequestVO= selected_roles;
							delete	seletedrequest.createdOn;
							delete	seletedrequest.requestDate;
							console.log(JSON.stringify(seletedrequest));
							saveRoles(seletedrequest);
						});
                    
						
						
				
					
					
					
					
					var tcodeDataTables	=null;
					tcodeDataTables = $("#tcodeDataTables").DataTable({
						//stateSave: true,
						select : {
							'style' : 'single'
						},
						
						"stripeClasses" : [ 'odd-row', 'even-row' ],
						"pageLength": 100,
						"lengthMenu": [100, 50, 25,10],
						"responsive" : true,
						 "bFilter" : false,
						"bProcessing" : true,
						"bServerSide" : true,
						//Default: Page display length
						"iDisplayLength" : 10,
						//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
						"iDisplayStart" : 0,
						"sAjaxSource" : "getRequestedTcode/0/0",
						"aaSorting" : [ [ 0, "asc" ] ],	
						"aaSorting" : [],
						"columnDefs" : [ {
							"orderable" : false,
							"targets" : [ 0 ]
							}, {
							"name" : "tcode",
							"targets" : 0,
							"defaultContent" : "",
							"searchable" : false,
							"orderable" : false,
							"className" : 'dt-body-center'
							}, {
								"name" : "tcodeDesc",
								"targets" : 1,
								"defaultContent" : "",
								"searchable" : false,
								"orderable" : false,
								"className" : 'dt-body-center'
								},{
							"name" : "module",
							"targets" : 2,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center'
						}/*,{
							"name" : "status",
							"targets" : 3,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center',
							"render" : function(data, type, full, meta){
						               

						               return getStatusTextDetails(data);
						            }
						
						}*/],
						"aoColumns" : [ {
							"mData" : "tcode"
							}, {
							"mData" : "tcodeDesc"
							},{
							"mData" : "module"
							}/*, {
							"mData" : "status"
							}*/],
						"rowCallback" : function(row,
						data, dataIndex) {									
							console
							.log("row "
							+ JSON
							.stringify(data));				
							if(rows_selected.filter( e => (e.surtRecid === data.surtRecid) && (e.surRecid === data.surRecid) && (e.requestId === data.requestId) ).length > 0){		
								$(row).find('input[type="checkbox"]').prop('checked',true);
								$(row).addClass('selected');
							}
						}
					});
					
					
					
					
					$('#tcodeDataTables').on('draw', function() {
						// Update state of "Select all" control
						//console.log('mastertrole Change');
						//updateDataTableSelectAllCtrl(tcodeDataTables);
					});
					$('#tcodeDataTables').on('draw.dt', function() {
						//console.log('mastertrole Change');
						//updateDataTableSelectAllCtrl(tcodeDataTables);					
					});
					
					var pantDataTables = $("#pantDataTables").DataTable({			            
						
						"stripeClasses" : [ 'odd-row', 'even-row' ],
						//"dom" : 'l<"tocdetoolbar">frtip',
						"dom" : 'l<"toolbar">frtip',
						/* dom: "<'row'<'col-sm-12'tr>>" +
						"<'row'<'col-sm-4'l><'col-sm-8'p>>", */
						"pageLength": 100,
						"lengthMenu": [100, 50, 25,10],
						 "bFilter" : false,
						"responsive" : true,
						"bProcessing" : true,
						"bServerSide" : true,
						//Default: Page display length
						"iDisplayLength" : 10,
						//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
						"iDisplayStart" : 0,
						"sAjaxSource" : "getRequestedPlantForRequest/0/0",
						//"aaSorting" : [ [ 0, "asc" ] ],	
						"aaSorting" : [],
						"columnDefs" : [ {
							"orderable" : false,
							"targets" : [ 0 ]
							}, {
							"name" : "plant",
							"targets" : 0,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center'
						},{
							"name" : "purchaseGroup",
							"targets" : 1,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center'
						} ],
						"aoColumns" : [  {
							"mData" : "plant"
							},{
							"mData" : "purchaseGroup"
						   }  ],
						"rowCallback" : function(row,
								data, dataIndex) {
									// Get row ID
									
									console.log("row "		+ JSON.stringify(data)	);									
									/*if(rows_selected.filter( e => (e.tcode === data.tcode) && (e.satId === data.tcodeId)).length > 0){
										$(row).find('input[type="checkbox"]').prop('checked',true);							
										$(row).addClass('selected');
									}*/
								}
					});
					
					function getTocdePlantRequstSelected(seletedrequest){
						if(!isEmpty(seletedrequest)){
							tcodeDataTables.ajax.url("getRequestedTcode/"+seletedrequest.surRecid+"/"+seletedrequest.requestId).load();
							pantDataTables.ajax.url("getRequestedPlantForRequest/"+seletedrequest.surRecid+"/"+seletedrequest.requestId).load();
							approvalDetailsTables.ajax.url("getUserRequestStatus/"+seletedrequest.surRecid+"/"+seletedrequest.requestId).load();
						}
					};

					
					var approvalDetailsTables=null;
					approvalDetailsTables = $("#approvalDetailsTables").DataTable({
						//stateSave: true,
						select : {
							'style' : 'single'
						},
						
						"stripeClasses" : [ 'odd-row', 'even-row' ],
						"dom" : 'lf<"toolbars">rtip',
						"pageLength": 100,
						"lengthMenu": [100, 50, 25,10],
						"bFilter" : false,
						"responsive" : true,
						"bProcessing" : true,
						"bServerSide" : true,
						//Default: Page display length
						"iDisplayLength" : 10,
						//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
						"iDisplayStart" : 0,
						"sAjaxSource" : "getUserRequestStatus/0/0",
						"aaSorting" : [ [ 0, "asc" ] ],	
						"aaSorting" : [],
						"columnDefs" : [ {
							"orderable" : false,
							"targets" : [ 0 ]
							}
							,{
								"name" : "wfLevel",
								"targets" : 0,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
								
							}
							,{
								"name" : "empCode",
								"targets" : 1,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "wfCode",
								"targets" : 2,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},
												
						 {
							"name" : "companyCode",
							"targets" : 3,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center',
							"render" : function(data, type, full, meta){ 
					               return stringToArray(data);
					        }
						},{
							"name" : "sbu",
							"targets" : 4,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center',
							"render" : function(data, type, full, meta){ 
					               return stringToArray(data);
					            }
							
						},{
							"name" : "sapModule",
							"targets" : 5,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center',
							"render" : function(data, type, full, meta){ 
					               return stringToArray(data);
					         }
						},{
							"name" : "status",
							"targets" : 6,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center',
							"render" : function(data, type, full, meta){
					               

					               return getStatusTextWorkFlow(full);
					            }
					    
						} ],	
						"aoColumns" : [{
							"mData" : "wfLevel"
						   },{
								"mData" : "empCode"
							},
							{
							"mData" : "wfCode"
							}, {
							"mData" : "companyCode"
							},{
							"mData" : "sbu"
							},{
							"mData" : "sapModule"
							},
							{
							"mData" : "status"
							}],	
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
					//$("div.toolbars").html($('#toolbar').html());
				
					$('#approvalDetailsTables').on('draw', function() {
						// Update state of "Select all" control
						console.log('approvalDetailsTables Change 1');
						//$('#assgin_role_form').trigger("reset");
						
						// rows_selected=[];
						
					});
					$('#approvalDetailsTables').on('draw.dt', function() {
						console.log('approvalDetailsTables Change 2');
						
						 //rows_selected=[];
						

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
				<span class="ui-text">User Request for approval </span>
				<!--  <mark></mark> -->
			</div>
			<div class="portlet-content form-group">
				<div class="showback"></div>

				<table id="userRequestDataTable"
					class="table table-bordered table-striped  table-condensed "
					cellspacing="0" width="100%">
					<thead>
						<tr>
							<th style="width: 2%"></th>
							<th style="width: 5%">Request Number</th>
							<th style="width: 15%">Employee Name</th>							
							<th style="width: 8%">Requester CC</th>
							<th style="width: 5%">Req DEPT</th>	
							<th style="width: 10%">Request For</th>						
							<th style="width: 10%">Applied On </th>
							<th style="width: 5%">Status</th>
							<th style="width: 40%">Reason</th>
						</tr>
					</thead>
					
				</table>
			</div>
		</div>
		<!-- /content-panel -->
		<div id="showRequestType" style="width: 100%; display: none;">
			<div class="col-lg-12 column" style="width: 60%">
				<div class="portlet">
					<div class="portlet-header">
						<span class="ui-text">T-code added for approval </span>
						<!--  <mark></mark> -->
					</div>
					<div class="portlet-content form-group">
						<div class="showback"></div>

						<table id="tcodeDataTables"
							class="table table-bordered table-striped  table-condensed "
							cellspacing="0" width="100%">
							<thead>
								<tr>
									<th style="width: 20%">T-code name</th>
									<th style="width: 35%">T-code desc</th>
									<th style="width: 10%">Module</th>
									<!--  <th style="width: 10%">Status</th>-->

								</tr>
							</thead>
						</table>
					</div>
				</div>
				<!-- /content-panel -->
			</div>
			<!-- /content-panel -->
			<div class="col-lg-12 column" style="width: 40%">
				<div class="portlet">
					<div class="portlet-header">
						<span class="ui-text">Plant/Purchase Group List </span>
						<!--  <mark></mark> -->
					</div>
					<div class="portlet-content form-group">
						<div class="showback"></div>

						<table id="pantDataTables"
							class="table table-bordered table-striped  table-condensed "
							cellspacing="0" width="100%">
							<thead>
								<tr>								
									<th style="width: 20%">Plant name</th>
									<th style="width: 20%">Purchase Group</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				<!-- /content-panel -->
			</div>
		</div>
		<div id="showRequestTypeRole" style="width: 100%; display: none;">
			<div class="col-lg-12 column" style="width: 100%">
				<div class="portlet">
					<div class="portlet-header">
						<span class="ui-text"> Request Role</span>
						<!--  <mark></mark> -->
					</div>
					<div class="portlet-content form-group">
						<div class="showback"></div>
						<table id="assignrolestable"
							class="table table-bordered table-striped  table-condensed "
							cellspacing="0" width="100%">
							<thead>
								<tr>
									<!--  style="background-color: #d9edf7; height: 30px;" 
									<th style="width: 5%"><input name="select_all" value="1"
										type="checkbox"></th>-->
									<th style="width: 5%">Role Id</th>
									<th style="width: 25%">Role code</th>
									<th style="width: 35%">Role Name</th>
									<th style="width: 5%">Role Type</th>
									<th style="width: 15%">Entity</th>								
									<th style="width: 15%">Plant</th>							
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-12 column" style="width: 100%">
			<div class="portlet">
				<div class="portlet-header">
					<span class="ui-text"> Request status</span>
					<!--  <mark></mark> -->
				</div>
				<div class="portlet-content form-group">
					<div class="showback"></div>
					<table id="approvalDetailsTables"
						class="table table-bordered table-striped  table-condensed "
						cellspacing="0" width="100%">
						<thead>
							<tr>
								<th style="width: 5%">WF Level</th>
								<!--  <th style="width: 5%">Request Number</th>-->
								<th style="width: 15%">Approve By</th>
								<th style="width: 5%">WF Code</th>
								<th style="width: 10%">Company Code</th>
								<th style="width: 5%">SBU</th>
								<th style="width: 15%">SAP Module</th>
								<th style="width: 10%">Status</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
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
