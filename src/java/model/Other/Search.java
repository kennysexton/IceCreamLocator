package model.Other;

import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Search {


    public static StringData getOtherById(DbConn dbc, String id) {

        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringData sd = new StringData();
        try {
            String sql = "SELECT flavor_id, flavor_name, date_added, flavor_color "
                    + "FROM flavor, user_role WHERE flavor_id = ?";
                    

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
            sd.errorMsg = "Exception thrown in Other.getUserById(): " + e.getMessage();
        }
        return sd;
    }
} // class
