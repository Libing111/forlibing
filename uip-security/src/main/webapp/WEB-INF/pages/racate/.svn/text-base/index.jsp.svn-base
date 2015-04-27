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
<!-- 树形下拉框end -->
</head>
<body>
<table width="100%">
	<tr>
	<!--左侧区域start-->
		<td width="100%" class="ver01" >
		
			<div class="padding_right5">
				<div id="racate"></div>
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
						<input type="button" value="删除" onclick="deleteResourceSet()"/>
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
		grid = $("#racate").quiGrid({
         columns:[
				{display: '名称', name: 'name',align: 'left',width:"50%", editor: { type: 'text' , detailEdit:false}},
				{display: '编号', name: 'code',align: 'left',width:"50%",editor: { type: 'text'}}
                ], 
              url:'<%=basePath%>secureconf/racate/resset/racate/query', 
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
              onDblClickRow:function(rowdata, rowindex){
            		grid.beginEdit(rowindex);
                },
               onSelectRow:function(rowdata, rowindex, rowDomElement){
                    var query = {
                    		raCategoryId: rowdata.id,
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
	                ]
                }            
            });
		}
		//初始化resourceSetGrid处理
		function resourceSetGrid() {
			resourceSetGrid = $("#resourceSet").quiGrid({
				columns:[
					{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
					{ display: '资源集名称', name: 'resourceSetName', align: 'left', width: "50%",editor: { type: 'text'}},
					{ display: '资源集代码', name: 'resourceSetCode', align: 'left', width: "50%",editor: { type: 'text'}},
					//{display: '备注', name: 'description',align: 'left', width:"50%",editor: { type: 'textarea'}}
				  	],
			 url:'<%=basePath%>/secureconf/racate/resset/resourceSet/query', 
			 rownumbers:true,
			 checkbox:true,
			 enabledEdit: true,
	         height: '100%',
	         percentWidthMode:true, 
	         width:"100%",
	         pageSize:5,
	         clickToEdit: false,
	        
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
			  var row = grid.getRow(0);
			  var rowData={
			  			id:'',
					    name:"",
						code:"",
						adminAccount:"",
						categoryCode:"",
						lastChangePerson:"",
						changeTime: new Date(),
						createPerson:"",
						createTime:new Date(),
						description:""
					}
			  grid.addEditRow(rowData, row, true);
			 
		}
		

		//删除后的提示
		function handleResult(result){
			if(result.status == 1){
				//top.Dialog.alert("删除成功！",null,null,null);
				grid.loadData();
				return;
			}
			
			top.Dialog.alert(result.SYS_ERROR);
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
		searchHandler();
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
    			top.Dialog.alert("请选择注册机构分类！",null,null,null);
    		}
    		var raCategoryId = selectedRow.id;
    		$.ajax({ url: '<%=basePath%>secureconf/racate/resset/save',
    			     async:false,
    			     data: {raCategoryId:raCategoryId,resourceSetId:resourceSetId},
    			     success: function(result){
    			    	 if(result == "SYS_SUCCESS"){
    			    		 top.Dialog.alert("保存成功！",null,null,null);
    			    		 initTree(roleId,true);
    			    	 }
    			     },
    			 	 error: function(xhr, textStatus, errorThrown){
    			 	}
    			});
     
    		
    	 }
		function deleteResourceSet(){
			   var rows = grid.getSelectedRows();
			   var raCategoryId = rows[0].id;
				top.Dialog.confirm("确定要删除该记录吗？",function(){
				  	//删除记录
				  	var row = resourceSetGrid.getSelectedRow();
					$.ajax({
					  type: 'POST',
					  url: '<%=basePath %>secureconf/racate/resset/del?resourceSetId='+row.resourceSetId+'&raCategoryId='+raCategoryId,
					  success:function(result){
								if(result.status == 1){
									resourceSetGrid.loadData();
									return;
								}
								
								top.Dialog.alert(result.SYS_ERROR);
							  },
					  dataType: "json",
					 // async:false
					});		
					//刷新表格
					resourceSetGrid.loadData();		
				});
					
			}
</script>		
</body>
</html>