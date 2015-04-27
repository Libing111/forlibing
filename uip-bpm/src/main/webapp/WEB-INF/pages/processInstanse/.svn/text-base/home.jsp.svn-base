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

</head>
<body>
<table width="100%" >
	<tr>
		<td width="100%" class="ver01" >
		<fieldset style="border-color:#999999; ">
			<div  panelTitle="查询" showStatus="false" id="searchPanel">
				<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
					<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
					<table>
						<tr>
							<td>工作名称：</td>
							<td>
								<input type="text" id="searchInput" name="userinfor.userName"/>
								<input type="text" style="width:2px;display:none;"/>
							</td>
							<td>申请人：</td>
							<td>
								<input type="text" id="processInstanceInitiatorName" name="userinfor.userName"/>
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
				<div id="assignee"></div>
			</div>
		</td>
	</tr>
	</table>
	
<script type="text/javascript">
	//定义grid
	var grid = null;
	var categoryCode = "${categoryCode}";
	//初始化函数
	function initComplete(){
		//初始化grid
		initGrid();
	}

	//初始化Grid处理
	function initGrid() {
		grid = $("#assignee").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'center', width: "0%", hide:true},
				{ display: '工作名称', name: 'processInstanceName',     align: 'center', width: "25%",render:function (row){
	            	return getWorkName(row);  
	            }},
			    //{ display: '经办人', name: 'assigneeName', 	 align: 'left', width: "10%"},
			    { display: '事项', name: 'processDefinitinCategoryName',     align: 'center', width: "15%",render:function (row){
	            	return row.variables.processDefinitinCategoryName;  
	            }},
			    //{ display: '流程步骤名称', name: 'name',     align: 'center', width: "10%"},
			    { display: '申请人', name: 'processInstanceInitiatorName', 	 align: 'center', width: "20%"},
			    { display: '申请时间', name: 'startTime', align: 'center', width: "20%"},
			    //{ display: '到达时间', name: 'createTime', 	 align: 'center',  wdith:"10%"} ,
			    //{ display: '截止时间', name: 'dueDate', 	 align: 'left',  wdith:"10%"},
// 			    { display: '状态', name: 'status',     align: 'center', width: "10%",render : function(rowdata, rowindex, value, column){
// 					if (!rowdata.assignee) {
// 						return "审件";
// 					}
// 					return "办理中";
//                 }},
			    { display: '操作', isAllowHide: false, align: 'center', width:"20%",
					 render: function (rowdata, rowindex, value, column){
                 	     var h = "";
		                 h += "<a onclick='onView(" + rowindex + ")'><span class='icon_view'>详细</span></a>"; 
		                 return h;
	                 }
	            }
			  ],
		 url: '<%=path%>/process/instanse/monitor/instance/query?categoryCode='+categoryCode, 
		 sortName: 'id',
		 rownumbers:true,
		 checkbox:true,
         height: '100%', 
         width:"100%",
         pageSize:20,
         percentWidthMode:true
        // toolbar:{items:[
						 //{text: '签收', click: claim, iconClass: 'icon_email'},
						 //{ line : true },
                         //{text: '办理', click: handle , iconClass: 'icon_email'},
	        			// { line : true },
		        		 //{text: '退签', click: anticlaim, iconClass: 'icon_email'}]}
		        		// { line : true },
		        		// {text: '退回', click: goback, iconClass: 'icon_exit'}]}
		        		//{text: '详细', click: anticlaim, iconClass: 'icon_email'}]}
		});
	}
	
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
	}
	

	function getWorkName(row){
  		 var workName = row.processInstanceName;
  		 if(row.description == null){
  		 	return workName;
  		 }
  	     var index= row.description.indexOf("workName=");
  	     if(index < 0){
  	        return workName;
  	     }
  	        
  	     var indexEnd = row.description.indexOf("&", index);
  	     if(indexEnd > index){
  	        workName = workName + '[' + row.description.substring(index + 9, indexEnd) +']';
  	        return workName;
  	     }
  	     workName = workName + '[' + row.description.substring(index + 9) +']';
  	     return workName;
  	};
  	
    //查询
    function searchHandler(){
    	 var query = {
    		processInstanceName:$("#searchInput").val(),
    	 	processInstanceInitiatorName:$("#processInstanceInitiatorName").val(),
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
    //详细
	function onView(rowidx){
		var row = grid.getRow(rowidx);
		var url = '<%=basePath %>/process/instanse/monitor/onView?processInstanceId='+ row.id +'&processDefinitionId=' + row.processDefinitionId+'&categoryCode=' + categoryCode;
		url = url + "&rebackUrl=${rebackUrl}";
		window.location = url;
	}
		
</script>	
</body>
</html>