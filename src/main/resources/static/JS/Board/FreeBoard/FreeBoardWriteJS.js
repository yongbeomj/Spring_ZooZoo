//Summernote 사용하기 (한글)
$(document).ready(function() {
  $('#freebcontents').summernote({
    lang: 'ko-KR', // default: 'en-US' //메뉴 한글버전
    minHeight:400, //최소 높이
    maxHeight:null, //최대
    placeholder:"내용입력"
  });
});

//글쓰기
function boardwrite(){
   var formData = new FormData(document.getElementById("freeboardform"));
   $.ajax({
        type:"POST",
        url:"/Board/FreeBoardWriteController",
        data:formData,
        processData:false,
        contentType:false,
        success: function(result){
            if(result == 1){
                alert("글쓰기 완료.");
                location.href = "/freeboard";
            }else if(result == 2){
                alert("로그인을 먼저 해주세요.");
                location.href = "/Member/Login";
            }else if(result == 3){
                alert("내용을 입력해주세요.");
            }else{
                alert("버그 발생!")
            }
        }
   });
}
