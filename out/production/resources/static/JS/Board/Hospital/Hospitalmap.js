var mapContainer , // 지도를 표시할 div
    mapOption;

var map;
// 마커가 표시될 위치입니다
var markerPosition ;

// 마커를 생성합니다
var marker ;

var overlay = new kakao.maps.CustomOverlay({
    content: null,
    map: map,
    position: null
});
var getlat,
    getlon,
    getaddr;


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


$(document).ready(function (callback){
    var h_addr ;
    var h_tel ;
    var h_status ;

    $.get("/testmap.json", function(data) {
      h_addr = data.hospital.refineroadnmaddr;
      h_tel =  data.hospital.locplcfaclttelno;
      h_status = data.hospital.bsnstatenm;
    });

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(37.56682, 126.97865), // 지도의 중심좌표
            level: 10, // 지도의 확대 레벨
            mapTypeId : kakao.maps.MapTypeId.ROADMAP // 지도종류
        };
    $("#sidebartoggle").trigger('click');
    // 지도를 생성한다
    var map = new kakao.maps.Map(mapContainer, mapOption);


    $.get("/testmap.json", function(data) {
        alert(  JSON.stringify(data.hospital));
        /*alert(  JSON.stringify(data.hospital));
        alert(  JSON.stringify(data.hospital.lat));
        alert(  JSON.stringify(data.hospital.logt));*/
        var markerposition = new kakao.maps.LatLng(data.hospital.lat, data.hospital.logt);
        // 지도에 마커를 생성하고 표시한다
        var marker = new kakao.maps.Marker({
            position: markerposition, // 마커의 좌표
            map: map // 마커를 표시할 지도 객체
        });

        // 지도 가운데 포지션 정렬
        map.setCenter(markerposition);

        var content = '<div class="wrap">' +
            '    <div class="info">' +
            '        <div class="title">' +
                        data.hospital.bizplcnm +
            '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
            '        </div>' +
            '        <div class="body">' +
            '            <div class="img">' +
            '                <img src="../../IMG/logofoot.svg" width="73" height="70">' +
            '           </div>' +
            '            <div class="desc">' +
            '                <div class="ellipsis">'+data.hospital.refineroadnmaddr+'</div>' +
            '                <div class="jibun ellipsis">'+ data.hospital.locplcfaclttelno   +'</div>' +
            '                <span class="badge bg-success badge-sm">'+ data.hospital.bsnstatenm +'</span>'+
            /*'                <div><a href="https://www.kakaocorp.com/main" target="_blank" class="link">홈페이지</a></div>' +*/
            '            </div>' +
            '        </div>' +
            '    </div>' +
            '</div>';

        // 마커 위에 커스텀오버레이를 표시합니다
        // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
        var overlay = new kakao.maps.CustomOverlay({
            content: content,
            map: map,
            position: marker.getPosition()
        });

    });

    $.ajax({
        url: '/getmapside' ,
        success: function(data) {
            alert(getaddr);
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


$(window).on(function() {
    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
    kakao.maps.event.addListener(marker, 'click', function() {
        overlay.setMap(map);
    });

    // 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
    function closeOverlay() {
        overlay.setMap(null);
    }
});


    // 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
    function closeOverlay() {
        overlay.setMap(null);
    }
/*

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.56660, 126.97949), // 지도의 중심좌표
        level: 3, // 지도의 확대 레벨
        mapTypeId : kakao.maps.MapTypeId.ROADMAP // 지도종류
    };



// 지도를 생성한다
    var map = new kakao.maps.Map(mapContainer, mapOption);
}*/

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
            $("#review").html(data);
        }
    });
}


