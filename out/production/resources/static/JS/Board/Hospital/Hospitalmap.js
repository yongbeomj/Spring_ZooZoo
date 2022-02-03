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

$(document).ready(function (callback){
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(37.56682, 126.97865), // 지도의 중심좌표
            level: 10, // 지도의 확대 레벨
            mapTypeId : kakao.maps.MapTypeId.ROADMAP // 지도종류
        };

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



