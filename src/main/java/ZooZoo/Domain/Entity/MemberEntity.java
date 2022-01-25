package ZooZoo.Domain.Entity;

import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "member")
@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;
    @Column(name = "mid")
    private String mid;
    @Column(name = "mpw")
    private String mpw;
    @Column(name = "mname")
    private String mname;
    @Column(name = "memail")
    private String memail;
    @Column(name = "mbirth")
    private String mbirth;
    @Column(name = "maddress")
    private String maddress;
}
