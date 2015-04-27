<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.proper.uip.common.utils.JsonUtil"  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <script type="text/javascript">
  		$(function(){
		  		var userCategoryList = <%=JsonUtil.array2JSON(request.getAttribute("userCategoryList"))%>;
				$("#categoryName").combobox({
				data:userCategoryList,
				valueField:'id',
				textField:'name',
				width:160,
				panelHeight:75,
				required:true,
				editable:false,
				onSelect:function(record){
					$("#categoryId").val($(this).combobox("getValue"));
					$("#categoryName").val($(this).combobox("getText"));
					},
				onUnselect:function(record){
					$("#categoryId").val($(this).combobox("getValue"));
					$("#categoryName").val($(this).combobox("getText"));
					}
			
			});
  	});
  	 
  	
  	
  	
</script>
<form id="form" method="post">
<input type="hidden" name="id" id="id" value="${userEntity.id}" />
 <table class="table">
	<tr>
	  	<td class="left" width="120">登录名：</td>
		<td colspan="3">
	  		<input type="text" name="loginName" class="validate[required],custom[noSpecialCaracters],length[1,18]"  id="loginName" value="${userEntity.loginName}" class="easyui-validatebox" required="true" missingMessage="登录名必须填写"/>
		</td>
	</tr>
 	
	
	
	<tr>
	  	<td class="left" width="120">姓名：</td>
		<td colspan="3">
	  		<input type="text" name="name" id="name" value="${userEntity.name}" class="easyui-validatebox" required="true" type="password" value=""  missingMessage="姓名必须填写"/>
		</td>
	</tr>
	
	
	<tr>
	  	<td class="left" width="120">用户类别：</td>
		<td colspan="3">
	  		<input type="text" name="categoryName" id="categoryName" value="${userEntity.categoryName}"  />
	  		<input type="hidden" name="categoryId" id="categoryId" value="${userEntity.categoryId}" > 
	  		<input type="hidden" name="categoryName" id="categoryName" value="${userEntity.categoryName}" > 
		</td>
	</tr>
	
	<tr>
	  	<td class="left" width="120">邮箱：</td>
		<td colspan="3">
	  		<input type="text" name="email" id="email" value="${userEntity.email}"  validType="email" invalidMessage="请填写正确的邮箱格式" />
		</td>
	</tr>
	
	<tr>
	  	<td class="left" width="120">备注</td>
		<td colspan="3">
	  		<textarea rows="5" cols="3" name="decription" id="decription" style="width:90%;"   required="true" maxlength="100">${userEntity.decription}</textarea>
		</td>
	</tr>
  </table>
</form>
