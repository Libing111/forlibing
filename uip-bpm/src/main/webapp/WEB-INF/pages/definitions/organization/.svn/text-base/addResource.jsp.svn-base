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
<table width="100%" >
	<tr>
		<!--左侧区域start-->
		<td class="ver01" >
			 <div  overflow="auto" showStatus="false" panelTitle="栏目名称">
			 	<div class="cusBoxContent"  style="width:250px;">
			  		<div id="resource"></div>
			  	</div>
		  	</div>
		</td>
		<!--左侧区域end-->
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
			<div style="margin: 0;padding: 0 5px 0 0;">
				<div id="resources"></div>
			</div>
		</td>
		<!--右侧区域end-->
	</tr>
	</table>
<script type="text/javascript">
	
	var gridGroup = null;
	var gridGroupbbb = null;
	//初始化函数
	function initComplete(){
		initGridGroup();
		initGridGroupbbb();
		//监听查询框的回车事件
		 $("#searchInputGroup").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		 });
	}
	
	//初始化Grid处理
	function initGridGroup() {
		gridResource = $("#resource").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', hide:true},
				{ display: '名称', name: 'name',align: 'left', width: "100%"},
			  ],
		 url:'<%=basePath%>definitions/organization/resourceQuery',
		 rownumbers:true,
		 checkbox:true,
		 enabledEdit: true,
         height: '100%',
         percentWidthMode:true, 
         width:"100%",
         pageSize:20,
         clickToEdit: false,
         onSelectRow:function(rowdata, rowindex, rowDomElement){
        	gridResourcebbb.addRow( rowdata);
            gridResource.loadData();
         },
         onBeforeEdit: onBeforeEdit, 
		});
	}
		
	//获取所有选中行获取选中行的id 格式为 ids=1&ids=2 
	function getSelectIds(gridGroup) {
		var selectedRows = gridGroup.getSelectedRows();
		var selectedRowsLength = selectedRows.length;
		var ids = "";
		
		for(var i = 0;i<selectedRowsLength;i++) {
			ids += selectedRows[i].userId + ",";
		}
		return {"ids":ids};
	}

	
	//初始化Grid处理
	function initGridGroupbbb() {
		gridResourcebbb = $("#resources").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left',  hide:true},
				{ display: '名称', name: 'name',align: 'left', width: "100%"},
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
        	 gridResourcebbb.deleteRow( rowdata);
         },
		});
	}
	
	function mytest(){
		var data = gridResourcebbb.getData();
		return data;
	}	
	
    
    //查询
    function searchHandler(){
    	 var query = {
    	 	name:$("#searchInputGroup").val(),
    	 };
		 gridGroup.setOptions({ params : query});
		 //页号重置为1
		 gridGroup.setNewPage(1);
		//刷新表格数据 
		gridGroup.loadData();
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