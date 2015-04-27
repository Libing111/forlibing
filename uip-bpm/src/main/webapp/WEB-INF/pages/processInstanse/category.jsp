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


<!--去除虚线链接start-->
<style>
a {
	behavior:url(<%=contextPath%>file/libs/js/method/focus.htc)
}
</style>
<!--去除虚线链接end-->

</head>
<body>
<table width="100%">
	<tr>
		<td width="100" class="ver01" style="background-image: url('<%=contextPath%>file/img/backgrounda.png');">
		<div class="singleNavMin">
			<div class="current"><span><a href="<%=path%>/process/instanse/monitor/statistics" target="frmrightChild">统计</a></span></div>
			<c:forEach var="category" items="${definitionsCategoryList}" varStatus="i">
				<!-- 
				<c:if test="${i.index == 0}">
					<div><span><a href="<%=path%>/process/instanse/monitor/home?categoryCode=${category.code}" target="frmrightChild">${category.name}</a></span></div>
				</c:if>
				<c:if test="${i.index != 0}">
	  				<div><span><a href="<%=path%>/process/instanse/monitor/home?categoryCode=${category.code}" target="frmrightChild">${category.name}</a></span></div>
				</c:if>
				 -->
				 <div><span><a href="<%=path%>/process/instanse/monitor/home?categoryCode=${category.code}" target="frmrightChild">${category.name}</a></span></div>
			</c:forEach>
		</div>
		
		</td>
		<td class="ver01" style="background-image: url('<%=contextPath%>file/img/backgrounda.png');">
		<div style="background-image: url('<%=contextPath%>file/img/backgrounda.png');">
			<div class="cusBoxContent" style="overflow-y:auto;overflow-x:hidden;">
			 <!--  
			 <c:forEach var="category" items="${definitionsCategoryList}" varStatus="i">
				<c:if test="${i.index == 0}">
					<IFRAME height="100%" width="100%" frameBorder=0 id=frmrightChild name=frmrightChild src="<%=path%>/tasks/category/home?categoryCode=${category.code}" allowTransparency="true"></IFRAME>
				</c:if>
			</c:forEach>
			-->
				<IFRAME height="100%" width="100%" frameBorder=0 id=frmrightChild name=frmrightChild src="<%=path%>/process/instanse/monitor/statistics" allowTransparency="true"></IFRAME>
			</div>
		</div>
		</td>
	</tr>
</table>
<script type="text/javascript">
var definitionsCategoryList = <%=JsonUtil.array2JSON(request.getAttribute("definitionsCategoryList"))%>;
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-20)
	}
</script>
</body>
</html>