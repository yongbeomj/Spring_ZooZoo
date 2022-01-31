//Summernote 사용하기 (한글)
$(document).ready(function() {
alert("몰라유");
  $('#freebcontents').summernote({
    lang: 'ko-KR', // default: 'en-US' //메뉴 한글버전
    minHeight:400, //최소 높이
    maxHeight:null, //최대
    placeholder:"내용입력"
  });
});

//글쓰기
function boardwrite(){
   alert("눌렀음");
   var form = new FormData(form);
   var freebname = $("#freebname").val();
   var freebcontents = $("#freebcontents").val();
   alert(String.valueOf(form));
}
