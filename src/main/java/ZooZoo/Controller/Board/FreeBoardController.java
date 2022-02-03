package ZooZoo.Controller.Board;

import ZooZoo.Domain.DTO.Board.BoardDTO;
import ZooZoo.Service.Free.FreeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FreeBoardController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    FreeBoardService FreeBoardService;


    //자유게시판 쓰기 이동
    @GetMapping("/Board/FreeBoardWrite")
    public String goToFreeBoardWrite(){
        return "Board/Free/FreeBoardWrite";
    }

    //자유게시판 쓰기 처리
    @PostMapping("/Board/FreeBoardWriteController")
    @ResponseBody
    public String freeBoardWriteController(BoardDTO boardDTO,
                                           @RequestParam("freebfile") List<MultipartFile> files){

        String btitle = request.getParameter("freebtitle");
        String bcontents = request.getParameter("freebcontents");
        if(btitle == null || btitle.equals("") || bcontents == null || bcontents.equals("")){
            return "3";
        }else{
            boardDTO.setBtitle(btitle);
            boardDTO.setBcontents(bcontents);
            int rs = FreeBoardService.FreeBoardWrite(boardDTO, files);
            if(rs== 1){
                return "1";
            }else{
                return "2";
            }
        }
    }
}