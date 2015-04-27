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

</head>
<body>
	<table >
	<tr>
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
				<div class="box1" panelTitle="查询" showStatus="false">
					<form action="<%=path%>/getUsersOfPager.action" id="queryForm" method="post">
					<div class="filterProper_main">
						<div class="filterProper_content">
							<table class="filterProper_searchTable">
								<tr>
									<td>账号：</td>
									<td>
										<input type="text" id="searchInputLoginName" name="user.loginName"/>
										<input type="text" style="width:2px;display:none;"/>
									</td>
									<td>姓名：</td>
									<td>
										<input type="text" id="searchInputUser" name="user.name"/>
										<input type="text" style="width:2px;display:none;"/>
									</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									
								</tr>
								<tr>
									<td>注册机构：</td>
									<td>
										<input type="text" id="searchInputRa" name="user.raName"/>
										<input type="text" style="width:2px;display:none;"/>
									</td>
							
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									
									<td style="padding-left: 10px;"><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
									<td style="padding-left: 5px;"><button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button></td>
								</tr>
							</table>
						</div>
					</div>
					</form>
					</div>
					<div class="padding_right5">
						<div id="user"></div>
					</div>
			</td>
		<!--右侧区域end-->
		
		<!--左侧区域start-->
		<td  class="ver01" >
				<div class="box1" panelTitle="查询" showStatus="false">
				<div class="cusBoxContent"  style="width:500px;">
					<div class="padding_right5">
						<div id="role"></div>
					</div>
				</div>
				</div>	
			</td>
		<!--左侧区域end-->
	</tr>
	</table>
<script type="text/javascript">
	function closeWin(){
		Dialog.close();
	}
	
	 var userCategoryList = <%=JsonUtil.array2JSON(request.getAttribute("categoryList"))%>;
       var userCategoryData = {"list":[]};
       var userCategory;
       for(var i = 0; i < userCategoryList.length; i++){
       		userCategory = {'value':userCategoryList[i].code, 'key':userCategoryList[i].name};
       		
       		userCategoryData.list.push(userCategory);
       }
       
       var raList = <%=JsonUtil.array2JSON(request.getAttribute("raList"))%>;
       var raData = {"list":[]};
       var ra;
       for(var i = 0; i < raList.length; i++){
       		ra = {'value':raList[i].id, 'key':raList[i].name};
       		
       		raData.list.push(ra);
       }
	//定义grid
	var gridUser = null;
	var gridRole = null;
	//初始化函数
	function initComplete(){
		//初始化grid组件
		initGridUser();
		initGridRole();
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
				{ display: '账号', name: 'account', align: 'left', width: "15%",editor: { type: 'text'}},
				{ display: '姓名', name: 'name', align: 'left', width: "15%",editor: { type: 'text'}},
				{ display: '编号', name: 'code', align: 'left', width: "10%",editor: { type: 'text'}},
	            {display: '用户类别', name: 'categoryCode',align: 'left',width:"10%", editor: { type: 'select',data:userCategoryData,selWidth:100},render:function (item){
	                	for (var i = 0; i < userCategoryData["list"].length; i++)
                        {
                            if (userCategoryData["list"][i]['value'] == item.categoryCode)
                                return userCategoryData["list"][i]['key'];
                        }
                        return item.categoryName;
	             }},
	             {display: '注册机构', name: 'raId',align: 'left',width:"10%", editor: { type: 'select',data:raData,selWidth:100},render:function (item){
	                	for (var i = 0; i < raData["list"].length; i++)
                        {
                            if (raData["list"][i]['value'] == item.raId)
                                return raData["list"][i]['key'];
                        }
                        return item.raName;
	             }},
	             { display: '创建人', name: 'createPerson', align: 'left', width: "10%", editor: { type: 'text'}},
		            { display: '创建时间', name: 'createTime', align: 'left', width: "10%",render : function(rowdata, rowindex, value, column){
						if (value == null) {
							return null;
						}
						var timeText = date2Text(value, 'yyyy-MM-dd');
						return timeText;
	                }},
	            //{ display: '启用状态', name: 'activeStatus', align: 'left', width: "10%", editor: { type: 'text'}},
	            //{ display: '锁定状态', name: 'useStatus', align: 'left', width: "10%", editor: { type: 'text'}},
	            { display: '邮箱', name: 'email', align: 'left', width: "20%", editor: { type: 'text'}}
	            //{ display: '备注', name: 'description', align: 'left', width: "10%", isSort:false,editor: { type: 'textarea'}},
           		
			  ],
		 url:'<%=basePath%>user/query', 
		 rownumbers:true,
		 checkbox:true,
		 //enabledEdit: true,
         height: '100%',
         percentWidthMode:true, 
         pageSize:10,
         clickToEdit: false,
         onDblClickRow:function(rowdata, rowindex){
            		gridUser.beginEdit(rowindex);
                },
         onSelectRow:function(rowdata, rowindex, rowDomElement){
            var query = {
						userId: rowdata.id,
					};
			gridRole.setOptions({ params : query});
			gridRole.loadData();
         },
          onCheckRow:function(checked, rowdata, rowindex, rowDomElement){
    		    	var rows = gridUser.getCheckedRows();
    		    	for(var i = 0; i < rows.length; i++){
    		    		gridUser.unselect(rows[i].__id);
    		    	}
    		    	gridUser.select(rowindex);
         },
        // onBeforeEdit: onBeforeEdit, 
         //onBeforeSubmitEdit: onBeforeSubmitEdit,
         //onAfterSubmitEdit: onAfterSubmitEdit,
         toolbar:{
        	 items:[
        		  {text: '新增非院内用户', click: addUnit,    iconClass: 'icon_add'},
        		  { line : true },
        		  {text: '新增院内用户', click: addPersonUnit,    iconClass: 'icon_add'},
        		  { line : true },
        		  {text: '修改', click: updateUnit,    iconClass: 'icon_edit'},
        		  { line : true },
        		  {text: '删除', click: deleteUnit,    iconClass: 'icon_delete'},
        		  { line : true }
        		  //{text: '批量删除', click: deleteUnits, iconClass: 'icon_delete'},
        		  //{ line : true }
        		]
         	}
		});
	}
	//角色JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ
	//初始化Grid处理
	function initGridRole() {
		gridRole = $("#role").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', width: "0%", hide:true},
				{ display: '名称', name: 'name', align: 'left', width: "25%",editor: { type: 'text'}},
				{ display: '编号', name: 'code', align: 'left', width: "25%",editor: { type: 'text'}},
	            { display: '角色分类', name: 'categoryName', align: 'left', width: "50%", editor: { type: 'text'}},
	           // { display: '备注', name: 'description', align: 'left', width: "25%", isSort:false,editor: { type: 'textarea'}},
           		
			  ],
		 url:'<%=basePath%>user/roleUserRelation/query',
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
		//新增
		function addUnit() {
			//Dialog.open({
			//	URL:'<%=basePath %>user/add',
			//	Title:"新增",
			//	Width:600,
			//	Height:300
			//	});
			var diag = new top.Dialog();
		
			$.extend(diag,myproper.defaultGridDialogOptions);
			diag.Title = "新增用户";
			diag.properType="add";
	    	diag.URL = "<%=basePath %>user/add";
	    	diag.Width = 600;
	    	diag.Height = 300;
	    	diag.OKEvent = function(){
	        var valid = diag.innerFrame.contentWindow.valids(); 
	        if(valid){     
		      	$.post(
		        	"<%=path%>/user/save",
		       		diag.innerFrame.contentWindow.getParams(),
		           	function(result){
		        		console.log(result);
		        		if(result == "SYS_SUCCESS"){
		           			myproper.defaultFuncs.addSuccess(gridUser,diag);
		           		}else{
		           				top.Dialog.alert(result, function() { });
		           		}
		 			},
		         	"json");
			}else{
				top.Dialog.alert(myproper.defaultMsgs.formInvalid);
			}
	    	};
	    	diag.show();
		}
		//新增人员账号
		function addPersonUnit() {
				var selected = [];
				proper.personchoose({
				userId:'${userId}',
					basePath:'<%=contextPath%>',
					checkbox:true,
					selectIds:selected,
					scope:0,
					selected:function(persons,dialog){
							var pText=[];
							var pId=[];
							var personIds = '';
							var pers="";
						    for(var i=0;i<persons.length;i++){
								var p = persons[i];
								pText.push(p.name);
								pId.push(p.personId);
								personIds = personIds+p.personId;
								if(i<persons.length-1)
								{
									personIds = personIds+",";
								}
								pers=pers+","+p.personId+":"+p.name;
							}
							$.ajax({type: 'POST',
								url: '<%=basePath %>/user/addPerson',
								data:{personIds:personIds},
								success:function(result){
									console.log(result);
		        					if(result == "SYS_SUCCESS"){
		           						myproper.defaultFuncs.addSuccess(gridUser,dialog);
		           					}else{
		           						alert(result);
// 		           						dialog.close();
		           					}
								},
								dataType: "json",
								async:false
							});	
							return false;
					}
				});
	
		}
		//修改
		function updateUnit() {
		
		
		   var selectedRow = gridUser.getSelectedRows();
		   
			if(selectedRow.length == 0){
				top.Dialog.alert("请选择要修改的用户！");
				return;
			}
			if(selectedRow.length > 1){
				top.Dialog.alert("请选择单个用户！");
				return;
			}
			
			var id = selectedRow[0].id;
			var diag = new top.Dialog();
		
			$.extend(diag,myproper.defaultGridDialogOptions);
			diag.Title = "修改用户";
			diag.properType="add";
	    	diag.URL = "<%=basePath %>user/update?id=" + id;
	    	diag.Width = 600;
	    	diag.Height = 300;
	    	diag.OKEvent = function(){
	        var valid = diag.innerFrame.contentWindow.valids(); 
	        if(valid){     
		      	$.post(
		        	"<%=path%>/user/save",
		       		diag.innerFrame.contentWindow.getParams(),
		           	function(result){
		        		console.log(result);
		        		if(result == "SYS_SUCCESS"){
		           			myproper.defaultFuncs.addSuccess(gridUser,diag);
		           		}else{
		           				top.Dialog.alert(result, function() { });
		           		}
		 			},
		         	"json");
			}else{
				top.Dialog.alert(myproper.defaultMsgs.formInvalid);
			}
	    	};
	    	diag.show();
			//Dialog.open({
			//	URL:'<%=basePath %>user/update?id=' + id,
			//	Title:"修改",
			//	Width:600,
			//	Height:350
			//	});
		}
		function deleteUnit(){
		   var selectedRow = gridUser.getSelectedRows();
			if(selectedRow.length == 0){
				top.Dialog.alert("请选择要删除的用户！");
				return;
			}
			if(selectedRow.length > 1){
				top.Dialog.alert("请选择单个用户！");
				return;
			}
			//var selectedRow = gridUser.getSelectedRow();
			//if(selectedRow == null){
			//	top.Dialog.alert("请选择要删除的数据！");
			//	return;
			//}
		  	if(selectedRow[0].loginName == "admin"){
		  		top.Dialog.alert("此用户不可以删除!");
				return;
		  	}
			
			top.Dialog.confirm("确定要删除该记录吗？",function(){
				var id = selectedRow[0].id;
			  	//删除记录
				$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>user/del?ids='+selectedRow[0].id,
				  success:function(result){
					  if(result.status == 1){
						gridUser.loadData();
					   }else{top.Dialog.alert(result.SYS_ERROR);}
				  },
				  dataType: "json",
				  //async:false
				});		
				//刷新表格
				gridUser.loadData();		
			});	
		}
		//删除角色
		function deleteUnitRole(){
		   var userRows = gridUser.getSelectedRows();
// 		   	if(userRows.length > 1){
// 				top.Dialog.alert("请选择单个用户！");
// 				return;	
// 			}
		   var rows = gridRole.getSelectedRows();
		   
			if(rows.length == 0){
				top.Dialog.alert("请选择要删除的角色！");
				return;
			}
			if(rows.length > 1){
				top.Dialog.alert("请选择单个角色！");
				return;
			}
		   var userId = userRows[0].id;
			top.Dialog.confirm("确定要删除该角色吗？",function(){
			  	//删除记录
			  	var row = gridRole.getSelectedRow();
				$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>user/roleUserRelation/del?roleId='+row.id+'&userId='+userId,
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
			var row = gridUser.getSelectedRows();
			if(row.length == 0){
				top.Dialog.alert("请选择用户！");
				return;
			}
			if(row.length > 1){
				top.Dialog.alert("请选择单个用户！");
				return;
			}
			var diag = new top.Dialog();
			diag.ShowMaxButton=true;
			diag.ShowMinButton=true;
			diag.MinToTask=true;
			diag.URL = '<%=basePath%>user/role/add',
			diag.Title = "添加角色",
			diag.Width=800,
			$.extend(diag,myproper.defaultGridDialogOptions);
			diag.OKEvent = function(){
				var user = gridUser.getSelectedRow();
				var userId = user.id;
				if(user == null){
					return;
				}
				var rows = diag.innerFrame.contentWindow.mytest();
				
				//var data = gridRolebbb.getData();
		  			var ids = [];
		  			for(var i=0; i<rows.length; i++){
		  				ids.push(rows[i].id);
		  			}
		  		$.ajax({ url: '<%=basePath %>user/role/save',
					         async:false,
					         data: {ids:ids.join(","),userId:userId},
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
		var rows = gridUser.getSelectedRows();
		var rowsLength = rows.length;
		
		if(rowsLength == 0) {
			top.Dialog.alert("请选中要删除的记录!");
			return;
		}
		top.Dialog.confirm("确定要删除吗？",function(){
			$.post('<%=basePath %>user/del',
					//获取所有选中行
					getSelectIds(gridUser),
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
		var rowData = gridUser.getSelectedRows();
			if(result.status == 1){
				gridUser.loadData();
				return;
			}
			
			top.Dialog.alert(result.SYS_ERROR);
		}
	//获取所有选中行获取选中行的id 格式为 ids=1&ids=2 
	function getSelectIds(gridUser) {
		var selectedRows = gridUser.getSelectedRows();
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
		 gridUser.setOptions({ params : query});
		 //页号重置为1
		 gridUser.setNewPage(1);
		//刷新表格数据 
		gridUser.loadData();
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
						gridUser.loadData();
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
	function getSelectIds(gridUser) {
		var selectedRows = gridUser.getSelectedRows();
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