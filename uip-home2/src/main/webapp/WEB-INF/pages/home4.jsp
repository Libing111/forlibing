<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ page import="com.proper.uip.common.utils.JsonUtil"  %>
<jsp:useBean id="jsonUtil" class="com.proper.uip.common.utils.JsonUtil" scope="page" />
<%
String path = request.getContextPath();
String contexPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<div id="home4Wrap" class="plat-center-panel">
	<div id="home4Pages">
		<div class="home4OnePage">
			<table
				style="width: 100%; height: 100%; border-bottom: 1px #D2D2D0 solid; border-collapse: collapse;">
				<tr valign="top" style="height: 80px;">
					<td>
						<table class="homelayouttable4" >
							<tr style="background-color: white;">
								<td class="homelayouttable_td td1 hh" width="230px">
									<table>
										<tr>
											<td><div id="home_photo_wrap">
													<!--如果有照片 <img id="home_photo"   src="" border="0"/> -->
												</div></td>
											<td><ul>
							<li class="hhmsg1">工号：<c:if test="${empty person}"> ${user.account}</c:if><c:if test="${not empty person}"> ${person.jobNumber}</c:if><li>
							<li class="hhmsg1">姓名：<c:if test="${empty person}"> ${user.name}</c:if><c:if test="${not empty person}"> ${person.name}</c:if><li>
							<li class="hhmsg1">职位：<c:if test="${not empty person}"> ${UserPositionInfo.jobName}</c:if><li>
						</ul></td>
										</tr>
									</table>
								</td>
								<td style="width: 30px;" align="left" valign="middle"><div class="hhmsg1_splitter"></div></td>
								<td class="homelayouttable_td td2 hh " valign="top">
								<c:forEach var="menuNode" items="${menuNodeList}" varStatus="status">
									<a href="javascript:void(0)" onclick="getMenuNode('${menuNode.id}')" class="shortcut">${menuNode.title}</a>
								</c:forEach>
								
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table id="home4_bottom1"
							style="width: 100%; height: 100%; border-bottom: 1px #D2D2D0 solid; border-collapse: collapse;background-color: white;">
							<tr>
								<td style="width: 34%; border-left: 1px #D2D2D0 solid;" valign="top">
									<div class="home_news4">
										<div class="hd">
											<table>
												<tr>
													<td align="left"><h3>公告通知</h3></td>
													<td align="right"><a  href="javascript:void(0)" onclick="getList('52200100')" class="mymore">更多</a></td>
												</tr>
											</table>
										</div>
										<div class="bd">
											<ul class="ulist">
												 <c:forEach var="notifi" items="${notifiList}" varStatus="status">
													 <li onclick="getNotifi('${notifi.id}')"><span class="mytitle">${notifi.msgText}</span><span class="myright">${notifi.time}</span><span class="myright_new"></span> 
													<!--<li><span class="mytitle">${notifi.msgText}</span><span class="myright">${notifi.time}</span><span class="myright_new"></span>-->
												 </c:forEach>
												
											</ul>
										</div>
										<div class="divchat" id="chartPie1" style=""></div>
									</div>

								</td>
								<td 
									style="width: 32%; background-color: white; border-left: 1px #D2D2D0 solid;overflow: hidden;">
									<div class="div_col2" style="height: 0px;overflow: hidden;border-bottom: 1px #D2D2D0 solid;">
										<div class="home_news4">
											<div class="hd">
												<table>
													<tr>
														<td align="left"><h3>今日提醒</h3></td>
														<td align="right"> <a
															href="javascript:alert('你点击了更多按钮。');" class="mymore"></a></td>
													</tr>
												</table>
											</div>
											<div class="bd">
												<ul class="ulist">
												 <c:forEach var="todayList" items="${todayList}" varStatus="status">
													<li class="nolink"><span class="mytitle2 ">${todayList.msgText}</span></li>
												 </c:forEach>
												</ul>
											</div>
										</div>
									</div>
									<div class="div_col2" style="height: 0px;overflow: hidden;border-bottom: 1px #D2D2D0 solid;">
										<div class="home_news4">
											<div class="hd">
												<table>
													<tr>
														<td align="left"><h3>最新公文</h3></td>
														<td align="right"><a  href="javascript:void(0)" onclick="getList('522A0300')" class="mymore">更多</a></td>
													</tr>
												</table>
											</div>
											<div class="bd">
												<ul class="ulist">
												 <c:forEach var="documentmanagement" items="${documentmanagementList}" varStatus="status">
													<li onclick="getDocumentmanagement('${documentmanagement.id}')"><span class="mytitle2">${documentmanagement.msgText}</span></li>
												 </c:forEach>

												</ul>
											</div>
										</div>
									</div>
									<div class="div_col2" style="height: 0px;overflow: hidden;">
										<div class="home_news4">
											<div class="hd">
												<table>
													<tr>
														<td align="left"><h3>内部邮件</h3></td>
														<td align="right"><a  href="javascript:void(0)" onclick="getList('10018984')" class="mymore">更多</a></td>
													</tr>
												</table>
											</div>
											<div class="bd">
												<ul class="ulist">
													 <c:forEach var="mail" items="${mailList}" varStatus="status">
														<li onclick="getMail('${mail.id}')"><span class="mytitle2">${mail.msgText}</span></li>
													 </c:forEach>
												</ul>
											</div>
										</div>
									</div>
								</td>
								<td
									style="width: 34%; border-left: 1px #D2D2D0 solid; border-right: 1px #D2D2D0 solid;" valign="top">
									<div class="home_news4">
										<div class="hd">
											<table>
												<tr>
													<td align="left"><h3>待办工作</h3></td>
													<td align="right"><a  href="javascript:void(0)" onclick="getList('10010204')" class="mymore">更多</a></td>
												</tr>
											</table>
										</div>
										<div class="bd">
											<ul class="ulist">
												 <c:forEach var="bpm" items="${bpmList}" varStatus="status">
													<li onclick="getBpm('${bpm.id}')"><span class="mytitle">${bpm.msgText}</span><span
													class="myright">${bpm.time}</span><span class="myright_new"></span></li>
												 </c:forEach>

											</ul>
										</div>
										<div class="divchat" id="chartTodo1" style=""></div>
									</div>

								</td>

							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<script language="javascript" src="<%=path%>/statics/js/home4.js"></script>

<script type="text/javascript">
	var menuNodeList = <%=JsonUtil.array2JSON(request.getAttribute("menuNodeList"))%>;
	var bpmList = <%=JsonUtil.array2JSON(request.getAttribute("bpmList"))%>;
	var documentmanagementList = <%=JsonUtil.array2JSON(request.getAttribute("documentmanagementList"))%>;
	var notifiList = <%=JsonUtil.array2JSON(request.getAttribute("notifiList"))%>;
	var mailList = <%=JsonUtil.array2JSON(request.getAttribute("mailList"))%>;
	
	function getMenuNode(menuNodeId){
		for(var i = 0; i < menuNodeList.length; i++){
			if(menuNodeList[i].id == menuNodeId){
				customAddTaskPanel(menuNodeList[i]);
			}
		}
	
	}
	function getBpm(bpmId){
		for(var i = 0; i < bpmList.length; i++){
			if(bpmList[i].id == bpmId){
				customAddTaskPanel(bpmList[i]);
			}
		}
	
	}
	function getDocumentmanagement(documentmanagementId){
		for(var i = 0; i < documentmanagementList.length; i++){
			if(documentmanagementList[i].id == documentmanagementId){
				customAddTaskPanel(documentmanagementList[i]);
			}
		}
	
	}
	function getNotifi(notifiId){
		for(var i = 0; i < notifiList.length; i++){
			if(notifiList[i].id == notifiId){
				customAddTaskPanel(notifiList[i]);
			}
		}
	
	}
	function getMail(mailId){
		for(var i = 0; i < mailList.length; i++){
			if(mailList[i].id == mailId){
				customAddTaskPanel(mailList[i]);
			}
		}
	
	}
	function getList(id){
		$.ajax({
			type: "POST",
			url: "home/getList",
			dataType:'json',
			data:{id:id},
			cache: false,
			async:false,
			success: function(data){
				customAddTaskPanel(data);
			}
		});
	
	}
	//加载图表
	$(document).ready(function() {
		
			
		//今日提醒饼图 
		var messageReport;
		$.ajax({
			type: "POST",
			url: "home/messageReportList",
			dataType:'json',
			cache: false,
			async:false,
			success: function(data){
				messageReport = data;
			}
		});
		
		var chartPie1 = echarts.init(document.getElementById('chartPie1'));
		chartPie1.setOption({
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			series : [ {
				name : '公告分类',
				type : 'pie',
				radius : '80%',
				center : [ '50%', '50%' ],
				data : messageReport
			} ]
		});
		
		var bpm;
		$.ajax({
			type: "POST",
			url: "home/messageBpmList",
			dataType:'json',
			cache: false,
			async:false,
			success: function(data){
				bpm = data;
			}
		});
		
		var chartTodo1 = echarts.init(document.getElementById('chartTodo1'));
		chartTodo1.setOption({

			tooltip : {
				trigger : 'axis'
			},
			grid : {
				y : 10,

			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : [ '1日内', '3日内', '7日内', '1月内' ]
			} ],
			yAxis : [ {
				type : 'value',
				axisLabel : {
					formatter : function(value) {
						return parseInt(value);
					}
				}
			} ],
			series : [ {
				name : '待办数量',
				type : 'line',
				data : bpm,
				markPoint : {
					data : [ {
						type : 'min',
						name : '最小值'
					} ]
				},
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			} ]
		});
	});
</script>