package ZooZoo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String goToMain() {
        return "Main";
    }
    @GetMapping("/Member/SignUp")
    public String goToSighUp() {
        return "Member/SignUp";
    }
}
