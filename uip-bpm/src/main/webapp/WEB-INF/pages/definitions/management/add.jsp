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
	<input type="hidden" id="id" name="id" value="${processDefinitionManagement.id}"/>
	<input type="hidden" id="key" name="key" value="${processDefinitionManagement.key}"/>
	<input type="hidden" id="version" name="version" value="${processDefinitionManagement.version}"/>
	<input type="hidden" id="name" name="name" value="${processDefinitionManagement.name}"/>
	<table class="tableStyle" formMode="view" align="center">	
		<tr>
			<td style="text-align:left !important; width: 60px;height: 200px">类别:</td>
			<td >
			 <table>
			<!-- <select prompt="请选择类别" id="categoryId" class="validate[required]"  style="width:400px" name="categoryId" data="" selectedValue="${processDefinitionManagement.categoryId}"></select><span class="star">*</span>-->
				<c:forEach var="category" items="${definitionsCategoryList}" varStatus="status">
				
					<c:if test="${status.index % 3 == 0}">
						<tr>
					</c:if>
					<td style="text-align:left !important; background-color: white;">
	  				<input type='radio' id='${category.id}' name='radio' value='${category.id}'/>
	  				<label style="width: 65px" class='hand' for='${category.id}'>${category.name}</label>
	  				</td>
	  				<c:if test="${status.count % 3 == 0 || status.end == true}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
			</td>
		</tr>
		<tr height="60px">
		    <td colspan="2" >
				<input type="submit" value=" 提 交 "/>
				<input type="button" value=" 关闭 " onclick="parent.Dialog.close();"/>
			</td>
	    </tr>
	</table>
	</form>
</div>
<!-- 异步提交start -->
<script type="text/javascript">
	var definitionsCategoryList = <%=JsonUtil.array2JSON(request.getAttribute("definitionsCategoryList"))%>;
	$("#${processDefinitionManagement.categoryId}").attr("checked",true);
	
	function initComplete(){
		
		 $('#myForm').submit(function(){ 
			 var msg = "";
				$("input:radio[name=radio]").each(function(){
					if($(this).attr("checked")){
						msg = $(this).val();
					}
				});
			$('#myForm').attr("action","<%=path%>/definitions/management/save?categoryId="+msg);
			    //判断表单的客户端验证是否通过
					var valid = $('#myForm').validationEngine({returnIsValid: true});
					if(valid){
					   $(this).ajaxSubmit({
					        //表单提交成功后的回调
					        success: function(responseText, statusText, xhr, $form){
					            if(responseText == "SYS_SUCCESS"){
					    			top.Dialog.alert("成功提交",function(){
					    				parent.processDefinitionGrid.loadData();
					    				//parent.initTree();
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