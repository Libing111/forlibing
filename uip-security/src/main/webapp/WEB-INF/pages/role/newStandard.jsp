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
<script type="text/javascript" src="<%=contextPath%>file/script/proper.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/script/organizationchoose.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/script/personchoose.js"></script>
<script type="text/javascript" src="<%=contextPath%>file/script/selfchoose.js"></script>
<script type="text/javascript">
function customHeightSet(contentHeight){
    $("#scrollContent").height(contentHeight);
}
</script>

</head>
<body>
<div id="scrollContent" class="properscrollcontent" >
	<div align="center" id="myBox">
	<form id="myform"  method="post">
	<input type="hidden" id="categoryName" name="categoryName" value="${roleEntity.categoryName}"/>
	<input type="hidden" id="categoryCode" name="categoryCode" value="${roleEntity.categoryCode}"/>
	<input type="hidden" id="id" name="id" value="${roleEntity.id}"/>

	<table class="tableStyle" id="tbaletable" style="width: 100%">	
		<tr>
			<td class="properForm2Left">名称:</td>
			<td class="properForm2Right1"><input id="name" class="validate[required,length[0,40]]" style="width:400px" name="name" type="text" value="${roleEntity.name}"/></td>
		</tr>
		<tr>
			<td class="properForm2Left">编号:</td>
			<td class="properForm2Right1"><input id="code" style="width:400px" class="validate[required,custom[noSpecialCaracters],length[0,20]]"   name="code" type="text" value="${roleEntity.code}"/></td>
		</tr>
		<tr>
			<td class="properForm2Left">规则：
					<input type="hidden" name="principalIds" id="principalIds" value="${ruleId}"/>
					<input type="hidden" name="principalValues" id="principalValues" value="${ruleValue}"/>
			</td>
			<td >
				<select prompt="请选择规则" id="ruleId"  selWidth="390" onchange="selectRule()" name="ruleId" data=""  selectedValue="${ruleId}"></select>
			</td>
		</tr>
		<tr id="selectUser" >
			<td class="properForm2Left">选择人员：</td>
			<td>
				
				<div style="float: left"><textarea  id="userNames"   name="userNames" style="width:400px"  readonly="readonly"></textarea></div>
				<div style="position:absolute; top:150px;left:500px"><input type="button"  onclick="clickUser()" value="选择人员"/></div>
			</td>
		</tr>
		<tr id="selectDepartment">
			<td class="properForm2Left">选择部门：</td>
			<td >
				<div style="float: left"><textarea id="deparmentNames" readonly="readonly" style="width:400px" name="deparmentNames"></textarea></div>
				<div style="position:absolute; top:150px;left:500px"><input type="button"  onclick="clickDepartment()" value="选择部门"/></div>
			</td>
		</tr>
		<tr id="selectPost">
			<td class="properForm2Left" height="100px">选择职务：</td>
			<td >
				<div style="float: left"><textarea id="postNames" readonly="readonly" style="width:400px" name="postNames" ></textarea></div>
				<div style="position:absolute; top:150px;left:500px"><input type="button" onclick="clickPost()" value="选择职务"/></div>
			</td>
		</tr>

		<tr>
			<td class="properForm2Left">备注:</td>
			<td class="properForm2Right1"><input id="decription" style="width:400px" name="decription" type="text" value="${roleEntity.decription}"/></td>
		</tr>
	</table>
	</form>
	</div>
</div>
<!-- 异步提交start -->
<script type="text/javascript">
	var principalIds =  $("#principalIds").val();
	var principalValues =  $("#principalValues").val();
	
	if(principalIds != null && principalIds == "role.filter.rule.user"){
		$("#userNames").val(principalValues);
		$("#selectUser").show();
	}
	var ruleList = <%=JsonUtil.array2JSON(request.getAttribute("roleFilterRuleExtensionList"))%>;
	var ruleData = {"list":[]};
	var rule;
	for(var i = 0; i < ruleList.length; i++){
		rule = {'value':ruleList[i].id, 'key':ruleList[i].name};
		ruleData.list.push(rule);
	}
	var a = JSON.stringify(ruleData);
	$('#ruleId').attr("data",a);
	
	function selectRule(){
		var ruleId = $('#ruleId').attr("relValue");
		if(ruleId == "role.filter.rule.user"){
			$("#selectUser").show();
			$("#selectDepartment").hide();
			$("#selectPost").hide();
		}
		if(ruleId == "role.filter.rule.all.user"){
			$("#selectUser").hide();
			$("#selectDepartment").hide();
			$("#selectPost").hide();
		}
		if(ruleId == "role.filter.rule.department"){
			$("#selectDepartment").show();
			$("#selectUser").hide();
			$("#selectPost").hide();
		}
		if(ruleId == "role.filter.rule.all.department"){
			$("#selectUser").hide();
			$("#selectDepartment").hide();
			$("#selectPost").hide();
		}
		if(ruleId == "role.filter.rule.post"){
			$("#selectUser").hide();
			$("#selectDepartment").hide();
			$("#selectPost").show();
		}
        
	
	}	
	function clickUser(){
		var pId =  $("#principalIds").val();
		var selected = [];
		if(pId && pId != ""){
				selected = pId.split(',');
		}
		proper.personchoose({
					userId:'${userId}',
					basePath:'<%=contextPath%>',
					checkbox:true,
					selectIds:selected,
					//scope:0,
					selected:function(persons){
							var pText=[];
							var pId=[];
							var personIds = '';
							var pers="";
							var pername="";
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
								pername = p.name + "," + pername;
								
							}
							//alert(personIds);
							//alert(pername);
							pername=pername.substring(0,pername.length-1);
							$("#principalIds").val(personIds);
							$("#userNames").val(pername);
							$("#principalValues").val(pername);
							
						
					}
				});
	}
	function valids() {
			var valid = $('#myform').validationEngine({
				returnIsValid : true
			});
			return valid;
		}
		function getParams() {
			var params = $('#myform').serialize();
			return params;
		}
		function initComplete() {
			$("#selectUser").hide();
			$("#selectDepartment").hide();
			$("#selectPost").hide();
			//表单提交
			$('#myFormId').submit(
					function() {
						//判断表单的客户端验证是否通过
						var valid = $('#myFormId').validationEngine({
							returnIsValid : true
						});
						if (valid) {
							$(this).ajaxSubmit(
									{
										//表单提交成功后的回调
										success : function(responseText,
												statusText, xhr, $form) {
											top.Dialog.alert(
													responseText.message,
													function() {
														closeWin();
													})
										}
									});
						}
						//阻止表单默认提交事件
						return false;
					});
		}

		//重置
		function closeWin() {
			var update = false;
			var isupdate = '<s:property value="userinfor.userId"/>';
			if (isupdate != '') {
				update = true;
			} else {
				update = false;
			}
			//刷新数据
			top.frmright.refresh(update);
			//关闭窗口
			top.Dialog.close();
		}
		//机构
		function clickDepartment(){
				var pId =  $("#principalIds").val();
				var selected = [];
				if(pId && pId != ""){
						selected = pId.split(',');
				}
				proper.organizationchoose({
				                userId:'${userId}',
							    basePath:'<%=contextPath%>',
								checkbox:true,//默认false
								selectIds:selected,//已选中的ID
								selected:function(orgs){
									var orgText=[];
									var orgId=[];
									var orgstr="";
									if(orgs.length>0){
										for(var i=0;i<orgs.length;i++){
											var o = orgs[i];
											orgText.push(o.name);
											orgId.push(o.id);
										}
								   }
									$("#principalIds").val(orgId);
									$("#deparmentNames").val(orgText);
									$("#principalValues").val(orgText);
								},
								
							},'orgs');
			}
	function clickPost(){
			
				var pId =  $("#principalIds").val();
				var selected = [];
				if(pId && pId != ""){
						selected = pId.split(',');
				}
				proper.selfchoose({
						userId:'${userId}',
					    title:'职务选择器',
					    basePath:'<%=contextPath%>',
					    url:'<%=basePath%>role/range/job',
						checkbox:true,//默认false
						selectIds:selected,//已选中的ID
						selUrl:'<%=basePath%>role/range/job/selected',
						selected:function(jobs){
							var jobText=[];
							var jobId=[];
							if(jobs.length>0){
								for(var i=0;i<jobs.length;i++){
									var o = jobs[i];
									jobText.push(o.name);
									jobId.push(o.id);
									//job=job+","+o.id+":"+o.name;
								}
						   }
									$("#principalIds").val(jobId);
									$("#postNames").val(jobText);
									$("#principalValues").val(jobText);
						}
					});
		}
</script>
<!-- 异步提交end -->	
</body>
</html>