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
<title>用户管理</title>
<!--框架必需start-->
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>

<!--框架必需end-->
<!--框架必需end-->

<!--树组件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/tree/ztree/ztree.js"></script>
<link src="<%=contextPath%>file/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!--树组件end -->


<!--数据表格start-->
<script src="<%=contextPath%>file/libs/js/table/quiGrid.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/dialog.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/drag.js"></script>
<!--数据表格end-->
<!--自动提示框start-->
<script type='text/javascript' src='<%=contextPath%>file/libs/js/form/suggestion.js'></script>
<!--自动提示框end-->
</head>
<body>
	<table >
	<tr>
	<div>
	   <form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
			<table>
				<tr>
					<td>注册机构:</td>
					<td style="width: 200px"><div prompt="请选择注册机构" id="raId" class="suggestion"  data=""  showList="true" boxWidth="200" inputWidth="200" ></div></td>
					<td width="4"></td>
					<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
					<td width="4"></td>
					<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置查询</span></button></td>
				</tr>
			</table>
		</form>
		<div class="padding_right5">
			<div id="user"></div>
		</div>
	</div>
	</tr>
	</table>
<script type="text/javascript">
 //重置查询
    function resetSearch(){
    	$("#queryForm")[0].reset();
		searchHandler();
    }
	function closeWin(){
		Dialog.close();
	}
	var raList = <%=JsonUtil.array2JSON(request.getAttribute("raList"))%>;
	var raData = {"list":[]};
	var ra;
	for(var i = 0; i < raList.length; i++){
		ra = {'value':raList[i].id, 'key':raList[i].name,'suggest':raList[i].name};
		raData.list.push(ra);
	}
	$('#raId').data("data",raData);
	$('#raId').render();
	//定义grid
	var gridUser = null;
	var processDefinitionId ="${processDefinitionId}";
	//初始化函数
	function initComplete(){
		//初始化grid组件
		initGridUser();
		//监听查询框的回车事件
		 $("#searchInputUser").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		 });
	}
	
	//初始化Grid处理
	function initGridUser() {
		gridUser = $("#user").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
				{ display: '姓名', name: 'name', align: 'left', width: "30%",editor: { type: 'text'}},
				{ display: '编号', name: 'code', align: 'left', width: "30%",editor: { type: 'text'}},
	            {display: '注册机构', name: 'raId',align: 'left',width:"40%", editor: { type: 'select',data:raData,selWidth:100},render:function (item){
	                	for (var i = 0; i < raData["list"].length; i++)
                        {
                            if (raData["list"][i]['value'] == item.raId)
                                return raData["list"][i]['key'];
                        }
                        return item.raName;
	             }},
			  ],
		 url:'<%=basePath%>definitions/monitor/user/query', 
		 rownumbers:true,
		 checkbox:true,
		 //enabledEdit: true,
         height: '100%',
         percentWidthMode:true,
         selectRowButtonOnly:false, 
         pageSize:20,
         clickToEdit: false,
         onDblClickRow:function(rowdata, rowindex){
            		gridUser.beginEdit(rowindex);
          },
         toolbar:{
        	 items:[
        		  {text: '保存', click: saveUnit,    iconClass: 'icon_add'},
        		  { line : true },
        		]
         	}
		});
	}
	
	
		//新增
	function saveUnit() {
		$.ajax({
		  type: 'POST',
		  url: '<%=basePath %>definitions/monitor/saveUser?processDefinitionId='+processDefinitionId,
		  data:getSelectIds(gridUser),
		  success:function(result){
			 if(result == "SYS_SUCCESS"){
			    top.Dialog.alert("保存成功",function(){
			    		parent.monitorGrid.loadData();
						parent.Dialog.close();
    			});
			    }else{
			    	top.Dialog.alert("保存失败");	
			    }
			},
		  dataType: "json",
		  async:false
		});  
	}
		
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
	}
	
		//编辑前事件 
    function onBeforeEdit(e){
         	var str="编辑前事件，可阻止某些行或列进行编辑。列名："+e.column.name+"；行号："+e.rowindex+"；编辑前的值："+e.value+"\n";
     }
      //查询
    function searchHandler(){
	    query = {
	    	 raId : $("#raId").attr("relValue"),
	    	 };
		 gridUser.setOptions({ params : query});
		 //页号重置为1
		 gridUser.setNewPage(1);
		//刷新表格数据 
		gridUser.loadData();
    }
    function getSelectIds(gridUser) {
		var selectedRows = gridUser.getSelectedRows();
		var selectedRowsLength = selectedRows.length;
		var loginNames = "";
		
		for(var i = 0;i<selectedRowsLength;i++) {
			loginNames += selectedRows[i].loginName + ",";
		}
		return {"loginNames":loginNames};
	}   
	
</script>	
</body>
</html>