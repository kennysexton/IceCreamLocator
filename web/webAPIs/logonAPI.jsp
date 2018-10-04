<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.webUser.*" %>  
<%@page language="java" import="com.google.gson.*" %>
<%@page language="java" import="view.WebUserView"%>

<%    
    StringDataList list = new StringDataList();
    String webUserObj = "";

    String strEmail = request.getParameter("email");
    String strPass = request.getParameter("password");
    
    if (strEmail == null) {
        list.dbError = "email not provided - 'email' must be supplied";
    } else if (strPass == null) {
         list.dbError = "password not provided - 'password' must be supplied";
    } else {

        DbConn dbc = new DbConn();
        list.dbError = dbc.getErr(); // returns "" if connection is good, else db error msg.

        if (list.dbError.length() == 0) { // if got good DB connection,

            System.out.println("*** Ready to call allUsersAPI");
            list = WebUserView.getByLogonInf(dbc, strEmail, strPass);
            session.setAttribute ("user", list); 
            
        }

        dbc.close(); // EVERY code path that opens a db connection, must also close it - no DB Conn leaks.
    }
    
    Gson gson = new Gson();
    out.print(gson.toJson(list).trim());
%>
