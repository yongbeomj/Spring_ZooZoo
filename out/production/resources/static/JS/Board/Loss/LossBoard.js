function categoryChange(e) {
    var good_city = ["가평군","고양시","과천시","광명시","광주시","구리시","군포시","김포시","남양주시","동두천시","부천시","성남시","수원시","시흥시","안산시","안성시","안양시","양주시","양평군","여주군","연천군","오산시","용인시","의왕시","의정부시","이천시","파주시","평택시","포천시","하남시","화성시"];
    var good_tot = ["가평군유기동물보호소","고양시동물보호센터","광명반함센터","광주TNR동물병원초월","광주TNR동물병원송정","남양주동물보호협회","대관령동물병원","24시아이동물메디컬","부천시수의사회","가나동물병원","cj동물병원","가야동물병원","펫토피아동물병원","수원시 동물보호센터","한국야생동물보호협회","스타캣츠","이성준동물병원","한국동물구조관리협회","양평군유기동물보호소","위더스 동물보호센터","오산 유기동물보호소","용인시 동물보호센터","버디종합동물병원","나은동물병원","파주24시동물병원","파주독 애견호텔","웰니스클리닉","로뎀동물병원","금릉동물병원","문산동물병원","미엘동물병원","행복한동물병원","평택시유기동물보호소","하남동물병원","남양동물보호센터"];
    var good_none = ["없음"];
    var good_a = ["가평군유기동물보호소"];
    var good_b = ["고양시동물보호센터"];
    var good_d = ["광명반함센터"];
    var good_e = ["광주TNR동물병원초월","광주TNR동물병원송정"];
    var good_i = ["남양주동물보호협회","대관령동물병원"];
    var good_k = ["24시아이동물메디컬","부천시수의사회","가나동물병원","cj동물병원","가야동물병원"];
    var good_l = ["펫토피아동물병원"];
    var good_m = ["수원시 동물보호센터"];
    var good_o = ["한국야생동물보호협회","스타캣츠"];
    var good_p = ["이성준동물병원"];
	var good_r = ["한국동물구조관리협회"];
	var good_s = ["양평군유기동물보호소"];
	var good_t = ["위더스 동물보호센터"];
	var good_v = ["오산 유기동물보호소"];
	var good_w = ["용인시 동물보호센터"];
    var good_aa = ["버디종합동물병원","나은동물병원","파주24시동물병원","파주독 애견호텔","웰니스클리닉","로뎀동물병원","금릉동물병원","문산동물병원","미엘동물병원","행복한동물병원"];
    var good_bb = ["평택시유기동물보호소"];
    var good_dd = ["하남동물병원"];
    var good_ee = ["남양동물보호센터"];

    var target = document.getElementById("cals09");

    if(e.value == "total") var d = good_tot;
    else if(e.value == "과천시" || e.value == "구리시" || e.value == "군포시" || e.value == "김포시" || e.value == "동두천시" || e.value == "시흥시" ||
         e.value == "안양시" || e.value == "연천군" || e.value == "의왕시" || e.value == "의정부시" || e.value == "이천시" || e.value == "포천시")
    var d = good_none;
    else if(e.value =="가평군") var d = good_a;
    else if(e.value =="고양시") var d = good_b;
    else if(e.value =="광명시") var d = good_d;
    else if(e.value =="광주시") var d = good_e;
    else if(e.value =="남양주시") var d = good_i;
    else if(e.value =="부천시") var d = good_k;
    else if(e.value =="성남시") var d = good_l;
    else if(e.value =="수원시") var d = good_m;
    else if(e.value =="안산시") var d = good_o;
    else if(e.value =="안성시") var d = good_p;
    else if(e.value =="양주시") var d = good_r;
    else if(e.value =="양평군") var d = good_s;
    else if(e.value =="여주군") var d = good_t;
    else if(e.value =="오산시") var d = good_v;
    else if(e.value =="용인시") var d = good_w;
    else if(e.value =="파주시") var d = good_aa;
    else if(e.value =="평택시") var d = good_bb;
    else if(e.value =="하남시") var d = good_dd;
    else if(e.value =="화성시") var d = good_ee;

    target.options.length = 0;

    for (x in d) {
        var opt = document.createElement("option");
        opt.value = d[x];
        opt.innerHTML = d[x];
        target.appendChild(opt);
    }
}

// 댓글
function replywrite(apikey){
    var rcontents = $("#rcontents").val();
    var cano = $("#cano").val();
    // 댓글내용 공백 시 알람
    if( rcontents == "" ){
        alert("댓글 내용을 입력해주세요");
        return;
    }

    $.ajax({
        url: "/replywrite" ,
        data : { "apikey" : apikey , "cano" : cano, "rcontents" : rcontents },
        success: function(data) {
            if( data == 1 ){
                $('#replytable').load( location.href+' #replytable' );
                $("#rcontents").val("");
            }else {
                alert("로그인 후 사용가능합니다");
                return;
            }
        }
    });
}

function rdelete(bno) {

    $.ajax({
        url: "/replydelete",
        data: {"bno" : bno},
        success: function(result){
            if (result == 1) {
                $('#replytable').load( location.href+' #replytable' );
                alert("댓글이 삭제되었습니다");
            } else {
                alert("오류발생");
            }
        }
    });
}

function rupdate(bno){

    document.getElementById("tdbcontents"+bno).innerHTML = "<input class='form-control' type='text' id='newcontents' name='newcontents'>";
    document.getElementById("btnrupdate"+bno).style = "display:none"; // 수정버튼 감추기
    document.getElementById("btnrchange"+bno).style = "display:block"; // 확인버튼 보이기

    $(function(){
        $("#btnrchange"+bno).click(function(){ // 확인 버튼 클릭
            $.ajax({
                url: "/replyupdate",
                data: {"bno" : bno, "newcontents" : document.getElementById("newcontents").value},
                success : function(result){
                    if(result == 1){
                        document.getElementById("tdbcontents"+bno).innerHTML = document.getElementById("newcontents").value;
                        document.getElementById("btnrchange"+bno).style = "display:none"; // 확인버튼 감추기
                        document.getElementById("btnrupdate"+bno).style = "display:block"; // 수정버튼 보이기
                    } else {
                        alert("오류발생");
                    }
                }
            });
        });
    });
}

// 팝업




