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
<title>经办人设置</title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>
<!--框架必需end-->

<!-- 树组件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/tree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>file/libs/js/tree/ztree/ztree.css"></link>
<!-- 树组件end -->
<!-- 树形下拉框start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/selectTree.js"></script>
<!-- 树形下拉框end -->


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
			taskDefinitionGrid.setOptions({ params : query});
			taskDefinitionGrid.loadData();
         },
         onCheckRow:function(checked, rowdata, rowindex, rowDomElement){
    		    	var rows = processDefinitionGrid.getCheckedRows();
    		    	for(var i = 0; i < rows.length; i++){
    		    		processDefinitionGrid.unselect(rows[i].__id);
    		    	}
    		    	processDefinitionGrid.select(rowindex);
         },
		});
	}
		//初始化taskDefinitionGrid处理
	function initTaskDefinitionGrid() {
		taskDefinitionGrid = $("#taskDefinition").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
				{ display: '步骤名称', name: 'taskDefinitionName', align: 'left', width: "15%",editor: { type: 'text'}},
				{ display: '步骤代码', name: 'taskDefinitionKey', align: 'left', width: "15%",editor: { type: 'text'}},
// 				{ display: '场景分类', name: 'strategyName', align: 'left', width: "15%",editor: { type: 'text'}},
// 				{ display: '场景', name: 'scenarioItemName', align: 'left', width: "15%",editor: { type: 'text'}},
	            { display: '经办人', name: 'identityLinkNames', align: 'left', width: "40%", isSort:false,editor: { type: 'text'}},
			  ],
		 url:'<%=basePath%>/definitions/assignment/taskDefinitions/query', 
		  rownumbers:true,
		 checkbox:true,
		 enabledEdit: true,
		 usePager:false,
         height: '100%',
         percentWidthMode:true, 
         width:"100%",
         pageSize:5,
//          fixedCellHeight:false,
         clickToEdit: false,
         toolbar:{
        	 items:[
				  {text: '流程图', click: pictrue,    iconClass: 'icon_add'},
				  { line : true },
        		  {text: '新增步骤', click: addTaskDefinitions,    iconClass: 'icon_add'},
        		  { line : true },
        		  {text: '修改步骤', click: updateTaskDefinitions,    iconClass: 'icon_edit'},
        		  { line : true },
        		  {text: '删除', click: deleteTaskDefinitions, iconClass: 'icon_delete'},
        		  { line : true },
        		  {text: '设置经办人', click: addUser, iconClass: 'icon_ok' },
        		  { line : true }
        		  //{text: '设置人员', click: addPersonnel, iconClass: 'icon_ok' },
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
			    diag.URL = "<%=path%>/definitions/assignment/bpm?processDefinitionId="+row.id,
			    diag.Width=600;
			    diag.Height=400;
			    diag.show();	    
		}
	//新增步骤
	function addTaskDefinitions() {
		var row = processDefinitionGrid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择流程定义!");
			return false;
		}
		Dialog.open({
				URL:'<%=basePath %>definitions/assignment/taskDefinitions/add?processDefinitionId='+row.id,
				Title:"新增步骤",
				Width:400,
				Height:300
				});
	}
	
	//修改步骤
	function updateTaskDefinitions() {
		
		var rowd = processDefinitionGrid.getSelectedRow();
		if(rowd == null){
			top.Dialog.alert("请选择流程定义!");
			return false;
		}
		
		var rows = taskDefinitionGrid.getSelectedRows();
		if(rows == null || rows.length == 0){
			top.Dialog.alert("请选择流程步骤!");
			return false;
		}
		if(rows.length > 1){
			top.Dialog.alert("只能选中一条流程步骤!",null,null,null);
			return false;
		}
		
		var row = rows[0];
// 		if(row == null){
// 			top.Dialog.alert("请选择步骤!");
// 			return false;
// 		}
		Dialog.open({
				URL:'<%=basePath %>definitions/assignment/taskDefinitions/update?id='+row.id+'&processDefinitionId='+rowd.id,
				Title:"修改步骤",
				Width:400,
				Height:300
				});
	}
	
	//删除
	function deleteTaskDefinitions() {
		var rowd = processDefinitionGrid.getSelectedRow();
		if(rowd == null){
			top.Dialog.alert("请选择流程定义!");
			return false;
		}
		
		var rows = taskDefinitionGrid.getSelectedRows();
		if(rows == null || rows.length == 0){
			top.Dialog.alert("请选择流程步骤!");
			return false;
		}
		if(rows.length > 1){
			top.Dialog.alert("只能选中一条流程步骤!",null,null,null);
			return false;
		}
		
		var row = rows[0];
		
// 		var row = taskDefinitionGrid.getSelectedRow();
// 		if(row == null){
// 			top.Dialog.alert("请选择步骤!");
// 			return false;
// 		}
		top.Dialog.confirm("确定要删除吗？",function(){
			$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>definitions/assignment/taskDefinitions/del?id='+row.id+'&processDefinitionId='+rowd.id+'&taskDefinitionKey='+row.taskDefinitionKey+'&strategyId='+row.strategyId+'&scenarioItemId='+row.scenarioItemId,
				  success:function(result){
							handleResult(result);
						  },
				  dataType: "json",
				 // async:false
				});		
		});
	}
	
	
	//删除后的提示
		function handleResult(result){
			if(result.status == 1){
				//top.Dialog.alert("删除成功！",null,null,null);
				taskDefinitionGrid.loadData();
				return;
			}
			
			top.Dialog.alert(result.SYS_ERROR);
		}
	//设置经办人用户
	function addUser() {
		var row = taskDefinitionGrid.getSelectedRows();
		if(row == null || row.length == 0){
			top.Dialog.alert("请选择流程步骤!");
			return false;
		}
		if(row.length > 1){
			top.Dialog.alert("只能选中一条流程步骤，才能设置",null,null,null);
			return false;
		}
		var diag = new top.Dialog();
		$.extend(diag,myproper.defaultGridDialogOptions);
		diag.Title = "设置经办人";
		diag.Width = 750;
		diag.Height = 500;
		diag.ID = "identitylink";
		diag.properType="add";
		diag.URL='<%=basePath %>definitions/assignment/addUser?processDefinitionId='+row[0].processDefinitionId+'&taskDefinitionKey='+row[0].taskDefinitionKey+'&strategyId='+row[0].strategyId+'&scenarioItemId='+row[0].scenarioItemId,
	    diag.OKEvent = function(){
	        var valid = diag.innerFrame.contentWindow.valids(); 
	        if(valid){     
		      	$.post(
		        	'<%=basePath %>/definitions/assignment/saveAssignment',
		       		diag.innerFrame.contentWindow.getParams(),
		           	function(result){
		        		console.log(result);
		        		if(result == "SYS_SUCCESS"){
			        		top.Dialog.alert("成功提交",function(){ 
								taskDefinitionGrid.loadData();
								diag.close();
							});
		           			taskDefinitionGrid.loadData();
		           		}else{
		           			top.Dialog.alert("保存失败！");
		           		}
		 			},
		         	"json");
			}else{
				top.Dialog.alert(myproper.defaultMsgs.formInvalid);
			}
	    };
	    diag.show();
// 		top.Dialog.open({
// 				URL:'<%=basePath %>definitions/assignment/addUser?processDefinitionId='+row.processDefinitionId+'&taskDefinitionKey='+row.taskDefinitionKey+'&strategyId='+row.strategyId+'&scenarioItemId='+row.scenarioItemId,
// 				Title:"设置经办人",
// 				Width:750,
// 				Height:450,
// 				Modal:true
// 				});
	}
	//设置经办人人员
	function addPersonnel() {
		var row = taskDefinitionGrid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择流程步骤!");
			return false;
		}
		Dialog.open({
				URL:'<%=basePath %>definitions/assignment/addPersonnel?processDefinitionId='+row.processDefinitionId+'&taskDefinitionKey='+row.taskDefinitionKey,
				Title:"设置人员",
				Width:800,
				Height:500
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
    
     //查询
    function search(){
    	var row = processDefinitionGrid.getSelectedRow();
    	 var querys = {
    		taskDefinitionName:$("#taskDefinitionName").val(),
    		scenarioItemName:$("#scenarioItemName").val(),
    		identityLinkNames:$("#identityLinkNames").val(),
    		processDefinitionId: row.id,
    	 };
    	 taskDefinitionGrid.setOptions({ params : querys});
		 //页号重置为1
		 taskDefinitionGrid.setNewPage(1);
		//刷新表格数据 
		taskDefinitionGrid.loadData();
    }
	 
    //重置查询
    function resets(){
    	var row = processDefinitionGrid.getSelectedRow();
    	 var queryss = {
    		processDefinitionId: row.id,
    	 };
    	 taskDefinitionGrid.setOptions({ params : queryss});
		 //页号重置为1
		 taskDefinitionGrid.setNewPage(1);
		//刷新表格数据 
		taskDefinitionGrid.loadData();
		$("#query")[0].reset();
		search();
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
							<td>流程名称：</td>
							<td>
								<input type="text" id="searchInput" name="userinfor.userName"/>
								<input type="text" style="width:2px;display:none;"/>
							</td>
							<td width="4"></td>
							<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
							<td width="4"></td>
							<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button></td>
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
		<td  class="ver01" >
				
				<div  panelTitle="经办人列表" showStatus="false">
				<form  id="query" method="post">
				<div class="cusBoxContent"  style="width:750px;">
				<fieldset> 
					<legend>查询</legend>
					<table>
						<tr>
							<td>步骤名称：</td>
							<td>
								<input type="text" id="taskDefinitionName" name="taskDefinitionName"/>
								<input type="text" style="width:2px;display:none;"/>
							</td>
<!-- 							<td>场景：</td> -->
<!-- 							<td> -->
<!-- 								<input type="text" id="scenarioItemName" name="scenarioItemName"/> -->
<!-- 								<input type="text" style="width:2px;display:none;"/> -->
<!-- 							</td> -->
							<td>经办人：</td>
							<td>
								<input type="text" id="identityLinkNames" name="identityLinkNames"/>
								<input type="text" style="width:2px;display:none;"/>
							</td>
							<td width="4"></td>
							<td><button type="button" onclick="search()"><span class="icon_find">查询</span></button></td>
							<td width="4"></td>
							<td><button type="button" onclick="resets()"><span class="icon_reload">重置</span></button></td>
						</tr>
					</table>
					</fieldset>
					<div class="padding_right5">
						<div id="taskDefinition"></div>
					</div>
				</div>
				</form>
				</div>
					
			</td>
		<!--右侧区域end-->
	</tr>
	</table>
	

</body>
</html>