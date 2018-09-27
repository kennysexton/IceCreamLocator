// ********************** buildTable ***********************************
// list: an array of objects that are to be built into an HTML table.
// dbErrorMsg: a string that has a Db Error message (or empty string if all OK). 
// target: reference to DOM object where HTML table is to be placed (by buildTable). 

function buildTable(list, dbErrorMsg, target) {

    // The next several functions are all private to function buildTable.

    function alignCell(cell) { //right justify HTML cell if it contains a number

        var cellContent = cell.innerHTML;
        if (!isNaN(cellContent) || // if numeric 
                ((cellContent.length > 0) && (cellContent.charAt(0) === "$"))) { // or dollar amt
            cell.style.textAlign = "right";
            console.log("right alligning " + cellContent);
        }
    } // end function alignCell

// Make column heading pretty. (The column heading name is probably coming from a java property field name.) 
// Capitalize the first letter, then insert space before every subsequent capital letter. "userEmail" --> "User Email"
    function prettyColumnHeading(colHdg) {
        if (colHdg.length === 0) {
            return "";
        }
        newHdg = colHdg.charAt(0).toUpperCase();
        for (var i = 1; i < colHdg.length; i++) {
            if (colHdg.charAt(i) < "a") {
                newHdg += " ";
            }
            newHdg += colHdg.charAt(i);
        }
        return newHdg;
    } // end function prettyColumnHeading


// Here starts the logic of function buildTable.

    if (dbErrorMsg === null) {
        console.log("Error: dbErrorMsg is null");
        target.innerHTML = "Error: dbErrorMsg is null";
        return;
    }

    if (dbErrorMsg.length > 0) {
        console.log("Database Error is: " + dbErrorMsg);
        target.innerHTML = "Database Error is: " + dbErrorMsg;
        return;
    }

    if (list === null) {
        console.log("list is null");
        target.innerHTML = "list is null";
        return;
    }

    if (list.length === 0) {
        console.log("list has 0 elements");
        target.innerHTML = "list has 0 elements";
        return;
    }

    // Create a new HTML element (a HTML table) and append 
    // that into the page. 
    var newTable = document.createElement("table");
    target.appendChild(newTable);
    var newRow;
    var newCell;
    // Add one row (to HTML table) per element) in the webUserList array
    // (a property of the object created from the JSON string).
    for (var i in list) {
        newRow = newTable.insertRow(i);
        var col = 0;
        var data = list[i];
        for (var prop in data) {
            newCell = newRow.insertCell(col);
            col++;
            newCell.innerHTML = data[prop];
            alignCell(newCell); // I/O parameter
        }
    }

    // Add header row at top (with field names as column headings): 
    newHead = newTable.createTHead();
    newRow = newHead.insertRow(0);
    var data = list[0];
    col = 0;
    for (prop in data) {
        newCell = newRow.insertCell(col);
        newCell.innerHTML = prettyColumnHeading(prop);
        newCell.style.verticalAlign = "bottom";
        newCell.style.textAlign = "center";
        col++;
    }
} // end function buildTable