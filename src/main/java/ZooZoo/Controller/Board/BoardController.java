package ZooZoo.Controller.Board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    // 분양게시판으로
    @GetMapping("/ShareBoardList")
    public String goToShareBoardList() {
        return "Board/Share/ShareBoardList";
    }

    // 자유게시판으로
    @GetMapping("/freeboard")
    public String GotoFreeBoard(){
        return "Board/Free/FreeBoardMain";
    }

    // 유기게시판으로
    @GetMapping("/LossBoardlist")
    public String goToLossBoardList() {
        return "Board/Loss/LossBoardlist";
    }

    // 상세페이지로
    @GetMapping("/Board/Loss/LossBoardView")public String goToLossBoardView() {return "Board/Loss/LossBoardView";}
    @GetMapping("/Board/Share/ShareBoardView")public String goToShareBoardView() {return "Board/Share/ShareBoardView";}
    @GetMapping("/Board/Free/FreeBoardView")public String goToFreeBoardView() {return "Board/Free/FreeBoardView";}
}
