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
</head>
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
<form id="myForm"  method="post"  failAlert="表单填写不正确，请按要求填写！">
  <table class="tableStyle" formMode="view">
  <input type="hidden" id="id" name="id" value="${id}"/>
  <tr style="height: 60px">
	  <td style="width: 100px">资源分类</td>
	  <td>
	  	<c:forEach var="resourseCategory" items="${categoryList}" varStatus="i">
	  		<input type='checkbox' id='${resourseCategory.code}' name='checkbox' value='${resourseCategory.code}'/><label class='hand' for='${resourseCategory.code}'>${resourseCategory.name}</label>
		</c:forEach>
	  </td>
  </tr>
  
  <tr style="height: 60px">
	  <td style="width: 100px">桌面文字标题</td>
	  <td>
	  	<input style="width: 250px" class="validate[required]"  type="text" name="title" id="title" value="${desktopTitleSY.value}"/>
	  	<span class="star">*</span>
	  </td>
  </tr>
  
  <tr style="height: 60px">
	  <td style="width: 100px">桌面背景图片路径</td>
	  <td>
		  <input style="width: 250px" class="validate[required]"  type="text" name="picture" id="picture" value="${backgroudpictureSY.value}"/>
		  <span class="star">*</span>
	  </td>
  </tr>
  
  <tr style="height: 60px">
	<td colspan="3">
		<input type="submit" id="submit" value="提交" />&nbsp&nbsp&nbsp
		<input type="button" value="取消" onclick="top.Dialog.close()"/>
	</td>
  </tr>
  </table> 
  </form>
</div>


<script type="text/javascript">
	var resourceCategoryList = <%=JsonUtil.array2JSON(request.getAttribute("categoryList"))%>;
	var spList = <%=JsonUtil.array2JSON(request.getAttribute("value"))%>;
	var resourceCategory;
	for(var i = 0; i < resourceCategoryList.length; i++){
		for(var j = 0; j < spList.length; j++){
			if(resourceCategoryList[i].code == spList[j]){
				$("input:checkbox[name=checkbox]").eq(j).attr("checked",true);
			}
		}
	}
function initComplete(){
	 $('#myForm').submit(function(){ 
			var msg = "";
			$("input:checkbox[name=checkbox]").each(function(){
				if($(this).attr("checked")){
					msg = msg + "," + $(this).val();
				}
			})
			msg = msg.substring(1);
			$('#myForm').attr("action","<%=path%>/systema/preferences/save?systemCategory=${systemCategory}&msgCode="+msg);

		    //判断表单的客户端验证是否通过
				var valid = $('#myForm').validationEngine({returnIsValid: true});
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

</body>
</html>