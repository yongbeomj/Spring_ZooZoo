$(".toggle-height").click(function(){
    $(".side-panel").animate({
        height: "toggle"
    });
});
$(".toggle-height1").click(function(){
    $(".side-panel1").animate({
        height: "toggle"
    });
});

function WriteReply(addrx, addry, agreedate, code) {
    var btitle = $("#btitle").val();
    var bcontents = $("#bcontents").val();
    $.ajax({
        url: "/ShareReply",
        data: {"btitle" : btitle, "bcontents" : bcontents, "addrx" : addrx, "addry" : addry, "agreedate" : agreedate, "code" : code},
        success: function(result) {
            if(result == 1) {
                $("#sideul").load(location.href+ " #sideul");
                $("#btitle").val("");
                $("#bcontents").val("");
            } else if(result == 2) {
                alert("글쓰기 실패 관리자에게 문의");
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
            if(result == 1) {

            }
        }
    });
}

/* 드랍다운 */
//let main = document.querySelector('.main')
//
//function show(type){
//    document.querySelector('.textBox').value = type;
//}
let dropdown = document.querySelector('.dropdown');
dropdown.onclick = function(){
    dropdown.classList.toggle('active');
}