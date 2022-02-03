package ZooZoo.Controller.Board;

import ZooZoo.Domain.DTO.Board.BoardDTO;
import ZooZoo.Domain.DTO.Board.ShareDTO;
import ZooZoo.Service.Free.FreeBoardService;
import ZooZoo.Service.Share.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class BoardController {
	@Autowired
	ShareService shareService;
    @Autowired
    FreeBoardService freeBoardService;
    // 분양게시판으로
    @GetMapping("/ShareBoardList")
    public String goToShareBoardList(Model model) {
	    ShareDTO shareDTO = new ShareDTO();
	    ArrayList<ShareDTO> shareDTOS = new ArrayList<>();
	    ArrayList<String> petshop = shareService.Share();
	    ArrayList<String> oneStep = new ArrayList<>();
	    ArrayList<String> twoStep = new ArrayList<>();
	    ArrayList<String> threeStep = new ArrayList<>();
	    String[] s = new String[petshop.size()];
	    for(int i = 0; i < petshop.size(); i++) {
		    s[i] = petshop.get(i);
		    oneStep.add(s[i].split(":")[0]);
		    twoStep.add(s[i].split(":")[1]);
		    threeStep.add(s[i].split(":")[2]);
		    shareDTOS.add(new ShareDTO(oneStep.get(i), threeStep.get(i), twoStep.get(i)));
	    }
	    model.addAttribute("share", shareDTOS);
	    return "Board/Share/ShareBoardList";
    }

    // 자유게시판으로
    @GetMapping("/freeboard")
    public String GotoFreeBoard(Model model){
        ArrayList<BoardDTO> boardDTOs = freeBoardService.GetAll();
        System.out.println("@@@@@@ boardDtos : "+ boardDTOs);
        model.addAttribute("freeboard", boardDTOs);
        return "Board/Free/FreeBoardMain";
    }

    // 유기게시판으로
    @GetMapping("/LossBoardlist")
    public String goToLossBoardList() {
        return "Board/Loss/LossBoardlist";
    }

    // 상세페이지로
    @GetMapping("/Board/Loss/LossBoardView")public String goToLossBoardView() {return "Board/Loss/LossBoardView";}
    @GetMapping("/Board/Share/ShareBoardView")public String goToShareBoardView() {return "Board/Share/ShareBoardView";}
    @GetMapping("/Board/Free/FreeBoardView")public String goToFreeBoardView() {return "Board/Free/FreeBoardView";}

}
