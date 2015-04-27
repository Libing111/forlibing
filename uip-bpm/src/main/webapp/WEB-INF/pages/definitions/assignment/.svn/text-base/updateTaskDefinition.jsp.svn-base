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
<title>经办人设置</title>
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
<!--数据表格start-->
<script src="/file/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
</head>
<body>
 <div >
	<form id="myFormId" action="<%=path%>/definitions/assignment/taskDefinitions/save?type=add" target="frmright" method="post"  failAlert="表单填写不正确，请按要求填写！">
	<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${processDefinitionId}"/>
	<table class="tableStyle" formMode="view" align="center">	
		<input type="hidden" id="strategyId" name="strategyId" value="${taskDefinition.strategyId}"/>
		<input type="hidden" id="strategyName" name="strategyName" value="${taskDefinition.strategyName}"/>
		<input type="hidden" id="scenarioItemId" name="scenarioItemId" value="${taskDefinition.scenarioItemId}"/>
		<input type="hidden" id="scenarioItemName" name="scenarioItemName" value="${taskDefinition.scenarioItemName}"/>
		<input type="hidden" id="id" name="id" value="${taskDefinition.id}"/>
		<input type="hidden" id="identityLinkNames" name="identityLinkNames" value="${taskDefinition.identityLinkNames}"/>
		<tr style="height: 60px">
				<td>场景类别：</td>
				<td >
					<select prompt="请选择" id="strategy"   class="validate[required]"  name="strategy" data="" onchange="voluation()"  selectedValue="${taskDefinition.strategyId}"></select>
				</td>
		</tr>
		
		<tr style="height: 60px">
			<td >场景：</td>
			<td >
				<select prompt="请选择" id="scenarioItem" style="width:400px"   class="validate[required]"  name="scenarioItem" onchange="voluationItem()" data='{"list":[{"key":"${taskDefinition.scenarioItemName}","value":"${taskDefinition.scenarioItemId}"}]}'  selectedValue="${taskDefinition.scenarioItemId}"></select>
			</td>
		</tr>
		<tr>
			<td>步骤名称:</td>
			<td ><input id="taskDefinitionName" class="validate[required]" style="width:150px" name="taskDefinitionName" type="text" value="${taskDefinition.taskDefinitionName}"/></td>
		</tr>
		<tr>
			<td>步骤编号:</td>
			<td><input id="taskDefinitionKey" style="width:150px" class="validate[required]"  name="taskDefinitionKey" type="text" value="${taskDefinition.taskDefinitionKey}"/></td>
		</tr>
		<tr>
			<td>步骤序号:</td>
			<td><input id="sequenceNumber" class="validate[required]" style="width:150px" name="sequenceNumber"  type="text" value="${taskDefinition.sequenceNumber}"/></td>
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
	
	var itemList = <%=JsonUtil.array2JSON(request.getAttribute("scenarioItemList"))%>;
	var itemDate = {"list":[]};
	var item;
	for(var i = 0; i < itemList.length; i++){
		item = {'value':itemList[i].id, 'key':itemList[i].name};
		itemDate.list.push(item);
	}
	var aa = JSON.stringify(itemDate);
	$('#scenarioItem').attr("data",aa);
	$('#scenarioItem').render();
	function voluation(){
		var strategyName = $("#strategy").attr("relText");
		var strategyId = $("#strategy").attr("value");
		
		$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>definitions/assignment/scenarioQuery?strategyId='+strategyId,
			  success:function(result){
				  
				  var scenarioItemData = {"list":[]};
				  var scenarioItem;
					for(var i = 0; i < result.length; i++){
						scenarioItem = {'value':result[i].id, 'key':result[i].name};
						scenarioItemData.list.push(scenarioItem);
					}
					$('#scenarioItem').data("data",scenarioItemData);
					if(result.length > 0){
						$('#scenarioItem').setValue(result[0].id);
					}
					$('#scenarioItem').render();
				},
			  dataType: "json",
			  async:false
			});  
		$('#strategyName').val(strategyName);
		$('#strategyId').val(strategyId);
	}
function voluationItem(){
	var scenarioItemName = $("#scenarioItem").attr("relText");
	var scenarioItemId = $("#scenarioItem").attr("value");
	
	$('#scenarioItemName').val(scenarioItemName);
	$('#scenarioItemId').val(scenarioItemId);
}
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
			    					parent.taskDefinitionGrid.loadData();
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