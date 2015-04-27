<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.proper.uip.common.utils.JsonUtil"  %>
<%
String path = request.getContextPath();
String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>经办人设置</title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>
<!--框架必需end-->
<script type="text/javascript" src="<%=contextPath%>file/script/proper.js"></script>
<!-- 条件过滤器 start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/filter.js"></script>
<!-- 条件过滤器 end -->

<!-- 树组件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/tree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>file/libs/js/tree/ztree/ztree.css"></link>
<!-- 树组件end -->
<!-- 树形下拉框start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/selectTree.js"></script>
<!-- 树形下拉框end -->
<!-- 条件过滤器2 start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/condition.js"></script>
<!-- 条件过滤器2 end -->

<!-- 表单验证start -->
<script src="<%=contextPath%>file/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="<%=contextPath%>file/libs/js/form/validation.js" type="text/javascript"></script>
<!-- 表单验证end -->
<script>
var linkDataArray =  <%=JsonUtil.toJSON(request.getAttribute("linkData"))%>;
var entity = <%=JsonUtil.toJSON(request.getAttribute("entity"))%>;

	function initComplete(){
		
	 	$("input:radio[name=mode][value='"+ entity.mode +"']").attr("checked",true);
	 	$("input:radio[name=mode]").addClass("validate[required]");
	 	
		$("input:radio[name=mode]").click(function(){
			if($(this).val()=="autoMode"){
				$("#sel-info").mask();
				$("#sel-filter-rule").attr("disabled","disabled");
				$("#sel-auto-rule").attr("disabled",false) ;
				$("#sel-filter-rule").removeClass("validate[required]") ;
				$("#sel-auto-rule").addClass("validate[required]") ;
   				$("#sel-filter-rule").render();
   				$("#sel-auto-rule").render();
			}
			else{
				$("#sel-info").unmask();
				$("#sel-filter-rule").attr("disabled",false);
				$("#sel-auto-rule").attr("disabled",true);
				$("#sel-filter-rule").addClass("validate[required]") ;
				$("#sel-auto-rule").removeClass("validate[required]") ;
   				$("#sel-filter-rule").render();
   				$("#sel-auto-rule").render();
			}
			$(this).attr("checked",true);
	 	 });
	 	 $("input:radio[name=mode][value='"+ entity.mode +"']").click();
		if(typeof linkDataArray.length != "undefined")
		{
			var list = eval( "(" + linkDataArray + ")");
			var identityLinkList = new Array();
			var identityLink;
			for(var i=0;i<list.length;i++){
			 	identityLink = new IdentityLink( list[i].personId,list[i].orgId, list[i].jobId,list[i].type,list[i].name);
			 	identityLinkList.push(identityLink);
			}
			transactionTo(identityLinkList);
		}
		//选人过滤规则
		 $.post("<%=basePath%>definitions/assignment/getFilterRules",{},function(result){  
			//赋给data
		    $("#sel-filter-rule").data("data",result);
		    
		    $("#sel-filter-rule").setValue(entity.filterRule);	
		    //刷新下拉框
		    $("#sel-filter-rule").render(); 
		    },"json");
		//自动选人规则
		 $.post("<%=basePath%>definitions/assignment/getAutoRules",{},function(result){  
			//赋给data
		    $("#sel-auto-rule").data("data",result);
		    
		    $("#sel-auto-rule").setValue(entity.autoRule);	
		    //刷新下拉框
		    $("#sel-auto-rule").render(); 
		    },"json");	
		
	
		//机构
		$('#org-button').click(function() {
				var orgs =  $(".select2-info[type='org']");
				var selected = [];
				if(orgs && orgs.length != 0){
					for(var i=0;i<orgs.length;i++)
					{
						var org = orgs[i];
						selected.push(org.attributes["orgId"].nodeValue);
					}
				}
// 				var orgs = $('#linkOrganization').val();
// 				var selected = [];
// 				if (orgs && orgs != '') {
// 					selected = orgs.split(';');
// 				}
				proper.organizationchoose({
				                userId:'${userId}',
							    basePath:'<%=contextPath%>',
								checkbox:true,//默认false
								selectIds:selected,//已选中的ID
								selected:function(orgs){
									var oldOrgs =  $(".select2-info[type='org']");
									for(var i=0;i<oldOrgs.length;i++){
										$(oldOrgs[i]).remove();
									}
									getIdentityLinkList("org",orgs);
									var orgText=[];
									var orgId=[];
									var orgstr="";
									if(orgs.length>0){
										for(var i=0;i<orgs.length;i++){
											var o = orgs[i];
											orgText.push(o.name);
											orgId.push(o.id);
										}
								   }
								   $('#linkOrgName').val(orgText.join(';'));
								   $('#linkOrgNames').val(orgText.join(';'));
								//   $('#forgid').val(orgId.join(';'));
								   $('#linkOrganization').val(orgId.join(';'));
								   return true;
								},
								
							},'orgs');
						});
			//---机构over
			//----职务
			$('#job-button').click(function(){
			
					var jobs =  $(".select2-info[type='job']");
					var selected = [];
					if(jobs && jobs.length != 0){
						for(var i=0;i<jobs.length;i++)
						{
							var job = jobs[i];
							selected.push(job.attributes["jobId"].nodeValue);
						}
					}
// 							var jobs = $('#linkJob').val();
// 							var selected = [];
// 							if (jobs && jobs != '') {
// 								selected = jobs.split(';');
// 							}
					proper.jobchoose({
						basePath:'<%=contextPath%>',
						userId:'${userId}',
					    title:'职务选择器',
// 					    basePath:'<%=contextPath%>',
// 					    url:'<%=basePath%>definitions/assignment/range/job',
						checkbox:true,//默认false
						selectIds:selected,//已选中的ID
// 						selUrl:'<%=basePath%>definitions/assignment/range/job/selected',
						selected:function(jobs){
							var oldJobs =  $(".select2-info[type='job']");
							for(var i=0;i<oldJobs.length;i++){
								$(oldJobs[i]).remove();
							}
							getIdentityLinkList("job",jobs);
							var jobText=[];
							var jobId=[];
							if(jobs.length>0){
								for(var i=0;i<jobs.length;i++){
									var o = jobs[i];
									jobText.push(o.name);
									jobId.push(o.id);
									//job=job+","+o.id+":"+o.name;
								}
						   }
						   $('#linkJobName').val(jobText.join(';'));
						   $('#linkJobNames').val(jobText.join(';'));
						   $('#linkJob').val(jobId.join(';'));
						}
					});
				});
						
		$('#person-button').click(function(){
	
				var persons =  $(".select2-info[type='person']");
				var selected = [];
				if(persons && persons.length != 0){
					for(var i=0;i<persons.length;i++)
					{
						var person = persons[i];
						selected.push(person.attributes["personId"].nodeValue);
					}
				}
// 				var pId = $('#linkPerson').val();
// 				var selected = [];
// 				if(pId && pId != ""){
// 					selected = pId.split(';');
// 				}
				proper.personchoose({
				userId:'${userId}',
					basePath:'<%=contextPath%>',
					checkbox:true,
					selectIds:selected,
					selected:function(persons){
					
								var oldPersons =  $(".select2-info[type='person']");
								for(var i=0;i<oldPersons.length;i++){
									$(oldPersons[i]).remove();
								}
								getIdentityLinkList("person",persons);
								var pText=[];
								var pId=[];
								var pers="";
							    for(var i=0;i<persons.length;i++){
									var p = persons[i];
									pText.push(p.name);
									pId.push(p.personId);
									pers=pers+","+p.personId+":"+p.name;
								}
								
								$('#linkPersonName').val(pText.join(';'));
								$('#linkPersonNames').val(pText.join(';'));
							  $('#linkPerson').val(pId.join(';'));
					}
				});
	
		});
		$('#position-button').click(function(){
		var diag = new top.Dialog();
			$.extend(diag,myproper.defaultGridDialogOptions);
			diag.Title = "职位选择器";
			diag.Width = 650;
			diag.Height = 400;
			diag.properType="add";
			diag.URL="<%=path%>/definitions/assignment/positionlink",
		    diag.OKEvent = function(){
		   		 diag.innerFrame.contentWindow.dealPositionLink();
		   		 diag.close();
		// 	    	var zTree = $.fn.zTree.getZTreeObj("orgTree");
		// 		    得到选中的数据集
		// 		    var checkedNodes = zTree.getCheckedNodes(true);
	
		    };
		    diag.show();
		    
		// 		top.Dialog.open({
		// 				URL:"<%=path%>/definitions/assignment/positionlink",
		// 				Title:"查看",
		// 				Width:650,
		// 				Height:400,
		// 				ShowButtonRow:true,
		// 				CancelButtonText:"关闭",
		// 				ShowOkButton:false
		// 			});
			    //获取json数据
		// 	   $.post("<%=basePath %>/definitions/assignment/org/tree",{},function(result){
		// 	        赋给data属性
		//         $.fn.zTree.init($("#orgTree"), orgSetting, result.orgs);
		// 		$("#orgTree").render();
		// 	    },"json");
		});
		$('.select2-info').live("click",function(){
			$(this).remove();
		});
		
	}

	function IdentityLink(personId, orgId, jobId,type,name) {
	    this.personId = personId;
	    this.orgId = orgId;
	    this.jobId = jobId;
	    this.type = type;
	    this.name = name;
	}
	function getIdentityLinkList(type,oldList){
		var identityLinkList = new Array();
		var identityLink ;
		switch (type)
		{
			case "person":
			{
				 for(var i=0;i<oldList.length;i++)
				 {
					person = oldList[i];
				 	identityLink = new IdentityLink( person.personId,"", "","person",person.name);
				 	identityLinkList.push(identityLink);
				 }
			 	 break;
			}
			case "org":
				{
				 for(var i=0;i<oldList.length;i++)
				 {
					org = oldList[i];
				 	identityLink = new IdentityLink("" ,org.id, "","org",org.name);
				 	identityLinkList.push(identityLink);
				 }
			 	 break;
			}
			case "job":
				{
				 for(var i=0;i<oldList.length;i++)
				 {
					job = oldList[i];
				 	identityLink = new IdentityLink( "","", job.id,"job",job.name);
				 	identityLinkList.push(identityLink);
				 }
			 	 break;
			}
		}
		transactionTo(identityLinkList);
	}
	function transactionTo(identityLinkList){
		var identityLink ;
		
		var htmlStr = "";
		for(var i=0;i<identityLinkList.length;i++)
		{
			identityLink = identityLinkList[i];
			var identityLinkId = identityLink.type+identityLink.personId+identityLink.orgId+identityLink.jobId;
			if($("#"+identityLinkId).length==0)
			{
				htmlStr += "<li class='select2-info' ";
				htmlStr += "id='"+identityLinkId+"' ";
				htmlStr += "type='"+identityLink.type+"' ";
				htmlStr += "personId='"+identityLink.personId+"' ";
				htmlStr += "orgId='"+identityLink.orgId+"' ";
				htmlStr += "jobId='"+identityLink.jobId+"' ";
				htmlStr += ">"+identityLink.name+"</li>";
			}
		}
		$("#selectContainer").append(htmlStr);
	}
	
</script>
<style type="">
.select2-container, .select2-drop, .select2-search, .select2-search input {
-moz-box-sizing: border-box;
-ms-box-sizing: border-box;
-webkit-box-sizing: border-box;
-khtml-box-sizing: border-box;
box-sizing: border-box;
}
.select2-container {
position: relative;
display: inline-block;
/* top:20px; */
zoom: 1;
vertical-align: top;
}
.select2-container-multi .select2-choices {
background-color: #fff;
-webkit-border-radius: 4px;
-moz-border-radius: 4px;
border-radius: 4px;
border: 1px solid #dadfe6;
margin: 0;
padding-bottom:10px;
/* padding: 0 75px 0 0; */
cursor: text;
overflow: hidden;
height: auto !important;
height: 1%;
position: relative;
min-height: 26px;
}
.select2-container-multi .select2-choices .select2-operate {
float: right;
position: relative;
z-index: 10;
height:22px;
padding: 5px;
}
.select2-container-multi .select2-choices li {
float: left;
list-style: none;
}
.select2-container-multi .select2-choices .select2-search-field {
white-space: nowrap;
margin: 0;
padding: 0;
width: 20px;
}
.select2-container-multi .select2-choices .select2-search-field input {
color: #666;
background: transparent !important;
font-family: 'Microsoft Yahei', sans-serif;
font-size: 100%;
height: 26px;
min-height: 26px;
padding: 5px;
margin: 1px 0;
outline: 0;
border: 0;
overflow: visible;
-webkit-box-sizing: content-box;
-moz-box-sizing: content-box;
box-sizing: content-box;
-webkit-box-shadow: none;
-moz-box-shadow: none;
-o-box-shadow: none;
box-shadow: none;
}
.select2-container-multi .select2-choices .select2-operate .operate-btn {
padding-left: 8px;
padding-right: 8px;
}
.select2-container-multi .select2-choices .select2-operate a {
margin-left: 5px;
}
.select2-container-multi .select2-info {
width: 150px;
height: 22px;
line-height: 22px;
border:1px solid  #80c0e7 ;
float: left;
display: inline;
margin: 4px;
cursor: pointer;
cursor: hand;
padding-right: 8px!important;
padding-left: 5px!important;
background-position: 145px 40%;
background-repeat: no-repeat;
background-image: url(<%=contextPath%>file/libs/skins/blue/form/filterCloseHover.gif);
}
.operate-btn {
border-radius: 4px;
display: inline-block;
text-align: center;
height: 20px;
line-height: 20px;
padding: 5px;
color: #82939e;
background-color: #ebedf0;
white-space: nowrap;
-webkit-transition: background linear 0.1s;
-moz-transition: background linear 0.1s;
-o-transition: background linear 0.1s;
transition: background linear 0.1s;
}
element.style {
}
user agent stylesheeti, cite, em, var, address, dfn {
font-style: italic;
}
Pseudo ::before element
.glyphicon-user:before {
content: "\e008";
}
.filterDIV{
position: relative;
display: inline-block;
vertical-align: top;
}
.wholeTable{
width:100%;
margin-top:20px;
}
.wholeTable{
padding:5px;
}
</style>
</head>
<body>
	<div>
	<form id="myform">
<!-- 	<div class="page-top"></div> -->
<!-- 	<div id="condition-1" class="condition" multiMode="true"></div> -->
<!-- 	<div class="filter" multiMode="true" selectedValue="1,2,3,4"   data='{"list":[{"value":"1","key":"员工1"},{"value":"2","key":"员工2"},{"value":"3","key":"员工3"},{"value":"4","key":"员工4"}]}'></div> -->
	<table class="wholeTable"  cellpadding="10">
		<tr>
			<td width="100px">设置方式</td>
			<td>
				<input type="radio" id="manualMode" class="validate[required] radio" name="mode" value="manualMode" /><label for="manualMode" class="hand">手动设置</label>
				
	    		<input type="radio" id="autoMode" class="validate[required] radio" name="mode" value="autoMode" /><label for="autoMode" class="hand">自动设置</label>
			</td>
		</tr>
		<tr>
			<td width="100px">经办人设置</td>
			<td>
				<div class="select2-container select2-container-multi" id="sel-info" style="width: 100%">    
					<ul class="select2-choices" id="selectContainer">
						<li class="select2-operate">
							<a href="javascript:;"  id="person-button"  class="operate-btn"><i class="glyphicon-user">选择人员</i></a>
						</li>  
						<li class="select2-operate">
							<a href="javascript:;"  id="org-button" class="operate-btn"><i class="glyphicon-user">选择机构</i></a>
						</li>  
						<li class="select2-operate">
							<a href="javascript:;"  id="job-button"  class="operate-btn"><i class="glyphicon-user">选择职务</i></a>
						</li>  
						<li class="select2-operate">
							<a href="javascript:;"  id="position-button"  class="operate-btn"><i class="glyphicon-user">选择职位</i></a>
						</li>
					</ul>
					 		<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${processDefinitionId}"/>
					 		<input type="hidden" id="taskDefinitionKey" name="taskDefinitionKey" value="${taskDefinitionKey}"/>
					 		<input type="hidden" id="strategyId" name="strategyId" value="${strategyId}"/>
					 		<input type="hidden" id="scenarioItemId" name="scenarioItemId" value="${scenarioItemId}"/>
					 		<input type="hidden" name="linkPerson" id="linkPerson" value="${entity.linkPerson}" />
							<input type="hidden" name="linkPersonName" id="linkPersonName" value="${entity.linkPersonName}" />
							<input type="hidden" name="linkOrganization" id="linkOrganization" value="${entity.linkOrganization}" />
							<input type="hidden" name="linkOrgName" id="linkOrgName" value="${entity.linkOrgName}" />
							<input type="hidden" name="linkJob" id="linkJob" value="${entity.linkJob}" />
							<input type="hidden" name="linkJobName" id="linkJobName" value="${entity.linkJobName}" />
							<input type="hidden" id="linkData" name="linkData"/>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				选人过滤规则
			</td>
			<td>
				<div class="filterDIV">
					<select prompt="选人过滤规则" id="sel-filter-rule" name="filterRule"  selWidth="250" boxHeight="200"></select>
					<span style="color:red;">[注]只有在经办人中包含本部门人员时才可选择“只允许选择本部门经办人”,否则会造成流程走失。</span>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				自动选人规则
			</td>
			<td>
				<div class="filterDIV">
					<select  id="sel-auto-rule" name="autoRule"  selWidth="250" openDirection="bottom" boxHeight="150"></select>
				</div>
			</td>
		</tr>
	</table>
	</form>
	</div>
	<script type="text/javascript">
		function addLink(){
			var processDefinitionId = $("#processDefinitionId").val();
			var taskDefinitionKey = $("#taskDefinitionKey").val();
			var strategyId = $("#strategyId").val();
			var scenarioItemId = $("#scenarioItemId").val();
			var linkPerson = $("#linkPerson").val();
			var linkOrganization = $("#linkOrganization").val();
			var linkJob = $("#linkJob").val();
			var linkPersonName = $("#linkPersonName").val();
			var linkOrgName = $("#linkOrgName").val();
			var linkJobName = $("#linkJobName").val();
			$.ajax({type: 'POST',
				url: '<%=basePath %>/definitions/assignment/saveAssignment',
				data:{
					  processDefinitionId:processDefinitionId,
					  taskDefinitionKey:taskDefinitionKey,
					  strategyId:strategyId,
					  scenarioItemId:scenarioItemId,
					  linkPerson:linkPerson,
					  linkOrganization:linkOrganization,
					  linkJob:linkJob,
					  linkPersonName:linkPersonName,
					  linkOrgName:linkOrgName,
					  linkJobName:linkJobName
					  },
				success:function(result){
					if(result == "SYS_SUCCESS"){
						top.Dialog.alert("成功提交",function(){ 
							parent.taskDefinitionGrid.loadData();
							parent.Dialog.close();
						});
					}else{
						top.Dialog.alert("保存失败！");
					}
				},
				dataType: "json",
				async:false
			});	
		}
	function valids() {
		var valid = $('#myform').validationEngine({
			returnIsValid : true
		});
		return valid;
	}
	function getParams() {
		getSelectedData();
		var params = $('#myform').serialize();
		return params;
	}
	function getSelectedData(){
		var selectedList = $(".select2-info");
		var selectedData = [];
		var selectRow;
		for(var i=0;i<selectedList.length;i++)
		{
			selectRow = selectedList[i];
			selectedData.push(new IdentityLink(selectRow.attributes["personId"].nodeValue, selectRow.attributes["orgId"].nodeValue, selectRow.attributes["jobId"].nodeValue,selectRow.attributes["type"].nodeValue,selectRow.innerHTML));
		}
		$("#linkData").val(JSON.stringify(selectedData));
	}
	</script>
</body>
</html>