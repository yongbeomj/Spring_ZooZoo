package ZooZoo.Controller.Board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FreeBoardController {
    //자유게시판 쓰기 이동
    @GetMapping("/Board/FreeBoardWrite")
    public String goToFreeBoardWrite(){
        return "Board/Free/FreeBoardWrite";
    }

}
