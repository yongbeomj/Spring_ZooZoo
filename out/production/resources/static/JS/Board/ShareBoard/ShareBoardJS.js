//function selectArea() {
//    var i = $(".sel").val();
//    var select = $("#selectA" + i).val();
//    alert(select);
//}
function changeSelection(){
    var selectedElement = document.getElementById("selectBoxTest"); // 선택한 option의 value, 텍스트
    var optionVal = selectedElement.options[selectedElement.selectedIndex].value;
    alert(optionVal);
//    location.href="/ShareBoardList?option="+optionVal;
    if(optionVal == "시도") {
        $.ajax({
            url: "/ShareBoardList",
            success: function(result) {
                location.href = "/ShareBoardList";
            }
        });
    } else {
        $.ajax({
            url : "/ShareBoardListController",
            data : {"option" : optionVal},
            success : function(result) {
            alert(result);
                $("#pp").html("");
                $("#cate").html(result);
            }
        });
    }
}

// 지도----------------------------------------------------------------------------------------------------------
var lat = $("#lat").val(); var post = $("#post").val();
var lng = $("#lng").val(); var tel = $("#tel").val();
var name = $("#name").val();
var address = $("#address").val();
var old = $("#oldaddr").val();
var mapContainer = document.getElementById('map'), // 지도의 중심좌표
    mapOption = {
        center: new kakao.maps.LatLng(lat, lng), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

    if(lat != "정보없음" && lng != "정보없음") {
        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        // 지도에 마커를 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(lat, lng)
        });

        // 커스텀 오버레이에 표시할 컨텐츠 입니다
        // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
        // 별도의 이벤트 메소드를 제공하지 않습니다
        var content = '<div class="wrap">' +
                    '    <div class="info">' +
                    '        <div class="title">' +
                                name +
                    '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
                    '        </div>' +
                    '        <div class="body">' +
                    '            <div class="desc">' +
                    '                <div class="ellipsis">' + address + '</div>' +
                    '                <div class="jibun ellipsis">(우) ' + post +
                    '                <div class="jibun ellipsis">(지번) ' + old + '</div>' +
                    '                <div class="jibun ellipsis">번호 (' + tel + '</div>' +
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

        // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
        kakao.maps.event.addListener(marker, 'click', function() {
            overlay.setMap(map);
        });

        // 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
        function closeOverlay() {
            overlay.setMap(null);
        }
    } else if(lat == "정보없음" || lng == "정보없음") {
        $("#map").html("<div class = 'text-center' style = 'padding-top: 30px; background-color: white; height: 380px; border-top-left-radius: 25px; border-top-right-radius: 25px;'><h2 style='font-size: 130px; margin-top: 80px;'>지도정보가 없습니다.</h2></div>");
    }


// 지도----------------------------------------------------------------------------------------------------------