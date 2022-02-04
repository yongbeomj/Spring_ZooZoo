function getmap(logt, lat, bizplcnm, refineroadnmaddr, bsnstatenm, locplcfaclttelno, sigunnm, siguncd ){

    var hospitalDto = {
        "logt" : logt,
        "lat" : lat,
        "bizplcnm":bizplcnm,
        "refineroadnmaddr":refineroadnmaddr,
        "bsnstatenm":bsnstatenm,
        "locplcfaclttelno":locplcfaclttelno,
        "sigunnm":sigunnm,
        "siguncd":siguncd
    };

    $.ajax({
        url : "/hospitalmapcontroller",
        method: 'post',
        contentType: "application/json" ,  //  ajax 타입,
        data : JSON.stringify(hospitalDto),
        success: function(data) {
            if(data == "1"){
                location.href = "/hospitalmap"
            }
        }
    })
}

function getmap2(){
    lat = $("#lat").val();
    logt = $("#logt").val();
    alert(lat)
    alert(logt)
}

/*function getmap(){

    $(function() {
        alert('hello')
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div

            mapOption = {
                center: new kakao.maps.LatLng(lat, logt), // 지도의 중심좌표
                level: 3, // 지도의 확대 레벨
                mapTypeId : kakao.maps.MapTypeId.ROADMAP // 지도종류
            };
        // 지도를 생성한다
        var map = new kakao.maps.Map(mapContainer, mapOption);
    });
}*/


/*var mapContainer = document.getElementById('map'), // 지도를 표시할 div

mapOption = {
    center: new kakao.maps.LatLng(37.56660, 126.97949), // 지도의 중심좌표
    level: 3, // 지도의 확대 레벨
    mapTypeId : kakao.maps.MapTypeId.ROADMAP // 지도종류
};

// 지도를 생성한다
var map = new kakao.maps.Map(mapContainer, mapOption);*/

