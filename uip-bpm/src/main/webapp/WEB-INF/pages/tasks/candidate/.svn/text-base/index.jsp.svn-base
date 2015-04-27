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
							<td width="4"></td>
							<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
							<td width="4"></td>
							<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置查询</span></button></td>
						</tr>
					</table>
				</form>
			</div>
			<div class="padding_right5">
				<div id="candidate"></div>
			</div>
		</td>
	</tr>
</table>
<script type="text/javascript">
	//定义grid
	var candidateGrid = null;
	
	//初始化函数
	function initComplete(){
		//初始化tree
		initCandidateGrid();
	}

	//初始化Grid处理
	function initCandidateGrid() {
		candidateGrid = $("#candidate").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
				{ display: '工作名称', name: 'workName',     align: 'left', width: "10%",render:function (row){
	            	return getWorkName(row);  
	            }},
			    { display: '经办人', name: 'assigneeName', 	 align: 'left', width: "10%"},
			    { display: '流程步骤名称', name: 'name',     align: 'left', width: "20%"},
			    { display: '发起人', name: 'processInstanceInitiatorName', 	 align: 'left', width: "10%"},
			    { display: '发起时间', name: 'processInstanceStartTime', align: 'left', width: "20%"},
			    { display: '到达时间', name: 'createTime', 	 align: 'left',  wdith:"20%"} ,
			    { display: '截止时间', name: 'dueDate', 	 align: 'left',  wdith:"10%"} ,
			  ],
		 url: '<%=path%>/tasks/candidate/query', 
		 sortName: 'id',
		 rownumbers:true,
		 checkbox:true,
         height: '100%', 
         width:"100%",
         pageSize:5,
         percentWidthMode:true,
         toolbar:{
        	 items:[
        		  {text: '签收', click: claim, iconClass: 'icon_email'},
        		  { line : true },
        		  ]
        	 }
		});
		
	}
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
	}
	
	//签收
	function claim(){
		var selectedRows = candidateGrid.getSelectedRows();
		if(selectedRows == null || selectedRows.length == 0){
			top.Dialog.alert("没有选中待办工作，请先选中待办工作",null,null,null);
			return;
		}

        var idArray = new Array(selectedRows.length);
        for (var i = 0; i < selectedRows.length; i++) {
        	idArray[i] = selectedRows[i].id;
        }

		top.Dialog.confirm("确定要签收吗？", function(){
			$.ajax({type: 'POST',
				url: '<%=basePath %>/tasks/candidate/claim',
				data:{taskIds:idArray.join(",")},
				success:function(result){
					if(result.status == 1){
						candidateGrid.loadData();
						
						return;
					}
					top.Dialog.alert(result.SYS_ERROR);
				},
				dataType: "json",
				async:false
			});	
        });		
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
</script>	
</body>
</html>