<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script>
	$(document).ready(function() {

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
		$('input[type=radio][name=requestType]').change(function() {			 
		    if (this.value == "T") {		    	
				   $("#requestTocde").show();
				   $("#requestEmployeeRefrnces").hide();
				   $("#requestRole").hide();	

		    }else if (this.value == "R") {

		    	 $("#requestRole").show();
		    	 $("#requestEmployeeRefrnces").hide();
		    	 $("#requestTocde").hide();	
		    	

		    }else if (this.value == "E") {		    	
		    	 $("#requestEmployeeRefrnces").show();
		    	 $("#requestTocde").hide();	
		    	 $("#requestRole").hide();
		    }

		});
		
			$
					.ajax({
						type : 'GET',
						url : "<c:url value="/listHeaderData"/>",
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
				var modulelistHtml = '<option value="">Select modules</option>';
				var sapCompanyCodeHtml = '<option value="">Select  sap company</option>';
				
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

			};
			
            
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
					
					rows_selected.forEach(function(rowData) {															
						lplants.push(rowData.plantCode);								
					}); 
					$('#plants').val(lplants);							
					console.log(lplants);							
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
				
				
				rows_selected.forEach(function(rowData) {															
					lgroups.push(rowData.masterIdCode);								
				}); 
				$('#pgroups').val(lgroups);
			
				console.log(lgroups);
				
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
					}, {
					"name" : "tcodeDesc",
					"targets" : 2,
					"searchable" : false,
					"orderable" : false,
					"defaultContent" : "",
					"className" : 'dt-body-center'
				},{
					"name" : "module",
					"targets" : 3,
					"searchable" : false,
					"orderable" : false,
					"defaultContent" : "",
					"className" : 'dt-body-center'
				} ],
				"aoColumns" : [ {
					"mData" : "tcode"
				    },{
					"mData" : "tcode"
					}, {
					"mData" : "tcodeDesc"
					},{
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
						rows_selected.forEach(function(rowData) {															
							tcodes.push(rowData.tcode);	
							tcodeIds.push(rowData.tcodeId);
						}); 
						$('#tcodesText').val(tcodes);					
						$('#tcodeId').val(tcodeIds);					
						console.log(tcodes);						
						$('#showttcodemodal').modal('hide');
			});
		
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
								"name" : "funCode",
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
							} ],
					"aoColumns" : [ {
						"mData" : "funId"
					}, {
						"mData" : "roleId"
					},{
						"mData" : "roleCode"
					}, {
						"mData" : "roleName"
					}, {
						"mData" : "funCode"
					}, {
						"mData" : "module"
					}, {
						"mData" : "entity"
					}],
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
					rows_selected.forEach(function(rowData) {	
						console.log(rowData.roleCode+"     "+rowData.roleId);		
					
						roleCodes.push(rowData.roleCode);								
						roleIds.push(rowData.roleId);								
					}); 
					$('#derivedRolesText').val(roleCodes);						
					$('#roleId').val(roleIds);					
					console.log(roleCodes);						
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
         
         $('#addTcodeReqBtn').click(function() {			 
			 var module = isEmpty($("#modulelist option:selected").val())?"false":$("#modulelist option:selected").val();
			// alert(module);
			 openTcodetableModel(module);   
		 });
         
         $('#btcodes')
			.click(
					function() {
					
						console.log(JSON.stringify(getFormData($("#requestFormTocde"))));
						resetErrors();
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
										console
												.log(JSON
														.stringify(data));

										swal("Success!",
												data.ok,
												"success");
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
											confirmButtonText : 'Danger!'
										});
									}
								});
						return false;
					});
      
				function	saveStatus(data){	
						resetErrors();
						$
								.ajax({
									contentType : 'application/json; charset=utf-8',
									type : 'POST',
									url : 'submitRequest',
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
										$("#requestId").val("");
										$("#surRecid").val("");
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
											confirmButtonText : 'Danger!'
										});
									}
								});
						return false;
					};
            $('#submitTcodeReqBtn')
						.click(
								function() {	
									
				//$("#requestId").val(7420231);
			//	$("#surRecid").val(17);
				if(!isEmpty($("#requestId").val())){
				 swal({
                     title: "Are you sure?",
                     text: "Once submited ,request can't be modified !",
                     type: "warning",
                     showCancelButton: true,
                     confirmButtonClass: "'btn btn-success'",
                     confirmButtonText: "Yes, Submit it!",
                     closeOnConfirm: false
                 },
                  function(isConfirm) {
                     //alert(isConfirm);					
                   if (isConfirm) {
                	   
                var sdata=	{};
       		    sdata.status="S";   
				
				sdata.requestId= $("#requestId").val();
				sdata.surRecid=  $("#surRecid").val();
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
         $('#addRoleBtn')
			.click(
					function() {
					
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

										swal("Success!",
												data.ok,
												"success");
									//	mroletable.ajax.url("tcodeDataTables").load();
										rolesDataTables.ajax.url("getRequestedRolesUserID").load();
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
										//	mroletable.ajax.url("masterroledata").load();
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
         $('#requestRefEmpBtn')
			.click(
					function() {
					
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

										swal("Success!",
												data.ok,
												"success");
										//mroletable.ajax.url("tcodeDataTables").load();

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
											//mroletable.ajax.url("masterroledata").load();
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
      
     	var tcodeDataTables	=null;
		tcodeDataTables = $("#tcodeDataTables").DataTable({
			//stateSave: true,
			select : {
				'style' : 'single'
			},
			"dom" : 'l<"toolbar">frtip',
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
				"name" : "requestId",
				"targets" : 1,
				"defaultContent" : "",
				"searchable" : false,
				"orderable" : false,	
			},
			{
				"name" : "tcode",
				"targets" : 2,
				"defaultContent" : "",
				"searchable" : false,
				"orderable" : false,
				"className" : 'dt-body-center'
				}, {
					"name" : "tcodeDesc",
					"targets" : 3,
					"defaultContent" : "",
					"searchable" : false,
					"orderable" : false,
					"className" : 'dt-body-center'
					},{
				"name" : "module",
				"targets" : 4,
				"searchable" : false,
				"orderable" : false,
				"defaultContent" : "",
				"className" : 'dt-body-center'
			},{
				"name" : "status",
				"targets" : 5,
				"searchable" : false,
				"orderable" : false,
				"defaultContent" : "",
				"className" : 'dt-body-center'
			},{
				"name" : "surRecid",
				"targets" : 6,
				"searchable" : false,
				"orderable" : false,				
				"className" : 'dt-body-center',
				"render" : function( data, type, row, meta ){
					
					return '<button id="delete" type="button" class="btn btn-danger" style="padding: 1px"><i class="fa fa-trash "></i> Delete </button>';
							
				}
			} ],
			"aoColumns" : [  {
				"mData" : "surtRecid"
				},  {
				"mData" : "requestId"
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
				$("#requestId").val(data.requestId);
				$("#surRecid").val(data.surRecid);
				$("#sapCompanyCodes").val(data.sapCompanyCode);
				$("#modulelist").val(data.module);
				$("#sapCompanyCodes").attr("disabled", true); 
				$("#modulelist").attr("disabled", true); 
				$("#sapCompanyCode").val(data.sapCompanyCode);
				$("#sapModuleCode").val(data.module);
				//"module":"CO","sapCompanyCode":"2000"
			}
		});
		
		var selectedTcodeData = null;
		$('#tcodeDataTables').on('draw.dt', function() {
			console.log('tcodeDataTables Change');	
			if(!isEmpty($("#requestId").val()))
			selectedPlantDataTables.ajax.url("getRequestedPlant/"+$("#surRecid").val()+"/"+$("#requestId").val()).load();
			else{
				selectedPlantDataTables.ajax.url("getRequestedPlant/0/0").load(); 
			}
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
									'#cfe2ff');
						}
					} else {
						tcodeDataTables.$('tr.selected')
								.removeClass('selected');
					
						tcodeDataTables.$('tr.odd-row').each(
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
						selectedTcodeData  = tcodeDataTables.row(this).data();						
						$(this).addClass('selected');
						getPlantForSelectedTocde(selectedTcodeData);
					}

				});
		
		function getPlantForSelectedTocde(selectedTcodeData){
			if(!isEmpty(selectedTcodeData)){
				selectedPlantDataTables.ajax.url("getRequestedPlant/"+selectedTcodeData.surRecid+"/"+selectedTcodeData.requestId).load();
			}
		};
		
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
			"iDisplayLength" : 10,
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
					
				},{
				"name" : "requestId",
				"targets" : 1,
				"defaultContent" : "",
				"searchable" : false,
				"orderable" : false,
				"className" : 'dt-body-center'
				}, {
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
				"name" : "surRecid",
				"targets" : 4,
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
				"mData" : "requestId"
				}, {
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
                closeOnConfirm: false
            },
             function(isConfirm) {
                //alert(isConfirm);					
              if (isConfirm) {
           	   
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
                closeOnConfirm: false
            },
             function(isConfirm) {
                //alert(isConfirm);					
              if (isConfirm) {
           	   
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
							$("#requestId").val("");
							$("#surRecid").val("");
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
								confirmButtonText : 'Danger!'
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
							$("#requestId").val("");
							$("#surRecid").val("");
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
								confirmButtonText : 'Danger!'
							});
						}
					});
			return false;
		};
		//$("#selectedPlantDataTables").DataTable({bFilter: false, bInfo: false});
		
		
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
				"name" : "surpRecid",
				"targets" : 0,
				"defaultContent" : "",
				"searchable" : false,
				"orderable" : false,
				"className" : 'dt_col_hide'
				
			},
			{
				"name" : "requestId",
				"targets" : 1,
				"defaultContent" : "",
				"searchable" : false,
				"orderable" : false,	
			},
			{
				"name" : "roleCode",
				"targets" : 2,
				"defaultContent" : "",
				"searchable" : false,
				"orderable" : false,
				"className" : 'dt-body-center'
				}, {
					"name" : "roleName",
					"targets" : 3,
					"defaultContent" : "",
					"searchable" : false,
					"orderable" : false,
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
				"mData" : "surpRecid"
				},  {
				"mData" : "requestId"
				},{
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
				$('#roleRequestId').val(data.requestId);
				$('#roleSurRecid').val(data.surRecid);
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
									'#cfe2ff');
						}
					} else {
						rolesDataTables.$('tr.selected')
								.removeClass('selected');
					
						rolesDataTables.$('tr.odd-row').each(
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
                closeOnConfirm: false
            },
             function(isConfirm) {
                //alert(isConfirm);					
              if (isConfirm) {
           	   
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
							$('#roleRequestId').val("");
							$('#roleSurRecid').val("");
							rolesDataTables.ajax.url("getRequestedRolesUserID").load();
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
								confirmButtonText : 'Danger!'
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
	if(!isEmpty($("#roleRequestId").val())){
	 swal({
       title: "Are you sure?",
       text: "Once submited ,request can't be modified !",
       type: "warning",
       showCancelButton: true,
       confirmButtonClass: "'btn btn-success'",
       confirmButtonText: "Yes, Submit it!",
       closeOnConfirm: false
   },
    function(isConfirm) {
       //alert(isConfirm);					
     if (isConfirm) {
  	   
  var sdata=	{};
	    sdata.status="S";   
	
	sdata.requestId= $("#roleRequestId").val();
	sdata.surRecid=  $("#roleSurRecid").val();
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
				resetErrors();
				$
						.ajax({
							contentType : 'application/json; charset=utf-8',
							type : 'POST',
							url : 'submitRequest',
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
								$('#roleRequestId').val("");
								$('#roleSurRecid').val("");
								rolesDataTables.ajax.url("getRequestedRolesUserID").load();

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
									confirmButtonText : 'Danger!'
								});
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
		
		$('#fromdate').datepicker({
			dateFormat : 'dd/mm/yy',
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
			dateFormat : 'dd/mm/yy',
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
		    jQuery(window).load(function () {
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
							<input type="hidden" id="tcodeId" name="tcodeId"> 
							<input type="hidden" id="sapCompanyCode" name="sapCompanyCode">
							<input type="hidden" id="sapModuleCode" name="sapModuleCode">
							<input
								type="hidden" id="surRecid" name="surRecid"> <input
								type="hidden" name="requestType" value="T" />
							<table
								class="table table-bordered table-striped  table-condensed "
								id="" style="">

								<tr style="width: 100%">
									<td style="width: 10%">SAP Compnay</td>
									<td style="width: 40%"><select id="sapCompanyCodes"
										class="sapCompanyCodes" name="sapCompanyCodes">
									</select></td>
									<td style="width: 10%">Module list</td>
									<td style="width: 40%"><select id="modulelist"
										name="modulelist">
									</select></td>
								</tr>
								<!-- <tr style="width: 100%">
									<td style="width: 10%" class="vplant">
										<button id="bplant" type="button" class="btn btn-info ">
											<i class="fa get-pocket "></i> Get Plants list
										</button>
									</td>
									<td style="width: 40%" class="vplant"><input type="text"
										id="plants" placeholder="Select Plants" name="plant"
										class="form-control" readonly="readonly"></td>

									<td style="width: 10%;" class="vpgroup">
										<button id="bPurGrp" type="button" class="btn btn-info ">
											<i class="fa get-pocket "></i>Purchasing Grp
										</button>
									</td>
									
									<td style="width: 40%;" class="vpgroup"><input type="text"
										id="pgroups" placeholder="Select Purchasing Group"
										name="purchaseGroup" class="form-control" readonly="readonly"></td>
								</tr> -->
								<!-- <td style="width: 10%"><label for="tcode">T-Codes</label></td>
							 <td style="width: 40%"><input id="tcode" name="tcode"
								placeholder="Select tcode"
								class="form-control immybox immybox_witharrow tcode" type="text"
								value=""></td>-->
								<!--  <tr style="width: 100%">

									
									<td style="width: 10%" class="tcodes">
										<button id="btcodes" type="button" class="btn btn-info ">
											<i class="fa get-pocket "></i>Get T-code list
										</button>
									</td>
									<td style="width: 40%;" class="tcodes"><input type="text"
										id="tcodesText" placeholder="Select t-code" name="tcode"
										class="form-control" readonly="readonly"></td>
									<td style="width: 10%">Type of role</td>
									<td style="width: 40%"><select id="stdroles"
										name="typeOfRight[]" multiple="multiple" class="4colactive"></select></td>

								</tr>-->
								<tr style="width: 100%">
									<td style="width: 10%">Reason for t-code access</td>
									<td style="width: 30%" colspan="3"><textarea rows="2"
											name="reason" class="form-control" id="reason"
											placeholder="Enter reason for t-code access"></textarea></td>

								</tr>
								<tr style="width: 100%">
									<td style="width: 10%">Request for duration</td>
									<td class="radio_parent" style="width: 40%">
										<fieldset id="group1">
											<input type="radio" name="fortime" id="allTime"
												value="allTime" checked="checked" />All time <input
												type="radio" name="fortime" id="temporary" value="temporary" />Short
											Duration
										</fieldset>
									</td>
									<td style="width: 10%"><label for="requestId">Request
											Number</label></td>
									<td style="width: 50%"><input name="requestId"
										class="form-control" id="requestId" readonly="readonly" /></td>
								</tr>
								<tr style="width: 100%; display: none;" class="fortime">
									<td style="width: 10%">From Date</td>
									<td style="width: 40%"><div class="input-group">
											<div class="input-group-addon">
												<i class="fa fa-calendar"> </i>
											</div>
											<input class="form-control datepopup" id="fromdate"
												name="fromDate" placeholder="dd/MM/yyyy" type="text"
												readonly />
										</div></td>
									<td style="width: 10%">To Date</td>
									<td style="width: 40%"><div class="input-group">
											<div class="input-group-addon">
												<i class="fa fa-calendar"> </i>
											</div>
											<input class="form-control datepopup" id="todate"
												name="toDate" placeholder="dd/MM/yyyy" type="text" readonly />
										</div> <!--<input class="form-control datepopup" type="text" /> disabled --></td>
								</tr>
							</table>
							<div class="showback">
							<button id="resetTcodeReqBtn" type="button"
									class="btn btn-success">
									<i class="fa fa-save"></i> Reset Request
								</button>
								<button id="saveReqBtn" type="button"
									class="btn btn-success">
									<i class="fa fa-save"></i> Save
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
							<span class="ui-text">T-code added for approval </span>
							<!--  <mark></mark> -->
						</div>
						<div class="portlet-content form-group">
							<div class="showback">
							  <button id="addTcodeReqBtn" type="button"
									class="btn btn-success">
									<i class="fa fa-save"></i> Add T-code
								</button>								
							</div>

							<table id="tcodeDataTables"
								class="table table-bordered table-striped  table-condensed "
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<th style="width: 10%">Req No</th>
										<th style="width: 10%">Request No</th>
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
							<span class="ui-text">Plant/Purchase Group List </span>
							<!--  <mark></mark> -->
						</div>
						<div class="portlet-content form-group">
							<div class="showback">
							<button id="addPlantBtn" type="button"
									class="btn btn-success">
									<i class="fa fa-save"></i> Add Plant
								</button>
								<button id="addPurchaseReqBtn" type="button"
									class="btn btn-success">
									<i class="fa fa-save"></i> Add Purchase Group
								</button>
							</div>

							<table id="selectedPlantDataTables"
								class="table table-bordered table-striped  table-condensed "
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<th style="width: 10%">Id</th>
										<th style="width: 10%">Request No</th>
										<th style="width: 20%">Plant name</th>
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
							<input type="hidden" id="roleId" name="roleId"> <input
								type="hidden" id="roleSurRecid" name="surRecid"> <input
								type="hidden" id="requestType" name="requestType" value="R">
							<table
								class="table table-bordered table-striped  table-condensed "
								id="" style="">
								<tr style="width: 100%">
									<td style="width: 10%" class="roles">
										<button id="btnDerivedRoles" type="button"
											class="btn btn-info ">
											<i class="fa get-pocket "></i>Get roles
										</button>
									</td>
									<td style="width: 40%;" class="roles"><input type="text"
										id="derivedRolesText" placeholder="Select roles  "
										name="roleCode" class="form-control" readonly="readonly"></td>
									<td style="width: 10%"><label for="requestId">Request
											Number</label></td>
									<td style="width: 40%"><input id="roleRequestId"
										name="requestId" class="form-control" id="requestId"
										readonly="readonly" /></td>
								</tr>
								<tr style="width: 100%">
									<td style="width: 10%">Reason for role access</td>
									<td style="width: 50%"><textarea rows="2" name="reason"
											class="form-control" id="reason_role"
											placeholder="Enter reason for role access"></textarea></td>
									<td style="width: 10%">Request for duration</td>
									<td class="radio_parent" style="width: 40%">
										<fieldset id="group1">
											<input type="radio" name="fortime" id="allTime"
												value="allTime" checked="checked" />All time <input
												type="radio" name="fortime" id="temporary" value="temporary" />Short Duration
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
												name="fromdate" placeholder="dd/MM/yyyy" type="text"
												readonly />
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

							</table>
							<div class="showback">
								<button id="addRoleBtn" type="button" class="btn btn-success">
									<i class="fa fa-save"></i> Add role
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
							<span class="ui-text">Role added for approval </span>
							<!--  <mark></mark> -->
						</div>
						<div class="portlet-content form-group">
							<div class="showback"></div>

							<table id="rolesDataTables"
								class="table table-bordered table-striped  table-condensed "
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<th style="width: 10%">Request No</th>
										<th style="width: 10%">Role Id</th>
										<th style="width: 15%">Role-code</th>
										<th style="width: 25%">Role desc</th>
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

						<input type="hidden" id="tcodeId" name="refSapUserId"> <input
							type="hidden" id="requestType" name="requestType" value="E">
						<table
							class="table table-bordered table-striped  table-condensed "
							id="" style="">

							<tr style="width: 100%">
								<td style="width: 10%">SAP Compnay</td>
								<td style="width: 40%"><select id="sapCompanyCode"
									class="sapCompanyCode" name="sapCompanyCode">
								</select></td>
								<!--  <td style="width: 10%"><label for="emp_code">Employee</label></td>

							<td style="width: 40%"><input id="emp_code" name="emp_code"
								placeholder="Select employee"
								class="form-control immybox immybox_witharrow tcode" type="text"
								value=""></td>	-->



								<td style="width: 10%"><label for="users">Search
										Users</label></td>
								<td style="width: 40%" colspan="4"><input id="users"
									name="refEmpCode" placeholder="Select users"
									class="form-control immybox immybox_witharrow users"
									type="text" value=""></td>

							</tr>



							<tr style="width: 100%">
								<td style="width: 10%">Reason for access</td>
								<td style="width: 50%"><textarea rows="2" name="reason"
										class="form-control" id="reason"
										placeholder="Enter reason for access"></textarea></td>
								<td style="width: 10%">Request for duration</td>
								<td class="radio_parent" style="width: 40%">
									<fieldset id="group1">
										<input type="radio" name="fortime" id="allTime"
											value="allTime" checked="checked" />All time <input
											type="radio" name="fortime" id="temporary" value="temporary" />Short Duration
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

								<th style="width: 5%"><input name="select_all" value="1"
									type="checkbox"></th>
								<th style="width: 40%">Plant Code</th>
								<th style="width: 60%">Plant description</th>
								<th style="width: 60%">SAP Company</th>

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


	<div class="modal top fade" id="purchasingModal" tabindex="-1"
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

								<th style="width: 5%"><input name="select_all" value="1"
									type="checkbox"></th>
								<th style="width: 40%">Purchasing group</th>
								<th style="width: 55%">Purchasing name</th>


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


								<th style="width: 5%"><input name="select_all" value="1"
									type="checkbox"></th>
								<th style="width: 40%">T-code</th>
								<th style="width: 55%">T-code Description</th>
								<th style="width: 55%">module</th>


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
								<th style="width: 5%"><input name="select_all" value="1"
									type="checkbox"></th>
								<th class="dt_col_hide"></th>
								<th style="width: 25%">Role code</th>
								<th style="width: 25%">Role name</th>
								<th style="width: 20%">FUN Code</th>
								<th style="width: 10%">Module</th>
								<th style="width: 15%">Entity</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>

		</div>
	</div>



	<div id="apptoolbar" style="display: none">
		<span class="showback">
			<button id="assignplant" class="btn btn-info" type="button">
				<i class="glyphicon glyphicon-edit"></i> Assign plant
			</button>
		</span>
	</div>

	<div id="pgtoolbar" style="display: none">
		<span class="showback">
			<button id="assignpggroup" class="btn btn-info" type="button">
				<i class="glyphicon glyphicon-edit"></i> Assign purchasing group
			</button>
		</span>
	</div>
	<div id="tocdetoolbar" style="display: none">
		<span class="showback">
			<button id="btnAssignTcode" class="btn btn-info" type="button">
				<i class="glyphicon glyphicon-edit"></i> Assign T-code
			</button>
		</span>
	</div>
	<div id="roletoolbars" style="display: none">
		<span class="showback">
			<button id="btnAssignRoles" class="btn btn-info" type="button">
				<i class="glyphicon glyphicon-edit"></i> Assign Roles
			</button>
		</span>
	</div>
</section>
