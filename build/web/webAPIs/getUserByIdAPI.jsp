<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.webUser.*" %> 
<%@page language="java" import="view.*" %> 
<%@page language="java" import="com.google.gson.*" %>

<%
    
    WebUserWithRoleList wurl = new WebUserWithRoleList();
    wurl.webUser = new StringData();
    wurl.role = new model.role.StringDataList();
    
    String searchId = request.getParameter("id");
    if (searchId == null) {
        wurl.webUser.errorMsg = "Cannot search for web user - 'id' most be supplied as URL parameter";
    } else {

        DbConn dbc = new DbConn();
        wurl.webUser.errorMsg = dbc.getErr(); // returns "" if connection is good, else db error msg.

        if (wurl.webUser.errorMsg.length() == 0) { // if got good DB connection,

            System.out.println("*** Ready to call getUserById");
            wurl.webUser = Search.getUserById(dbc, searchId);
            
            wurl.role= view.RoleView.allRolesAPI(dbc); 
        }

        dbc.close(); // EVERY code path that opens a db connection, must also close it - no DB Conn leaks.
    }
    // This object (from the GSON library) can to convert between JSON <-> POJO (plain old java object) 
    Gson gson = new Gson();
    out.print(gson.toJson(wurl).trim());
%>