<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script>
	$(document).ready(function() {
		jQuery('.dataTable').wrap('<div class="dataTables_scroll" />');		
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
		function isEmpty(val) {
			return (val === undefined || val == null || val.length <= 0) ? true
					: false;
		};
		function resetErrors() {
			$('form input, form select').removeClass(
					'inputTxtError');
			$('label.error').remove();
		};
		var requestType="T";
		$('input[type=radio][name=requestType]').change(function() {			 
		    if (this.value == "T") {		    	
				   $("#requestTocde").show();
				   $("#requestEmployeeRefrnces").hide();
				   $("#requestRole").hide();
					$.ajax({
						type : 'GET',
						url : "<c:url value="/listHeaderData/T"/>",
						dataType : "json",
						async : false,
						success : handleData,
						error : function(xhRequest, ErrorText,
								thrownError) {

						}
					});
		    }else if (this.value == "R") {
		    	 $("#requestRole").show();
		    	 $("#requestEmployeeRefrnces").hide();
		    	 $("#requestTocde").hide();	
		    		$.ajax({
						type : 'GET',
						url : "<c:url value="/listHeaderData/R"/>",
						dataType : "json",
						async : false,
						success : handleDataRole,
						error : function(xhRequest, ErrorText,
								thrownError) {

						}
					});

		    }else if (this.value == "E") {		    	
		    	 $("#requestEmployeeRefrnces").show();
		    	 $("#requestTocde").hide();	
		    	 $("#requestRole").hide();		    
		    		$.ajax({
						type : 'GET',
						url : "<c:url value="/listHeaderData/E"/>",
						dataType : "json",
						async : false,
						success : handleDataEmployee,
						error : function(xhRequest, ErrorText,
								thrownError) {

						}
					});
		         }

		    });
		
		
		
		    	 function handleDataEmployee(data) {
						console.log(JSON.stringify(data));
						
						var sapCompanies = data.sapCompany;
						var sapModules = data.sapModule;
						var sapCompanyCodeHtml = '';
						if(isEmpty(${pageContext.request.userPrincipal.principal.sapEntityCode})){
						 sapCompanyCodeHtml = '<option value="">Select  sap company</option>';}
						
						
						var sodUserRequests = data.sodUserRequests[0];	
						if(!isEmpty(sodUserRequests )&& !isEmpty(sodUserRequests.requestId ))	{
						$("#EmpRequestId").val(sodUserRequests.requestId);
						$("#EmpSurRecid").val(sodUserRequests.surRecid);					
						$("#reasonEmp").val(sodUserRequests.reason);	}
						//rolesDataTables.ajax.url("getRequestedRolesUserID").load();
						
							for (var i = 0; i < sapCompanies.length; i++) {
					if(!isEmpty(sapCompanies[i].companyCode ))				
					sapCompanyCodeHtml += '<option value="' + sapCompanies[i].companyCode + '">'
							+ sapCompanies[i].companyCode
							+ " , "
							+ sapCompanies[i].companyName
							+ '</option>';
				         }
							$('#empSapCompanyCode').html(sapCompanyCodeHtml);	
							$("#empSapCompanyCode").val(${pageContext.request.userPrincipal.principal.sapEntityCode});
							
					};
					
					 function handleDataRole(data) {
							console.log(JSON.stringify(data));
							
							var sapCompanies = data.sapCompany;
							var sapModules = data.sapModule;				
							var sodUserRequests = data.sodUserRequests[0];	
							if(!isEmpty(sodUserRequests )&& !isEmpty(sodUserRequests.requestId ))	{
							$("#roleRequestId").val(sodUserRequests.requestId);
							$("#roleSurRecid").val(sodUserRequests.surRecid);					
							$("#reasonrole").val(sodUserRequests.reason);	}
							//rolesDataTables.ajax.url("getRequestedRolesUserID").load();
						};
			$.ajax({
						type : 'GET',
						url : "<c:url value="/listHeaderData/T"/>",
						dataType : "json",
						async : false,
						success : handleData,
						error : function(xhRequest, ErrorText,
								thrownError) {

						}
					});
        
			function handleData(data) {
				console.log(JSON.stringify(data));
				
				var sapCompanies = data.sapCompany;
				var sapModules = data.sapModule;				
				var sodUserRequests = data.sodUserRequests[0];				
				var modulelistHtml = '<option value="">Select modules</option>';
				//var sapCompanyCodeHtml = '<option value="">Select  sap company</option>';
				var sapCompanyCodeHtml = '';
				if(isEmpty(${pageContext.request.userPrincipal.principal.sapEntityCode})){
					 sapCompanyCodeHtml = '<option value="">Select  sap company</option>';}
				for (var i = 0; i < sapModules.length; i++) {
					
				     
						modulelistHtml += '<option value="' + sapModules[i].masterIdCode + '">' 
								+ sapModules[i].masterIdCode
								+ " , "
								+ sapModules[i].description
								+ '</option>';						
					}
				
				for (var i = 0; i < sapCompanies.length; i++) {
					if(!isEmpty(sapCompanies[i].companyCode ))				
					sapCompanyCodeHtml += '<option value="' + sapCompanies[i].companyCode + '">'
							+ sapCompanies[i].companyCode
							+ " , "
							+ sapCompanies[i].companyName
							+ '</option>';
				}
				
				

				
				
				$('#modulelist').html(modulelistHtml);			
				$('.sapCompanyCodes').html(sapCompanyCodeHtml);
				$("#sapCompanyCodes").val(${pageContext.request.userPrincipal.principal.sapEntityCode});
				$("#sapCompanyCode").val(${pageContext.request.userPrincipal.principal.sapEntityCode});
				if(!isEmpty(sodUserRequests )&& !isEmpty(sodUserRequests.requestId ))	{
					$("#requestId").val(sodUserRequests.requestId);
					$("#surRecid").val(sodUserRequests.surRecid);
					$("#sapCompanyCodes").val(sodUserRequests.sapCompanyCode);
					$("#modulelist").val(sodUserRequests.module);
					if(sodUserRequests.module=='MM')
					$(".vpgroup").show();	
					$("#sapCompanyCodes").attr("disabled", true); 
					$("#modulelist").attr("disabled", true); 
					$("#sapCompanyCode").val(sodUserRequests.sapCompanyCode);
					$("#sapModuleCode").val(sodUserRequests.module);	
					$("#reason").val(sodUserRequests.reason);
					console.log("+++++++++++++++++++++");
					console.log(sodUserRequests.reason);
				}
			};
			$('#modulelist').on('change', function() {
				   // alert( $(this).find(":selected").val() );
				   if($(this).find(":selected").val()=='MM'){
					  // $(".vplant").hide();
					   $(".vpgroup").show();									 
				     }else{									   
					   $(".vpgroup").hide();
					  // $(".vplant").show();								  
				      }
				   });
			function pGroupListData(data) {
					console.log("pGroupListData " + JSON.stringify(data));
					pGroupList = data;
					//console.log(" masterRoleList  role " + JSON.stringify(masterRoleList));
					var pGroupListHtml = '<option value="">Select Purchasing Group</option>';
					for ( var i = 0; i < data.length; i++) {

						//masterroletHtml += '<option value="' + data[i].roleCode+":"+data[i].entity + '">'
						pGroupListHtml += '<option value="' + data[i].masterIdCode+'">'
								+data[i].masterIdCode+", "+ data[i].description + '</option>';
					}
					//console.log("1 masterRoleList  role " + JSON.stringify(masterRoleList));
					$('#pgroups').html(pGroupListHtml);
				};
			
		
			
		$('select[multiple]').multiselect({
			columns : 1
		});
		
		
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
   };

   $('#modulelist').on('change', function() {
	   // alert( $(this).find(":selected").val() );
	    $('#tcodesText').val("");					
		$('#tcodeId').val("");
		$('#sapModuleCode').val($("#modulelist option:selected").val());
		
     });
     $('.sapCompanyCodes').on('change', function() {
    	 $('#plants').val("");	
    	 $('#sapCompanyCode').val($("#sapCompanyCodes option:selected").val());
     });
		$('#bplant').click(
				function() {						
					resetErrors();
					var sapCompanyCode = $("#sapCompanyCodes option:selected").val();
					if (isEmpty(sapCompanyCode)) {
						var msg = '<label class="error" for="sapCompanyCodes">Select Sap company</label>';
						$('select[name="sapCompanyCodes"]')
						.addClass(
						'inputTxtError')
						.after(msg);	
						swal("Select SAP Company",
						"Select SAP Company to get plant.");
						return;
					}               
					openPlantModel(sapCompanyCode);					
				});
				var planttable = null;
				
				function openPlantModel(selected) {
					rows_selected=[];		
					console.log(selected.toString());
					planttable = $("#planttable").DataTable(
					{
						//stateSave: true,
						select : {
							'style' : 'multi'
						},
						/* "oLanguage" : {
							"sLengthMenu" : "Display _MENU_ ",
						}, */
						"stripeClasses" : [ 'odd-row',
						'even-row' ],
						"dom" : 'l<"apptoolbar">frtip',
						//"dom" : 'l<"toolbar">frtip',
						/* dom: "<'row'<'col-sm-12'tr>>" +
						"<'row'<'col-sm-4'l><'col-sm-8'p>>", */
						//bFilter : false,
						"pageLength": 100,
						"lengthMenu": [100, 50, 25,10],
						"responsive" : true,
						"bProcessing" : true,
						"bServerSide" : true,
						//Default: Page display length
						"iDisplayLength" : 10,
				
						"iDisplayStart" : 0,
						"sAjaxSource" : "getPlantByEntity/"+selected,
						//"aaSorting" : [ [ 0, "asc" ] ],	
						"aaSorting" : [],
						"columnDefs" : [
						{
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
							"name" : "plantCode",
							"targets" : 1,
							"defaultContent" : "",
							"searchable" : false,
							"orderable" : false,
							"className" : 'dt-body-center'
						},
						{
							"name" : "description",
							"targets" : 2,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center'
						},{
							"name" : "sapEntityCode",
							"targets" : 3,
							"searchable" : false,
							"orderable" : false,
							"defaultContent" : "",
							"className" : 'dt-body-center'
						}],
						"aoColumns" : [ {
							"mData" : "plantCode"
							}, {
							"mData" : "plantCode"
							}, {
							"mData" : "description"
							},{
							"mData" : "sapEntityCode"
							}],
						"rowCallback" : function(row,
						data, dataIndex) {
							// Get row ID
							var rowId = data.plantCode;
							console	.log("row "+ JSON.stringify(data)+ " "+ rowId+ "  "+ dataIndex+ "  "+ data.plantCode);
							// If row ID is in the list of selected row IDs
							/* if ($.inArray({"samtdRecid":data.samtdRecid,"masterIdCode":data.masterIdCode},
							rows_selected) !== -1) { */
							//console.log(" draw modal ++++++++ rows_selected "+JSON.stringify(rows_selected));		
							if(rows_selected.filter( e => (e.plantCode === data.plantCode)).length > 0){		
								$(row).find('input[type="checkbox"]').prop('checked',true);
								$(row).addClass('selected');
							}
						}
					});
					// allPages = planttable.cells( ).nodes( );
					$('#plantmodel').modal('show');
					//$("#plantmodel").css("z-index", "1200");
					//$('#myModal').appendTo("body").modal('show');

				}
				;
				$('#plantmodel').on('shown.bs.modal', function() {
					$("div.apptoolbar").html($('#apptoolbar').html());
					//$('#myInput').trigger('focus');
				});
				
				$('#plantmodel').on('click', '#assignplant',
				function(e) {
					$('#plants').val();							
					var lplants = [];		
					var tempPlant = selectedPlantDataTables.rows().data();
					console.log(tempPlant);	
					  
					console.log(isEmpty(tempPlant));
					$.each( tempPlant, function( key, value ) {
						var index = rows_selected.findIndex(row => (row.plantCode===value.plant));
						 if ( index !== -1) {
							rows_selected.splice(index, 1);
						}
			          }); 
					
					
					rows_selected.forEach(function(rowData) {															
						lplants.push(rowData.plantCode);								
					}); 
					$('#plants').val(lplants);							
					console.log(lplants);	
					createTcodeRequest();
					$('#plantmodel').modal('hide');
					
				});
				$('#plantmodel').on('hidden.bs.modal',function() {					
					$('#planttable  thead input[name="select_all"]:checked')
					.trigger('click');					
					planttable.clear().destroy();
					$('#planttable').dataTable()
					.fnDestroy();
					
				});
				
				$('#planttable thead input[name="select_all"]').on('click',function(e) {
					if (this.checked) {
						$(
						'#planttable tbody input[type="checkbox"]:not(:checked)')
						.trigger('click');
						} else {
						$(
						'#planttable tbody input[type="checkbox"]:checked')
						.trigger('click');
					}
					
					// Prevent click event from propagating to parent
					e.stopPropagation();
				});
				$('#planttable').on('click','tbody input[type="checkbox"]',	function(e) {
					var $row = $(this).closest('tr');
					
					// Get row data
					var data = planttable.row($row).data();
					
					// Get row ID
					var plantCode = data.plantCode;	
					
					var index = rows_selected.findIndex(row => (row.plantCode===plantCode));
					
					
					if (this.checked && index === -1) {
						rows_selected.push({"plantCode":plantCode});								
						} else if (!this.checked && index !== -1) {
						rows_selected.splice(index, 1);
						
					}
					
					if (this.checked) {
						$row.addClass('selected');
						} else {
						$row.removeClass('selected');
					}
					
					// Update state of "Select all" control
					updateDataTableSelectAllCtrl(planttable);
					
					// Prevent click event from propagating to parent
					e.stopPropagation();
				});
				
				$('#planttable').on('draw', function() {
					// Update state of "Select all" control
					//console.log('mastertrole Change');
					updateDataTableSelectAllCtrl(planttable);
				});
				$('#planttable').on('draw.dt', function() {
					//console.log('mastertrole Change');
					updateDataTableSelectAllCtrl(planttable);					
				});
				
				 $('#bPurGrp').click(function() {openPurchasingModel();});
			var purchasingTable = null;
			//var allPages =null;.
			function openPurchasingModel() {
			rows_selected = [];
				purchasingTable = $("#purchasingTable").DataTable({
									//stateSave: true,
									select : {
										'style' : 'multi'
									},												
									"stripeClasses" : [ 'odd-row',
											'even-row' ],
									"dom" : 'l<"toolbar">frtip',	
									"pageLength": 100,
									"lengthMenu": [100, 50, 25,10],
									"responsive" : true,
									"bProcessing" : true,
									"bServerSide" : true,												
									"iDisplayLength" : 10,												
									"iDisplayStart" : 0,
									"sAjaxSource" : "getPGroupJson",
									"aaSorting" : [],
					"columnDefs" : [
					{
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
							return '<input type="checkbox" name="id[]" value="'
							+ $('<div/>')
							.text(
							data)
							.html()
							+ '">';
						}
						
					},
					
					{
						"name" : "masterIdCode",
						"targets" : 1,
						"defaultContent" : "",
						"searchable" : false,
						"orderable" : false,
						"className" : 'dt-body-center'
					},
					{
						"name" : "description",
						"targets" : 2,
						"searchable" : false,
						"orderable" : false,
						"defaultContent" : "",
						"className" : 'dt-body-center'
					}],
					"aoColumns" : [ {
						"mData" : "samtdRecid"
						}, {
						"mData" : "masterIdCode"
						}, {
						"mData" : "description"
						}],
					"rowCallback" : function(row,
					data, dataIndex) {
						// Get row ID
						var rowId = data.samtdRecid;
						console
						.log("row "
						+ JSON
						.stringify(data)
						+ " "
						+ rowId
						+ "  "
						+ dataIndex
						+ "  "
						+ data.samtdRecid);
								
						if(rows_selected.filter( e => (e.samtdRecid === data.samtdRecid) && (e.masterIdCode === data.masterIdCode )).length > 0){		
							$(row)
							.find(
							'input[type="checkbox"]')
							.prop(
							'checked',
							true);
							$(row).addClass(
							'selected');
						}
					}
				});				
				$('#purchasingModal').modal('show');
			};
			
			$('#purchasingModal').on('shown.bs.modal', function() {
				$("div.toolbar").html($('#pgtoolbar').html());
				//$('#myInput').trigger('focus');
			});

		
		
			$('#purchasingModal').on('click', '#assignpggroup',
			function(e) {
				$('#pgroups').val();
				
				var lgroups = [];
				var tempPGroup = selectedPlantDataTables.rows().data();
				console.log(tempPGroup);	
				  
				console.log(isEmpty(tempPGroup));
				$.each( tempPGroup, function( key, value ) {
					var index = rows_selected.findIndex(row => (row.masterIdCode===value.purchaseGroup));
					 if ( index !== -1) {
						rows_selected.splice(index, 1);
					}
		          }); 
				
				rows_selected.forEach(function(rowData) {															
					lgroups.push(rowData.masterIdCode);								
				}); 
				$('#pgroups').val(lgroups);
			
				console.log(lgroups);
				createTcodeRequest();
				$('#purchasingModal').modal('hide');
			});
			$('#purchasingModal')
			.on(
			'hidden.bs.modal',
			function() {
				
				$('#purchasingTable  thead input[name="select_all"]:checked')
				.trigger('click');
				
				purchasingTable.clear().destroy();
				$('#purchasingTable').dataTable()
				.fnDestroy();
				
			});
			
			$('#purchasingTable thead input[name="select_all"]')
			.on(
			'click',
			function(e) {
				if (this.checked) {
					$(
					'#purchasingTable tbody input[type="checkbox"]:not(:checked)')
					.trigger('click');
					} else {
					$(
					'#purchasingTable tbody input[type="checkbox"]:checked')
					.trigger('click');
				}
				
				// Prevent click event from propagating to parent
				e.stopPropagation();
			});
			$('#purchasingTable').on(
			'click',
			'tbody input[type="checkbox"]',
			function(e) {
				var $row = $(this).closest('tr');
				
				// Get row data
				var data = purchasingTable.row($row).data();
				
				// Get row ID
				var masterIdCode = data.masterIdCode;
				var samtdRecid = data.samtdRecid;
				
				var index = rows_selected.findIndex(row => (row.samtdRecid===samtdRecid) && (row.masterIdCode===masterIdCode));
				
				
				if (this.checked && index === -1) {
					rows_selected.push({"samtdRecid":samtdRecid,"masterIdCode":masterIdCode});
					
					} else if (!this.checked && index !== -1) {
					rows_selected.splice(index, 1);
					
				}
				
				if (this.checked) {
					$row.addClass('selected');
					} else {
					$row.removeClass('selected');
				}
				
				// Update state of "Select all" control
				updateDataTableSelectAllCtrl(purchasingTable);
				
				// Prevent click event from propagating to parent
				e.stopPropagation();
			});
			
			$('#purchasingTable').on('draw', function() {
				// Update state of "Select all" control
				//console.log('mastertrole Change');
				updateDataTableSelectAllCtrl(purchasingTable);
			});
			$('#purchasingTable').on('draw.dt', function() {
				//console.log('mastertrole Change');
				updateDataTableSelectAllCtrl(purchasingTable);
				
			});		
			
			
			 $('#btcodes').click(function() {
				 
				 var module = isEmpty($("#modulelist option:selected").val())?"false":$("#modulelist option:selected").val();
				// alert(module);
				 openTcodetableModel(module);      
				 
			 });
			
			var tcodeTable = null;
			
			function openTcodetableModel(module) { 
				rows_selected=[];
				tcodeTable = $("#tcodeTable").DataTable({
                select : {
					'style' : 'multi'
				},
				
				"stripeClasses" : [ 'odd-row', 'even-row' ],
				"dom" : 'l<"tocdetoolbar">frtip',
				//"dom" : 'l<"toolbar">frtip',
				/* dom: "<'row'<'col-sm-12'tr>>" +
				"<'row'<'col-sm-4'l><'col-sm-8'p>>", */
				//bFilter : false,
				"pageLength": 100,
				"lengthMenu": [100, 50, 25,10],
				"responsive" : true,
				"bProcessing" : true,
				"bServerSide" : true,
				//Default: Page display length
				"iDisplayLength" : 10,
				//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
				"iDisplayStart" : 0,
				"autoWidth": false,
				"sAjaxSource" : "getTcodeByModule/"+module,
				//"aaSorting" : [ [ 0, "asc" ] ],	
				"aaSorting" : [],
				"columnDefs" : [ {
					"orderable" : false,
					"targets" : [ 0 ]
					},{
						'targets' : 0,
						"searchable" : false,
						"orderable" : false,
						"className" : 'dt-body-center',
						"render" : function(
						data, type,
						full, meta) {
							return '<input type="checkbox" name="id[]" value="'+ $('<div/>').text(data).html()+ '">';
						}
						
					},{
					"name" : "tcode",
					"targets" : 1,
					"defaultContent" : "",
					"searchable" : false,
					"orderable" : false,
					"className" : 'dt-body-center'
					},{
						"name" : "stdroles",
						"targets" : 2,
						"searchable" : false,
						"orderable" : false,
						"defaultContent" : "",
						"className" : 'dt-body-center',
						"render" : function(data, type, full, meta){
				               return getStatusTextHeader(data);
				            }
					},
					
					{
					"name" : "tcodeDesc",
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
				},{
					"name" : "module",
					"targets" : 5,
					"searchable" : false,
					"orderable" : false,
					"defaultContent" : "",
					"className" : 'dt-body-center'
				} ],
				"aoColumns" : [ {
					"mData" : "tcode"
				    },{
					"mData" : "tcode"
					}
					,{
					 "mData" : "stdroles"
					},{
					"mData" : "tcodeDesc"
					},
					{
						"mData" : "sensitive"
					},
					{
					"mData" : "module"
				} ],
				"rowCallback" : function(row,
						data, dataIndex) {
							// Get row ID
							var tcode = data.tcode;
							console.log("row "		+ JSON.stringify(data)+ " "+ tcode	);									
							if(rows_selected.filter( e => (e.tcode === data.tcode) && (e.satId === data.tcodeId)).length > 0){
								$(row).find('input[type="checkbox"]').prop('checked',true);							
								$(row).addClass('selected');
							}
						}
			});
			$('#showttcodemodal').modal('show');
			};
			$('#showttcodemodal').on('shown.bs.modal', function() {
				$("div.tocdetoolbar").html($('#tocdetoolbar').html());
			});
			$('#showttcodemodal').on('click', '#btnAssignTcode',
					function(e) {
						$('#tcodesText').val();						
						var tcodes = [];
						var tcodeIds = [];
						var tempTcode = tcodeDataTables.rows().data();
						console.log(tempTcode);	
						  
						console.log(isEmpty(tempTcode));
						$.each( tempTcode, function( key, value ) {
							var index = rows_selected.findIndex(row => (row.tcode===value.tcode && row.tcodeId===value.tcodeId));
							 if ( index !== -1) {
								rows_selected.splice(index, 1);
							}
				            }); 
						rows_selected.forEach(function(rowData) {							
								tcodes.push(rowData.tcode);	
								tcodeIds.push(rowData.tcodeId);								
							
						}); 
						$('#tcodesText').val(tcodes);					
						$('#tcodeId').val(tcodeIds);					
						console.log(tcodes);
						createTcodeRequest();
						$('#showttcodemodal').modal('hide');
						
			});
		
			function getStatusTextHeader(status){
				
				switch(status) {
				  case 'CRE':
					  return "Create"; 
				   
				  case 'DISP':
					  return "Display"; 
				}
				 
			};
			
			function getSensitiveHeader(status){
				
				if(status==='Y'){
					return "Yes";
				}else
					return "No";
				 
			};
			$('#showttcodemodal').on('hidden.bs.modal',function() {
				$('#tcodeTable  thead input[name="select_all"]:checked')
				.trigger('click');
				tcodeTable.clear().destroy();
				$('#tcodeTable').dataTable().fnDestroy();
				
			});
			
			$('#tcodeTable thead input[name="select_all"]')
			.on(
			'click',
			function(e) {
				if (this.checked) {
					$(
					'#tcodeTable tbody input[type="checkbox"]:not(:checked)')
					.trigger('click');
					} else {
					$(
					'#tcodeTable tbody input[type="checkbox"]:checked')
					.trigger('click');
				}
				
				// Prevent click event from propagating to parent
				e.stopPropagation();
			});
			$('#tcodeTable').on(
			'click',
			'tbody input[type="checkbox"]',
			function(e) {
				var $row = $(this).closest('tr');
				
				// Get row data
				var data = tcodeTable.row($row).data();
				
				// Get row ID
				var tcode = data.tcode;
				var tcodeId = data.satId;
				var index = rows_selected.findIndex(row => (row.tcode===tcode && row.satId===tcodeId));
				if (this.checked && index === -1) {
					rows_selected.push({"tcode":tcode,"tcodeId":tcodeId});
					
					} else if (!this.checked && index !== -1) {
					rows_selected.splice(index, 1);
				}
				
				if (this.checked) {
					$row.addClass('selected');
					} else {
					$row.removeClass('selected');
				}
				
				// Update state of "Select all" control
				updateDataTableSelectAllCtrl(tcodeTable);
				
				// Prevent click event from propagating to parent
				e.stopPropagation();
			});
			
			$('#tcodeTable').on('draw', function() {
				// Update state of "Select all" control
				//console.log('mastertrole Change');
				updateDataTableSelectAllCtrl(tcodeTable);
			});
			$('#tcodeTable').on('draw.dt', function() {
				//console.log('mastertrole Change');
				updateDataTableSelectAllCtrl(tcodeTable);
				
			});	
				
		$('input[type=radio][name=fortime]').change(function() {
			//$("#forDuration").val("allTime");
			if (this.value == "temporary") {

				// $(".vplant").hide();
				
				$(".fortime").show();

			} else if (this.value == "allTime") {

				$(".fortime").hide();
				//  $(".vpgroup").show();	

			}
		    //$("#forDuration").val(this.value);
		});
		
		var assignrolestable=null;
		function getRoles(){			
		 rows_selected=[];
		 assignrolestable = $("#assignrolestable")
		.DataTable(
				{

					select : {
						style : 'single'
					},

					"stripeClasses" : [ 'odd-row',
							'even-row' ],
					"dom" : 'lf<"roletoolbar">rtip',
					"pageLength": 100,
					"lengthMenu": [100, 50, 25,10],
					"responsive" : true,
					"bProcessing" : true,
					"bServerSide" : true,
					//Default: Page display length
					"iDisplayLength" : 10,
					//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
					"iDisplayStart" : 0,
					"sAjaxSource" : "getRoles",
					"aaSorting" : [ [ 1, "asc" ] ],
					"columnDefs" : [

							{
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
									return '<input type="checkbox" name="id[]" value="'
											+ $(
													'<div/>')
													.text(
															data)
													.html()
											+ '">';
								}

							},
							{
	                           "name" : "funId",
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
								"name" : "roleName",
								"targets" : 3,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},
							{
								"name" : "plant",
								"targets" : 4,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},
							{
								"name" : "module",
								"targets" : 5,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							},
							{
								"name" : "entity",
								"targets" : 6,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center'
							} /*,{
								"name" : "sensitive",
								"targets" : 7,
								"searchable" : false,
								"orderable" : false,
								"defaultContent" : "",
								"className" : 'dt-body-center',
								"render" : function(data, type, full, meta){
						               return getSensitiveHeader(data);
						            }
							}*/
							],
					"aoColumns" : [ {
						"mData" : "funId"
					}, {
						"mData" : "roleId"
					},{
						"mData" : "roleCode"
					}, {
						"mData" : "roleName"
					}, {
						"mData" : "plant"
					}, {
						"mData" : "module"
					}, {
						"mData" : "entity"
					}/*, {
						"mData" : "sensitive"
					}*/],
					"rowCallback" : function(row,
							data, dataIndex) {
						// Get row ID												

						if(rows_selected.filter( e => (e.roleId === data.roleId) && (e.roleCode === data.roleCode )).length > 0){	
							$(row)
									.find(
											'input[type="checkbox"]')
									.prop(
											'checked',
											true);
							$(row).addClass(
									'selected');
						}
					}
				});
		  $('#showrolemodal').modal('show');
         };
         
        
		
		$('#showrolemodal').on('shown.bs.modal', function() {
			$("div.roletoolbar").html($('#roletoolbars').html());
		});
		$('#showrolemodal').on('click', '#btnAssignRoles',
				function(e) {
					$('#derivedRolesText').val();						
					$('#roleId').val();						
					var roleCodes = [];
					var roleIds = [];
					
					var tempTcode = rolesDataTables.rows().data();
					console.log(tempTcode);	
					  
					console.log(isEmpty(tempTcode));
					$.each( tempTcode, function( key, value ) {
						var index = rows_selected.findIndex(row => (row.roleId===value.roleId && row.roleCode===value.roleCode));
						 if ( index !== -1) {
							rows_selected.splice(index, 1);
						}
			            }); 
					
					rows_selected.forEach(function(rowData) {	
						console.log(rowData.roleCode+"     "+rowData.roleId);		
					
						roleCodes.push(rowData.roleCode);								
						roleIds.push(rowData.roleId);								
					}); 
					$('#derivedRolesText').val(roleCodes);						
					$('#roleId').val(roleIds);					
					console.log(roleCodes);	
					addRoles();
					$('#showrolemodal').modal('hide');
		});
	
		$('#showrolemodal').on('hidden.bs.modal',function() {
			$('#assignrolestable  thead input[name="select_all"]:checked')
			.trigger('click');
			assignrolestable.clear().destroy();
			$('#assignrolestable').dataTable()
			.fnDestroy();			
		});
		
		$('#assignrolestable thead input[name="select_all"]')
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
			
			// Prevent click event from propagating to parent
			e.stopPropagation();
		});
		$('#assignrolestable').on('click','tbody input[type="checkbox"]',function(e) {
			var $row = $(this).closest('tr');
			
			// Get row data
			var data = assignrolestable.row($row).data();
			
			// Get row ID
			var roleCode = data.roleCode;
			var roleId = data.roleId;
			var index = rows_selected.findIndex(row => ((row.roleId === data.roleId) && (row.roleCode === data.roleCode )));
			if (this.checked && index === -1) {
				rows_selected.push({"roleCode":roleCode,"roleId":roleId});
				
				} else if (!this.checked && index !== -1) {
				rows_selected.splice(index, 1);
			}
			
			if (this.checked) {
				$row.addClass('selected');
				} else {
				$row.removeClass('selected');
			}
			
			// Update state of "Select all" control
			updateDataTableSelectAllCtrl(assignrolestable);
			
			// Prevent click event from propagating to parent
			e.stopPropagation();
		});
		
		$('#assignrolestable').on('draw', function() {
			// Update state of "Select all" control
			//console.log('mastertrole Change');			
			updateDataTableSelectAllCtrl(assignrolestable);
		});
		$('#assignrolestable').on('draw.dt', function() {
			//console.log('mastertrole Change');			
			updateDataTableSelectAllCtrl(assignrolestable);			
		});	
         
         $('#btnDerivedRoles').click(function() {
        	 //alert("Get Roles");         
        	 getRoles();
        });	
         
         $('#addTcodeReqBtn')
			.click(
					function() {
					
						createTcodeRequest();
		});
         
         function createTcodeRequest(){
         console.log(JSON.stringify(getFormData($("#requestFormTocde"))));
			resetErrors();
			$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
			$
					.ajax({
						contentType : 'application/json; charset=utf-8',
						type : 'POST',
						url : 'createTcodeRequest',
						data : JSON
								.stringify(getFormData($("#requestFormTocde"))),
						dataType : 'json',
						async : false,
						success : function(data) {
							
							$('#requestFormTocde')[0].reset();
							
							$
							.ajax({
								type : 'GET',
								url : "<c:url value="/listHeaderData/T"/>",
								dataType : "json",
								async : false,
								success : handleData,
								error : function(xhRequest, ErrorText,
										thrownError) {

								}
							});
							console
									.log(JSON
											.stringify(data));
							
							
							tcodeDataTables.ajax.url("getRequestedByUserID").load();
							 if(!isEmpty($("#requestId").val())  && !isEmpty($("#surRecid").val()))
					    		   selectedPlantDataTables.ajax.url("getRequestedPlant/"+$("#surRecid").val()+"/"+$("#requestId").val()).load();
							 $('#pageblock').modal('hide');   
							 swal("Success!",
										data.ok,
										"success");
											$("#tcodeId").val("");
											$("#pgroups").val("");
											$("#plants").val("");
											$("#tcodesText").val("");
						
						
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
				function	saveStatus(data){	
						resetErrors();
						$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
						$
								.ajax({
									contentType : 'application/json; charset=utf-8',
									type : 'POST',
									url : 'submitRequest',
									data : JSON
							          .stringify(data),
									dataType : 'json',
									async : false,
									success : function(data, textStatus, xhr) {
										console
												.log(JSON
														.stringify(data));
                                      
									
										$("#requestId").val("");
										$("#surRecid").val("");
										$("#sapCompanyCodes").removeAttr("disabled"); 
										$("#modulelist").removeAttr("disabled"); 
										$("#sapCompanyCode").val("");
										$("#sapModuleCode").val("");
										$('#requestFormTocde')[0].reset();
										tcodeDataTables.ajax.url("getRequestedByUserID").load();
										//if(isEmpty($("#requestId").val())  && isEmpty($("#surRecid").val()))
								         selectedPlantDataTables.ajax.url("getRequestedPlant/0/0").load();
								     	$.ajax({
											type : 'GET',
											url : "<c:url value="/listHeaderData/T"/>",
											dataType : "json",
											async : false,
											success : handleData,
											error : function(xhRequest, ErrorText,
													thrownError) {

											}
										});
								         $('#pageblock').modal('hide');
								     	swal("Success!",
												data.ok,
												"success");
								        /* if(xhr.status==200){}else if(xhr.status==202){
										swal("Success!",
												data.ok,
												"success");
                                      }*/
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
											swal.close()
										}  //if(xhr.status==200){}
										else if(xhRequest.status==404){
										swal("Request not Found!",
											JSON.parse(xhRequest.responseText).ok,
										"error");
                                       }	
										
										else{									
										swal({
											title : "Error",
											text : JSON.parse(xhRequest.responseText).error,
											type : "error",
											showCancelButton : false,
											confirmButtonClass : 'btn btn-theme04',
											confirmButtonText : 'Error!'
										});}
									}
								});
						return false;
					};
            $('#submitTcodeReqBtn')
						.click(
								function() {	
									
				//$("#requestId").val(7420231);
			    //$("#surRecid").val(17);
			    if(!isTcodeAdded){
			    	swal("T-code not addedd ",
					"Add t-code Then submit");
			    	return;
			    }
                 if(!isPlantAdded){
                	 swal("Plant not addedd ",
 					"Add Plant or Purchase group Then submit");
 			    	return;
                	
			    }
                 
				if(!isEmpty($("#requestId").val())){
				 swal({
                     title: "Are you sure?",
                     text: "Once submited ,request can't be modified !",
                     type: "warning",
                     showCancelButton: true,
                     confirmButtonClass: "'btn btn-success'",
                     confirmButtonText: "Yes, Submit it!",
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
                	   //alert(isConfirm);	
                	   //$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
                	   var temp=getFormData($("#requestFormTocde"));
                console
				.log(JSON
						.stringify(temp));
                var sdata=	{};//"fortime":"allTime","requestId":"21420231","fromDate":"","toDate":""
       		    sdata.status="S";   
				
				sdata.requestId= $("#requestId").val();
				sdata.surRecid=  $("#surRecid").val();
				sdata.fortime= temp.fortime;
				sdata.fromDate= temp.fortime=='allTime'?null:temp.fromDate;
				sdata.toDate=  temp.fortime=='allTime'?null:temp.toDate;
				sdata.reason=  temp.reason;
				
			    //	delete	sdata.createdOn;
				//  delete	sdata.requestDate;
				console.log(JSON.stringify(sdata));
				saveStatus(sdata);
                   }
                });
                   
              }    
				else{						
						swal("Request not cteated ",
						"Create Request then submit.");
				}
			});
     	
         function jsonConcat(o1, o2) {
			 for (var key in o2) {
			  o1[key] = o2[key];
			 }
			 return o1;
		};
		
		function addRoles(){
			rows_selected=[];
			$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
			console.log(JSON.stringify(getFormData($("#requestFormRoleFrom"))));
			resetErrors();
			$
					.ajax({
						contentType : 'application/json; charset=utf-8',
						type : 'POST',
						url : 'createRoleRequest',
						data : JSON
								.stringify(getFormData($("#requestFormRoleFrom"))),
						dataType : 'json',
						async : false,
						success : function(data) {
							console
									.log(JSON
											.stringify(data));
							$('#requestFormRoleFrom')[0].reset();
							console
									.log(JSON
											.stringify(data));
							$.ajax({
								type : 'GET',
								url : "<c:url value="/listHeaderData/R"/>",
								dataType : "json",
								async : false,
								success : handleDataRole,
								error : function(xhRequest, ErrorText,
										thrownError) {

								}
							});
							rolesDataTables.ajax.url("getRequestedRolesUserID").load();
							/*$("#tcodeId").val("");
							$("#pgroups").val("");
							$("#plants").val("");
							$("#tcodesText").val("");*/
							
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
							//	mroletable.ajax.url("masterroledata").load();
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
			return false;
		}
         $('#addRoleBtn')
			.click(
					function() {
						
					});   
         $('#requestRefEmpBtn')
			.click(
					function() {	
						
						console.log(JSON.stringify($("#users").immybox('getValue')[0]));			
						
						if(isEmpty($("#users").immybox('getValue')[0])){								
							swal("Select user",
							"Select user .");
							return;
						   }
						var result = userdata.filter(obj => {
							  return obj.sapUserid === $("#users").immybox('getValue')[0];
							})
						//	console.log(JSON.stringify(result));
						var temp=result[0];//JSON.parse(result)
						
						console.log(temp.hrisCode);
						console.log(temp.sapUserid);
				
						 $("#refEmpCode").val(temp.hrisCode);
						 $("#refSapUserId").val(temp.sapUserid);
						 $("#hreason").val($("#reasonEmp").val());
						//return;
						$('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
						console.log(JSON.stringify(getFormData($("#requestEmployeeRefrncesFrom"))));
						resetErrors();
						$
								.ajax({
									contentType : 'application/json; charset=utf-8',
									type : 'POST',
									url : 'createEmpRefRequest',
									data : JSON
											.stringify(getFormData($("#requestEmployeeRefrncesFrom"))),
									dataType : 'json',
									async : false,
									success : function(data) {
										console
												.log(JSON
														.stringify(data));
										 $('#pageblock').modal('hide');
										swal("Success!",
												data.ok,
												"success");
										//mroletable.ajax.url("tcodeDataTables").load();
										 $("#refEmpCode").val("");
										 $("#refSapUserId").val("");
										 $("#hreason").val("");
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
											//mroletable.ajax.url("masterroledata").load();
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
						return false;
					});
        var isTcodeAdded=false;
      
     	var tcodeDataTables	=null;
		tcodeDataTables = $("#tcodeDataTables").DataTable({
			//stateSave: true,
			select : {
				'style' : 'single'
			},
			
			"stripeClasses" : [ 'odd-row', 'even-row' ],
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
			"sAjaxSource" : "getRequestedByUserID",
			"aaSorting" : [ [ 0, "asc" ] ],	
			"aaSorting" : [],
			"columnDefs" : [ {
				"orderable" : false,
				"targets" : [ 0 ]
				}, 	
				{
				"name" : "surtRecid",
				"targets" : 0,
				"defaultContent" : "",
				"searchable" : false,
				"orderable" : false,
				"className" : 'dt_col_hide'
				
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
			},{
				"name" : "status",
				"targets" : 4,
				"searchable" : false,
				"orderable" : false,
				"defaultContent" : "",
				"className" : 'dt-body-center'
			},{
				"name" : "surRecid",
				"targets" : 5,
				"searchable" : false,
				"orderable" : false,				
				"className" : 'dt-body-center',
				"render" : function( data, type, row, meta ){
					
					return '<button id="delete" type="button" class="btn btn-danger" style="padding: 1px"><i class="fa fa-trash "></i> Delete </button>';
							
				}
			} ],
			"aoColumns" : [  {
				"mData" : "surtRecid"
				},{
				"mData" : "tcode"
				}, {
				"mData" : "tcodeDesc"
				},{
				"mData" : "module"
				}, {
				"mData" : "status"
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
				
				
				//$("#sapCompanyCodes").attr("disabled", true); 
			         
				
				//"module":"CO","sapCompanyCode":"2000"
			},	"initComplete": function (settings, json) {
				console.log(JSON.stringify(json));
			
                if(this.api().data().length>0){
                	 isTcodeAdded=true;                	 
                	 $("#modulelist").attr("disabled", true);  
                	 $("#modulelist").val(json.aaData[0].module);
                	 $("#sapCompanyCode").val(json.aaData[0].sapCompanyCode);
     				 $("#sapModuleCode").val(json.aaData[0].module);
     				 $("#requestId").val(json.aaData[0].requestId);
    				 $("#surRecid").val(json.aaData[0].surRecid);
    				 $("#sapCompanyCodes").val(json.aaData[0].sapCompanyCode);
    				 if(!isEmpty(json.aaData[0].reason))
    				 $("#reason").val(json.aaData[0].reason);   
			       } else{
						//$("#fillpgd").removeAttrttr("disabled");	
						 isTcodeAdded=false;   
						$("#modulelist").removeAttr("disabled");
					}                                               
              },
              "drawCallback": function( settings ) {								               
            	  if(this.api().data().length>0){
                 	 isTcodeAdded=true;
                 	  var api = this.api();
                      // Output the data for the visible rows to the browser's console
                     // console.log(" drawCallback"+ JSON.stringify(api.rows( {page:'current'} ).data()) );
                      if(!isEmpty(api.rows( {page:'current'} ).data()[0].reason))
                      $("#reason").val(api.rows( {page:'current'} ).data()[0].reason);   
                	 $("#modulelist").attr("disabled", true); 
             
 			       } else{
 						//$("#fillpgd").removeAttrttr("disabled");	
 						 isTcodeAdded=false;   
 						$("#modulelist").removeAttr("disabled");
 					}  
              }
		});
		
		var selectedTcodeData = null;
		$('#tcodeDataTables').on('draw.dt', function() {
			console.log('tcodeDataTables Change');	
			/*if(!isEmpty($("#requestId").val()))
			selectedPlantDataTables.ajax.url("getRequestedPlant/"+$("#surRecid").val()+"/"+$("#requestId").val()).load();
			else{
				selectedPlantDataTables.ajax.url("getRequestedPlant/0/0").load(); 
			}*/
			//var psurRecid =$("#surRecid").val();
			 selectedTcodeData = null;
		});
		
		
		$('#tcodeDataTables tbody').on(
				'click',
				'tr',
				function() {				
					selectedTcodeData = null;
					if ($(this).hasClass('selected')) {						
						$(this).removeClass('selected');
						if ($(this).hasClass('odd-row')) {
																	
							$(this).find('td').css(
									'background-color',
									'##A6DFC1');
						}
					} else {
						tcodeDataTables.$('tr.selected')
								.removeClass('selected');
					
						tcodeDataTables.$('tr.odd-row').each(
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
						selectedTcodeData  = tcodeDataTables.row(this).data();						
						$(this).addClass('selected');
						//getPlantForSelectedTocde(selectedTcodeData);
					}

				});
		var isPlantAdded=false;
		/*function getPlantForSelectedTocde(selectedTcodeData){
			if(!isEmpty(selectedTcodeData)){
				selectedPlantDataTables.ajax.url("getRequestedPlant/"+selectedTcodeData.surRecid+"/"+selectedTcodeData.requestId).load();
			}
		};*/
		
		var selectedPlantDataTables = $("#selectedPlantDataTables").DataTable({
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
			"iDisplayLength" : 100,
			//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
			"iDisplayStart" : 0,
			"sAjaxSource" : "getRequestedPlant/0/0",
			//"aaSorting" : [ [ 0, "asc" ] ],	
			"aaSorting" : [],
			"columnDefs" : [ {
				"orderable" : false,
				"targets" : [ 0 ]
				},{
					"name" : "surpRecid",
					'targets' : 0,
					"searchable" : false,
					"orderable" : false,
					"className" : 'dt-body-center',
				/*	"render" : function(
					data, type,
					full, meta) {
						return '<input type="checkbox" name="id[]" value="'+ $('<div/>').text(data).html()+ '">';
					}*/
					
				} ,{
				"name" : "plant",
				"targets" : 1,
				"searchable" : false,
				"orderable" : false,
				"defaultContent" : "",
				"className" : 'dt-body-center'
			},{
				"name" : "purchaseGroup",
				"targets" : 2,
				"searchable" : false,
				"orderable" : false,
				"defaultContent" : "",
				"className" : 'dt-body-center'
			},{
				"name" : "surRecid",
				"targets" : 3,
				"searchable" : false,
				"orderable" : false,				
				"className" : 'dt-body-center',
				"render" : function( data, type, row, meta ){
					
					return '<button id="delete" type="button" class="btn btn-danger" style="padding: 1px"><i class="fa fa-trash "></i> Delete </button>';
							
				}
			} ],
			"aoColumns" : [ {
				"mData" : "surpRecid"
			    },{
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
			}	,
						"initComplete": function (settings, json) {//here is the tricky part 
							
		                  /*  var count = $('#qualificationDatatableMA tr').length-1;
		                   console.log("this.api().data().length MA "+this.api().data().length);
		                   console.log("$('#qualificationDatatableMA tr').length-1 MA "+count); */
                          if(this.api().data().length>0){
                        	  isPlantAdded=true;
					       } else{
								//$("#fillpgd").removeAttrttr("disabled");	
								isPlantAdded=false;
					    	   $("#sapCompanyCodes").removeAttr("disabled"); 
							}                                               
		                },
		                "drawCallback": function( settings ) {								               
		                	if(this.api().data().length>0){
	                        	  isPlantAdded=true;
						       } else{
									//$("#fillpgd").removeAttrttr("disabled");	
									isPlantAdded=false;
						    	   $("#sapCompanyCodes").removeAttr("disabled"); 
								}   
		                }			
		});
						    	   (function ($) {
						    		    //Here jQuery is $
						    		    if(!isEmpty($("#requestId").val())  && !isEmpty($("#surRecid").val()))
						    		   selectedPlantDataTables.ajax.url("getRequestedPlant/"+$("#surRecid").val()+"/"+$("#requestId").val()).load();

						    		})(jQuery);
		
		$('#tcodeDataTables  tbody')
		.on('click','button[type="button"]',
		function(e) {
			var $row = $(this).closest('tr');
			
			// Get row data
			var data = tcodeDataTables.row($row)
			.data();
			console.log(JSON.stringify(data));
			
			swal({
                title: "Are you sure?",
                text: "Once delete , data will not recover!",
                type: "warning",
                showCancelButton: true,
                confirmButtonClass: "'btn btn-danger'",
                confirmButtonText: "Yes, Delete it!",
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
  		    sdata.status="D";
			sdata.requestId= data.requestId;
			sdata.surtRecid=  data.surtRecid;
		    //	delete	sdata.createdOn;
			//  delete	sdata.requestDate;
			console.log(JSON.stringify(sdata));
			//return;
			deleteDecodes(sdata);
              }
           });
		});
		
		$('#selectedPlantDataTables  tbody')
		.on('click','button[type="button"]',
		function(e) {
			var $row = $(this).closest('tr');
			// Get row data
			var data = selectedPlantDataTables.row($row)
			.data();
			console.log(JSON.stringify(data));
			swal({
                title: "Are you sure?",
                text: "Once delete , data will not recover!",
                type: "warning",
                showCancelButton: true,
                confirmButtonClass: "'btn btn-danger'",
                confirmButtonText: "Yes, Delete it!",
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
  		    sdata.status="D";			
			sdata.requestId= data.requestId;
			sdata.surpRecid= data.surpRecid ;
		    //	delete	sdata.createdOn;
			//  delete	sdata.requestDate;
			console.log(JSON.stringify(sdata));
			//return;
			deletePlantsPurchegroup(sdata);
              }
           });
		});
		function	deleteDecodes(data){	
			resetErrors();
			$
					.ajax({
						contentType : 'application/json; charset=utf-8',
						type : 'POST',
						url : 'deleteTcode',
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
							//$("#requestId").val("");
							//$("#surRecid").val("");
							tcodeDataTables.ajax.url("getRequestedByUserID").load();

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
		function deletePlantsPurchegroup(data){	
			resetErrors();
			$
					.ajax({
						contentType : 'application/json; charset=utf-8',
						type : 'POST',
						url : 'deletePlantsPurchegroup',
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
							//$("#requestId").val("");
							//$("#surRecid").val("");
							if(!isEmpty($("#requestId").val())  && !isEmpty($("#surRecid").val()))
					    		   selectedPlantDataTables.ajax.url("getRequestedPlant/"+$("#surRecid").val()+"/"+$("#requestId").val()).load();

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
		//$("#selectedPlantDataTables").DataTable({bFilter: false, bInfo: false});
		
		var isRoleAdded=false;;
		var rolesDataTables	=null;
		rolesDataTables = $("#rolesDataTables").DataTable({
			//stateSave: true,
			select : {
				'style' : 'single'
			},
			
			"stripeClasses" : [ 'odd-row', 'even-row' ],
			"pageLength": 100,
			"lengthMenu": [100, 50, 25,10],
			"responsive" : true,
			"bProcessing" : true,
			"bServerSide" : true,
			//Default: Page display length
			"iDisplayLength" : 10,
			//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
			"iDisplayStart" : 0,
			"sAjaxSource" : "getRequestedRolesUserID",
			"aaSorting" : [ [ 0, "asc" ] ],	
			"aaSorting" : [],
			"columnDefs" : [ {
				"orderable" : false,
				"targets" : [ 0 ]
				},
			{
				"name" : "roleId",
				"targets" : 0,
				"defaultContent" : "",
				"searchable" : false,
				"orderable" : false,
				"className" : 'dt-body-center'
				},
			{
				"name" : "roleCode",
				"targets" : 1,
				"defaultContent" : "",
				"searchable" : false,
				"orderable" : false,
				"className" : 'dt-body-center'
				}, {
					"name" : "roleName",
					"targets" : 2,
					"defaultContent" : "",
					"searchable" : false,
					"orderable" : false,
					"className" : 'dt-body-center'
					},{
				"name" : "status",
				"targets" : 3,
				"searchable" : false,
				"orderable" : false,
				"defaultContent" : "",
				"className" : 'dt-body-center'
			},{
				"name" : "surRecid",
				"targets" : 4,
				"searchable" : false,
				"orderable" : false,				
				"className" : 'dt-body-center',
				"render" : function( data, type, row, meta ){
					
					return '<button id="delete" type="button" class="btn btn-danger" style="padding: 1px"><i class="fa fa-trash "></i> Delete </button>';
							
				}
			} ],
			"aoColumns" : [  {
				"mData" : "roleId"
				},
				{
				"mData" : "roleCode"
				}, {
				"mData" : "roleName"
				}, {
				"mData" : "status"
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
			/*	$('#roleRequestId').val(data.requestId);
				$('#roleSurRecid').val(data.surRecid);
				$('#reasonrole').val(data.reason);*/
			},	"initComplete": function (settings, json) {
				console.log(JSON.stringify(json));			
                if(this.api().data().length>0){
                	 isRoleAdded=true;
     				 $("#roleRequestId").val(json.aaData[0].requestId);
    				 $("#roleSurRecid").val(json.aaData[0].surRecid);    				 
    				 $("#reasonrole").val(json.aaData[0].reason);   
			       } else{
						//$("#fillpgd").removeAttrttr("disabled");	
						 isRoleAdded=false; 
				}                                               
              },
              "drawCallback": function( settings ) {								               
            	  if(this.api().data().length>0){
            		  isRoleAdded=true;
                 	  var api = this.api();
                 	 $("#roleRequestId").val(api.rows( {page:'current'} ).data()[0].requestId);
    				 $("#roleSurRecid").val(api.rows( {page:'current'} ).data()[0].surRecid);   
                      $("#reasonrole").val(api.rows( {page:'current'} ).data()[0].reason);
             
 			       } else{ 						
 						 isRoleAdded=false;   
 						
 					}  
              }
		});
		
		
		var selectedRolesData=null;
		$('#rolesDataTables tbody').on(
				'click',
				'tr',
				function() {				
					selectedRolesData = null;
					if ($(this).hasClass('selected')) {						
						$(this).removeClass('selected');
						if ($(this).hasClass('odd-row')) {
																	
							$(this).find('td').css(
									'background-color',
									'##A6DFC1');
						}
					} else {
						rolesDataTables.$('tr.selected')
								.removeClass('selected');
					
						rolesDataTables.$('tr.odd-row').each(
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
						selectedRolesData  = rolesDataTables.row(this).data();						
						$(this).addClass('selected');
						//getPlantForSelectedTocde(selectedRolesData);
					}

				});
		
		
		$('#rolesDataTables  tbody')
		.on('click','button[type="button"]',
		function(e) {
			var $row = $(this).closest('tr');
			// Get row data
			var data = rolesDataTables.row($row)
			.data();
			console.log(JSON.stringify(data));
			swal({
                title: "Are you sure?",
                text: "Once delete , data will not recover!",
                type: "warning",
                showCancelButton: true,
                confirmButtonClass: "'btn btn-danger'",
                confirmButtonText: "Yes, Delete it!",
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
  		    sdata.status="D";			
			sdata.requestId= data.requestId;
			sdata.surpRecid= data.surpRecid ;
		    //	delete	sdata.createdOn;
			//  delete	sdata.requestDate;
			console.log(JSON.stringify(sdata));
			deleteRoles(sdata);
              }
           });
		});
		function	deleteRoles(data){	
			
			 $('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
			resetErrors();
			$
					.ajax({
						contentType : 'application/json; charset=utf-8',
						type : 'POST',
						url : 'deletePlantsPurchegroup',
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
							/*$('#roleRequestId').val("");
							$('#roleSurRecid').val("");*/
							$.ajax({
								type : 'GET',
								url : "<c:url value="/listHeaderData/R"/>",
								dataType : "json",
								async : false,
								success : handleDataRole,
								error : function(xhRequest, ErrorText,
										thrownError) {

								}
							});
							rolesDataTables.ajax.url("getRequestedRolesUserID").load();
							 $('#pageblock').modal('hide');   
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
		
		
		
		  $('#submitRoleReqBtn')
			.click(
					function() {	
						
	//$("#requestId").val(7420231);
//	$("#surRecid").val(17);
					    if(!isRoleAdded){
					    	swal("Role not addedd ",
							"Add Roles Then submit");
					    	return;
					    }

	if(!isEmpty($("#roleRequestId").val())){
	 swal({
       title: "Are you sure?",
       text: "Once submited ,request can't be modified !",
       type: "warning",
       showCancelButton: true,
       confirmButtonClass: "'btn btn-success'",
       confirmButtonText: "Yes, Submit it!",
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
    	 var temp=getFormData($("#requestFormRoleFrom"));
         console
			.log(JSON
					.stringify(temp));
         var sdata=	{};
 	         sdata.status="S"; 
 	         sdata.requestId= $("#roleRequestId").val();
 	         sdata.surRecid=  $("#roleSurRecid").val();
			sdata.fortime= temp.fortime;
			sdata.fromDate= temp.fortime=='allTime'?null:temp.fromDate;
			sdata.toDate=  temp.fortime=='allTime'?null:temp.toDate;
			sdata.reason=  temp.reasonrole;
        
  //	delete	sdata.createdOn;
	//  delete	sdata.requestDate;
	     console.log(JSON.stringify(sdata));
	     saveRoleStatus(sdata);
     }
     });
     
    }    
   	else{						
			swal("Request not cteated ",
			"Create Request then submit.");
    	}
    });
		  
		  
		  function	saveRoleStatus(data){	
			  
			  $('#pageblock').modal({backdrop:'static',keyboard:false, show:true});
				resetErrors();
				$
						.ajax({
							contentType : 'application/json; charset=utf-8',
							type : 'POST',
							url : 'submitRequestRole',
							data : JSON
					          .stringify(data),
							dataType : 'json',
							async : false,
							success : function(data) {
								console
										.log(JSON
												.stringify(data));
								$('#requestFormRoleFrom')[0].reset();
								swal("Success!",
										data.ok,
										"success");
								$('#roleRequestId').val("");
								$('#roleSurRecid').val("");
								rolesDataTables.ajax.url("getRequestedRolesUserID").load();
								 $('#pageblock').modal('hide');   
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
										swal.close()
									}  //if(xhr.status==200){}
									else if(xhRequest.status==404){
									swal("Request not Found!",
										JSON.parse(xhRequest.responseText).ok,
									"error");
                                 }	
									
									else{									
									swal({
										title : "Error",
										text : JSON.parse(xhRequest.responseText).error,
										type : "error",
										showCancelButton : false,
										confirmButtonClass : 'btn btn-theme04',
										confirmButtonText : 'Error!'
									});}
							}
						});
				return false;
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
			};

			function getFormData($form) {
				var unindexed_array = $form.serializeArray();
				var indexed_array = {};

				$.map(unindexed_array, function(n, i) {
					indexed_array[n['name']] = n['value'];
				});

				return indexed_array;
			}
			; 
		
		$('#fromdate').datepicker({
			dateFormat : 'yy-mm-dd',
			weekStart : 0,
			startDate : "0d",
			todayBtn : "linked",
			changeYear : true,
			language : "it",
			autoclose : true,
			minDate : '0',
			todayHighlight : true,
			onSelect : function(selected) {
				$("#todate").removeAttr('disabled');
				$("#todate").datepicker("option", "minDate", selected)
			}
		});

		$('#todate').datepicker({
			dateFormat : 'yy-mm-dd',
			weekStart : 0,
			startDate : "0d",
			todayBtn : "linked",
			changeYear : true,
			language : "it",
			autoclose : true,
			minDate : '0',
			todayHighlight : true,
			onSelect : function(selected) {
				//$("#fromdate").datepicker("option", "minDate", selected)
			}
		});
		
		
		
		$('#fromdate_role').datepicker({
			dateFormat : 'yy-mm-dd',
			weekStart : 0,
			startDate : "0d",
			todayBtn : "linked",
			changeYear : true,
			language : "it",
			autoclose : true,
			minDate : '0',
			todayHighlight : true,
			onSelect : function(selected) {
				$("#todate_role").removeAttr('disabled');
				$("#todate_role").datepicker("option", "minDate", selected)
			}
		});

		$('#todate_role').datepicker({
			dateFormat : 'yy-mm-dd',
			weekStart : 0,
			startDate : "0d",
			todayBtn : "linked",
			changeYear : true,
			language : "it",
			autoclose : true,
			minDate : '0',
			todayHighlight : true,
			onSelect : function(selected) {
				//$("#fromdate").datepicker("option", "minDate", selected)
			}
		});

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
		 //  $('#pageblock').modal('hide');
			$.unblockUI();
		    $("#wait").css("display", "none");
		});
		$(document).ajaxStop($.unblockUI());		
	   });
		    jQuery(window).load(function () {
		    	//$('#pageblock').modal('hide');
		    	$("#wait").css("display", "none");
		    	  $(document).ajaxStop($.unblockUI());
			});
</script>
<section class="wrapper site-min-height" style="padding: 0px;">

	<div id="wait" class="uiblock"
		style="display: none; width: 169px; height: 189px; border: 1px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
		<img src='resources/assets/img/ajax-loader.gif' width="164"
			height="164" /><br>
	</div>
	<div class="col-lg-12 column" style="width: 100%">
		<div class="portlet">
			<div class="portlet-header">

				<fieldset id="requestType">
					<span class="ui-text"> <label for="requestType">
							Select Request Type</label></span> <input type="radio" name="requestType"
						id="tcode_radio" value="T" checked="checked" />T-Code <input
						type="radio" name="requestType" id="role_radio" value="R" />Role
					<input type="radio" name="requestType" id="emp_ref_radio" value="E" />Employee
					code reference
				</fieldset>
			</div>
			<div id="requestTocde">
				<div class="portlet">
					<form id="requestFormTocde">
						<div class="portlet-content form-group">
							<input type="hidden" id="tcodeId" name="tcodeId"/> <input
								type="hidden" id="sapCompanyCode" name="sapCompanyCode"/>
							<input type="hidden" id="sapModuleCode" name="sapModuleCode"/>
							<input type="hidden" id="surRecid" name="surRecid"/> <input
								type="hidden" name="requestType" value="T" />
							  <input type="hidden" id="pgroups" name="purchaseGroup"/>
							<input type="hidden" id="plants" name="plant"/>
							<input type="hidden" id="tcodesText" name="tcode"/>	
							<table
								class="table table-bordered table-striped  table-condensed "
								id="" style="">

								<tr style="width: 100%">
									<td style="width: 10%">SAP Compnay <font color=red>★</font></td>
									<td style="width: 40%"><select id="sapCompanyCodes"
										class="sapCompanyCodes" name="sapCompanyCodes">
									</select></td>
									<td style="width: 10%">Module list</td>
									<td style="width: 40%"><select id="modulelist"
										name="modulelist">
									</select></td>
								</tr>
						  		<tr style="width: 100%">
								    <td style="width: 10%"><label for="requestId">Request
											Number</label></td>
									<td style="width: 30%"><input name="requestId"
										class="form-control" id="requestId" readonly="readonly" /></td>
							
									<td style="width: 10%">Request for duration</td>
									<td class="radio_parent" style="width: 40%">
										<fieldset id="group1">
											<input type="radio" name="fortime" id="allTime"
												value="allTime" checked="checked" />All time <input
												type="radio" name="fortime" id="temporary" value="temporary" />Short
											Duration
										</fieldset>
									</td>
								</tr>
								<tr style="width: 100%; display: none;" class="fortime">
									<td style="width: 10%">From Date</td>
									<td style="width: 40%"><div class="input-group">
											<div class="input-group-addon">
												<i class="fa fa-calendar"> </i>
											</div>
											<input class="form-control datepopup" id="fromdate"
												name="fromDate" placeholder="yyyy-MM-dd" type="text"
												readonly />
										</div></td>
									<td style="width: 10%">To Date</td>
									<td style="width: 40%"><div class="input-group">
											<div class="input-group-addon">
												<i class="fa fa-calendar"> </i>
											</div>
											<input class="form-control datepopup" id="todate"
												name="toDate" placeholder="yyyy-MM-dd" type="text" readonly />
										</div> <!--<input class="form-control datepopup" type="text" /> disabled --></td>
								</tr>
								<tr style="width: 100%">	<td style="width: 10%">Reason for t-code access <font color=red>★</font></td>
									<td style="width: 10%" colspan="9"><input name="reason"
										class="form-control" id="reason" class="reason"
										placeholder="Enter reason for t-code access"></td>
								</tr>
							</table>
							<div class="showback">
								<!-- <button id="addTcodeReqBtn" type="button"
									class="btn btn-success">
									<i class="fa fa-plus"></i> Save Draft
								</button> -->
								
								<button id="btcodes" type="button" class="btn btn-success ">
											<i class="fa fa-plus "></i>Get T-code list 
								</button>
								<button id="bplant" type="button" class="btn btn-success ">
											<i class="fa fa-plus "></i> Get Plants list
								</button>
							   <button id="bPurGrp" type="button" class="btn btn-success  vpgroup" style="display: none;" >
											<i class="fa fa fa-plus "></i> Get Purchasing Grp
								</button>
								<button id="submitTcodeReqBtn" type="button"
									class="btn btn-success">
									<i class="fa fa-save"></i> Final Submit
								</button>
							</div>
						</div>
					</form>

				</div>

				<div class="col-lg-12 column" style="width: 60%">
					<div class="portlet">
						<div class="portlet-header">
							<span class="ui-text">T-code added for approval <font color=red>★</font></span>
							<!--  <mark></mark> -->
						</div>
						<div class="portlet-content form-group">
							<div class="showback"></div>

							<table id="tcodeDataTables"
								class="table table-bordered table-striped  table-condensed "
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<th style="width: 10%">Req No</th>
										<!--<th style="width: 10%">Request No</th> -->
										<th style="width: 20%">T-code name</th>
										<th style="width: 35%">T-code desc</th>
										<th style="width: 10%">Module</th>
										<th style="width: 10%">Status</th>
										<th style="width: 5%"></th>
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
							<span class="ui-text">Plant<font color=red>★</font>/Purchase Group List </font></span>
							<!--  <mark></mark> -->
						</div>
						<div class="portlet-content form-group">
							<div class="showback"></div>

							<table id="selectedPlantDataTables"
								class="table table-bordered table-striped  table-condensed "
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<th style="width: 10%">Id</th>
										<!-- <th style="width: 10%">Request No</th> -->
										<th style="width: 20%">Plant name <font color=red>★</font></th>
										<th style="width: 20%">Purchase Group</th>
										<th style="width: 5%"></th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<!-- /content-panel -->
				</div>
			</div>
			<div id="requestRole" style="display: none">
				<div class="portlet">
					<form id="requestFormRoleFrom">
						<div class="portlet-content form-group">
							<input type="hidden" id="roleId" name="roleId"> 
							<input type="hidden" id="derivedRolesText" name="roleCode">
							<input
								type="hidden" id="roleSurRecid" name="surRecid"> <input
								type="hidden" id="requestType" name="requestType" value="R">
							<table
								class="table table-bordered table-striped  table-condensed "
								id="" style="">
								<tr style="width: 100%">
									<!--  <td style="width: 10%" class="roles">
										<button id="btnDerivedRoles" type="button"
											class="btn btn-success ">
											<i class="fa fa-get-pocket" aria-hidden="true"></i>Get roles
										</button>
									</td>
									<td style="width: 40%;" class="roles"><input type="text"
										id="derivedRolesText" placeholder="Select roles  "
										name="roleCode" class="form-control" readonly="readonly"></td>-->
									<td style="width: 10%"><label for="requestId">Request
											Number</label></td>
									<td style="width: 40%"><input id="roleRequestId"
										name="requestId" class="form-control" id="requestId"
										readonly="readonly" /></td>
									<td style="width: 10%">Request for duration</td>
									<td class="radio_parent" style="width: 40%">
										<fieldset id="group1">
											<input type="radio" name="fortime" id="allTime"
												value="allTime" checked="checked" />All time <input
												type="radio" name="fortime" id="temporary" value="temporary" />Short
											Duration
										</fieldset>
									</td>
								</tr>
								<tr style="width: 100%; display: none;" class="fortime">
									<td style="width: 10%">From Date</td>
									<td style="width: 40%"><div class="input-group">
											<div class="input-group-addon">
												<i class="fa fa-calendar"> </i>
											</div>
											<input class="form-control datepopup" id="fromdate_role"
												name="fromdate" placeholder="yyyy-mm-dd" type="text"
												readonly />
										</div></td>
									<td style="width: 10%">To Date</td>
									<td style="width: 40%"><div class="input-group">
											<div class="input-group-addon">
												<i class="fa fa-calendar"> </i>
											</div>
											<input class="form-control datepopup" id="todate_role"
												name="todate" placeholder="yyyy-mm-dd" type="text" readonly />
										</div> <!--<input class="form-control datepopup" type="text" />--></td>
								</tr>
                                <tr style="width: 100%">
									<td style="width: 10%">Reason for role access <font color=red>★</font></td>
									<td style="width: 10%" colspan="9"><input name="reasonrole"
										class="form-control" id="reasonrole"
										placeholder="Enter reason for role access"></td>
								
								</tr>

							</table>
							<div class="showback">
								<!--  <button id="addRoleBtn" type="button" class="btn btn-success">
									<i class="fa fa-save"></i> Save Draft
								</button>-->
								<button id="btnDerivedRoles" type="button"
											class="btn btn-success ">
											<i class="fa fa-plus" aria-hidden="true"></i>Add roles
										</button>
								<button id="submitRoleReqBtn" type="button"
									class="btn btn-success">
									<i class="fa fa-save"></i> Final Submit
								</button>
							</div>


						</div>
					</form>
				</div>
				<div class="col-lg-12 column" style="width: 100%">
					<div class="portlet">
						<div class="portlet-header">
							<span class="ui-text">Role added for approval <font color=red>★</font> </span>
							<!--  <mark></mark> -->
						</div>
						<div class="portlet-content form-group">
							<div class="showback"></div>

							<table id="rolesDataTables"
								class="table table-bordered table-striped  table-condensed "
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<!-- <th style="width: 10%">Request No</th>-->
										<th style="width: 10%">Role Id</th>
										<th style="width: 30%">Role-code</th>
										<th style="width: 45%">Role desc</th>
										<th style="width: 10%">Status</th>
										<th style="width: 5%"></th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<!-- /content-panel -->
				</div>
			</div>
			<div id="requestEmployeeRefrnces" style="display: none">
				<form id="requestEmployeeRefrncesFrom">
					<div class="portlet-content form-group">

						<input type="hidden" id="refSapUserId" name="refSapUserId"> 
						<input type="hidden" id="refEmpCode" name="refEmpCode"> 
						<input type="hidden" id="hreason" name="reason"> 
						<input
								type="hidden" id="EmpSurRecid" name="surRecid">
						<input
							type="hidden" id="requestType" name="requestType" value="E">
						
						<!-- $("#EmpRequestId").val(sodUserRequests.requestId);
						$("#EmpSurRecid").val(sodUserRequests.surRecid);					
						$("#reasonEmp").val(sodUserRequests.reason); -->
						<table
							class="table table-bordered table-striped  table-condensed "
							id="" style="">

							<tr style="width: 100%">
								<td style="width: 10%">SAP Compnay</td>
								<td style="width: 40%"><select id="empSapCompanyCode"
									class="sapCompanyCode" name="sapCompanyCode">
								</select></td>
								<!--  <td style="width: 10%"><label for="emp_code">Employee</label></td>

							<td style="width: 40%"><input id="emp_code" name="emp_code"
								placeholder="Select employee"
								class="form-control immybox immybox_witharrow tcode" type="text"
								value=""></td>	-->



								<td style="width: 10%"><label for="users">Search
										Users</label></td>
								<td style="width: 40%" ><input id="users"
									name="refEmpCodes" placeholder="Select users"
									class="form-control immybox immybox_witharrow users"
									type="text" value=""></td>

							</tr>



							<tr style="width: 100%">
							<td style="width: 10%"><label for="requestId">Request
											Number</label></td>
								<td style="width: 40%"><input id="EmpRequestId"
										name="requestId" class="form-control" id="requestId"
										readonly="readonly" /></td>
								<td style="width: 10%">Request for duration</td>
								<td class="radio_parent" style="width: 40%">
									<fieldset id="group1">
										<input type="radio" name="fortime" id="allTime"
											value="allTime" checked="checked" />All time <input
											type="radio" name="fortime" id="temporary" value="temporary" />Short
										Duration
									</fieldset>
								</td>
							</tr>
							<tr style="width: 100%; display: none;" class="fortime">
								<td style="width: 10%">From Date</td>
								<td style="width: 40%"><div class="input-group">
										<div class="input-group-addon">
											<i class="fa fa-calendar"> </i>
										</div>
										<input class="form-control datepopup" id="fromdate"
											name="fromdate" placeholder="dd/MM/yyyy" type="text" readonly />
									</div></td>
								<td style="width: 10%">To Date</td>
								<td style="width: 40%"><div class="input-group">
										<div class="input-group-addon">
											<i class="fa fa-calendar"> </i>
										</div>
										<input class="form-control datepopup" id="todate"
											name="todate" placeholder="dd/MM/yyyy" type="text" readonly />
									</div> <!--<input class="form-control datepopup" type="text" />--></td>
							</tr>
							<tr style="width: 100%">
								<td style="width: 10%">Reason for access</td>
								<td style="width: 10%" colspan="9"><input  name="empreason"
										class="form-control" id="reasonEmp"
										placeholder="Enter reason for access"></td>
							
							</tr>
							
						</table>
						<div class="showback">
							<!--  <button id="getTocdeBtn" type="button" class="btn btn-success">
							<i class="fa fa-save"></i> Add role
						</button>-->
							<button id="requestRefEmpBtn" type="button"
								class="btn btn-success">
								<i class="fa fa-save"></i> Final Submit
							</button>
						</div>


					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="modal top fade" id="plantmodel" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		data-backdrop="false" data-keyboard="false">
		<div class="modal-dialog modal-side modal-top-right modal-lg">
			<div class="modal-content">
				<div class="modal-header text-white">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">Tocde</h5>
				</div>


				<div class="modal-body">
					<table id="planttable"
						class="table table-bordered table-striped  table-condensed "
						cellspacing="0" width="100%">
						<thead>
							<tr>

								<th style="width: 3%"><input name="select_all" value="1"
									type="checkbox"></th>
								<th style="width: 15%">Plant Code</th>
								<th style="width: 72%">Plant description</th>
								<th style="width: 10%">SAP Company</th>

							</tr>
						</thead>
					</table>
				</div>
				<!-- <div class="modal-footer">
					<button type="button" class="btn btn-info" id="savedetails">Save</button>
					<button type="button" class="btn btn-outline-info ripple-surface"
					data-dismiss="modal" style="min-width: 84px;">Close</button>
				</div> -->
			</div>

		</div>
	</div>


	<!-- Modal details-->


	<div class="modal top fade " id="purchasingModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		data-backdrop="false" data-keyboard="false">
		<div class="modal-dialog modal-side modal-top-right modal-lg">
			<div class="modal-content">
				<div class="modal-header text-white">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">Purchasing group</h5>
				</div>


				<div class="modal-body">
					<table id="purchasingTable"
						class="table table-bordered table-striped  table-condensed "
						cellspacing="0" width="100%">
						<thead>
							<!-- style="background-color: #d9edf7; height: 50px;" -->
							<tr>

								<th style="width: 3%"><input name="select_all" value="1"
									type="checkbox"></th>
								<th style="width: 20%">Purchasing group</th>
								<th style="width: 77%">Purchasing name</th>


								<!-- <th style="width: 20%">Role</th> -->

							</tr>
						</thead>
					</table>
				</div>

			</div>

		</div>
	</div>




	<div class="modal top fade" id="showttcodemodal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		data-backdrop="false" data-keyboard="false">
		<div class="modal-dialog modal-side modal-top-right modal-lg">
			<div class="modal-content">
				<div class="modal-header text-white">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">T-code</h5>
				</div>
				<div class="modal-body">
					<table id="tcodeTable"
						class="table table-bordered table-striped  table-condensed "
						cellspacing="0" width="100%">
						<thead>
							<tr>


								<th style="width: 3%"><input name="select_all" value="1"
									type="checkbox"></th>
								<th style="width: 17%">T-code</th>
								<th style="width: 10%">Type of role</th>
								<th style="width: 60%">T-code Description</th>
								<th style="width: 60%">Sensitive</th>
								<th style="width: 10%">module</th>


								<!-- <th style="width: 20%">Role</th> -->

							</tr>
						</thead>
					</table>
				</div>
			</div>

		</div>
	</div>


	<div class="modal top fade" id="showrolemodal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		data-backdrop="false" data-keyboard="false">
		<div class="modal-dialog modal-side modal-top-right modal-lg">
			<div class="modal-content">
				<div class="modal-header text-white">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">Roles</h5>
				</div>
				<div class="modal-body">
					<table id="assignrolestable"
						class="table table-bordered table-striped  table-condensed "
						cellspacing="0" width="100%">
						<thead>
							<tr>
								<!--  style="background-color: #d9edf7; height: 30px;" -->
								<th style="width: 3%"><input name="select_all" value="1"
									type="checkbox"></th>
								<th class="dt_col_hide"></th>
								<th style="width: 22%">Role code</th>
								<th style="width: 45%">Role name</th>
								<th style="width: 10%">Plant</th>
								<th style="width: 10%">Module</th>
								<th style="width: 10%">Entity</th>
								<!--  <th style="width: 10%">Sensitive</th>-->
							</tr>
						</thead>
					</table>
				</div>
			</div>

		</div>
	</div>



	<div id="apptoolbar" style="display: none">
		<span class="showback">
			<button id="assignplant" class="btn btn-success" type="button">
				<i class="glyphicon glyphicon-edit"></i> Save plant
			</button>
		</span>
	</div>

	<div id="pgtoolbar" style="display: none">
		<span class="showback">
			<button id="assignpggroup" class="btn btn-success" type="button">
				<i class="glyphicon glyphicon-edit"></i> Save purchasing group
			</button>
		</span>
	</div>
	<div id="tocdetoolbar" style="display: none">
		<span class="showback">
			<button id="btnAssignTcode" class="btn btn-success" type="button">
				<i class="glyphicon glyphicon-edit"></i> Save T-code
			</button>
		</span>
	</div>
	<div id="roletoolbars" style="display: none">
		<span class="showback">
			<button id="btnAssignRoles" class="btn btn-success" type="button">
				<i class="glyphicon glyphicon-edit"></i> Save Roles
			</button>
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
</section>
