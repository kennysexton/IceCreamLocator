package model.Other;

import model.Other.*;
import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbMods {

    /*
    Returns a "StringData" object that is full of field level validation
    error messages (or it is full of all empty strings if inputData
    totally passed validation.  
     */
    private static StringData validate(StringData inputData) {

        StringData errorMsgs = new StringData();

    
        // Validation
        errorMsgs.flavor_name = ValidationUtils.stringValidationMsg(inputData.flavor_name, 45, true);
        errorMsgs.date_added = ValidationUtils.dateValidationMsg(inputData.date_added,  false);


        errorMsgs.flavor_color = ValidationUtils.stringValidationMsg(inputData.flavor_color, 45, false);

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
            String sql = "INSERT INTO flavor (flavor_name, date_added, flavor_color) "
                    + "values (?,?,?)";

            // PrepStatement is Sally's wrapper class for java.sql.PreparedStatement
            // Only difference is that Sally's class takes care of encoding null 
            // when necessary. And it also System.out.prints exception error messages.
            PrepStatement pStatement = new PrepStatement(dbc, sql);

            // Encode string values into the prepared statement (wrapper class).
            pStatement.setString(1, inputData.flavor_name); // string type is simple
            pStatement.setDate(2, ValidationUtils.dateConversion(inputData.date_added));
            pStatement.setString(3, inputData.flavor_color);
            
           
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
            } else if (errorMsgs.errorMsg.contains("Duplicate entry")) {
                errorMsgs.errorMsg = "That flavor name is already taken";
            }

        } // customerId is not null and not empty string.
        return errorMsgs;
    } // insert
    
        public static String delete(String flavor_id, DbConn dbc) {

        if (flavor_id == null) {
            return "Programmer error: cannot attempt to delete flavor record that matches null flavor_id";
        }

        // This method assumes that the calling Web API (JSP page) has already confirmed 
        // that the database connection is OK. BUT if not, some reasonable exception should 
        // be thrown by the DB and passed back anyway... 
        String result = ""; // empty string result means the delete worked fine.
        try {

            String sql = "DELETE FROM flavor WHERE flavor_id = ?";

            // This line compiles the SQL statement (checking for syntax errors against your DB).
            PreparedStatement pStatement = dbc.getConn().prepareStatement(sql);

            // Encode user data into the prepared statement.
            pStatement.setString(1, flavor_id);

            int numRowsDeleted = pStatement.executeUpdate();

            if (numRowsDeleted == 0) {
                result = "Programmer Error: did not delete the record with flavor_id " + flavor_id;
            } else if (numRowsDeleted > 1) {
                result = "Programmer Error: > 1 record deleted. Did you forget the WHERE clause?";
            }

        } catch (Exception e) {
            result = "Exception thrown in model.Other.DbMods.delete(): " + e.getMessage();
            if (result.contains("foreign key")) {
                result = "Cannot delete flavor " + flavor_id + " because that user already posted reviews.";
            }
        }

        return result;
    } // delete
    
} // class