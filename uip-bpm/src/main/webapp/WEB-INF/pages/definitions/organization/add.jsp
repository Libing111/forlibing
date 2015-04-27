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
 <div  class="box1">
	<form id="myFormId" action="<%=path%>/definitions/organization/save"  method="post"  failAlert="表单填写不正确，请按要求填写！">
	<input type="hidden" id="processDefinitionId" name="processDefinitionId" value="${organizationEntity.processDefinitionId}"/>
	<input type="hidden" id="id" name="id" value="${organizationEntity.id}"/>
	<input type="hidden" id="processDefinitionKey" name="processDefinitionKey" value="${organizationEntity.processDefinitionKey}"/>
	<input type="hidden" id="processDefinitionVersion" name="processDefinitionVersion" value="${organizationEntity.processDefinitionVersion}"/>
	<input type="hidden" id="resourceCode" name="resourceCode" value="${organizationEntity.resourceCode}"/>
	<input type="hidden" id="resourceId" name="resourceId" value="${organizationEntity.resourceId}"/>
	<table class="tableStyle" formMode="transparent" align="center">	
	
		<tr>
			<td>登记机关:</td>
			<td ><select prompt="请选择" id="organizationCode" class="validate[required]" style="width:500px" name="organizationCode" data="" selectedValue ="${organizationEntity.organizationCode}"></select></td>
		</tr>
		<tr>
			<td>资源&nbsp&nbsp:</td>
			<td>
			    <input id="resourceName" style="width:150px" class="validate[required]"  name="resourceName" type="text" value="${organizationEntity.resourceName}"/>
				<input type="button" value="选择资源" onclick="addResource()"/>
			</td>
		</tr>
		<tr>
			<td>流程定义:</td>
			<td>
				<input id="processDefinitionName" class="validate[required]" style="width:150px" name="processDefinitionName"  type="text" value="${organizationEntity.processDefinitionName}"/>
				<input type="button" value="选择流程定义" onclick="addProcessDefinition()"/>
			</td>
		
		</tr>
		<tr>
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

	var organizationList = <%=JsonUtil.array2JSON(request.getAttribute("organizationList"))%>;
	var organizationData = {"list":[]};
	var organization;
	for(var i = 0; i < organizationList.length; i++){
		organization = {'value':organizationList[i].code, 'key':organizationList[i].name};
		organizationData.list.push(organization);
	}
	var a = JSON.stringify(organizationData);
	$('#organizationCode').attr("data",a);


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

//选择资源
function addResource(){
    var diag = new top.Dialog();
    diag.Title = "选择资源";
    diag.ShowMaxButton=true;
    diag.ShowMinButton=true;
    diag.OKEvent = function(){
    	var rows = diag.innerFrame.contentWindow.mytest();
			var names = [];
			var codes = [];
			var ids = [];
			for(var i=0; i<rows.length; i++){
				names.push(rows[i].name);
				codes.push(rows[i].code);
				ids.push(rows[i].id);
			}
    	$('#resourceName').val(names);
    	$('#resourceCode').val(codes);
    	$('#resourceId').val(ids);
    	diag.close();
        };
    diag.CancelEvent = function(){
        diag.close();
        };
    diag.URL = "<%=basePath %>definitions/organization/addResource";
    diag.ShowButtonRow=true;
    diag.show();

}

//流程定义
function addProcessDefinition(){
    var diag = new top.Dialog();
    diag.Title = "选择流程定义";
    diag.ShowMaxButton=true;
    diag.ShowMinButton=true;
    diag.OKEvent = function(){
    	var rows = diag.innerFrame.contentWindow.mytest();
			var names = [];
			var ids = [];
			var codes = [];
			var versions = [];
			for(var i=0; i<rows.length; i++){
				names.push(rows[i].name);
				ids.push(rows[i].id);
				codes.push(rows[i].key);
				versions.push(rows[i].version);
			}
    	$('#processDefinitionName').val(names);
    	$('#processDefinitionId').val(ids);
    	$('#processDefinitionKey').val(codes);
    	$('#processDefinitionVersion').val(ids);
    	diag.close();
        };
    diag.CancelEvent = function(){
        diag.close();
        };
    diag.URL = "<%=basePath %>definitions/organization/addProcessDefinition";
    diag.ShowButtonRow=true;
    diag.show();

}
</script>
<!-- 异步提交end -->	
</body>
</html>