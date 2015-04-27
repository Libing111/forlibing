<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>沈阳社会组织协同办公平台</title>
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<script type="text/javascript" src="<%=contextPath %>file/script/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>file/script/jquery.easyui.min.js"></script>
	
	<script type="text/javascript" src="<%=contextPath %>file/script/lang/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="<%=contextPath %>file/script/plugin/jquery.easyui.mask.js"></script>
	
	<script type="text/javascript" src="<%=contextPath %>/file/script/plugin/jquery.portal.js"></script>
	
	<script type="text/javascript" src="<%=contextPath %>file/script/plugin/testdata.js"></script>
	 
	
	<script type="text/javascript" src="<%=contextPath %>file/script/proper.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>file/css/global.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>file/css/easyui/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>file/css/opt.css">
	
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>file/platform/statics/index3.css"/>
	
  </head>
  <script type="text/javascript">
	$(function(){
		$(".module_resource").first().click();
	});
	
	function buttonClick(url) {
		$('#workIframe').attr('src', url);
		$('#workIframe').load();
	};
  </script>
  
  <body class="easyui-layout">
  	
  <div id="north" region="north" border="false" style="height:80px;overflow:hidden;background:#F6F6F6">
		<div class="top">
			<div class="dashboard">
				<div>
					<ul>
						<li>
							<a id="logout" href="<%=basePath%>/logout">
								<img src="<%=contextPath%>file/platform/statics/images/exitsystem-min.png" alt="退出系统"/>
								退出系统
							</a>
						</li>
						<c:forEach items="${subsystemList}" var="sybsystem" varStatus="i">
							<li>
								<a id="resetPwd" href="${sybsystem.url}">
									<img src="${sybsystem.icon}" alt="${sybsystem.name}"/>
									${sybsystem.name}
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div region="west" style="width:200px;">
		<c:forEach items="${moduleList}" var="module" varStatus="i">
			<input class="module_resource" type="button" onclick="buttonClick('${module.url}')" style="margin:0px 0px;width:100%;height:60px;" value="${module.name}"/>
		</c:forEach>
	</div>
  	<div region="center">
		<iframe style="width: 100%; height: 100%" id="workIframe" src=""></iframe> 
	</div>
	<div id="south" region="south" border="false" style="height:30px;background:#F6F6F6;padding:5px;text-align:center">
		技术支持：沈阳工大普日软件技术有限公司
	</div>
  
  </body>
</html>
