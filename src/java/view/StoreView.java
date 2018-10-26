package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.webUser.*;

// classes in my project
import dbUtils.*;

public class StoreView {

    public static StringDataList storeAPI(DbConn dbc) {

        StringDataList sdl = new StringDataList();
        try {
//            String sql = "SELECT web_user_id, store_name, user_email "+
//                    "FROM web_user "+
//                    "ORDER BY web_user_id "; 
            String sql = "SELECT web_user_id, store_name, user_email "+
                    "FROM web_user WHERE store_name IS NOT NULL "+
                    "ORDER BY web_user_id "; 
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.add(results);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in RoleView.allRolesAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }
}
