function makePickList (list, keyProp, valueProp, selectListId) {

    // get reference to the DOM select tag for the role)
    var selectList = document.getElementById(selectListId);

    for (var i in list) { // i iterates through all the elements in array list

        // new Option(): first parameter is displayed option, second is option value. 
        var myOption = new Option(list[i][valueProp], list[i][keyProp]); 
        
        // add option into the select list
        selectList.appendChild(myOption);
    }
}