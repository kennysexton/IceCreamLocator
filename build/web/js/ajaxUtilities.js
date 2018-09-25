// Make an ajax call to the given url. Then, once the call has been made 
// then if the call was successful, call the Success Callback fn, otherwise, 
// call the Failure CallBack function. 
function ajaxCall(url, callBackSuccess, callBackFailure) {

    // The httpReq Object is now local to function "ajaxCall" (not global)
    var httpReq;
    if (window.XMLHttpRequest) {
        httpReq = new XMLHttpRequest();  //For Firefox, Safari, Opera
    } else if (window.ActiveXObject) {
        httpReq = new ActiveXObject("Microsoft.XMLHTTP");    //For IE 5+
    } else {
        alert('ajax not supported');
    }

    console.log("ready to get content " + url);
    httpReq.open("GET", url); // specify which page you want to get

    // Save the url into the httpReq object (into a custom property that Sally 
    // is adding to the browser supplied http request object).
    httpReq.myUrl = url;

    // Ajax calls are asyncrhonous (non-blocking). Specify the code that you 
    // want to run when the response (to the http request) is available. 
    httpReq.onreadystatechange = function () {

        // readyState == 4 means that the http request is complete
        if (httpReq.readyState === 4) {
            if (httpReq.status === 200) {
                callBackSuccess(httpReq);
            } else {
                // First use of property creates new (custom) property
                httpReq.errorMsg = "Error (" + httpReq.status + " " + httpReq.statusText +
                        ") while attempting to read '" + url + "'";
                callBackFailure(httpReq);
            }
        }
    };  // end of anonymous function

    httpReq.send(null); // initiate ajax call

} // end function ajaxCall

