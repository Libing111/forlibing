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
	<form id="myFormIda"  method="post">
	<table class="tableStyle" id="tbaletable" style="width: 100%">	
	
		<tr>
			<td class="properForm2Left">名称:</td>
			<td class="properForm2Right1">
				<input type="hidden" id="id" name="id" value="${resourceCategoryEntity.id}"/>
				<input id="name" class="validate[required,length[0,20]]" style="width:400px" name="name" type="text" value="${resourceCategoryEntity.name}"/>
			</td>
		</tr>
		<tr>
			<td class="properForm2Left">编号:</td>
			<td class="properForm2Right1"><input id="code" style="width:400px" class="validate[required,length[0,50]]"  name="code" type="text" value="${resourceCategoryEntity.code}"/></td>
		</tr>
		<tr>
			<td class="properForm2Left">序号:</td>
			<td class="properForm2Right1"><input id="sequenceNumber" style="width:400px" class="validate[required,custom[onlyNumber],length[0,20]]"  name="sequenceNumber" type="text" value="${resourceCategoryEntity.sequenceNumber}"/></td>
		</tr>
		<tr>
			<td class="properForm2Left">备注:</td>
			<td class="properForm2Right1"><input id="description" style="width:400px" class="validate[length[0,200]]" name="description" type="text" value="${resourceCategoryEntity.description}"/></td>
		</tr>
	</table>
	</form>
</div>
<!-- 异步提交start -->
<script type="text/javascript">

	function valids() {
			var valid = $('#myFormIda').validationEngine({
				returnIsValid : true
			});
			return valid;
		}
		function getParams() {
			var params = $('#myFormIda').serialize();
			return params;
		}
		function initComplete() {
			//表单提交
			$('#myFormIda').submit(
					function() {
						//判断表单的客户端验证是否通过
						var valid = $('#myFormIda').validationEngine({
							returnIsValid : true
						});
						if (valid) {
							$(this).ajaxSubmit(
									{
										//表单提交成功后的回调
										success : function(responseText,
												statusText, xhr, $form) {
											top.Dialog.alert(
													responseText.message,
													function() {
														closeWin();
													})
										}
									});
						}
						//阻止表单默认提交事件
						return false;
					});
		}

		//重置
		function closeWin() {
			//刷新数据
			top.frmright.refresh(update);
			//关闭窗口
			top.Dialog.close();
		}	
</script>
<!-- 异步提交end -->	
</body>
</html>