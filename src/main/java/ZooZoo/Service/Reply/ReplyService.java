package ZooZoo.Service.Reply;

import ZooZoo.Domain.DTO.Member.MemberDTO;
import ZooZoo.Domain.Entity.Board.BoardEntity;
import ZooZoo.Domain.Entity.Board.BoardRepository;
import ZooZoo.Domain.Entity.Member.MemberEntity;
import ZooZoo.Domain.Entity.Member.MemberRepository;
import ZooZoo.Domain.Entity.Reply.ReplyEntity;
import ZooZoo.Domain.Entity.Reply.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    HttpServletRequest request;

    public int writeReply(int bno, int cano, String frcontents) {

        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("loginDTO");
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberDTO.getMno());
        if(memberDTO == null || memberDTO.equals("")) return 1;
        if(frcontents == null || frcontents.equals("")) return 2;
        Optional<BoardEntity> boardEntity = boardRepository.findById(bno);
        ReplyEntity replyEntity = ReplyEntity.builder()
                .boardEntity2(boardEntity.get())
                .rcontents(frcontents)
                .memberEntity2(memberEntity.get())
                .categoryEntity2(boardEntity.get().getCategoryEntity())
                .build();
        replyRepository.save(replyEntity);
        boardEntity.get().getReplyEntities().add(replyEntity);
        return 3;
    }
    //댓글 리스트 출력
    public List<ReplyEntity> getAllReplys(int bno, int cano) {
        List<ReplyEntity>replyEntity = new ArrayList<>();
        try{
            replyEntity = replyRepository.findFreeReply(bno, cano);
        }catch (Exception e) {

        }
        return replyEntity;
    }

    //댓글 삭제
    public boolean deleteReply(int rno) {
        Optional<ReplyEntity> replyEntity = replyRepository.findById(rno);
        if(replyEntity.get() != null){
            replyRepository.delete(replyEntity.get());
            return true;
        }else{
            return false;
        }
    }

    // 댓글 수정
    @Transactional
    public boolean updateReply(int rno, String newfreecontents) {
        // 댓글 가져오기
        ReplyEntity replyEntity = replyRepository.findById(rno).get();
        System.out.println("bno : " + rno);
        System.out.println("내용 : " + newfreecontents);
        // 내용 수정
        replyEntity.setRcontents(newfreecontents);
        return true;
    }

    // 댓글 수정
    @Transactional
    public boolean replyupdate(int bno, String newcontents) {
        // 댓글 가져오기
        ReplyEntity replyEntity = replyRepository.findById(bno).get();
        System.out.println("bno : " + bno);
        System.out.println("내용 : " + newcontents);
        System.out.println(replyRepository.findById(bno).get());
        // 내용 수정
        replyEntity.setRcontents(newcontents);
        return true;
    }
}
