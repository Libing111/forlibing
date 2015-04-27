<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String contexPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//允许跨iframe设置访问cookie
//[ref]: http://www.cnblogs.com/ccdc/archive/2012/05/08/2489535.html  http://blog.csdn.net/wonder4/article/details/2125804
response.setHeader("P3P","CP=CAO PSA OUR IDC DSP COR ADM DEVi TAIi PSD IVAi IVDi CONi HIS IND CNT");
String quipath=contexPath+"file";//静态文件的路径
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0"> 
<title>协同管理-首页</title>



<!-- 框架 libs -->
<script language="javascript" src="<%=quipath%>/libs/js/jquery.js"></script>
<script language="javascript" src="<%=quipath%>/libs/js/language/cn.js"></script>
<script language="javascript" src="<%=quipath%>/libs/js/framework.js"></script>
<script type="text/javascript" src="<%=quipath%>/libs/js/main.js"></script>
<script type="text/javascript" src="<%=quipath%>/libs/js/drag/dragResize.js"></script>
<script language="javascript" src="<%=quipath%>/libs/js/popup/drag.js"></script>
<script language="javascript" src="<%=quipath%>/libs/js/popup/dialog.js"></script>
<script language="javascript" src="<%=quipath%>/libs/js/popup/messager.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="<%=path%>/statics/theme/favicon.png">

<link href="<%=quipath%>/libs/css/import_basic.css" rel="stylesheet" type="text/css" />

<link href="<%=quipath%>/libs/skins/${theme}/style.css" id="theme"   themeColor="${theme}" rel="stylesheet" type="text/css" />
<link type="text/css" jqpoint="themecss" theme="${theme}" rel="stylesheet" href="<%=path%>/statics/js/plus/scrollbar/perfect-scrollbar.css" />
<link type="text/css" jqpoint="themecss" theme="${theme}" rel="stylesheet"  href="<%=path%>/statics/theme/${theme}/css/default.css"   />

<script type="text/javascript" src="<%=path%>/statics/js/lib/jquery-ui-1.8.16.custom.min.js"></script>
<script language="javascript" src="<%=path%>/statics/js/lib/JsonTools.js"></script>
<script language="javascript" src="<%=path%>/statics/js/lib/common.js"></script>
<script language="javascript" src="<%=path%>/statics/js/plus/taskpanel/jquery.TaskButton.js"></script>
<script language="javascript" src="<%=path%>/statics/js/plus/taskpanel/jquery.TaskPanel.js"></script>

<!-- 瀑布插件布局插件 -->
<script language="javascript" src="<%=path%>/statics/js/lib/jquery.mousewheel-3.0.6.js"></script>
<script type="text/javascript" src="<%=path%>/statics/js/plus/scrollbar/perfect-scrollbar.js"></script>
<!-- 菜单插件 -->
<script language="javascript" src="<%=path%>/statics/js/plus/menu/jquery.menu.js"></script>
<!-- 模块插件 -->
<script language="javascript" src="<%=path%>/statics/js/plus/model/jquery.quickmodel.js"></script>
<!-- 模块插件 -->
<script language="javascript" src="<%=path%>/statics/js/plus/model/jquery.model.js"></script>
<!-- 瀑布插件布局插件 -->
<script language="javascript" src="<%=path%>/statics/js/plus/wookmark/jquery.wookmark.js"></script>
<!-- 滚动条插件 -->
<link type="text/css" rel="stylesheet" href="<%=path%>/statics/js/plus/scrollbar/perfect-scrollbar.css" />
<script language="javascript" src="<%=path%>/statics/js/plus/scrollbar/perfect-scrollbar.js"></script>

<!-- 查询输入框的插件 -->
<script language="javascript" src="<%=path%>/statics/js/plus/queryinput/jquery.queryinput.js"></script>

<!-- layout插件 如果没有用到leftTree布局的内容此处可以注释掉 -->
<script language="javascript" src="<%=path%>/statics/js/plus/jlayout/jquery.layout.js"></script>
<script language="javascript" src="<%=path%>/statics/js/plus/menu/jquery.verticalmenu.js"></script>
<!-- 桌面图标 -->
<script language="javascript" src="<%=path%>/statics/js/plus/desktop/jquery.desktop.js"></script>

<!-- 拼音插件start -->
<script type="text/javascript" src="<%=quipath%>/libs/thirdparty/myproper/pinyin.js"></script>
<!-- 拼音插件end -->
<!-- 主页页面js文件 -->
<script language="javascript" src="<%=path%>/statics/js/main.js"></script>

</head>

<body class="plat-content-bg">
<script type="text/javascript" src="<%=quipath%>/libs/thirdparty/echart/echarts-min-all.js"></script>
	<!--页面头，也是菜单容器-->
	<div id="headerWrap" class="plat-header-wrap">
		<div id="header" class="plat-header">
			<div id="logoPanel" class="plat-logo"></div>
			<!--一级菜单-->
			<div id="plat-menu-wrap" class="plat-menu-wrap"></div>
			<!--工具栏-->
			<div id="toolsPanel" class="plat-tools-wrap">
		<ul class="plat-tools">
			<li class="selected">
				<a href="###">
					<div class="plat-tools-bt-user"><span>&nbsp;</span></div>
					<c:if test="${not empty currentUser}">
					${currentUser.name}
					</c:if>
				</a>
			</li>
			 <li class="ThemeTools ">
						<a href="javascript:void(0);">
							<div class="plat-tools-bt-setting"><span>&nbsp;</span></div>
							<span>设置</span>
							<div class="plat-tools-bt-icodown">
								<span>&nbsp;</span>
							</div>
						</a>
						<ul id="settingSelector"
							class="plat-theme-list setting1">
							<li class="nolast" datatype="subtitle"><div class="plat-tools-bt-theme">
								<span>&nbsp;</span>
							</div>&nbsp;<span>切换主题</span></li>
							
							<li class="nolast sub" datatype="theme" themecolor="red">&nbsp;&nbsp;<div class="themeico red">
								<span>&nbsp;</span>
							</div>&nbsp;<span>中国红</span></li>
							<li class="nolast sub" datatype="theme" themecolor="green">&nbsp;&nbsp;<div class="themeico green">
								<span>&nbsp;</span>
							</div>&nbsp;<span>生命绿</span></li>
							<li class="nolast sub" datatype="theme" themecolor="blue">&nbsp;&nbsp;<div class="themeico blue">
								<span>&nbsp;</span>
							</div>&nbsp;<span>经典蓝</span></li>
							<li class="nolast" datatype="changepass"><div class="plat-tools-bt-setting"><span>&nbsp;</span></div>&nbsp;<span>修改密码</span></li>
						</ul>
					</li>
					<li style="padding-left: 0px;">
								<a id="logoutButton" href="javascript:void(0);">
										<div class="plat-tools-bt-logout">
											<span>&nbsp;</span>
										</div> 退出
								</a>
					</li>
		</ul>
	</div>
</div>
</div>
<!--主显示区-->
	<div id="contentwrap" class="plat-content-wrap ">
		<div id="content" class="plat-content"></div>
	</div>

	<!--页面脚部文件-->
	<div id="footer" class="plat-footer">
		<div class="plat-footer-inner">版权所有<span class="cc">©</span>沈阳工大普日软件技术有限公司</div>
	</div>



</body>
</html>
