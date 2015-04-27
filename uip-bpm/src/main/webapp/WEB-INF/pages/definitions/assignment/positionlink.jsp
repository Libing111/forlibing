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
<!--框架必需start-->
<jsp:include page="/common/taglibs.jsp" ></jsp:include>
<!--框架必需end-->
<!-- 树组件start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/tree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" href="<%=contextPath%>file/libs/js/tree/ztree/ztree.css"></link>
<!-- 树组件end -->
<!-- 树形下拉框start -->
<script type="text/javascript" src="<%=contextPath%>file/libs/js/form/selectTree.js"></script>
<!-- 树形下拉框end -->
<!--布局控件start-->
<script src="<%=contextPath%>file/libs/js/nav/layout.js" type="text/javascript"></script>
<!--布局控件end-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.select2-info {
width: 150px;
height: 22px;
line-height: 22px;
border:1px solid  #80c0e7 ;
float: left;
display: inline;
margin: 4px;
cursor: pointer;
cursor: hand;
padding-right: 8px!important;
padding-left: 5px!important;
background-position: 145px 40%;
background-repeat: no-repeat;
background-image: url(<%=contextPath%>file/libs/skins/blue/form/filterCloseHover.gif);
}
</style>
<script>
	var orgSetting = {
		    callback: {
				onClick: orgTreeClick,
			}
// 			,
// 			check: {
// 	        enable: true,
// 	        chkboxType:{ "Y" : "", "N" : "" }
// 	    }
	};
	var jobSetting = {
		    callback: {
				onClick: jobClick,
			}
// 			,
// 			check: {
// 	        enable: true,
// 	        chkboxType:{ "Y" : "", "N" : "" }
// 	    }
	};	
	var personSetting = {
// 			check: {
// 	        enable: true,
// 	        chkboxType:{ "Y" : "", "N" : "" }
// 	    }
	};		
	
	function initComplete(){
	
	     var layout=$("#layout1").layout({ 
				//leftWidth: 320,
				allowLeftCollapse:false,
				allowRightCollapse:false,
				allowLeftResize:false,
				leftWidth:200,
				centerWidth:200,
				rightWidth:200,
				onEndResize:function(){
					$('#layout1').removeClass('l-layout-header');
// 					g.resetWidth();
				}
			});
	     $("#layout2").layout({ 
				bottomHeight: 22,
				allowBottomCollapse:false,
				allowBottomResize:false
			});
	  //获取json数据
		   $.post("<%=basePath %>/definitions/assignment/org/tree",{},function(result){
		        //赋给data属性
	        $.fn.zTree.init($("#orgTree"), orgSetting, result.orgs);
			$("#orgTree").render();
		    },"json");
		    
		    $('.select2-info').live("click",function(){
			$(this).remove();
		});
		    
	}

	function customHeightSet(contentHeight){
	    $(".layout_content").height(contentHeight-30)
	}

	function orgTreeClick(event, treeId, treeNode, clickFlag){
		var orgId = treeNode.id;
	    //获取json数据
	   $.post("<%=basePath %>/definitions/assignment/job/tree",{orgId:orgId},function(result){
	        //赋给data属性
        $.fn.zTree.init($("#jobTree"), jobSetting, result.jobs);
		$("#jobTree").render();
	    },"json");
	}
	function jobClick(event, treeId, treeNode, clickFlag){
		var orgId = treeNode.orgId;
		var jobId = treeNode.id;
		var orgName = "";
		$.post("<%=basePath %>/definitions/assignment/getOrgName",{orgId:orgId},function(result){
			orgName = result.orgName;
			var name = orgName + treeNode.name;
			$("#rightPanel").append("<li class='select2-info' id='"+orgId+jobId+"' type='position' orgId='"+orgId+"' jobId='"+ jobId +"'>"+ name +"</li>");
		},"json");
	    //获取json数据
	}
	function dealPositionLink(){
	//获取父页面的值
	// 		top.document.getElementById("_DialogFrame_identitylink").contentWindow.document.getElementById("test").innerHTML;
		//获取部门选中值
// 		VAR ZTREE = $.fn.ztree.getztreeobj("orgtree");
// 	    var checkednodes = ztree.getcheckednodes(true);
// 	    var identitylinklist = new array();
// 	    for(var i = 0; i < checkednodes.length; i++){
// 	   		var identitylink = new identitylink("",checkednodes[i].id, "","org",checkednodes[i].name);
// 			identitylinklist.push(identitylink);
// 	    }
	    //获取职位选中值
	    
	    var identitylinklist = new Array();
	    var selectInfoArray = $(".select2-info");
	    if(selectInfoArray.length!=0)
	    {
	    	for(var i=0;i<selectInfoArray.length;i++){
	    		var selectInfo = selectInfoArray[i];
	    		var identityLink = new IdentityLink("",selectInfo.attributes["orgId"].nodeValue, selectInfo.attributes["jobId"].nodeValue,"position",selectInfo.innerHTML);
					identitylinklist.push(identityLink);
	    	}
	    }
// 	    zTree = $.fn.zTree.getZTreeObj("jobTree");
// 	    if(zTree){
// 		    checkedNodes = zTree.getCheckedNodes(true);
// 		    for(var i = 0; i < checkedNodes.length; i++){
		    
		    	
// 			$.ajax({type: 'POST',
// 				url: '<%=basePath %>/definitions/assignment/getOrgName',
// 				data:{
// 					  orgId:checkedNodes[i].orgId
// 					  },
// 				success:function(result){
					
// 			   		var identityLink = new IdentityLink("",checkedNodes[i].orgId, checkedNodes[i].id,"position",result.orgName+" "+checkedNodes[i].name);
// 					identityLinkList.push(identityLink);
// 				},
// 				dataType: "json",
// 				async:false
// 			});	
// 		    }
// 	    }
	    //获取人员选中值
// 	    zTree = $.fn.zTree.getZTreeObj("personTree");
//    	    if(zTree){
// 		    checkedNodes = zTree.getCheckedNodes(true);
// 		    for(var i = 0; i < checkedNodes.length; i++){
// 		   		var identityLink = new IdentityLink( checkedNodes[i].id,"", "","person",checkedNodes[i].name);
// 				identityLinkList.push(identityLink);
// 		    }
// 	    }
	    //调回到经办人设置界面，将选中值写入小方块
	    top.document.getElementById("_DialogFrame_identitylink").contentWindow.transactionTo(identitylinklist);
	    
	}
	
	function IdentityLink(personId, orgId, jobId,type,name) {
	    this.personId = personId;
	    this.orgId = orgId;
	    this.jobId = jobId;
	    this.type = type;
	    this.name = name;
	}
</script>
</head>
<body>
<table>
	<tr>
		<td colspan="4" >
			<div id="layout1">
				 <div position="left"  id="leftDiv" panelTitle="机构">
				 	<div id="layout2" class="layout_content">
<!-- 				 		<div position="center"  > -->
<!-- 				 			<div> -->
    							<ul id="orgTree" class="ztree"></ul>
<!-- 							</div> -->
<!-- 				 		</div> -->
					</div>
				 </div>
				 <div position="center" panelTitle="职务">
				 	<ul id="jobTree" class="ztree"></ul>
				</div>
				 <div position="right" style="overflow:auto;height:355px;" id="rightPanel" panelTitle="职位">
<!-- 				 	<ul id="personTree" class="ztree"></ul> -->
				</div>
			</div>
		</td>
	</tr>
</table>
</body>
</html>