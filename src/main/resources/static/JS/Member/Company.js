// 회원가입 유효성검사
var idj = /^[a-z0-9]{3,15}$/;
var pwj = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\d~!@#$%^&*()+|=]{3,16}$/;
var namej = /^[가-힣]{1,15}$/;
var phonej = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
var emailj = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

$(function() {
    $("#cmpw").keyup(function() {
        var cmpw = $("#cmpw").val();
        if(!pwj.test(cmpw)) {
            $("#checkpw1").html("3~15글자 영대소문자, 숫자, 특수문자 포함");
        } else {
            $("#checkpw1").html("사용 가능");
        }
    });
    $("#cmpw2").keyup(function() {
        var cmpw1 = $("#cmpw").val();
        var cmpw2 = $("#cmpw2").val();
        if(cmpw1 == cmpw2) {
            if(!pwj.test(cmpw2)) {
                $("#checkpw2").html("3~15글자 영대소문자, 숫자, 특수문자 포함");
            } else {
                $("#checkpw2").html("일치");
            }
        } else {
            $("#checkpw2").html("불일치")
        }
    });
    $("#cmname").keyup(function() {
        var mname = $("#cmname").val();
        if(!namej.test(mname)) {
            $("#checkcmname").html("2~15글자 한글만가능");
        }else {
            $("#checkcmname").html("사용 가능");
        }
    });
    $("#cmemail").keyup(function() {
        var cmemail = $("#cmemail").val();
        if(!emailj.test(cmemail)) {
            $("#checkcmemail").html("이메일 형식에 맞지 않습니다.");
        }else {
            $("#checkcmemail").html("사용 가능");
        }
    });
    $("#cmphone").keyup(function() {
        var cmphone = $("#cmphone").val();
        if(!phonej.test(cmphone)) {
            $("#checkcmphone").html("전화번호 형식에 맞지 않습니다.");
        }else {
            $("#checkcmphone").html("사용 가능");
        }
    });
    
});

// 회원가입 ajax
function CompanySignup() {
    var cmcompanyno = $("#cmno").val();
    if(cmcompanyno == null || cmcompanyno == ""){
        alert("사업자등록번호를 입력해주세요")
        return false;
    }
    var cmpw = $("#cmpw").val();
    var cmname = $("#cmname").val();
    var cmemail = $("#cmemail").val();
    var cmphone = $("#cmphone").val();
    var cmaddress = $("#sample4_postcode").val() + "," + $("#sample4_roadAddress").val() + "," + $("#sample4_jibunAddress").val() + "," + $("#sample4_extraAddress").val() + "," + $("#sample4_detailAddress").val();
    alert("gㅎㅇ")
    alert(cmaddress)
    $.ajax({
        url: "/Member/CompanySignUpController",
        method: "post",
        data: {"cmcompanyno" : cmcompanyno, "cmname" : cmname, "cmaddress" : cmaddress, "cmemail" : cmemail, "cmphone" : cmphone, "cmpw" : cmpw},
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

function companysignup(){
    var b_no = $("#b_no").val();
    var start_dt = $("#start_dt").val();
    var p_nm = $("#p_nm").val();
    var data = {
        "businesses": [
            {
                "b_no": b_no,
                "start_dt": start_dt,
                "p_nm": p_nm,
                "p_nm2": "",
                "b_nm": ""
            }
        ]
    };
    $.ajax({
        url: "https://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=hm1u3zRV0ba96YTa5BqV4zu0jYFV2LGfPe2aRk0NyJVQsoX5FCSjuVth8RKvBvQzOW8ApIHwaxmajW9%2FRaYR5A%3D%3D",  // serviceKey 값을 xxxxxx에 입력
        type: "POST",
        data: JSON.stringify(data), // json 을 string으로 변환하여 전송
        dataType: "JSON",
        contentType: "application/json",
        accept: "application/json",
        success: function(result) {
            console.log(result);
            /*alert(JSON.stringify(result.data[0].valid));*/
            if(result.data[0].valid == "01"){
                alert("인증성공")
                $("#cmno").val(b_no);
                $('#exampleModal').modal('hide');
            }else{
                alert("인증실패")
            }
        },
        error: function(result) {
            console.log(result.responseText); //responseText의 에러메세지 확인
        }
    });

}