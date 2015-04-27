<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

 <script type="text/javascript">
  	$(function(){

  	});
  	
  	
  	
  	
</script>
<form id="form" method="post">
 <input type="hidden" name="id" id="id" value="${resourceMocDefinitionEntity.id}" />
 
 <table class="table">
	
	<tr>
	  	<td class="left" width="120">名称：</td>
		<td colspan="3">
	  		<input type="text" name="name" id="name" value="${resourceMocDefinitionEntity.name}" class="easyui-validatebox" required="true" missingMessage="名称必须填写"/>
		</td>
	</tr>
	
	<tr>
	  	<td class="left" width="120">父资源：</td>
		<td colspan="3">
	  		<input type="text" name="parent" id="parent" value="${resourceMocDefinitionEntity.parent}" class="easyui-validatebox" required="true" missingMessage="父类型必须填写"/>
		</td>
	</tr>
	
	<tr>
	  	<td class="left" width="120">类型：</td>
		<td colspan="3">
	  		<input type="text" name="type" id="type" value="${resourceMocDefinitionEntity.type}" class="easyui-validatebox" required="true" missingMessage="类型必须填写"/>
		</td>
	</tr>
	
	<tr>
	  	<td class="left" width="120">备注</td>
		<td colspan="3">
	  		<textarea rows="5" cols="3" name="description" id="description" style="width:90%;"   required="true" maxlength="200" class="easyui-validatebox" required="true" missingMessage="不得多于200字">${resourceMocDefinitionEntity.description}</textarea>
		</td>
	</tr>
  </table>
</form>
