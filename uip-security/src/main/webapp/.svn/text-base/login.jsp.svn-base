<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
	<title>协同办公应用平台</title>
	<script type="text/javascript">
		var myclear="<%=basePath %>file/img/blank.gif";
	</script>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<script type="text/javascript" src="<%=basePath %>file/script/jquery-1.4.4.min.js"></script>

	<link rel="stylesheet" href="<%=basePath %>file/css/login/default.css" type="text/css" />

	<script src="<%=basePath %>file/script/iepngfix_tilebg.js" type="text/javascript"></script>

	<style type="text/css">
		div,img {behavior: url(<%=basePath %>file/css/iepngfix.htc);}
	</style>

</head>

<link rel="Shortcut Icon" href="<%=basePath %>file/img/login/favicon.ico" type="image/x-icon"/>

<body scroll="no" style="overflow:hidden; border:0;">
	<img id="img_bg" src="<%=basePath %>file/img/login/background.jpg" width="100%" height="100%" style="margin:0;  padding:0; border:0"/>
	<form action="j_spring_security_check" method="post"name="form1" id="form1">
	<div id="login_main">
    <div id="login_center" style="display: none">
    	<div id="login_content">
          <input type="text" name="j_username" id="loginname" />
          <input type="password" name="j_password" id="password"/>
          <img id="login_button" class="login_button_1" src="<%=basePath %>file/img/login/button_2.png" onClick="closeMe()";/>
          <div id="err">
            <c:choose >
		            <c:when test="${param.error == true}">用户名不存在或者密码错误</c:when>
		        </c:choose>
          </div>
          <div id="copy">版权所有&copy;沈阳工大普日软件技术有限公司&nbsp;&nbsp;&nbsp;&nbsp;建议使用IE8以上版本</div>
        </div>
    </div>
	</div>
	</form>
	<script language="javascript">
		if (self != top) top.location= self.location;
		$(document).ready(function(){						   
			initLogin();
			$("#login_button").mouseover(function(){  
        $("#login_button").attr("src","<%=basePath %>file/img/login/button_1.png");
  		}).mouseout(function(){  
        	$("#login_button").attr("src","<%=basePath %>file/img/login/button_2.png");
  		});
	
			document.form1.loginname.focus(); 
			document.onkeydown = function(e){ 
	    	var ev = document.all ? window.event : e;
	    	if(ev.keyCode==13) {
	    		document.form1.submit();
	    		return false;
	    	}
			}
			
		});
		$(window).resize(function() {
			initLogin();
		});
		function initLogin(){
			var _h = $(document).height();
			var _w = $(document).width();
	
			$("#login_main").height(_h);
			$("#login_main").width(_w);
	
			var _t = (_h - 256)/2;
			var _l = (_w - 626)/2;

			$("#login_center").offset({top:_t,left:_l});
			$("#login_center").show();
		}

		function closeMe(){
			document.form1.submit();
			return false;
		}
	</script>
</body>

</html>
