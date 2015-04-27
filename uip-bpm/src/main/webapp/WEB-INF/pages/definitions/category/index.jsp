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
<!--框架必需start-->
<script type="text/javascript" src="/file/libs/js/jquery.js"></script>
<script type="text/javascript" src="/file/libs/js/framework.js"></script>
<link href="/file/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="/file/"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!--树组件start -->
<script type="text/javascript" src="/file/libs/js/tree/ztree/ztree.js"></script>
<link href="/file/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!--树组件end -->


<!--数据表格start-->
<script src="/file/libs/js/table/quiGrid.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/dialog.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/drag.js"></script>
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

		<fieldset> 
			<legend>检索条件</legend>
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
						<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置查询</span></button></td>
					</tr>
				</table>
				</form>
				</fieldset>
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
	//定义grid
	var grid = null;
	//定义选中的树节点
	var selectTreeNode = null;
	
	//树属性配置
	var selectionSetting = {
		//view: {dblClickExpand: true},
		callback: {onClick: zTreeSelect}
	};
	
	//初始化函数
	function initComplete(){
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
    	 		parent:nodes[0].extendId,
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
			url : '<%=basePath%>definitions/category/tree/query',
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
	    var columns = [{ display: '名称', name: 'name', align: 'center', width: "25%",editor: { type: 'text'}},
	            	   { display: '编号', name: 'code', align: 'center', width: "25%", editor: { type: 'text'}},
	            	   { display: '序号', name: 'sequence', align: 'center', width: "25%", editor: { type: 'text'}},
	            	   { display: '备注', name: 'description', align: 'center', width: "25%", editor: { type: 'text'}},
	            	  ];
		    grid = $("#dataBasic").quiGrid({
		    		columns:columns,
		            url:'<%=basePath%>definitions/category/query',
		            rownumbers:true,
		            checkbox:true,
		            isScroll:true,
		            enabledEdit: true,
		            height: '100%',
		            percentWidthMode:true,
		            width:'100%',
		            pageSize:20,
		            clickToEdit: false,
                	onBeforeEdit: onBeforeEdit,
                	onCheckRow:function(checked, rowdata, rowindex, rowDomElement){
    		    	var rows = grid.getCheckedRows();
    		    	for(var i = 0; i < rows.length; i++){
    		    		grid.unselect(rows[i].__id);
    		    	}
    		    	grid.select(rowindex);
        			//初始化tree
     				
         			},
                	onBeforeSubmitEdit: onBeforeSubmitEdit,
                	onAfterSubmitEdit: onAfterSubmitEdit,
                	toolbar:{items:[{text: '新增', click: addUnit,    iconClass: 'icon_add'},
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
    

	//新增
	function addUnit() {
		var parentExtendId = "";
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		 var nodes = treeObj.getNodes();
				
		 if(nodes.length > 0){
		 	parentExtendId=nodes[0].extendId;
		 }
		
		if(selectTreeNode != null){
			parentExtendId=selectTreeNode.extendId;
		}
		var diag = new top.Dialog();
		$.extend(diag,myproper.defaultGridDialogOptions);
		diag.Title = "添加";
		diag.Width = 450;
		diag.Height = 220;
		diag.properType="add";
		diag.URL='<%=basePath %>definitions/category/add?parentExtendId='+ parentExtendId,
	    diag.OKEvent = function(){
	     var valid = diag.innerFrame.contentWindow.valids(); 
	        if(valid){     
		      	$.post(
		        	"<%=path%>/definitions/category/save?parentExtendId="+ parentExtendId,
		       		diag.innerFrame.contentWindow.getParams(),
		           	function(result){
		        		console.log(result);
		        		if(result == "SYS_SUCCESS"){
		           			myproper.defaultFuncs.addSuccess(grid,diag);
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
		var id = selectedRow.id;
		
		var parentExtendId = "";
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		 var nodes = treeObj.getNodes();
				
		 if(nodes.length > 0){
		 	parentExtendId=nodes[0].extendId;
		 }
		
		if(selectTreeNode != null){
			parentExtendId=selectTreeNode.extendId;
		}
		var diag = new top.Dialog();
		$.extend(diag,myproper.defaultGridDialogOptions);
		diag.Title = "修改";
		diag.Width = 450;
		diag.Height = 220;
		diag.properType="update";
		diag.URL='<%=basePath %>definitions/category/update?parentExtendId='+ parentExtendId + '&id=' + id,
	    diag.OKEvent = function(){
	     var valid = diag.innerFrame.contentWindow.valids(); 
	        if(valid){     
		      	$.post(
		        	"<%=path%>/definitions/category/save?parentExtendId="+ parentExtendId,
		       		diag.innerFrame.contentWindow.getParams(),
		           	function(result){
		        		console.log(result);
		        		if(result == "SYS_SUCCESS"){
		           			myproper.defaultFuncs.addSuccess(grid,diag);
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
		Dialog.confirm("确定要删除该记录吗？",function(){
			var selectedRow = grid.getSelectedRow();
			var id = selectedRow.id;
		  	//删除记录
			$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>definitions/category/validateDel?id='+id,
			  success:function(result){
				  if(result == "SYS_SUCCESS"){
					  grid.loadData();  
				  }
				  Dialog.confirm(result,function(){
				  	$.ajax({
						  type: 'POST',
						  url: '<%=basePath %>definitions/category/del?id='+id,
						  success:function(result){
							  if(result == "SYS_SUCCESS"){
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
    	var query = null;
    	if(selectTreeNode == null){
    		query = {
    	    	 	name:$("#name").val(),
    	    	 };
    	
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
</script>	
</body>
</html>