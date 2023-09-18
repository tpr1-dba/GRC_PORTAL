<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section class="wrapper site-min-height">

	<div id="wait" class="uiblock"
		style="display: none; width: 169px; height: 189px; border: 1px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
		<img src='resources/assets/img/ajax-loader.gif' width="164"
			height="164" /><br>
	</div>
	<div class="col-lg-12 column" style="width: 100%">
		
		<div class="portlet">
			<div class="portlet-header">
				<span class="ui-text">Request Number</span>
				<!--  <mark></mark> -->
			</div>
			<div class="portlet-content form-group" style="padding: 10px">
				<div class="showback"></div>
				
				
				<td class="request_sec" style="width: 40%">
				<form id="requestNumberForm" action="javascript:void(0);" >
						<fieldset id="group1">
							<input type="text" maxlength="8" name="requestNumber" id="searchRequestNumber" value="" placeholder="Enter Request Number"/>
						</fieldset>
				</form>
				</td>
				
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
								<th style="width: 10%">Employee Code</th>
								<th style="width: 10%">Request No</th>
								<th style="width: 10%">Am User RecId</th>
								<th style="width: 5%"></th>
								
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


<div class="modal top fade" id="updateModel" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		data-backdrop="false" data-keyboard="false">
		<div class="modal-dialog modal-side modal-top-right modal-lg">
			<div class="modal-content">
				<div class="modal-header text-white">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">Update</h5>
				</div>
				<div class="modal-body">
					<table class="table table-bordered table-striped  table-condensed " id="" style="">
					<tbody>
						<tr style="width: 100%">
								    <td style="width: 10%"><label for="updateReqNo">Request
											Number</label></td>
									<td style="width: 30%"><input name="updateReqNo" class="form-control" id="updateReqNo" readonly="readonly"></td>
									<td style="width: 10%"><label for="updateWFCode">WorkFlow Level</label></td>
									<td style="width: 30%"><input name="updateWFCode" class="form-control" id="updateWFCode" readonly="readonly"></td>
							</tr>
							<tr style="width: 100%">
								    <td style="width: 10%"><label for="updateSBU">SBU</label></td>
									<td style="width: 30%"><input name="updateSBU" class="form-control" id="updateSBU" readonly="readonly"></td>
									<td style="width: 10%"><label for="updateEmpCode">Employee Code</label></td>
									<td style="width: 30%"><input type='hidden' name="updateEmpCode" class="form-control" id="updateEmpCode">
									<select class="form-control" id="updateEmpCodeDD" name="updateEmpCodeDD">
									   <option>Choose a number</option>	</select>
									</td>
							</tr>
							<tr style="width: 100%">
								<td colspan="4" style="width: 30%"><button type="button" id='updateEmpCodeBtn' class="btn btn-success"><i class="fa fa-plus "></i> Update</button></td>
							</tr>
					</tbody>
					</table>
				</div>
			</div>

		</div>
	</div>

</section>

<script type="text/javascript">

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

var approvalDetailsTables=null;
var seletedrequest=null;
var requestNo=null;
var employeeList=null;
var swmRecidReq=null;
var nempCode=null;

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
	"sAjaxSource" : "getUserViewWorkflowData/0/0",
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
               

               return getStatusTextWorkFlow(data);
            }
    
	} ,
	{
		"name" : "userCode",
		"targets" : 7,
		"searchable" : false,
		"orderable" : false,
		"defaultContent" : "",
		"className" : 'dt-body-center'
	},
	{
		"name" : "requestNo",
		"targets" : 8,
		"searchable" : false,
		"orderable" : false,
		"defaultContent" : "",
		"className" : 'dt-body-center'
	},
	{
		"name" : "sapAmRecId",
		"targets" : 9,
		"searchable" : false,
		"orderable" : false,
		"defaultContent" : "",
		"className" : 'dt-body-center'
	}],	
	"aoColumns" : [{
		"mData" : "wfLevel"
	   },{
			"mData" : "empCode"
		},
		{
			"mData" : "wfCode"
		}, 
		{
			"mData" : "companyCode"
		},
		{
			"mData" : "sbu"
		},
		{
			"mData" : "sapModule"
		},
		{
			"mData" : "status"
		},
		{
			"mData" : "userCode"
		},
		{
			"mData" : "requestNo"
		},
		{
			"mData" : "sapAmRecId"
		},
		{
            "mData": null,
            "searchable" : false,
			"orderable" : false,				
			"className" : 'dt-body-center',
			"render" : function( data, type, row, meta ){
				return '<button type="button" class="btn btn-success"><i class="fa fa-plus "></i> Edit</button>';
			}
		
            //"bSortable": false,
           //"mRender": function (o) { return '<button id="btnHRApprove" type="button" class="btn btn-success" style="padding: 1px"><i class="fa fa-submit "></i> Approve </button>';}
        	   //<a href=#/'+o.userCode+ '>' + 'Edit' + '</a>'; }
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


function onlyUnique(value, index, array) {
         return array.indexOf(value) === index;
}

function stringToArray(value) {
	var array = value.split(",").map(function(item) {
                return item.trim();
    });
    return  array.filter(onlyUnique);
}

//seletedrequest
function getStatusTextWorkFlow(data){
	var status=data.status;
	var stext="Initiated";
	switch(status) {
	  case 'A':
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

function getRolesByRequest(){						     
	 approvalDetailsTables.ajax.url("getUserViewWorkflowData/"+seletedrequest.surRecid+"/"+seletedrequest.requestId).load();
};

function getRolesByRequestBlank(){						     
	 approvalDetailsTables.ajax.url("getUserViewWorkflowData/0/0").load();
};

function openUpdateModel(){
	$('#updateModel').modal('show');
}

function closeUpdateModel(){
	$('#updateModel').modal('hide');
}

approvalDetailsTables.on('click', 'button', function (e) {
    let data = approvalDetailsTables.row(e.target.closest('tr')).data();
    updateDetails(data);
});

function updateDetails(data) {
	getUserBySwmRecid(data.sapAmRecId);
	openUpdateModel();
}

$('#searchRequestNumber').on('keypress',
		function(e) {
	if(e.keyCode == 13){
		getRolesByRequestBlank();
		requestNo= this.value;
		setSelectedValue(requestNo);
		$('#searchRequestNumber').val('');
	}
});

$(document).ajaxComplete(function() {
	$.unblockUI();
	$("#wait").css("display", "none");
});
$(document).ajaxStop($.unblockUI());

 function setSelectedValue(requestNo){
	 //sEcho=1&iColumns=9&sColumns=%2CrequestId%2CemployeeName%2Centity%2Cdepartment%2CrequestType%2CappliedOn%2Cstatus%2Creason&iDisplayStart=0&iDisplayLength=10&mDataProp_0=surRecid&bSortable_0=false&mDataProp_1=requestId&bSortable_1=false&mDataProp_2=employeeName&bSortable_2=false&mDataProp_3=entity&bSortable_3=false&mDataProp_4=department&bSortable_4=false&mDataProp_5=requestType&bSortable_5=false&mDataProp_6=appliedOn&bSortable_6=false&mDataProp_7=status&bSortable_7=false&mDataProp_8=reason&bSortable_8=false&iSortingCols=0&_=1693765473432&
	$.get("getUserRequestHistoryByReqNo?sEcho=1&requestNo="+requestNo, 
			function(data, status){
	    if(data){
	    	var myObj = JSON.parse(data);
	    	if(myObj.iTotalRecords===1){
	    		seletedrequest= myObj.aaData[0];	
	    		getRolesByRequest();
	    	}else {
	    		alert("No Data Found!")
	    		seletedrequest=null;
	    	}
	    }
	  });
}

/* function setSelectedValue(requestNo){
	 approvalDetailsTables.ajax.url("getUserRequestHistoryByReqNo/"+requestNo).load();
	 console.log("test ===================",approvalDetailsTables.ajax.url("getUserRequestHistoryByReqNo/"+requestNo))
	$.get("getUserRequestHistoryByReqNo/"+requestNo, 
			function(data, status){
	    if(data){
	    	var myObj = JSON.parse(data);
	    	if(myObj.iTotalRecords===1){
	    		seletedrequest= myObj.aaData[0];	
	    		getRolesByRequest();
	    	}else {
	    		alert("No Data Found!")
	    		seletedrequest=null;
	    	}
	    }
	  });
} */

$('#approvalDetailsTables').on('click', 'button#approverEdit', function (e) {
	
	/* e.printDefault(); */
	approvalDetailsTables
	
	approvalDetailsTables.edit(e.target.closest('tr'), {
        title: 'Edit record',
        buttons: 'Update'
    });
	
	/* approvalDetailsTables.row(this).edit({
		button: [
			{ label: 'Cancel', fn: function () { this.close(); } },
            'Edit'
		]
	}) */
	console.log("passssssssss");
	alert('open test');
});

function getUserBySwmRecid(swmRecid) {
	$.get("getUserBySwmRecid?swmRecid="+swmRecid, 
			function(data, status) {
	    if(data){
	    	var myObj = JSON.parse(data);
	    	console.log(myObj);
	    	swmRecidReq=swmRecid;
	    	$("#updateReqNo").val(myObj.requestId);
	    	$("#updateWFCode").val(myObj.wfCode);
	    	$("#updateSBU").val(myObj.sbu);
	    	$("#updateEmpCode").val(myObj.empCode);
	    	$('#updateEmpCodeDD').val(myObj.empCode);
	    } else {
	    		alert("No Data Found!")
	    	}
	  });
}


function setEmpOptions() {
	var select = document.getElementById("updateEmpCodeDD");
	for(var i = 0; i < employeeList.length; i++) {
	    var opt = employeeList[i];
	    var el = document.createElement("option");
	    el.textContent = opt.employeeName+'-'+opt.empCode;
	    el.value = opt.empCode;
	    select.appendChild(el);
	}
}

function getAllUsers() {
	$.get("getAllHRMEmployees?isRefresh=false", 
			function(data, status) {
	    if(data) {
	    	employeeList = JSON.parse(data);
	    	setEmpOptions();
	    } else {
	    		alert("No Data Found!")
	    }
	  });
}

getAllUsers();

$('#updateEmpCodeBtn').click(function(e) {
	updateEmpCode();
});


function isNumeric(value) {
    return /^\d+$/.test(value);
}
	
function updateEmpCode(){
	nempCode = $('#updateEmpCodeDD').val();
	$.ajax({url: "updateSapAmUserEmpCodeBySwmRecid?empCode="+nempCode+"&swmRecid="+swmRecidReq,  method: 'post', async: false,
		success: function(result){
			console.log(result)
				alert(result);
			setSelectedValue(requestNo);
			$('#searchRequestNumber').val('');
			closeUpdateModel();
		}});
}

function resetVars(){
	nempCode = null;
	swmRecid = null;
}

});

</script>
