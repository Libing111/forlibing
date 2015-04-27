<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.proper.uip.common.utils.JsonUtil"  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("P3P","CP=CAO PSA OUR IDC DSP COR ADM DEVi TAIi PSD IVAi IVDi CONi HIS IND CNT");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分屏与栏目设置</title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>

<!--框架必需end-->

<!--树组件start -->
<script type="text/javascript" src="/file/libs/js/tree/ztree/ztree.js"></script>
<link href="/file/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!--树组件end -->


<!--数据表格start-->
<script src="/file/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!-- 表单验证start -->
<script src="/file/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="/file/libs/js/form/validation.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 表单异步提交start -->
<script src="/file/libs/js/form/form.js" type="text/javascript"></script>
<!-- 表单异步提交end -->
</head>
<body>
 <div>
	<form id="myForm"  method="post" >
	<input type="hidden" id="id" name="id" value="${applicationAndGroup.id}"/>
	<input type="hidden" id="parentExtendId" name="parentExtendId" value="${parentExtendId}"/>
	<input type="hidden" id="systemCategory" name="systemCategory" value="${systemCategory}"/>
	<table class="tableStyle" id="tbaletable" style="width: 100%">	
	
		<tr>
			<td class="properForm2Left">名称:</td>
			<td class="properForm2Right1"><input id="name" class="validate[required]" style="width:200px" name="name" type="text" value="${applicationAndGroup.name}"/></td>
		</tr>
		<tr>
			<td class="properForm2Left">序号:</td>
			<td class="properForm2Right1"><input id="sequenceNumber" style="width:200px" class="validate[required]"  name="sequenceNumber" type="text" value="${applicationAndGroup.sequenceNumber}"/></td>
		</tr>
		
	</table>
	</form>
</div>
<!-- 异步提交start -->
<script type="text/javascript">
	function valids() {
		var valid = $('#myForm').validationEngine({
			returnIsValid : true
		});
		return valid;
	}
	function getParams() {
		var params = $('#myForm').serialize();
		return params;
	}
	function initComplete() {
			//表单提交
			$('#myForm').submit(
					function() {
						//判断表单的客户端验证是否通过
						var valid = $('#myForm').validationEngine({
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