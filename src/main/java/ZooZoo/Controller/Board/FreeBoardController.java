package ZooZoo.Controller.Board;

import ZooZoo.Domain.DTO.Board.BoardDTO;
import ZooZoo.Domain.DTO.Category.CategoryDTO;
import ZooZoo.Domain.DTO.Member.MemberDTO;
import ZooZoo.Domain.Entity.Board.BoardEntity;
import ZooZoo.Domain.Entity.Board.BoardImgEntity;
import ZooZoo.Domain.Entity.Board.BoardRepository;
import ZooZoo.Domain.Entity.Category.CategoryEntity;
import ZooZoo.Service.Free.FreeBoardService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
public class FreeBoardController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    FreeBoardService freeBoardService;

    @Autowired
    BoardRepository boardRepository;


    //자유게시판 쓰기 이동
    @GetMapping("/Board/FreeBoardWrite")
    public String goToFreeBoardWrite(){
        return "Board/Free/FreeBoardWrite";
    }

    //자유게시판 쓰기 처리
    /*@PostMapping("/Board/FreeBoardWriteController")
    @ResponseBody
    public String freeBoardWriteController(
                                           @RequestParam("freebfile") List<MultipartFile> files){

        *//*HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginDTO");
        CategoryDTO categoryDTO = new CategoryDTO(4, "자유");*//*

        String btitle = request.getParameter("freebtitle");
        String bcontents = request.getParameter("freebcontents");
        if(btitle == null || btitle.equals("") || bcontents == null || bcontents.equals("")){
            return "3";
        }else{

            int rs = freeBoardService.FreeBoardWrite(btitle, bcontents, files);
            if(rs== 1){
                return "1";
            }else if(rs==4){
                return "4";
            }else{
                return "2";
            }
        }
    }*/
    //자유게시판 쓰기 드래그 앤 드롭
    @ResponseBody
    @RequestMapping(value = { "/Board/FreeBoardWriteController" }, method = RequestMethod.POST, produces = "json/plain;charset=UTF-8")
    public String uploadPath(MultipartHttpServletRequest mtfRequest) {
        String res = "1";
        String btitle = request.getParameter("freebtitle");
        String bcontents = request.getParameter("freebcontents");
        System.out.println("제목 > " + btitle);
        System.out.println("내용 > " + bcontents);
        //전달받은 제목, 타이틀이 없으면 3반환
        if (btitle == null || btitle.equals("") || bcontents == null || bcontents.equals("")) {
            return "3";
        }
        //전달받은 제목, 내용이 있으면 (첨부파일은 multipart라 null이래도 사이즈는 0이다.)
        if (mtfRequest != null) {
            List<MultipartFile> fileList = mtfRequest.getFiles("fileList");
            //첨부파일이 없어도 글 써야됨
            if (fileList.isEmpty()) {
                String rs = freeBoardService.FreeBoardWrite(btitle, bcontents, fileList);
                return rs;
            }
            //첨부파일이 있으면
            for (int i = 0; i < fileList.size(); i++) {
                MultipartFile multi = fileList.get(i);
                System.out.println("multi : " + multi);
                //첨부파일이 null이면
                if (multi == null) {
                    return "0";
                //첨부파일이 있는데 사이즈가 0이다?? 말도안되니까 리턴
                } else if (multi.getSize() == 0) {
                    return "0";
                //첨부파일이 null이 아니고, 사이즈도 0이 아니니까 글 등록해야됨
                } else {
                    String rs2 = freeBoardService.FreeBoardWrite(btitle, bcontents, fileList);
                    System.out.println("파일명 : " + multi.getOriginalFilename() + " / 파일 사이즈 : " + multi.getSize());
                    return rs2;
                }
            }
        }
        return res;
    }

    //자유게시판 상세페이지
    @GetMapping("/Board/Free/FreeBoardView/{bno}")
    public String goToFreeBoardView(@PathVariable("bno") int bno, Model model) {
        ArrayList<String> bimglist = new ArrayList<String>();
        try {
            BoardEntity boardEntity = freeBoardService.getFreeBoardView(bno);
            for(int i =0; i<boardEntity.getBoardImgEntities().size(); i++){
                bimglist.add(boardEntity.getBoardImgEntities().get(i).getBimg());
            }
            model.addAttribute("boardEntity", boardEntity);
            model.addAttribute("bimglist",bimglist);
        } catch(Exception e){
            System.out.println(e);
        }
        return "Board/Free/FreeBoardView";
    }

    //자유게시판 게시물 삭제
    @GetMapping("/Board/Free/FreeBoardDelete")
    @ResponseBody
    public int FreeBoardDelete(@RequestParam("bno") int bno){
        boolean result = freeBoardService.deleteBoard(bno);
        if(result){
            return 1;
        }else{
            return 2;
        }
    }

    //자유게시판 게시물 수정페이지 이동
    @GetMapping("/Board/Free/goToFreeBoardUpdate/{bno}")
    public String goToFreeBoardUpdate(@PathVariable("bno") int bno, Model model){
        BoardEntity boardEntity = freeBoardService.getFreeBoardView(bno);
        //리스트에 담아서 첨부파일 보여주기
        List<String> imgList = new ArrayList<>();
        if(boardEntity.getBoardImgEntities().size() != 0){
            for(int i=0; i<boardEntity.getBoardImgEntities().size(); i++){
                //원래 첨부파일 보여주는 거
                String realImg = boardEntity.getBoardImgEntities().get(i).getBimg().toString().split("_")[1];
                imgList.add(realImg);
            }
        }else{
            System.out.println("없음");
        }
        System.out.println(imgList);
        //첨부파일이 없다면???????
        //원래 첨부파일 보여주는 거
        String imgStr = imgList.toString().replace("[","").replace("]","");
        model.addAttribute("imgStr",imgStr);
        model.addAttribute("boardEntity", boardEntity);
        return "Board/Free/FreeBoardUpdate";
    }

    //자유게시판 수정 이미지 개별 삭제
    @ResponseBody
    @GetMapping ("/Board/Free/FreeBoardImgDelete")
    public String FreeBoardImgDelete(@RequestParam("bno") int bno, @RequestParam("bimg") String bimg){
        boolean rs = freeBoardService.deleteBoardImg(bno, bimg);
        if(rs) {
            return "1";
        }else {
            return "2";
        }
    }


    //자유게시판 수정처리
    @ResponseBody
    @RequestMapping(value = {"/Board/Free/FreeBoardUpdate"}, method = RequestMethod.POST, produces = "json/plain;charset=UTF-8")
    public String FreeBoardUpdate(MultipartHttpServletRequest mtfRequest,
                                  @RequestParam("bcontents") String bcontents,
                                  @RequestParam("btitle") String btitle,
                                  @RequestParam("bno") int bno){

        String res = "1";
        System.out.println("제목 > " + btitle);
        System.out.println("내용 > " + bcontents);
        System.out.println(bcontents+", "+btitle+", "+bno+", "+mtfRequest);
        
        //수정 요청이 있으면
        if (mtfRequest != null) {
            List<MultipartFile> fileList = mtfRequest.getFiles("fileList");
            //첨부파일이 없으면 ("")이면 저장해야됨, 첨부파일이 없어도 글은 수정할 수 있어야됨
            if (fileList.isEmpty()) {
                String rs = freeBoardService.updateFreeBoard(bcontents, btitle, bno, fileList);
                return rs;
            }
            //첨부파일이 있을 때
            for (int i = 0; i < fileList.size(); i++) {
                MultipartFile multi = fileList.get(i);
                System.out.println("multi : " + multi);
                //첨부파일 리스트의 i번째가 null이면?
                if (multi == null) {
                    return "0";
                //첨부파일 리스트가 null은 아닌데 사이즈가 0이면??
                } else if (multi.getSize() == 0) {
                    return "0";
                //첨부파일 리스트가 null이 아니고 사이즈가 0이 아니면 글 수정하기
                } else {
                    String rs2 = freeBoardService.updateFreeBoard(bcontents, btitle,  bno, fileList);
                    System.out.println("파일명 : " + multi.getOriginalFilename() + " / 파일 사이즈 : " + multi.getSize());
                    return rs2;
                }
            }
        }
        //1 반환 (글쓰기 완료)
        return res;

    }

    //첨부파일 다운로드 처리
    //boolean rs = freeBoardService.updateFreeBoard(bcontents, btitle, bno, file2);
       /* if(rs) {
            return "redirect:/Board/Free/FreeBoardView/" + bno;
        }
        return "redirect:/Board/Free/FreeBoardView/" + bno;*/
}












