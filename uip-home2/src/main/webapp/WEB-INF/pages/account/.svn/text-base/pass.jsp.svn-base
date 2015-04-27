<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.proper.uip.common.utils.JsonUtil"  %>
<%
String path = request.getContextPath();
String contexPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("P3P","CP=CAO PSA OUR IDC DSP COR ADM DEVi TAIi PSD IVAi IVDi CONi HIS IND CNT");
path=contexPath+"file";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
<script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
<link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!-- 异步上传控件start -->
<script type="text/javascript" src="<%=path%>/libs/js/form/upload/fileUpload.js"></script>
<script type="text/javascript" src="<%=path%>/libs/js/form/upload/handlers.js"></script>
<!--异步上传控件end -->

<!-- 表单验证start -->
<script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 表单异步提交start -->
<script src="<%=path%>/libs/js/form/form.js" type="text/javascript"></script>
<!-- 表单异步提交end -->
<script>
function getParams() {
	var params = $('#myFormId').serialize();
	return params;
}
function valids() {
	var valid = $('#myFormId').validationEngine({
		returnIsValid : true
	});
	return valid;
}
</script>
</head>
  
<body>
<div   whiteBg="true">

<form id="myFormId" action=""  method="post" target="frmright">
	<table class="tableStyle" formMode="view">
		<tr>
			<td style="width: 100px">姓名：</td>
			<td>
				<input type="hidden" id="id" name="id" value="${user.id}"/>
				<input type="hidden" id="name" name="name" value="${user.name}"/>
				${user.name}
			</td>
		</tr>
		<tr >
			<td style="width: 100px">原密码：</td>
			<td id="tdpwd">
				<input class="validate[required],length[6,11],custom[noSpecialCaracters]" style="width: 200px" type="password" name="oldPassword"  id="pwd" value="${oldPassword}"/><span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td style="width: 100px">新密码：</td>
			<td id="tdpwds">
				<input type="password" class="validate[required],length[6,11],custom[noSpecialCaracters]"  style="width: 200px " name="password" id="password" value="${password}" watermark="长度在6-12之间，只能包含字符、数字和下划线" /><span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td style="width: 100px">确认密码：</td>
			<td>
				<input  type="password" class="validate[required,confirm[password]]" style="width: 200px" name="confirmPassword" id="confirmPassword" value="${confirmPassword}" maxlength="12"/><span class="star">*</span>
			</td>
		</tr>
<!-- 		<tr> -->
<!-- 			<td colspan="3"> -->
<!-- 				<input type="submit" id="submit" value="提交" />&nbsp&nbsp&nbsp&nbsp -->
<!-- 				<input type="button" value="取消" onclick="top.Dialog.close()"/> -->
<!-- 			</td> -->
<!-- 		</tr> -->
	</table>
</form>

</div>
<script type="text/javascript">

function initComplete(){

	 //表单异步提交处理
    $('#myFormId').submit(function(){ 
    	var oldPassword = document.getElementById("pwd").value;
    	var password = document.getElementById("password").value;
    	var confirmPassword = document.getElementById("confirmPassword").value;
		$('#myFormId').attr("action","<%=basePath%>account/savePassWord?oldPassword="+oldPassword);
    	//判断表单的客户端验证时候通过
		var valid = $('#myFormId').validationEngine({returnIsValid: true});
		if(valid){
		   $(this).ajaxSubmit({
		        //表单提交成功后的回调
		        success: function(responseText, statusText, xhr, $form){
		    		if(responseText == "SYS_SUCCESS"){
		    			top.Dialog.alert("修改成功",function(){
		    				top.Dialog.close();
							});
		    		}else{
		    			top.Dialog.alert(responseText,function(){
		    			window.location.href="<%=basePath %>account/password?oldPassword="+oldPassword+"&password="+password+"&confirmPassword="+confirmPassword;	
		    			
			            });	
		    		}
		        }
		    }); 
		 }
	    
	    //阻止表单默认提交事件
	    return false; 
	});
}

function submitPassword(){
 
    	var oldPassword = document.getElementById("pwd").value;
    	var password = document.getElementById("password").value;
    	var confirmPassword = document.getElementById("confirmPassword").value;
		$('#myFormId').attr("action","<%=basePath%>account/savePassWord?oldPassword="+oldPassword);
    	//判断表单的客户端验证时候通过
		var valid = $('#myFormId').validationEngine({returnIsValid: true});
		if(valid){
		   $(this).ajaxSubmit({
		        //表单提交成功后的回调
		        success: function(responseText, statusText, xhr, $form){
		    		if(responseText == "SYS_SUCCESS"){
		    			top.Dialog.alert("修改成功",function(){
		    				top.Dialog.close();
							});
		    		}else{
		    			top.Dialog.alert(responseText,function(){
		    			window.location.href="<%=basePath %>account/password?oldPassword="+oldPassword+"&password="+password+"&confirmPassword="+confirmPassword;	
		    			
			            });	
		    		}
		        }
		    }); 
		 }
	    
	    //阻止表单默认提交事件
	    return false; 
}
function hint(){
	$('#panpwds').remove();
	$("<span id='panpwds' class='yxsh' float='left' >*长度在6-12之间，只能包含字符、数字和下划线</span>").appendTo($('#hint'));
}

</script>
</body>
  
</html>