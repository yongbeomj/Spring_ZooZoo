package ZooZoo.Controller.Board;

import ZooZoo.Domain.DTO.Board.BoardDTO;
import ZooZoo.Domain.DTO.Board.LossDTO;
import ZooZoo.Domain.DTO.Board.ShareDTO;
import ZooZoo.Domain.DTO.Pagination;
import ZooZoo.Service.Free.FreeBoardService;
import ZooZoo.Service.Loss.LossService;
import ZooZoo.Service.Share.ShareService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;

@Controller
public class BoardController {
    @Autowired
    ShareService shareService;
    @Autowired
    FreeBoardService freeBoardService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    LossService lossService;

    //     분양게시판으로
    @GetMapping("/ShareBoardList")
    public String goToShareBoardList(Model model, @RequestParam(defaultValue = "1") int page) {
//        HttpSession session = request.getSession();
//        session.setAttribute("option", null);

        System.out.println("3");
        ShareDTO shareDTO = new ShareDTO();
        ArrayList<ShareDTO> shareDTOS = new ArrayList<>();
        ArrayList<String> petshop = shareService.Share(null);
        ArrayList<String> oneStep = new ArrayList<>();
        ArrayList<String> twoStep = new ArrayList<>();
        ArrayList<String> threeStep = new ArrayList<>();
        String[] s = new String[petshop.size()];
        int totalSize = petshop.size();
        Pagination paging = new Pagination(totalSize, page);
        int j = 0;
        if (page != 80) {
            for (int i = (page - 1) * 100; i < paging.getPageSize() * page; i++) {
                s[i] = petshop.get(i);
                oneStep.add(s[i].split(":")[0]);
                twoStep.add(s[i].split(":")[1]);
                threeStep.add(s[i].split(":")[2]);
                shareDTOS.add(new ShareDTO(oneStep.get(j), threeStep.get(j), twoStep.get(j)));
                j++;
            }
        } else {
            for (int i = (page - 1) * 100; i < totalSize; i++) {
                s[i] = petshop.get(i);
                oneStep.add(s[i].split(":")[0]);
                twoStep.add(s[i].split(":")[1]);
                threeStep.add(s[i].split(":")[2]);
                shareDTOS.add(new ShareDTO(oneStep.get(j), threeStep.get(j), twoStep.get(j)));
                j++;
            }
        }

        String[] select = {
                "김포시", "파주시", "고양시", "양주시", "동두천시", "포천시", "가평군",
                "남양주시", "양평군", "여주시", "이천시", "광주시", "하남시", "성남시",
                "용인시", "안성시", "오산시", "평택시", "화성시", "수원시", "안산시",
                "의왕시", "과천시", "군포시", "시흥시", "광명시", "부천시"
        };
        ArrayList<String> selectArr = new ArrayList<>();
        for (int i = 0; i < select.length; i++) {
            selectArr.add(select[i]);
        }

        model.addAttribute("select", selectArr);
        model.addAttribute("pagination", paging);
        model.addAttribute("share", shareDTOS);

        return "Board/Share/ShareBoardList";
    }


    // @GetMapping("/ShareBoardList")
//    @ResponseBody
    @GetMapping("/ShareBoardListController")
    public String ShareBoardListController(Model model, @RequestParam(defaultValue = "1") int page) {
        String option = request.getParameter("option");
        System.out.println("Option : " + option);
        HttpSession session = request.getSession();
        if (option != null) {
            String op = option;
            session.setAttribute("option", op);
        }else{
            option = (String) session.getAttribute("option");
        }

        System.out.println("option : " + option);
        if(option == null || option.equals("null") || option.equals(null)) { // 처음 페이지 열 때
            System.out.println("1");
            ShareDTO shareDTO = new ShareDTO();
            ArrayList<ShareDTO> shareDTOS = new ArrayList<>();
            ArrayList<String> petshop = shareService.Share(option);
            ArrayList<String> oneStep = new ArrayList<>();
            ArrayList<String> twoStep = new ArrayList<>();
            ArrayList<String> threeStep = new ArrayList<>();
            String[] s = new String[petshop.size()];
            int totalSize = petshop.size();
            Pagination paging = new Pagination(totalSize, page);
            int j = 0;
            if (page != 80) {
                for (int i = (page - 1) * 100; i < paging.getPageSize() * page; i++) {
                    s[i] = petshop.get(i);
                    oneStep.add(s[i].split(":")[0]);
                    twoStep.add(s[i].split(":")[1]);
                    threeStep.add(s[i].split(":")[2]);
                    shareDTOS.add(new ShareDTO(oneStep.get(j), threeStep.get(j), twoStep.get(j)));
                    j++;
                }
            } else {
                for (int i = (page - 1) * 100; i < totalSize; i++) {
                    s[i] = petshop.get(i);
                    oneStep.add(s[i].split(":")[0]);
                    twoStep.add(s[i].split(":")[1]);
                    threeStep.add(s[i].split(":")[2]);
                    shareDTOS.add(new ShareDTO(oneStep.get(j), threeStep.get(j), twoStep.get(j)));
                    j++;
                }
            }

            String[] select = {
                    "김포시", "파주시", "고양시", "양주시", "동두천시", "포천시", "가평군",
                    "남양주시", "양평군", "여주시", "이천시", "광주시", "하남시", "성남시",
                    "용인시", "안성시", "오산시", "평택시", "화성시", "수원시", "안산시",
                    "의왕시", "과천시", "군포시", "시흥시", "광명시", "부천시"
            };
            ArrayList<String> selectArr = new ArrayList<>();
            for (int i = 0; i < select.length; i++) {
                selectArr.add(select[i]);
            }

            model.addAttribute("select", selectArr);
            model.addAttribute("pagination", paging);
            model.addAttribute("share", shareDTOS);
            return "Board/Share/ShareTable";
        } else { // 카테고리 선택되면
            System.out.println("2");
            ShareDTO shareDTO = new ShareDTO();
            ArrayList<ShareDTO> shareDTOS = new ArrayList<>();
            ArrayList<String> petshop = shareService.Share(option);
            ArrayList<String> oneStep = new ArrayList<>();
            ArrayList<String> twoStep = new ArrayList<>();
            ArrayList<String> threeStep = new ArrayList<>();
            String[] s = new String[petshop.size()];
            int totalSize = petshop.size();
            Pagination paging = new Pagination(totalSize, page);
            int j = 0;
            if (page != 80) {
                for (int i = (page - 1) * 100; i < paging.getPageSize() * page; i++) {
//                    System.out.println(petshop.get(i));
                    s[i] = petshop.get(i);
                    oneStep.add(s[i].split(":")[0]);
                    twoStep.add(s[i].split(":")[1]);
                    threeStep.add(s[i].split(":")[2]);
                    shareDTOS.add(new ShareDTO(oneStep.get(j), threeStep.get(j), twoStep.get(j)));
                    j++;
                }
            } else {
                for (int i = (page - 1) * 100; i < totalSize; i++) {
                    s[i] = petshop.get(i);
                    oneStep.add(s[i].split(":")[0]);
                    twoStep.add(s[i].split(":")[1]);
                    threeStep.add(s[i].split(":")[2]);
                    shareDTOS.add(new ShareDTO(oneStep.get(j), threeStep.get(j), twoStep.get(j)));
                    j++;
                }
            }

            String[] select = {
                    "김포시", "파주시", "고양시", "양주시", "동두천시", "포천시", "가평군",
                    "남양주시", "양평군", "여주시", "이천시", "광주시", "하남시", "성남시",
                    "용인시", "안성시", "오산시", "평택시", "화성시", "수원시", "안산시",
                    "의왕시", "과천시", "군포시", "시흥시", "광명시", "부천시"
            };
            ArrayList<String> selectArr = new ArrayList<>();
            for (int i = 0; i < select.length; i++) {
                selectArr.add(select[i]);
            }

            model.addAttribute("select", selectArr);
            model.addAttribute("pagination", paging);
            model.addAttribute("share", shareDTOS);
            return "Board/Share/ShareTable";
        }
    }

    // 자유게시판으로
    @GetMapping("/freeboard")
    public String GotoFreeBoard(Model model) {
        ArrayList<BoardDTO> boardDTOs = freeBoardService.GetAll();
        model.addAttribute("freeboard", boardDTOs);
        return "Board/Free/FreeBoardMain";
    }

    // 유기게시판으로
    @GetMapping("LossBoardlist")
    public String goToLossBoardList(Model model, @RequestParam(defaultValue = "1") int page) {

        String sex = request.getParameter("sex"); // 성별
        String kind = request.getParameter("kind"); // 축종
        String city = request.getParameter("city"); // 시군구
        HttpSession session = request.getSession();

        if (sex != null || kind != null || city != null) {
            session.setAttribute("sex", sex);
            session.setAttribute("kind", kind);
            session.setAttribute("city", city);
        } else {
            sex = (String) session.getAttribute("sex");
            kind = (String) session.getAttribute("kind");
            city = (String) session.getAttribute("city");
        }


        ArrayList<LossDTO> parses = lossService.losslist(sex, kind, city); // 필터링 게시물
        ArrayList<LossDTO> parsesPage = lossService.parsenum(parses, page); // 페이징

        Pagination pagination = new Pagination(parses.size(), page);

        model.addAttribute("parsesPage", parsesPage);
        model.addAttribute("pagination", pagination);
        return "Board/Loss/LossBoardlist";
    }

    // 상세페이지로
    @GetMapping("/Board/Loss/LossBoardView/{ABDM_IDNTFY_NO}")
    public String goToLossBoardView(Model model, @PathVariable("ABDM_IDNTFY_NO") String ABDM_IDNTFY_NO) {
        ArrayList<LossDTO> lossDTOS = lossService.getlossboard(ABDM_IDNTFY_NO);
        model.addAttribute("lossDTOS", lossDTOS);
        return "Board/Loss/LossBoardView";
    }

    @GetMapping("/Board/Free/FreeBoardView")
    public String goToFreeBoardView() {
        return "Board/Free/FreeBoardView";
    }

    @GetMapping("/ShareBoardView/{shareno}")
    public String SBView(ShareDTO shareDTO, Model model) {
        String no = shareDTO.getShareno();
        String sno = no.split(",")[0];
        String s_no = sno.split("=")[1];
        String name = no.split(",")[1];
        String sname = name.split("=")[1];
        String address = no.split(",")[2];
        String saddress = address.split("=")[1];

        ShareDTO dto = new ShareDTO();
        dto.setShareno(s_no);
        dto.setSharename(sname);
        dto.setShareaddress(saddress);
        model.addAttribute("shareDTO", dto);
        return "Board/Share/ShareBoardView";
    }
}
