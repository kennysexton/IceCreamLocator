package model.webUser;

import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Search {

    public static StringData logonFind(String email, String pw, DbConn dbc) {
        StringData foundData = new StringData();
        if ((email == null) || (pw == null)) {
            foundData.errorMsg = "Search.logonFind: email and pw must be both non-null.";
            return foundData;
        }
        try {

            // prepare (compiles) the SQL statement
            String sql = "SELECT web_user_id, user_email, user_password, membership_fee, birthday, "
                    + "web_user.user_role_id, user_role_type "
                    + "FROM web_user, user_role "
                    + "WHERE web_user.user_role_id = user_role.user_role_id "
                    + "AND user_email = ? and user_password = ? "
                    + "ORDER BY web_user_id ";

            // This line compiles the SQL statement (checking for syntax errors against your DB).
            PreparedStatement pStatement = dbc.getConn().prepareStatement(sql);

            // Encode string values into the prepared statement (wrapper class).
            pStatement.setString(1, email);
            pStatement.setString(2, pw);

            // This line runs the SQL statement and gets the result set.
            ResultSet results = pStatement.executeQuery();
            if (results.next()) {
                // Record found in database, credentials are good.
                return new StringData(results);
            } else {
                // Returning null means that the username / pw were not found in the database
                return null;
            }
        } catch (Exception e) {
            foundData.errorMsg = "Exception in Search.logonFind: " + e.getMessage();
            System.out.println("******" + foundData.errorMsg);
            return foundData;
        }
    } // logonFind

    public static StringData getUserById(DbConn dbc, String id) {

        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringData sd = new StringData();
        try {
            String sql = "SELECT web_user_id, user_email, user_password, membership_fee, birthday, store_name, "
                    + "web_user.user_role_id, user_role_type "
                    + "FROM web_user, user_role WHERE web_user.user_role_id = user_role.user_role_id "
                    + "AND web_user_id = ?";

            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);

            // Encode the id (that the user typed in) into the select statement, into the first 
            // (and only) ? 
            stmt.setString(1, id);

            ResultSet results = stmt.executeQuery();
            if (results.next()) { // id is unique, one or zero records expected in result set
                sd = new StringData(results);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            sd.errorMsg = "Exception thrown in WebUserView.getUserById(): " + e.getMessage();
        }
        return sd;
    }
} // class
