package ZooZoo.Domain.DTO.Member;

import ZooZoo.Domain.Entity.Company.CompanyEntity;
import ZooZoo.Domain.Entity.Member.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class CMMemberDTO {
    private int cmno;
    private String cmcompanyno;
    private String cmname;
    private String cmaddress;
    private String cmemail;
    private String cmphone;
    private String cmpw;
    private LocalDateTime createdDate;

    public CompanyEntity companyEntity() {
        return CompanyEntity.builder()
                .cmcompanyno(this.cmcompanyno)
                .cmname(this.cmname)
                .cmaddress(this.cmaddress)
                .cmphone(this.cmphone)
                .cmemail(this.cmemail)
                .cmpw(this.cmpw)
                .build();
    }
}
