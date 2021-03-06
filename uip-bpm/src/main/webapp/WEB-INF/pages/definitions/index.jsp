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
<title>资源管理</title>
<!--框架必需start-->
<script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
<link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="/home/"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!--树组件start -->
<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
<link href="<%=path%>/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!--树组件end -->


<!--数据表格start-->
<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->

</head>
<body>
	<table width="100%" >
	<tr>
		<!--左侧区域start-->
		<td class="ver01" >
			 <div class="box1"  overflow="true" showStatus="false" panelTitle="组织结构树">
			 	<div class="cusBoxContent"  style="width:150px;">
			  		<ul id="tree" class="ztree"></ul>
			  	</div>
		  	</div>
		</td>
		<!--左侧区域end-->
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" > 
			<IFRAME id="workIframe" scrolling="no" width="100%" height="100%"  frameBorder=0  src="<%=path%>/definitions/assignment/index"></IFRAME>
		</td>
		<!--右侧区域end-->
	</tr>
	</table>
	
<script type="text/javascript">
	
	//定义grid
	var grid = null;
	//定义选中的树节点
	var selectTreeNode = null;
	
	//定义树节点初始数据
	var nodes =[
		{ id:"0", parentId:-1, name:"经办人设置", open:true,icon:"<%=path%>/libs/icons/home.gif"},
		{ id:"1", parentId:-1, name:"监控人设置", open:true,icon:"<%=path%>/libs/icons/home.gif"},
	];
	
	//树属性配置
	var selectionSetting = {
			view: {
				dblClickExpand: true
			},
			callback: {
				onClick: zTreeSelect
			}
	};
	//初始化函数
	function initComplete(){
		//初始化tree
		initTree();
		
		//监听查询框的回车事件
		 $("#searchInput").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		 })
	}
	
	//初始化tree处理
	function initTree() {
		$.fn.zTree.init($("#tree"), selectionSetting, nodes);
	}
	
	
	
	
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55)
		$("#workIframe").height(contentHeight-55)
		
	}
	
	//点击树节点刷选对应的表格数据 
	function zTreeSelect(event,treeId,treeNode) {
		var url = "<%=path%>/definitions/assignment/index";
		if(treeNode.id == "0"){
		    url = "<%=path%>/definitions/assignment/index";
		}
		if(treeNode.id == "1"){
			url = "<%=path%>/definitions/monitor/index";
		}
		
		$('#workIframe').attr('src', url);
		$('#workIframe').load();
	}
</script>	
</body>
</html>