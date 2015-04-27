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

<!--图表脚本start-->
<script language="JavaScript" src="<%=contextPath%>file/libs/thirdparty/fusioncharts/js/FusionCharts.js"></script>
<!--图表脚本end-->

<script type="text/javascript">
function initComplete(){
		var statisticsList = <%=JsonUtil.array2JSON(request.getAttribute("statisticsList"))%>;
		
		var chartPath = "<%=contextPath%>file/libs/thirdparty/fusioncharts/swf/";
        var myChart = new FusionCharts(chartPath + "Column2D.swf", "ChartId", "100%", "320", "0", "0");
	    myChart.setJSONData({"chart":{ "caption" : "分类统计","baseFontSize":"12","xAxisName":"分类", "yAxisName":"工作数量","decimals":"0", "formatNumberScale":"0", "chartRightMargin":"30", "useRoundEdges":"1", "rotateYAxisName":"0"},                                                
				             "data" :statisticsList});
   		myChart.render("chartdiv2");
   		
   		var myChart1 = new FusionCharts(chartPath + "Column2D.swf", "ChartId1", "100%", "320", "0", "0");
	    myChart1.setJSONData({"chart":{ "caption" : "时间统计","baseFontSize":"12", "xAxisName":"时间", "yAxisName":"工作数量","decimals":"0", "formatNumberScale":"0", "chartRightMargin":"30", "useRoundEdges":"1", "rotateYAxisName":"0"},                                                
				             "data" :                         
				             [                                 
				             { "label" : "当天", "value" : "${currentDateCount}"},                                
				             { "label" : "本周", "value" : "${weekDateCount}" },                                 
				             { "label" : "本月", "value" : "${monthDateCount}" },                                 
				             { "label" : "月前", "value" : "${monthBeforeDateCount}" }                         
				             ]});   
   		myChart1.render("chartdiv1");
};

</script>
</head>
<body>
<div class="box1">
	<table width="100%">
		<tr>
			<td width="50%">
				<fieldset> 
		     	 <legend style="text-align: center;">分类统计</legend>
					<table class="tableStyle" formMode="view" align="center">
						<tr>
							<c:forEach var="statistics" items="${statisticsList}" varStatus="i">
								<td style="text-align: center;">${statistics.label}</td>
							</c:forEach>
							<td style="text-align: center;">总计</td>
						</tr>
						<tr>
							<c:forEach var="statistics" items="${statisticsList}" varStatus="i">
								<td style="text-align: center;">${statistics.value}</td>
							</c:forEach>
							<td style="text-align: center;">${total}</td>
						</tr>
					</table>
				</fieldset>
			</td>
			<td width="50%">
				<fieldset> 
	      		<legend style="text-align: center;">时间统计</legend>
					<table class="tableStyle" formMode="view" align="center">
						<tr>
							<td style="text-align: center;">当天</td>
							<td style="text-align: center;">本周</td>
							<td style="text-align: center;">本月</td>
							<td style="text-align: center;">月前</td>
						</tr>
						<tr>
							<td style="text-align: center;">${currentDateCount}</td>
							<td style="text-align: center;">${weekDateCount}</td>
							<td style="text-align: center;">${monthDateCount}</td>
							<td style="text-align: center;">${monthBeforeDateCount}</td>
						</tr>
					</table>
				</fieldset>
			
			</td>
		</tr>
		<tr>
			<td>
		     	<div id="chartdiv2" > 
			        <div class="flash_install">
						<div class="msg_icon3"></div>
						<div class="flash_install_con" >
						您需要升级Flash播放器，<span class="forIE"><a href="<%=path%>/libs/flash/flash_IE.exe">点击这里</a>安装</span>
						<span class="forFF"><a href="<%=path%>/libs/flash/flash_FF.exe">点击这里</a>进行安装</span><br />安装后请关闭浏览器再重新打开
						</div>
					</div>
				</div>
			   <div class="height_50"></div>
			</td>
			<td>
		     	<div id="chartdiv1"> 
			        <div class="flash_install">
						<div class="msg_icon3"></div>
						<div class="flash_install_con" >
						您需要升级Flash播放器，<span class="forIE"><a href="<%=path%>/libs/flash/flash_IE.exe">点击这里</a>安装</span>
						<span class="forFF"><a href="<%=path%>/libs/flash/flash_FF.exe">点击这里</a>进行安装</span><br />安装后请关闭浏览器再重新打开
						</div>
					</div>
				</div>
			   <div class="height_50"></div>
			</td>
		</tr>
	</table>
</div>
</body>
</html>