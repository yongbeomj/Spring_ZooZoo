$(".toggle-height").click(function(){
    $(".side-panel").animate({
        height: "toggle"
    });
});

function WriteReply(addrx, addry, agreedate) {
    var btitle = $("#btitle").val();
    var bcontents = $("#bcontents").val();
    $.ajax({
        url: "/ShareReply",
        data: {"btitle" : btitle, "bcontents" : bcontents, "addrx" : addrx, "addry" : addry, "agreedate" : agreedate},
        success: function(result) {
            if(result == 1) {
                alert("Success");
            } else if(result == 2) {
                alert("Failed");
            } else {
                alert("로그인 후 이용 가능");
            }
        }
    });
}
function ReviewView(addrx, addry, agreedate) {
    alert(addrx + " : " + addry + " : " + agreedate);
    $.ajax({
        url: "/ShareReviewView",
        data: {"addrx" : addrx, "addry" : addry, "agreedate" : agreedate},
        success: function(result) {

        }
    });
}