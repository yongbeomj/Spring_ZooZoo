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

function RIReply() {
    var bno = $("#bno").val();
    var RIReply = $("#RIReplycontents").val();
    $.ajax({
        url: "/RIReply",
        data: {"bno" : bno, "RIReply" : RIReply},
        success: function(result) {
            if(result == 1) {
                $("#sideaside1").load(location.href+ " #sideaside1");
            } else {
                alert("댓글 등록 불가 : 관리자에게 문의")
            }
        }
    });
}

function RIReply(bno) {
    $.ajax({
        url: "/RIReplyView",
        data: {"bno" : bno},
        success: function(result) {
            $("#sideaside1").html(result);
        }
    });
}