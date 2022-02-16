$(document).ready(function (callback){
    weather();
});
var weather_code = [];
var weather_value = [];

// 맑음
svg_1 = "https://basmilius.github.io/weather-icons/production/fill/all/clear-day.svg"
// 비 옴
svg_2 = "https://basmilius.github.io/weather-icons/production/fill/all/rain.svg"
// 눈/비
svg_3 = "https://basmilius.github.io/weather-icons/production/fill/all/sleet.svg"
// 눈
svg_4 = "https://basmilius.github.io/weather-icons/production/fill/all/snow.svg"
// 빗방울
svg_5 = "https://basmilius.github.io/weather-icons/production/fill/all/raindrops.svg"
// 빗방울 눈날림
svg_6 = "https://basmilius.github.io/weather-icons/production/fill/all/sleet.svg"
//눈날림
svg_7 = "https://basmilius.github.io/weather-icons/production/fill/all/snowflake.svg"

function weather() {
    $.ajax({
        url : "/api/weather",
        type : "get",
        timeout: 3000,
        contentType: "application/json",
        dataType : "json",
        success : function(data, status, xhr) {
            totalCount = JSON.stringify(data.data.response.body.totalCount);
            data_result = JSON.stringify(data.data.response.body.items.item[1] );

            //데이터 응답코드
            dataHeader = data.data.response.header.resultCode;
            for( i =0; i<totalCount; i++){
                //데이터 카테고리
                weather_code[i] = JSON.stringify(data.data.response.body.items.item[i].category);

                //실질적 데이터 값
                weather_value[i] = JSON.stringify(data.data.response.body.items.item[i].obsrValue );
            }

            if (dataHeader == "00") {
                console.log("success == >")
                /*alert(weather_result + "여기서도 값이 나오네요")*/
                for(i = 0; i<weather_code.length;i++) {
                    console.log(weather_code[i] + ":" + weather_value[i])
                    weather_value[i] = weather_value[i].split("\"")[1]
                }

                // 아무것도 없음
                if(weather_value[0]=="0"&&weather_value[0] != null){
                    $("#weather_status").html("<img src="+ svg_1 +" width=\"50\" height=\"50\" alt> <br>" )
                    $("#weather_value").html(
                        "<span>기온  " + weather_value[3] +"°C</span><br>" +
                        "<span>습도  " + weather_value[1] +"%</span><br>" +
                        "<span>풍속  " + weather_value[7] +"m/s </span><br>" +
                        "<span>강수량  " + weather_value[2] +"mm</span><br>"
                    )
                    // 비가 온다면
                }else if(weather_value[0]=="1"&&weather_value[0] != null){
                    $("#weather_status").html("<img src="+ svg_2 +" width=\"25\" height=\"25\" alt> <br>" )
                    $("#weather_value").html(
                        "<span>기온  " + weather_value[3] +"°C</span><br>" +
                        "<span>습도  " + weather_value[1] +"%</span><br>" +
                        "<span>풍속  " + weather_value[7] +"m/s </span><br>" +
                        "<span>강수량  " + weather_value[2] +"mm</span><br>"
                    )
                    // 비/눈 sleet
                }else if(weather_value[0]=="2"&&weather_value[0] != null) {
                    $("#weather_status").html("<img src="+ svg_3 +" width=\"25\" height=\"25\" alt> <br>" )
                    $("#weather_value").html(
                        "<span>기온  " + weather_value[3] +"°C</span><br>" +
                        "<span>습도  " + weather_value[1] +"%</span><br>" +
                        "<span>풍속  " + weather_value[7] +"m/s </span><br>" +
                        "<span>강수량  " + weather_value[2] +"mm</span><br>"
                    )
                    // 눈
                }else if(weather_value[0]=="3"&&weather_value[0] != null) {
                    $("#weather_status").html("<img src="+ svg_4 +" width=\"25\" height=\"25\" alt> <br>" )
                    $("#weather_value").html(
                        "<span>기온  " + weather_value[3] +"°C</span><br>" +
                        "<span>습도  " + weather_value[1] +"%</span><br>" +
                        "<span>풍속  " + weather_value[7] +"m/s </span><br>" +
                        "<span>강수량  " + weather_value[2] +"mm</span><br>"
                    )
                    // 초단기 실황은 4번이 없음 빗방울
                }else if(weather_value[0]=="5"&&weather_value[0] != null) {
                    $("#weather_status").html("<img src="+ svg_5 +" width=\"25\" height=\"25\" alt> <br>" )
                    $("#weather_value").html(
                        "<span>기온  " + weather_value[3] +"°C</span><br>" +
                        "<span>습도  " + weather_value[1] +"%</span><br>" +
                        "<span>풍속  " + weather_value[7] +"m/s </span><br>" +
                        "<span>강수량  " + weather_value[2] +"mm</span><br>"
                    )
                    // 빗방울 눈날림
                }else if(weather_value[0]=="6"&&weather_value[0] != null) {
                    $("#weather_status").html("<img src="+ svg_6 +" width=\"25\" height=\"25\" alt> <br>" )
                    $("#weather_value").html(
                        "<span>기온  " + weather_value[3] +"°C</span><br>" +
                        "<span>습도  " + weather_value[1] +"%</span><br>" +
                        "<span>풍속  " + weather_value[7] +"m/s </span><br>" +
                        "<span>강수량  " + weather_value[2] +"mm</span><br>"
                    )
                    // 눈날림
                }else if(weather_value[0]=="7"&&weather_value[0] != null) {
                    $("#weather_status").html("<img src="+ svg_7 +" width=\"25\" height=\"25\" alt> <br>" )
                    $("#weather_value").html(
                        "<span>기온  " + weather_value[3] +"°C</span><br>" +
                        "<span>습도  " + weather_value[1] +"%</span><br>" +
                        "<span>풍속  " + weather_value[7] +"m/s </span><br>" +
                        "<span>강수량  " + weather_value[2] +"mm</span><br>"
                    )
                }

            } else {
                console.log("fail == >");
                console.log(data2);
                alert("오류발생 : 관리자 문의 요망");
            }
        },
        error : function(e, status, xhr, data) {
            console.log("error == >");
            console.log(e);
            console.log("작동 에러 : API 접근을 시도합니다")
            weather();
        }
    });
}