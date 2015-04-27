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
<title></title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>
<!--框架必需end-->
<!--框架必需start-->
<script type="text/javascript" src="/file/libs/js/jquery.js"></script>
<script type="text/javascript" src="/file/libs/js/framework.js"></script>
<link href="/file/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="/file/"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
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
	<form id="myForm"  method="post"  failAlert="表单填写不正确，请按要求填写！">
	<input type="hidden" id="id" name="id" value="${definitionsCategory.id}"/>
	<table class="tableStyle"  align="center">	
		<tr>
			<td style="text-align:left !important;width:60px">名称:</td>
			<td><input id="name" style="width:200px" class="validate[required],length[1,16]"  name="name" type="text" value="${definitionsCategory.name}" onchange="validateName()"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td style="text-align:left !important;width:60px">编号:</td>
			<td><input id="code" style="width:200px" class="validate[required],length[1,16],custom[onlyLetter]"  name="code" type="text" value="${definitionsCategory.code}" onchange="validateCode()"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td style="text-align:left !important;width:60px">序号:</td>
			<td><input id="sequence" style="width:200px" class="validate[required],custom[onlyNumber]"  name="sequence" type="text" value="${definitionsCategory.sequence}" onchange="validateSequence()"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td style="text-align:left !important;width:60px">备注:</td>
			<td><input id="description" style="width:200px" name="description" type="text" value="${definitionsCategory.description}"/></td>
		</tr>
<!-- 		<tr> -->
<!-- 		    <td colspan="2" height="60px"> -->
<!-- 				<input type="submit" id="submit" value=" 提 交 "/> -->
<!-- 				<input type="reset" value=" 重 置 "/> -->
<!-- 				<input type="button" value=" 关闭 " onclick="parent.Dialog.close();"/> -->
<!-- 			</td> -->
<!-- 	    </tr> -->
	</table>
	</form>
</div>
<!-- 异步提交start -->
<script type="text/javascript">
	var parentExtendId = "${parentExtendId}";
	$('#myForm').attr("action","<%=path%>/definitions/category/save?parentExtendId="+ parentExtendId);
	function initComplete(){
		 $('#myForm').submit(function(){ 
			    //判断表单的客户端验证是否通过
					var valid = $('#myForm').validationEngine({returnIsValid: true});
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
function validateName(){
	var name = document.getElementById("name").value;
	$.ajax({type: 'POST',
		url: '<%=basePath%>/definitions/category/validateName?name='+name,
		success:function(result){
			if(result == "SYS_SUCCESS"){
			$("#submit").removeAttr("disabled");
			return;
			}
			top.Dialog.alert("此名称已经被使用，请重新输入！");
			$("#submit").attr({"disabled":"disabled"});
		},
		dataType: "json",
		async:false
	});	
}	
function validateCode(){
	var code = document.getElementById("code").value;
	$.ajax({type: 'POST',
		url: '<%=basePath%>/definitions/category/validateCode?code='+code,
		success:function(result){
			if(result == "SYS_SUCCESS"){
			$("#submit").removeAttr("disabled");
			return;
			}
			top.Dialog.alert("此编号已经被使用，请重新输入！");
			$("#submit").attr({"disabled":"disabled"});
		},
		dataType: "json",
		async:false
	});	
}
function validateSequence(){
	var sequence = document.getElementById("sequence").value;
	$.ajax({type: 'POST',
		url: '<%=basePath%>/definitions/category/validateSequence?sequence='+sequence+ '&parentExtendId=' + parentExtendId,
		success:function(result){
			if(result == "SYS_SUCCESS"){
			$("#submit").removeAttr("disabled");
			return;
			}
			top.Dialog.alert("此序号已经被使用，请重新输入！");
			$("#submit").attr({"disabled":"disabled"});
		},
		dataType: "json",
		async:false
	});	
}
function valids(){
		var valid = $('#myForm').validationEngine({
			returnIsValid : true
		});
		return valid;
}
function getParams() {
			var params = $('#myForm').serialize();
			return params;
		}
</script>
<!-- 异步提交end -->	
</body>
</html>