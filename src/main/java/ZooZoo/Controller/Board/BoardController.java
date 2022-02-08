package ZooZoo.Controller.Board;

import ZooZoo.Domain.DTO.Board.BoardDTO;
import ZooZoo.Domain.DTO.Board.LossDTO;
import ZooZoo.Domain.DTO.Board.ShareDTO;
import ZooZoo.Domain.DTO.Pagination;
import ZooZoo.Service.Free.FreeBoardService;
import ZooZoo.Service.Loss.LossService;
import ZooZoo.Service.Share.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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

    // 분양게시판으로
    @GetMapping("/ShareBoardList")
    public String goToShareBoardList(Model model, @RequestParam(defaultValue = "1") int page) {
	    ShareDTO shareDTO = new ShareDTO();
	    ArrayList<ShareDTO> shareDTOS = new ArrayList<>();
	    ArrayList<String> petshop = shareService.Share();
	    ArrayList<String> oneStep = new ArrayList<>();
	    ArrayList<String> twoStep = new ArrayList<>();
	    ArrayList<String> threeStep = new ArrayList<>();
	    String[] s = new String[petshop.size()];
        int totalSize = petshop.size();
        Pagination paging = new Pagination(totalSize, page);
        int j = 0;
        if(page != 80) {
	        for(int i = (page-1) * 100; i < paging.getPageSize() * page; i++) {
                s[i] = petshop.get(i);
                oneStep.add(s[i].split(":")[0]);
                twoStep.add(s[i].split(":")[1]);
                threeStep.add(s[i].split(":")[2]);
                shareDTOS.add(new ShareDTO(oneStep.get(j), threeStep.get(j), twoStep.get(j)));
                j++;
	        }
        } else {
            for(int i = (page-1) * 100; i < totalSize; i++) {
                s[i] = petshop.get(i);
                oneStep.add(s[i].split(":")[0]);
                twoStep.add(s[i].split(":")[1]);
                threeStep.add(s[i].split(":")[2]);
                shareDTOS.add(new ShareDTO(oneStep.get(j), threeStep.get(j), twoStep.get(j)));
                j++;
	        }
        }
        model.addAttribute("pagination", paging);
	    model.addAttribute("share", shareDTOS);
	    return "Board/Share/ShareBoardList";
    }

    // 자유게시판으로
    @GetMapping("/freeboard")
    public String GotoFreeBoard(Model model){
        ArrayList<BoardDTO> boardDTOs = freeBoardService.GetAll();
        model.addAttribute("freeboard", boardDTOs);
        return "Board/Free/FreeBoardMain";
    }

    // 유기게시판으로
    @GetMapping("/LossBoardlist")
    public String goToLossBoardList(Model model) {

        ArrayList<LossDTO> lossDTOS = lossService.Losslist();
        model.addAttribute("lossDTOS",lossDTOS);
        return "Board/Loss/LossBoardlist";
    }

    // 상세페이지로
    @GetMapping("/Board/Loss/LossBoardView")public String goToLossBoardView() {return "Board/Loss/LossBoardView";}
    @GetMapping("/Board/Free/FreeBoardView")public String goToFreeBoardView() {return "Board/Free/FreeBoardView";}

    @GetMapping("/ShareBoardView/{shareno}")
    public String SBView(ShareDTO shareDTO, Model model) {
        String no = shareDTO.getShareno(); String sno = no.split(",")[0]; String s_no = sno.split("=")[1];
        String name = no.split(",")[1]; String sname = name.split("=")[1];
        String address = no.split(",")[2]; String saddress = address.split("=")[1];

        ShareDTO dto = new ShareDTO();
        dto.setShareno(s_no); dto.setSharename(sname); dto.setShareaddress(saddress);
        model.addAttribute("shareDTO", dto);
        return "Board/Share/ShareBoardView";
    }
}
