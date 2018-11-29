<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.Assoc.*" %> 
<%@page language="java" import="view.*" %> 
<%@page language="java" import="com.google.gson.*" %>

<%
    
    SupplyList wurl = new SupplyList();
    wurl.supplyS = new StringData();
    wurl.flavor_dropdown = new model.Other.StringDataList();
    wurl.store_dropdown = new model.webUser.StringDataList();
    
    String searchId = request.getParameter("id");
    if (searchId == null) {
        wurl.supplyS.errorMsg = "Cannot search for supply - 'id' most be supplied as URL parameter";
    } else {

        DbConn dbc = new DbConn();
        wurl.supplyS.errorMsg = dbc.getErr(); // returns "" if connection is good, else db error msg.

        if (wurl.supplyS.errorMsg.length() == 0) { // if got good DB connection,

            System.out.println("*** Ready to call getUserById");
            wurl.supplyS = Search.getAssocById(dbc, searchId);
            
            wurl.flavor_dropdown = view.FlavorView.flavorAPI(dbc);
            wurl.store_dropdown = view.StoreView.storeAPI(dbc);
        }

        dbc.close(); // EVERY code path that opens a db connection, must also close it - no DB Conn leaks.
    }
    // This object (from the GSON library) can to convert between JSON <-> POJO (plain old java object) 
    Gson gson = new Gson();
    out.print(gson.toJson(wurl).trim());
%>