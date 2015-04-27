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
<title>监控人设置</title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>
<!--框架必需end-->

<!--树组件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/tree/ztree/ztree.js"></script>
<link href="<%=contextPath%>file/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!--树组件end -->


<!--数据表格start-->
<script src="<%=contextPath%>file/libs/js/table/quiGrid.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/dialog.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/drag.js"></script>
<!--数据表格end-->

</head>
<body>
<table width="100%" >
	<tr>
	
	<!--左侧区域start-->
		<td width="30%" class="ver01" >
		<fieldset> 
			<legend>查询</legend>
			<div id="searchPanel">
				<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
					<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
					<table>
						<tr>
							<td>名称：</td>
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
		<!--右侧区域start-->
		<td  width="70%" class="ver01" >
				<div panelTitle="监控人列表" showStatus="false">
				<div class="cusBoxContent">
					<div class="padding_right5">
						<div id="monitor"></div>
					</div>
				</div>
				</div>	
			</td>
		<!--右侧区域end-->
	</tr>
	</table>
	
<script type="text/javascript">
	function closeWin(){
		Dialog.close();
	}
	
	//定义grid
	var processDefinitionGrid = null;
	var monitorGrid = null;
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
			    { display: '流程名称', name: 'name', 	 align: 'left', width: "50%"},
			    { display: '版本', name: 'version',     align: 'left', width: "50%"},
			  ],
		 url: '<%=path%>/definitions/query', 
		 sortName: 'id',
		 rownumbers:true,
		 checkbox:true,
         height: '100%', 
         width:"100%",
         pageSize:20,
         percentWidthMode:true,
         onSelectRow:function(rowdata, rowindex, rowDomElement){
            var query = {
						processDefinitionId: rowdata.id,
					};
			monitorGrid.setOptions({ params : query});
			monitorGrid.loadData();
         },
         onCheckRow:function(checked, rowdata, rowindex, rowDomElement){
    		    	var rows = processDefinitionGrid.getCheckedRows();
    		    	for(var i = 0; i < rows.length; i++){
    		    		processDefinitionGrid.unselect(rows[i].__id);
    		    	}
    		    	processDefinitionGrid.select(rowindex);
         }
		});
	}
		//初始化monitorGrid处理
	function initTaskDefinitionGrid() {
		monitorGrid = $("#monitor").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
	            { display: '监控人', name: 'identityLinkNames', align: 'left', width: "30%", isSort:false,editor: { type: 'text'}},
	            { display: '所在机构', name: 'raName', align: 'left', width: "40%", isSort:false,editor: { type: 'text'}},
			    { display: '类别', name: 'typeName', align: 'left', width: "30%", isSort:false,editor: { type: 'text'}},
			  ],
		 url:'<%=basePath%>/definitions/monitor/query', 
		  rownumbers:true,
		 checkbox:true,
		 enabledEdit: true,
         height: '100%',
         percentWidthMode:true, 
         width:"100%",
         pageSize:20,
         clickToEdit: false,
         toolbar:{
        	 items:[
        		  {text: '设置用户', click: addUser, iconClass: 'icon_ok' },
        		  { line : true },
        		  {text: '删除监控人', click: deleteMonitor, iconClass: 'icon_delete'},
        		]
         	}
		});
	}
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
	}
	
	

	//设置经办人用户
	function addUser() {
		var row = processDefinitionGrid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择流程定义!");
			return false;
		}
		Dialog.open({
				URL:'<%=basePath %>definitions/monitor/addUser?processDefinitionId='+row.id,
				Title:"设置用户",
				Width:600,
				Height:400
				});
	}
	
	//删除步骤
	function deleteMonitor(){
			var row = monitorGrid.getSelectedRow();
			if(row == null){
				top.Dialog.alert("请选择监控人!");
				return false;
			}
			top.Dialog.confirm("确定要删除该记录吗？",function(){
			  	//删除记录
			  	var row = monitorGrid.getSelectedRow();
				$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>definitions/monitor/del?ids='+row.id,
				  success:function(result){
						if(result.status == 1){
							monitorGrid.loadData();
							return;
						}
						top.Dialog.alert(result.SYS_ERROR);
				  },
				  dataType: "json",
				});		
				//刷新表格
				monitorGrid.loadData();		
			});
	}
	 //查询
    function searchHandler(){
    	 var query = {
    		name:$("#searchInput").val(),
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
</body>
</html>