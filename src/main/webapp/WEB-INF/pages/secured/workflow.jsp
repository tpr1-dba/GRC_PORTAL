
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<script>  	
    $(document).ready(function() {
	 function getWLevel(sel){
       alert(sel.value);
    }
    var table;	
    $(document).ajaxStart(function(){
           $("#wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
           $("#wait").css("display", "none");
    });
	$.validator.addMethod("valueNotEquals", function(value, element, arg){
                       return (arg != value);
    }, "Value must not equal arg.");
	 
	$('#employee_form').validate({   
					  rules:{
						"sbuCode":{valueNotEquals: "",rangelength : [2, 2]}											
					},
					messages:{					   
						"sbuCode": { valueNotEquals: "Select OU Code",rangelength : "OU code conatain 2 char only"}						
					},
		    invalidHandler: function(event, validator) {
               var errors = validator.numberOfInvalids();	
	          },
		    errorPlacement: function(error,element) { 
			      if (element.attr("name") == "sbuCode")  //Id of input field
                    error.appendTo('#sbuCode_err');						  
                return true;
            } , 
		     submitHandler: function(form)
             {
                //alert("ok");
				    // param=$("#sbuCode").val();
					 //console.log("ghgfhfh  "+param);
					// table.draw();
					// $("#saveWorkflow").attr("disabled", true);
					// $("#addRow").removeAttr("disabled");
             }
	}); 
              										  
	$.ajax({
			type : 'POST',
			url : "<c:url value="/codeLsit"/>",
			dataType : "json",
			async : false,
			success : handleData,
			error : function(xhRequest,	ErrorText,thrownError) {
																//alert("Not Save");
																console.log(xhRequest.status);
																console.log(ErrorText);
																console.log(thrownError);
															}
			});							
	var distCodeList=[];
    var OUcodeList=[];			 
	function handleData(data){	
						     distCodeList= data.distributorCodelist.map(function(code) {
                                       return {"text": "("+code.deptCode+"),"+code.description,"value":code.deptCode};
                             });
							 $.each(distCodeList, function(i, code) {
								$('#deptCode').append(
										$('<option>').text(code.text).attr('value',
												code.value));
							});
							 
							 OUcodeList= data.codelistSalesman.map(function(code) {
                                       return {"text": code.company,"value":code.compDesc};
                             });
							$('#sbuCode').immybox({
                                        choices: OUcodeList,
										defaultSelectedValue: 'Dharampal Satyapal Limited,[DS HQ C-6-10/67],Noida',
										    formatChoice:function(query) {
                                                                 const reg = new RegExp("(" + query + ")", "i");
                                                                  //return choice => return ();
                                                                 return function(choice){return (
                                                                                   "<div class='mdl-grid mdl-grid--no-spacing'>" +
                                                                                   "<div class='mdl-cell mdl-cell--6-col'>" +
                                                                                   [
                                                                                     choice.text .replace(reg, "<u>$1</u>"),
                                                                                     choice.value
                                                                                    ].join(", ") +
                                                                                    "</div>" +          
                                                                                    "</div>"
                                                                                    );}
                                                                }
										}); 
							/*
						OUcodeList= data.codelistSalesman.map(function(code) {
                                       return {"text": "("+code.company+"), "+ code.compDesc,"value":code.company};
                             });
						$.each(OUcodeList, function(i, oucode) {
						    console.log(oucode.value);
						    if(oucode.value=="55"){							
							    $('#sbuCode').append(
										$('<option selected>').text(oucode.text).attr('value',
												oucode.value));
						     }else{
							    $('#sbuCode').append(
										$('<option>').text(oucode.text).attr('value',
												oucode.value));
							 }						 
							});	
						$('#sbuCode').editableSelect({ value: "55",filter: true });
						$(function() {
                          $("select").keyup(function () {
                                $(this).triggerHandler("change")
                                   });
                        });
                        $('#sbuCode').on('change', function() {
                                alert( this.value+" ff "+$('.es-list li .selected').text() );
                        });*/
						$.each(data.ouLevel, function(i, value) {
								$('#wfLevel').append(
										$('<option>').text(value).attr('value',
												value));
							});							
						$.each(data.statusCode, function(i, value) {
								$('#status').append(
										$('<option>').text(value).attr('value',
												value));
						$("#status").val("A");	
                        $(".istatus").val("A");								
							});
		}
	var param="55";
	$('#sbuCode').val('55');
	/*$('#sbuCode').bind('input', function() { 
	      // alert( $(this).val());        
    });*/
	//$("#sbuCode option[value='55']").attr("selected", "selected");
	$('#cnwf').click(function() {	
	                 $("#saveWorkflow").attr("disabled", true);
                     if ($('#employee_form').valid()) {
					 //console.log("ghgfhfh  "+$("#sbuCode").val());
					 param=$("#sbuCode").val();
					 //console.log("ghgfhfh  "+param);
					 table.draw();
					 $("#saveWorkflow").attr("disabled", true);
					 //$("#cnwf").attr("disabled", true);
					 $("#addRow").removeAttr("disabled");
					 }else{
					    $("#addRow").attr("disabled", true);
                          param="";						
					 }
	});
    $("#saveWorkflow").attr("disabled", true);					 
    var dcodeList=[];
    var ouList=[];	
	function listdata(data) {	
						     dcodeList= data.distributorCodelist.map(function(code) {
                                       return {"text": code.deptCode,"value":code.description};
                             });
									ouList= data.codelistSalesman.map(function(code) {
                                       return {"text": code.company,"value":code.compDesc};
                             });
                            }							 
						
					table = $("#example").DataTable({
						                   select: {
                                            style: 'single'
                                            },
		                                    "bFilter" : false,               
                                            "bLengthChange": false,
											"responsive" : true,
											"bProcessing" : true,
											"bServerSide" : true,
											//Default: Page display length
											"iDisplayLength" : 10,
											//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
											"iDisplayStart" : 0,
											"sAjaxSource" : "workflow/"+param,
                                            "fnServerParams": function (aoData) {
                                                                                aoData.push({ "name": "firstCriteria", "value": param });},											
											"aaSorting" : [ [ 1, "asc" ]],											
											"columnDefs" : [
											         {
														"name" : "hcwfRecid",
														"targets" : 0,
														"visible": false,
														"searchable" : false,
														"orderable" : false,
                                                        "className" : 'dt_col_hide'														
													},
													{
														"name" : "wfLevel",
														"targets" : 1,
														"searchable" : false,
														"orderable" : false,
														"className" : 'dt-body-center'
													}, {
														"name" : "deptCode",
														"targets" : 2,
														"searchable" : false,
														"orderable" : false,
														"className" : 'dt-body-center'
													} , {
														"name" : "status",
														"targets" : 3,
														"searchable" : false,
														"orderable" : false,
														"className" : 'dt-body-center'
													}, {
														"name" : "mandatory",
														"targets" : 4,
														"searchable" : false,
														"orderable" : false,
														"className" : 'dt-body-center'
													},{
													    "data": null,
                                                        "defaultContent": "",
														"name" : "wfDesc",
														"targets" : 5,
														"searchable" : false,
														"orderable" : false,
														"className" : 'dt-body-center'
													} , {
													    "data": null,
                                                        "defaultContent": "",
														"name" : "remarks",
														"targets" : 6,
														"searchable" : false,
														"orderable" : false,
														"className" : 'dt-body-center'
													} ,{
													    "data": null,
                                                        "defaultContent": "<i class=\"fa fa-edit\" aria-hidden=\"true\"></i> <i class=\"fa fa-times\" aria-hidden=\"true\"></i>",
														"name" : "action",
														"targets" : 7,
														"searchable" : false,
														"orderable" : false,
														"className" : 'dt-body-center'
													} ,{													    
                                                        "defaultContent": "n",
														"name" : "chek",
														"targets" : 8,
														"visible": false,
														"searchable" : false,
														"orderable" : false,
                                                        "className" : 'dt_col_hide'
													},
													{
														"name" : "createdOn",
														"targets" : 9,
														"searchable" : false,
														"orderable" : false,
														"className" : 'dt_col_hide'
													}],
											"aoColumns" : [
                                            {
												"mData" : "hcwfRecid"
											},{
												"mData" : "wfLevel"
											}, {
												"mData" : "deptCode"
											}, {
												"mData" : "status"
											},  {
												"mData" : "mandatory"
											},{
												"mData" : "wfDesc"
											}, {
												"mData" : "remarks"
											},{
												"mData" : ""
											},
											{
												"mData" : "n"
											},
											{
												"mData" : "createdOn"
											}],											
											"rowCallback" : function(row, data,dataIndex) {
												// Get row 
												
												//delete data.createdOn;
												var workd=JSON.stringify(data);
												console.log(workd);
												var rowId = data.hcwfRecid;
												$(row).find('input[type="checkbox"]').prop('value',rowId);
												console.log(rowId + "  "+ dataIndex + "  "+ data.hcwfRecid);
												// If row ID is in the list of selected row IDs
												if ($.inArray(rowId,rows_selected) !== -1) {
													$(row).find('input[type="checkbox"]').prop('checked',true);
													$(row).addClass('selected');
												}
											}
										});
									/*	var table = $('#example').DataTable(
										{"bFilter" : false,               
                                       "bLengthChange": false,
                                       "ordering": false}
										);*/
      $("#example").on("mousedown", "td .fa.fa-times", function(e) {
       // table.row($(this).closest("tr")).remove().draw();
		  //$(this).parents("tr").remove();
		  //var row_index = $(this).parent().index();
		  //var row_index= $(this).closest("tr").index();
		   //workflows.splice(row_index-1, 1);
		   //console.log(JSON.stringify(workflows));
		   
                         	  
		            var $row = $(this).closest("tr").off("mousedown");
		            //var $td = $row.find("td :first-child");
		            var $tds = $row.find("td");
		            var hRecid="";
		            $.each($tds, function(i, el) {
                            hRecid = $(this).text();	
		                    console.log(hRecid);
					        if(i==0)
					           return false; 
					    });
			        var workflow={};
                    workflow.hcwfRecid=hRecid;
                    swal({
                        title: "Are you sure?",
                        text: "Your will not be able to recover this data!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonClass: "btn-danger",
                        confirmButtonText: "Yes, delete it!",
                        closeOnConfirm: false
                    },
                     function(isConfirm) {
					 var rownum="Unsaved" ;
                      if (isConfirm) {					
			          if(hRecid!==""){
			            $.ajax({
					            contentType : 'application/json; charset=utf-8',
						        type : 'POST',
						        url : 'deleteWorkflow',
						        data : JSON.stringify(workflow),
						        dataType : 'json',
						        async : false,
						        success : function(data) {table.draw();
						                           // swal("Success!",data,"success");
                                                 	rownum=data;											   
												  // swal("Deleted!", data+" row has been deleted.", "success");
												  },
						        error : function(xhRequest,ErrorText,thrownError) {
                                                                  console.log("xhRequest "+xhRequest.responseText +"  ErrorText "+JSON.stringify(ErrorText) +" thrownError "+JSON.stringify(thrownError) );
																  //swal("Eerror!", ErrorText, "error");		
																  swal({title : "Error",
																	    text : xhRequest.responseText,
																	    type : "error",
																	    showCancelButton : false,
																	    confirmButtonClass : 'btn btn-theme04',
																	    confirmButtonText : 'Danger!'
																        });
																console.log(xhRequest.status);
																console.log(ErrorText);
																console.log(thrownError);
															}
								});
			        }		
		        //$(this).parents("tr").remove();
				$row.remove();
		        //console.log(row_index+"  row_index  "+ $(this).closest("tr").index()+" workflows.length "+workflows.length);
		        if(workflows.length<=0){
		           $("#saveWorkflow").attr("disabled", true);
		        }
		        $("#addRow").removeAttr("disabled");
		        //swal("Deleted!", "Your data has been deleted.", "success");
				  editmode=false;
				 swal("Deleted!", rownum+" row has been deleted.", "success");
				}				
         });
      });
      $("#example").on('mousedown', "i.fa.fa-undo", function(e) {   
   		//$(this).parent()empty().html("<i class=\"fa fa-edit\" aria-hidden=\"true\"> <i class=\"fa fa-check\" aria-hidden=\"true\">");
		//$(this).parent().find("i").addClass("fa");
		//$(this).parent().find("i")[1].addClass("fa fa-edit");
		//$(this).parent().html("<i class=\"fa fa-edit\" aria-hidden=\"true\"> <i class=\"fa fa-check\" aria-hidden=\"true\">");
		//$(this).parent().find("i.fa-save").removeClass("fa");		
        var $row = $(this).closest("tr").off("mousedown");
        var $tds = $row.find("td").not(':first').not(':last');	
        $(this).parent().empty().html("<i class=\"fa fa-edit\" aria-hidden=\"true\"> </i><i class=\"fa fa-times\" aria-hidden=\"true\"></i>");		
		 $.each($tds, function(i, el) {
		$(this).addClass("dt-body-center");
	    switch (i) {
                   case 0:{
	                      console.log("1"+i);
                          $(this).empty();                          						  
	                      $(this).html(tempWorkflow.wfLevel);
                           break;	
						  }
                   case 1:{
	                      console.log("1"+i);						  
                          $(this).empty();		                     
						  $(this).html(tempWorkflow.deptCode);
                          break;	
						  }
                   case 2:{
	                      console.log("1"+i);
                          $(this).empty();	
	                      $(this).html(tempWorkflow.status);						  
                          break;	
						  }	
				    case 3:{
	                      console.log("1"+i);
                          $(this).empty();	
	                      $(this).html(tempWorkflow.mandatory);						  
                          break;	
						  }			  
                   case 4:{
                         $(this).html(tempWorkflow.wfDesc);						
                         break;
						 }
				   case 5:{$(this).html(tempWorkflow.remarks);
                         break;
						 }
					case 7:{
                          $(this).html("n");//.append("<input class=\"form-control\" style=\"width: 100%\" type='text' value=\""+txt+"\">");
                          break;
						  }	
		        }			
        });
		tempWorkflow={};
		editmode=false;
		$("#addRow").removeAttr("disabled");
	 });
    $("#example").on('mousedown.edit', "i.fa.fa-edit", function(e) { 
	   if(!isParam(param,$("#sbuCode").val())){
               swal("Message","Select OU Cede then click Genrate Hirerchy");
			   return;
	   }
        if(editmode){
               swal("Message","Save open row or Undo Change!");
			   return;
	    }	
        $(this).removeClass().addClass("fa fa-check");         
        var $row = $(this).closest("tr").off("mousedown");
        var $tds = $row.find("td").not(':first').not(':last');
        // var $tds = $row.find("td").not(':last');
		var ele="<i class=\"fa fa-undo\" aria-hidden=\"true\"></i>";
		$(this).after(ele);
		//insertAfter(this, ele);	
		   console.log(JSON.stringify(workflows));
		  // $(this).parents("tr").remove();
		
        $.each($tds, function(i, el) {
                    var txt = $(this).text();					
		            switch (i) {
		                        case 0:
								console.log("txt "+txt);
								       //$("#wfLevel").val(txt);
									    tempWorkflow.wfLevel=txt;
									   var v=pad(txt,2);
									   console.log(v);
									  //$('#wfLevel option:contains("+txt+")').prop('selected',true);
									  // $(".wfLevel option:contains(07)").prop('selected',true);
									   $("#wfLevel option").removeAttr("selected");
		                               //$("#wfLevel option[value='01']").attr("selected", "selected");
	                                   $("#wfLevel option[value='"+v+"']").attr("selected", "selected");
									   $("#wfLevel").val(v);
									   var colHtml= $("#newRow").find("td")[1].innerHTML;
                                       $(this).html("").append(colHtml);	
									   $(this).removeClass();
                                       //console.log("%%%%%% "+colHtml);
                                       //$(".hcwfRecid").val($(this).find("input").val());
                                       break;
                                case 1:
								tempWorkflow.deptCode=txt;
								       //$("#deptCode").val(txt);
									   $("#deptCode option").removeAttr("selected");
		                               $("#deptCode option[value='"+txt+"']").attr("selected", "selected");
	                                  $("#deptCode").val(txt);
									  var colHtml= $("#newRow").find("td")[2].innerHTML;
                                       $(this).html("").append(colHtml);	
                                       //console.log("%%%%%% "+colHtml);
                                       //$(".hcwfRecid").val($(this).find("input").val());
                                       break;	
                                case 2:
								      tempWorkflow.status=txt;
								      // $("#status").val(txt);
									  $("#status option").removeAttr("selected");
		                               //$("#wfLevel option[value='01']").attr("selected", "selected");
	                                   $("#status option[value='"+txt+"']").attr("selected", "selected");
									   $("#status").val(txt);
	                                   var colHtml= $("#newRow").find("td")[3].innerHTML;
                                       $(this).html("").append(colHtml);	
                                       //console.log("%%%%%% "+colHtml);
                                       //$(".hcwfRecid").val($(this).find("input").val());
                                       break;
								case 3:
								      tempWorkflow.mandatory=txt;
								      // $("#status").val(txt);
									  $("#mandatory option").removeAttr("selected");
		                               //$("#wfLevel option[value='01']").attr("selected", "selected");
	                                   $("#mandatory option[value='"+txt+"']").attr("selected", "selected");
									   $("#mandatory").val(txt);
	                                   var colHtml= $("#newRow").find("td")[4].innerHTML;
                                       $(this).html("").append(colHtml);	
                                       //console.log("%%%%%% "+colHtml);
                                       //$(".hcwfRecid").val($(this).find("input").val());
                                       break;		   
                                case 4:
								       tempWorkflow.wfDesc=txt;
                                       $(this).html("").append("<input class=\"form-control\" style=\"width: 100%\" type='text' value=\""+txt+"\">");
                                       break;
								case 5:
								        tempWorkflow.remarks=txt;
                                       $(this).html("").append("<input class=\"form-control\" style=\"width: 100%\" type='text' value=\""+txt+"\">");
                                       break;
								/*case 6:
                                       $(this).html("y");//.append("<input class=\"form-control\" style=\"width: 100%\" type='text' value=\""+txt+"\">");
                                       break;*/	   
						}
             // $(this).html("").append("<input style=\"width: 100%\" type='text' value=\""+txt+"\">");
            });
			editmode=true;
        $("#addRow").attr("disabled",true);
      });

      $("#example").on('mousedown', "input", function(e) {
        e.stopPropagation();
      });
   
   
   
    $("#example").on('mousedown.save', "i.fa.fa-check", function(e) {
	  if(!isParam(param,$("#sbuCode").val())){
               swal("Message","Select OU Cede then click Genrate Hirerchy");
			   return;
	   }
        //$(this).parent().empty();//find("i").removeClass('fa').addClass("fa fa-edit")
       // $(this).parent().html("<i class="fa fa-check" aria-hidden=\"true\">");
	   var empty = false;
        //$(this).parent().empty();//find("i").removeClass('fa').addClass("fa fa-edit")
       // $(this).parent().html("<i class="fa fa-check" aria-hidden=\"true\">");
	   // table.find("tr")[0].remove();
	   //$("#example tr:eq(0)").remove();
	   $(".fa-undo").remove();

	   var workflow={};
	  // workflow.sbuCode=$("#sbuCode").val();
	    workflow.sbuCode=param;
	    workflow.hcwfRecid="";
	    console.log("fbddgfd  "+$("#wfLevel").val());
	    var input = $(this).parents("tr").find('input[type="text"]');
        input.each(function(){
			if(!$(this).val()){
				$(this).addClass("error");
				empty = true;
			} else{
                $(this).removeClass("error");
            }
		});
		$(this).parents("tr").find(".error").first().focus();
	    if(empty)return;
	    $('#example .dataTables_empty').hide();
		$(this).removeClass().addClass("fa fa-edit");
		//console.log("$(this).siblings()  "+JSON.stringify($(this).siblings()));
		$(this).siblings().empty();
        var $row = $(this).closest("tr");
        var $tds = $row.find("td").not(':first');//.not(':last');        
        $.each($tds, function(i, el) {
		$(this).addClass("dt-body-center");
	    switch (i) {
                   case 0:{
				          
				          //console.log("wlevel"+wlevel+" ww "+$("#wfLevel").val());
				          //wlevel =(wlevel==""||wlevel==undefined)?$("#wfLevel").val():wlevel;
	                      console.log("1"+i);
                          $(this).empty();                          						  
	                      $(this).html($("#wfLevel").val());
						  workflow.wfLevel=$("#wfLevel").val();
						  wlevel="";
                           break;	
						  }
                   case 1:{
	                      console.log("1"+i);
						  //console.log("dCode"+dCode+" ff "+$("#deptCode").val());
						   //dCode=(dCode==""||dCode==undefined)?$("#deptCode").val():dCode;
                          $(this).empty();	
	                      //$(this).html($("#deptCode").val());
						  $(this).html($("#deptCode").val());
						  workflow.deptCode=$("#deptCode").val();
						  dCode="";
                          break;	
						  }
                   case 2:{
				         //sts=(sts==""||sts==undefined)?$("#status").val():sts;
	                      console.log("1"+i);
                          $(this).empty();	
	                      $(this).html($("#status").val());
						  workflow.status=$("#status").val();
                          break;	
						  }	
				   case 3:{
				         //sts=(sts==""||sts==undefined)?$("#status").val():sts;
	                      console.log("1"+i);
                          $(this).empty();	
	                      $(this).html($("#mandatory").val());
						  workflow.mandatory=$("#mandatory").val();
                          break;	
						  }	
                   case 4:{
                         console.log("d"+i);
                         var txt = $(this).find("input").val();		   
                         $(this).html(txt);
						 workflow.wfDesc=txt;
                         break;
						 }
				   case 5:{
                         console.log("d"+i);
                         var txt = $(this).find("input").val();		   
                         $(this).html(txt);
						 workflow.remarks=txt;
                         break;
						 }
					case 7:{
                          $(this).html("y");//.append("<input class=\"form-control\" style=\"width: 100%\" type='text' value=\""+txt+"\">");
                          break;
						  }	 
		
		        }			
        });
		/*console.log(JSON.stringify(workflow));	
		//workflows.push(workflow);
		var row_index= $(this).closest("tr").index();
		   workflows.splice(row_index-1, 1);
		   //array.splice(2, 0, "three");
		   workflows.splice(row_index-1, 1, workflow);
		if(workflows.length>0){
		$("#saveWorkflow").removeAttr("disabled");
		}*/
		$("#saveWorkflow").removeAttr("disabled");
		$("#addRow").removeAttr("disabled"); 
        editmode=false;	
      });
	  
	  $('#saveWorkflow').click(function() {
	   if(!isParam(param,$("#sbuCode").val())){
               swal("Message","Select OU Cede then click Genrate Hirerchy");
			   return;
	    }
		if(editmode){
               swal("Message","Save open row or Undo Change!");
			   return;
	    }
           //console.log(JSON.stringify(workflows));
		   var count=0; 
               $('#example > tbody  > tr').each(function() {
			          var chek="";
			          var workflow={};
	                  workflow.sbuCode=param;
			          var $tds = $(this).find("td");
	                  //console.log("iii "+count++);
					  $.each($tds, function(i, el) {
					     //console.log($(this).text());
						 var txt=$(this).text();
					     switch (i) {
						 case 0:{
				                    workflow.hcwfRecid=txt;
						            break;	
						           }
                            case 1:{
				                    workflow.wfLevel=txt;
						            break;	
						           }
                            case 2:{
						            workflow.deptCode=txt;
						            break;	
						           }
                            case 3:{
				                    workflow.status=txt;
                                    break;	
						         }
                            case 4:{
				                    workflow.mandatory=txt;
                                    break;	
						         }								 
                            case 5:{
            					    workflow.wfDesc=txt;
                                    break;
						         }
				            case 6:{
                                    workflow.remarks=txt;
                                    break;
						         }
						    case 8:{
							console.log("case 8"+txt);
                                    chek=txt;
                                    break;
						         }		 
		
		                 }			
						 console.log("workflow :: "+JSON.stringify(workflow));
					  });
					  
					  if(chek==="y"){
					     workflows.push(workflow);						 
					  }
	           });	
               console.log(" workflows "+JSON.stringify(workflows));			   
		        $.ajax({
					    contentType : 'application/json; charset=utf-8',
						type : 'POST',
						url : 'addWorkflows',
						data : JSON.stringify(workflows),
						dataType : 'json',
						async : false,
						success : function(data) {table.draw();
						                            editmode=false;
						                            $("#saveWorkflow").attr("disabled", true);
													$("#addRow").removeAttr("disabled");
													workflows=[];
						                            swal("Success!",data,"success");
												  },
						error : function(xhRequest,ErrorText,thrownError) {
                                                                  console.log("xhRequest "+xhRequest.responseText +"  ErrorText "+JSON.stringify(ErrorText) +" thrownError "+JSON.stringify(thrownError) );
																  //swal("Eerror!", ErrorText, "error");		
																  swal({title : "Error",
																	    text : "Some problem in saving data",
																	    type : "error",
																	    showCancelButton : false,
																	    confirmButtonClass : 'btn btn-theme04',
																	    confirmButtonText : 'Danger!'
																        });
																console.log(xhRequest.status);
																console.log(ErrorText);
																console.log(thrownError);
															}
														});
	  });
	   //$('<div class="addRow"><button id="addRow">Add New Row</button></div>').insertAfter('#example');

      // add row
      $('#addRow').click(function() {   
         if(!isParam(param,$("#sbuCode").val())){
               swal("Message","Select OU Cede then click Genrate Hirerchy");
			   return;
	     }
        var rowHtml = $("#newRow").find("tr")[0].outerHTML;		
        //console.log(rowHtml);
        //table.row.add($(rowHtml)).draw();
		$('#example > tbody').append($(rowHtml));
		$("#wfLevel option").removeAttr("selected");
		$("#wfLevel option[value='01']").attr("selected", "selected");
		$("#status option").removeAttr("selected");
		$("#status option[value='A']").attr("selected", "selected");
		$("#mandatory option").removeAttr("selected");
		$("#mandatory option[value='Y']").attr("selected", "selected");		
		$("#deptCode option").removeAttr("selected");
		$("#deptCode option[value='(ADM),ADMINISTRATION']").attr("selected", "selected");		
		$("#addRow").attr("disabled", true);
        editmode=true;		
        });
	 $("#example").on('mousedown', "input", function(e) {
        e.stopPropagation();
      });  
});
</script>
<section class="wrapper site-min-height">
	<h4>
		<li class="fa fa-angle-right">Work FLow</li>
		<!-- <li class="fa fa-angle-right">Employee Details</li>
		<li class="fa fa-angle-right">Employee</li> -->
	</h4>
	<div id="wait"
		style="display: none; width: 69px; height: 89px; border: 1px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
		<img src='resources/assets/img/ajax-loader.gif' width="64" height="64" /><br>Loading..
	</div>

	<div class="col-lg-12 column">
		<div class="portlet">
			<div class="portlet-header">
				<span class="ui-text"> Create New Workflow</span>
			</div>
			<div class="portlet-content form-group">
				<form id="employee_form">
					<div class="showback">
						<button class="btn btn-theme02" type="button" id="cnwf">
							<i class="glyphicon glyphicon-ok"></i> Genrate Hirerchy
						</button>
					</div>
					<table
						class="table table-bordered table-striped table-condensed cf"
						id="newemp" style="padding: 0.1em;">

						<tr>
							<th style="width: 20%"><label for="sbuCode" class="required">Org.
									Unit </label></th>
							<td style="width: 50%">
							  <input id="sbuCode" name="sbuCode"
								placeholder="Select OU Code"
								class="form-control immybox immybox_witharrow" type="text">
								<!--<select name="sbuCode" id="sbuCode"
					              class="form-control sbuCode">								  
				                </select>-->
							</td>

							<td style="width: 30%"><span id="sbuCode_err"><label
									class="error"></label></span></td>

						</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- /content-panel -->
	</div>
	<table id="newRow" style="display: none">
		<tbody>
			<tr style="width: 100%">
			    <td class="dt_col_hide"></td>
				<td style="width: 10%"><select name="wfLevel" id="wfLevel" onchange="getWLevel(this);"
					class="form-control wfLevel">
				</select></td>
				<td style="width: 25%"><select id="deptCode" name="deptCode" onchange="getDptCode(this);"
					class="form-control">
				</select></td>
				<td style="width: 8%"><select id="status" name="status" onchange="getSatus(this);"
					class="form-control">
				</select></td>
				<td style="width: 8%"><select id="mandatory" name="mandatory" onchange="getMandatory(this);"
					class="form-control">
					<option selected value="Y">Y</option>
					<option value="N">N</option>
				</select></td>
				<td style="width: 20%"><input type="text" id="wfDesc"
					name="wfDesc" class="form-control" /></td>
				<td style="width: 20%"><input type="text" id="remarks"
					name="remarks" class="form-control" /></td>
				<td style="width: 10%"><i class="fa fa-check"
					aria-hidden="true"></i> <i class="fa fa-times" aria-hidden="true"></i>
				</td>
				<td class="dt_col_hide"></td>
				<td class="dt_col_hide"></td>
			</tr>
		</tbody>
	</table>
	<div class="row mt">
		<div class="col-lg-12 column">
			<div class="portlet">
				<div class="portlet-header">
					<span class="ui-text">Work Flow Details</span>
				</div>

				<div class="portlet-content">
					<div class="showback">
						<button id="addRow" type="button" class="btn btn-info add-new">
							<i class="fa fa-plus"></i> Add New
						</button>
						<button id="saveWorkflow" type="button" class="btn btn-info add-new"
							disabled>
							<i class="glyphicon glyphicon-ok "></i> Save Hirerchy
						</button>
					</div>
					<table id="example" class="display select responsive no-wrap"
						cellspacing="0" width="100%">
						<thead>
							<tr style="width: 100%">
							    <th class="dt_col_hide"></th>
								<th style="width: 10%">OU Level</th>
								<th style="width: 25%">Dept Code</th>
								<th style="width: 8%">Status</th>
								<th style="width: 8%">Mandatory</th>
								<th style="width: 20%">Desc</th>
								<th style="width: 20%">Remarks</th>
								<th style="width: 10%">Action</th>	 
								<th class="dt_col_hide"></th>	
                                <th class="dt_col_hide"></th>								
							</tr>
						</thead>						
					</table>
				</div>
			</div>
		</div>
		<!-- /col-lg-12 -->
	</div>


</section>




