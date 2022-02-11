$(document).ready(function() {
  $('#freebcontents').summernote({
    lang: 'ko-KR', // default: 'en-US' //메뉴 한글버전
    minHeight:400, //최소 높이
    maxHeight:null, //최대
    placeholder:"내용입력"
  });
});


function freeboardDelete(bno){
    $.ajax({
        url: "/Board/Free/FreeBoardImgDelete",
        data:{"bno":bno},
        success: function(result){
            alert(result);
            if(result == 1){
                $("#imgBox").load(location.href + ' #imgBox');
            }else{
                alert("오류발생");
            }
        }
    });
}

//드래그앤 드롭으로 자유게시판 글 수정하기
var fileList = []; //파일 정보를 담아 둘 배열
$(document).ready(function(){
    //드래그앤드랍
    $("#fileDrop").on("dragenter", function(e){
        e.preventDefault();
        e.stopPropagation();
    }).on("dragover", function(e){
        e.preventDefault();
        e.stopPropagation();
        $(this).css({
            background: "rgba(255,206,147,1)",
            transition: "all 0.3s ease"
        });
    }).on("dragleave", function(e){
        e.preventDefault();
        e.stopPropagation();
        $(this).css("background-color", "#FFF");
    }).on("drop", function(e){
        e.preventDefault();

        var files = e.originalEvent.dataTransfer.files;
        if(files != null && files != undefined){
            var tag = "";
            for(i=0; i<files.length; i++){
                var f = files[i];
                fileList.push(f);
                var fileName = f.name;
                var fileSize = f.size / 1024 / 1024;
                fileSize = fileSize < 1 ? fileSize.toFixed(3) : fileSize.toFixed(1);
                tag +=
                        "<div class='fileList'>" +
                            "<span class='fileName'>"+fileName+"</span>" +
                            "<span class='fileSize'>"+fileSize+" MB</span>" +
                            "<span class='clear'></span>" +
                        "</div>";
            }
            $(this).append(tag);
        }

        $(this).css("background-color", "#FFF");
    });

    //저장
    $(document).on("click", "#save2", function(){
        var bno = $("#bno").val();
        alert(bno);
        var formData = new FormData($("#fileForm")[0]);
        if(fileList.length > 0){
            fileList.forEach(function(f){
                formData.append("fileList", f);
            });
        }

        $.ajax({
            url : "/Board/Free/FreeBoardUpdate",
            data : formData,
            type:'POST',
            enctype:'multipart/form-data',
            processData:false,
            contentType:false,
            dataType:'json',
            cache:false,
            success:function(result){
            //0이면 오류발생 //1이면 글쓰기 완료 //2이면 내용이나 제목이 없음 -> 글쓰기 실패 //3이면??
                if(result == 1){
                    alert("글 수정 완료.");
                    location.href = "/Board/Free/FreeBoardView/"+bno;
                }else if(result == 2){
                    alert("로그인을 먼저 해주세요.");
                    location.href = "/Member/Login";
                }else{
                    alert("버그 발생!");
                }
            }
        });
    });
});

//수정페이지 이미지 개별삭제
function boardImgDelete(bno, bimg){

    //alert(bno +", " + bimg);
    $.ajax({
        url:"/Board/Free/FreeBoardImgDelete",
        data:{"bno":bno,"bimg":bimg},
        success: function(result) {
            alert(result);
            location.reload();
        }
    });
}

