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
<!--自动提示框start-->
<script type='text/javascript' src='<%=contextPath%>file/libs/js/form/suggestion.js'></script>
<!--自动提示框end-->
<!--数据表格start-->
<script src="<%=contextPath%>file/libs/js/table/quiGrid.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/dialog.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/drag.js"></script>
<!--数据表格end-->

</head>
<body>

<table width="100%" >
	<tr>
		<td width="100%" class="ver01" >
		<fieldset> 
			<legend>检索条件</legend>
			<div  id="searchPanel">
				<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
					<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
					<table>
						<tr>
							<td>名称：</td>
							<td>
								<input type="text" id="name" name="name"/>
								<input type="text" style="width:2px;display:none;"/>
							</td>
							<!--  <td>停用状态：</td>
							<td>
								<select name="stop" id="stop">
									<option value="">全部</option>
									<option value="true">已停用</option>
									<option value="false">已启用</option>
								</select>
							</td>-->
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
				<div id="roleCategory"></div>
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
		reportUnitName : "";
    	reportUnitId : "";
		//初始化grid
		initGrid();
	}

	//初始化Grid处理
	function initGrid() {
		grid = $("#roleCategory").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'center', width: "0%", hide:true},
			    { display: '名称', name: 'name', 	 align: 'center', width: "25%"},
			    { display: '编号', name: 'code', 	 align: 'center', width: "25%"},
			    { display: '序号', name: 'sequenceNumber',     align: 'center', width: "25%"},
			    { display: '备注', name: 'description', 	 align: 'center', width: "25%"}
			   // { display: '停用状态', name: 'stop', 	 align: 'center', width: "20%",render : function(rowdata, rowindex, value, column){
				//	if (value == false) {
				//	 return false == value ? "<font color=green>已启用</font>" : "已启用";
				//	}
				//	if(value == true){
				//		return true == value ? "<font color=red>已停用</font>" : "已停用";
				///	}
               // }}
			  ],
		 url: '<%=path%>/roleCategory/query', 
		 sortName: 'id',
		 rownumbers:true,
		 checkbox:true,
         height: '100%', 
         width:"100%",
         pageSize:20,
         percentWidthMode:true,
          onCheckRow:function(checked, rowdata, rowindex, rowDomElement){
    		    	var rows = grid.getCheckedRows();
    		    	for(var i = 0; i < rows.length; i++){
    		    		grid.unselect(rows[i].__id);
    		    	}
    		    	grid.select(rowindex);
        			//初始化tree
         },
         toolbar:{items:[{text: '新增', click: onAdd , iconClass: 'icon_add'},
	        			 { line : true },
		        		 {text: '修改', click: onUpdate, iconClass: 'icon_edit'},
		        		 { line : true },
		        		 {text: '删除', click: onDelete, iconClass: 'icon_delete'}
		        		//{ line : true },
		        		 //{text: '停用', click: stop, iconClass: 'icon_globe'},
		        		 //{ line : true },
		        		// {text: '启用', click: nostop, iconClass: 'icon_no'}
		        ]}
		});
	}
	
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
	}
	function onAdd(){
		Dialog.open({
			URL:'<%=basePath %>/roleCategory/add',
			Title:"新增",
			Width:600,
			Height:310,
			ShowMaxButton:true,
			ShowMinButton:true,
			MinToTask:true
			});
	}
	function onUpdate(){
		var row = grid.getSelectedRow();
		if(row == null){
			Dialog.alert("请选择要修改的记录！");
			return false;
		}
		var selectedRows = grid.getSelectedRows();
		if(selectedRows.length > 1){
			top.Dialog.alert("请选择单条记录！");
			return;
		}
		Dialog.open({
			URL:'<%=basePath %>/roleCategory/update?id='+row.id,
			Title:"修改",
			Width:600,
			Height:310
			});	
		}
	function onDelete(){
		var row = grid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择要删除的记录！");
			return false;
		}
		var rowsLength = rows.length;
		if(rowsLength > 1) {
			top.Dialog.alert("请选择单条记录!");
			return;
		}
		top.Dialog.confirm("确定要删除该记录吗？",function(){
			$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>roleCategory/del?ids='+row.id,
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
//停用
	function stop(){
		var row = grid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择数据！");
			return false;
		}
		top.Dialog.confirm("确定要停用该记录吗？",function(){
			$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>roleCategory/stop?id='+row.id,
			  success:function(result){
				    if(result.status == 1){
						//top.Dialog.alert("发布成功！",null,null,null);
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
	//启用
	function nostop(){
		var row = grid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择数据！");
			return false;
		}
		top.Dialog.confirm("确定要启用该记录吗？",function(){
			$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>roleCategory/nostop?id='+row.id,
			  success:function(result){
				    if(result.status == 1){
						//top.Dialog.alert("发布成功！",null,null,null);
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
    	 	stop:document.getElementById("stop").value,
    	 };
		 grid.setOptions({ params : query});
		 //页号重置为1
		 grid.setNewPage(1);
		//刷新表格数据 
		grid.loadData();
    }
    //重置查询
    function resetSearch(){
//     	$("#queryForm")[0].reset();
// 		searchHandler2();
		window.location.reload();
    }
    
</script>	
</body>
</html>