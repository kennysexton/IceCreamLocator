<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="com.google.gson.*" %>
<%@page language="java" import="model.webUser.*" %>
<%@page language="java" import="view.WebUserView"%>


<%
//    WebUser userObj = (WebUser) session.getAttribute("user"); 
    StringDataList userObj = (StringDataList) session.getAttribute("user");
    
    if (userObj == null){
        StringDataList list = new StringDataList();
        list.dbError = "not logged in";
                
        Gson gson = new Gson();
        out.print(gson.toJson(list).trim());
    } else {
        Gson gson = new Gson();
        out.print(gson.toJson(userObj).trim());
    }  
%>
