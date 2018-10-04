<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="com.google.gson.*" %>
<%@page language="java" import="model.webUser.*" %>
<%@page language="java" import="view.WebUserView"%>

<%
  session.invalidate();
  
    StringDataList list = new StringDataList();
    list.dbError = "you have logged off";
                
    Gson gson = new Gson();
    out.print(gson.toJson(list).trim());

%>