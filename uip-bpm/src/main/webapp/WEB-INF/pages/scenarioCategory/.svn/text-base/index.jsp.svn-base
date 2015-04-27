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

<!--数据表格start-->
<script src="<%=contextPath%>file/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->

</head>
<body>
<table width="100%" >
	<tr>
		<td width="100%" class="ver01" >
			<div class="box1" panelTitle="查询" showStatus="false" id="searchPanel">
				<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
					<input type="hidden" id="id" name="id" value="${id}"/>
					<table>
						<tr>
							<td>场景名称：</td>
							<td>
								<input type="text" id="name" name="name"/>
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
				<div id="page"></div>
			</div>
		</td>
	</tr>
	</table>
	
<script type="text/javascript">
	//定义grid
	var grid = null;
	
	//初始化函数
	function initComplete(){
		//初始化grid
		initGrid();
	}

	//初始化Grid处理
	function initGrid() {
		grid = $("#page").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
				{ display: '场景名称', name: 'name',     align: 'left', width: "50%"},
				{ display: '策略', name: 'strategyName',     align: 'left', width: "50%"},
			  ],
		 url: '<%=path%>/scenario/category/query', 
		 sortName: 'id',
		 rownumbers:true,
		 checkbox:true,
         height: '100%', 
         width:"100%",
         pageSize:20,
         percentWidthMode:true,
         toolbar:{items:[{text: '新增', click: onAdd , iconClass: 'icon_add'},
	        			 { line : true },
		        		 {text: '修改', click: onUpdate, iconClass: 'icon_edit'},
		        		 { line : true },
		        		 {text: '删除', click: onDelete, iconClass: 'icon_delete'}
		        ]}
		});
	}
	
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
	}
	function onAdd(){
		top.Dialog.open({
			URL:'<%=basePath %>/scenario/category/add',
			Title:"新增",
			Width:400,
			Height:185,
			ShowMaxButton:true,
			ShowMinButton:true,
			MinToTask:true
			});
	}
	function onUpdate(){
		var row = grid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择数据！");
			return false;
		}
		top.Dialog.open({
			URL:'<%=basePath %>/scenario/category/update?id='+row.id,
			Title:"创建公告",
			Width:800,
			Height:800
			});	
		}
	function onDelete(){
		var row = grid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择数据！");
			return false;
		}
		top.Dialog.confirm("确定要删除该记录吗？",function(){
			$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>scenario/category/del?ids='+row.id,
			  success:function(result){
				    if(result.status == 1){
						//top.Dialog.alert("删除成功！",null,null,null);
						grid.loadData();
						return;
					}
					top.Dialog.alert(result.SYS_ERROR);
			  },
			  dataType: "json",
			 // async:false
			});		
			//刷新表格
			grid.loadData();		
		});
	}
	
	
	  //查询
    function searchHandler(){
    	
    	 var query = {
    	 	name:$("#name").val(),
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
    
</script>	
</body>
</html>