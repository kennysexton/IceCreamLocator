function makePickList (list, keyProp, valueProp, selectListId, desiredValue) {

    // get reference to the DOM select tag for the role)
    var selectList = document.getElementById(selectListId);

    for (var i in list) { // i iterates through all the elements in array list

        // new Option(): first parameter is displayed option, second is option value. 
        var myOption = new Option(list[i][valueProp], list[i][keyProp]); 
        
        // add option into the select list
        selectList.appendChild(myOption);
    }
    if (desiredValue) {
        console.log("setting picklist option to this value " + desiredValue);

        for (var i = 0; i < selectList.options.length; i++) {
            if (selectList.options[i].value === desiredValue) {
                selectList.selectedIndex = i;
            }
        }
    }
}