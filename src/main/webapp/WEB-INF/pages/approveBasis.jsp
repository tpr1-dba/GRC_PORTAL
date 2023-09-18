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
							 "bFilter" : false,
							"responsive" : true,
							"bProcessing" : true,
							"bServerSide" : true,
							//Default: Page display length
							"pageLength": 100,
						    "lengthMenu": [100, 50, 25,10],
							"iDisplayLength" : 100,
							//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
							"iDisplayStart" : 0,
							"sAjaxSource" : "getBasisRequest",
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
								"name" : "requestType",
								"targets" : 3,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "entity",
								"targets" : 4,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},{
								"name" : "department",
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
								"name" : "sapModuleCode",
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
							} ,{
								"name" : "surRecid",
								"targets" : 9,
								"searchable" : false,
								"orderable" : false,				
								"className" : '',
								"render" : function( data, type, row, meta ){
									
									return '<button id="btnHRRemarks" type="button" class="btn btn-success" style="padding: 1px" data-toggle="tooltip" data-placement="top" title="Enter remarks!"><i class="glyphicon glyphicon-pencil">     </i></button>';
											
								}
							   } ,{
								"name" : "surRecid",
								"targets" : 10,
								"searchable" : false,
								"orderable" : false,				
								"className" : 'dt-body-center',
								"render" : function( data, type, row, meta ){
									
									return '<button id="btnHRApprove" type="button" class="btn btn-success" style="padding: 1px"><i class="fa fa-submit "></i> Approve </button>';
											
								}
							} 
							,{
								"name" : "surRecid",
								"targets" : 11,
								"searchable" : false,
								"orderable" : false,				
								"className" : 'dt-body-center',
								"render" : function( data, type, row, meta ){
									
									return '<button id="btnHRReject" type="button" class="btn btn-danger" style="padding: 1px"><i class="fa fa-trash "></i> Reject </button>';
											
								}
							}],	
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
						
						
						 var seletedrequest=null;
						 $('#userRequestDataTable  tbody')
							.on('click','input[type="radio"]',
							function(e) {
									 rows_selected=[];
										var $row = $(this).closest('tr');
										var data = userRequestDataTable.row($row).data();
									
									    seletedrequest=null;
									if ($(this).hasClass('selected')) {												
										$(this).removeClass('selected');
										 $("#showRequestType").hide();
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
										
											 $("#showRequestType").show();	
											 getAssignedRoleBasis(seletedrequest);
										
										 
										// rows_selected.push({"roleId": roleId,"roleCode": roleCode,"roleName": roleName, "funId": funId, "funCode": funCode });
										// rows_selected.push({"funId":funId,"roleId":roleId,"roleCode":roleCode,"funCode":funCode,"roleName":roleName});
										console.log(JSON.stringify(seletedrequest));
									//	roleuserseleted = true;
										$(this).addClass('selected');
										$(this).prop('checked', true);
									}
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
						 
						 $('#userRequestDataTable').on('click', 'button#btnHRApprove', function () {
							   // alert("Hello "+seletedrequest);
							    var closestRow = $(this).closest('tr');
							    var data = userRequestDataTable.row(closestRow).data();
							    
							    
							    if(isEmpty(seletedrequest)){								
									swal("Select row",
									"Select request and review roles! ");
									return;
								  }
						
							    if(data.requestId!==seletedrequest.requestId){
							    	swal("Worng  selection",
									"Aprrove same request as selected ");
									return;
							    }
							    if(isEmpty($("#"+seletedrequest.requestId).val())){								
									swal("Enter remarks",
									"Enter  remarks! ");
									return;
								  }
							    seletedrequest=data;
								console.log(JSON.stringify(data));
								swal({
					                title: "Are you sure to approve?",
					                text: "Once , Review!",
					             //  type: "warning",
					                html: '<h7>Action taken at SAP! <input type="checkbox" id="roleinsap"  /></h7><p/>' ,
					                showCancelButton: true,
					                confirmButtonClass: "'btn btn-ok'",
					                confirmButtonText: "Yes, Approve it!",
					              //  closeOnConfirm: false,
					                preConfirm: () => {
							            var roleinsap = document.getElementById('roleinsap').checked
							            //var cigarro = document.getElementById('cigarro').checked
							            //console.log("Alcool = " + alcool + " cigarro = "+ cigarro)


							          //  return {roleinsap: roleinsap, cigarro: cigarro}
							            return {roleinsap: roleinsap}
							            //return false
							          }
					            }).then((result) => {
					            	//{"dismiss":"cancel"}
					            	console.log(JSON.stringify(result));
					            	if(isEmpty(result.dismiss)){
					            	if(!isEmpty(result.value.roleinsap) && result.value.roleinsap==false){"1 gdsgsdgsdg    "+console.log(JSON.stringify(result.value.roleinsap));					           
					            	   swal("<h7>Assign role in SAP then approve !</h7>");}
					            	else if(!isEmpty(result.value.roleinsap) && result.value.roleinsap==true) {	
					            		console.log("2  sdgsdgdsg >>>  "+JSON.stringify(result.value.roleinsap));					            	
					            		$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
					            var sdata=	{};
					  		    sdata.status="A";
								sdata.requestId= data.requestId;
								sdata.surRecid=  data.surRecid;
								sdata.swmRecid=  data.swmRecid;
								sdata.remarks=  $("#"+seletedrequest.requestId).val();
							    //	delete	sdata.createdOn;
								//  delete	sdata.requestDate;
								console.log(JSON.stringify(sdata));	
								//return;
								saveAssignedRolesBasis(sdata);
								//updateRequestEmpHod(sdata);
					            	}
					            	}	
					           });   
								
							});

						$('#userRequestDataTable').on('click', 'button#btnHRReject', function () {
							   // alert("Hello "+seletedrequest);
							    var closestRow = $(this).closest('tr');
							    var data = userRequestDataTable.row(closestRow).data();
							    
							    if(isEmpty(seletedrequest)){								
									swal("Select row",
									"Select request and review roles! ");
									return;
								  }
						
							    if(data.requestId!==seletedrequest.requestId){
							    	swal("Worng  selection",
									"Aprrove same request as selected ");
									return;
							    }
							    
							    if(isEmpty($("#"+seletedrequest.requestId).val())){								
									swal("Enter remarks",
									"Enter rejection remarks! ");
									return;
								  }
							    seletedrequest=data;								
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
				            		//$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
					           	   
					           var sdata=	{};
					  		    sdata.status="R";
								sdata.requestId= data.requestId;
								sdata.surRecid=  data.surRecid;
								sdata.swmRecid=  data.swmRecid;
								sdata.remarks=  $("#"+seletedrequest.requestId).val();
							    //	delete	sdata.createdOn;
								//  delete	sdata.requestDate;
								console.log(JSON.stringify(sdata));
								//return;
								//updateRequestEmpHod(sdata);
								deleteUserRequest(sdata);
					              }
					           });
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
			            		//$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
				           	   
				           var sdata=	{};
				  		    sdata.status="R";
							sdata.requestId= data.requestId;
							sdata.surRecid=  data.surRecid;
						    //	delete	sdata.createdOn;
							//  delete	sdata.requestDate;
							console.log(JSON.stringify(sdata));
							//return;
							deleteUserRequest(sdata);
				              }
				           });
						});
						
						function	deleteUserRequest(data){	
							resetErrors();
							$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
							$
									.ajax({
										contentType : 'application/json; charset=utf-8',
										type : 'POST',
										url : 'updateRequestBasis',
										data : JSON
								          .stringify(data),
										dataType : 'json',
										async : false,
										success : function(data) {
											console
													.log(JSON
															.stringify(data));
											userRequestDataTable.ajax.url("getBasisRequest").load();
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
						
					function getAssignedRoleBasis(seletedrequest){
						if(!isEmpty(seletedrequest)){
							assignedRoleDataTables.ajax.url("getAssignedRoleBasis/"+seletedrequest.requestId+"/"+seletedrequest.surRecid).load();							
						}
					};

					
					
					
					var assignedRoleDataTables	=null;
					assignedRoleDataTables = $("#assignedRoleDataTables").DataTable({
						//stateSave: true,
						select : {
							'style' : 'multi'
						},
						"dom" : 'lf<"toolbars">rtip',
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
						"sAjaxSource" : "getAssignedRoleBasis/0/0",
						"aaSorting" : [ [ 0, "asc" ] ],	
						"aaSorting" : [],
						"columnDefs" : [ {
							"orderable" : false,
							"targets" : [ 0 ]
							}, 	
							
						
						{
							"name" : "tcode",
							"targets" : 0,
							"defaultContent" : "",
							"searchable" : false,
							"orderable" : false,
							"className" : 'dt-body-center'
							}, {
								"name" : "roleCode",
								"targets" : 1,
								"defaultContent" : "",
								"searchable" : false,
								"orderable" : false,
								"className" : 'dt-body-center'
								},{
							"name" : "plant",
							"targets" : 2,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center'
						},{
							"name" : "purchaseGroup",
							"targets" : 3,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center'
						},{
							"name" : "isConflicted",
							"targets" : 4,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "<i>No Conflict</i>",
							"className" : 'dt-body-center',
							"render" : function( data, type, row, meta ){
								if(typeof(data) !== "undefined" ){
								console.log(JSON.stringify(type)+" ### "+JSON.stringify(row)+"  @@@  "+JSON.stringify(data));
								return (typeof(data) !== "undefined" && data=== 'Y' ) ? '<button id="showConflicte" type="button" class="btn btn-success" style="padding: 1px"><i class="fas fa-list"></i> Conflict</button>'
								: "<i>No Conflict</i>";}
								//return "";		
							}
						} 
						,{
							"name" : "sensitive",
							"targets" : 5,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "<i>No Sensitive T-codes</i>",
							"className" : 'dt-body-center',
							"render" : function( data, type, row, meta ){
								if(typeof(data) !== "undefined" ){
								console.log(JSON.stringify(type)+" ### "+JSON.stringify(row)+"  @@@  "+JSON.stringify(data));
								return (typeof(data) !== "undefined" && data=== 'Y' ) ? '<button id="btnGETTCODES" type="button" class="btn btn-success" style="padding: 1px"><i class="fas fa-list"></i> Sensitive T-codes</button>'
								: "<i>No Sensitive T-codes</i>";}
								//return "";		
							}
						} ,
						{
							"name" : "suarRecid",
							"targets" : 6,
							"searchable" : false,
							"orderable" : false,				
							"className" : 'dt-body-center',
							"render" : function( data, type, row, meta ){
								
								return '<button id="delete" type="button" class="btn btn-danger" style="padding: 1px"><i class="fa fa-trash "></i> Reject </button>';
										
							}
						} ],
						"aoColumns" : [  {
							"mData" : "tcode"
							}, {
							"mData" : "roleCode"
							},{
							"mData" : "plant"
							}, {
							"mData" : "purchaseGroup"
							},{
							"mData" : "isConflicted"
							}
							,{
							"mData" : "sensitive"
							},
							{
							"mData" : "suarRecid"
							}],
						"rowCallback" : function(row,
						data, dataIndex) {									
							console
							.log("row "
							+ JSON
							.stringify(data));				
							/* if(rows_selected.filter( e => (e.surtRecid === data.surtRecid) && (e.surRecid === data.surRecid) && (e.requestId === data.requestId) ).length > 0){		
								$(row).find('input[type="checkbox"]').prop('checked',true);
								$(row).addClass('selected');
							} */
						}
					});
					$("div.toolbars").html($('#toolbar').html());
					
					$('#assignedRoleDataTables thead input[name="select_all"]').on('click',function(e) {
						if (this.checked) {
							$(
							'#assignedRoleDataTables tbody input[type="checkbox"]:not(:checked)')
							.trigger('click');
							} else {
							$(
							'#assignedRoleDataTables tbody input[type="checkbox"]:checked')
							.trigger('click');
						}
						
						// Prevent click event from propagating to parent
						e.stopPropagation();
					});
					$('#assignedRoleDataTables').on('click','tbody input[type="checkbox"]',	function(e) {
						var $row = $(this).closest('tr');
						
						// Get row data
						var data = assignedRoleDataTables.row($row).data();
						
						// Get row ID
						//var surtRecid = data.surtRecid;	
						
						var index = rows_selected.findIndex(row => (row.suarRecid === data.suarRecid)&& (row.surRecid === data.surRecid) && (row.requestId === data.requestId));
						
						
						if (this.checked && index === -1) {
							rows_selected.push({"suarRecid":data.suarRecid,"surRecid":data.surRecid,"requestId":data.requestId});								
							} else if (!this.checked && index !== -1) {
							rows_selected.splice(index, 1);
							
						}
						
						if (this.checked) {
							$row.addClass('selected');
							} else {
							$row.removeClass('selected');
						}
						
						// Update state of "Select all" control
						//updateDataTableSelectAllCtrl(assignedRoleDataTables);
						
						// Prevent click event from propagating to parent
						e.stopPropagation();
					});
					
					$('#assignedRoleDataTables').on('draw', function() {
						// Update state of "Select all" control
						//console.log('mastertrole Change');
						//updateDataTableSelectAllCtrl(assignedRoleDataTables);
					});
					$('#assignedRoleDataTables').on('draw.dt', function() {
						//console.log('mastertrole Change');
						//updateDataTableSelectAllCtrl(assignedRoleDataTables);					
					});
					
					$('#assignedRoleDataTables').on('click', 'button#showConflicte', function () {
						   // alert("Hello "+seletedrequest);
						    var closestRow = $(this).closest('tr');
						    var data = assignedRoleDataTables.row(closestRow).data();
							if(isEmpty(data)){								
								swal("Select row",
								"Select roles ");
								return;
							   }
							//"requestId":27420233
							console.log(JSON.stringify(data));
							 getConfilct(data.requestId)   
							//return false;
						});
					
					var conflictData=null;
					function	getConfilct(data){
						conflictData=null;
						$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
						$.ajax({
							contentType : 'application/json; charset=utf-8',
							type : 'GET',
							url : 'getConfilctByRequestID/'+data,							
							dataType : 'json',
							async : false,
							success : function(data) {
								$('#pageblock').modal('hide');
								conflictData=data;
								console.log(JSON.stringify(data));
								openConflictedModel(data);
							return;
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
					$('#assignedRoleDataTables').on('click', 'button#btnGETTCODES', function () {
						   // alert("Hello "+seletedrequest);
						    var closestRow = $(this).closest('tr');
						    var data = assignedRoleDataTables.row(closestRow).data();
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
					
					$('#assignedRoleDataTables c tbody')
					.on('click','button[type="button"]',
					function(e) {
						var $row = $(this).closest('tr');
						
						// Get row data
						var data = assignedRoleDataTables.row($row)
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
		            		//$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
			           	   
			           var sdata=	{};
			  		    sdata.status="R";
						sdata.requestId= data.requestId;
						sdata.suarRecid=  data.suarRecid;
						sdata.surRecid=  data.surRecid;
					    //	delete	sdata.createdOn;
						//  delete	sdata.requestDate;
						console.log(JSON.stringify(sdata));
						//return;
						deleteDecodes(sdata);
			              }
			           });
					});
					$('#assignedRoleDataTables').on('click', 'button#delete', function () {	
						var $row = $(this).closest('tr');
						
						// Get row data
						var data = assignedRoleDataTables.row($row)
						.data();
						console.log(JSON.stringify(data));
						if (assignedRoleDataTables.rows().data().length==1){
							
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
		            		//$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
			           	   
			           var sdata=	{};
			  		    sdata.status="R";
						sdata.requestId= data.requestId;
						sdata.suarRecid=  data.suarRecid;
						sdata.surRecid=  data.surRecid;
					    //	delete	sdata.createdOn;
						//  delete	sdata.requestDate;
						console.log(JSON.stringify(sdata));
						//return;
						deleteDecodes(sdata);
			              }
			           });
					});
					
					function	deleteDecodes(data){	
						var requestId =data.requestId; var surRecid=data.surRecid;
						$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
						resetErrors();
						$
								.ajax({
									contentType : 'application/json; charset=utf-8',
									type : 'POST',
									url : 'updateAssignedRolesBasis',
									data : JSON
							          .stringify(data),
									dataType : 'json',
									async : false,
									success : function(data) {
										console
												.log(JSON
														.stringify(data));
										
										
										if(!isEmpty(data)){
											assignedRoleDataTables.ajax.url("getAssignedRoleBasis/"+requestId+"/"+surRecid).load();							
										}
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
					function isEmpty(val) {
						return (val === undefined || val == null || val.length <= 0) ? true
								: false;
					};
				
			
			
					$('#assignedRoleDataTables_wrapper #btnApproveRoles').on('click',
							function(e) {				
					   // alert("Hello "+seletedrequest);
						if(isEmpty(rows_selected)){								
							swal("Select row",
							"Select roles ");
							return;
						   }
						else if (assignedRoleDataTables.rows().data().length!==rows_selected.length){
							
							swal("Select All role",
							"Approve All role or reject remaining");
							return;
						   } 
								
						
						
						 
						console.log(JSON.stringify(rows_selected));
						saveAssignedRolesBasis(rows_selected[0])
																	
					});
			
					
					function	saveAssignedRolesBasis(data){	
						var requestId =data.requestId; var surRecid=data.surRecid;
						$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
						resetErrors();
						$
								.ajax({
									contentType : 'application/json; charset=utf-8',
									type : 'POST',
									url : 'saveAssignedRolesBasis',
									data : JSON
							          .stringify(data),
									dataType : 'json',
									async : false,
									success : function(data) {
										console
												.log(JSON
														.stringify(data));
										
										userRequestDataTable.ajax.url("getBasisRequest").load();
										/* if(!isEmpty(data)){
											assignedRoleDataTables.ajax.url("getAssignedRoleBasis/"+requestId+"/"+surRecid).load();							
										} */
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
			
	
				var conflictData=null;
				function	chekConfilct(data){
					conflictData=null;
					$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
					$.ajax({
						contentType : 'application/json; charset=utf-8',
						type : 'POST',
						url : 'chekConfilct',
						data : JSON
						          .stringify(data),
						dataType : 'json',
						async : false,
						success : function(data) {
							 $('#pageblock').modal('hide');
							conflictData=data;
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
				
				
				
				function	openConflictedModel(data){
					//var tableData = JSON.parse(data);
					var tableData = data;
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
				   sdata.status="C";
				   sdata.isSOD=isSOD;
				   sdata.reason=$('#sodremarks').val();	
					console.log(JSON.stringify(sdata));
				   saveRoles(sdata);
				});	
				
				var conflictData=null;
				function	saveRoles(data){
					conflictData=null;
					$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
					$.ajax({
						contentType : 'application/json; charset=utf-8',
						type : 'POST',
						url : 'approveRole',
						data : JSON
						          .stringify(data),
						dataType : 'json',
						async : false,
						success : function(data) {
							 $('#pageblock').modal('hide');
							conflictData=data;
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
				
				
		   function jsonConcat(o1, o2) {
			 for (var key in o2) {
			  o1[key] = o2[key];
			 }
			 return o1;
			};
					function resetErrors() {
							$('form input, form select').removeClass(
									'inputTxtError');
							$('label.error').remove();
					};

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
						//	$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
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
						//	$('#pageblock').modal('hide');
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
							<th style="width: 41%">Reason</th>
							<th style="width: 3%">Remarks</th>
							<th style="width: 5%">Approve</th>
							<th style="width: 5%">Reject</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<!-- /content-panel -->
		<div id="showRequestType" style="width: 100%; display: none;">
			<div class="col-lg-12 column" style="width: 100%">
				<div class="portlet">
					<div class="portlet-header">
						<span class="ui-text">Assigned role for T-code </span>
						<!--  <mark></mark> -->
					</div>
					<div class="portlet-content form-group">
						<div class="showback"></div>

						<table id="assignedRoleDataTables"
							class="table table-bordered table-striped  table-condensed "
							cellspacing="0" width="100%">
							<thead>
								<tr>
									<th style="width: 20%">T-code name</th>
									<th style="width: 50%">Role</th>
									<th style="width: 10%">plant</th>
									<th style="width: 5%">Purchase Group</th>
									<th style="width: 5%">Conflicted</th>
									<th style="width: 5%">Sensitive</th>
									<th style="width: 5%">Reject</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				<!-- /content-panel -->
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
	<div id="toolbar_remove" style="display: none">
		<span class="showback"> <!-- <button id="addRow" type="button" class="btn btn-success">
				<i class="fa fa-plus"></i> Add New
			</button> -->
			<button id="btnApproveRoles" class="btn btn-success" type="button">
				<i class="glyphicon glyphicon-plus"></i> Approve roles
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
</section>
