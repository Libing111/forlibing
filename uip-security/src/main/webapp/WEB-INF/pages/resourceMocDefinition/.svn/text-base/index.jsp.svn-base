<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp"></jsp:include>

<!--框架必需end-->
</head>

<script type="text/javascript">
	$(function(){
			var resourceMocDefinition_columns=[[
				{name:'id',width:100,checkbox:true,align:'center'},
				{name:'name',display:'名称',width:100,align:'left'},
				{name:'parent',display:'父资源',width:100,align:'left'},
				{name:'type',display:'类型',width:100,align:'left'},
				{name:'description',display:'备注',width:200,align:'left'}
				]];
			
			$('#resourceMocDefinition').quiGrid({
				singleSelect:true,
				url:'<%=basePath%>resourceMocDefinition/query',
				columns:resourceMocDefinition_columns,
				toolbar:'#tb',
				//onSelect:function(rowIndex, rowData){
					//var rows = $('#resourceMocDefinition').datagrid('getSelections');
					
				//},
				//onUnselect:function(rowIndex, rowData){
					//var rows = $('#resourceMocDefinition').datagrid('getSelections');
					
				//}
			});
			
		});
		
	function addOrUpdateStandard(op) {
			var commitUrl='<%=basePath %>resourceMocDefinition/save';
			var title='资源类型';
			if (op=="add") {
			proper.dialogForm({
					title:title,
					width:450,
					height:300,
					commitURL:commitUrl,
					modal:true, 
					href:'<%=basePath%>resourceMocDefinition/add',
					commitSuccess:function(resp){
						proper.reloadDatagrid('resourceMocDefinition');
						proper.reloadDatagrid('qual');
					}
				});
			}
			else {
				var row = $('#resourceMocDefinition').datagrid('getSelected');
				$('#dialog').dialogForm({
					title:title,
					width:450,
					height:300,
					modal:true,
					commitURL:commitUrl,
					href:'<%=basePath %>resourceMocDefinition/update?id='+row.id,
					commitSuccess:function(resp){
						proper.reloadDatagrid('resourceMocDefinition');
						proper.reloadDatagrid('qual');
					}
				});
			}
		}
	function delCategory() {
			var rows = $('#resourceMocDefinition').datagrid('getSelections');
			var len=rows.length;
			var id = [];
			for(var i = 0;i<len;i++){
				id.push(rows[i].id);
			}
			if(confirm("您确定要删除选中的资源类型吗？") == false){
				return;
			};
			proper.ajax("<%=basePath %>resourceMocDefinition/del", {ids:id.join(",")},function(resp){
					proper.reloadDatagrid('resourceMocDefinition');
					proper.reloadDatagrid('qual');
				});

		}
		
</script>
	<body class="easyui-layout">
		<div region="center" style="border:0px;" title="资源类型">
			<div id="tb" style="padding:0px;height:auto">
				<div>
					<a href="javascript:void(0)" class="easyui-linkbutton" id="btn_add" iconCls="icon-add" onclick="addOrUpdateStandard('add')" plain="true">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" id="btn_modify" iconCls="icon-edit" onclick="addOrUpdateStandard('update')" plain="true">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" id="btn_delete" iconCls="icon-delete" onclick="delCategory()"  plain="true">删除</a>
				</div>
			
			
		
			</div>
			<table id="resourceMocDefinition"></table>
		</div>
	</body>
</html>
