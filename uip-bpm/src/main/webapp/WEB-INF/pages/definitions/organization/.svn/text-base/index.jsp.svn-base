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

<!--树组件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/tree/ztree/ztree.js"></script>
<link href="<%=contextPath%>file/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!--树组件end -->


<!--数据表格start-->
<script src="<%=contextPath%>file/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/dialog.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/drag.js"></script>

</head>
<body>
<table width="100%" >
	<tr>
		<td width="100%" class="ver01" >
		<fieldset style="border-color:#999999; ">
			<div  id="searchPanel">
				<form action="" id="queryForm" method="post">
					<table>
						<tr>
							<td>登记机关：</td>
							<td>
								<input type="text" id="organizationName" name="organizationName"/>
								<input type="text" style="width:2px;display:none;"/>
							</td>
							<td>资源：</td>
							<td>
								<input type="text" id="resourceName" name="resourceName"/>
								<input type="text" style="width:2px;display:none;"/>
							</td>
							<td>流程定义：</td>
							<td>
								<input type="text" id="processDefinitionName" name="processDefinitionName"/>
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
				<div id="bpmOrganization"></div>
			</div>
		</td>
	</tr>
	</table>
	
<script type="text/javascript">


	//定义grid
	var grid = null;
	function closeWin(){
		Dialog.close();
	}
	//初始化函数
	function initComplete(){
		//初始化grid
		initGrid();
	}

	//初始化Grid处理
	function initGrid() {
		grid = $("#bpmOrganization").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
			    { display: '登记机关', name: 'organizationName', 	 align: 'left', width: "25%"},
			    { display: '资源', name: 'resourceName',     align: 'left', width: "25%"},
			    { display: '流程定义', name: 'processDefinitionName', 	 align: 'left', width: "25%"},
			    { display: '流程版本', name: 'processDefinitionVersion', align: 'left', width: "25%"}
			  ],
		 url: '<%=path%>/definitions/organization/query', 
		 sortName: 'id',
		 rownumbers:true,
		 checkbox:true,
         height: '100%', 
         width:"100%",
         pageSize:20,
         percentWidthMode:true,
         toolbar:{items:[
						 {text: '增加', click: addUnit, iconClass: 'icon_add'},
						 { line : true },
                         {text: '修改', click: updateUnit , iconClass: 'icon_edit'},
	        			 { line : true },
		        		 {text: '删除', click: deleteUnit, iconClass: 'icon_delete'}
	        			 ]}
		});
	}
	
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
	}
	
    //查询
    function searchHandler(){
    	 var query = {
    		organizationName:$("#organizationName").val(),
    		resourceName:$("#resourceName").val(),
    		processDefinitionName:$("#processDefinitionName").val(),
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
	//新增
	function addUnit(){
		Dialog.open({
				URL:'<%=basePath %>definitions/organization/add',
				Title:"新增",
				Width:400,
				Height:250
				});
	}
	function updateUnit(){
		var row = grid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择数据！");
			return false;
		}
		Dialog.open({
			URL:'<%=basePath %>definitions/organization/update?id='+row.id,
			Title:"修改",
			Width:400,
			Height:250
			});
	}

	function deleteUnit(){
		top.Dialog.confirm("确定要删除该记录吗？",function(){
			var selectedRow = grid.getSelectedRow();
			var id = selectedRow.id;
		  	//删除记录
			$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>definitions/organization/del?id='+id,
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
			grid.loadData();		
		});	
	}
</script>	
</body>
</html>