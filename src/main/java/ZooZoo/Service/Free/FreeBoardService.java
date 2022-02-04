package ZooZoo.Service.Free;

import ZooZoo.Domain.DTO.Board.BoardDTO;
import ZooZoo.Domain.DTO.Board.BoardImgDTO;
import ZooZoo.Domain.DTO.Member.MemberDTO;
import ZooZoo.Domain.Entity.Board.BoardEntity;
import ZooZoo.Domain.Entity.Board.BoardImgEntity;
import ZooZoo.Domain.Entity.Board.BoardImgRepository;
import ZooZoo.Domain.Entity.Board.BoardRepository;
import ZooZoo.Domain.Entity.Category.CategoryEntity;
import ZooZoo.Domain.Entity.Category.CategoryRepository;
import ZooZoo.Domain.Entity.Member.MemberEntity;
import ZooZoo.Domain.Entity.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FreeBoardService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardImgRepository bimgRepository;
    @Autowired
    private HttpServletRequest request;

    //자유게시판 (카테고리 넘버 4번) (리스트) 가져오기
    public ArrayList<BoardDTO> GetAll() {
        int categoryNumber = 4;
        List<BoardEntity> boardEntities = boardRepository.findAllBoard(categoryNumber);
        //DTO담을 리스트
        ArrayList<BoardDTO> result = new ArrayList<>();
        System.out.println("사이즈 : " +boardEntities.size());
        for(BoardEntity temp : boardEntities){

            //날짜 변환 MMM -> 2월
            String month = temp.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd"));
            System.out.println("@@@@@"+temp.getBno()+ "번째 날짜 : " + month);

            //엔티티 -> dto
            //System.out.println("이미지엔티티 : " +temp.getBoardImgEntities());
            BoardImgDTO boardImgDTO = new BoardImgDTO();
            System.out.println(boardImgDTO.getBimg());
            BoardDTO boardDTO = BoardDTO.builder()
                    .bno(temp.getBno())
                    .btitle(temp.getBtitle())
                    .bcontents(temp.getBcontents())
                    .bview(temp.getBview())
                    .bfile(  new ArrayList<>() )
                    .bcreateddate(month)

                    .bwriter(temp.getMemberEntity().getMid())
                    .build();

            if(temp.getBoardImgEntities() != null) {
                for (BoardImgEntity temp2 : temp.getBoardImgEntities()) {
                    //System.out.println("aaaaaa ; " +temp2.getBimg());
                    boardDTO.getBfile().add( temp2.getBimg() );
                }
            }
            result.add(boardDTO);

        }
        return result;
    }

    //자유게시판 쓰기
    public int FreeBoardWrite(BoardDTO boardDTO, List<MultipartFile> files) {
        //멤버 엔티티 넣어주기
        //1. 멤버 넘버로 멤버 엔티티 찾기(세션에서)
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginDTO");
        System.out.println(memberDTO);
        if(memberDTO == null){return 2;}
        int mno = memberDTO.getMno();
        System.out.println(mno);
        Optional<MemberEntity> mEntityOpt = memberRepository.findById(mno);
        System.out.println(mEntityOpt);
        Optional<CategoryEntity> cEntityOpt = categoryRepository.findById(4);
        MemberEntity memberEntity = mEntityOpt.get();
        CategoryEntity categoryEntity = cEntityOpt.get();
        //2. 우선, 게시판 엔티티에 보드 엔티티, 카테고리 엔티티 넣어주기
        BoardEntity boardEntity = boardDTO.toentity();
        boardEntity.setCategoryEntity(categoryEntity);
        boardEntity.setMemberEntity(memberEntity);


        //3. 카테고리 엔티티에 게시판 엔티티 넣어주기 (모르겠다...)



        //System.out.println(boardEntity);

        // 4. 멤버 엔티티에도 게시판 엔티티 넣어줘야됨
        //게시판 넘버로 게시판 엔티티 객체화
        int bno = boardRepository.save(boardEntity).getBno();
        BoardEntity savedBoardEntity = boardRepository.findById(bno).get();
        memberEntity.getBoardEntities().add(savedBoardEntity);

        //5. 첨부파일 첨부파일 db는 어쩌죠
        String uuidfile = null;

        if(files.size() != 0){
            for(MultipartFile temp : files){
                UUID uuid = UUID.randomUUID();
                uuidfile = uuid.toString()+"_"+temp.getOriginalFilename().replaceAll("_","-");
                //String filepath = "C:\\Users\\JHD\\IdeaProjects\\Spring_ZooZoo\\src\\main\\resources\\static\\IMG\\Board\\FreeBoardIMG\\"+uuidfile;
                //String filepath = "C:\\Users\\504\\IdeaProjects\\Spring_ZooZoo\\src\\main\\resources\\static\\IMG\\Board\\FreeBoardIMG\\"+uuidfile;
                String filepath = "C:\\Users\\505\\IdeaProjects\\Spring_ZooZoo\\src\\main\\resources\\static\\IMG\\Board\\FreeBoardIMG\\"+uuidfile;
                try {
                    temp.transferTo(new File(filepath));
                }catch(Exception e) {
                    System.out.println("파일 저장 실패함" + e);
                }

                BoardImgDTO boardImgDTO = BoardImgDTO.builder()
                        .bimg(uuidfile)
                        .bno(bno)
                        .build();
                BoardImgEntity boardImgEntity = boardImgDTO.toBoardImgEntity();
                boardImgEntity.setBoardEntity(savedBoardEntity);
                boardImgEntity.setCategoryEntity2(cEntityOpt.get());
                savedBoardEntity.setBoardImgEntities(boardImgEntity.getBoardEntity().getBoardImgEntities());
                int bimgno = bimgRepository.save(boardImgEntity).getBimgno();
            }
        }
        return 1;
    }


}
