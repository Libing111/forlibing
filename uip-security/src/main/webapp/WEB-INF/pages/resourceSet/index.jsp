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
<title>资源集管理</title>
<!--框架必需start-->
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>

<!--框架必需end-->
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
			<td width="100%" class="ver01" >
				<div class="box1" panelTitle="查询" showStatus="false">
					<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
					<table>
						<tr>
							<td>名称：</td>
							<td>
								<input type="text" id="searchInput" name="role.name"/>
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
					<div class="padding_right5">
						<div id="dataBasic"></div>
					</div>
			</td>
			<!--左侧区域end-->
			<!--右侧区域start-->
			<td class="ver01" >
				 <div class="box1"  overflow="true" showStatus="false" panelTitle="组织结构树">
				 	<div class="cusBoxContent"  style="width:300px;">
					 	<div>
					  		<form>
						  		<table >
						  			<tr>
						  				<td>添加资源：</td>
						  				<td >
							  			<input type="button" value="修改" onclick="changeTreeNode()"/>&nbsp;
							  			<input type="button" value="确定" onclick="addTreeNode()"/>
						  			   </td>
						  			</tr>
						  		</table>
					  		</form>
				  		</div>
				  		<ul id="tree" class="ztree"></ul>
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
	var grid = null;
	//定义选中的树节点
	var selectTreeNode = null;
	var setting1 = {
	    check: { enable: true,
	    		 chkboxType: { "Y" : "ps", "N" : "ps" }
	    		 //chkDisabled: true
				},
		callback: {
	        onCheck: onCheck11
   		 }
	};
	
	//初始化函数
	function initComplete(){
		//当提交表单刷新本页面时关闭弹窗
		//top.Dialog.close();
		
		//初始化tree
		initTree(null,true);
		
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
	function initTree(resourceSetId,chkDisabled) {
		
		$.ajax({
			type : 'POST',
			url : '<%=basePath%>resourceSet/resourceTree/query',
			data : {resourceSetId:resourceSetId,chkDisabled:chkDisabled},
			success : function(result){
				//nodes = nodes.concat(result.treeNodes);
				$.fn.zTree.init($("#tree"), setting1, result);
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
		grid = $("#dataBasic").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', hide:true},
				{ display: '名称', name: 'name', align: 'left', width: "30%",editor: { type: 'text'}},
	            { display: '编号', name: 'code', align: 'left', width: "30%", editor: { type: 'text'}},
	            { display: '备注', name: 'description', align: 'left', width: "40%", isSort:false,editor: { type: 'textarea'}}
			  ],
		 url:'<%=basePath%>resourceSet/query',
		 rownumbers:true,
		 checkbox:true,
		 isScroll:true,
		 enabledEdit: true,
         height: '100%',
         percentWidthMode:true, 
         width:"100%",
         pageSize:5,
         clickToEdit: false,
         
         onDblClickRow:function(rowdata, rowindex){
            		grid.beginEdit(rowindex);
                },
         onCheckRow:function(checked, rowdata, rowindex, rowDomElement){
    		    	var rows = grid.getCheckedRows();
    		    	for(var i = 0; i < rows.length; i++){
    		    		grid.unselect(rows[i].__id);
    		    	}
    		    	grid.select(rowindex);
        			//初始化tree
     				initTree(rowdata.id,true);
         },
         onBeforeEdit: onBeforeEdit, 
         onBeforeSubmitEdit: onBeforeSubmitEdit,
         onAfterSubmitEdit: onAfterSubmitEdit,
         toolbar:{
        	 items:[
        		  {text: '新增', click: addUnit,    iconClass: 'icon_add'},
        		  { line : true },
        		  {text: '修改', click: updateUnit,    iconClass: 'icon_edit'},
        		  { line : true },
        		  {text: '删除', click: deleteUnit,    iconClass: 'icon_delete'},
        		  { line : true }
        		]
         	}
		});
		
	}
	
		//新增
		function addUnit() {
			Dialog.open({
				URL:'<%=basePath %>resourceSet/add',
				Title:"添加",
				Width:600,
				Height:300
				});
		}
	//修改
		function updateUnit() {
			var selectedRow = grid.getSelectedRow();
			if(selectedRow == null){
				top.Dialog.alert("请选择数据！");
				return;
			}
			var id = selectedRow.id;
			Dialog.open({
				URL:'<%=basePath %>resourceSet/update?id=' + id,
				Title:"修改",
				Width:600,
				Height:350
				});
		}
		function deleteUnit(){
		var selectedRow = grid.getSelectedRow();
			if(selectedRow == null){
				top.Dialog.alert("请选择要删除的数据！");
				return;
			}
			Dialog.confirm("确定要删除该记录吗？",function(){
				var id = selectedRow.id;
			  	//删除记录
				$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>resourceSet/del?ids='+selectedRow.id,
				  success:function(result){
					  if(result.status == 1){
						grid.loadData();
					   }else{top.Dialog.alert(result.SYS_ERROR);}
				  },
				  dataType: "json",
				  //async:false
				});		
				//刷新表格
				
				grid.loadData();	
				initTree(null,true);	
			});	
		}
		//全部确认修改
       function endAllEdit(){
        	grid.endEdit();
        }
         //全部取消修改
        function cancelAllEdit()
        {
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
        function endEdit(rowid)
        {
            grid.endEdit(rowid);
        }
	
	//删除
		function onDelete(rowidx){
			top.Dialog.confirm("确定要删除该记录吗？",function(){
			  	//删除记录
			  	var row = grid.getRow(rowidx);
				$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>resourceSet/del?ids='+row.id,
				  success:function(result){
							handleResult(result);
						  },
				  dataType: "json",
				 // async:false
				});		
				//刷新表格
				grid.loadData();		
			});
				
		}
		
		
	//批量删除
	function deleteUnit() {
		var rows = grid.getSelectedRows();
		var rowsLength = rows.length;
		
		if(rowsLength == 0) {
			top.Dialog.alert("请选中要删除的记录!");
			return;
		}
		top.Dialog.confirm("确定要删除吗？",function(){
			$.post('<%=basePath %>resourceSet/del',
					//获取所有选中行
					getSelectIds(grid),
					function(result){
						handleResult(result);
					},
					"json");
		});
	}
	
	
	//删除后的提示
		function handleResult(result){
		var rowData = grid.getSelectedRows();
			if(result.status == 1){
				//top.Dialog.alert("删除成功！",null,null,null);
				grid.loadData();
				return;
			}
			
			top.Dialog.alert(result.SYS_ERROR);
		}
	//获取所有选中行获取选中行的id 格式为 ids=1&ids=2 
	function getSelectIds(grid) {
		var selectedRows = grid.getSelectedRows();
		var selectedRowsLength = selectedRows.length;
		var ids = "";
		
		for(var i = 0;i<selectedRowsLength;i++) {
			ids += selectedRows[i].userId + ",";
		}
		return {"ids":ids};
	}

	//点击树节点刷选对应的表格数据 
	function zTreeSelect(event,treeId,treeNode) {
		selectTreeNode = treeNode;
		var query = {
    	 	parentId:treeNode.id,
    	 };
    	grid.setOptions({ params : query});
		//页号重置为1
		grid.setNewPage(1);
		//刷新表格数据 
		grid.loadData();
	}
	
    
    //查询
    function searchHandler(){
    	
    	 var query = {
    	 	//parentId:selectTreeNode.id,
    	 	name:$("#searchInput").val(),
    	 };
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
         	var str="编辑前事件，可阻止某些行或列进行编辑。列名："+e.column.name+"；行号："+e.rowindex+"；编辑前的值："+e.value+"\n";
     }
        
		//编辑后事件 
    function onAfterSubmitEdit(e){
        	//在这里一律作修改处理
			var rowData = e.newdata;
        	rowData.id = e.record.id;
          	$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>resourceSet/save',
			  data:rowData,
			  success:function(result){
						grid.loadData();
					  },
			  dataType: "json",
			  async:false
			});  
            
    }
    
     //编辑提交前事件 
        function onBeforeSubmitEdit(e){
       		var rowData = e.newdata;
       		rowData.id = e.record.id;
     		if(e.newdata.name==""){
     			top.Dialog.alert("名称不能为空！",null,null,null);
     			return false;
     		}
     		if(e.newdata.code==""){
     			top.Dialog.alert("编号不能为空！",null,null,null);
     			return false;
     		}
     		var returnValue = true;
	     		$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>resourceSet/queryUnique',
				  data:rowData,
				  success:function(result){
							returnValue = unitResult(result);
						  },
				  dataType: "json",
				  async:false
				});
			if(returnValue == false){
				return false;
			}
        }
       
        //判断序号是否唯一
		function unitResult(result){
			if(result.status != 1){
				top.Dialog.alert(result.SYS_ERROR);
				return false;
			}
			return true;
		}
		
		//获取所有选中行获取选中行的id 格式为 ids=1&ids=2 
	function getSelectIds(grid) {
		var selectedRows = grid.getSelectedRows();
		var selectedRowsLength = selectedRows.length;
		var ids = "";
		
		for(var i = 0;i<selectedRowsLength;i++) {
			ids += selectedRows[i].id + ",";
		}
		return {"ids":ids};
	}	
	
	function onCheck11(event, treeId, treeNode){
	   
	}
	function changeTreeNode(){
		var selectedRow = grid.getSelectedRow();
			if(selectedRow != null){
				var resourceSetId = selectedRow.id;
				initTree(resourceSetId,false);
			} 
		
	 }
	 
	function addTreeNode(){
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getCheckedNodes();
		
		var sequenceNumbers = "";
		for(var node = 0; node <nodes.length; node++){
			sequenceNumbers = sequenceNumbers + "," + nodes[node].id; 
		}

		sequenceNumbers = sequenceNumbers.substring(1, sequenceNumbers.length);
		var selectedRow = grid.getSelectedRow();
		if(selectedRow == null){
			top.Dialog.alert("请选择角色！",null,null,null);
				
		}
		var resourceSetId = selectedRow.id;
		$.ajax({ url: '<%=basePath%>resourceSet/tree/update',
			     async:false,
			     type:"post",
			     data: {resourceSetId:resourceSetId,resourceSequenceNumbers:sequenceNumbers},
			     success: function(result){
			    	 if(result == "SYS_SUCCESS"){
			    		 top.Dialog.alert("保存成功！",null,null,null);
			    		 initTree(resourceSetId,true);
			    	 }
			     },
			 	 error: function(xhr, textStatus, errorThrown){
			 	}
			});
 
		
	 }

</script>
</body>
</html>