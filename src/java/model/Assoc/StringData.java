package model.Assoc;

import model.Assoc.*;
import dbUtils.FormatUtils;
import java.sql.ResultSet;


/* The purpose of this class is just to "bundle together" all the 
 * character data that the user might type in when they want to 
 * add a new Customer or edit an existing customer.  This String
 * data is "pre-validated" data, meaning they might have typed 
 * in a character string where a number was expected.
 * 
 * There are no getter or setter methods since we are not trying to
 * protect this data in any way.  We want to let the JSP page have
 * free access to put data in or take it out. */
public class StringData {

    public String supply_id = "";
    public String selling_price = "";
    public String special_additions = "";
    public String flavor_name = "";
    public String store_name = "";
    public String errorMsg = "";

    // default constructor leaves all data members with empty string (Nothing null).
    public StringData() {
    }

    // overloaded constructor sets all data members by extracting from resultSet.
    public StringData(ResultSet results) {
        try {
            this.supply_id = FormatUtils.formatInteger(results.getObject("supply_id"));
            this.selling_price = FormatUtils.formatDollar(results.getObject("selling_price"));
            this.special_additions = FormatUtils.formatString(results.getObject("special_additions"));
            this.flavor_name = FormatUtils.formatString(results.getObject("flavor_name"));
            this.store_name = FormatUtils.formatString(results.getObject("store_name"));
            
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.Assoc.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }

    public int getCharacterCount() {
        String s = this.supply_id + this.selling_price + this.special_additions + this.flavor_name + this.store_name;
                
        return s.length();
    }

    public String toString() {
        return "supply_id:" + this.supply_id
                + ", selling_price: " + this.selling_price
                + ", special_additions: " + this.special_additions
                + ", flavor_name: " + this.flavor_name
                + ", store_name: " + this.store_name;

    }
}
