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
<title>角色管理</title>
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>
<!--框架必需end-->
<!--布局控件start-->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/nav/layout.js"></script>
<!--布局控件end-->

<!-- 条件过滤器 start -->

<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/filter.js"></script>

<!-- 条件过滤器 end -->
</head>
<body>
<div id="layout1">
<div  position="left">  
 						<form id="queryForm" method="post">
							<div class="filterDemo_main">
								<div class="filterDemo_content">
								<table class="filterDemo_searchTable">
									<tr>
										<td class="left">名称：</td>
										<td  class="right">
										<input type="text" id="roleName" name="roleName"/>
										</td>
													<!--<td>分类：</td>
													<td>
														<input type="text" id="searchInputCategoryName" name="role.categoryName"/>
													</td>
													<td>注册机构：</td>
													<td>
														<input type="text" id="searchInputRa" name="user.raName"/>
													</td>  -->
										<td style="padding-left: 5px;"><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
										<td width="4"></td>
										<td><button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button></td>
									
									</tr>
								</table>
								</div>
							
							</div>
					</form>
					<div class="padding_right5">
						<div id="dataBasic"></div>
					</div>
</div>
<div id="centerCon" position="center" style="over">
	 <div class="layout_content">
							<table class="filterTable">
								<tr>
									<td colspan="2" class="left" class="ali02 padding5">
										<input type="button" value="全选" onclick="allResource()"/>
										<input type="button" value="取消选择" onclick="resetForm()"/>
										<input type="button" value="提交" onclick="addResource()"/>
									</td>
								</tr>
								<c:forEach var="resourceParent" items="${resourcePerentList}" varStatus="status">
									<tr>
										<td   width="100" style="cursor:pointer" class="left" onclick="onclickpaging('${resourceParent.code}')">${resourceParent.simpleName}</td>
										<td class="right"><div class="filter" multiMode="true" showTip="true" id="${resourceParent.code}" url="<%=basePath%>role/queryChildResource?parentChildCode=${resourceParent.code}" ></div></td>
									</tr>
								</c:forEach>

							</table>
	  				
	 </div>
</div>

</div>
<script type="text/javascript">
	function closeWin(){
		Dialog.close();
	}
	var resourceList = <%=JsonUtil.array2JSON(request.getAttribute("resourcePerentList"))%>;
 	var roleCategoryList = <%=JsonUtil.array2JSON(request.getAttribute("categoryList"))%>;
    var roleCategoryData = {"list":[]};
    var roleCategory;
    for(var i = 0; i < roleCategoryList.length; i++){
       roleCategory = {'value':roleCategoryList[i].code, 'key':roleCategoryList[i].name};
       		
       roleCategoryData.list.push(roleCategory);
    }
	//定义grid
	var grid = null;
//高度调整
function customHeightSet(contentHeight){
    $(".layout_content").height(contentHeight-30)

}
	//初始化函数
	function initComplete(){
	var layout=$("#layout1").layout(
	 { 
		 leftWidth: "50%",
		 centerWidth:"50%",
		 allowLeftCollapse:false,
		 allowLeftResize:false
      }
    );   
		//初始化grid组件
		initGrid();
	}

	//初始化Grid处理
	function initGrid() {
		grid = $("#dataBasic").quiGrid({
			columns:[
				{ display: 'id', name: 'id', align: 'left', hide:true},
				{ display: '名称', name: 'name', align: 'left', width: "20%",editor: { type: 'text'}},
	            { display: '编号', name: 'code', align: 'left', width: "20%", editor: { type: 'text'}},
	            {display: '分类', name: 'categoryCode',align: 'left',width:"10%", editor: { type: 'select',data:roleCategoryData,selWidth:100},render:function (item){
	                	for (var i = 0; i < roleCategoryData["list"].length; i++)
                        {
                            if (roleCategoryData["list"][i]['value'] == item.categoryCode)
                                return roleCategoryData["list"][i]['key'];
                        }
                        return item.categoryName;
	            }},
	            { display: '创建人', name: 'createPerson', align: 'left', width: "10%", editor: { type: 'text'}},
	            { display: '创建时间', name: 'createTime', align: 'left', width: "20%",render : function(rowdata, rowindex, value, column){
					if (value == null) {
						return null;
					}
					var timeText = date2Text(value, 'yyyy-MM-dd');
					return timeText;
                }},

                { display: '注册机构', name: 'raName', align: 'left', width: "20%", editor: { type: 'text'}}


           		
			  ],
		 url:'<%=basePath%>role/query',
		 rownumbers:true,
		 checkbox:true,
		 isScroll:true,
		// enabledEdit: true,zzz
         height: '100%',
         percentWidthMode:true, 
         width:"100%",
         pageSize:10,
         //clickToEdit: false,
         
         onDblClickRow:function(rowdata, rowindex){
            		grid.beginEdit(rowindex);
                },
         onCheckRow:function(checked, rowdata, rowindex, rowDomElement){
    		    	var rows = grid.getCheckedRows();
    		    	for(var i = 0; i < rows.length; i++){
    		    		grid.unselect(rows[i].__id);
    		    	}
    		    	grid.select(rowindex);
        			
        			dynamicFilter(rowdata.id);
         },
         toolbar:{
        	 items:[
        		  {text: '新增', click: onAdd,    iconClass: 'icon_add'},
        		  { line : true },
        		  {text: '修改', click: onEdit,    iconClass: 'icon_edit'},
        		  { line : true },
        		  {text: '删除', click: deleteUnit, iconClass: 'icon_delete'},
        		  { line : true }
        		]
         	}
		});
		
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
	function onAdd() {
		var diag = new top.Dialog();
		
		$.extend(diag,myproper.defaultGridDialogOptions);
		diag.Title = "新增角色信息";
		diag.properType="add";
	    diag.URL = "<%=basePath %>role/add";
	    diag.Width = 600;
	    diag.Height = 200;
	    diag.OKEvent = function(){
	        var valid = diag.innerFrame.contentWindow.valids(); 
	        if(valid){     
		      	$.post(
		        	"<%=path%>/role/save",
		       		diag.innerFrame.contentWindow.getParams(),
		           	function(result){
		        		console.log(result);
		        		if(result == "SYS_SUCCESS"){
		           			myproper.defaultFuncs.addSuccess(grid,diag);
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
		//修改
		function updateUnit() {
			var selectedRow = grid.getSelectedRow();
			if(selectedRow == null){
				top.Dialog.alert("请选择要修改的记录！");
				return;
			}
			var id = selectedRow.id;
			Dialog.open({
				URL:'<%=basePath %>role/update?id=' + id,
				Title:"修改",
				Width:600,
				Height:350
				});
		}
function onEdit(){
		var selectedRow = grid.getSelectedRow();
		if(selectedRow == null){
			top.Dialog.alert("请选择要修改的记录！");
			return;
		}
		var selectedRows = grid.getSelectedRows();
		if(selectedRows.length > 1){
			top.Dialog.alert("请选择单条记录！");
			return;
		}
		var id = selectedRow.id;
		
		var diag = new top.Dialog();
		$.extend(diag,myproper.defaultGridDialogOptions);
		diag.Title = "修改角色信息";
		diag.properType="edit";
		 diag.Width = 600;
		  diag.Height = 200;
	    diag.URL = "<%=basePath %>role/update?id=" + id;
	    diag.OKEvent = function(){
	        var valid = diag.innerFrame.contentWindow.valids(); 
	        if(valid){     
		      	$.post(
		        	"<%=path%>/role/updateSave",
		       		diag.innerFrame.contentWindow.getParams(),
		           	function(result){
		        		console.log(result);
		        		if(result == "SYS_SUCCESS"){
		        			myproper.defaultFuncs.modifySuccess(grid,diag);
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
	//删除
		function onDelete(rowidx){
			top.Dialog.confirm("确定要删除该记录吗？",function(){
			  	//删除记录
			  	var row = grid.getRow(rowidx);
				$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>role/del?ids='+row.id,
				  success:function(result){
							handleResult(result);
						  },
				  dataType: "json",
				 // async:false
				});		
				//刷新表格
				grid.loadData();		
			});
				
		}
		
		
	//批量删除
	function deleteUnit() {
		var rows = grid.getSelectedRows();
		var rowsLength = rows.length;
		
		if(rowsLength == 0) {
			top.Dialog.alert("请选择要删除的记录!");
			return;
		}
		if(rowsLength > 1) {
			top.Dialog.alert("请选择单条记录!");
			return;
		}
		top.Dialog.confirm("确定要删除吗？",function(){
			$.post('<%=basePath %>role/del',
					//获取所有选中行
					getSelectIds(grid),
					function(result){
						handleResult(result);
					},
					"json");
		});
	}
	
	
	//删除后的提示
		function handleResult(result){
		var rowData = grid.getSelectedRows();
			if(result.status == 1){
				//top.Dialog.alert("删除成功！",null,null,null);
				grid.loadData();
				return;
			}
			top.Dialog.confirm(result.SYS_ERROR,function(){
				$.post('<%=basePath %>role/confirmDel',
						//获取所有选中行
						getSelectIds(grid),
						function(result){
							handleResult(result);
						},
						"json");
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


    //查询
    function searchHandler(){
    	
    	 var query = {
    	 	//parentId:selectTreeNode.id,
    	 	name:$("#roleName").val(),
    	 	//categoryName:$("#searchInputCategoryName").val(),
    	 	//raName:$("#searchInputRa").val(),
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
    
	//编辑前事件 
    function onBeforeEdit(e){
         	var str="编辑前事件，可阻止某些行或列进行编辑。列名："+e.column.name+"；行号："+e.rowindex+"；编辑前的值："+e.value+"\n";
     }
        
		//编辑后事件 
    function onAfterSubmitEdit(e){
        	//在这里一律作修改处理
			var rowData = e.newdata;
        	rowData.id = e.record.id;
          	$.ajax({
			  type: 'POST',
			  url: '<%=basePath %>role/save',
			  data:rowData,
			  success:function(result){
						grid.loadData();
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
     		if(e.newdata.code==""){
     			top.Dialog.alert("编号不能为空！",null,null,null);
     			return false;
     		}
     		var returnValue = true;
	     		$.ajax({
				  type: 'POST',
				  url: '<%=basePath %>role/queryUnique',
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
	function getSelectIds(grid) {
		var selectedRows = grid.getSelectedRows();
		var selectedRowsLength = selectedRows.length;
		var ids = "";
		
		for(var i = 0;i<selectedRowsLength;i++) {
			ids += selectedRows[i].id + ",";
		}
		return {"ids":ids};
	}	

	function addTreeNode(){
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getCheckedNodes();
		
		var sequenceNumbers = "";
		for(var node = 0; node <nodes.length; node++){
			sequenceNumbers = sequenceNumbers + "," + nodes[node].id; 
		}
		if(sequenceNumbers == ""){
			return;
		}
		sequenceNumbers = sequenceNumbers.substring(1, sequenceNumbers.length);
		var selectedRow = grid.getSelectedRow();
		if(selectedRow == null){
			top.Dialog.alert("请选择角色！",null,null,null);
				
		}
		var roleId = selectedRow.id;
		$.ajax({ url: '<%=basePath%>role/tree/update',
			     async:false,
			     data: {roleId:roleId,resourceSequenceNumbers:sequenceNumbers},
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
  function dynamicFilter(roleId){
    
    	$.ajax({
		url: '<%=basePath%>role/queryResource',
		data:{roleId:roleId},
		async:false,
		success: function(result){
				$(".filter").setValue("");
				for (var parentCode in result) {
				    $("#"+parentCode).setValue(result[parentCode]);
				};
			}
		});	  
  }
  
  function onclickpaging(pagingId){
  		var selectedRow = grid.getSelectedRow();
		if(selectedRow == null){
			top.Dialog.alert("请选择角色！",null,null,null);
			return;	
		}
    	$.ajax({
		url: '<%=basePath%>role/clickParentResource',
		async:false,
		data: {pagingId:pagingId},
		success: function(result){
				//$(".filter").setValue("");
				for (var parentCode in result) {
				    $("#"+parentCode).setValue(result[parentCode]);
				};
			}
		});	  
	}
	function allResource(){
		var selectedRow = grid.getSelectedRow();
		if(selectedRow == null){
			top.Dialog.alert("请选择角色！",null,null,null);
			return;		
		}
    	$.ajax({
		url: '<%=basePath%>role/allResource',
		async:false,
		success: function(result){
				//$(".filter").setValue("");
				for (var parentCode in result) {
				    $("#"+parentCode).setValue(result[parentCode]);
				};
			}
		});	  
	}	
	function resetForm(){
    	$(".filterTable .filter").setValue(""); 
	}
   function addResource(){
		
		var selectedRow = grid.getSelectedRow();
		if(selectedRow == null){
			top.Dialog.alert("请选择角色！",null,null,null);
			return;
		}
		
		var childResourceIds = "";
		for(var i = 0; i < resourceList.length; i++){
			childResourceIds =childResourceIds + "," + $("#"+resourceList[i].code).attr("relValue");
		}
		var roleId = selectedRow.id;
		$.ajax({ url: '<%=basePath%>role/addResource',
			     async:false,
			     data: {roleId:roleId,childResourceIds:childResourceIds},
			     success: function(result){
			    	 if(result == "SYS_SUCCESS"){
			    		 top.Dialog.alert("保存成功！",null,null,null);
			    		 dynamicFilter(roleId);
			    	 }
			     },
			 	 error: function(xhr, textStatus, errorThrown){
			 	}
			});
	 }
	 
</script>
</body>
</html>