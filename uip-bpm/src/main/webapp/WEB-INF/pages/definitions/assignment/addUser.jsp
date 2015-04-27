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
<title>用户经办人设置</title>
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

<script>
<%--	function initComplete(){--%>
<%--		$("#listerTree1").bind("itemClick",function(e){--%>
<%--			$.post("<%=basePath %>/definitions/assignment/usertree/query",{"processDefinitionId":"${processDefinitionId}","taskDefinitionKey":"${taskDefinitionKey}"},function(result){--%>
<%--				$("#listerTree1").data("data",result)--%>
<%--				$("#listerTree1").render(); --%>
<%--			},"json");--%>
<%--		    })--%>
<%--		}--%>
	function initComplete(){
	    //获取json数据
	   $.post("<%=basePath %>/definitions/assignment/usertree/query",{"processDefinitionId":"${processDefinitionId}","taskDefinitionKey":"${taskDefinitionKey}"},function(result){
	        //赋给data属性
	    $("#listerTree1").data("data",result);
	    $("#listerTree1").render(); 
	    },"json");
	
	}

	function addUserIdentityLink(){
		var users = $("#listerTree1").data("selectedNodes");
		var loginNameArray = new Array(users.length); 
		if (users == null) {
			users = new Array(0);
		}
		for(var i=0; i<users.length; i++){
			loginNameArray[i] = users[i].loginName;
		}
		var processDefinitionId = $("#processDefinitionId").val();
		var taskDefinitionKey = $("#taskDefinitionKey").val();
			$.ajax({type: 'POST',
				url: '<%=basePath %>/definitions/assignment/saveUser',
				data:{loginNames:loginNameArray.join(","),
					  processDefinitionId:processDefinitionId,
					  taskDefinitionKey:taskDefinitionKey
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

//查询
<%--function searchHandler(){--%>
<%--	raName:$("#raName").val(),--%>
<%--	UserName:$("#UserName").val(),--%>
<%--	$.ajax({--%>
<%--			  type: 'POST',--%>
<%--			  url: '<%=basePath %>definitions/assignment/queryRaAndUser?raName=' + raName + '&UserName=' + UserName,--%>
<%--			  data:rowData,--%>
<%--			  success:function(result){--%>
<%--						alert("选中");--%>
<%--					  },--%>
<%--			  dataType: "json",--%>
<%--			  async:false--%>
<%--			});  --%>
<%--}--%>
<%--	--%>
	
</script>

</head>
<body>
 <div panelWidth="800">
 <fieldset> 
 <div  id="searchPanel">
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
								<input type="text" id="UserName" name="UserName"/>
								<input type="text" style="width:2px;display:none;"/>
							</td>
							<td width="4"></td>
							<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
							<td width="4"></td>
							<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置查询</span></button></td>
						</tr>
					</table>
				</form>
			</div>
  </fieldset>
 <table class="tableStyle" formMode="line">
 	<tr>
 		<td>设置用户经办人：</td>
 		<td>
	 		<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${processDefinitionId}"/>
	 		<input type="hidden" id="taskDefinitionKey" name="taskDefinitionKey" value="${taskDefinitionKey}"/>
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

</body>
</html>