var otherCRUD = {};

(function () {  // This is an IIFE, an Immediately Invoked Function Expression

    
    // Other
    otherCRUD.startInsert = function () {

        ajaxCall('htmlPartials/insertOther.html', setInsertUI, 'content');

        function setInsertUI(httpRequest) {

            // Place the inserttUser html snippet into the content area.
            console.log("Ajax call was successful.");
            document.getElementById("content").innerHTML = httpRequest.responseText;

        }
    };


    otherCRUD.insertSave = function () {

        console.log ("otherCRUD.insertSave was called");

        //var ddList = document.getElementById("rolePickList");

        // create an other object from the values that the user has typed into the page.
        var otherInputObj = {
            "flavor_id": "",
            "flavor_name": document.getElementById("flavor_name").value,
            "date_added": document.getElementById("date_added").value,
            "flavor_color": document.getElementById("flavor_color").value,
            
            "errorMsg": ""
        };
        console.log(otherInputObj);

        // build the url for the ajax call. Remember to escape the user input object or else 
        // you'll get a security error from the server. JSON.stringify converts the javaScript
        // object into JSON format (the reverse operation of what gson does on the server side).
        var myData = escape(JSON.stringify(otherInputObj));
        var url = "webAPIs/insertOtherAPI.jsp?jsonData=" + myData;
        ajaxCall(url, otherProcessInsert, "recordError");

        function otherProcessInsert(httpRequest) {
            console.log("other processInsert was called here is httpRequest.");
            console.log(httpRequest);

            // the server prints out a JSON string of an object that holds field level error 
            // messages. The error message object (conveniently) has its fiels named exactly 
            // the same as the input data was named. 
            var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
            console.log("here is JSON object (holds error messages.");
            console.log(jsonObj);
            console.log(jsonObj.flavor_name);

            document.getElementById("flavor_nameError").innerHTML = jsonObj.flavor_name;
            document.getElementById("date_addedError").innerHTML = jsonObj.date_added;
            document.getElementById("flavor_colorError").innerHTML = jsonObj.flavor_color;
            

            if (jsonObj.errorMsg.length === 0) { // success
                jsonObj.errorMsg = "Record successfully inserted !!!";
            }
            document.getElementById("recordError2").innerHTML = jsonObj.errorMsg;
        }
    };
    
    otherCRUD.delete = function (flavor_id, icon) {
        // clear out any old error msg (non-breaking space)
        document.getElementById("listMsg").innerHTML = "&nbsp;";

        if (confirm("Do you really want to delete flavor " + flavor_id + "? ")) {

            // Calling the DELETE API. 
            var url = "webAPIs/deleteOtherAPI.jsp?deleteId=" + flavor_id;
            ajaxCall(url, success, "listMsg");

            function success(http) { // API was successfully called (doesnt mean delete worked)
                var obj = JSON.parse(http.responseText);
                console.log("delete API called with success. next line has the object returned.");
                console.log(obj);
                if (obj.errorMsg.length > 0) {
                    document.getElementById("listMsg").innerHTML = obj.errorMsg;
                } else { // everything good, no error means record was deleted

                    // delete the <tr> (row) of the clicked icon from the HTML table
                    console.log("icon that was passed into JS function is printed on next line");
                    console.log(icon);

                    // icon's parent is cell whose parent is row 
                    var dataRow = icon.parentNode.parentNode;
                    var rowIndex = dataRow.rowIndex - 1; // adjust for oolumn header row?
                    var dataTable = dataRow.parentNode;
                    dataTable.deleteRow(rowIndex);

                    document.getElementById("listMsg").innerHTML = "flavor "+flavor_id+ " deleted.";
                }
            }
        }
    };


    otherCRUD.list = function () {

        document.getElementById("content").innerHTML = "";
        var dataList = document.createElement("div");
        dataList.id = "dataList"; // set the id so it matches CSS styling rule.
        dataList.innerHTML = "<h2>Flavors <img src='icons/insert.png' onclick='otherCRUD.startInsert();'/></h2>" +
                "<div id='listMsg'>&nbsp;</div>";
        document.getElementById("content").appendChild(dataList);

        ajaxCall('webAPIs/listOtherAPI.jsp', setListUI, 'dataList');

        function setListUI(httpRequest) {

            console.log("starting otherCRUD.list (setListUI) with this httpRequest object (next line)");
            console.log(httpRequest);

            var obj = JSON.parse(httpRequest.responseText);

            if (obj === null) {
                dataList.innerHTML = "listOthersResponse Error: JSON string evaluated to null.";
                return;
            }
            
            for (var i = 0; i < obj.flavorList.length; i++) {

                // remove a property from each object in webUserList
                var id = obj.flavorList[i].flavor_id;
                obj.flavorList[i].delete = "<img src='icons/delete.png'  onclick='otherCRUD.delete(" + id + ",this)'  />";
                
            }

            buildTable(obj.flavorList, obj.dbError, dataList);
        }
    };

}());


