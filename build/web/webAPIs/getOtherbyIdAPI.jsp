<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.Other.*" %> 
<%@page language="java" import="view.*" %> 
<%@page language="java" import="com.google.gson.*" %>

<%
    
    FlavorList wurl = new FlavorList();
    wurl.otherS = new StringData();
    //wurl.role = new model.role.StringDataList();
    
    String searchId = request.getParameter("id");
    if (searchId == null) {
        wurl.otherS.errorMsg = "Cannot search for flavor - 'id' most be supplied as URL parameter";
    } else {

        DbConn dbc = new DbConn();
        wurl.otherS.errorMsg = dbc.getErr(); // returns "" if connection is good, else db error msg.

        if (wurl.otherS.errorMsg.length() == 0) { // if got good DB connection,

            System.out.println("*** Ready to call getUserById");
            wurl.otherS = Search.getOtherById(dbc, searchId);
            
            //wurl.role= view.RoleView.allRolesAPI(dbc); 
        }

        dbc.close(); // EVERY code path that opens a db connection, must also close it - no DB Conn leaks.
    }
    // This object (from the GSON library) can to convert between JSON <-> POJO (plain old java object) 
    Gson gson = new Gson();
    out.print(gson.toJson(wurl).trim());
%>