package ZooZoo.Chatting.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChattingController {

    @GetMapping("/Chatting")
    public String goToChatting() {
        return "Board/Chatting/ChattingView";
    }
}
