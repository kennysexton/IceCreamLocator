package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Other.*;

// classes in my project
import dbUtils.*;

public class FlavorView {

    public static StringDataList flavorAPI(DbConn dbc) {

        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT flavor_id, flavor_name "+
                    "FROM flavor ORDER BY flavor_id ";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.add(results);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in FlavorView.flavorAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }
}