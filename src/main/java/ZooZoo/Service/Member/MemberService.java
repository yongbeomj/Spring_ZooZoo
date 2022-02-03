package ZooZoo.Service.Member;

import ZooZoo.Domain.DTO.Member.MemberDTO;
import ZooZoo.Domain.Entity.Member.MemberEntity;
import ZooZoo.Domain.Entity.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    // 회원가입
    public boolean SignUp(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.builder()
                .mid(memberDTO.getMid())
                .mpw(memberDTO.getMpw())
                .mname(memberDTO.getMname())
                .memail(memberDTO.getMemail())
                .mbirth(memberDTO.getMbirth())
                .maddress(memberDTO.getMaddress())
                .build();
        memberRepository.save(memberEntity);
        return true;
    }

    // 아이디 중복확인
    public boolean Checkid(String mid) {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        for(MemberEntity memberEntity : memberEntityList) {
            if(memberEntity != null && memberEntity.getMid().equals(mid)) {
                return true;
            }
        }
        return false;
    }

    @Autowired
    HttpServletRequest request;

    // 로그인
    public boolean Login(String mid, String mpw) {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        for(MemberEntity memberEntity : memberEntityList) {
            if(memberEntity.getMid().equals(mid) && memberEntity.getMpw().equals(mpw)) {
                MemberDTO memberDTO = MemberDTO.builder().mid(memberEntity.getMid()).mpw(memberEntity.getMpw()).mno(memberEntity.getMno()).build();
                HttpSession session = request.getSession();
                session.setAttribute("loginDTO", memberDTO);
                return true;
            }
        }
        return false;
    }

    // 회원 아이디 찾기
    public String FindId(String memail, String mname) {
        List<MemberEntity> memberEntities = memberRepository.findAll();
        for(MemberEntity temp : memberEntities){
            if(temp.getMemail() != null && temp.getMname() != null && temp.getMemail().equals(memail) && temp.getMname().equals(mname)){
                return temp.getMid();
            }
        }
        return null;
    }

    // 회원 비밀번호 찾기
    public String findpw(String mid, String memail) {
        List<MemberEntity> memberEntities = memberRepository.findAll();
        for (MemberEntity memberEntity : memberEntities) {
            if (memberEntity.getMid().equals(mid) && memberEntity.getMemail().equals(memail)) {
                return memberEntity.getMpw();
            }
        }
        return null;
    }
}
