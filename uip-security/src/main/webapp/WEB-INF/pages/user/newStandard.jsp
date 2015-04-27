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
 <div >
	<form id="myFormId"  method="post"  failAlert="表单填写不正确，请按要求填写！">
	<input type="hidden" id="raName" name="raName"/>
	<input type="hidden" id="raCode" name="raCode"/>
	<input type="hidden" id="categoryCode" name="categoryCode" value="${userEntity.categoryCode}"/>
	<input type="hidden" id="categoryName" name="categoryName" value="${userEntity.categoryName}"/>
	<input type="hidden" id="id" name="id" value="${userEntity.id}"/>
	<table class="tableStyle" formMode="view" align="center">	
		<c:if test="${userEntity.extendId==null}">
			<tr>
				<td>账号:</td>
				<td ><input id="account" class="validate[required],custom[noSpecialCaracters],length[1,18]" watermark="请输入长度为1-18的英文或数字" class="validate[required]" style="width:400px" name="account" type="text" value="${userEntity.account}"/></td>
			</tr>
			<tr>
				<td>姓名:</td>
				<td ><input id="name" class="validate[required]" style="width:400px" name="name" type="text" value="${userEntity.name}"/></td>
			</tr>
		</c:if>
		<c:if test="${userEntity.extendId!=null}">
			<tr>
				<td>账号:</td>
				<td ><input id="account" disabled="true" class="validate[required],custom[noSpecialCaracters],length[1,18]" watermark="请输入长度为1-18的英文或数字" class="validate[required]" style="width:400px" name="account" type="text" value="${userEntity.account}"/>
					 <input type="hidden" name="account" value="${userEntity.account}" />
				</td>
			</tr>
			<tr>
				<td>姓名:</td>
				<td ><input id="name" disabled="true"  class="validate[required]" style="width:400px" name="name" type="text" value="${userEntity.name}"/>
					<input type="hidden" name="name" value="${userEntity.name}" />
				</td>
			</tr>
		</c:if>
		<tr>
			<td>编号:</td>
			<td><input id="code" style="width:400px" class="validate[required]"  name="code" type="text" value="${userEntity.code}"/></td>
		</tr>
		<tr>
			<td>email:</td>
			<td><input id="email" style="width:400px"   name="email" type="email" value="${userEntity.email}"/></td>
		</tr>
		<tr>
			<td>注册机构：</td>
			<td >
				<select prompt="请选择类别" id="raId" class="validate[required]" style="width:250px" name="raId" data=""  selectedValue="${userEntity.raId}"></select>
			</td>
		</tr>
		<tr>
			<td>备注:</td>
			<td><input id="decription" style="width:400px" name="decription" type="text" value="${userEntity.decription}"/></td>
		</tr>
<!-- 		<tr style="height: 60px"> -->
<!-- 		    <td colspan="2"> -->
<!-- 				<input type="submit" value=" 提 交 "/> -->
<!-- 				<input type="reset" value=" 重 置 "/> -->
<!-- 				<input type="button" value=" 关闭 " onclick="parent.Dialog.close();"/> -->
<!-- 			</td> -->
<!-- 	    </tr> -->
	</table>
	</form>
</div>
<!-- 异步提交start -->
<script type="text/javascript">
	var raList = <%=JsonUtil.array2JSON(request.getAttribute("raList"))%>;
	var raData = {"list":[]};
	var ra;
	for(var i = 0; i < raList.length; i++){
		ra = {'value':raList[i].id, 'key':raList[i].name};
		raData.list.push(ra);
	}
	var a = JSON.stringify(raData);
	$('#raId').attr("data",a);
	
	
	
	$('#myFormId').attr("action","<%=path%>/user/save");
	function initComplete(){
    //表单提交
    $('#myFormId').submit(function(){ 
	    //判断表单的客户端验证是否通过
			var valid = $('#myFormId').validationEngine({returnIsValid: true});
			if(valid){
			   $(this).ajaxSubmit({
			        //表单提交成功后的回调
			        success: function(responseText, statusText, xhr, $form){
			            if(responseText == "SYS_SUCCESS"){
			    			top.Dialog.alert("成功提交",function(){
			    					parent.gridUser.loadData();
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

function valids() {
	var valid = $('#myFormId').validationEngine({
		returnIsValid : true
	});
	return valid;
}
function getParams() {
	var params = $('#myFormId').serialize();
	return params;
}
</script>
<!-- 异步提交end -->	
</body>
</html>