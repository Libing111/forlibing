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
<title>我的桌面</title>
<!--框架必需start-->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/language/cn.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/framework.js"></script>
<link href="<%=contextPath%>file/libs/css/import_basic.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" id="skin" prePath="<%=contextPath%>file/" splitMode="true"  href="<%=contextPath%>file/libs/skins/blue/style.css" />
<link rel="stylesheet" type="text/css" id="customSkin" />
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

<!-- 条件过滤器 start -->

<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/filter.js"></script>

<!-- 条件过滤器 end -->

</head>
<body>
 <div id="centerCon" position="center" style="over">
	 <div class="layout_content">

							<table class="filterTable">
								<tr>
									<td colspan="2" class="left" class="ali02 padding5">
										<input type="button" value="全选" onclick="allResource()"/>
										<input type="button" value="取消全选" onclick="resetForm()"/>
									</td>
								</tr>
								<c:forEach var="paging" items="${pagingList}" varStatus="status">
									<tr>
										<td   width="100" style="cursor:pointer" class="left" onclick="onclicksss('${paging.id}')">${paging.title}</td>
										<td class="right"><div class="filter" multiMode="true" showTip="true" id="${paging.id}" url="<%=basePath%>mydesktop/queryChildResource?pagingId=${paging.id}" ></div></td>
									</tr>
								</c:forEach>

							</table>
	  				
	 </div>
</div>
<!-- 异步提交start -->
<script type="text/javascript">
 var pagingList = <%=JsonUtil.array2JSON(request.getAttribute("pagingList"))%>;	
	function onclicksss(pagingId){
    	$.ajax({
		url: '<%=basePath%>mydesktop/clickParentResource',
		async:false,
		data: {pagingId:pagingId},
		success: function(result){
				//$(".filter").setValue("");
				for (var parentCode in result) {
				    $("#"+parentCode).setValue(result[parentCode]);
				};
			}
		});	  
	}	
	function getResource(){
	    var pagingIds = "";
		for(var i = 0; i < pagingList.length; i++){
			pagingIds =pagingIds + "," + $("#"+pagingList[i].id).attr("relValue");
		}
		return pagingIds;
	}	

	function resetForm(){
    	$(".filterTable .filter").setValue(""); 
	}
	function allResource(){
		
    	$.ajax({
		url: '<%=basePath%>mydesktop/allResource',
		async:false,
		success: function(result){
				//$(".filter").setValue("");
				for (var parentCode in result) {
				    $("#"+parentCode).setValue(result[parentCode]);
				};
			}
		});	  
	}
</script>
<!-- 异步提交end -->	


</body>
</html>