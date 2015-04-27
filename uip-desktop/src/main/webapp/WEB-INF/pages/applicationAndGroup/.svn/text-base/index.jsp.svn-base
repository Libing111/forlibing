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
<title>分屏与栏目设置</title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>

<!--框架必需end-->

<style>
.l-grid-row-over .l-grid-row-cell,.l-grid-row-over {
	background: #ffffaf;
}

.l-selected .l-grid-row-cell,.l-selected {
	background: #ffffaf;
	background-repeat: repeat-x;
}

.left{
	background:#F2F2F2;
	color:#3f3f3f;
	font-weight:bold;
}
</style>
</head>
<body>
	<table width="100%" >
	<tr>
		<!--左侧区域start-->
		<td class="ver01" >
			 <div class="box1"  overflow="true" showStatus="false" panelTitle="组织结构树">
			 	<div class="cusBoxContent"  style="width:300px;">
			  		<ul id="tree" class="ztree"></ul>
			  	</div>
		  	</div>
		</td>
		<!--左侧区域end-->
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
			<div class="box1" panelTitle="查询" showStatus="false">
				<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
				<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
				<table>
					<tr>
						<td>名称：</td>
						<td>
							<input type="text" id="name" name="userinfor.userName"/>
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
				<div class="padding_right5">
					<div id="dataBasic"></div>
				</div>
		</td>
		<!--右侧区域end-->
	</tr>
	</table>
<script type="text/javascript">
	function closeWin(){
		Dialog.close();
	}
	systemCategory = "${systemCategory}";
	//定义grid
	var grid = null;
	//定义选中的树节点
	var selectTreeNode = null;
	
	//树属性配置
	var selectionSetting = {
		view: {dblClickExpand: true},
		callback: {onClick: zTreeSelect}
	};
	
	//初始化函数
	function initComplete(){
		//当提交表单刷新本页面时关闭弹窗
		//top.Dialog.close();
		
		//初始化tree
		initTree();
		//初始化grid组件
		initGrid();
		
		//监听查询框的回车事件
		$("#searchInput").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		});
		 
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getNodes();
		 if(nodes.length > 0){
			 	treeObj.selectNode(nodes[0]);
				var query = {
	    	 		parentId:nodes[0].id,
	    	 	};
	    		grid.setOptions({ params : query});
				//刷新表格数据 
				grid.loadData();
			 }
	}
	
	//初始化tree处理
	function initTree() {
		$.ajax({
			type : 'POST',
			url : '<%=basePath%>desktop-configuration/tree/query?systemCategory='+systemCategory,
			data : null,
			success : function(result){
				//nodes = nodes.concat(result.treeNodes);
				$.fn.zTree.init($("#tree"), selectionSetting, result);
			},
			error : function(a){
				top.Dialog.alert("访问服务器端出错！");
			},
			dataType : 'json',
			async:false
		});
	}
	
	//初始化Grid处理
	function initGrid() {
	    var columns = [{ display: '名称', name: 'name', align: 'left', width: "50%",editor: { type: 'text'}},
	            	   { display: '序号', name: 'sequenceNumber', align: 'left', width: "50%", editor: { type: 'text'}},
	            	  ];
		    grid = $("#dataBasic").quiGrid({
		    		columns:columns,
		            url:'<%=basePath%>desktop-configuration/query?systemCategory='+systemCategory,
		            rownumbers:true,
		            checkbox:true,
		            isScroll:true,
		            enabledEdit: true,
		            height: '100%',
		            percentWidthMode:true,
		            width:'100%',
		            pageSize:15,
		            clickToEdit: false,
		            onDblClickRow:function(rowdata, rowindex){
            				grid.beginEdit(rowindex);
                	},
                	onBeforeEdit: onBeforeEdit,
                	onBeforeSubmitEdit: onBeforeSubmitEdit,
                	onAfterSubmitEdit: onAfterSubmitEdit,
                	toolbar:{items:[
                			{text: '新增屏', click: addPing,    iconClass: 'icon_add'},
        		  			{ line : true },
                			{text: '新增栏目', click: addUnit,    iconClass: 'icon_add',disabled:true},
        		  			{ line : true },
        		  			{text: '新增应用', click: addUnitUse,    iconClass: 'icon_add',disabled:true},
        		  			{ line : true },
        		  			{text: '修改', click: updateUnit, iconClass: 'icon_edit'},
        		  			{ line : true },
        		  			{text: '删除', click: deleteUnit, iconClass: 'icon_delete'},
        		  			{ line : true }
        		  			]
                			}
		            });
	}//initGrid
		
	//全部确认修改
    function endAllEdit(){
        grid.endEdit();
    }
    //全部取消修改
    function cancelAllEdit(){
        grid.cancelEdit();
    }
    
	//编辑
	function beginEdit(rowid) { 
        grid.beginEdit(rowid);
    }
    //取消编辑
    function cancelEdit(rowid) { 
        grid.cancelEdit(rowid);
    }  
    //结束编辑
    function endEdit(rowid){
        grid.endEdit(rowid);
    }
	function addPing() {
		var parentExtendId = "";
		if(selectTreeNode != null){
			parentExtendId=selectTreeNode.extendId;
		}
		if(selectTreeNode == null){
		var diag = new top.Dialog();
		
		$.extend(diag,myproper.defaultGridDialogOptions);
		diag.Title = "新增屏信息";
		diag.properType="add";
	    diag.URL = '<%=basePath %>desktop-configuration/add?parentExtendId='+ parentExtendId + '&systemCategory=' + systemCategory;
	    diag.Width = 300;
	    diag.Height = 100;
	    diag.OKEvent = function(){
	        var valid = diag.innerFrame.contentWindow.valids(); 
	        if(valid){     
		      	$.post(
		        	"<%=path%>/desktop-configuration/save",
		       		diag.innerFrame.contentWindow.getParams(),
		           	function(result){
		        		console.log(result);
		        		if(result == "SYS_SUCCESS"){
		           			myproper.defaultFuncs.addSuccess(grid,diag);
		           			initTree();
		           		}else{
		           				top.Dialog.alert(result, function() { });
		           		}
		 			},
		         	"json");
			}else{
				top.Dialog.alert(myproper.defaultMsgs.formInvalid);
			}
	    };
	    diag.show();
	   }
	}

	function addUnit() {
		var parentExtendId = "";
		if(selectTreeNode != null){
			parentExtendId=selectTreeNode.extendId;
		}
		
		var diag = new top.Dialog();
		
		$.extend(diag,myproper.defaultGridDialogOptions);
		diag.Title = "新增栏目信息";
		diag.properType="add";
	    diag.URL = '<%=basePath %>desktop-configuration/add?parentExtendId='+ parentExtendId + '&systemCategory=' + systemCategory;
	    diag.Width = 300;
	    diag.Height = 100;
	    diag.OKEvent = function(){
	        var valid = diag.innerFrame.contentWindow.valids(); 
	        if(valid){     
		      	$.post(
		        	"<%=path%>/desktop-configuration/save",
		       		diag.innerFrame.contentWindow.getParams(),
		           	function(result){
		        		console.log(result);
		        		if(result == "SYS_SUCCESS"){
		           			myproper.defaultFuncs.addSuccess(grid,diag);
		           			initTree();
		           		}else{
		           				top.Dialog.alert(result, function() { });
		           		}
		 			},
		         	"json");
			}else{
				top.Dialog.alert(myproper.defaultMsgs.formInvalid);
			}
	    };
	    diag.show();
	}
		//新增应用
	function addUnitUse() {
		var parentExtendId = "";
		if(selectTreeNode != null){
			parentExtendId=selectTreeNode.extendId;
		}
		var diag = new top.Dialog();
		
		$.extend(diag,myproper.defaultGridDialogOptions);
		diag.Title = "新增应用信息";
		diag.properType="add";
	    diag.URL = '<%=basePath %>desktop-configuration/addResource?parentExtendId='+ parentExtendId + '&systemCategory=' + systemCategory;
	    diag.Width = 300;
	    diag.Height = 100;
	    diag.OKEvent = function(){
	        var valid = diag.innerFrame.contentWindow.valids(); 
	        var resourceId =  diag.innerFrame.contentWindow.getResource();
	        if(resourceId == ""){
	        	top.Dialog.alert("请选择资源");
	        	return;
	        }
	        if(valid){     
		      	$.post(
		        	"<%=path%>/desktop-configuration/save?resourceId="+resourceId,
		       		diag.innerFrame.contentWindow.getParams(),
		           	function(result){
		        		console.log(result);
		        		if(result == "SYS_SUCCESS"){
		           			myproper.defaultFuncs.addSuccess(grid,diag);
		           			initTree();
		           		}else{
		           				top.Dialog.alert(result, function() { });
		           		}
		 			},
		         	"json");
			}else{
				top.Dialog.alert(myproper.defaultMsgs.formInvalid);
			}
	    };
	    diag.show();
	}
	//修改
	function updateUnit() {
		var selectedRow = grid.getSelectedRow();
		var selectedRows = grid.getSelectedRows();
			var rowsLength = selectedRows.length;
			if(selectedRow == null) {
				top.Dialog.alert("请选择要修改的记录!");
				return;
			}	
			if(rowsLength > 1) {
				top.Dialog.alert("请选择单条修改数据!");
				return;
			}
		var parentExtendId = "";
		var id = selectedRow.id;
		if(selectTreeNode != null){
			parentExtendId=selectTreeNode.extendId;
		}
		if(selectTreeNode == null){
			moc = "paging";
			var diag = new top.Dialog();
			$.extend(diag,myproper.defaultGridDialogOptions);
			diag.Title = "修改屏信息";
			diag.properType="edit";
		    diag.URL = '<%=basePath %>desktop-configuration/update?parentExtendId='+ parentExtendId + '&id=' + id+ '&systemCategory=' + systemCategory;
		    diag.Width = 300;
		    diag.Height = 100;
		    diag.OKEvent = function(){
		        var valid = diag.innerFrame.contentWindow.valids(); 
		        if(valid){     
			      	$.post(
			        	"<%=path%>/desktop-configuration/save",
			       		diag.innerFrame.contentWindow.getParams(),
			           	function(result){
			        		console.log(result);
			        		if(result == "SYS_SUCCESS"){
			           			myproper.defaultFuncs.modifySuccess(grid,diag);
			           			initTree();
			           		}else{
			           				top.Dialog.alert(result, function() { });
			           		}
			 			},
			         	"json");
				}else{
					top.Dialog.alert(myproper.defaultMsgs.formInvalid);
				}
		    };
		    diag.show();
			return;
		}
		moc = selectTreeNode.moc;
		
		if(moc == "group"){
			var diag = new top.Dialog();
			
			$.extend(diag,myproper.defaultGridDialogOptions);
			diag.Title = "修改应用信息";
			diag.properType="edit";
		    diag.URL = '<%=basePath %>desktop-configuration/updateResource?parentExtendId=' + parentExtendId + '&id=' + id+ '&systemCategory=' + systemCategory;
		    diag.Width = 300;
		    diag.Height = 100;
		    diag.OKEvent = function(){
		    	var resourceId =  diag.innerFrame.contentWindow.getResource();
		    	if(resourceId == ""){
	        		top.Dialog.alert("请选择资源");
	        		return;
	        	}
		        var valid = diag.innerFrame.contentWindow.valids(); 
		        if(valid){     
			      	$.post(
			        	"<%=path%>/desktop-configuration/save?resourceId="+resourceId,
			       		diag.innerFrame.contentWindow.getParams(),
			           	function(result){
			        		console.log(result);
			        		if(result == "SYS_SUCCESS"){
			           			myproper.defaultFuncs.modifySuccess(grid,diag);
			           			initTree();
			           		}else{
			           				top.Dialog.alert(result, function() { });
			           		}
			 			},
			         	"json");
				}else{
					top.Dialog.alert(myproper.defaultMsgs.formInvalid);
				}
		    };
		    diag.show();
			return;
		}
		
			var diag = new top.Dialog();
		
			$.extend(diag,myproper.defaultGridDialogOptions);
			diag.Title = "修改栏目信息";
			diag.properType="edit";
		    diag.URL = '<%=basePath %>desktop-configuration/update?parentExtendId='+ parentExtendId + '&id=' + id+ '&systemCategory=' + systemCategory;
		    diag.Width = 300;
		    diag.Height = 100;
		    diag.OKEvent = function(){
		        var valid = diag.innerFrame.contentWindow.valids(); 
		        if(valid){     
			      	$.post(
			        	"<%=path%>/desktop-configuration/save",
			       		diag.innerFrame.contentWindow.getParams(),
			           	function(result){
			        		console.log(result);
			        		if(result == "SYS_SUCCESS"){
			           			myproper.defaultFuncs.modifySuccess(grid,diag);
			           			initTree();
			           		}else{
			           				top.Dialog.alert(result, function() { });
			           		}
			 			},
			         	"json");
				}else{
					top.Dialog.alert(myproper.defaultMsgs.formInvalid);
				}
		    };
		    diag.show();
	}
	function deleteUnit(){
		var rows = grid.getSelectedRows();
		var rowsLength = rows.length;
		
		if(rowsLength == 0) {
			top.Dialog.alert("请选中要删除的记录!");
			return;
		}
		var rowsLength = rows.length;
			
			if(rowsLength > 1) {
				top.Dialog.alert("请选择单条数据!");
				return;
			}
		Dialog.confirm("确定要删除该记录吗？",function(){
			var selectedRow = grid.getSelectedRow();
			var id = selectedRow.id;
		  	//删除记录
			$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>desktop-configuration/del?id='+id+ '&systemCategory=' + systemCategory,
			  success:function(result){
				  if(result == "SYS_SUCCESS"){
					  //top.Dialog.alert("删除成功！",null,null,null);
					  grid.loadData();  
				  }
					
			  },
			  dataType: "json",
			  async:false
			});		
			//刷新表格
			initTree();
			grid.loadData();		
		});
			
	}
	
	//获取所有选中行获取选中行的id 格式为 ids=1&ids=2 
	function getSelectIds(grid) {
		var selectedRows = grid.getSelectedRows();
		var selectedRowsLength = selectedRows.length;
		var ids = "";
		
		for(var i = 0;i<selectedRowsLength;i++) {
			ids = ids + "," + selectedRows[i].id;
		}
		
		if(ids != ""){
			ids = ids.substring(1, ids.length);
		}
		
		return {"ids":ids};
	}

	//点击树节点刷选对应的表格数据 
	function zTreeSelect(event,treeId,treeNode) {
		selectTreeNode = treeNode;
		if(selectTreeNode != null){
			parentExtendId=selectTreeNode.extendId;
			enabledButtonStatus('dataBasic',1,addUnit);
			enabledButtonStatus('dataBasic',2,addUnitUse);
			disabledButton('dataBasic',0);
			
		}
		moc = selectTreeNode.moc;
		if(moc == "group"){
			disabledButton('dataBasic',0);
			disabledButton('dataBasic',1);
			enabledButtonStatus('dataBasic',3,updateUnit);
			enabledButtonStatus('dataBasic',4,deleteUnit);
		}
		if(moc == "resource"){
			disabledButton('dataBasic',0);
			disabledButton('dataBasic',1);
			disabledButton('dataBasic',2);
			disabledButton('dataBasic',3);
			disabledButton('dataBasic',4);
		}
		var query = {
    	 	parent:treeNode.extendId,
    	 };
    	grid.setOptions({ params : query});
		//页号重置为1
		grid.setNewPage(1);
		//刷新表格数据 
		grid.loadData();
	}
	
	
    
    //查询
    function searchHandler(){
    	var query = null
    	if(selectTreeNode == null){
    		query = {
    	    	 	name:$("#name").val(),
    	    	 }
    	
    	}else{
	    	query = {
	    	 	parent:selectTreeNode.extendId,
	    	 	name:$("#name").val(),
	    	 	};
    	}
		 grid.setOptions({ params : query});
		 //页号重置为1
		 grid.setNewPage(1);
		//刷新表格数据 
		grid.loadData();
    }
    
    //重置查询
    function resetSearch(){
    	$("#queryForm")[0].reset();
    	searchHandler();
    }
    
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
	}
	//编辑前事件 
    function onBeforeEdit(e){
        //var str="编辑前事件，可阻止某些行或列进行编辑。列名："+e.column.name+"；行号："+e.rowindex+"；编辑前的值："+e.value+"\n";
    }   
	//编辑后事件 
    function onAfterSubmitEdit(e){
    	//在这里一律作修改处理
		var rowData = e.newdata;
        rowData.id = e.record.id;
        
        var url = "<%=basePath%>paging/add/standard";
        var parentNodeType = selectTreeNode.nodeType;
        if(parentNodeType != "node.type.root"){
            var pagingId = selectTreeNode.extendId;
        	url = "<%=basePath%>group/add/standard?pagingId=" + pagingId;
        }
        
        $.ajax({type: 'POST',
			    url: url,
			    data:rowData,
			    success:function(result){
			    	if(result  == "ERROR"){
			    		top.Dialog.alert("不能添加重复名称");
			    	}
			    	 if(result=="REPEAT"){
			    	top.Dialog.alert("每屏组上限为3组！");
			    	}
			    	
			    	if(result == "SYS_SUCCESS"){
			    		initTree();
			    		var treeObj = $.fn.zTree.getZTreeObj("tree");
						var nodes = treeObj.getNodes();
					
						for(var i = 0; i < nodes.length; i++){
							if(nodes[i].id != selectTreeNode.id){
							continue;
							}
							selectTreeNode = nodes[i];
						}
					
						treeObj.selectNode(selectTreeNode);
						treeObj.expandNode(selectTreeNode,true);	
			    	}
				    
				    //刷新表格数据 
					grid.loadData();
				},
				dataType: "json",
			  	//async:false
		});  
    }
    
    //编辑提交前事件 
    function onBeforeSubmitEdit(e){
        if(selectTreeNode == null){
        	top.Dialog.alert("没有选中树节点！",null,null,null);
     		return false;
        }
        
        var rowData = e.newdata;
        
     	if(rowData.name == null ||  rowData.name == ""){
     		top.Dialog.alert("名称不能为空！",null,null,null);
     		return false;
     	}
     	if(rowData.sequenceNumber == null || rowData.sequenceNumber == ""){
     		top.Dialog.alert("序号不能为空！",null,null,null);
     		return false;
     	}
     		
     	if (!validateInput(rowData.sequenceNumber, "^[0-9]*$")){
     		top.Dialog.alert("序号格式不对！",null,null,null);
     		return false;
     	}
	}
    
    //判断序号是否唯一
	function unitResult(result){
		if(result == -1){
			top.Dialog.alert("输入的编号已存在");
			return false;
		}
		
		if(result==-2){
			top.Dialog.alert("输入的名称已存在");
			return false;
		}

		if(result==-3){
			top.Dialog.alert("输入的节点号已存在");
			return false;
		}
		return true;
	}
	
	function enabledButtonStatus(gridId, index, funcName) {
		var btn = $($('.l-panel-topbar:first .l-panel-btn', $('#' + gridId))[index]);
		btn.unbind('click');
		btn.bind('click', funcName);
		btn.hover(function() {
			btn.addClass("l-panel-btn-over");
		}, function() {
			btn.removeClass("l-panel-btn-over");
		});
		btn.css({
			'color' : '#2C4D79',
			'cursor' : 'pointer'
		});
	}
	function disabledButton(gridId, index) {
		var btn = $($('.l-panel-topbar:first .l-panel-btn', $('#' + gridId))[index]);
		btn.unbind('click');
		btn.hover(function() {
			btn.removeClass("l-panel-btn-over");
		}, function() {
			btn.removeClass("l-panel-btn-over");
		});
		btn.css({
			"color" : "#999999",
			"cursor" : "default"
		});
	}
</script>	
</body>
</html>