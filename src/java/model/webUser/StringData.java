package model.webUser;

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

    public String web_user_id = "";
    public String user_email = "";
    public String user_password = "";
    public String user_password2 = "";
    public String birthday = "";
    public String membership_fee = "";
    public String user_role_id = "";   // Foreign Key
    public String userRoleType = ""; // getting it from joined user_role table.
    public String store_name = "";
    
    public String errorMsg = "";
    

    // default constructor leaves all data members with empty string (Nothing null).
    public StringData() {
    }

    // overloaded constructor sets all data members by extracting from resultSet.
    public StringData(ResultSet results) {
        try {
            this.web_user_id = FormatUtils.formatInteger(results.getObject("web_user_id"));
            this.user_email = FormatUtils.formatString(results.getObject("user_email"));
            this.store_name = FormatUtils.formatString(results.getObject("store_name"));
            this.user_password = FormatUtils.formatString(results.getObject("user_password"));
            this.birthday = FormatUtils.formatDate(results.getObject("birthday"));
            this.membership_fee = FormatUtils.formatDollar(results.getObject("membership_fee"));
            this.user_role_id = FormatUtils.formatInteger(results.getObject("web_user.user_role_id"));
            this.userRoleType = FormatUtils.formatString(results.getObject("user_role_type"));
            
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }

    public int getCharacterCount() {
        String s = this.web_user_id + this.user_email + this.user_password + this.birthday
                + this.membership_fee + this.user_role_id + this.userRoleType + this.store_name;
        return s.length();
    }

    public String toString() {
        return "Web User Id:" + this.web_user_id
                + ", User Email: " + this.user_email
                + ", User Password: " + this.user_password
                + ", Birthday: " + this.birthday
                + ", Membership Fee: " + this.membership_fee
                + ", User Role Id: " + this.user_role_id
                + ", User Role Type: " + this.userRoleType
                + ", Store Name: " + this.store_name;
    }
}
