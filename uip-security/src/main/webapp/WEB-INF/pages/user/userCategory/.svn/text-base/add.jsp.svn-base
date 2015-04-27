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

</head>
<body>
 <div>
	<form id="myFormIda"  method="post" action="<%=path%>/userCategory/save" target="frmright" failAlert="表单填写不正确，请按要求填写！">
	<table class="tableStyle" formMode="view" align="center">	
	
		<tr style="height: 60px">
			<td style="width: 60px">名称:</td>
			<td >
				<input type="hidden" id="id" name="id" value="${userCategoryEntity.id}"/>
				<input id="name" class="validate[required,length[0,20]]"style="width:400px" name="name" type="text" value="${userCategoryEntity.name}"/>
			</td>
		</tr>
		<tr style="height: 60px">
			<td style="width: 60px">编号:</td>
			<td><input id="code" style="width:400px" class="validate[required,length[0,20]]"  name="code" type="text" value="${userCategoryEntity.code}"/></td>
		</tr>
		<tr style="height: 60px">
			<td style="width: 60px">序号:</td>
			<td><input id="sequenceNumber" style="width:400px" class="validate[required,custom[onlyNumber],length[0,20]]"  name="sequenceNumber" type="text" value="${userCategoryEntity.sequenceNumber}"/></td>
		</tr>
		<tr style="height: 60px">
			<td style="width: 60px">备注:</td>
			<td><input id="description" style="width:400px" name="description"  class="validate[required,length[0,200]]" type="text" value="${userCategoryEntity.description}"/></td>
		</tr>
		<tr style="height: 60px">
		    <td colspan="2">
				<input type="submit" value=" 提 交 "/>
				<input type="reset" value=" 重 置 "/>
				<input type="button" value=" 关闭 " onclick="parent.Dialog.close();"/>
			</td>
	    </tr>
	</table>
	</form>
</div>
<!-- 异步提交start -->
<script type="text/javascript">

function initComplete(){
    //表单提交
    $('#myFormIda').submit(function(){ 
	    //判断表单的客户端验证是否通过
			var valid = $('#myFormIda').validationEngine({returnIsValid: true});
			if(valid){
			   $(this).ajaxSubmit({
			        //表单提交成功后的回调
			        success: function(responseText, statusText, xhr, $form){
			            if(responseText == "SYS_SUCCESS"){
			    			top.Dialog.alert("成功提交",function(){
			    					parent.grid.loadData();
									parent.Dialog.close();
    							});
			    		}else{
			    			top.Dialog.alert(responseText,function(){
				            	closeWin();
				            });	
			    		}
			        }
			    }); 
			 }
		    //阻止表单默认提交事件
		    return false; 
	});
}

//重置
function closeWin(){
	window.location.reload();
	top.Dialog.close();
}
	
</script>
<!-- 异步提交end -->	
</body>
</html>