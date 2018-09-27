/*
 * To use this as a drop down menu, set up your HTML like this (see below) where you have a navigantion Link 
 * element with a menu heading inside (instead of a link). In this example, the menu heading is "Data". 
 * After the menu heading, have your submenu div contain whatever links are to be hidden/shown when the 
 * user clicks on the menu heading. The non-breaking spaces (&nbsp;) have been added so that the 
 * menu heading is wider than the widest link in the submenu. Otherwise, the menu heading will appear 
 * wider when the submenu is shown, narrower otherwise.
 * 
   <div class="navLink" onclick="toggleChild(this)">&nbsp;&nbsp;&nbsp;&nbsp;Data&nbsp;&nbsp;&nbsp;&nbsp;
        <div class="subMenu">
            <a onclick = "ajaxCall('webAPIs/listUsersAPI.jsp', setUserContent, setError)">Users</a><br/>
            <a onclick = "">Products</a><br/>
            <a onclick = "">Purchases</a>
        </div>
    </div>

* With a style something like this (see below). The subMenu must have display:none to start out hidden. 
* You may want to set the nd the background color should match the color of the parent (e.g. of navLink) - the rest is just preference).
     <style>
        .subMenu {
            display:none; 
            background-color:black;
            font-size:0.9em;
            line-height: 1.7em;
            padding-top:0.8ex;
        }
    </style>
 */


function toggleChild(ele) {
    // Note: childNodes[0] is the Menu Heading label, e.g., "Show"
    // childNodes[1] is the next element, the div that we want to hide/show.
    var child = ele.childNodes[1];

    // Tip: JS doesnt understand the initial CSS values (the values 
    // set by style sheet instead of by JS), so don't reverse 
    // the order of the if/else block. In other words, test first for what it's not 
    // initially (as set by the CSS).                       

    if (child.style.display === "block") {
        child.style.display = "none";
    } else {
        child.style.display = "block";
    }
}