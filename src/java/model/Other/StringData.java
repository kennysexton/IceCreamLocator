package model.Other;

import model.Other.*;
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

    public String flavor_id = "";
    public String flavor_name = "";
    public String date_added = "";
    public String flavor_color = "";
    public String errorMsg = "";

    // default constructor leaves all data members with empty string (Nothing null).
    public StringData() {
    }

    // overloaded constructor sets all data members by extracting from resultSet.
    public StringData(ResultSet results) {
        try {
            this.flavor_id = FormatUtils.formatInteger(results.getObject("flavor_id"));
            this.flavor_name = FormatUtils.formatString(results.getObject("flavor_name"));
            this.date_added = FormatUtils.formatDate(results.getObject("date_added"));
            this.flavor_color = FormatUtils.formatString(results.getObject("flavor_color"));
            
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.Other.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }

    public int getCharacterCount() {
        String s = this.flavor_id + this.flavor_name + this.date_added + this.flavor_color;
        //String s = this.flavor_id + this.flavor_name;
                
        return s.length();
    }

    public String toString() {
        return "flavor_id:" + this.flavor_id
                + ", flavor_name: " + this.flavor_name
                + ", date_added: " + this.date_added
                + ", flavor_color: " + this.flavor_color;

    }
}
