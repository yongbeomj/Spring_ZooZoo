// 회원가입 유효성검사
var idj = /^[a-z0-9]{3,15}$/;
var pwj = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\d~!@#$%^&*()+|=]{3,16}$/;
var namej = /^[가-힣]{1,15}$/;
var phonej = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
var emailj = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

$(function() {
    $("#mid").keyup(function() {
        var mid = $("#mid").val();
        if(!idj.test(mid)) {
            $("#checkid").html("아이디는 3~15글자");
        } else {
            $.ajax({
                url: "/Member/Checkid",
                data: {"mid" : mid},
                success: function(result) {
                    if(result == 1) {
                        $("#checkid").html("중복된 아이디");
                    } else {
                        $("#checkid").html("사용가능한 아이디");
                    }
                }
            });
        }
    });
    $("#mpw").keyup(function() {
        var mpw = $("#mpw").val();
        if(!pwj.test(mpw)) {
            $("#checkpw1").html("3~15글자 영대소문자, 숫자, 특수문자 포함");
        } else {
            $("#checkpw1").html("사용 가능");
        }
    });
    $("#mpw2").keyup(function() {
        var mpw1 = $("#mpw").val();
        var mpw2 = $("#mpw2").val();
        if(mpw1 == mpw2) {
            if(!pwj.test(mpw2)) {
                $("#checkpw2").html("3~15글자 영대소문자, 숫자, 특수문자 포함");
            } else {
                $("#checkpw2").html("일치");
            }
        } else {
            $("#checkpw2").html("불일치")
        }
    });
    $("#mname").keyup(function() {
        var mname = $("#mname").val();
        if(!namej.test(mname)) {
            $("#checkname").html("2~15글자 한글만가능");
        }else {
            $("#checkname").html("사용 가능");
        }
    });
    $("#sample4_detailAddress").keyup(function() {
        if($("#sample4_detailAddress") != null) {
            $("#confirmBtn").html("<button type='button' class='btn bg-gradient-primary w-100 my-4 mb-2' onclick = 'Signup();'>Sign Up</button>");
        }
    });
});

// 회원가입 ajax
function Signup() {
    var mid = $("#mid").val();
    var mpw = $("#mpw").val();
    var mname = $("#mname").val();
    var memail = $("#memail").val();
    var mbirth = $("#mbirth").val();
    var maddress = $("#sample4_postcode").val() + "," + $("#sample4_roadAddress").val() + "," + $("#sample4_jibunAddress").val() + "," + $("#sample4_extraAddress").val() + "," + $("#sample4_detailAddress").val();

    $.ajax({
        url: "/Member/SignUpController",
        method: "post",
        data: {"mid" : mid, "mpw" : mpw, "mname" : mname, "memail" : memail, "mbirth" : mbirth, "maddress" : maddress},
        success: function(result) {
            if(result == 1) {
                alert("회원가입 성공");
                location.href = "/Member/Login";
            } else {
                alert("실패");
                location.reload();
            }
        }
    });
}

function sample4_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample4_postcode').value = data.zonecode;
            document.getElementById("sample4_roadAddress").value = roadAddr;
            document.getElementById("sample4_jibunAddress").value = data.jibunAddress;

            // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            if(roadAddr !== ''){
                document.getElementById("sample4_extraAddress").value = extraRoadAddr;
            } else {
                document.getElementById("sample4_extraAddress").value = '';
            }

            var guideTextBox = document.getElementById("guide");
            // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
            if(data.autoRoadAddress) {
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                guideTextBox.style.display = 'block';

            } else if(data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                guideTextBox.style.display = 'block';
            } else {
                guideTextBox.innerHTML = '';
                guideTextBox.style.display = 'none';
            }
        }
    }).open();
}