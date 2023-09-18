<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script>
	$(document).ready(function() {

		//$("#wait").css("display", "block");
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
			 
		    if (this.value == "t-code") {		    	
				   $("#requestTocde").show();
				   $("#requestEmployeeRefrnces").hide();
				   $("#requestRole").hide();	

		    }else if (this.value == "role") {

		    	 $("#requestRole").show();
		    	 $("#requestEmployeeRefrnces").hide();
		    	 $("#requestTocde").hide();	
		    	

		    }else if (this.value == "emp_ref") {		    	
		    	 $("#requestEmployeeRefrnces").show();
		    	 $("#requestTocde").hide();	
		    	 $("#requestRole").hide();
		    }

		});
		 var masterRoleList=[];
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
				$('#sapCompanyCode').html(sapCompanyCodeHtml);

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
			
			$('#masterrole').on('change', function() {
				                   
            var roleCode = $("#masterrole option:selected").val();
				if (isEmpty(roleCode)) {
					return;
				}
			// console.log("2   " + JSON.stringify(masterRoleList));
          var masterRole= masterRoleList.find(role => role.roleCode === roleCode);
         
           $('#parentRoleId').val(masterRole.roleId);
           $('#funId').val(masterRole.funId);
           $('#entity').val(masterRole.entity);                         
				//getPlantList(masterRole);
			});
			
		$('select[multiple]').multiselect({
			columns : 1
		});

		$('#bplant').click(
				function() {						
					resetErrors();
					var rflag = false;
					var selected = [];
				    for (var option of document.getElementById('sapCompanyCode').options)
				    {
				        if (option.selected) {
				            selected.push(option.value);
				        }
				    }
				console.log(isEmpty(selected));
					if (isEmpty(selected)) {
						swal("Select SAP Company",
						"Select SAP Company to get plant.");
						return;
					}
					                   
					openPlantModel(selected);					
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
						// If row ID is in the list of selected row IDs
						/* if ($.inArray({"samtdRecid":data.samtdRecid,"masterIdCode":data.masterIdCode},
						rows_selected) !== -1) { */
						//console.log(" draw modal ++++++++ rows_selected "+JSON.stringify(rows_selected));		
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
			
			
			 $('#btcodes').click(function() {openTcodetableModel();});
			var tcodeTable = null;
			
			function openTcodetableModel() { tcodeTable = $("#tcodeTable").DataTable({
				//stateSave: true,
				select : {
					'style' : 'single'
				},
				/* "oLanguage" : {
					"sLengthMenu" : "Display _MENU_ ",
				}, */
				"stripeClasses" : [ 'odd-row', 'even-row' ],
				"dom" : 'l<"tocdetoolbar">frtip',
				//"dom" : 'l<"toolbar">frtip',
				/* dom: "<'row'<'col-sm-12'tr>>" +
				"<'row'<'col-sm-4'l><'col-sm-8'p>>", */
				//bFilter : false,
				"responsive" : true,
				"bProcessing" : true,
				"bServerSide" : true,
				//Default: Page display length
				"iDisplayLength" : 10,
				//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
				"iDisplayStart" : 0,
				"sAjaxSource" : "getTcodeByModule/204",
				//"aaSorting" : [ [ 0, "asc" ] ],	
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
						"render" : function(data, type, full, meta){
				               if(type === 'display'){
					                  data = '<input type="radio" name="id" value="' + data.satId + '">';      
					               }

					               return data;
					            }


					},
					{
					"name" : "satId",
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
					"name" : "tcodeDesc",
					"targets" : 3,
					"searchable" : false,
					"orderable" : false,
					"defaultContent" : "",
					"className" : 'dt-body-center'
				}
				,{
					"name" : "module",
					"targets" : 4,
					"searchable" : false,
					"orderable" : false,
					"defaultContent" : "",
					"className" : 'dt-body-center'
				} ],
				"aoColumns" : [ {
					"mData" : "satId"
				    },{
					"mData" : "satId"
					}, {
					"mData" : "tcode"
					}, {
					"mData" : "tcodeDesc"
					},{
					"mData" : "module"
				} ],
				"rowCallback" : function(row,
				data, dataIndex) {
					// Get row ID
					//var functios = data.functios;
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
			$('#showttcodemodal').modal('show');
			};
			$('#showttcodemodal').on('shown.bs.modal', function() {
				$("div.tocdetoolbar").html($('#tocdetoolbar').html());
			});
			$('#showttcodemodal').on('click', '#btnAssignTcode',
					function(e) {
						$('#tcodesText').val();						
						var tcodes = [];
						rows_selected.forEach(function(rowData) {															
							lgroups.push(rowData.masterIdCode);								
						}); 
						$('#tcodesText').val(tcodes);					
						console.log(tcodes);						
						$('#showttcodemodal').modal('hide');
			});
		
			$('#tcodeTable tbody').on('click','tr',function() {
						/* if($(this).hasClass('odd-row')){
						
							$(this).find('td').css('background-color', 'transparent');
							}  */
							var $row = $(this).closest('tr');
							var data = tcodetable.row($row).data();
							console.log(JSON.stringify(data));
						    var tcode = data.tcode;
							var tcodeId = data.satId;																											
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
							tcodetable.$('tr.selected')
									.removeClass('selected');

							tcodetable.$('tr.odd-row').each(
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
							 rows_selected.push({"tcode": tcode,"tcodeId": tcodeId });
							 $("#tcodeId").val(tcodeId);
							$("#tcode").val(tcode);
							console.log(JSON.stringify(rows_selected));
						
							$(this).addClass('selected');
							$(this).find('input:radio').prop('checked', true);
						}
				});
			
			    $('#tcodeTable').on('draw', function() {
				// Update state of "Select all" control
				console.log('tcodetable Change 1');
				 rows_selected=[];
				//updateDataTableSelectAllCtrl(assignrolestable);
				});
				$('#tcodeTable').on('draw.dt', function() {
				console.log('tcodetable Change 2');
						
				 rows_selected=[];
				//updateDataTableSelectAllCtrl(assignrolestable);

				});

				$('#showttcodemodal')
				.on(
				'hidden.bs.modal',
				function() {
					tcodeTable.clear().destroy();
					$('#tcodeTable').dataTable().fnDestroy();});
				
		$('input[type=radio][name=fortime]').change(function() {

			if (this.value == "temporary") {

				// $(".vplant").hide();
				$("#fortime").show();

			} else if (this.value == "allTime") {

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

		$('#todate').datepicker({
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
				$("#fromdate").datepicker("option", "maxDate", selected)
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
							Select Request Type</label></span> 
				<input type="radio" name="requestType"
						id="tcode_radio" value="t-code" checked="checked" />T-Code 
				<input
						type="radio" name="requestType" id="role_radio" value="role" />Role
			    <input type="radio" name="requestType" id="emp_ref_radio"
						value="emp_ref" />Employee Reference
				</fieldset>
			</div>
			<div id="requestTocde">
			<form id="requestFormTocde">
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
							<td style="width: 10%">Module list</td>
							<td style="width: 40%"><select id="modulelist" name="module">
							</select></td>

						</tr>
						<tr style="width: 100%">

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
							<!--  display: none; -->
							<td style="width: 40%;" class="vpgroup"><input type="text"
								id="pgroups" placeholder="Select Purchasing Group"
								name=" purchaseGroup" class="form-control" readonly="readonly"></td>
						</tr>
						<tr style="width: 100%">
							
							<!-- <td style="width: 10%"><label for="tcode">T-Codes</label></td>
							 <td style="width: 40%"><input id="tcode" name="tcode"
								placeholder="Select tcode"
								class="form-control immybox immybox_witharrow tcode" type="text"
								value=""></td>-->
                            <td style="width: 10%" class="tcodes">
								<button id="btcodes" type="button" class="btn btn-info ">
									<i class="fa get-pocket "></i>Get T-code list
								</button>
							</td>
							<td style="width: 40%;" class="tcodes"><input type="text"
								id="tcodesText" placeholder="Select t-code "
								name="tcodes" class="form-control" readonly="readonly"></td>
							<td style="width: 10%">Type of role</td>
							<td style="width: 40%"><select id="stdroles"
								name="typeOfRight[]" multiple="multiple" class="4colactive"></select></td>
							<!--  <td style="width: 10%">Request for duration</td>
							<td class="radio_parent" style="width: 40%">
								<fieldset id="group1">
									<input type="radio" name="fortime" id="allTime" value="allTime" />All
									time <input type="radio" name="fortime" id="temporary"
										value="temporary" />Temporary
								</fieldset>
							</td>-->
						</tr>
						<tr style="width: 100%;">
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
								</div> <!--<input class="form-control datepopup" type="text" />--></td>
						</tr>


						<tr style="width: 100%">
							<td style="width: 10%">Reason for t-code access</td>
							<td style="width: 30%" colspan="3"><input type="text"
								name="reason" class="form-control" id="reason"
								placeholder="Enter reason for t-code access"></td>
						</tr>
					</table>
					<div class="showback">
						<button id="getTocdeBtn" type="button" class="btn btn-success">
							<i class="fa fa-save"></i> Add T-code
						</button>
						<button id="requestTocdeBtn" type="button" class="btn btn-success">
							<i class="fa fa-save"></i> Final Submit
						</button>
					</div>


				</div>
			</form>
			</div>
			<div id="requestRole" style="display: none">
			<form id="requestFormRoleFrom">
			<div class="portlet-content form-group">

					<input type="hidden" id="tcodeId" name="tcodeId"> <input
						type="hidden" id="tcode" name="tcode">


					<table class="table table-bordered table-striped  table-condensed "
						id="" style="">
						<tr style="width: 100%">
							<td style="width: 10%" class="tcodes">
								<button id="btnDerivedRoles" type="button" class="btn btn-info ">
									<i class="fa get-pocket "></i>Get Derived roles 
								</button>
							</td>
							<td style="width: 30%;" class="derivedRoles" colspan="3"><input type="text"
								id="derivedRolesText" placeholder="Select derived roles  "
								name="tcodes" class="form-control" readonly="readonly"></td>
						</tr>
						<tr style="width: 100%" id="fortime">
							<td style="width: 10%">From Date</td>
							<td style="width: 40%" ><div class="input-group">
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
						</tr>


						<tr style="width: 100%">
							<td style="width: 10%">Reason for role access</td>
							<td style="width: 30%" colspan="3"><input type="text"
								name="reason" class="form-control" id="reason"
								placeholder="Enter reason for role access"></td>
						</tr>
					</table>
					<div class="showback">
						<button id="getTocdeBtn" type="button" class="btn btn-success">
							<i class="fa fa-save"></i> Add role
						</button>
						<button id="requestTocdeBtn" type="button" class="btn btn-success">
							<i class="fa fa-save"></i> Final Submit
						</button>
					</div>


				</div>
			</form>
			</div>
			<div id="requestEmployeeRefrnces" style="display: none">
			<form id="requestEmployeeRefrncesFrom">
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
		</div>
		<div class="col-lg-12 column" style="width: 100%">
			<div class="portlet">
				<div class="portlet-header">
					<span class="ui-text">T-code added for approval </span>
					<!--  <mark></mark> -->
				</div>
				<div class="portlet-content form-group">
					<div class="showback"></div>

					<table id="tcodetables"
						class="table table-bordered table-striped  table-condensed "
						cellspacing="0" width="100%">
						<thead>
							<tr>
								<th style="width: 10%">Request No</th>
								<th style="width: 20%">T-code name</th>
								<th style="width: 25%">T-code desc</th>
								<th style="width: 15%">Module</th>
								<th style="width: 15%">Status</th>
								<th style="width: 15%">Delete</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<!-- /content-panel -->
		</div>
		<!-- /content-panel -->
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

								<th style="width: 5%"></th>
							    <th class="dt_col_hide"></th>
								<th style="width: 40%">T-code </th>
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
</section>
