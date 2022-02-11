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

    $.ajax({
        url : "/ShareBoardListController",
        data : {"option" : optionVal},
        success : function(result) {

            $("#tab").html( result );

        }
    });
}
