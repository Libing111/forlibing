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
<title>人员经办人设置</title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>
<!--框架必需end-->

<!-- 树组件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/tree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>file/libs/js/tree/ztree/ztree.css"></link>
<link type="text/css" rel="stylesheet" href="home/system/workdesk/skin/style.css"></link>
<!-- 树组件end -->

<!-- 树形双选器start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/listerTree.js"></script>
<!-- 树形双选器end -->

<!-- 树形下拉框start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/selectTree.js"></script>
<!-- 树形下拉框end -->
</head>
<body>
 <div class="box1" panelWidth="800">
 <fieldset> 
 <table class="tableStyle" formMode="line">
 	<tr>
 		<td>设置用户经办人：</td>
 		<td>
	 		<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${processDefinitionId}"/>
	 		<input type="hidden" id="taskDefinitionKey" name="taskDefinitionKey" value="${taskDefinitionKey}"/>
	 		<div class="listerTree" id="listerTree1" url="<%=basePath %>/definitions/assignment/personneltree/query" params='{"processDefinitionId":"${processDefinitionId}" ,"taskDefinitionKey":"${taskDefinitionKey}"}' listerHeight="300"></div>
	 		
 		</td>
 	</tr>
 	<tr>
		<td colspan="2">
		<input type="button" value="确定" onclick="addPersonnelIdentityLink()"/>
		<input type="button" value="取消"/>
		</td>
	</tr>
 </table>
 </fieldset>
 </div>
<script>
	function addPersonnelIdentityLink(){
		var personnels = $("#listerTree1").data("selectedNodes");
		var loginNameArray = new Array(personnels.length); 
		if (personnels == null) {
			personnels = new Array(0);
		}
		for(var i=0; i<personnels.length; i++){
			loginNameArray[i] = personnels[i].loginName;
		}
		var processDefinitionId = $("#processDefinitionId").val();
		var taskDefinitionKey = $("#taskDefinitionKey").val();
			$.ajax({type: 'POST',
				url: '<%=basePath %>/definitions/assignment/savePersonnel',
				data:{loginNames:loginNameArray.join(","),
					  processDefinitionId:processDefinitionId,
					  taskDefinitionKey:taskDefinitionKey
					  },
				success:function(result){
					if(result == "SYS_SUCCESS"){
						top.Dialog.alert("成功提交",function(){ closeWin(); });
    					return;
					}
					top.Dialog.alert("保存失败！");
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
	
	
	
</script>
</body>
</html>