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

</head>
<body>
 <div >
	<form id="myFormId" action="<%=path%>/resource/save?parentCode=parentCode"  method="post"  failAlert="表单填写不正确，请按要求填写！">
	<input type="hidden" id="categoryName" name="categoryName"/>
	<input type="hidden" id="id" name="id" value="${resourceEntity.id}"/>
	<table class="tableStyle" formMode="view" align="center">	
	
		<tr>
			<td>名称:</td>
			<td ><input id="name" class="validate[required]" style="width:400px" name="name" type="text" value="${resourceEntity.name}"/></td>
		</tr>
		<tr>
			<td>简称:</td>
			<td ><input id="simpleName" class="validate[required]" style="width:400px" name="simpleName" type="text" value="${resourceEntity.simpleName}"/></td>
		</tr>
		<tr>
			<td>编号:</td>
			<td><input id="code" style="width:400px" class="validate[required,custom[noSpecialCaracters],length[8,8]]"  name="code" type="text" value="${resourceEntity.code}"/></td>
		</tr>
		<tr>
			<td>序号:</td>
			<td><input id="sequenceNumber" class="validate[required,custom[onlyNumber]]" style="width:200px" name="sequenceNumber" type="text" value="${resourceEntity.sequenceNumber}"/></td>
		</tr>
		<tr>
			<td>url:</td>
			<td><input id="url" style="width:400px" class="validate[required]"  name="url" type="text" value="${resourceEntity.url}"/></td>
		</tr>
		<tr>
			<td>图标:</td>
			<td><input id="icon" style="width:400px" class="validate[required]"  name="icon" type="text" value="${resourceEntity.icon}"/></td>
		</tr>
		<tr>
			<td>资源类别：</td>
			<td >
				<select prompt="请选择类别" id="categoryCode" class="validate[required]" style="width:250px" name="categoryCode" data="" selectedValue="${resourceEntity.categoryCode}"></select>
			</td>
		</tr>
		<tr>
			<td>匿名资源:</td>
		    <td>
		        <input type="radio" id="radio-1" name="result" value="true" class="radio"/><label for="radio-1" class="hand">是</label>
		        <input type="radio" id="radio-2" name="result" value="false" class="radio"/><label for="radio-2" class="hand">不是</label>
		    </td>
		</tr>
		<tr>
			<td>备注:</td>
			<td><input id="description" style="width:400px" name="description" type="text" value="${resourceEntity.description}"/></td>
		</tr>
		<tr>
		    <td colspan="2">
				<input type="submit" value=" 提 交 "/>
				<input type="button" value=" 关闭 " onclick="parent.Dialog.close();"/>
			</td>
	    </tr>
	</table>
	</form>
</div>
<!-- 异步提交start -->
<script type="text/javascript">

	
	var resourceCategoryList = <%=JsonUtil.array2JSON(request.getAttribute("categoryList"))%>;

	var resourceCategoryData = {"list":[]};
	var resourceCategory;
	for(var i = 0; i < resourceCategoryList.length; i++){
			resourceCategory = {'value':resourceCategoryList[i].code, 'key':resourceCategoryList[i].name};
			
			resourceCategoryData.list.push(resourceCategory);
	}
	var a = JSON.stringify(resourceCategoryData);
	$('#categoryCode').attr("data",a);
	var parentCode = '${parentCode}';
	
	var anonymouslyResult = ${resourceEntity.anonymously};
	$('#radio-1').attr("checked",true);
	if(anonymouslyResult == false){
		$('#radio-2').attr("checked",true);
	}
	
	
	$('#myFormId').attr("action","<%=path%>/resource/save?parentCode="+parentCode);
	function initComplete(){
		$('#myFormId').submit(function(){ 
		 	//var isAnonymously = $("input:radio[name=result]").filter("[checked]").val();
			///$('#anonymously').val(isAnonymously);
		    //判断表单的客户端验证是否通过
		    
		    //判断表单的客户端验证是否通过
				var valid = $('#myFormId').validationEngine({returnIsValid: true});
				if(valid){
				   $(this).ajaxSubmit({
				        //表单提交成功后的回调
				        success: function(responseText, statusText, xhr, $form){
				            if(responseText == "SYS_SUCCESS"){
				    			top.Dialog.alert("成功提交",function(){
				    					parent.grid.loadData();
				    					parent.initTree();
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