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
	<!--左侧区域start-->
		<td width="100%" class="ver01" >
			<div class="box1" panelTitle="工作列表" showStatus="false" id="searchPanel">
				<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
					<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
					<table>
						<tr>
							<td>工作名称：</td>
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
				<div id="instanse"></div>
			</div>
		</td>
		<!--左侧区域end-->
		<!--右侧区域start-->
		<td  class="ver01" >
				<div class="box1" panelTitle="任务列表" showStatus="false">
				<table>
						<tr>
							<td>任务列表</td>
						</tr>
				</table>
				<div class="cusBoxContent"  style="width:500px;">
					<div class="padding_right5">
						<div id="task"></div>
					</div>
				</div>
				</div>	
			</td>
		<!--右侧区域end-->
	</tr>
	</table>
	
<script type="text/javascript">

	var initInstanseGrid = null;
	var initTaskGrid = null;
	//初始化函数
	function initComplete(){
		//初始化grid
		instanseGrid();
		taskGrid();
	}

	//初始化Grid处理
	function instanseGrid() {
		initInstanseGrid = $("#instanse").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
			    { display: '工作名称', name: 'processInstanceName', 	 align: 'left', width: "20%"},
			    { display: '发起人', name: 'processInstanceInitiatorName',     align: 'left', width: "20%"},
			    { display: '发起时间', name: 'startTime',     align: 'left', width: "20%"},
			    { display: '结束时间', name: 'endTime',     align: 'left', width: "20%"},
			    { display: '进展状况', name: 'completed',     align: 'left', width: "20%"},
			  ],
		 url: '<%=path%>/process/instanse/startedByMe/instance/query', 
		 sortName: 'id',
		 rownumbers:true,
		 checkbox:true,
         height: '100%', 
         width:"100%",
         pageSize:10,
         percentWidthMode:true,
//          toolbar:{items:[
//                          {text: '挂起', click: suspend , iconClass: 'icon_email'},
// 	        			 { line : true },
// 	        			 {text: '继续', click: activate , iconClass: 'icon_email'},
// 	        			 { line : true },
// 	        			 {text: '撤销', click: cancel , iconClass: 'icon_email'},
// 	        			 { line : true }
	        			 
// 	        	  ]},
         onSelectRow:function(rowdata, rowindex, rowDomElement){
             var query = {
            		 processInstanceId: rowdata.id,
 					};
             initTaskGrid.setOptions({ params : query});
             initTaskGrid.loadData();
          },
         onCheckRow:function(checked, rowdata, rowindex, rowDomElement){
    		    	var rows = initInstanseGrid.getCheckedRows();
    		    	for(var i = 0; i < rows.length; i++){
    		    		initInstanseGrid.unselect(rows[i].__id);
    		    	}
    		    	initInstanseGrid.select(rowindex);
         },
          
		});
	}
	
	//初始化Grid处理
	function taskGrid() {
		initTaskGrid = $("#task").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
			    { display: '任务步骤名称', name: 'name', 	 align: 'left', width: "35%"},
			    { display: '经办人', name: 'assignee',     align: 'left', width: "30%"},
// 			    { display: '进展状况', name: 'completed',     align: 'left', width: "20%"},
			    { display: '发起时间', name: 'claimTime',     align: 'left', width: "35%",render : function(rowdata, rowindex, value, column){
					if (value == null) {
						return null;
					}
					var timeText = date2Text(value, 'yyyy-MM-dd');
					return timeText;
                }}
//                 ,
// 			    { display: '结束时间', name: 'dueTime',     align: 'left', width: "20%",render : function(rowdata, rowindex, value, column){
// 					if (value == null) {
// 						return null;
// 					}
// 					var timeText = date2Text(value, 'yyyy-MM-dd');
// 					return timeText;
//                 }}
			  ],
		 url: '<%=path%>/process/instanse/startedByMe/instance/task/query', 
		 sortName: 'id',
		 rownumbers:true,
		 checkbox:true,
         height: '100%', 
         width:"100%",
         pageSize:5,
         percentWidthMode:true,
		});
	}
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
	}
	 //查询
    function searchHandler(){
    	 var query = {
    		processInstanceName:$("#searchInput").val(),
    	 };
    	 initInstanseGrid.setOptions({ params : query});
		 //页号重置为1
		 initInstanseGrid.setNewPage(1);
		//刷新表格数据 
		initInstanseGrid.loadData();
    }
    //重置查询
    function resetSearch(){
    	$("#queryForm")[0].reset();
		searchHandler();
    }
    
	var date2Text = function(v, fmt) {
		var fv = function(s){
			if(s<10){
				return '0'+s;
			}else{
				return s;
			}
		};
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
	///挂起
	function suspend() {
		var row = initInstanseGrid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择流程实例!");
			return false;
		}
		top.Dialog.confirm("确定要挂起吗？", function(){
			$.ajax({type: 'POST',
				url: '<%=basePath %>/process/instanse/startedByMe/instance/suspend',
				data:{processInstanceId:row.id},
				success: function (data){
					initInstanseGrid.loadData();
	       		},
	       		error: function(){
	       			$.messager.alert('提示', '挂起流程失败', 'warning');
	  			},
				dataType: "json",
				async:false
			});	
        });		
	}
	///继续
	function activate(){
		var row = initInstanseGrid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择流程实例!");
			return false;
		}
		top.Dialog.confirm("确定要继续吗？", function(){
			$.ajax({type: 'POST',
				url: '<%=basePath %>/process/instanse/startedByMe/instance/activate',
				data:{processInstanceId:row.id},
				success: function (data){
					initInstanseGrid.loadData();
	       		},
	       		error: function(){
	       			$.messager.alert('提示', '继续流程失败', 'warning');
	  			},
				dataType: "json",
				async:false
			});	
        });		
	}
	///撤销

	function cancel(){
		var url;
		var row = initInstanseGrid.getSelectedRow();
		if(row == null){
			top.Dialog.alert("请选择流程实例!");
			return false;
		}
		top.Dialog.confirm("确定要撤销吗？", function(){
			$.ajax({type: 'POST',
				url: '<%=basePath %>/process/instanse/startedByMe/instance/cancel',
				data:{processInstanceId:row.id},
				success: function (data){
					url = data;
	       		},
	       		error: function(){
	       			$.messager.alert('提示', '撤销流程失败', 'warning');
	  			},
				dataType: "json",
				async:false
			});	
        });
        //通知业务结束流程
        var success = true;
	     if(!url){
	     	$.ajax({type: 'POST',
				url: url,
				data:{processInstanceId:row.id},
				success: function (data){
					if(!data){
						success = data;
					}
	       		},
	       		error: function(){
	       			$.messager.alert('提示', '撤销流程失败', 'warning');
	  			},
				dataType: "json",
				async:false
			});	
	     }
	     //流程引擎删除流程	
	     if(success){
		     $.ajax({type: 'POST',
					url: '<%=basePath %>/process/instanse/startedByMe/instance/cancel',
					data:{processInstanceId:row.id},
					success: function (data){
						url = data;
		       		},
		       		error: function(){
		       			$.messager.alert('提示', '撤销流程失败', 'warning');
		  			},
					dataType: "json",
					async:false
				});	
	     }	
	}
	 
	
</script>	
</body>
</html>