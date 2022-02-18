package ZooZoo.Service.Member;


import ZooZoo.Domain.DTO.Member.CMMemberDTO;
import ZooZoo.Domain.DTO.Member.MemberDTO;
import ZooZoo.Domain.Entity.Company.CompanyEntity;
import ZooZoo.Domain.Entity.Company.CompanyRepository;
import ZooZoo.Domain.Entity.Member.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CompanyMemberService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    HttpServletRequest request;
    // 기업 회원가입
    public boolean CompanySignUp(CMMemberDTO cmmemberDTO) {
        CompanyEntity companyEntity = CompanyEntity.builder()
                .cmcompanyno(cmmemberDTO.getCmcompanyno())
                .cmname(cmmemberDTO.getCmname())
                .cmaddress(cmmemberDTO.getCmaddress())
                .cmphone(cmmemberDTO.getCmphone())
                .cmemail(cmmemberDTO.getCmemail())
                .cmpw(cmmemberDTO.getCmpw())
                .build();
        companyRepository.save(companyEntity);
        return true;
    }

    // 기업 로그인
    public boolean CompanyLogin(String cmid, String cmpw){
        List<CompanyEntity> cmmemberEntityList = companyRepository.findAll();
        for (CompanyEntity companyEntity : cmmemberEntityList) {
            if (companyEntity.getCmcompanyno().equals(cmid) && companyEntity.getCmpw().equals(cmpw)) {
                CMMemberDTO cmMemberDTO = CMMemberDTO.builder().cmcompanyno(companyEntity.getCmcompanyno()).cmpw(companyEntity.getCmpw()).cmno(companyEntity.getCmmo()).build();
                HttpSession session = request.getSession();
                session.setAttribute("CMloginDTO", cmMemberDTO);
                return true;
            }
        }
        return false;
    }

    // 기업 아이디 찾기
    public String FindId(String memail, String mname) {
        List<CompanyEntity> memberEntities = companyRepository.findAll();
        for (CompanyEntity temp : memberEntities) {
            if (temp.getCmemail() != null && temp.getCmname() != null && temp.getCmemail().equals(memail) && temp.getCmname().equals(mname)) {
                return temp.getCmcompanyno();
            }
        }
        return null;
    }

    // 기업 비밀번호 찾기
    public String Findpw(String cmno, String cmemail) {
        List<CompanyEntity> memberEntities = companyRepository.findAll();
        for (CompanyEntity memberEntity : memberEntities) {
            if (memberEntity.getCmcompanyno().equals(cmno) && memberEntity.getCmemail().equals(cmemail)) {
                return memberEntity.getCmpw();
            }
        }
        return null;
    }
}
