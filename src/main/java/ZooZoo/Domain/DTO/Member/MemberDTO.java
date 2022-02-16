package ZooZoo.Domain.DTO.Member;

import ZooZoo.Domain.Entity.Member.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class MemberDTO {
    private int mno;
    private String mid;
    private String mpw;
    private String mname;
    private String memail;
    private String mbirth;
    private String maddress;
    private LocalDateTime createdDate;

    public MemberEntity memberEntity() {
        return MemberEntity.builder()
                .mid(this.mid)
                .mpw(this.mpw)
                .mname(this.mname)
                .memail(this.memail)
                .mbirth(this.mbirth)
                .maddress(this.maddress)
                .build();
    }
}