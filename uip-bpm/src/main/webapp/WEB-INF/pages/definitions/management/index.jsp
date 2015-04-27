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
<title>流程定义分类设置</title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>
<!--框架必需end-->

<!--树组件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/tree/ztree/ztree.js"></script>
<link href="<%=contextPath%>file/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!--树组件end -->


<!--数据表格start-->
<script src="<%=contextPath%>file/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/dialog.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/drag.js"></script>
<script type="text/javascript">
	//定义grid
	var processDefinitionGrid = null;
	var taskDefinitionGrid = null;
	
	function closeWin(){
		Dialog.close();
	}
	
	//初始化函数
	function initComplete(){
		//初始化grid
		initProcessDefinitionGrid();
		
		initTaskDefinitionGrid();
	}

	//初始化Grid处理
	function initProcessDefinitionGrid() {
		processDefinitionGrid = $("#definitions").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
				{ display: '流程分类', name: 'categoryName', 	 align: 'left', width: "30%"},
			    { display: '流程名称', name: 'name', 	 align: 'left', width: "50%"},
			    { display: '版本', name: 'version',     align: 'left', width: "20%"},
			  ],
		 url: '<%=path%>/definitions/management/query', 
		 sortName: 'id',
		 rownumbers:true,
		 checkbox:true,
         height: '100%', 
         width:"100%",
         pageSize:20,
         percentWidthMode:true,
         onSelectRow:function(rowdata, rowindex, rowDomElement){
         },
         onCheckRow:function(checked, rowdata, rowindex, rowDomElement){
    		    	var rows = processDefinitionGrid.getCheckedRows();
    		    	for(var i = 0; i < rows.length; i++){
    		    		processDefinitionGrid.unselect(rows[i].__id);
    		    	}
    		    	processDefinitionGrid.select(rowindex);
         },
         toolbar:{
        	 items:[
				  {text: '流程图', click: pictrue,    iconClass: 'icon_add'},
				  { line : true },
        		  {text: '设置分类', click: addDefinitionCategory,    iconClass: 'icon_add'},
        		  { line : true }
        		]
         	}
		});
	}
	
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
	}
	//流程图
	function pictrue(){
		var row = processDefinitionGrid.getSelectedRow();
	       var diag = new top.Dialog();
			    diag.Title = "流程图";
			    diag.URL = "<%=path%>/definitions/management/bpm?processDefinitionId="+row.id,
			    diag.Width=600;
			    diag.Height=400;
			    diag.show();	    
		}
	//
	function addDefinitionCategory() {
		var row = processDefinitionGrid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择流程定义!");
			return false;
		}
		Dialog.open({
				URL:'<%=basePath %>definitions/management/add?id='+row.id,
				Title:"设置分类",
				Width:800,
				Height:500
				});
	}
	 //查询
    function searchHandler(){
    	 var query = {
    		name:$("#searchInput").val(),
    		categoryName:$("#categoryName").val(),
    	 };
    	 processDefinitionGrid.setOptions({ params : query});
		 //页号重置为1
		 processDefinitionGrid.setNewPage(1);
		//刷新表格数据 
		processDefinitionGrid.loadData();
    }
	 
    //重置查询
    function resetSearch(){
    	$("#queryForm")[0].reset();
		searchHandler();
    }
   
</script>	

</head>
<body>

<table width="100%" >
	<tr>
	<!--左侧区域start-->
	
		<td width="100%" class="ver01" >
		<fieldset> 
		<legend>查询</legend>
			<div  panelTitle="流程定义列表" showStatus="false" id="searchPanel">
				<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
					<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
					<table>
						<tr>
							<td>流程分类：</td>
							<td>
								<input type="text" id="categoryName" name="categoryName"/>
								<input type="text" style="width:2px;display:none;"/>
							</td>
							<td>流程名称：</td>
							<td>
								<input type="text" id="searchInput" name="userinfor.userName"/>
								<input type="text" style="width:2px;display:none;"/>
							</td>
							<td width="4"></td>
							<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
							<td width="4"></td>
							<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置查询</span></button></td>
						</tr>
					</table>
				</form>
			</div>
			</fieldset>
			<div class="padding_right5">
				<div id="definitions"></div>
			</div>
		</td>
		<!--左侧区域end-->
	</tr>
	</table>
	

</body>
</html>