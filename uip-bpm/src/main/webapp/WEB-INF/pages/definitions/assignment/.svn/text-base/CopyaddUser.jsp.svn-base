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
<title></title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>
<!--框架必需end-->

<!-- 日期控件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->

<!-- 树组件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/tree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>file/libs/js/tree/ztree/ztree.css"></link>
<!-- 树组件end -->

<!-- 树形下拉框start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/selectTree.js"></script>
<!-- 树形下拉框end -->

<!-- 表单验证start -->
<script src="<%=contextPath%>file/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="<%=contextPath%>file/libs/js/form/validation.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 表单异步提交start -->
<script src="<%=contextPath%>file/libs/js/form/form.js" type="text/javascript"></script>
<!-- 表单异步提交end -->


<!-- 树组件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/tree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>file/libs/js/tree/ztree/ztree.css"></link>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>file/system/workdesk/skin/style.css"></link>
<!-- 树组件end -->

<!-- 树形双选器start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/listerTree.js"></script>
<!-- 树形双选器end -->

<!-- 树形下拉框start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/selectTree.js"></script>
<!-- 树形下拉框end -->
</head>
<body>
 <div>
<form action="" id="queryForm" method="post">
		<table>
			<tr>
				<td>机构名称：</td>
				<td>
					<input type="text" id="raName" name="raName"/>
					<input type="text" style="width:2px;display:none;"/>
				</td>
				<td>用户名：</td>
				<td>
					<input type="text" id="userName" name="userName"/>
					<input type="text" style="width:2px;display:none;"/>
				</td>
				<td width="4"></td>
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
				<td width="4"></td>
				<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置查询</span></button></td>
			</tr>
		</table>
</form>
<table class="tableStyle" formMode="line">
 	<tr>
 		<td>设置用户经办人：</td>
 		<td>
	 		<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${processDefinitionId}"/>
	 		<input type="hidden" id="taskDefinitionKey" name="taskDefinitionKey" value="${taskDefinitionKey}"/>
	 		<input type="hidden" id="strategyId" name="strategyId" value="${strategyId}"/>
	 		<input type="hidden" id="scenarioItemId" name="scenarioItemId" value="${scenarioItemId}"/>
	 		
	 		<div class="listerTree" id="listerTree1"  listerHeight="300"></div>
	 		
 		</td>
 	</tr>
 	<tr>
		<td colspan="2">
		<input type="button" value="确定" onclick="addUserIdentityLink()"/>
		<input type="button" value="取消" onclick="parent.Dialog.close();"/>
		</td>
	</tr>
 </table>
</div>
<!-- 异步提交start -->
<script type="text/javascript">
	
	function initComplete(){
    	showProgressBar();

	    //获取json数据
	   $.post("<%=basePath %>/definitions/assignment/usertree/query",{"processDefinitionId":"${processDefinitionId}","taskDefinitionKey":"${taskDefinitionKey}","scenarioItemId":"${scenarioItemId}"},function(result){
	        //赋给data属性
	    $("#listerTree1").data("data",result);
	    $("#listerTree1").render(); 
	    },"json");
	}

	function addUserIdentityLink(){
		var users = $("#listerTree1").data("selectedNodes");
		var personIdArray = new Array(users.length); 
		if (users == null) {
			users = new Array(0);
		}
		for(var i=0; i<users.length; i++){
			personIdArray[i] = users[i].extendId;
		}
// 		var loginNameArray = new Array(users.length); 
// 		if (users == null) {
// 			users = new Array(0);
// 		}
// 		for(var i=0; i<users.length; i++){
// 			loginNameArray[i] = users[i].loginName;
// 		}
		var processDefinitionId = $("#processDefinitionId").val();
		var taskDefinitionKey = $("#taskDefinitionKey").val();
		var strategyId = $("#strategyId").val();
		var scenarioItemId = $("#scenarioItemId").val();
		var personIds = personIdArray.join(",");
			$.ajax({type: 'POST',
				url: '<%=basePath %>/definitions/assignment/saveUser',
				data:{personIds:personIds,
// 				loginNames:loginNameArray.join(","),
					  processDefinitionId:processDefinitionId,
					  taskDefinitionKey:taskDefinitionKey,
					  strategyId:strategyId,
					  scenarioItemId:scenarioItemId,
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
	

//重置
function closeWin(){
	window.location.reload();
	top.Dialog.close();
	
}
//重置查询
function resetSearch(){
	$("#queryForm")[0].reset();
	searchHandler();
}
function searchHandler(){
	var raName = $("#raName").val();
	var userName = $("#userName").val();
	$.post("<%=basePath %>/definitions/assignment/usertree/query",
		   {"processDefinitionId":"${processDefinitionId}",
			"taskDefinitionKey":"${taskDefinitionKey}",
			"raName":raName,
			"userName":userName
			},
		   function(result){
			 $("#listerTree1").data("data",result);
			 $("#listerTree1").render(); 
			},
			"json");
}
</script>
<!-- 异步提交end -->	
</body>
</html>