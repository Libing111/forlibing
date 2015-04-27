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
<title>办结工作</title>
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
						<td width="4"></td>
						<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
						<td width="4"></td>
						<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置查询</span></button></td>
					</tr>
				</table>
				</form>
				</div>
				<div class="padding_right5">
					<div id="history"></div>
				</div>
		</td>
		<!--右侧区域end-->
		
	</tr>
	</table>
	
<script type="text/javascript">
	
	//定义grid
	var historyGrid = null;
	
	//初始化函数
	function initComplete(){
		//初始化tree
		initHistoryGrid();
		
		
	}

	//初始化Grid处理
	function initHistoryGrid() {
		historyGrid = $("#history").quiGrid({
			columns:[
				{ display: '工作名称', name: 'processInstanceName',     align: 'left', width: "20%"},
			    { display: '发起人', name: 'processInstanceInitiatorName', 	 align: 'left', width: "20%"},
			    { display: '发起时间', name: 'startTime', align: 'left', width: "20%", render : function(rowdata, rowindex, value, column){
			    	if (value == null) {
			    		return null;
			    		}
			    		var timeText = dateText(value, 'yyyy-MM-dd hh:mm:ss');
			    		return timeText;
			    }},
			    { display: '结束时间', name: 'endTime', 	 align: 'left',  wdith:"20%", render : function(rowdata, rowindex, value, column){
			    	if (value == null) {
			    		return null;
			    		}
			    		var timeText = dateText(value, 'yyyy-MM-dd hh:mm:ss');
			    		return timeText;
			    }},
			    { display: '进展状况', name: 'completed', 	 align: 'left',  wdith:"20%"} ,
			  ],
		 url: '<%=path%>/tasks/history/query', 
		 sortName: 'userId',
		 rownumbers:true,
		 checkbox:true,
         height: '100%', 
         width:"100%",
         pageSize:20,
         percentWidthMode:true,
	     toolbar:{
	        	 items:[
	        		  {text: '签收', click: history, iconClass: 'folder-doc'},
	        		  { line : true },
	        		  ]
	        	 }
		});
		
	}
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55)
	}
	function dateText(v, fmt) {
		var fv = function(s){
			if(s<10){
				return '0'+s;
			}else{
				return s;
			}
		}
		if(typeof v=='string'){
			return v;
		}
		if (typeof v == 'number'){
			var v1 = new Date();
			v1.setTime(v);
			v = v1;
		} 
		if(v&&typeof v == 'object'){
			var fmtItem = ['yyyy','MM','dd','hh','mm','ss'];
			if(!fmt){
				fmt = 'yyyy-MM-dd';
			}
			var exp = new RegExp('(' + fmtItem.join('|') + ')', "g");
            var fv = function(v) {
                return v < 10 ? ('0' + v) : v;
            };
            return fmt.replace(exp, function($1, $2) {
                switch ($2) {
                    case 'yyyy':return v.getFullYear();
                    case 'MM':return fv(v.getMonth() + 1);
                    case 'dd':return fv(v.getDate());
                    case 'hh':return fv(v.getHours());
                    case 'mm':return fv(v.getMinutes());
                    case 'ss':return fv(v.getSeconds());
                }
            });
		}else{
			return '';
		}
	};

</script>	
</body>
</html>