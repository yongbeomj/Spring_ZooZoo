function Login() {
    var mid = $("#loginid").val();
    var mpw = $("#loginpw").val();
    $.ajax({
        url: "/Member/LoginController",
        data : {"mid" : mid, "mpw" : mpw},
        success: function(result) {
            if(result == 1) {
                alert("메인페이지로 이동합니다.");
                location.href = "/";
            } else {
                alert("로그인 실패.");
            }
        }
    });
}