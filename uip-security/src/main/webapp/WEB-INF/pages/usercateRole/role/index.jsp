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
<!--数据表格end-->

</head>
<body>
	<table >
	 
	<tr>

		<!--右侧区域start-->
		<td width="100%" class="ver01" >
			<div class="padding_right5">
				<div id="userCategory"></div>
			</div>
		</td>
		<!--右侧区域end-->
	
		<!--左侧区域start-->
		<td  class="ver01" >
				<div class="cusBoxContent"  style="width:500px;">
					<div class="padding_right5">
						<div id="role"></div>
					</div>
				</div>
			</td>
		<!--左侧区域end-->
	</tr>
	</table>
<script type="text/javascript">
	//定义grid
	var gridUserCategory = null;
	var gridRole = null;
	//初始化函数
	function initComplete(){
		//初始化grid组件
		initGridUserCategory();
		initGridRole();
		//监听查询框的回车事件
		 $("#searchInputUser").keydown(function(event){
		 	if(event.keyCode==13){
				searchHandler();
			}
		 });
	}
	
	//初始化Grid处理
	function initGridUserCategory() {
		gridUserCategory = $("#userCategory").quiGrid({
		columns:[
					{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
					{ display: '名称', name: 'name', align: 'left', width: "50%",editor: { type: 'text'}},
					{ display: '编号', name: 'code', align: 'left', width: "50%",editor: { type: 'text'}},
			  	],
		 url:'<%=basePath%>secureconf/usercate/role/usercategory/query', 
		 rownumbers:true,
		 checkbox:true,
		 //enabledEdit: true,
         height: '100%',
         percentWidthMode:true, 
         pageSize:10,
         toolbar:{
        	 items:[
        		]
         	},
         clickToEdit: false,
         onDblClickRow:function(rowdata, rowindex){
        	 gridUserCategory.beginEdit(rowindex);
                },
         onSelectRow:function(rowdata, rowindex, rowDomElement){
            var query = {
            		userCategoryId: rowdata.id,
					};
			gridRole.setOptions({ params : query});
			gridRole.loadData();
         },
		});
	}
	//角色JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ
	//初始化Grid处理
	function initGridRole() {
		gridRole = $("#role").quiGrid({
		 columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
				{ display: '名称', name: 'name', align: 'left', width: "50%",editor: { type: 'text'}},
	            { display: '角色分类', name: 'categoryName', align: 'left', width: "50%", editor: { type: 'text'}},
			  ],
		 url:'<%=basePath%>secureconf/usercate/role/query',
		 rownumbers:true,
		 checkbox:true,
		 enabledEdit: true,
         height: '100%',
         percentWidthMode:true, 
         width:"100%",
         pageSize:5,
         clickToEdit: false,
         onBeforeEdit: onBeforeEdit, 
         onBeforeSubmitEdit: onBeforeSubmitEdit,
         onAfterSubmitEdit: onAfterSubmitEdit,
         toolbar:{
        	 items:[
        		  {text: '添加角色', click: addUnitRole,    iconClass: 'icon_add'},
        		  { line : true },
        		  {text: '删除', click: deleteUnitRole, iconClass: 'icon_delete'},
        		  { line : true },
        		]
         	}
		});
	}
		
		//全部确认修改
       function endAllEdit(){
        	gridUser.endEdit();
        }
         //全部取消修改
        function cancelAllEdit()
        {
            gridUser.cancelEdit();
        }
		//编辑
		 function beginEdit(rowid) { 
            gridUser.beginEdit(rowid);
        }
        
        //取消编辑
        function cancelEdit(rowid) { 
            gridUser.cancelEdit(rowid);
        }
        
        //结束编辑
        function endEdit(rowid)
        {
            gridUser.endEdit(rowid);
        }
		
		//删除角色
		function deleteUnitRole(){
		   var rows = gridUserCategory.getSelectedRows();
		   var userCategoryId = rows[0].id;
			top.Dialog.confirm("确定要删除该记录吗？",function(){
			  	//删除记录
			  	var row = gridRole.getSelectedRow();
				$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>secureconf/usercate/role/del?roleId='+row.id+'&userCategoryId='+userCategoryId,
				  success:function(result){
							if(result.status == 1){
								gridRole.loadData();
								return;
							}
							
							top.Dialog.alert(result.SYS_ERROR);
						  },
				  dataType: "json",
				 // async:false
				});		
				//刷新表格
				gridRole.loadData();		
			});
				
		}
		//新增
		function addUnitRole() {
			var row = gridUserCategory.getSelectedRow();
			if(row == null){
				top.Dialog.alert("请选择用户分类!");
				return false;
			}
			var diag = new top.Dialog();
			diag.ShowMaxButton=true;
			diag.ShowMinButton=true;
			diag.MinToTask=true;
			diag.URL = '<%=basePath%>secureconf/usercate/role/add',
			diag.Title = "添加角色",
			diag.Width=800,
			diag.OKEvent = function(){
				var userCategory = gridUserCategory.getSelectedRow();
				var userCategoryId = userCategory.id;
				if(userCategory == null){
					return;
				}
				var rows = diag.innerFrame.contentWindow.mytest();
				
				//var data = gridRolebbb.getData();
		  			var ids = [];
		  			for(var i=0; i<rows.length; i++){
		  				ids.push(rows[i].id);
		  			}
		  		$.ajax({ url: '<%=basePath %>secureconf/usercate/role/save',
					         async:false,
					         data: {ids:ids.join(","),userCategoryId:userCategoryId},
					         success: function(){
					         			},
    				 		 error: function(xhr, textStatus, errorThrown){
    				 		 }
    				 });
				gridRole.loadData();
				diag.close();
			};
			diag.CancelEvent = function(){
			diag.close();
			};
			diag.ShowButtonRow=true,
			diag.show();
		}
	
	
		
	
	//批量删除
	function deleteUnits() {
		var rows = gridUserCategory.getSelectedRows();
		var rowsLength = rows.length;
		
		if(rowsLength == 0) {
			top.Dialog.alert("请选中要删除的记录!");
			return;
		}
		top.Dialog.confirm("确定要删除吗？",function(){
			$.post('<%=basePath %>user/del',
					//获取所有选中行
					getSelectIds(gridUserCategory),
					function(result){
						handleResult(result);
					},
					"json");
		});
	}
	//批量删除角色
	function deleteUnitRoles() {
		var rows = gridRole.getSelectedRows();
		var rowsLength = rows.length;
		
		if(rowsLength == 0) {
			top.Dialog.alert("请选中要删除的记录!");
			return;
		}
		top.Dialog.confirm("确定要删除吗？",function(){
			$.post('<%=basePath %>user/roleUserRelation/del',
					//获取所有选中行
					getSelectIds(gridRole),
					function(result){
						handleResult(result);
					},
					"json");
		});
	}
	
	
	//删除后的提示
		function handleResult(result){
		var rowData = gridUserCategory.getSelectedRows();
			if(result.status == 1){
				gridUserCategory.loadData();
				return;
			}
			
			top.Dialog.alert(result.SYS_ERROR);
		}
	//获取所有选中行获取选中行的id 格式为 ids=1&ids=2 
	function getSelectIds(gridUserCategory) {
		var selectedRows = gridUserCategory.getSelectedRows();
		var selectedRowsLength = selectedRows.length;
		var ids = "";
		
		for(var i = 0;i<selectedRowsLength;i++) {
			ids += selectedRows[i].userId + ",";
		}
		return {"ids":ids};
	}

	
	
    
    //查询
    function searchHandler(){
    	 var query = {
    	 	name:$("#searchInputUser").val(),
    	 	loginName:$("#searchInputLoginName").val(),
    	 	raName:$("#searchInputRa").val(),
    	 };
		 gridUserCategory.setOptions({ params : query});
		 //页号重置为1
		 gridUserCategory.setNewPage(1);
		//刷新表格数据 
		gridUserCategory.loadData();
    }
    
    //重置查询
    function resetSearch(){
    	$("#queryForm")[0].reset();
		searchHandler();
    }
    
    
	
	//处理高度自适应，每次浏览器尺寸变化时触发
	function customHeightSet(contentHeight){
		$(".cusBoxContent").height(contentHeight-55);
	}
	
		//编辑前事件 
    function onBeforeEdit(e){
         	var str="编辑前事件，可阻止某些行或列进行编辑。列名："+e.column.name+"；行号："+e.rowindex+"；编辑前的值："+e.value+"\n";
     }
        
		//编辑后事件 
    function onAfterSubmitEdit(e){
			var rowData = e.newdata;
        	rowData.id = e.record.id;
          	$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>user/save',
			  data:rowData,
			  success:function(result){
						gridUserCategory.loadData();
					  },
			  dataType: "json",
			  async:false
			});  
    }
    
     //编辑提交前事件 
        function onBeforeSubmitEdit(e){
       		var rowData = e.newdata;
       		rowData.id = e.record.id;
     		if(e.newdata.name==""){
     			top.Dialog.alert("名称不能为空！",null,null,null);
     			return false;
     		}
     		var returnValue = true;
	     		$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>user/queryUnique',
				  data:rowData,
				  success:function(result){
							returnValue = unitResult(result);
						  },
				  dataType: "json",
				  async:false
				});
			
			if(returnValue == false){
				return false;
			}
     		if (!validateInput(e.newdata.email, /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
     			top.Dialog.alert("请输入正确的email格式！",null,null,null);
     			return false;
     		}
         		 
        }
        //判断序号是否唯一
		function unitResult(result){
			if(result.status != 1){
				top.Dialog.alert(result.SYS_ERROR);
				return false;
			}
			return true;
		}
		
		//获取所有选中行获取选中行的id 格式为 ids=1&ids=2 
	function getSelectIds(gridUserCategory) {
		var selectedRows = gridUserCategory.getSelectedRows();
		var selectedRowsLength = selectedRows.length;
		var ids = "";
		
		for(var i = 0;i<selectedRowsLength;i++) {
			ids += selectedRows[i].id + ",";
		}
		return {"ids":ids};
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