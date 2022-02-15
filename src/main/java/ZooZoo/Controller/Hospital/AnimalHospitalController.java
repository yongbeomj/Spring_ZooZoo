package ZooZoo.Controller.Hospital;

import ZooZoo.Domain.DTO.Member.MemberDTO;
import ZooZoo.Domain.DTO.Pagination;
import ZooZoo.Domain.Entity.Board.BoardEntity;
import ZooZoo.Service.Hospital.AnimalHospitalService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AnimalHospitalController {

    @Autowired
    AnimalHospitalService animalHospitalService;

    @Autowired
    HttpServletRequest request;

    HospitalDto hospitalDto = new HospitalDto();

    // 동물병원 API 게시판 출력하기
    @GetMapping("/hospitalboard")
    public String hospitalboard(Model model,
                                @RequestParam(defaultValue = "1") int page){
        /* 검색 서비스 */
        String keyword = request.getParameter("keyword");
        String search = request.getParameter("search");
        String status = request.getParameter("status");

        HttpSession session = request.getSession();

        if( keyword!=null || search!=null || status != null){
            session.setAttribute("keyword" , keyword);
            session.setAttribute("search" , search);
            session.setAttribute("status" , status);
        }else{
            if(keyword==null){
                keyword = "";
            }
            if(search==null){
                search = "";
            }
            if(status==null){
                status = "";
            }
            keyword =  (String) session.getAttribute("keyword");
            search =   (String) session.getAttribute("search");
            status =  (String) session.getAttribute("status");
        }

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        ArrayList<HospitalDto> parses = new ArrayList<>();
        ArrayList<HospitalDto> parsePage = new ArrayList<>();

        int totalList = animalHospitalService.getapitotal(); // 총 토탈 게시물 개수

        jsonArray = animalHospitalService.getapi(totalList); // 총 개수를 받아서 출력할 페이지
        /// 검색 테스트 start ///
        if(keyword==null){
            keyword = "";
        }
        if(search==null){
            search = "";
        }
        if(status==null){
            status = "";
        }

        parses = animalHospitalService.parseapisearch(jsonArray, keyword, search, status);
        /// 검색 테스트 end ///
        /* parses = animalHospitalService.parseapi(jsonArray); // 모든 게시물 출력*/
        parsePage = animalHospitalService.parsenum(parses, page);

        Pagination pagination = new Pagination(parses.size(), page); // 전달 값을 토탈 개수를 전달해야됨
        model.addAttribute("parses",parsePage);
        model.addAttribute("pagination", pagination);
        return "Board/Hospital/HospitalBoard";
    }

    // 동물병원맵 작동시키기
    @GetMapping("/hospitalmap")
    public String hospitalmap(Model model){
        System.out.println("값은 찍히냐?"+ hospitalDto);
        model.addAttribute("hospitalDto", hospitalDto);

        return "Board/Hospital/HospitalMap";
    }


    // 동물병원 맵 임시 api
    @GetMapping("/testmap.json")
    @ResponseBody
    public JSONObject testmap(){
        JSONObject jsonObject = new JSONObject(); // json 전체 [ 응답 용 ]
        jsonObject.put("hospital" ,  hospitalDto );  // json 전체에 리스트 넣기

        return jsonObject;
    }

    // 동물병원 맵 컨트롤러 주고받을 데이터 정하기
    @PostMapping("/hospitalmapcontroller")
    @ResponseBody
    public String hsmapcontroller(@RequestBody HospitalDto hospitalDto2, Model model){
        System.out.println("값"+ hospitalDto2);
        hospitalDto.setLogt(hospitalDto2.getLogt()); // 위도
        hospitalDto.setLat(hospitalDto2.getLat()); // 경도
        hospitalDto.setBizplcnm(hospitalDto2.getBizplcnm()); // 병원 이름
        hospitalDto.setRefineroadnmaddr(hospitalDto2.getRefineroadnmaddr()); // 병원 주소
        hospitalDto.setBsnstatenm(hospitalDto2.getBsnstatenm()); // 영업 상태명
        hospitalDto.setLocplcfaclttelno(hospitalDto2.getLocplcfaclttelno()); // 전화번호
        hospitalDto.setSigunnm(hospitalDto2.getSigunnm()); // 시 정보
        hospitalDto.setSiguncd(hospitalDto2.getSiguncd()); // 시 정보코드
        return "1";
    }

    // 동물병원 api 테스트
    @GetMapping("/hospitaltest")
    @ResponseBody
    public String hospital(Model model){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonArray = animalHospitalService.getapi(animalHospitalService.getapitotal());
        return jsonArray.toString();
    }

    // 사이드 바 맵에 전달해야되는 값
    @GetMapping("/getmapside")
    public String getmapside(Model model){

        char encoding = hospitalDto.getBizplcnm().charAt(0);
        int code = (int)encoding;
        String encode = Integer.toHexString(code);

        double x = Double.parseDouble(hospitalDto.getLogt());
        int x_i = (int) x;
        String s_x = Integer.toString(x_i);

        double y = Double.parseDouble(hospitalDto.getLat());
        int y_i = (int) y;
        String s_y = Integer.toString(y_i);

        encode = encode + s_x + s_y;

        double avg = animalHospitalService.getreplyavg(encode, 3);
        List<BoardEntity> replyEntities = animalHospitalService.getreplylist(encode, 3);
        double avg_s = avg * 10*2;
        model.addAttribute("avg_s", avg_s);
        model.addAttribute("avg", avg);
        model.addAttribute("hospitalDto", hospitalDto);
        model.addAttribute("replyEntities", replyEntities);
        model.addAttribute("encode", encode);
        return "/Board/Hospital/HospitalSide";
    }

    @GetMapping("/getmapsidehome")
    public String getmapsidehome(Model model){
        model.addAttribute("hospitalDto", hospitalDto);

        return "/Board/Hospital/HospitalSideHome";
    }

    @GetMapping("/getmapsidereview")
    public String getmapsidereview(Model model){
        /*char ch = '최';
        int code = (int)ch;
        String encode = Integer.toHexString(code);
        System.out.println(encode);*/
        char encoding = hospitalDto.getBizplcnm().charAt(0);
        int code = (int)encoding;
        String encode = Integer.toHexString(code);

        double x = Double.parseDouble(hospitalDto.getLogt());
        int x_i = (int) x;
        String s_x = Integer.toString(x_i);

        double y = Double.parseDouble(hospitalDto.getLat());
        int y_i = (int) y;
        String s_y = Integer.toString(y_i);

        encode = encode + s_x + s_y;


        System.out.println(encode);
        // 해당 게시물 댓글 호출  /* 동물병원 카테고리 넘버 3*/
        List<BoardEntity> replyEntities = animalHospitalService.getreplylist(encode, 3);

       /* replyEntities.get(0).getBcontents()*/

        model.addAttribute("replyEntities", replyEntities);
        model.addAttribute("encode", encode);
        model.addAttribute("hospitalDto", hospitalDto);
        return "/Board/Hospital/HospitalSideReview";
    }

    @GetMapping("/hospitaltable")
    public String gethospitaltable(Model model,
                                   @RequestParam(defaultValue = "1") int page){
        /* 검색 서비스 */
        String keyword = request.getParameter("keyword");
        String search = request.getParameter("search");
        String status = request.getParameter("status");

        HttpSession session = request.getSession();

        if( keyword!=null || search!=null || status != null){
            session.setAttribute("keyword" , keyword);
            session.setAttribute("search" , search);
            session.setAttribute("status" , status);
        }else{
            if(keyword==null){
                keyword = "";
            }
            if(search==null){
                search = "";
            }
            if(status==null){
                status = "";
            }
            keyword =  (String) session.getAttribute("keyword");
            search =   (String) session.getAttribute("search");
            status =  (String) session.getAttribute("status");
        }

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        ArrayList<HospitalDto> parses = new ArrayList<>();
        ArrayList<HospitalDto> parsePage = new ArrayList<>();

        int totalList = animalHospitalService.getapitotal(); // 총 토탈 게시물 개수

        jsonArray = animalHospitalService.getapi(totalList); // 총 개수를 받아서 출력할 페이지
        /// 검색 테스트 start ///
        if(keyword==null){
            keyword = "";
        }
        if(search==null){
            search = "";
        }
        if(status==null){
            status = "";
        }

        parses = animalHospitalService.parseapisearch(jsonArray, keyword, search, status);
        /// 검색 테스트 end ///
        /* parses = animalHospitalService.parseapi(jsonArray); // 모든 게시물 출력*/
        parsePage = animalHospitalService.parsenum(parses, page);

        Pagination pagination = new Pagination(parses.size(), page); // 전달 값을 토탈 개수를 전달해야됨
        model.addAttribute("parses",parsePage);
        model.addAttribute("pagination", pagination);

        return "/Board/Hospital/HospitalTable";
    }

    @GetMapping("/hospitalpaging")
    public String gethospitalpaging(Model model,
                                   @RequestParam(defaultValue = "1") int page){
        /* 검색 서비스 */
        String keyword = request.getParameter("keyword");
        String search = request.getParameter("search");
        String status = request.getParameter("status");

        HttpSession session = request.getSession();

        if( keyword!=null || search!=null || status != null){
            session.setAttribute("keyword" , keyword);
            session.setAttribute("search" , search);
            session.setAttribute("status" , status);
        }else{
            keyword =  (String) session.getAttribute("keyword");
            search =   (String)  session.getAttribute("search");
            status =  (String)  session.getAttribute("status");
        }

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        ArrayList<HospitalDto> parses = new ArrayList<>();
        ArrayList<HospitalDto> parsePage = new ArrayList<>();

        int totalList = animalHospitalService.getapitotal(); // 총 토탈 게시물 개수

        jsonArray = animalHospitalService.getapi(totalList); // 총 개수를 받아서 출력할 페이지
        /// 검색 테스트 start ///
        parses = animalHospitalService.parseapisearch(jsonArray, keyword, search, status);
        /// 검색 테스트 end ///
        /* parses = animalHospitalService.parseapi(jsonArray); // 모든 게시물 출력*/
        parsePage = animalHospitalService.parsenum(parses, page);

        Pagination pagination = new Pagination(parses.size(), page); // 전달 값을 토탈 개수를 전달해야됨
        model.addAttribute("parses",parsePage);
        model.addAttribute("pagination", pagination);

        return "/Board/Hospital/HospitalTable";
    }

    // 댓글 작성
    @GetMapping("/hospitalreply")
    @ResponseBody
    public String replywrite(@RequestParam("apikey") String apikey,
                             @RequestParam("cano") int cano,
                             @RequestParam("rcontents") String rcontents,
                             @RequestParam("bstar") String bstar) {

        HttpSession session = request.getSession();
        MemberDTO memberDto = (MemberDTO) session.getAttribute("loginDTO");
        // 로그인 안되어 있을 경우
        if (memberDto == null) {
            return "2";
        } else {
            animalHospitalService.replywrite(apikey, cano, rcontents, bstar, memberDto.getMno());
            return "1";
        }
    }

    // 댓글 평균 평점 뽑아내기
    @GetMapping("/hospitalavgstar")
    @ResponseBody
    public String replystar(Model model){
        char encoding = hospitalDto.getBizplcnm().charAt(0);
        int code = (int)encoding;
        String encode = Integer.toHexString(code);

        double x = Double.parseDouble(hospitalDto.getLogt());
        int x_i = (int) x;
        String s_x = Integer.toString(x_i);

        double y = Double.parseDouble(hospitalDto.getLat());
        int y_i = (int) y;
        String s_y = Integer.toString(y_i);

        encode = encode + s_x + s_y;

        double avg = animalHospitalService.getreplyavg(encode, 3);
        List<BoardEntity> replyEntities = animalHospitalService.getreplylist(encode, 3);
        String avg_s = Double.toString(avg);

        return avg_s;
    }

}
