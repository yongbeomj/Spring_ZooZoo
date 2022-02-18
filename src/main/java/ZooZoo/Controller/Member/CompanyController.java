package ZooZoo.Controller.Member;

/*import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;*/
import ZooZoo.Domain.DTO.Member.CMMemberDTO;
import ZooZoo.Domain.DTO.Member.MemberDTO;
import ZooZoo.Service.Member.CompanyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Controller
public class CompanyController {

@Autowired
HttpServletRequest request;
/*
    @GetMapping("/")
    public String CompanyCheck(){
        try{
            URL url =  new URL("https://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=hm1u3zRV0ba96YTa5BqV4zu0jYFV2LGfPe2aRk0NyJVQsoX5FCSjuVth8RKvBvQzOW8ApIHwaxmajW9%2FRaYR5A%3D%3D");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
            String result = bufferedReader.readLine();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            System.out.println(jsonObject);

            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            System.out.println(jsonArray);
            for(int i =0; i<jsonArray.size(); i++){
                JSONObject obj = (JSONObject) jsonArray.get(i);
                System.out.println(obj);
            }
        }catch(Exception e){

        }

        return "Main";
    }
*/

    /*@GetMapping("/Member/CompanySignUp")
    public String Companysignup(){

    }*/
    @Autowired
    CompanyMemberService companyMemberService;
    // 회원가입
    @PostMapping("/Member/CompanySignUpController")
    @ResponseBody
    public String SignUp(CMMemberDTO cmmemberDTO) {
        boolean result = companyMemberService.CompanySignUp(cmmemberDTO);
        if (result) {
            return "1";
        } else {
            return "2";
        }
    }

    // 기업로그인
    @GetMapping("/Member/CompanyLoginController")
    @ResponseBody
    public String LoginController(@RequestParam("cmid") String cmid, @RequestParam("cmpw") String cmpw) {
        boolean result = companyMemberService.CompanyLogin(cmid, cmpw);
        if (result) {
            return "1";
        } else {
            return "2";
        }
    }

    // 기업로그인 아이디 찾기
    @PostMapping("/Member/CompanyFindIdController")
    @ResponseBody
    public String FindIdController(@RequestParam("cmname") String cmname, @RequestParam("cmemail") String cmemail){
        //이메일, 패스워드 둘 중 하나라도 공백이거나 null일 경우
        System.out.println("아이디님 값은 나오시죠?");
        if (cmemail.equals("") || cmemail == null || cmname.equals("") || cmname == null) {
            return "1";
        } else {
            String result = companyMemberService.FindId(cmemail, cmname);
            if (result != null && !result.equals("")) {
                return result;
                //동일한 회원 정보가 없으면
            } else {
                return "2";
            }
        }
    }

    // 기업 비밀번호 찾기
    @PostMapping("/Member/CompanyFindPwController")
    public String FindIdController(@RequestParam("cmno") String cmno, @RequestParam("cmemail") String cmemail, Model model){
        String result = companyMemberService.Findpw(cmno, cmemail);

        System.out.println("result : " + result);
        if (result != null) {
            String msg = " 회원님의 비밀번호 : " + result;
            model.addAttribute("findpwmsg", msg);
        } else {
            String msg = " 동일한 회원정보가 없습니다.";
            model.addAttribute("findpwmsg", msg);
        }

        return "Member/CompanyFindPw";
    }
}
