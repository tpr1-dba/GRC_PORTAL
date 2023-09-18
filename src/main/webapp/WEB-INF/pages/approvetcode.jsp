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
							 "bFilter" : false,
							"bProcessing" : true,
							"bServerSide" : true,
							//Default: Page display length
							"pageLength": 100,
						    "lengthMenu": [100, 50, 25,10],
							"iDisplayLength" : 100,
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
							},{
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
								
							},{
								"name" : "sapModuleCode",
								"targets" : 5,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center',
								"render" : function(data, type, full, meta){ 
						               return stringToArray(data);
						        }							
							}
							,{
								"name" : "requestType",
								"targets" : 3,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},
							{
								"name" : "appliedOn",
								"targets" : 7,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},
							{
								"name" : "reason",
								"targets" : 8,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							} ,{
								"name" : "surRecid",
								"targets" : 9,
								"searchable" : false,
								"orderable" : false,				
								"className" : '',
								"render" : function( data, type, row, meta ){
									
									return '<button id="btnHRRemarks" type="button" class="btn btn-success" style="padding: 1px" data-toggle="tooltip" data-placement="top" title="Enter remarks!"><i class="glyphicon glyphicon-pencil">     </i></button>';
											
								}
							   }						
							
							,{
								"name" : "surRecid",
								"targets" : 10,
								"searchable" : false,
								"orderable" : false,				
								"className" : 'dt-body-center',
								"render" : function( data, type, row, meta ){
									
									return '<button id="btnHRReject" type="button" class="btn btn-danger" style="padding: 1px"><i class="fa fa-trash "></i> Reject </button>';
											
								}
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
								"mData" : "sapModuleCode"
								},
								{
								"mData" : "requestType"
								},{
								"mData" : "appliedOn"
								},{
								"mData" : "reason"
								},{
								"mData" : "surRecid"
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
								
								
							},	"initComplete": function (settings, json) {
								console.log(JSON.stringify(json));
								
				                if(this.api().data().length>0){
				                	 				 
							       } else{
										
									}                                               
				              },
				              "drawCallback": function( settings ) {								               
				            	  if(this.api().data().length>0){
				                 	
				                	
				 			       } else{
				 						
				 					}  
				              }
						});
						$("div.toolbars").html($('#toolbar').html());
					
						$('#userRequestDataTable').on('draw', function() {
							// Update state of "Select all" control
							console.log('userRequestDataTable Change 1');
							$('#assgin_role_form').trigger("reset");
							 $("#showRequestType").hide();
							 rows_selected=[];
							
						});
						$('#userRequestDataTable').on('draw.dt', function() {
							console.log('userRequestDataTable Change 2');
							 $("#showRequestType").hide();
							 rows_selected=[];
							

						}); 
						
						function onlyUniqueGeneric(value, index, array) {						
						
                            return array.indexOf(value) === index;
                        }
						 function stringToArray(value) {
								console.log(value);
								//var array = value.split(",")
								var array = value.split(",").map(function(item) {
		                                   return item.trim();
		                       });
								console.log(array);
								return  array.filter(onlyUniqueGeneric);
			                 // "T";
			              }		
						 var seletedrequest=null;
						 $('#userRequestDataTable  tbody')
							.on('click','input[type="radio"]',
							function(e) {
									 rows_selected=[];
										var $row = $(this).closest('tr');
										var data = userRequestDataTable.row($row).data();
									
									    seletedrequest=null;
									if ($(this).hasClass('selected')) {
										 $("#showRequestType").hide();
									
										$(this).removeClass('selected');
										$(this).prop('checked', false);
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
										 
										 if(seletedrequest.requestType=="T-code Request"){
											 $("#showRequestType").show();	
											 getTocdePlantRequstSelected(seletedrequest);
										      
										 }else if(seletedrequest.requestType=="Role Request" || seletedrequest.requestType=="Refered Employee"){
											 $("#showRequestType").hide();
										      
										 }
										 
										// rows_selected.push({"roleId": roleId,"roleCode": roleCode,"roleName": roleName, "funId": funId, "funCode": funCode });
										// rows_selected.push({"funId":funId,"roleId":roleId,"roleCode":roleCode,"funCode":funCode,"roleName":roleName});
										console.log(JSON.stringify(seletedrequest));
									//	roleuserseleted = true;
										$(this).addClass('selected');
										$(this).prop('checked', true);
									}
								});
						$('#userRequestDataTable c tbody')
						.on('click','button[type="button"]',
						function(e) {
							var $row = $(this).closest('tr');
							
							// Get row data
							var data = userRequestDataTable.row($row)
							.data();
							console.log(JSON.stringify(data));
							
							swal({
				                title: "Are you sure to reject?",
				                text: "Once , Review then reject!",
				                type: "warning",
				                showCancelButton: true,
				                confirmButtonClass: "'btn btn-danger'",
				                confirmButtonText: "Yes, reject it!",
				                // closeOnConfirm: false
				            }/*,
				             function(isConfirm) {
			                //alert(isConfirm);					
			              if (isConfirm) {*/
						).then((result) => {
			            	//{"dismiss":"cancel"}
			            	console.log(JSON.stringify(result));
			            	if(isEmpty(result.dismiss)){
			            		$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
				           	   
				           var sdata=	{};
				  		    sdata.status="R";
							sdata.requestId = data.requestId;
							sdata.surRecid  =  data.surRecid;
							sdata.swmRecid  =  data.swmRecid;
							if(data.requestType=="T-code Request"){
								sdata.requestType  =  "T"; 
							      
							 }else if(data.requestType=="Role Request"){
								 sdata.requestType  = "R";
							      
							 }
							 else if(data.requestType=="Refered Employee"){
								 sdata.requestType  = "E";
							      
							 }
							
							
						    //	delete	sdata.createdOn;
							//  delete	sdata.requestDate;
							console.log(JSON.stringify(sdata));
							//return;
							deleteUserRequest(sdata);
				              }
				           });
						});
						
						
						$('#userRequestDataTable').on('click', 'button#btnHRReject', function () {
							var $row = $(this).closest('tr');
							
							// Get row data
							var data = userRequestDataTable.row($row)
							.data();
							console.log(JSON.stringify(data));
							   if(isEmpty(seletedrequest)){								
									swal("Select row",
									"Select request and review roles! ");
									return;
								  }
						
							    if(data.requestId!==seletedrequest.requestId){
							    	swal("Worng  selection",
									"Reject same request as selected ");
									return;
							    }
							    
							    
							    if(isEmpty($("#"+seletedrequest.requestId).val())){								
									swal("Enter remarks",
									"Enter rejection remarks! ");
									return;
								  }
							swal({
				                title: "Are you sure to reject?",
				                text: "Once , Review then reject!",
				                type: "warning",
				                showCancelButton: true,
				                confirmButtonClass: "'btn btn-danger'",
				                confirmButtonText: "Yes, reject it!",
				                // closeOnConfirm: false
				            }/*,
				             function(isConfirm) {
			                //alert(isConfirm);					
			              if (isConfirm) {*/
						).then((result) => {
			            	//{"dismiss":"cancel"}
			            	console.log(JSON.stringify(result));
			            	if(isEmpty(result.dismiss)){
			            		$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
				           	   
				           var sdata=	{};
				  		    sdata.status="R";
							sdata.requestId = data.requestId;
							sdata.surRecid  =  data.surRecid;
							sdata.swmRecid  =  data.swmRecid;
							sdata.remarks=  $("#"+seletedrequest.requestId).val();
							if(data.requestType=="T-code Request"){
								sdata.requestType  =  "T"; 
							      
							 }else if(data.requestType=="Role Request"){
								 sdata.requestType  = "R";
							      
							 }
							 else if(data.requestType=="Refered Employee"){
								 sdata.requestType  = "E";
							      
							 }
							
							
						    //	delete	sdata.createdOn;
							//  delete	sdata.requestDate;
							console.log(JSON.stringify(sdata));
							//return;
							deleteUserRequest(sdata);
				              }
				           });
						});
						
						 $('#userRequestDataTable').on('click', 'button#btnHRRemarks', function () {
							 
							 if(isEmpty(seletedrequest)){								
									swal("Select row",
									"Select Request ");
									return;
								   }
								
								    var closestRow = $(this).closest('tr');
								    var data = userRequestDataTable.row(closestRow).data();
								   
								    if(data.requestId!==seletedrequest.requestId){
								    	swal("Worng  selection",
										"Add remarks for same request as selected ");
										return;
								    }
							  $("#requestRemarks").val($("#"+seletedrequest.requestId).val());
							 $('#modalRemarks').modal({backdrop:'static',keyboard:false, show:true});
					});
						
				
				     function addRemarks () {
				    	 
				            
							$('#modalRemarks').modal('hide');
					};
				
					function cancelRemarks  () {
						$("#requestRemarks").val("");
						$('#modalRemarks').modal('hide');							
					};
					
					$("#btnRemarksAdd").click(function() {
						
						if($("#" +seletedrequest.requestId).length == 0) {	
						$('<input>').attr({
						        type: 'hidden',
						        id: seletedrequest.requestId,
						        name: 'remarks'
						    }).appendTo('#hiddenRemarks');
						
					}
						
				    // $("#requestRemarks_"+seletedrequest.requestId).attr('id','requestRemarks_'+seletedrequest.requestId);     
				     $("#"+seletedrequest.requestId).val($("#requestRemarks").val());
				     console.log($("#requestRemarks").val());
				     console.log($("#"+seletedrequest.requestId).val());
					 $('#modalRemarks').modal('hide');	
					});
					
					$("#btnRemarksCancle").click(function() {		
						
					     console.log($("#"+seletedrequest.requestId).val());
						$('#modalRemarks').modal('hide');	
					});
						
						function	deleteUserRequest(data){	
							$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
							resetErrors();
							$
									.ajax({
										contentType : 'application/json; charset=utf-8',
										type : 'POST',
										url : 'updateRequest',
										data : JSON
								          .stringify(data),
										dataType : 'json',
										async : false,
										success : function(data) {
											console
													.log(JSON
															.stringify(data));
											userRequestDataTable.ajax.url("getUserRequest").load();
											 $("#showRequestType").hide();
											 $('#pageblock').modal('hide');
											
											 swal("Success!",
													data.ok,
													"success");
											//$("#requestId").val("");
											//$("#surRecid").val("");
											
										},
										error : function(
												xhRequest,
												ErrorText,
												thrownError) {
											console
													.log(JSON
															.stringify(xhRequest));
										
											if (xhRequest.status == 400) {
												var responseText = JSON.parse(xhRequest.responseText);
												console
														.log(responseText);

												validateForm(responseText);
												return false;
											}
											//	mroletable.ajax.url("masterroledata").load();
											swal({
												title : "Error",
												text : JSON.parse(xhRequest.responseText).error,
												type : "error",
												showCancelButton : false,
												confirmButtonClass : 'btn btn-theme04',
												confirmButtonText : 'Error!'
											});
										}
									});
							return false;
						};
						$('#userRequestDataTable_wrapper #btnGetRoles').on('click',
								function(e) {				
						   // alert("Hello "+seletedrequest);
							if(isEmpty(seletedrequest)){								
								swal("Select row",
								"Select Request ");
								return;
							   }
							
							 if (tcodeDataTables.rows().data().length!==rows_selected.length){
									
									swal("Select All T-code",
									"Approve  All T-code or reject remaining");
									return;
								   } 
							else{
								 if(seletedrequest.requestType=="T-code Request"){
									 getRoles(seletedrequest);
								      
								 }else if(seletedrequest.requestType=="Role Request" || seletedrequest.requestType=="Refered Employee"){
									 getRolesByRequest(seletedrequest);								      
								 }									
							}
							console.log(JSON.stringify(seletedrequest));
						});
						/*var tempRequest={
							    "hrisCode": null,
							    "surtRecid": [],
							    "surRecid":  0,
							    "requestId": 0
							};*/
							function isEmpty(val) {
								return (val === undefined || val == null || val.length <= 0) ? true
										: false;
							};
					   var salectedroletcodeplant=[];
						var assignrolestable =null;
						function getRoles(seletedrequest){
							selected_roles=[];							
							salectedroletcodeplant=[];
						    	var tsurtRecid=[];	  
						    	//var tsurRecid=[];	  
						    //	var trequestId=[];	  
							rows_selected.forEach(function(rowData) {															
								tsurtRecid.push(rowData.surtRecid);								
								//tsurRecid.push(rowData.surRecid);								
								//trequestId.push(rowData.requestId);								
							});
							//tcodeDataTables.rows().data()
							$('#seletedTocdesApp').html( tcodeDataTables .columns( 1 ).data() .eq( 0 )      // Reduce the 2D array into a 1D array of data
                             .sort()       // Sort data alphabetically
                             .unique()     // Reduce to unique values
                             .join( ',' )
                             );
							console.log(JSON.stringify(seletedrequest));
							if (isEmpty(seletedrequest.tcode) && isEmpty(tsurtRecid)){								
								swal("Select row",
								"Select Request and t-codes for get.");
								return;
							   }
							var tempRequest={};
							
							tempRequest.hrisCode=seletedrequest.hrisCode;
							tempRequest.surtRecid=tsurtRecid.join(",");;
							tempRequest.surRecid=seletedrequest.surRecid;
							tempRequest.requestId=seletedrequest.requestId;
							console.log(JSON.stringify(tempRequest));							
							//assignrolestable.ajax.url("getRolesByTcode").load();
							
									
						assignrolestable = $("#assignrolestable")
							.DataTable(
									{
										select : {
												'style' : 'multi'
											},
											
									    "stripeClasses" : [ 'odd-row', 'even-row' ],
									    "dom" : 'l<"tocdetoolbar">frtip',
										"pageLength": 100,
										"lengthMenu": [100, 50, 25,10],
										"responsive" : true,
										"bProcessing" : true,
										"bServerSide" : true,
										//Default: Page display length
										"iDisplayLength" : 100,
										//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
										"iDisplayStart" : 0,
										"sAjaxSource" : "getRolesByTcodes/"+tempRequest.requestId+"/"+tempRequest.surRecid+"/"+tempRequest.surtRecid,
                                        "aaSorting" : [],
										//"data": [],
										"columnDefs" : [
											
											{
												'targets' : 0,
												"searchable" : false,
												"orderable" : false,
												"className" : 'dt-body-center',
												"render" : function(
												data, type,
												full, meta) {
													    return '<input type="checkbox" name="id[]" value="'	+ $('<div/>').text(data).html()+ '">';
												   }
										        },
												{
						                           "name" : "roleId",
						                           "targets" : 1,
						                           "defaultContent" : "",
						                           "searchable" : false,
						                           "orderable" : false,
						                           
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
													"name" : "roleName",
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
													"name" : "tcode",
													"targets" : 5,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												},
												{
													"name" : "plant",
													"targets" : 6,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												},{
													"name" : "sensitive",
													"targets" : 7,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center',
													"render" : function(data, type, full, meta){
											               return getSensitiveHeader(data);
											            }
												},
												{
													"name" : "confilctIncrese",
													"targets" : 8,
													"searchable" : false,
													"orderable" : false,
													"defaultContent" : "",
													"className" : 'dt-body-center'
												}										
												
												],
										"aoColumns" : [ {
											"mData" : "roleId"
										}, {
											"mData" : "roleId"
										},{
											"mData" : "roleCode"
										}, {
											
											"mData" : "roleName"
										}, {
											"mData" : "entity"
										}, {
											"mData" : "tcode"
										}, {
											"mData" : "plant"
										},{
											"mData" : "sensitive"
										},
										{
											"mData" : "confilctIncrese"
										},{
											"name" : "roleId",
											"targets" : 9,
											"searchable" : false,
											"orderable" : false,				
											"className" : 'dt-body-center',
											"render" : function( data, type, row, meta ){
												
												return '<button id="btnGETTCODES" type="button" class="btn btn-success" style="padding: 1px"><i class="fa fa-submit "></i> Show Tcodes </button>';
														
											}
										}],
										"rowCallback" : function(row,
												data, dataIndex) {
											// Get row ID												
                                              if( data.roleAssined==='Y'){	
											//if(rows_selected.filter( e => (e.roleId === data.roleId) && (e.roleCode === data.roleCode )).length > 0){	
												$(row)
														.find(
																'input[type="checkbox"]')
														.prop(
																'checked',
																true);
												$(row)
												.find(
														'input[type="checkbox"]')
												.prop(
														'disabled','disabled');
												$(row).addClass(
														'selected');
												
												   selected_roles.push(data);	
						                            var indexcount = salectedroletcodeplant.findIndex(row => ((row.tcode===data.tcode) && (row.plant===data.plant)));
													if ( indexcount === -1) {																					
														salectedroletcodeplant.push({"tcode": data.tcode,"plant": data.plant,"count": 1});								
													} else if (indexcount !== -1) {
														 var tpdata=salectedroletcodeplant[indexcount]; 
														 console.log(JSON.stringify(tpdata));
														 tpdata.count=tpdata.count+1;
														  salectedroletcodeplant.splice(indexcount, 1, tpdata);
														 //salectedroletcodeplant.push({"roleId": roleId,"tcode": row.tcode,"plant": row.plant,"count": 1});
														 console.log(JSON.stringify(tpdata));}
												
													console.log(JSON.stringify(data));
													console.log(JSON.stringify(selected_roles));
											}
										}
										
										
										
									});
						
						$('#showtrolesmodal').modal('show');
					};
					
					
					
					function getRolesByRequest(seletedrequest){						     
				
					var tempRequest={};
					  salectedroletcodeplant=[];
					
					tempRequest.surRecid=seletedrequest.surRecid;
					tempRequest.requestId=seletedrequest.requestId;
					console.log(JSON.stringify(tempRequest));							
					//assignrolestable.ajax.url("getRolesByTcode").load();
					
							
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
								"sAjaxSource" : "getRolesByRequestEmpCode/"+tempRequest.requestId+"/"+tempRequest.surRecid,
								 "aaSorting" : [],
									//"data": [],
									"columnDefs" : [
										
										{
											'targets' : 0,
											"searchable" : false,
											"orderable" : false,
											"className" : 'dt-body-center',
											"render" : function(
											data, type,
											full, meta) {
												return '<input type="checkbox" name="id[]" value="'	+ $('<div/>').text(data).html()+ '">';
											}
									},
											{
					                           "name" : "roleId",
					                           "targets" : 1,
					                           "defaultContent" : "",
					                           "searchable" : false,
					                           "orderable" : false,
					                           
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
												"name" : "roleName",
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
												"name" : "tcode",
												"targets" : 5,
												"searchable" : false,
												"orderable" : false,
												"defaultContent" : "",
												"className" : 'dt-body-center'
											},
											{
												"name" : "plant",
												"targets" : 6,
												"searchable" : false,
												"orderable" : false,
												"defaultContent" : "",
												"className" : 'dt-body-center'
											},
											{
												"name" : "sensitive",
												"targets" : 7,
												"searchable" : false,
												"orderable" : false,
												"defaultContent" : "",
												"className" : 'dt-body-center',
												"render" : function(data, type, full, meta){
										               return getSensitiveHeader(data);
										            }
											},
											{
												"name" : "confilctIncrese",
												"targets" : 8,
												"searchable" : false,
												"orderable" : false,
												"defaultContent" : "",
												"className" : 'dt-body-center'
											}],
									"aoColumns" : [ {
										"mData" : "roleId"
									}, {
										"mData" : "roleId"
									},{
										"mData" : "roleCode"
									}, {
										
										"mData" : "roleName"
									}, {
										"mData" : "entity"
									}, {
										"mData" : "tcode"
									}, {
										"mData" : "plant"
									},
									 {
										"mData" : "sensitive"
									},
									{
										"mData" : "confilctIncrese"
									},
									{
										"name" : "roleId",
										"targets" : 10,
										"searchable" : false,
										"orderable" : false,				
										"className" : 'dt-body-center',
										"render" : function( data, type, row, meta ){
											
											return '<button id="btnGETTCODES" type="button" class="btn btn-success" style="padding: 1px"><i class="fa fa-submit "></i> Get Tcodes </button>';
													
										}
									} ],
									"rowCallback" : function(row,
											data, dataIndex) {
										// Get row ID												
                                          if( data.roleAssined==='Y'){	
										//if(rows_selected.filter( e => (e.roleId === data.roleId) && (e.roleCode === data.roleCode )).length > 0){	
											$(row)
													.find(
															'input[type="checkbox"]')
													.prop(
															'checked',
															true);
											$(row)
											.find(
													'input[type="checkbox"]')
											.prop(
													'disabled','disabled');
											$(row).addClass(
													'selected');
											
											   selected_roles.push(data);	
					                           /* var indexcount = salectedroletcodeplant.findIndex(row => ((row.tcode===data.tcode) && (row.plant===data.plant)));
												if ( indexcount === -1) {																					
													salectedroletcodeplant.push({"tcode": data.tcode,"plant": data.plant,"count": 1});								
												} else if (indexcount !== -1) {
													 var tpdata=salectedroletcodeplant[indexcount]; 
													 console.log(JSON.stringify(tpdata));
													 tpdata.count=tpdata.count+1;
													  salectedroletcodeplant.splice(indexcount, 1, tpdata);
													 //salectedroletcodeplant.push({"roleId": roleId,"tcode": row.tcode,"plant": row.plant,"count": 1});
													 console.log(JSON.stringify(tpdata));}
											
												console.log(JSON.stringify(data));
												console.log(JSON.stringify(selected_roles));*/
										}
									}
								});
					
					$('#showtrolesmodal').modal('show');
			    };
			   
					
					$('#showtrolesmodal').on('shown.bs.modal', function() {
						$("div.tocdetoolbar").html($('#tocdetoolbar').html());
					});
					$('#showtrolesmodal').on('click', '#btnAssignTcode',
							function(e) {
								$('#tcodesText').val();						
								var tcodes = [];
								var tcodeIds = [];
								rows_selected.forEach(function(rowData) {															
									tcodes.push(rowData.tcode);	
									tcodeIds.push(rowData.tcodeId);
								}); 
								$('#tcodesText').val(tcodes);					
								$('#tcodeId').val(tcodeIds);					
								console.log(tcodes);						
								$('#showtrolesmodal').modal('hide');
					});
				
					$('#showtrolesmodal').on('hidden.bs.modal',function() {
						$('#assignrolestable  thead input[name="select_all"]:checked')
						.trigger('click');
						assignrolestable.clear().destroy();
						$('#assignrolestable').dataTable().fnDestroy();
						salectedroletcodeplant=[];
						
					});	
						
			//	$("div.toolbarb").html($('#toolbarrole').html());
				
		
               
				$('#assignrolestable').on(
						'click',
						'tbody input[type="checkbox"]',
						function(e) {
							var $row = $(this).closest('tr');
							var data = assignrolestable.row($row).data();
						    var roleCode = data.roleCode;
							var roleId = data.roleId;
							/*var funId = data.funId;
							var funCode = data.funCode;
							var roleName = data.roleName;*/
							console.log(JSON.stringify(data));
						    var index = selected_roles.findIndex(row => ((row.roleId===roleId) && (row.roleCode===roleCode)));
						   
							if (this.checked && index === -1) {							
								selected_roles.push(data);	
								
							} else if (!this.checked && index !== -1) {
								selected_roles.splice(index, 1);								
							}
														
							if (this.checked) {
								$row.addClass('selected');
							} else {
								$row.removeClass('selected');
							}
							 if(seletedrequest.requestType=="T-code Request"){
							var indexcount = salectedroletcodeplant.findIndex(row => ((row.tcode===data.tcode) && (row.plant===data.plant)));
							if (this.checked && indexcount === -1) {
								//salectedroletcodeplant.push({"roleId": roleId,"tcode": data.tcode,"plant": data.plant,"count": 1});								
								salectedroletcodeplant.push({"tcode": data.tcode,"plant": data.plant,"count": 1});								
							} else if (this.checked && indexcount !== -1) {
								 var tpdata=salectedroletcodeplant[indexcount]; 
								 console.log(JSON.stringify(tpdata));
								 tpdata.count=tpdata.count+1;
								 if( tpdata.count>1){
									 swal("Review Roles ",
										"You have selected one tcode in multiple roles, select single role only");
									    
										/*if ($(this).hasClass('selected')) {
											$(this).removeClass('selected');
											
											if ($(this).hasClass('odd-row')) {
												$(this).find('td').css(
														'background-color',
														'#abff32');
											}
										} 
										else {
											assignrolestable.$('tr.selected').removeClass(
													'selected');

											assignrolestable.$('tr.odd-row').each(
													function() {
														$(this).find('td').css(
																"background-color",
																"#abff32");
													});
											if ($(this).hasClass('odd-row')) {
												$(this).find('td').css(
														'background-color',
														'transparent');
											}
											//$(this).addClass('selected');
											 $row.addClass('duplicatrole');	
										}*/			 
									 
																	 
								 }
								 salectedroletcodeplant.splice(indexcount, 1, tpdata);
								 //salectedroletcodeplant.push({"roleId": roleId,"tcode": row.tcode,"plant": row.plant,"count": 1});
								 console.log(JSON.stringify(tpdata));								
							}else if (!this.checked && indexcount !== -1) {
								 var tpdata=salectedroletcodeplant[indexcount]; 
								 console.log(JSON.stringify(tpdata));
								 tpdata.count=tpdata.count-1;
								 //if( tpdata.count<=3){
									/* $row.removeClass('duplicatrole');
									 assignrolestable.$('tr.odd-row').each(
												function() {
													$(this).find('td').css(
															"background-color",
															"transparent");
												});
										if ($(this).hasClass('odd-row')) {
											$(this).find('td').css(
													'background-color',
													'transparent');
										}*/
								// }
								 salectedroletcodeplant.splice(indexcount, 1, tpdata);
								 //salectedroletcodeplant.push({"roleId": roleId,"tcode": row.tcode,"plant": row.plant,"count": 1});
								 console.log(JSON.stringify(tpdata));								
							 }
							 }
							//updateDataTableSelectAllCtrl(assignrolestable);
							e.stopPropagation();
						});
				$('#assignrolestable').on('click', 'button#btnGETTCODES', function () {
					   // alert("Hello "+seletedrequest);
					    var closestRow = $(this).closest('tr');
					    var data = assignrolestable.row(closestRow).data();
					   // seletedrequest=data;
						console.log(JSON.stringify(data));
						
						var sdata=	{};
						sdata.roleId= data.roleId;
						sdata.roleCode=  data.roleCode;
						sdata.tcode=  data.tcode;
					    //	delete	sdata.createdOn;
						//  delete	sdata.requestDate;
						console.log(JSON.stringify(sdata));
						//return;
						getTcodeByRoles(sdata);
						
					});

	function	getTcodeByRoles(data){
		$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'POST',
			url : 'getTcodeByRoles',
			data : JSON
			          .stringify(data),
			dataType : 'json',
			async : false,
			success : function(data) {
				
				 $('#pageblock').modal('hide');
				console.log(JSON.stringify(data));
				opentTcodeRolesModel(data);
			    
			},
			error : function(
					xhRequest,
					ErrorText,
					thrownError) {
				console
						.log(JSON
								.stringify(xhRequest));
				 $('#pageblock').modal('hide');
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
					confirmButtonText : 'Error!'
				});
		     	}
		   });

	   };
	   
		function	opentTcodeRolesModel(data){
			var tableData = data;
			
			var thead = '<thead><tr style="font-weight:100 !important; color: #fbf6f6;font-size:14px !important;padding:5px !important;background-color: #797979;;">'
			+ '<th style="width: 10%">Seq</th>'
			+ '<th style="width: 40%">T-code </th>'										
			+ '<th style="width: 40%">T-Code Description</th>'			
			+ '<th style="width: 10%">Sensitive</th>'			
			+ '</tr></thead>';
		
			var tagOpen='<span style="color:blue;font-weight:bold;">';
			var redOpen='<span style="color:red;font-weight:bold;">';
			var tagClosed='</span>';
			$('#roleTcodeTable').html(thead);
			var table = document
			.getElementById("roleTcodeTable");
			var tableBody = document
			.createElement('tbody');
			
			var i=1;
			
				
			for (let [key, value] of Object.entries(tableData)) {	
				
				    console.log(JSON.stringify(value));
				    console.log(JSON.stringify(key));
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
					
					cell1.appendChild(document
					.createTextNode(i++));// satId ,tcode, tcodeDesc
					cell2.appendChild(document
					.createTextNode(value.tcode));
					
					cell3.appendChild(document
					.createTextNode(value.tcodeDesc));
					
					cell4.appendChild(document
					.createTextNode(value.sensitive==='Y'?'Yes':'No'));
					row.appendChild(cell1);
					row.appendChild(cell2);
					row.appendChild(cell3);
					row.appendChild(cell4);
					
					tableBody.appendChild(row);
					
				};
			
			
			table.appendChild(tableBody);
			
			$('#roleTcodeModal').modal('show');
			$('#roleTcodeModal').find('.modal-title').text("T-codes");
		};
		
		$('#roleTcodeModal').on('hidden.bs.modal', function() {
			//$("#showttable > body").empty()
			$("#roleTcodeTable").empty();
			//table.html("");
		});
	
	
				$('#assignrolestable thead input[name="select_all"]').on('click',function(e) {
					if (this.checked) {
						$(
						'#assignrolestable tbody input[type="checkbox"]:not(:checked)')
						.trigger('click');
						} else {
						$(
						'#assignrolestable tbody input[type="checkbox"]:checked')
						.trigger('click');
					}
					
					// Prevent click event from propagating to parent
					e.stopPropagation();
				});
                            /*    var selectedrole=null;
								$('#assignrolestable tbody').on(
										'click',
										'tr',
										function() {
											
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
															'##A6DFC1');
												}
											} else {
												assignrolestable.$('tr.selected')
														.removeClass('selected');

												assignrolestable.$('tr.odd-row').each(
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
												//roleuser = roletousertable.row(this).data();
												// rows_selected.push({"roleId": roleId,"roleCode": roleCode,"roleName": roleName, "funId": funId, "funCode": funCode });
												// rows_selected.push({"funId":funId,"roleId":roleId,"roleCode":roleCode,"funCode":funCode,"roleName":roleName});
												selectedrole=data;
												console.log(JSON.stringify(selectedrole));
											//	roleuserseleted = true;
												$(this).addClass('selected');
												$(this).find('input:radio').prop('checked', true);
											}
										});*/

			
								
								
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
					//selected_roles=[];
					//salectedroletcodeplant=[];
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
					//selected_roles=[];
					//salectedroletcodeplant=[];
					//updateDataTableSelectAllCtrl(assignrolestable);

				}); 
						
						
			
						$('#btassignRoles').on('click',
								function(e) {
							//console.log(JSON.stringify(seletedrequest));
							if(isEmpty(seletedrequest)){								
								swal("Select row",
								"Select Request and t-codes for get role.");
								return;
							   }
							
							if(isEmpty(selected_roles)){								
								swal("Select role",
								"Select row for check SOD against roles.");
								return;
							   }
							resetErrors();
							
							if(seletedrequest.requestType=="T-code Request"){
							var tempAssignRoles =assignrolestable.rows().data();
							console.log(tempAssignRoles.length);
							var notSelectedTcode="";
							var roleNotSelectedTcode=[];
							for (var i = 0; i < tempAssignRoles.length; i++){
								var result = salectedroletcodeplant.find(obj => {
									 // return (obj.tcode == tempAssignRoles[i].tcode && obj.plant == tempAssignRoles[i].plant)
									  return (obj.tcode == tempAssignRoles[i].tcode )
								})
								//var indexcount = roleNotSelectedTcode.findIndex(row => ((row.tcode===tempAssignRoles[i].tcode) && (row.plant===tempAssignRoles[i].plant)));
								var indexcount = roleNotSelectedTcode.findIndex(row => ((row.tcode===tempAssignRoles[i].tcode)));
								if(isEmpty(result) && indexcount==-1 ){
									//roleNotSelectedTcode.push({"tcode": tempAssignRoles[i].tcode,"plant": tempAssignRoles[i].plant});
									roleNotSelectedTcode.push({"tcode": tempAssignRoles[i].tcode});
									notSelectedTcode+=tempAssignRoles[i].tcode+", ";
								}
							}	
							console.log(roleNotSelectedTcode);
							seletedrequest.sapAmRolesRequestVO= selected_roles;
							delete	seletedrequest.createdOn;
							delete	seletedrequest.requestDate;
							//console.log(JSON.stringify(seletedrequest));
							var result =	salectedroletcodeplant.find(obj => {
								  return obj.count >= 3
							})
							if(isEmpty(result) && isEmpty(notSelectedTcode)){
							     chekConfilct(seletedrequest);
							}else if(!isEmpty(notSelectedTcode)){
								swal("Not selected roles for T-codes." ,notSelectedTcode);
								return;
							}
							else{
								swal("Duplicate roles",
								" Remove duplicate roles. ");
								return;
							}
							}else if(seletedrequest.requestType=="Role Request" || seletedrequest.requestType=="Refered Employee") {
								seletedrequest.sapAmRolesRequestVO= selected_roles;
								delete	seletedrequest.createdOn;
								delete	seletedrequest.requestDate;							
								 chekConfilct(seletedrequest);
							}
						});
                    
						
						function onlyUnique(value, index, array) {
							  return array.indexOf(value.tcode && value.plant) === index;
						}
						
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
						var conflictData=null;
						function	chekConfilct(data){
							$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
							conflictData=null;
							$.ajax({
								contentType : 'application/json; charset=utf-8',
								type : 'POST',
								url : 'chekConfilct',
								data : JSON
								          .stringify(data),
								dataType : 'json',
								async : false,
								success : function(data) {
									
									conflictData=data;
									 $('#pageblock').modal('hide');
									console.log(JSON.stringify(data));
									openSaveRoleModel(data);
								
								},
								error : function(
										xhRequest,
										ErrorText,
										thrownError) {
									console
											.log(JSON
													.stringify(xhRequest));
									 $('#pageblock').modal('hide');
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
										confirmButtonText : 'Error!'
									});
								}
							});

						};
						
						function	openSaveRoleModel(data){	
							
							if(data.isConfilct=="N"){
								$(".conflicted").hide();
								$("#nosodlabel").text("No conflicts found, Critical : 0, High : 0,  Medium : 0, Low : 0 ");
          					    $(".noconflicted").show();
								
							}
                            else  if(data.isConfilct=="Y"){                            	
                            	var tableData = JSON.parse(data.confilct);
                            	var result = tableData.reduce(function (r, a) {
    								r[a.priority.trim()] = r[a.priority.trim()] || [];
    								console.log("===================");
    								console.log(JSON.stringify(a));
    								r[a.priority.trim()].push(a);					
    								//max=r[a.riskCode].length>max?r[a.riskCode].length:max;
    								//console.log(max);
    								return r;
    							}, Object.create(null));
                	                //  console.log(JSON.stringify(data.confilct));
                        	          //openConflictedModel(data.confilct);
                	               console.log(JSON.stringify(result));      
                	               // $(".vplant").hide();
                	               var Critical=0;
                	               var High=0;
                	               var Medium=0;
                	               var Low=0;
                	               for (let [key, value] of Object.entries(result)) {
                	            	   
                	                    if(key==='Critical')
                	                    	Critical = Math.floor(value.length/2);
                	                    if(key==='High')
                	                    	High = Math.floor(value.length/2);
                	                    if(key==='Medium')
                	                    	Medium = Math.floor(value.length/2);
                	                    if(key==='Low')
                	                    	Low = Math.floor(value.length/2);
								    
								    console.log(JSON.stringify(key));
								    console.log(JSON.stringify(value.length));
                	               }
                	               var label=" Critical :"+ Critical+", High : "+High+",  Medium :" +Medium+", Low : "+Low;
                	               $(".noconflicted").hide();
            					   $("#sodlabel").text(label);
            					   $(".conflicted").show();	 
            					 
                            }	
							$('#showrolesmodal').modal('show');
							$('#showrolesmodal').find('.modal-title').text("Ccoflict  found to user");
						};
						
						$('#showrolesmodal').on('hidden.bs.modal', function() {							
                            $('#declareChk').prop('checked', false);
                            $('#sodremarks').val("");							
						});
						$('#btnShowSOD').on('click',
								function(e) {
							
							if(isEmpty(conflictData)){								
								
								return;
							  }
							
							openConflictedModel(conflictData.confilct);
							
						});	
						
						
						function	openConflictedModel(data){
							var tableData = JSON.parse(data);
							//function createTable(tableData) {
							/* var table = document.
							.createElement('table'); */
							//console.log(JSON.stringify(tableData.funCode));
							
							var result = tableData.reduce(function (r, a) {
								r[a.riskCode] = r[a.riskCode] || [];
								console.log("===================");
								console.log(JSON.stringify(a));
								r[a.riskCode].push(a);					
								//max=r[a.riskCode].length>max?r[a.riskCode].length:max;
								//console.log(max);
								return r;
							}, Object.create(null));
							
							var thead = '<thead><tr style="font-weight:100 !important; color: #fbf6f6;font-size:14px !important;padding:5px !important;background-color: #797979;;">'
							+ '<th style="width: 10%">Seq</th>'
							+ '<th style="width: 10%">Risk </th>'
							+ '<th style="width: 20%">Function VS </th>'							
							+ '<th style="width: 35%">Risk Description</th>'
							+ '<th style="width: 25%">Priority</th>'
							+ '</tr></thead>';
							//{"rfId":null,"entity":null,"frDesc":null,"funId":28,"funCode":"AP01","funDesc":"AP01 - AP Payments","module":"FICO","riskCode":null,"riskId":null,"status":null}
							var tagOpen='<span style="color:blue;font-weight:bold;">';
							var redOpen='<span style="color:red;font-weight:bold;">';
							var tagClosed='</span>';
							$('#conflictTable').html(thead);
							var table = document
							.getElementById("conflictTable");
							var tableBody = document
							.createElement('tbody');
							
							var i=1;
							
									 console.log(JSON.stringify(result));
							for (let [key, value] of Object.entries(result)) {	
								
								    console.log(JSON.stringify(value));
								    console.log(JSON.stringify(key));
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
									if(value.length==2){
									cell3.appendChild(document
									.createTextNode(value[0].funCode+" vs "+value[1].funCode));}
									else if(value.length==3){
										cell3.appendChild(document.createTextNode(value[0].funCode+" vs "+value[1].funCode+" vs "+value[2].funCode));}
								cell4.appendChild(document
									.createTextNode(value[0].frDesc));
								 	 cell5.appendChild(document
									.createTextNode(value[0].priority));  
									row.appendChild(cell1);
									row.appendChild(cell2);
									row.appendChild(cell3);
									row.appendChild(cell4);
									row.appendChild(cell5);
									tableBody.appendChild(row);
									
								};
							
							
							table.appendChild(tableBody);
							//document.body.appendChild(table);
							// document.getElementById("showttable").innerHTML = tableBody; 
							$('#conflictModal').modal('show');
							$('#conflictModal').find('.modal-title').text("SOD :: Assigned confillcting role to user");
						};
						
						$('#conflictModal').on('hidden.bs.modal', function() {
							//$("#showttable > body").empty()
							$("#conflictTable").empty();
							//table.html("");
						});
						
						$('#btSaveRoles').on('click',
								function(e) {
							 var isSOD="N";
							 if(conflictData.isConfilct=="Y" && ! $('#declareChk').is(":checked")){
								 // it is checked
							
								  
								 swal("Accept  SOD ",
									"Please accept SOD ");
									return;
							}else if(conflictData.isConfilct=="Y" &&  $('#declareChk').is(":checked")){
								 // it is checked
								
								isSOD="Y";
								
							}
							 
							 if($('#sodremarks').val()===""){
								  // it is checked
								 
								 swal("Enter  remarks ",
									"Enter remarks  ");
									return;
							}
							 
							resetErrors();
							
							delete	seletedrequest.createdOn;
							delete	seletedrequest.requestDate;
							console.log(JSON.stringify(seletedrequest));
						   var sdata=seletedrequest;
						   sdata.status="O";
						   sdata.isSOD=isSOD;
						   if(isSOD==="Y")
						   sdata.confilcteds=JSON.parse(conflictData.confilct);						   
						   sdata.reason=$('#sodremarks').val();	
						   console.log(JSON.stringify(sdata));
						   $('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
						   saveRoles(sdata);
						});	
						
						var conflictData=null;
						function	saveRoles(data){
							conflictData=null;
							
							$.ajax({
								contentType : 'application/json; charset=utf-8',
								type : 'POST',
								url : 'approveRole',
								data : JSON
								          .stringify(data),
								dataType : 'json',
								async : false,
								success : function(data) {
									
									conflictData=data;
									console.log(JSON.stringify(data));
									$('.modal').modal('hide');
									seletedrequest=null;
									userRequestDataTable.ajax.url("getUserRequest").load();
									  $('#pageblock').modal('hide');
									swal("Success!",
											data.ok,
											"success");
								
								},
								error : function(
										xhRequest,
										ErrorText,
										thrownError) {
									console
											.log(JSON
													.stringify(xhRequest));
									  $('#pageblock').modal('hide');
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
										confirmButtonText : 'Error!'
									});
								}
							});

						};
						
						$('.modal').on('hidden', function () {
							  // write your code
							});	
				function jsonConcat(o1, o2) {
					 for (var key in o2) {
					  o1[key] = o2[key];
					 }
					 return o1;
					};
					
					
					
					
					var tcodeDataTables	=null;
					tcodeDataTables = $("#tcodeDataTables").DataTable({
						//stateSave: true,
						select : {
							'style' : 'multi'
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
						"sAjaxSource" : "getRequestedTcodeByRequestID/0/0",
						"aaSorting" : [ [ 0, "asc" ] ],	
						"aaSorting" : [],
						"columnDefs" : [ {
							"orderable" : false,
							"targets" : [ 0 ]
							}, 	
							{
								'targets' : 0,
								"searchable" : false,
								"orderable" : false,
								"className" : 'dt-body-center',
								"render" : function(
								data, type,
								full, meta) {
									return '<input type="checkbox" name="id[]" value="'	+ $('<div/>').text(data).html()+ '">';
								}
						},
						
						{
							"name" : "tcode",
							"targets" : 1,
							"defaultContent" : "",
							"searchable" : false,
							"orderable" : false,
							"className" : 'dt-body-center'
							}, {
								"name" : "tcodeDesc",
								"targets" : 2,
								"defaultContent" : "",
								"searchable" : false,
								"orderable" : false,
								"className" : 'dt-body-center'
								},{
							"name" : "module",
							"targets" : 3,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center'
						},
						
						{
							"name" : "sensitive",
							"targets" : 4,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center',
							"render" : function(data, type, full, meta){
					               return getSensitiveHeader(data);
					            }
						},
						{
							"name" : "surRecid",
							"targets" : 5,
							"searchable" : false,
							"orderable" : false,				
							"className" : 'dt-body-center',
							"render" : function( data, type, row, meta ){
								
								return '<button id="delete" type="button" class="btn btn-danger" style="padding: 1px"><i class="fa fa-trash "></i> Reject </button>';
										
							}
						} ],
						"aoColumns" : [  {
							"mData" : "surtRecid"
							}, {
							"mData" : "tcode"
							}, {
							"mData" : "tcodeDesc"
							},{
							"mData" : "module"
							}
							,{
								"mData" : "sensitive"
								},
							{
							"mData" : "surRecid"
							}],
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
					
					
					$('#tcodeDataTables thead input[name="select_all"]').on('click',function(e) {
						if (this.checked) {
							$(
							'#tcodeDataTables tbody input[type="checkbox"]:not(:checked)')
							.trigger('click');
							} else {
							$(
							'#tcodeDataTables tbody input[type="checkbox"]:checked')
							.trigger('click');
						}
						
						// Prevent click event from propagating to parent
						e.stopPropagation();
					});
					$('#tcodeDataTables').on('click','tbody input[type="checkbox"]',	function(e) {
						var $row = $(this).closest('tr');
						
						// Get row data
						var data = tcodeDataTables.row($row).data();
						
						// Get row ID
						//var surtRecid = data.surtRecid;	
						
						var index = rows_selected.findIndex(row => (row.surtRecid === data.surtRecid) && (row.surRecid === data.surRecid) && (row.requestId === data.requestId));
						
						
						if (this.checked && index === -1) {
							rows_selected.push({"surtRecid":data.surtRecid,"surRecid":data.surRecid,"requestId":data.requestId});								
							} else if (!this.checked && index !== -1) {
							rows_selected.splice(index, 1);
							
						}
						
						if (this.checked) {
							$row.addClass('selected');
							} else {
							$row.removeClass('selected');
						}
						
						// Update state of "Select all" control
						updateDataTableSelectAllCtrl(tcodeDataTables);
						
						// Prevent click event from propagating to parent
						e.stopPropagation();
					});
					
					$('#tcodeDataTables').on('draw', function() {
						// Update state of "Select all" control
						//console.log('mastertrole Change');
						updateDataTableSelectAllCtrl(tcodeDataTables);
					});
					$('#tcodeDataTables').on('draw.dt', function() {
						//console.log('mastertrole Change');
						updateDataTableSelectAllCtrl(tcodeDataTables);					
					});
                    function getSensitiveHeader(status){
						
						if(status==='Y'){
							return "Yes";
						}else
							return "No";
						 
					};
					var pantDataTables = $("#pantDataTables").DataTable({
			            select : {
							'style' : 'single'
						},
						
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
						},{
							"name" : "surRecid",
							"targets" : 2,
							"searchable" : false,
							"orderable" : false,				
							"className" : 'dt-body-center',
							"render" : function( data, type, row, meta ){
								
								return '<button id="delete" type="button" class="btn btn-danger" style="padding: 1px"><i class="fa fa-trash "></i> Reject </button>';
										
							}
						} ],
						"aoColumns" : [  {
							"mData" : "plant"
							},{
							"mData" : "purchaseGroup"
						   } ,{
							"mData" : "surRecid"
						} ],
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
							tcodeDataTables.ajax.url("getRequestedTcodeByRequestID/"+seletedrequest.surRecid+"/"+seletedrequest.requestId).load();
							pantDataTables.ajax.url("getRequestedPlantForRequest/"+seletedrequest.surRecid+"/"+seletedrequest.requestId).load();
						}
					};

					
					
					$('#tcodeDataTables  tbody')
					.on('click','button[type="button"]',
					function(e) {
						var $row = $(this).closest('tr');
						
						// Get row data
						var data = tcodeDataTables.row($row)
						.data();
						console.log(JSON.stringify(data));
						  if (tcodeDataTables.rows().data().length==1){
								
								swal("Reject request",
								"Reject request");
								return;
		                } 
						swal({
			                title: "Are you sure to reject?",
			                text: "Once , Review then reject!",
			                type: "warning",
			                showCancelButton: true,
			                confirmButtonClass: "'btn btn-danger'",
			                confirmButtonText: "Yes, reject it!",
			                // closeOnConfirm: false
			            }/*,
			             function(isConfirm) {
		                //alert(isConfirm);					
		              if (isConfirm) {*/
					).then((result) => {
		            	//{"dismiss":"cancel"}
		            	console.log(JSON.stringify(result));
		            	if(isEmpty(result.dismiss)){
		            		$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
			           	   
			           var sdata=	{};
			  		    sdata.status="R";
						sdata.requestId= data.requestId;
						sdata.surtRecid=  data.surtRecid;
						sdata.surRecid=  data.surRecid;
					    //	delete	sdata.createdOn;
						//  delete	sdata.requestDate;
						console.log(JSON.stringify(sdata));
						//return;
						deleteDecodes(sdata);
			              }
			           });
					});
					
					$('#pantDataTables  tbody')
					.on('click','button[type="button"]',
					function(e) {
						var $row = $(this).closest('tr');
						// Get row data
						var data = pantDataTables.row($row)
						.data();
						console.log(JSON.stringify(data));
						if (pantDataTables.rows().data().length==1){
							
							swal("Reject request",
							"Reject request");
							return;
	                  } 
						
						swal({
			                title: "Are you sure to reject?",
			                text: "Once , Review then reject!",
			                type: "warning",
			                showCancelButton: true,
			                confirmButtonClass: "'btn btn-danger'",
			                confirmButtonText: "Yes, reject it!",
			                // closeOnConfirm: false
			            }/*,
			             function(isConfirm) {
		                //alert(isConfirm);					
		              if (isConfirm) {*/
					).then((result) => {
		            	//{"dismiss":"cancel"}
		            	console.log(JSON.stringify(result));
		            	if(isEmpty(result.dismiss)){
		            		$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
			           	   
			           var sdata=	{};
			  		    sdata.status="R";			
						sdata.requestId= data.requestId;
						sdata.surpRecid= data.surpRecid ;
						sdata.surRecid= data.surRecid ;
					    //	delete	sdata.createdOn;
						//  delete	sdata.requestDate;
						console.log(JSON.stringify(sdata));
						//return;
						deletePlantsPurchegroup(sdata);
			              }
			           });
					});
					function	deleteDecodes(rdata){	
						resetErrors();
						$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
						$
								.ajax({
									contentType : 'application/json; charset=utf-8',
									type : 'POST',
									url : 'updateTcode',
									data : JSON
							          .stringify(rdata),
									dataType : 'json',
									async : false,
									success : function(data) {
										console
												.log(JSON
														.stringify(data));
										
										//$("#requestId").val("");
										//$("#surRecid").val("");
										if(!isEmpty(rdata))
											tcodeDataTables.ajax.url("getRequestedTcodeByRequestID/"+rdata.surRecid+"/"+rdata.requestId).load();
										  $('#pageblock').modal('hide');
											swal("Success!",
													data.ok,
													"success");
									},
									error : function(
											xhRequest,
											ErrorText,
											thrownError) {
										console
												.log(JSON
														.stringify(xhRequest));
										 $('#pageblock').modal('hide');
										if (xhRequest.status == 400) {
											var responseText = JSON.parse(xhRequest.responseText);
											console
													.log(responseText);

											validateForm(responseText);
											return false;
										}
										//	mroletable.ajax.url("masterroledata").load();
										swal({
											title : "Error",
											text : JSON.parse(xhRequest.responseText).error,
											type : "error",
											showCancelButton : false,
											confirmButtonClass : 'btn btn-theme04',
											confirmButtonText : 'Error!'
										});
									}
								});
						return false;
					};
					function deletePlantsPurchegroup(rdata){	
						resetErrors();
						$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
						$
								.ajax({
									contentType : 'application/json; charset=utf-8',
									type : 'POST',
									url : 'updatePlantsPurchegroup',
									data : JSON
							          .stringify(rdata),
									dataType : 'json',
									async : false,
									success : function(data) {
										console
												.log(JSON
														.stringify(data));
									  
										if(!isEmpty(rdata))
										 pantDataTables.ajax.url("getRequestedPlantForRequest/"+rdata.surRecid+"/"+rdata.requestId).load();
										  $('#pageblock').modal('hide');
											swal("Success!",
													data.ok,
													"success");
									},
									error : function(
											xhRequest,
											ErrorText,
											thrownError) {
										console
												.log(JSON
														.stringify(xhRequest));
										 $('#pageblock').modal('hide');
										if (xhRequest.status == 400) {
											var responseText = JSON.parse(xhRequest.responseText);
											console
													.log(responseText);

											validateForm(responseText);
											return false;
										}
										//	mroletable.ajax.url("masterroledata").load();
										swal({
											title : "Error",
											text : JSON.parse(xhRequest.responseText).error,
											type : "error",
											showCancelButton : false,
											confirmButtonClass : 'btn btn-theme04',
											confirmButtonText : 'Error!'
										});
									}
								});
						return false;
					};
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
							//$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
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
							//$('#pageblock').modal('hide');
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
							<th style="width: 10%">Employee Name</th>
							<th style="width: 5%">Requester CC</th>
							<th style="width: 5%">Req DEPT</th>
							<th style="width: 4%">Module</th>
							<th style="width: 8%">Request For</th>
							<th style="width: 10%">Applied On</th>
							<th style="width: 40%">Reason</th>
							<th style="width: 5%">Remarks</th>
							<th style="width: 5%">Reject</th>

						</tr>
					</thead>
				</table>
			</div>
		</div>
		<!-- /content-panel -->
		<div id="showRequestType" style="width: 100%; display: none;">
			<div class="col-lg-12 column" style="width: 70%">
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
									<th style="width: 3%"><input name="select_all" value="1"
										type="checkbox"></th>
									<th style="width: 27%">T-code name</th>
									<th style="width: 50%">T-code desc</th>
									<th style="width: 10%">Module</th>
									<th style="width: 5%">Sensitive</th>		
									<th style="width: 5%"></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				<!-- /content-panel -->
			</div>
			<!-- /content-panel -->
			<div class="col-lg-12 column" style="width: 30%">
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
									<th style="width: 50%">Plant name</th>
									<th style="width: 45%">Purchase Group</th>
									<th style="width: 5%"></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				<!-- /content-panel -->
			</div>
		</div>
	</div>


	<div class="modal top fade" id="showtrolesmodal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		data-backdrop="false" data-keyboard="false">
		<div class="modal-dialog modal-side modal-top-right modal-lg">
			<div class="modal-content">
				<div class="modal-header text-white">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">Suggested Roles for selected t-codes</h5>
				</div>
				<div class="modal-body">
					<form id="assgin_role_form">
						<input type="hidden" id="empCode" name="empCode"> <input
							type="hidden" id="sapEmpCode" name="sapEmpCode"> <input
							type="hidden" id="roleId" name="roleId"> <input
							type="hidden" id="roleCode" name="roleCode"> <input
							type="hidden" id="funId" name="funId"> <input
							type="hidden" id="funCode" name="funCode">

					</form>
					<div class="showback">
						<button id="btassignRoles" class="btn btn-success" type="button">
							<i class="glyphicon glyphicon-edit"></i> Check SOD
						</button>
						<!--   <button id="btRejectRoles" type="button" class="btn btn-danger ">
				<i class="fa fa-trash "></i> Reject
			</button></div> -->
					</div>

					<div class="portlet-content form-group">
						<div class="showback" style="color: black;  font-weight: bold;  padding: 4px;    font-family: Verdana, Arial, Helvetica, sans-serif; margin-right: 10px;"><span> T-codes</span>
						     <span id="seletedTocdesApp"> </span>
						 </div>
						<table id="assignrolestable"
							class="table table-bordered table-striped  table-condensed "
							cellspacing="0" width="100%">
							<thead>
								<tr>
									<!--  style="background-color: #d9edf7; height: 30px;" 
							<th style="width: 5%"><input name="select_all" value="1"
									type="checkbox"></th>-->
									<th style="width: 3%"></th>
									<th style="width: 5%">Role Id</th>
									<th style="width: 15%">Role code</th>
									<th style="width: 37%">Role Name</th>
									<th style="width: 5%">Entity</th>
									<th style="width: 10%">T-code</th>
									<th style="width: 5%">Plant</th>
									<th style="width: 5%">Sensitive </th>
									<th style="width: 5%">Conflict count</th>
									<th style="width: 5%"></th>
								</tr>
							</thead>
						</table>

					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal top fade" id="showrolesmodal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		data-backdrop="false" data-keyboard="false">
		<div class="modal-dialog modal-side modal-top-right modal-lg">
			<div class="modal-content">
				<div class="modal-header text-white">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">Assign Roles</h5>
				</div>
				<div class="modal-body">
					<div class="showback">

						<button id="btSaveRoles" type="button" class="btn btn-success ">
							<i class="fa fa-ok "></i> Save & Approved
						</button>
					</div>

				</div>

				<div class="portlet-content form-group">
					<table class="table table-bordered table-striped  table-condensed "
						id="" style="">
						<tr style="width: 100%">
							<td style="width: 10%">Remarks*</td>
							<td style="width: 90%"><input type="text" name="remarks"
								class="form-control" id="sodremarks" placeholder="Enter remarks">
							</td>
						</tr>
						<tr style="width: 100%">
							<td style="width: 10%; display: none;" class="noconflicted"
								colspan="10"><span id="nosodlabel"
								style="font-weight: bold;"></span></td>
						<tr style="width: 100%">
							<td style="width: 10%; display: none;" class="conflicted"><button
									id="btnShowSOD" class="btn btn-success " type="button">
									<i class="glyphicon glyphicon-edit"></i> Show SOD Details
								</button></td>
							<td style="width: 90%; display: none;" class="conflicted"><span
								id="sodlabel" style="color: red; font-weight: bold;"></span></td>
						</tr>
						<tr style="width: 100%">
							<td style="width: 10%; display: none;" class="conflicted"
								colspan="10"><div class="form-check"
									style="display: inline">
									<input type="hidden" value="A" id="status" name="status">
									<input class="form-check-input" type="checkbox" value="N"
										id="declareChk" name="isConflicted"> <label
										class="form-check-label" for="declareChk" style="color: black;  font-weight: bold;  padding: 4px;    font-family: Verdana, Arial, Helvetica, sans-serif;"> <font color=red>★</font>  Mitigations
										methods are followed and maintained by CTM HOD </label>
								</div></td>

						</tr>

					</table>

				</div>

			</div>
		</div>
	</div>

	<div class="modal top fade" id="conflictModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		data-backdrop="false" data-keyboard="false">
		<div class="modal-dialog modal-side modal-top-right modal-lg">
			<div class="modal-content">
				<div class="modal-header text-white">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">Conflict roles</h5>
				</div>
				<div class="modal-body">
					<table id="conflictTable"
						class="table table-bordered table-striped  table-condensed "
						cellspacing="0">
					</table>
				</div>
			</div>

		</div>
	</div>

	<div class="modal top fade" id="roleTcodeModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		data-backdrop="false" data-keyboard="false">
		<div class="modal-dialog modal-side modal-top-right modal-lg">
			<div class="modal-content">
				<div class="modal-header text-white">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">T-code in selected roles</h5>
				</div>
				<div class="modal-body">
					<table id="roleTcodeTable"
						class="table table-bordered table-striped  table-condensed "
						cellspacing="0">
					</table>
				</div>
			</div>

		</div>
	</div>
	<div id="toolbarrole" style="display: none">
		<span class="showback"> <input type="text" name="remarks"
			class="form-control" id="remarks" placeholder="Enter remarks">
			<button id="btassignRoles" class="btn btn-success " type="button">
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
			<button id="btnGetRoles" class="btn btn-success " type="button">
				<i class="glyphicon glyphicon-edit"></i> Get Role
			</button> <!-- <button id="delete" type="button" class="btn btn-danger ">
				<i class="fa fa-trash "></i> Delete
			</button> -->
		</span>

	</div>
	<div class="modal fade" id="pageblock" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">Warning</h4>
				</div>
				<div class="modal-body">
					<div>
						<h4>Please don't close or refresh page until the process is
							completed, Please Wait.</h4>
						<img src="resources/assets/img/loadingr.gif" class="centerblk"/>
					</div>
				</div>
			</div>
		</div>
	</div>
<div class="modal fade" id="modalRemarks" tabindex="-1"
											role="dialog" aria-hidden="true">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-hidden="true" >X</button>
														<h4 class="modal-title" id="myModalLabel">Add Remarks</h4>
													</div>


													<div class="modal-body" id="hiddenRemarks">
														 <textarea id="requestRemarks" name="remarks"   rows="2" cols="255"  maxlength="255" style="color: black"></textarea>
                                                         <span> </span>
													</div>
													<div class="form-group">
																<div class="col-sm-offset-3">
																    <span > <button type="button"  value="Add Remarks" id="btnRemarksAdd"
																		 class="btn btn-primary" >Add Remarks</button>
																	</span>
																	<span > <button type="button"  value="Close"  id="btnRemarksCancle"
																		 class="btn btn-primary" >Close</button>
																	</span>
																	
																</div>
													</div>
															
												</div>
											</div>
	</div>
</section>
