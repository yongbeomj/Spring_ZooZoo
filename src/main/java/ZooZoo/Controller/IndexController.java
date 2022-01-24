package ZooZoo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

//    @PostMapping("/Member/SignupController")
//    public String SignupController(Memb) {
//
//    }
}
