var mapContainer , // 지도를 표시할 div
    mapOption;

var map;

// 마커가 표시될 위치입니다
var markerposition ;

var h_addr,
    h_tel,
    h_status,
    h_x,
    h_y,
    h_cnm;

// 마커를 생성합니다
var marker ;
var checkdata;
var checkdatay;

var getlat,
    getlon,
    getaddr;

var checked = 0;
var bstar;

const drawStar = (target) => {
    bstar = target.value/2;
    $(`.star span`).css({
        width: `${target.value * 10}%`
    });
}

function getAddr(lat,lng){
    let geocoder = new kakao.maps.services.Geocoder();

    let coord = new kakao.maps.LatLng(lat, lng);
    let callback = function(result, status) {
        console.log("값 출력하기" + result);
        console.log("상태값 출력하기" + status);
        if (status === kakao.maps.services.Status.OK) {
            console.log(result);
        }
    };
    geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
}

if (navigator.geolocation) {
    // GeoLocation을 이용해서 접속 위치를 얻어옵니다.
    navigator.geolocation.getCurrentPosition(function(position) {
        var lat2 = position.coords.latitude, // 위도
            lon2 = position.coords.longitude; // 경도
        getlat = lat2;
        getlon = lon2;
        var urllocation = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x="+getlon+"&y="+getlat+""
        $.ajax({
            url: urllocation,
            headers: { 'Authorization': 'KakaoAK b2ccc164f6cc52f4401fce4a4488a831'},
            success: function(data) {
                getaddr = data.documents[0].address_name;
                console.log(getaddr);
            }
        });
    });


} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

}

/*로드뷰*/
var container = document.getElementById('container'), // 지도와 로드뷰를 감싸고 있는 div 입니다
    mapWrapper = document.getElementById('mapWrapper'), // 지도를 감싸고 있는 div 입니다
    btnRoadview = document.getElementById('btnRoadview'), // 지도 위의 로드뷰 버튼, 클릭하면 지도는 감춰지고 로드뷰가 보입니다
    btnMap = document.getElementById('btnMap'), // 로드뷰 위의 지도 버튼, 클릭하면 로드뷰는 감춰지고 지도가 보입니다
    rvContainer = document.getElementById('roadview'); // 로드뷰를 표시할 div 입니다

// 로드뷰 객체를 생성합니다
var roadview = new kakao.maps.Roadview(rvContainer);
var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
// 특정 장소가 잘보이도록 로드뷰의 적절한 시점(ViewPoint)을 설정합니다
// Wizard를 사용하면 적절한 로드뷰 시점(ViewPoint)값을 쉽게 확인할 수 있습니다
roadview.setViewpoint({
    pan: 321,
    tilt: 0,
    zoom: 0
});

// 지도와 로드뷰를 감싸고 있는 div의 class를 변경하여 지도를 숨기거나 보이게 하는 함수입니다
function toggleMap(active) {
    if (active) {

        // 지도가 보이도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
        container.className = "view_map"
    } else {

        // 지도가 숨겨지도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
        container.className = "view_roadview"

    }
}
/*로드뷰 end*/

var mapContainer = document.getElementById('map'), // 지도의 중심좌표
    mapOption = {
        center: new kakao.maps.LatLng(33.451475, 126.570528), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 로드뷰 타일 이미지 추가
map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);
// 실시간교통 타일 이미지 추가
map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);

var marker = new kakao.maps.Marker({
    map: map,
    position: null
});

var content = "";

var overlay = new kakao.maps.CustomOverlay({
    content: content,
    map: map,
    position: marker.getPosition()
});

marker.setMap(null)

$.get("/testmap.json", function(data) {
    h_cnm = data.hospital.bizplcnm
    h_addr = data.hospital.refineroadnmaddr;
    h_tel =  data.hospital.locplcfaclttelno;
    h_status = data.hospital.bsnstatenm;
    h_x   =  data.hospital.lat;
    h_y   =  data.hospital.logt;
    markerposition = new kakao.maps.LatLng(h_x, h_y);

    map.setCenter(markerposition);
    marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(h_x, h_y)
    });

    content = '<div class="wrap">' +
        '    <div class="info">' +
        '        <div class="title">' +
                        h_cnm +
        '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
        '        </div>' +
        '        <div class="body">' +
        '            <div class="img">' +
        '                <img src="../../IMG/logofoot.svg" width="73" height="70">' +
        '           </div>' +
        '            <div class="desc">' +
        '                <div class="ellipsis">'+h_addr+'</div>' +
        '                <div class="jibun ellipsis">'+ h_tel   +'</div>' +
        '                <span class="badge bg-success badge-sm">'+ h_status +'</span>'+
        '            </div>' +
        '        </div>' +
        '    </div>' +
        '</div>';

    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
    kakao.maps.event.addListener(marker, 'click', function() {
        overlay.setMap(map);
    });

    // 마커 위에 커스텀오버레이를 표시합니다
    // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
    overlay = new kakao.maps.CustomOverlay({
        content: content,
        map: map,
        position: marker.getPosition()
    });

    roadviewClient.getNearestPanoId(markerposition, 50, function(panoId) {
        roadview.setPanoId(panoId, markerposition);
    });

    // 로드뷰 초기화가 완료되면
    kakao.maps.event.addListener(roadview, 'init', function() {
        // 로드뷰에 특정 장소를 표시할 마커를 생성하고 로드뷰 위에 표시합니다
        rvMarker = new kakao.maps.Marker({
            position: markerposition,
            map: roadview
        });
    });

    return marker;
});

// 로드뷰의 위치를 특정 장소를 포함하는 파노라마 ID로 설정합니다
// 로드뷰의 파노라마 ID는 Wizard를 사용하면 쉽게 얻을수 있습니다
/*roadview.setPanoId(1023434522, markerposition);*/
// 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.


// 로드뷰 초기화가 완료되면
kakao.maps.event.addListener(roadview, 'init', function() {
    // 로드뷰에 특정 장소를 표시할 마커를 생성하고 로드뷰 위에 표시합니다
    var rvMarker = new kakao.maps.Marker({
        position: marker.getPosition(),
        map: roadview
    });
});

// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
kakao.maps.event.addListener(marker, 'click', function() {
    overlay.setMap(map);
});

// 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
function closeOverlay() {
    overlay.setMap(null);
}


$(document).ready(function (callback){
    $("#sidebartoggle").trigger('click');
    roadviewClient.getNearestPanoId(marker.getPosition(), 50, function(panoId) {
        roadview.setPanoId(panoId, marker.getPosition());
    });

    $.ajax({
        url: '/getmapside' ,
        success: function(data) {
            /*alert(getaddr);*/
            $("#addrget").text(getaddr);
            $("#contents").html(data);
        }
    });

    $.ajax({
        url: '/getmapsidehome' ,
        success: function(data) {
            $("#home").html(data);
        }
    });

});


$.ajax({
    url: '/getmapsidehome' ,
    success: function(data) {
        $("#home").html(data);
    }
});


// onclick 홈 html 전달 함수
function homeclick(){
    $.ajax({
        url: '/getmapsidehome' ,
        success: function(data) {
            $("#review").html("");
            $("#home").html(data);
        }
    });
}

// onclick 리뷰 html 전달 함수
function reviewclick(){
    $.ajax({
        url: '/getmapsidereview' ,
        success: function(data) {
            $("#home").html("");
            $("#review").html("");
            $("#review").html(data);
        }
    });
}

// 리뷰 달기 및 출력하기
function replywrite(apikey){
    var rcontents = $("#rcontents").val();
    var cano = 3; // 동물 병원 카테고리 병원은 3번일까요?

    if( rcontents == "" ){
        alert("리뷰 내용을 입력해주세요");
        return;
    }
    if(bstar == 0 || bstar == null){
        alert("리뷰 평점을 남겨주세요")
        return
    }

    if(bstar == null || bstar == ""){
        rupdatestar = rupdatestar;

    }else{
        rupdatestar = bstar;

    }

    if(rupdatestar == null || rupdatestar ==""){
        alert("리뷰 별점에 오류가 생겼습니다.");
        return ;
    }

    $.ajax({
        url: "/hospitalreply" ,
        data : { "apikey" : apikey , "cano" : cano, "rcontents" : rcontents, "bstar" : bstar },
        success: function(data) {
            if( data == 1 ){ // 만약 성공적으로 댓글이 달렸다면 값을 부여
            bstar = 0;
                $.ajax({
                    url: '/getmapside' ,
                    success: function(data) {
                        $("#addrget").text("");
                        $("#contents").html("");
                        $("#addrget").text(getaddr);
                        $("#contents").html(data);
                    }
                });
                $("#rcontents").val("");
                $.ajax({
                    url: '/getmapsidereview' ,
                    success: function(data) {
                        $("#home").html("");
                        $("#review").html("");
                        $("#review").html(data);
                    }
                });
            }else {
                alert("로그인 후 사용가능합니다");
                return;
            }
        }
    });
}

/*리뷰 삭제 하기*/
function replydelete(bno){
    $.ajax({
        url: '/reviewdelete',
        data:{"bno": bno},
        success: function(data){
            if(data == "1"){
                $.ajax({
                    url: '/getmapside' ,
                    success: function(data) {
                        $("#addrget").text("");
                        $("#contents").html("");
                        $("#addrget").text(getaddr);
                        $("#contents").html(data);
                    }
                });
                $.ajax({
                    url: '/getmapsidereview' ,
                    success: function(data) {
                        $("#home").html("");
                        $("#review").html("");
                        $("#review").html(data);
                    }
                });
            }else{
                alert("리뷰 삭제 오류 [관리자 문의]")
            }
        }
    });
}


/* 리뷰 수정하기 할때 처음에 가져온값 뿌려주는 함수 이후 업데이트 실행하기*/
function r_updateget(bno, bcontents, bustar){
    $('div[id=rupdateid]').attr('value',bno);
    $('div[id=rupdatestar]').attr('value',bustar);
    $("#rcontents2").text(bcontents);
    $(`#modalstar span`).css({
        width: `${bustar * 10 * 2}%`
    });

}

/* 리뷰 수정하기 */
function replyupdate(){
    var rcontents2 = document.getElementById("rcontents2").value;
    var bno = $('div[id=rupdateid]').attr('value');
    var rupdatestar = $('div[id=rupdatestar]').attr('value');

    // 리뷰내용 공백 시
    if( rcontents2 == "" ){
        alert("리뷰 내용을 입력해주세요");
        return;
    }

    if(bstar == 0 || bstar == null){
        alert("리뷰 평점을 남겨주세요")
        return
    }

    if(bstar == null || bstar == ""){
        rupdatestar = rupdatestar;

    }else{
        rupdatestar = bstar;
    }

    if(rupdatestar == null || rupdatestar ==""){
        alert("리뷰 별점에 오류가 생겼습니다.");
        return ;
    }
    $.ajax({
        url: '/getmapside' ,
        success: function(data) {
            $("#addrget").text("");
            $("#contents").html("");
            $("#addrget").text(getaddr);
            $("#contents").html(data);
        }
    });

    $.ajax({
        url: "/reviewupdate",
        data : {"bno":bno,"rcontents":rcontents2, "bstar":rupdatestar},
        success: function(data){
            if(data == "1"){
                alert("리뷰가 수정되었습니다.");
                $.ajax({
                        url: '/getmapside' ,
                        success: function(data) {
                            $("#addrget").text("");
                            $("#contents").html("");
                            $("#addrget").text(getaddr);
                            $("#contents").html(data);
                        }
                });
                $.ajax({
                    url: '/getmapsidereview' ,
                    success: function(data) {
                        $("#home").html("");
                        $("#review").html("");
                        $("#review").html(data);
                    }
                });
                bstar = 0;

            }else{
                alert("리뷰 수정 오류 [관리자 문의]")
            }

        }
    });

}



