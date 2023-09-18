<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!-- <script>
	$(document)
			.ready(
					function() {
						
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
						
						var requestNumber=null;
						$(document).ready(function() {
							//$("#requestNumber").on("keyup" , function(){
								//var searchRequestNumber = $(this).val();
								//console.log('requestNumber before')
								//$("SAP_AM_USER_REQUESTS").filter(function(){
									//$(this).toggle($(this).text().indexOf(searchRequestNumber) > -1);
									//console.log('requestNumber')
								//});
							//});
							
							$("#searchRequestNumber").on("keyup", function() {
							    var value = $(this).val();

							    $("table tr").each(function(index) {
							        if (index !== 0) {

							            $row = $(this);

							            var id = $row.find().text();
							            
							            console.log("searchRequestNumber id "+id);

							            if (id.indexOf(value) !== 0) {
							                $row.hide();
							            }
							            else {
							                $row.show();
							            }
							        }
							    });
							});
						});
						
						
						
						
					
					function getTocdePlantRequstSelected(seletedrequest){
						if(!isEmpty(seletedrequest)){
							tcodeDataTables.ajax.url("getRequestedTcode/"+seletedrequest.surRecid+"/"+seletedrequest.requestId).load();
							pantDataTables.ajax.url("getRequestedPlantForRequest/"+seletedrequest.surRecid+"/"+seletedrequest.requestId).load();
							//approvalDetailsTables.ajax.url("getUserRequestStatus/"+seletedrequest.surRecid+"/"+seletedrequest.requestId).load();
							approvalDetailsTables.ajax.url("getUserRequestStatus/2397/11820231").load();
							console.log(getUserRequestStatus/2397/11820231);
						}
					};

					
					/* var approvalDetailsTables=null;
					approvalDetailsTables = $("#approvalDetailsTables").DataTable */
					$(document).ready(function(){
						 var approvalDetailsTables=null;
						approvalDetailsTables = $("#approvalDetailsTables").DataTable({
						stateSave: true,
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
						"sAjaxSource" : "getUserRequestStatus/2397/11820231",
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
					};
					//$("div.toolbars").html($('#toolbar').html());
				
					$('#approvalDetailsTables').on('draw', function() {
						// Update state of "Select all" control
						console.log('approvalDetailsTables Change 1');
						//$('#assgin_role_form').trigger("reset");
						
						// rows_selected=[];
						
					});
					$('#approvalDetailsTables').on('draw.dt', function() {
						console.log('approvalDetailsTables Change 2');
						
						// rows_selected=[];
						

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
</script> -->
<section class="wrapper site-min-height">

	<div id="wait" class="uiblock"
		style="display: none; width: 169px; height: 189px; border: 1px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
		<img src='resources/assets/img/ajax-loader.gif' width="164"
			height="164" /><br>
	</div>
	<div class="col-lg-12 column" style="width: 100%">
	 	<!-- <div class="portlet">
			<div class="portlet-header">
				<span class="ui-text">User Request for approval </span>
				<!--  <mark></mark> 
			</div>
			<div class="portlet-content form-group">
				<div class="showback"></div>

				<table id="requestNumber"
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
		</div> -->
		
		<div class="portlet">
			<div class="portlet-header">
				<span class="ui-text">Request Number</span>
				<!--  <mark></mark> -->
			</div>
			<div class="portlet-content form-group" style="padding: 10px">
				<div class="showback"></div>
				
				<form id="requestNumberForm">
				<td class="request_sec" style="width: 40%">
						<fieldset id="group1">
							<input type="text" maxlength="8" name="requestNumber" id="searchRequestNumber" value="" placeholder="Enter Request Number"/>
						</fieldset>
				</td>
				</form>
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
						<tbody>
							<tr>
								<td>2435</td>
								<td>2435</td>
								<td>2435</td>
								<td>2435</td>
								<td>2435</td>
								<td>2435</td>
								<td>2435</td>
							</tr>
							<tr>
								<td>24353</td>
								<td>24354</td>
								<td>24356</td>
								<td>24355</td>
								<td>24357</td>
								<td>24358</td>
								<td>24359</td>
							</tr>
						</tbody>
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
