package ZooZoo.Controller.Member;

import ZooZoo.Domain.DTO.Member.MemberDTO;
import ZooZoo.Service.Member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    HttpServletRequest request;

    // 시작 - 메인화면
    @GetMapping("/")
    public String goToMain(Model model) {
        HttpSession session = request.getSession();
        if(session.getAttribute("loginDTO") != null) {
            return "LogMain";
        } else {
            return "Main";
        }
    }
    // 로그인화면으로
    @GetMapping("/Member/Login")
    public String goToLogin() {return "Member/Login";}
    // 일반 회원가입으로
    @GetMapping("/Member/SignUp")
    public String goToSignUp() {return "Member/SignUp";}
    // 기업 회원가입으로
    @GetMapping("/Member/CompanySignUp")
    public String goToCompanySignUp() {return "Member/CompanySignUp";}
    // 아이디 찾기로
    @GetMapping("/Member/FindId")
    public String goToFindId() {return "Member/FindId";}
    // 비밀번호 찾기로
    @GetMapping("/Member/FindPw")
    public String goToFindPw() {return "Member/FindPw";}


    // 회원가입
    @PostMapping("/Member/SignUpController") @ResponseBody
    public String SignUp(MemberDTO memberDTO) {
        boolean result = memberService.SignUp(memberDTO);
        if(result) {
            return "1";
        } else {
            return "2";
        }
    }

    // 아이디 중복체크
    @GetMapping("/Member/Checkid") @ResponseBody
    public String CheckID(@RequestParam("mid")String mid) {
        boolean result = memberService.Checkid(mid);
        if(result) {
            return "1";
        } else {
            return "2";
        }
    }

    // 로그인
    @GetMapping("/Member/LoginController") @ResponseBody
    public String LoginController(@RequestParam("mid")String mid, @RequestParam("mpw")String mpw) {
        boolean result = memberService.Login(mid, mpw);
        if(result) {
            return "1";
        } else {
            return "2";
        }
    }

    //아이디 찾기
    @ResponseBody
    @PostMapping("/Member/FindIdController")
    public String FindIdController(@RequestParam("memail") String memail,
                                   @RequestParam("mname") String mname,
                                   Model model){
        //이메일, 패스워드 둘 중 하나라도 공백이거나 null일 경우
        if(memail.equals("") || memail == null || mname.equals("") || mname == null){
            return "1";
        }else{
           String result = memberService.FindId(memail, mname);
            if(result != null && !result.equals("")){
                return result;
                //동일한 회원 정보가 없으면
            }else{
                return "2";
            }
        }
    }

    // 비밀번호찾기
    @PostMapping("/Member/FindpwController")
    public String findpwcontroller(Model model) {
        String mid = request.getParameter("mid");
        String memail = request.getParameter("memail");
        String result = memberService.findpw(mid, memail);
        System.out.println("result : " + result);
        if (result != null) {
            String msg = " 회원님의 비밀번호 : " + result;
            model.addAttribute("findpwmsg", msg);
        } else {
            String msg = " 동일한 회원정보가 없습니다.";
            model.addAttribute("findpwmsg", msg);
        }

        return "Member/FindPw";
    }
}
