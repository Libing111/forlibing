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
				{ display: '工作名称', name: 'processInstanceName',     align: 'center', width: "30%",render:function (row){
	            	return getWorkName(row);  
	            }},
			    //{ display: '经办人', name: 'assigneeName', 	 align: 'left', width: "10%"},
			    { display: '事项', name: 'processDefinitinCategoryName',     align: 'center', width: "15%",render:function (row){
	            	return row.variables.processDefinitinCategoryName;  
	            }},
			    //{ display: '流程步骤名称', name: 'name',     align: 'center', width: "10%"},
			    { display: '申请人', name: 'processInstanceInitiatorName', 	 align: 'center', width: "15%"},
			    { display: '申请时间', name: 'processInstanceStartTime', align: 'center', width: "15%"},
			    { display: '到达时间', name: 'createTime', 	 align: 'center',  wdith:"15%"} ,
			    //{ display: '截止时间', name: 'dueDate', 	 align: 'left',  wdith:"10%"},
			    { display: '状态', name: 'status',     align: 'center', width: "13%",render : function(rowdata, rowindex, value, column){
					if (!rowdata.assignee) {
						return "审件";
					}
					return "办理中";
                }}
// 			    { display: '操作', isAllowHide: false, align: 'center', width:"20%",
// 					 render: function (rowdata, rowindex, value, column){
//                  	     var h = "";
// 		                 h += "<a onclick='onView(" + rowindex + ")'><span class='icon_view'>详细</span></a>"; 
// 			             h += "<span>&nbsp&nbsp</span>";
// 			             h += "<a onclick='handlet(" + rowindex + ")'><span id='handle' style='color: green;'>办理</span></a>";
// 		                 return h;
// 	                 }
// 	            }
			  ],
		 url: '<%=path%>/tasks/category/query?categoryCode='+categoryCode, 
		 sortName: 'id',
		 rownumbers:true,
		 checkbox:true,
         height: '100%', 
         width:'100%',
         pageSize:20,
//          detail: { onShowDetail: getDetail, height: 'auto' },
         frozen:true,
         percentWidthMode:true,
        toolbar:{items:[
						 {text: '详细', click: onView, iconClass: 'icon_view'},
						 { line : true },
						 {text: '签收', click: claim, iconClass: 'icon_email'},
						 { line : true },
                         {text: '退签', click: anticlaim , iconClass: 'icon_no'},
	        			{ line : true },
		        		 {text: '办理', click: handle, iconClass: 'icon_edit'}
// 		        		{ line : true },
// 		        		{text: '退回', click: goback, iconClass: 'icon_exit'}
		        		]}
		});
	}
	
	//签收
	function claim(){
		var selectedRows = grid.getSelectedRows();
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
						grid.loadData();
						
						return;
					}
					top.Dialog.alert(result.SYS_ERROR);
				},
				dataType: "json",
				async:false
			});	
        });		
	}
	
	//办理
	function handle(){
	
		var selectedRows = grid.getSelectedRows();
		if(selectedRows == null || selectedRows.length == 0){
			top.Dialog.alert("没有选中待办工作，请先选中待办工作",null,null,null);
			return;
		}
		if(selectedRows.length > 1){
			top.Dialog.alert("只能选中一条待办工作，才能办理",null,null,null);
			return;
		}
		var row = selectedRows[0];
		if(!row.assignee){
		top.Dialog.confirm("事项未签收，是否签收并办理",
					function(){
						var claimSuccess = true;
						$.ajax({type: 'POST',
							url: '<%=basePath %>/tasks/candidate/claim',
							data:{taskIds:row.id},
							success:function(result){
								if(result.status == 1){
									return;
								}
								top.Dialog.alert(result.SYS_ERROR);
								claimSuccess = false;
							},
							dataType: "json",
							async:false
						});	
						
						if(claimSuccess == false){
							return;
						}
						var url = null;
						$.ajax({type: 'POST',
							url: '<%=basePath %>/tasks/category/getTaskUrl',
							data:{taskDefinitionKey:row.taskDefinitionKey,processDefinitionId:row.processDefinitionId},
							success:function(result){
								url = result.taskUrl;
							},
							dataType: "json",
							async:false
						});	
						if(url == null || url.trim()==''){
							top.Dialog.alert("办理失败，没有配置表单",null,null,null);
							return;
						}
					    var char = "?";
						if(url.indexOf("?") >= 0){
							char = "&";
						}
						url = url + char + "taskId=" + row.id + "&processInstanceId="+row.processInstanceId;
						url = url + "&rebackUrl=${rebackUrl}";
						
						window.location = url;
					},
					function(){return;});
			}
			else{
				var url = null;
				$.ajax({type: 'POST',
					url: '<%=basePath %>/tasks/category/getTaskUrl',
					data:{taskDefinitionKey:row.taskDefinitionKey,processDefinitionId:row.processDefinitionId},
					success:function(result){
						url = result.taskUrl;
					},
					dataType: "json",
					async:false
				});	
				if(url == null || url.trim()==''){
					top.Dialog.alert("办理失败，没有配置表单",null,null,null);
					return;
				}
			    var char = "?";
				if(url.indexOf("?") >= 0){
					char = "&";
				}
				url = url + char + "taskId=" + row.id + "&processInstanceId="+row.processInstanceId;
				url = url + "&rebackUrl=${rebackUrl}";
				
				window.location = url;
			}

	}
	
	//退签
	function anticlaim(){
		var selectedRows = grid.getSelectedRows();
		if(selectedRows == null || selectedRows.length == 0){
			top.Dialog.alert("没有选中待办工作，请先选中待办工作",null,null,null);
			return;
		}

        var idArray = new Array(selectedRows.length);
        for (var i = 0; i < selectedRows.length; i++) {
        	idArray[i] = selectedRows[i].id;
        }

		top.Dialog.confirm("确定要退签吗？", function(){
			$.ajax({type: 'POST',
				url: '<%=basePath %>/tasks/candidate/anticlaim',
				data:{taskIds:idArray.join(",")},
				success:function(result){
					if(result.status == 1){
						grid.loadData();
						
						return;
					}
					top.Dialog.alert(result.SYS_ERROR);
				},
				dataType: "json",
				async:false
			});	
        });	
		
	}
	
	//退回
	function goback(){
	
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
    	 	processInstanceInitiatorName:$("#processInstanceInitiatorName").val()
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
		var selectedRows = grid.getSelectedRows();
		if(selectedRows == null || selectedRows.length == 0){
			top.Dialog.alert("没有选中待办工作，请先选中待办工作",null,null,null);
			return;
		}
		if(selectedRows.length > 1){
			top.Dialog.alert("选中了多条待办工作，请选中一条待办工作",null,null,null);
			return;
		}
		var row = selectedRows[0];
		var url = '<%=basePath %>/tasks/category/onView?taskId='+ row.id + '&processDefinitionId=' + row.processDefinitionId+ '&processInstanceId=' + row.processInstanceId+ '&executionId=' + row.executionId+ '&taskDefinitionKey=' + row.taskDefinitionKey+ '&categoryCode=' + categoryCode;
		url = url + "&rebackUrl=${rebackUrl}";
		window.location = url;
		
// 		top.Dialog.open({
// 			URL:'<%=basePath %>/tasks/category/onView?taskId='+ row.id + '&processDefinitionId=' + row.processDefinitionId+ '&processInstanceId=' + row.processInstanceId+ '&executionId=' + row.executionId+ '&taskDefinitionKey=' + row.taskDefinitionKey,
// 			Title:"详细",
// 			Width:900,
// 			Height:400,
// 			ShowMaxButton:true,
// 			ShowMinButton:true,
// 			MinToTask:true
// 			});		
				
		}
	
	//子列表显示详细流程步骤
	function getDetail(row, detailPanel,callback)
	{
	
			var url = '<%=path%>/tasks/category/taskQuery';
        	$.post(url,{
        	taskId: row.id,
        	processDefinitionId:row.processDefinitionId,
        	processInstanceId:row.processInstanceId,
        	executionId:row.executionId,
        	taskDefinitionKey:row.taskDefinitionKey
        	},function(result){
        		if(result.rows.length > 0){
        			var childGrid = document.createElement('div'); 
                    $(detailPanel).append(childGrid);
                    $(childGrid).css('margin',10).css('left','80px').quiGrid({
                         columns: [
							{ display: 'id', name: 'id', align: 'center', width: "0%", hide:true},
							{ display: '工作名称', name: 'processInstanceName',     align: 'center', width: "25%"},
						    { display: '事项', name: 'processDefinitionCategoryName',     align: 'center', width: "15%"},
				            { display: '申请人', name: 'processInstanceInitiatorName', 	 align: 'center', width: "10%"},
						    { display: '申请时间', name: 'processInstanceStartTime', align: 'center', width: "10%"},
						    { display: '审批环节', name: 'name',     align: 'center', width: "10%"},
						    { display: '办理意见', name: 'reviewOpinion',     align: 'center', width: "10%"},
						   	{ display: '办理人', name: 'assigneeName',     align: 'center', width: "10%"},
						    { display: '办结时间', name: 'endTime', 	 align: 'center',  width:"10%",render : function(rowdata, rowindex, value, column){
								if (value == null) {
									return null;
								}
								var timeText = date2Text(value, 'yyyy-MM-dd');
								return timeText;
			                }}
							    
                         ], 
                         isScroll: false,width: '92%', 
                         //url: '/quidemoflat/userAction.do?method=getUsers&parentId=' + row.orgId,
                         data: result,
                         usePager:false,
                         //onAfterShowData可以自定义回调
                         onAfterShowData: callback
                    });  
        		}
        	})
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
</script>	
</body>
</html>