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
<title>用户管理</title>
<!--框架必需start-->
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp"></jsp:include>

<!--框架必需end-->
<!--框架必需end-->

<!--树组件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/tree/ztree/ztree.js"></script>
<link href="<%=contextPath%>file/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!--树组件end -->


<!--数据表格start-->
<script src="<%=contextPath%>file/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->

</head>
<body>
	<table width="100%">
	<tr>
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
				<div class="box1" panelTitle="查询" showStatus="false">
					<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
					<table>
						<tr>
							<td>名称：</td>
							<td>
								<input type="text" id="searchInputRole" name="user.name"/>
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
						<div id="role"></div>
					</div>
			</td>
		<!--右侧区域end-->
		
		<!--左侧区域start-->
		<td class="ver01" >
				<div class="box1" panelTitle="查询"    showStatus="false">
				<div class="cusBoxContent"  style="width:100%;">
					<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
					<table>
						<tr>
							<td style="color:red;">注：单击可以删除角色</td>
						</tr>
					</table>
					</form>
					<div class="padding_right5">
						<div id="rolebbb"></div>
					</div>
				</div>
				</div>	
			</td>
		<!--左侧区域end-->
	</tr>
	</table>
<script type="text/javascript">
	
	var gridRole = null;
	var gridRolebbb = null;
	//初始化函数
	function initComplete(){
		initGridRole();
		initGridRolebbb();
		//监听查询框的回车事件
		 $("#searchInputRole").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		 });
	}
	
	//角色JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ
	//初始化Grid处理
	function initGridRole() {
		gridRole = $("#role").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
				{ display: '名称', name: 'name', align: 'left', width: "50%",editor: { type: 'text'}},
	            { display: '角色分类', name: 'categoryName', align: 'left', width: "50%", editor: { type: 'text'}},
			  ],
		 url:'<%=basePath%>user/role/query',
		 rownumbers:true,
		 checkbox:true,
		 enabledEdit: true,
         height: '100%',
         percentWidthMode:true, 
         width:"100%",
         pageSize:5,
         clickToEdit: false,
         onSelectRow:function(rowdata, rowindex, rowDomElement){
            gridRolebbb.addRow( rowdata);
            gridRole.loadData();
         },
         onBeforeEdit: onBeforeEdit, 
         //onBeforeSubmitEdit: onBeforeSubmitEdit,
         //onAfterSubmitEdit: onAfterSubmitEdit,
		});
	}
		
	//获取所有选中行获取选中行的id 格式为 ids=1&ids=2 
	function getSelectIds(gridRole) {
		var selectedRows = gridRole.getSelectedRows();
		var selectedRowsLength = selectedRows.length;
		var ids = "";
		
		for(var i = 0;i<selectedRowsLength;i++) {
			ids += selectedRows[i].userCategoryId + ",";
		}
		return {"ids":ids};
	}

	
	//初始化Grid处理
	function initGridRolebbb() {
		gridRolebbb = $("#rolebbb").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
				{ display: '名称', name: 'name', align: 'left', width: "50%",editor: { type: 'text'}},
	            { display: '角色分类', name: 'categoryName', align: 'left', width: "50%", editor: { type: 'text'}},
			  ],
		 rownumbers:true,
		 checkbox:true,
		 enabledEdit: true,
         height: '100%',
         percentWidthMode:true, 
         width:"100%",
         pageSize:5,
         clickToEdit: false,
         onBeforeEdit: onBeforeEdit, 
         onSelectRow:function(rowdata, rowindex, rowDomElement){
            gridRolebbb.deleteRow( rowdata);
         },
		});
	}
	
	function mytest(){
		var data = gridRolebbb.getData();
		return data;
	}	
	
    
    //查询
    function searchHandler(){
    	 var query = {
    	 	name:$("#searchInputRole").val(),
    	 };
		 gridRole.setOptions({ params : query});
		 //页号重置为1
		 gridRole.setNewPage(1);
		//刷新表格数据 
		gridRole.loadData();
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
    
   
</script>	
</body>
</html>