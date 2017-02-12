$(document).ready(function () {
//$('#aa').removeClass('box');
//$('#aa').addClass('red-bg');
$('#descending').hide();
$('#ascending').hide();
    $('#ascending').click(function(event){
        //alert("hey");
   //     event.preventDefault();
     //   var thisId = event.target.id;
        //$('#aa').attr({'class', "aaa"});
       // $('#aa').addClass("aaa");
        //$thisId.addClass("checked");
        //$thisId.addClass('checked');
       // alert(thisId);
    });
});

function radio(){
var thisId = event.target.id;
alert("hey");
//$('#descending').a
$("#descending").prop("checked", true);
//$('#aa').addClass('red-bg');
//$("#aa").addClass("aaa");
//$('#aa').attr({'class', "aaa"});
//$(thisId).addClass("aaa");
//alert(thisId);
//$('head').append('<style>input:focus{background:#fff}</style>');
//$("a#aa").css("background-color", "yellow");

}


