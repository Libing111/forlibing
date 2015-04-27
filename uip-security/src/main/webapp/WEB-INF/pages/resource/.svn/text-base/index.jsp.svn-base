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
<title>资源管理</title>
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
		<td class="ver01" >
			 <div class="box1"  overflow="true" showStatus="false" panelTitle="组织结构树">
			 	<div class="cusBoxContent"  style="width:200px;">
			  		<ul id="tree" class="ztree"></ul>
			  	</div>
		  	</div>
		</td>
		<!--左侧区域end-->
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
			<div class="box1" panelTitle="查询" showStatus="false" id="searchPanel">
				<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
				<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
				<table>
					<tr>
						<td>名称：</td>
						<td>
							<input type="text" id="searchInput" name="userinfor.userName"/>
							<input type="text" style="width:2px;display:none;"/>
						</td>
						<td>分类：</td>
						<td>
							<input type="text" id="searchInputCategory" name="userinfor.userName"/>
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
		<!--右侧区域end-->
	</tr>
	</table>
<script type="text/javascript">
	function closeWin(){
		Dialog.close();
	}
	
	
	 var resourceCategoryList = <%=JsonUtil.array2JSON(request.getAttribute("categoryList"))%>;
       var resourceCategoryData = {"list":[]};
       var resourceCategory;
       for(var i = 0; i < resourceCategoryList.length; i++){
       		resourceCategory = {'value':resourceCategoryList[i].code, 'key':resourceCategoryList[i].name};
       		
       		resourceCategoryData.list.push(resourceCategory);
       }
	//定义grid
	var grid = null;
	//定义选中的树节点
	var selectTreeNode = null;
	
	
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
		
		//初始化grid组件
		initGrid();
		 $("#searchPanel").bind("stateChange",function(e,state){
				grid.resetHeight();
		});
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
		 	selectTreeNode = nodes[0];
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
			url : '<%=basePath%>resource/tree/query',
			data : null,
			success : function(result){
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
		grid = $("#dataBasic").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
				{ display: '名称', name: 'name', align: 'left', width: "10%",editor: { type: 'text'}},
				{ display: '简称', name: 'simpleName', align: 'left', width: "10%",editor: { type: 'text'}},
	            { display: '编号', name: 'code', align: 'left', width: "10%", editor: { type: 'text'}},
	            { display: '序号', name: 'sequenceNumber', align: 'left', width: "10%", editor: { type: 'text'}},
	            { display: 'url', name: 'url', align: 'left', width: "15%", editor: { type: 'text'}},
	            {display: '分类', name: 'categoryCode',align: 'left',width:"10%", editor: { type: 'select',data:resourceCategoryData,selWidth:100},render:function (item){
                	for (var i = 0; i < resourceCategoryData["list"].length; i++)
                    {
                        if (resourceCategoryData["list"][i]['value'] == item.categoryCode)
                            return resourceCategoryData["list"][i]['key'];
                    }
                    return item.categoryName;
            	}},
	            { display: '图标', name: 'icon', align: 'left', width: "15%", editor: { type: 'text'}},
	            { display: '匿名资源', name: 'anonymously', align: 'left', width: "10%", render : function(rowdata, rowindex, value, column){
					if (value == false) {
						return "不是";
					}
					return "是";
                }},
	            { display: '备注', name: 'description', align: 'left', width: "10%", isSort:false,editor: { type: 'textarea'}}
			  ],
		 url:'<%=basePath%>resource/model/query', 
		 rownumbers:true,
		 checkbox:true,
		 //enabledEdit: true,
         height: '100%',
         percentWidthMode:true, 
         width:"100%",
         pageSize:10,
        // clickToEdit: false,
         onDblClickRow:function(rowdata, rowindex){
            		grid.beginEdit(rowindex);
          },
          onCheckRow:function(checked, rowdata, rowindex, rowDomElement){
    		    	var rows = grid.getCheckedRows();
    		    	for(var i = 0; i < rows.length; i++){
    		    		grid.unselect(rows[i].__id);
    		    	}
    		    	grid.select(rowindex);
         },
         toolbar:{
        	 items:[
        		  {text: '新增', click: addUnit,    iconClass: 'icon_add'},
        		  { line : true },
        		  {text: '修改', click: updateUnit,    iconClass: 'icon_edit'},
        		  { line : true },
        		  {text: '删除', click: deleteUnit, iconClass: 'icon_delete'},
        		  { line : true }
        		]
         	}
		});
	}
		function scrollHandler(scrollY,scrollX,anmition){
    		grid.setScroller(scrollY,scrollX,anmition);
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
		//新增
		function addUnit() {
			var parentCode=selectTreeNode.id;
			Dialog.open({
				URL:'<%=basePath %>resource/add?parentCode='+parentCode,
				Title:"添加",
				Width:600,
				Height:400
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
			var rows = grid.getSelectedRows();
			var rowsLength = rows.length;
			
			if(rowsLength > 1) {
				top.Dialog.alert("请选择单条数据!");
				return;
			}
			var parentCode=selectTreeNode.id;
			Dialog.open({
				URL:'<%=basePath %>resource/update?parentCode=' + parentCode + '&id=' + id,
				Title:"修改",
				Width:600,
				Height:400
				});
		}
		
	//删除
		function deleteUnit(){
			var selectedRow = grid.getSelectedRow();
			if(selectedRow == null){
				top.Dialog.alert("请选择要删除的数据！");
				return;
			}
			var rows = grid.getSelectedRows();
			var rowsLength = rows.length;
			
			if(rowsLength > 1) {
				top.Dialog.alert("请选择单条数据!");
				return;
			}
			var id = selectedRow.id;
			top.Dialog.confirm("确定要删除该数据吗？",function(){
				$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>resource/del?ids='+id,
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
// 	function deleteUnit() {
// 		var rows = grid.getSelectedRows();
// 		var rowsLength = rows.length;
		
// 		if(rowsLength == 0) {
// 			top.Dialog.alert("请选中要删除的记录!");
// 			return;
// 		}
// 		top.Dialog.confirm("确定要删除吗？",function(){
// 			$.post('<%=basePath %>resource/del',
					//获取所有选中行
// 					getSelectIds(grid),
// 					function(result){
// 						handleResult(result);
// 					},
// 					"json");
// 		});
// 	}
	
	
	//删除后的提示
		function handleResult(result){
			if(result.status == 1){
				//刷新树
				initTree();
				//刷新表格
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
    	if(selectTreeNode == null){
    		return false;
    	}
    	 var query = {
    	 	parentId:selectTreeNode.id,
    	 	name:$("#searchInput").val(),
    	 	categoryName:$("#searchInputCategory").val(),
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
		     var parentCode=selectTreeNode.id;
        	//在这里一律作修改处理
			var rowData = e.newdata;
        	rowData.id = e.record.id;
            var returnValue = true;
          	$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>resource/save?parentCode='+parentCode,
			  data:rowData,
			  success:function(result){
						returnValue = unitResult(result);
						//刷新树
				    	initTree();
						
						grid.loadData();
					  },
			  dataType: "json",
			  async:false
			});  
            if(returnValue == false){
				return false;
			}
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
     		if(e.newdata.sequenceNumber==""){
     			top.Dialog.alert("序号不能为空！",null,null,null);
     			return false;
     		}
     		if (!validateInput(e.newdata.sequenceNumber, "^[0-9]*$")){
     			top.Dialog.alert("序号格式不对！",null,null,null);
     			return false;
     		}
     		var returnValue = true;
     		//if(rowData.id == null){
	     		$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>resource/querySequenceNumber',
				  data:rowData,
				  success:function(result){
							returnValue = unitResult(result);
						  },
				  dataType: "json",
				  async:false
				});
     		//}
			
			if(returnValue == false){
				return false;
			}
     		if(e.newdata.url==""){
     			top.Dialog.alert("url不能为空！",null,null,null);
     			return false;
     		}
     		
     		if(e.newdata.icon==""){
     			top.Dialog.alert("图标不能为空！",null,null,null);
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
   
</script>	
</body>
</html>