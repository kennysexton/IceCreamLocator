//Script for opening and closing menu

function openNav() {
  document.getElementById("mySidebar").style.width = "33%";
}
function closeNav() {
//    document.getElementById("mySidebar").style.width = "0px";
    document.getElementById("mySidebar").removeAttribute("style");
}
var width = $(window).width();  // detects change in width
$(window).on('resize',function(){
    if($(this).width() != width){
        closeNav();
    }
});

$('.dropdown').click(function(){
    
    if($().hasClass('active')){
        $(this).removeClass('active')
    } else {
        $(this).addClass('active')
    }
});
