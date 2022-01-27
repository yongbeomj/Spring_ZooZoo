package ZooZoo.Controller.Board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    // 분양게시판으로
    @GetMapping("/ShareBoardList")
    public String goToShareBoardList() {
        return "Board/ShareBoardList";
    }

    // 자유게시판으로
    @GetMapping("/freeboard")
    public String GotoFreeBoard(){
        return "Board/FreeBoardMain";
    }

    // 유기게시판으로
    @GetMapping("/LossBoardlist")
    public String goToLossBoardList() {
        return "Board/LossBoardlist";
    }

    // 상세페이지로
    @GetMapping("/Board/BoardView")
    public String goToBoardView() {
        return "Board/BoardView";
    }
}
