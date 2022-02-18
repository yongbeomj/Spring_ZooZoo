//아이디 찾기
function findid(){
    var mname = $("#mname").val();
    var memail = $("#memail").val();
    $.ajax({
        url:"/Member/FindIdController",
        method: "post",
        data:{"mname":mname, "memail":memail},
        success: function(result) {
            //1이면 공백값이라 공백막기
            //alert(result);
           if(result == 1){
                $("#findidmsg").html("내용을 입력해주세요.");
                $("#findidmsg").css({"color":"#ff7f50","font-size":"1.0em"});
            }else if(result == 2){
                $("#findidmsg").html("동일한 회원 정보가 없습니다.");
                $("#findidmsg").css({"color":"#ff7f50","font-size":"1.0em"});
            }else{
                $("#findidmsg").html("회원님의 아이디는 "+result+"입니다.");
                $("#findidmsg").css({"color":"#ff7f50","font-size":"1.0em"});
            }
        }
    });
}

//기업 아이디 찾기
function cmfindid(){
    var cmname = $("#cmname").val();
    var cmemail = $("#cmemail").val();
    alert(cmname);
    alert(cmemail);
    $.ajax({
        url:"/Member/CompanyFindIdController",
        method: "post",
        data:{"cmname":cmname, "cmemail":cmemail},
        success: function(result) {
            //1이면 공백값이라 공백막기
            //alert(result);
            if(result == 1){
                $("#findidmsg").html("내용을 입력해주세요.");
                $("#findidmsg").css({"color":"#ff7f50","font-size":"1.0em"});
            }else if(result == 2){
                $("#findidmsg").html("동일한 회원 정보가 없습니다.");
                $("#findidmsg").css({"color":"#ff7f50","font-size":"1.0em"});
            }else{
                $("#findidmsg").html("회원님의 아이디는 "+result+"입니다.");
                $("#findidmsg").css({"color":"#ff7f50","font-size":"1.0em"});
            }
        }
    });
}

// 회원탈퇴
function mdelete(){
    var passwordconfirm = document.getElementById("passwordconfirm").value;

    $.ajax({
        url: "/Member/mdelete",
        data:{"passwordconfirm" : passwordconfirm},
        success: function(result){
            if (result == 1){
                alert("회원탈퇴가 완료되었습니다.")
                location.href = "/Member/Logout"
            } else {
                $("#deletemsg").html("비밀번호가 일치하지 않습니다.");
            }
        }
    });
}

// 회원수정
function mupdate(mno){
    document.getElementById("tdmid").style = "display:none";    document.getElementById("newmid").style = "display:block";
    document.getElementById("tdmname").style = "display:none";    document.getElementById("newmname").style = "display:block";
    document.getElementById("tdmemail").style = "display:none";    document.getElementById("newmemail").style = "display:block";
    document.getElementById("tdmbirth").style = "display:none";    document.getElementById("newmbirth").style = "display:block";
    document.getElementById("tdmaddress").style = "display:none";    document.getElementById("newmaddress").style = "display:block";
    document.getElementById("tdmdate").style = "display:none";    document.getElementById("newmdate").style = "display:block";
    document.getElementById("btnmupdate").style = "display:none";    document.getElementById("btnmchange").style = "display:block";

    $("#btnmchange").click(function(){
        $.ajax({
            url: "/Member/Update",
            data: {"mno" : mno,
                "newmname" : document.getElementById("newmname").value,
                "newmemail" : document.getElementById("newmemail").value,
                "newmbirth" : document.getElementById("newmbirth").value,
                "newmaddress" : document.getElementById("newmaddress").value,
                },
            success: function(result) {
                if (result == 1){
                    alert("정보가 수정되었습니다");
                    document.getElementById("tdmid").style = "display:block";    document.getElementById("newmid").style = "display:none";
                    document.getElementById("tdmname").style = "display:block";    document.getElementById("newmname").style = "display:none";
                    document.getElementById("tdmemail").style = "display:block";    document.getElementById("newmemail").style = "display:none";
                    document.getElementById("tdmbirth").style = "display:block";    document.getElementById("newmbirth").style = "display:none";
                    document.getElementById("tdmaddress").style = "display:block";    document.getElementById("newmaddress").style = "display:none";
                    document.getElementById("tdmdate").style = "display:block";    document.getElementById("newmdate").style = "display:none";
                    document.getElementById("btnmupdate").style = "display:block";    document.getElementById("btnmchange").style = "display:none";
                    location.reload();
                } else {
                    alert("오류발생");
                }
            }
        });
    });
}

function mpwupdate(mno){
    $.ajax({
        url: "/Member/Pwupdate",
        data: {"mno" : mno,
        "tdmpw" : document.getElementById("tdmpw").value,
        "newmpw" : document.getElementById("newmpw").value,
            },
        success: function(result) {
            if (result == 1){
                alert("정보가 수정되었습니다");
                location.href="/Member/Myinfo";
            } else {
                $("#pwfailmsg").html("비밀번호가 일치하지 않습니다");
                $("#tdmpw").val("");
                $("#newmpw").val("");
            }
        }
    });
}




