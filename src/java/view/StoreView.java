package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.webUser.*;

// classes in my project
import dbUtils.*;

public class StoreView {

    public static StringDataList storeAPI(DbConn dbc) {

        StringDataList sd2 = new StringDataList();
        try {
//            String sql = "SELECT web_user_id, store_name, user_email "+
//                    "FROM web_user "+
//                    "ORDER BY web_user_id "; 
            String sql = "SELECT web_user_id, store_name, user_email, birthday "+
                    "FROM web_user WHERE store_name IS NOT NULL "+
                    "ORDER BY web_user_id "; 
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sd2.add(results);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in StoreView.storeAPI(): " + e.getMessage();
            sd2.add(sd);
        }
        return sd2;
    }
}
