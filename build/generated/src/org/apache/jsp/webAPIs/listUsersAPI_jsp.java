package org.apache.jsp.webAPIs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import dbUtils.*;
import model.webUser.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.google.gson.*;

public final class listUsersAPI_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("application/json; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write(" \n");
      out.write("\n");
      out.write(" \n");
      out.write(" \n");
      out.write(" \n");
      out.write("\n");
      out.write("\n");

    // default constructor creates nice empty StringDataList with all fields "" (empty string, nothing null).
    StringDataList strDataList = new StringDataList();

    System.out.println("*** Ready to get Db Connection.");
    DbConn dbc = new DbConn();
    strDataList.dbError = dbc.getErr(); // returns "" if connection is good, else db error msg.
    System.out.println("*** Db Error is this (empty string means all good): " + dbc.getErr());
    if (strDataList.dbError.length() == 0) { // if got good DB connection, 
        try {
            String sql = "SELECT web_user_id, user_email, user_password, membership_fee, birthday, "
                    + "web_user.user_role_id, user_role_type "
                    + "FROM web_user, user_role where web_user.user_role_id = user_role.user_role_id "
                    + "ORDER BY web_user_id ";

            System.out.println("*** Ready to prepare statement. Sql is: " + sql);
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);

            System.out.println("*** Ready to execute the sql.");
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                System.out.println("*** Ready to extract one row from the result set.");
                strDataList.add(results);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("*** Exception thrown, messages is: " + e.getMessage());
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown, messages is: " + e.getMessage();
            strDataList.add(sd);
        }
    }

    dbc.close(); // EVERY code path that opens a db connection, must also close it - no DB Conn leaks.

    // This object (from the GSON library) can to convert between JSON <-> POJO (plain old java object) 
    Gson gson = new Gson();
    out.print(gson.toJson(strDataList).trim());

    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
