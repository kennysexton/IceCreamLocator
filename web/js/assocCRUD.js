var assocCRUD = {};

(function () {  // This is an IIFE, an Immediately Invoked Function Expression
    //alert("I am an IIFE!");
    
    assocCRUD.startInsert = function () {

        ajaxCall('htmlPartials/insertAssoc.html', setInsertUI, 'content');

        function setInsertUI(httpRequest) {

            // Place the inserttUser html snippet into the content area.
            console.log("Ajax call was successful.");
            document.getElementById("content").innerHTML = httpRequest.responseText;

            ajaxCall("webAPIs/getFlavorAPI.jsp", setFlavorList, "flavor_idError");
            ajaxCall("webAPIs/getStoreAPI.jsp", setStoreList, "storeError");

            function setFlavorList(httpRequest) {

                console.log("getFlavorAPI was called, see next line for object holding list of flavors");
                var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
                console.log(jsonObj);

                if (jsonObj.dbError.length > 0) {
                    document.getElementById("falvor_idError").innerHTML = jsonObj.dbError;
                    return;
                }

                makePickList(jsonObj.flavorList, "flavor_id", "flavor_name", "flavorPickList");
            }
            
            function setStoreList(httpRequest) {

                console.log("getStoreAPI was called, see next line for object holding list of stores");
                var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
                console.log(jsonObj);

                if (jsonObj.dbError.length > 0) {
                    document.getElementById("storeError").innerHTML = jsonObj.dbError;
                    return;
                }

                makePickList(jsonObj.webUserList, "web_user_id", "store_name", "storePickList");
            }
        }
    };


    assocCRUD.insertSave = function () {

        console.log ("assocCRUD.insertSave was called");

        var fList = document.getElementById("flavorPickList");
        var sList = document.getElementById("storePickList");

        // create a user object from the values that the user has typed into the page.
        var assocInputObj = {
            "supply_id": "",
            "selling_price": document.getElementById("selling_price").value,
            "special_additions": document.getElementById("special_additions").value,
             
            "flavor_id": fList.options[fList.selectedIndex].value,
            //"web_user_id": 1,

            "web_user_id": sList.options[sList.selectedIndex].value, // broken at the momnet

            "flavor_name": "",
            "store_name": "",
            "errorMsg": ""
        };
        console.log(assocInputObj);
        console.log(assocInputObj.web_user_id);
        var myData = escape(JSON.stringify(assocInputObj));
        var url = "webAPIs/insertAssocAPI.jsp?jsonData=" + myData;
        ajaxCall(url, assocProcessInsert, "recordError");

        function assocProcessInsert(httpRequest) {
            console.log("assoc processInsert was called here is httpRequest.");
            console.log(httpRequest);

            // the server prints out a JSON string of an object that holds field level error 
            // messages. The error message object (conveniently) has its fiels named exactly 
            // the same as the input data was named. 
            var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
            console.log("here is JSON object (holds error messages.");
            console.log(jsonObj);

            document.getElementById("selling_priceError").innerHTML = jsonObj.selling_price;
            document.getElementById("special_additionsError").innerHTML = jsonObj.special_additions;
            document.getElementById("flavor_idError").innerHTML = jsonObj.flavor_id;
            document.getElementById("storeError").innerHTML = jsonObj.store_name;

            if (jsonObj.errorMsg.length === 0) { // success
                jsonObj.errorMsg = "Record successfully inserted !!!";
            }
            document.getElementById("recordError").innerHTML = jsonObj.errorMsg;
        }
    };
    
    assocCRUD.delete = function (supply_id, icon) {
        // clear out any old error msg (non-breaking space)
        document.getElementById("listMsg").innerHTML = "&nbsp;";

        if (confirm("Do you really want to delete supply " + supply_id + "? ")) {

            // Calling the DELETE API. 
            var url = "webAPIs/deleteAssocAPI.jsp?deleteId=" + supply_id;
            ajaxCall(url, success, "listMsg");

            function success(http) { // API was successfully called (doesnt mean delete worked)
                var obj = JSON.parse(http.responseText);
                console.log("supply delete API called with success. next line has the object returned.");
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

                    document.getElementById("listMsg").innerHTML = "supply "+supply_id+ " deleted.";
                }
            }
        }
    };


    assocCRUD.list = function () {

        document.getElementById("content").innerHTML = "";
        var dataList = document.createElement("div");
        dataList.id = "dataList"; // set the id so it matches CSS styling rule.
        dataList.innerHTML = "<h2>Supply <img src='icons/insert.png' onclick='assocCRUD.startInsert();'/></h2>" +
                "<div id='listMsg'>&nbsp;</div>";
        document.getElementById("content").appendChild(dataList);

        ajaxCall('webAPIs/listAssocAPI.jsp', setListUI, 'dataList');

        function setListUI(httpRequest) {

            console.log("starting assocCRUD.list (setListUI) with this httpRequest object (next line)");
            console.log(httpRequest);

            var obj = JSON.parse(httpRequest.responseText);

            if (obj === null) {
                dataList.innerHTML = "listSupplyResponse Error: JSON string evaluated to null.";
                return;
            }
            
            for (var i = 0; i < obj.supplyList.length; i++) {

                // remove a property from each object in webUserList
                var id = obj.supplyList[i].supply_id;
                obj.supplyList[i].delete = "<img src='icons/delete.png'  onclick='assocCRUD.delete(" + id + ",this)'  />";
                
            }
 
            buildTable(obj.supplyList, obj.dbError, dataList);
        }
    };

}());  // the end of the IIFE