<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ page import="com.proper.uip.common.utils.JsonUtil"  %>
<%
String path = request.getContextPath();
String contexPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("P3P","CP=CAO PSA OUR IDC DSP COR ADM DEVi TAIi PSD IVAi IVDi CONi HIS IND CNT");
%>



<div class="plat-center-panel plat-margin-top30">
	<div class="plat-center-panel" id="quickPanel" style="width:980px;" ></div>
	<div class="plat-center-panel" id="homewrap" role="homewrap">
	<ul id="homeContainer" class="plat-waterfall-layout">
	</ul>
</div>
</div>

<script language="javascript" src="<%=path%>/statics/js/home.js"></script>



