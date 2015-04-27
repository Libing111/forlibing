<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
Map<String, Object> rtn = new HashMap<String, Object>();
String error = (String)request.getAttribute("SYS_ERROR");
if(error != null){
	rtn.put("SYS_ERROR", error);
} else{
	Map<String, Object> resp = (Map<String, Object>)request.getAttribute("SYS_RESP");
	if(resp != null){
		rtn = resp;
	}
}
%>

