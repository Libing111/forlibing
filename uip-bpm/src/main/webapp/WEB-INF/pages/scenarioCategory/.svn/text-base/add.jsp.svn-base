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
	<form id="myFormId"  method="post"  failAlert="表单填写不正确，请按要求填写！">
	<input type="hidden" id="strategyId" name="strategyId" value="${bpmScenarioStrategy.strategyId}"/>
	<input type="hidden" id="strategyName" name="strategyName" value="${bpmScenarioStrategy.strategyName}"/>
	<table class="tableStyle" formMode="view">	
		<tr style="height: 60px">
			<td >场景：</td>
			<td ><input id="name" class="validate[required]" style="width:200px" name="name" type="text" value="${bpmScenarioStrategy.name}"/></td>
		</tr>
		<tr style="height: 60px">
			<td>场景策略：</td>
			<td >
				<select prompt="请选择" id="strategy"   class="validate[required]"  name="strategy" data="" onchange="voluation()"  selectedValue="${bpmScenarioStrategy.strategyId}"></select>
			</td>
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
	var list = <%=JsonUtil.array2JSON(request.getAttribute("bpmScenarioStrategyList"))%>;
	var data = {"list":[]};
	var bpmScenarioStrategy;
	for(var i = 0; i < list.length; i++){
		bpmScenarioStrategy = {'value':list[i].id, 'key':list[i].name};
		data.list.push(bpmScenarioStrategy);
	}
	var a = JSON.stringify(data);
	$('#strategy').attr("data",a);
	
	function voluation(){
		var strategyName = $("#strategy").attr("relText");
		var strategyId = $("#strategy").attr("value");
		$('#strategyName').val(strategyName);
		$('#strategyId').val(strategyId);
	}
	
	
	$('#myFormId').attr("action","<%=path%>/scenario/category/save");
	function initComplete(){
		 $('#myFormId').submit(function(){ 
			    //判断表单的客户端验证是否通过
			    
			    //判断表单的客户端验证是否通过
					var valid = $('#myFormId').validationEngine({returnIsValid: true});
					if(valid){
					   $(this).ajaxSubmit({
					        //表单提交成功后的回调
					        success: function(responseText, statusText, xhr, $form){
					            if(responseText == "SYS_SUCCESS"){
					    			top.Dialog.alert("成功提交",function(){
					    				top.Dialog.close();
		    							});
					    		}else{
					    			top.Dialog.alert(responseText,function(){
					    				window.location.reload();
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