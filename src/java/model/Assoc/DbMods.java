package model.Assoc;

import model.Assoc.*;
import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbMods {

  
    private static StringData validate(StringData inputData) {

        StringData errorMsgs = new StringData();

        // Validation
        errorMsgs.selling_price = ValidationUtils.stringValidationMsg(inputData.selling_price, 45, true);
        errorMsgs.special_additions = ValidationUtils.stringValidationMsg(inputData.special_additions, 45, false);

       
        errorMsgs.flavor_id = ValidationUtils.integerValidationMsg(inputData.flavor_id, true);
        errorMsgs.web_user_id = ValidationUtils.integerValidationMsg(inputData.web_user_id, true);

        return errorMsgs;
    } // validate 

    public static StringData insert(StringData inputData, DbConn dbc) {

        StringData errorMsgs = new StringData();
        errorMsgs = validate(inputData);
        if (errorMsgs.getCharacterCount() > 0) {  // at least one field has an error, don't go any further.
            errorMsgs.errorMsg = "Please try again";
            return errorMsgs;

        } else { // all fields passed validation

            
            // Start preparing SQL statement
            String sql = "INSERT INTO supply (selling_price, special_additions, flavor_id, web_user_id) "
                    + "values (?,?,?,?)";

            
            PrepStatement pStatement = new PrepStatement(dbc, sql);

            // Encode string values into the prepared statement (wrapper class).
            pStatement.setString(1, inputData.selling_price); // string type is simple
            pStatement.setString(2, inputData.special_additions);
            pStatement.setInt(3, ValidationUtils.integerConversion(inputData.flavor_id));
            pStatement.setInt(4, ValidationUtils.integerConversion(inputData.web_user_id));

            // here the SQL statement is actually executed
            int numRows = pStatement.executeUpdate();

            // This will return empty string if all went well, else all error messages.
            errorMsgs.errorMsg = pStatement.getErrorMsg();
            if (errorMsgs.errorMsg.length() == 0) {
                if (numRows == 1) {
                    errorMsgs.errorMsg = ""; // This means SUCCESS. Let the user interface decide how to tell this to the user.
                } else {
                    // probably never get here unless you forgot your WHERE clause and did a bulk sql update.
                    errorMsgs.errorMsg = numRows + " records were inserted when exactly 1 was expected.";
                }
            } else if (errorMsgs.errorMsg.contains("foreign key")) {
                errorMsgs.errorMsg = "Invalid foreign key";
            } else if (errorMsgs.errorMsg.contains("Duplicate entry")) {
                errorMsgs.errorMsg = "duplicate entry";
            }

        } // customerId is not null and not empty string.
        return errorMsgs;
    } // insert
    
    public static String delete(String supply_id, DbConn dbc) {

        if (supply_id == null) {
            return "Programmer error: cannot attempt to delete supply record that matches null supply_id";
        }

        // This method assumes that the calling Web API (JSP page) has already confirmed 
        // that the database connection is OK. BUT if not, some reasonable exception should 
        // be thrown by the DB and passed back anyway... 
        String result = ""; // empty string result means the delete worked fine.
        try {

            String sql = "DELETE FROM supply WHERE supply_id = ?";

            // This line compiles the SQL statement (checking for syntax errors against your DB).
            PreparedStatement pStatement = dbc.getConn().prepareStatement(sql);

            // Encode user data into the prepared statement.
            pStatement.setString(1, supply_id);

            int numRowsDeleted = pStatement.executeUpdate();

            if (numRowsDeleted == 0) {
                result = "Programmer Error: did not delete the record with supply_id " + supply_id;
            } else if (numRowsDeleted > 1) {
                result = "Programmer Error: > 1 record deleted. Did you forget the WHERE clause?";
            }

        } catch (Exception e) {
            result = "Exception thrown in model.Assoc.DbMods.delete(): " + e.getMessage();
            if (result.contains("foreign key")) {
                result = "Cannot delete supply " + supply_id;
            }
        }

        return result;
    } // delete
    
} // class