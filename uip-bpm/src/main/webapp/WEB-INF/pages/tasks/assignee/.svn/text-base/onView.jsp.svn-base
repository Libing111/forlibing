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
	<tr >
		<td width="100%" class="ver01" >
			<div class="padding_right5">
				<div id="assignee"></div>
			</div>
		</td>
	</tr>
	</table>
	
<script type="text/javascript">
	//定义grid
	var grid = null;
	var taskId = "${taskId}";
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
				{ display: '工作名称', name: 'processInstanceName',     align: 'center', width: "20%"},
			    { display: '事项', name: 'processDefinitionCategoryName',     align: 'center', width: "10%"},
	            { display: '申请人', name: 'processInstanceInitiatorName', 	 align: 'center', width: "10%"},
			    { display: '申请时间', name: 'processInstanceStartTime', align: 'center', width: "10%"},
			    { display: '审批环节', name: 'name',     align: 'center', width: "10%"},
// 			    { display: '办理意见', name: 'reviewOpinion',     align: 'center', width: "10%"},
			   	{ display: '办理人', name: 'assigneeName',     align: 'center', width: "10%"},
			    { display: '办结时间', name: 'endTime', 	 align: 'center',  wdith:"10%",render : function(rowdata, rowindex, value, column){
					if (value == null) {
						return null;
					}
					var timeText = date2Text(value, 'yyyy-MM-dd');
					return timeText;
                }}, 
			    { display: '操作', isAllowHide: false, align: 'center', width:"10%",
					 render: function (rowdata, rowindex, value, column){
					 		if(rowdata.taskId == taskId){
					 		 	var h = "";
			                 	h += "<a onclick='handlet(" + rowindex + ")'><span id='handle' style='color: green;'>办理</span></a>";
			                 	h += "<span>&nbsp&nbsp</span>";
			                 	h += "<a onclick='goback(" + rowindex + ")'><span style='color: green ;'>返回</span></a>";  
			                 	return h;
					 		}
					 		
	                 }
	            }
			  ],

		 url: '<%=path%>/tasks/category/taskQuery?taskId=${taskId}&processDefinitionId=${processDefinitionId}&processInstanceId=${processInstanceId}&executionId=${executionId}&taskDefinitionKey=${taskDefinitionKey}',
		 sortName: 'id',
		 rownumbers:true,
		 checkbox:false,
         height: '100%', 
         width:"100%",
         pageSize:15,
         percentWidthMode:true,
          alternatingRow: false,
         rowAttrRender: function(rowdata, rowid){
           return null == rowdata.endTime ? "style=\"background-color:yellow\"" : '';
        }
		});
	}
	
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
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
	function handle (){
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
		var claimSuccess = true;
		$.ajax({type: 'POST',
			url: '<%=basePath %>/tasks/candidate/claim',
			data:{taskIds:row.taskId},
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
		var url = "${taskUrl}";
		if(url == null || url.trim()==''){
			top.Dialog.alert("办理失败，没有配置表单",null,null,null);
			return;
		}
	    var char = "?";
		if(url.indexOf("?") >= 0){
			char = "&";
		}
		url = url + char + "taskId=" + row.taskId + "&processInstanceId="+row.processInstanceId;
		url = url + "&rebackUrl=${rebackUrl}";
		
		window.location = url;
	}
//办理
	function goback(rowindex) {
		 	var url = '<%=basePath %>/tasks/category/home?categoryCode=${categoryCode}';
			window.location = url;
		}
	function handlet (rowindex){
		
		var row = grid.getRow(rowindex);
		
		var claimSuccess = true;
		$.ajax({type: 'POST',
			url: '<%=basePath %>/tasks/candidate/claim',
			data:{taskIds:row.taskId},
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
		var url = "${taskUrl}";
		if(url == null || url.trim()==''){
			top.Dialog.alert("办理失败，没有配置表单",null,null,null);
			return;
		}
	    var char = "?";
		if(url.indexOf("?") >= 0){
			char = "&";
		}
		url = url + char + "taskId=" + row.taskId + "&processInstanceId="+row.processInstanceId;
		url = url + "&rebackUrl=${rebackUrl}";
		
		window.location = url;
	}
	//反签收
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

		top.Dialog.confirm("确定要反签收吗？", function(){
			$.ajax({type: 'POST',
				url: '<%=basePath %>/tasks/assignee/anticlaim',
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