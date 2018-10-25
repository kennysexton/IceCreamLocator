package model.webUser;

import dbUtils.FormatUtils;
import java.sql.ResultSet;


/* This is a variant of the StringData.java file.  This one is used for recovering just the user Id and the store name */
public class StringStoreData {

    public String webUserId = "";
    public String store_name = "";

    public String errorMsg = "";
    

    // default constructor leaves all data members with empty string (Nothing null).
    public StringStoreData() {
    }

    // overloaded constructor sets all data members by extracting from resultSet.
    public StringStoreData(ResultSet results) {
        try {
            this.webUserId = FormatUtils.formatInteger(results.getObject("web_user_id"));
            this.store_name = FormatUtils.formatString(results.getObject("store_name"));
            
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }

    public int getCharacterCount() {
        String s = this.webUserId + this.store_name;
        return s.length();
    }

    public String toString() {
        return "Web User Id:" + this.webUserId
                + ", Store Name: " + this.store_name;
    }
}
