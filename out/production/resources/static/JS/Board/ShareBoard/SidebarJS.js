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
//    alert(addrx + " : " + addry + " : " + agreedate);
//    $.ajax({
//        url: "/ShareReviewView",
//        data: {"addrx" : addrx, "addry" : addry, "agreedate" : agreedate},
//        success: function(result) {
//            if(result == 1) {
//
//            }
//        }
//    });
}

/* 드랍다운 */
function setDetailsHeight(selector, wrapper = document) {
  const setHeight = (detail, open = false) => {
    detail.open = open;
    const rect = detail.getBoundingClientRect();
    detail.dataset.width = rect.width;
    detail.style.setProperty(
      open ? `--expanded` : `--collapsed`,
      `${rect.height}px`
    );
  };
  const details = wrapper.querySelectorAll(selector);
  const RO = new ResizeObserver((entries) => {
    return entries.forEach((entry) => {
      const detail = entry.target;
      const width = parseInt(detail.dataset.width, 10);
      if (width !== entry.contentRect.width) {
        detail.removeAttribute("style");
        setHeight(detail);
        setHeight(detail, true);
        detail.open = false;
      }
    });
  });
  details.forEach((detail) => {
    RO.observe(detail);
  });
}

/* Run it */
setDetailsHeight("details");

// 댓글 등록
function RIReplyWrite() {
    var bno = $("#bno").val();
    var RIReply = $("#RIReplycontents").val();
    alert(bno);

    $.ajax({
        url: "/RIReply",
        data: {"bno" : bno, "RIReply" : RIReply},
        success: function(result) {
        alert(result)
            if(result == 1) {
                $.ajax({
                        url: "/RIReplyView",
                        data: {"bno" : bno},
                        success: function(result) {
                            $("#sideaside1").html("");
                            $("#sideaside1").html(result);
                            $("#bno").val(bno);
                        }
                });
            } else {
                alert("댓글 등록 불가 : 관리자에게 문의")
            }
        }
    });
}

// 답글 버튼 눌렀을 때
function RIReply(bno) {
    $.ajax({
        url: "/RIReplyView",
        data: {"bno" : bno},
        success: function(result) {
            $("#RIRAside").html(result);
            $("#bno").val(bno);
            $("#reviewbno").val(bno);
        }
    });
}

// 댓글 수정
function reviewUpdate(bno) {
    var updatebox;
    $("#updateBox" + bno).html("<div class = 'row'><div class = 'col-md-9'><input type = 'text' class = 'form-control' id = 'reviewUpdateTitle'><input type = 'text' class = 'form-control mt-1' id = 'reviewUpdateContents'></div><div class = 'col-md-3'><button class = 'btn' style = 'font-size: 10px; width: 100%;' onclick = 'ReUpdate(" + bno + ");'>확인</button><button class = 'btn' style = 'width: 100%; font-size: 10px;' onclick = 'writeCancel(" + bno + ")'>취소</button></div></div>");
}
// 수정 중 취소
function writeCancel(bno) {
    $("#updateBox" + bno).html("");
}
// 댓글 수정
function ReUpdate(bno) {
    var Retitle = $("#reviewUpdateTitle").val();
    var Recontents = $("#reviewUpdateContents").val();
    $.ajax({
        url: "/ReviewUpdate",
        data: {"bno" : bno, "rTitle" : Retitle, "rContents" : Recontents},
        success: function(result) {
            if(result == 1) {
//                $("#sideul").html("");
//                $('#sideul').load(location.href+' #sideul');
                $("#sideul").load(location.href+" #sideul li");
            } else {
                alert("Failed")
            }
        }
    });
}

// 댓글 삭제
function RIReplyDelete(bno) {
    $.ajax({
        url: "/RIReplyDelete",
        data: {"bno" : bno},
        success: function(result) {
            if(result == 1) {
                $("#sideul").load(location.href+ " #sideul");
            } else {
                alert("삭제 실패 관리자에게 문의")
            }
        }
    })
}