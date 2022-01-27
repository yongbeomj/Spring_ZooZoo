/*$( document ).ready(function() {
    var ht = htotal()+"";
    console.log("값전달되나요?" +  ht);
    hreturn(ht);
});*/

function hreturn(htotal){
    $.ajax({
        url :"/api/hospital2",
        data : {"htotal": htotal},
        method: "get",
        contentType:"application/json",
        success:function(result){

        }
    });
}

/*function htotal(){
    var totaldata;
    $.ajax({
        url : "https://openapi.gg.go.kr/Animalhosptl?KEY=ccec045fa339456eaefa46cb9c828bc8&Type=json&plndex=1&pSize=100",
        type : "get",
        timeout: 3000,
        contentType: "application/json",
        async: false,
        dataType : "json",
        success : function(data, status, xhr) {
            /!*console.log(data.data);*!/
            totaldata = JSON.stringify(data.Animalhosptl[0].head[0].list_total_count);
            dataHeader = dataHeader.split("\"")[1]
            console.log(dataHeader);
            if (dataHeader == "INFO-000") {
                console.log("success == >");
                console.log("")
            } else {

            }
        },
        error : function(e, status, xhr, data) {
            console.log("error == >");
            console.log(e);
        }
    });
    return totaldata;
}*/

function hospitalapi() {

    $.ajax({
        url : "/api/hospital",
        type : "get",
        timeout: 3000,
        contentType: "application/json",
        dataType : "json",
        success : function(data, status, xhr) {
            /*console.log(data.data);*/
            data2 = JSON.stringify(data.data.Animalhosptl);
            dataHeader = JSON.stringify(data.data.Animalhosptl[0].head[1].RESULT.CODE);
            realdata = JSON.stringify(data.data.Animalhosptl[1].row[1]);/*값 때려박기 */
            totaldata = JSON.stringify(data.data.Animalhosptl[0].head[0].list_total_count);
            dataHeader = dataHeader.split("\"")[1]

            /*console.log("데이터 테스트" + data2)
            console.log("데이터 테스트2" + dataHeader)
            console.log("데이터 테스트3" + realdata)*/
            console.log("토탈" + totaldata)
            for(var i = 0; totaldata>i; i++){
               data3 = JSON.stringify(data.data.Animalhosptl[1].row[i])
               console.log("값을 출력합니다." + data3+i);
            }
            if (dataHeader == "INFO-000") {
                console.log("success == >");
                console.log("")
            } else {

            }
        },
        error : function(e, status, xhr, data) {
            console.log("error == >");
            console.log(e);
        }
    });

    var totaldata;
    $.ajax({
        url : "https://openapi.gg.go.kr/Animalhosptl?KEY=ccec045fa339456eaefa46cb9c828bc8&Type=json&plndex=1&pSize=100",
        type : "get",
        timeout: 3000,
        contentType: "application/json",
        async: false,
        dataType : "json",
        success : function(data, status, xhr) {
            /*console.log(data.data);*/
            totaldata = JSON.stringify(data.Animalhosptl[0].head[0].list_total_count);
            dataHeader = dataHeader.split("\"")[1]
            console.log(dataHeader);
            if (dataHeader == "INFO-000") {
                console.log("success == >");
                console.log("")
            } else {

            }
        },
        error : function(e, status, xhr, data) {
            console.log("error == >");
            console.log(e);
        }
    });
    hreturn(totaldata);
}