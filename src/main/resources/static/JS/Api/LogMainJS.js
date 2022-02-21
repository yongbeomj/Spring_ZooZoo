
var arrNumber = new Array();
var arrNumber2 = new Array();
var arrNumber3 = new Array();

$.ajax({
    url :"/LossData",
    method: "get",
    contentType:"application/json",
    success:function(result){
        arrNumber = result.split("_");
        for(var i=0; i<arrNumber.length; i++){
            if(i%2 == 0){
               /* console.log(arrNumber[i]);*/
                arrNumber2.push(arrNumber[i]);
                console.log(arrNumber2);
            }else{
               /* console.log(arrNumber[i]);*/
                arrNumber3.push(arrNumber[i]);
                console.log(arrNumber3);
            }
        }
    }
});

setInterval(test, 1000);

function test(){
    var context = document
        .getElementById('container2')
        .getContext('2d');
    var myChart = new Chart(context, {
        type: 'bar', // 차트의 형태
        data: { // 차트에 들어갈 데이터
            labels: arrNumber2,
            datasets: [
                { //데이터
                    label:'최근 500건 데이터', //차트 제목
                    fill: false, // line 형태일 때, 선 안쪽을 채우는지 안채우는지
                    data: arrNumber3,
                    backgroundColor: [
                        //색상
                        'rgb(255, 255, 255)'

                    ],
                    borderColor: [
                        //경계선 색상
                        'rgb(255, 255, 255)'
                    ],
                    borderWidth: 1 //경계선 굵기

                }
            ]
        },
        options: {

            scales: {
                yAxes: [
                    {
                        ticks: {
                            beginAtZero: true
                        }
                    }
                ]
            }
        }
    });

}