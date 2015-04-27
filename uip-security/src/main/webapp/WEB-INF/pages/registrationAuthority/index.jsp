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
<title>注册机构管理</title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>

<!--框架必需end-->

<!--数据表格start-->
<script src="<%=contextPath%>/file/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->

<!-- 日期选择框start -->
<script type="text/javascript" src="<%=contextPath%>/file/libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期选择框end -->

<!-- 数字步进器start -->
<script type="text/javascript" src="<%=contextPath%>/file/libs/js/form/stepper.js"></script>
<!-- 数字步进器end -->

<!-- 树组件start -->
<script type="text/javascript" src="<%=contextPath%>/file/libs/js/tree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" src="<%=contextPath%>/file/libs/js/tree/ztree/ztree.css"></link>
<!-- 树组件end -->

<!-- 树形下拉框start -->
<script type="text/javascript" src="<%=contextPath%>/file/libs/js/form/selectTree.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/dialog.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/libs/js/popup/drag.js"></script>
<!-- 树形下拉框end -->
</head>
<body>
<table width="100%">
	<tr>
	<!--左侧区域start-->
		<td width="100%" class="ver01" >
		<div class="box1" >
				<form action="<%=basePath%>registrationAuthority/query" id="queryForm" method="post">
				<table>
					<tr>
						<td>名称：</td>
						<td>
							<input type="text" id="searchInput" name="organizationCategoryList.name"/>
							<input type="text" style="width:2px;display:none;"/>
						</td>
						<td>分类：</td>
						<td>
							<input type="text" id="searchInput2" name="organizationCategoryList.categoryName"/>
							<input type="text" style="width:2px;display:none;"/>
						</td>
						<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
						<td width="4"></td>
						<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button></td>
					</tr>
				</table>
				</form>
			</div>
			<div class="padding_right5">
				<div id="registrationAuthority"></div>
			</div>
		</td>
	<!--左侧区域end-->
	<!--右侧区域start-->
		<td  class="ver01" >
				<div class="box1" panelTitle="资源集" showStatus="false">
				<table>
					<tr>
						<td class="ali03">资源集：</td>
						<td>
						<select prompt="请选择" id="resourceSetSelect" name="resourceSetList.name" ></select>
						</td>
						<td>
						<input type="button" value="确定" onclick="addResourceSet()"/>
						</td>
						<td colspan="2" class="ali02"></td>
					</tr>
				</table>
				<div class="cusBoxContent"  style="width:500px;">
					<div class="padding_right5">
						<div id="resourceSet"></div>
					</div>
				</div>
				</div>	
		</td>
	<!--右侧区域end-->
	</tr>
</table>
<script type="text/javascript">
		function closeWin(){
			Dialog.close();
		}
       var organizationCategoryList = <%=JsonUtil.array2JSON(request.getAttribute("categoryList"))%>;
       var organizationCategoryData = {"list":[]};
       var organizationCategory;
       for(var i = 0; i < organizationCategoryList.length; i++){
       		organizationCategory = {'value':organizationCategoryList[i].code, 'key':organizationCategoryList[i].name};
       		
       		organizationCategoryData.list.push(organizationCategory);
       }
      
       var resourceSetList = <%=JsonUtil.array2JSON(request.getAttribute("resourceSetList"))%>;
       var resourceSetData = {"list":[]};
       var resourceSet;
       for(var i = 0; i < resourceSetList.length; i++){
       		resourceSet = {'value':resourceSetList[i].code, 'key':resourceSetList[i].name};
       		
       		resourceSetData.list.push(resourceSet);
       }
       
       var grid;
       var resourceSetGrid;
		function initComplete(){
		//监听查询框的回车事件
		 $("#searchInput").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		 });
		initGrid();
		
		
		resourceSetGrid();
		$("#resourceSetSelect").data("data",resourceSetData)
		$("#resourceSetSelect").render(); 
		
		}
		
		function initGrid(){
		grid = $("#registrationAuthority").quiGrid({
         columns:[
				{display: '名称', name: 'name',align: 'left',width:"25%", editor: { type: 'text' , detailEdit:false}},
				{display: '编号', name: 'code',align: 'left',width:"25%",editor: { type: 'text'}},
				{display: '分类', name: 'categoryCode',align: 'left',width:"25%", editor: { type: 'select',data:organizationCategoryData,selWidth:100},render:function (item){
	                	for (var i = 0; i < organizationCategoryData["list"].length; i++)
                        {
                            if (organizationCategoryData["list"][i]['value'] == item.categoryCode)
                                return organizationCategoryData["list"][i]['key'];
                        }
                        return item.categoryName;
	            }},
	            {display: '备注', name: 'description',align: 'left', width:"25%",editor: { type: 'textarea'}}
                ], 
              url:'<%=basePath%>registrationAuthority/query', 
              rownumbers:true,
              pageSize:20,
              usePager: true,
              checkbox:true,
              dataAction:"server",
              percentWidthMode:true,
              height: "100%", 
              width:"100%",
              enabledEdit: true,
              clickToEdit: false,
             
               onSelectRow:function(rowdata, rowindex, rowDomElement){
                    var query = {
        						raId: rowdata.id,
        					};
                    resourceSetGrid.setOptions({ params : query});
                    resourceSetGrid.loadData();
                },
              onBeforeEdit: onBeforeEdit, 
              onBeforeSubmitEdit: onBeforeSubmitEdit,
              onAfterSubmitEdit: onAfterSubmitEdit,
				toolbar: 
					{ 
					items: [
		                { text: '新增', click: addRegistrationAuthority, iconClass: 'icon_add' },
		                { line: true },
		                {text: '修改', click: updateUnit,    iconClass: 'icon_edit'},
        		  		{ line : true },
        		  		{text: '删除', click: deleteUnit, iconClass: 'icon_delete'},
        		  		{ line : true }
		              
	                ]
                }            
            });
		}
		//初始化resourceSetGrid处理
		function resourceSetGrid() {
			resourceSetGrid = $("#resourceSet").quiGrid({
				columns:[
					{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
					{ display: '资源集名称', name: 'resourcesetName', align: 'left', width: "50%",editor: { type: 'text'}},
					//{ display: '资源集代码', name: 'code', align: 'left', width: "25%",editor: { type: 'text'}},
					{display: '备注', name: 'description',align: 'left', width:"50%",editor: { type: 'textarea'}}
				  	],
			 url:'<%=basePath%>/registrationAuthority/raResourceSet/query', 
			 rownumbers:true,
			 checkbox:true,
			 enabledEdit: true,
	         height: '100%',
	         percentWidthMode:true, 
	         width:"100%",
	         pageSize:5,
	         clickToEdit: false,
	         toolbar: 
					{ 
					items: [
        		  		{text: '删除', click: deleteResourceSet, iconClass: 'icon_delete'},
        		  		{ line : true }
		              
	                ]
                }        
			});
		}
		
		//删除
		function onDelete(rowidx){
			top.Dialog.confirm("确定要删除该记录吗？",function(){
			  	//删除记录
			  	var row = grid.getRow(rowidx)
			  	$.post('<%=basePath %>registrationAuthority/del', 
			  	       {'ids':row.id},
			  			function(result){
			  				handleResult(result);
						},
						'json');
				//刷新表格
				grid.loadData();		
			});
				
		}
		
		//编辑
		 function beginEdit(rowid) { 
            grid.beginEdit(rowid);
        }
        
        //取消编辑
        function cancelEdit(rowid) { 
            grid.cancelEdit(rowid);
        }
        
        //结束编辑
        function endEdit(rowid)
        {
            grid.endEdit(rowid);
        }

		
		//新增
		function addRegistrationAuthority(){
			  Dialog.open({
				URL:'<%=basePath %>registrationAuthority/add',
				Title:"添加",
				Width:550,
				Height:250
				});
			 
		}
		
		//修改
		function updateUnit() {
			var selectedRow = grid.getSelectedRow();
			if(selectedRow == null){
				top.Dialog.alert("请选择数据！");
				return;
			}
			var id = selectedRow.id;
			Dialog.open({
				URL:'<%=basePath %>registrationAuthority/update?id=' + id,
				Title:"修改",
				Width:600,
				Height:250
				});
		}
	function deleteUnit(){
			var selectedRow = grid.getSelectedRow();
			if(selectedRow == null){
				top.Dialog.alert("请选择要删除的数据！");
				return;
			}
			var rows = grid.getSelectedRows();
			var rowsLength = rows.length;
			
			if(rowsLength > 1) {
				top.Dialog.alert("请选择单条数据!");
				return;
			}
			top.Dialog.confirm("确定要删除该记录吗？",function(){
				var id = selectedRow.id;
			  	//删除记录
				$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>registrationAuthority/del?ids='+selectedRow.id,
				  success:function(result){
					  if(result.status == 1){
						grid.loadData();
					   }else{top.Dialog.alert(result.SYS_ERROR);}
				  },
				  dataType: "json",
				  //async:false
				});		
				//刷新表格
				grid.loadData();		
			});	
		}
		
		//获取所有选中行获取选中行的id 格式为 ids=1&ids=2 
	function getSelectIds(grid) {
		var selectedRows = grid.getSelectedRows();
		var selectedRowsLength = selectedRows.length;
		var ids = "";
		
		for(var i = 0;i<selectedRowsLength;i++) {
			ids += selectedRows[i].userId + ",";
		}
		return {"ids":ids};
	}
		//全部确认修改
       function endAllEdit(){
        	grid.endEdit();
        }
       
       
       //全部取消修改
        function cancelAllEdit()
        {
            grid.cancelEdit();
        }
        

		//编辑前事件 
        function onBeforeEdit(e)
        {
         	var str="编辑前事件，可阻止某些行或列进行编辑。列名："+e.column.name+"；行号："+e.rowindex+"；编辑前的值："+e.value+"\n";
         	//if(e.record.id=="121"){
         		//top.Dialog.alert("此行不可编辑",null,null,null,2);
         		// return false;
         	//}
        }
        
		//编辑后事件 
        function onAfterSubmitEdit(e)
        {
        	//在这里一律作修改处理
			var rowData = e.newdata;
        	rowData.id = e.record.id;
        	//ajax方式提交数据到数据库
            $.post("<%=basePath %>registrationAuthority/saveUpdate",rowData,function(result){
            	if(result.id ==0 || result.id == ''){
            		top.Dialog.alert(result.message);
		    	}
            },"json");
            //grid.loadData();
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
	
	   //查询
    function searchHandler(){
    	 //var query = $("#queryForm").formToArray(); 
    	 var query = {
    	 	name:$("#searchInput").val(),
    	 	categoryName:$("#searchInput2").val(),
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
		//searchHandler();
    }
    
     //编辑提交前事件 
        function onBeforeSubmitEdit(e){
     		if(e.newdata.name==""){
     			top.Dialog.alert("名称不能为空！",null,null,null);
     			return false;
     		}
     		else if (!validateInput(e.newdata.name, "^[\u4e00-\u9fa5]+$")){
     			top.Dialog.alert("名称需要是中文！",null,null,null);
     			return false;
     		}
         		 
     		if(e.newdata.code==""){
     			top.Dialog.alert("编号不能为空！",null,null,null);
     			return false;
     		}
     		else if (!validateInput(e.newdata.code, "^[0-9a-zA-Z]+$")){
     			top.Dialog.alert("编号需要是字母或数字！",null,null,null);
     			return false;
     		}
         		 
        }
     
        function addResourceSet(){
        	var resourceSetId = $("#resourceSetSelect").val();
    		var selectedRow = grid.getSelectedRow();
    		if(selectedRow == null){
    			top.Dialog.alert("请选择注册机构！",null,null,null);
    		}
    		var raId = selectedRow.id;
    		$.ajax({ url: '<%=basePath%>registrationAuthority/resourceSet/add',
    			     async:false,
    			     data: {raId:raId,resourceSetId:resourceSetId},
    			     success: function(result){
    			    	 if(result == "SYS_SUCCESS"){
    			    		 top.Dialog.alert("保存成功！",null,null,null);
    			    		 resourceSetGrid.loadData();
    			    		 initTree(roleId,true);
    			    	 }
    			    	 else{
			    			top.Dialog.alert(result);	
			    		}
    			     },
    			 	 error: function(xhr, textStatus, errorThrown){
    			 	}
    			});
     
    		
    	 }
    	 
    	 function deleteResourceSet(){
			var selectedRow = resourceSetGrid.getSelectedRow();
			if(selectedRow == null){
				alert("请选择要删除的数据！");
				return;
			}
			var rows = resourceSetGrid.getSelectedRows();
			var rowsLength = rows.length;
			
			if(rowsLength > 1) {
				top.Dialog.alert("请选择单条数据!");
				return;
			}
			top.Dialog.confirm("确定要删除该记录吗？",function(){
			  	//删除记录
				$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>registrationAuthority/deleteResourceSet?ids='+selectedRow.id,
				  success:function(result){
					  if(result.status == 1){
						resourceSetGrid.loadData();
					   }else{top.Dialog.alert(result.SYS_ERROR);}
				  },
				  dataType: "json",
				  //async:false
				});		
				//刷新表格
				resourceSetGrid.loadData();		
			});	
		}
</script>		
</body>
</html>