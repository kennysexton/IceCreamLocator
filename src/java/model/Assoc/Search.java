package model.Assoc;

import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Search {

    public static StringData getAssocById(DbConn dbc, String id) {

        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringData sd = new StringData();
        try {
            String sql = "SELECT supply_id, selling_price, special_additions, "
                    + "f.flavor_id, flavor_name, w.web_user_id, store_name "
                    + "FROM supply s "
                    + "JOIN flavor f ON f.flavor_id=s.flavor_id "
                    + "JOIN web_user w ON w.web_user_id = s.web_user_id "
                    + "WHERE supply_id = ?";

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
            sd.errorMsg = "Exception thrown in Supply.getUserById(): " + e.getMessage();
        }
        return sd;
    }
} // class
