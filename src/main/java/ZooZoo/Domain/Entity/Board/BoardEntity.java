package ZooZoo.Domain.Entity.Board;

import ZooZoo.Domain.Entity.DateEntity;
import ZooZoo.Domain.Entity.Member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="board")
public class BoardEntity extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;

    @Column
    private String btitle;
    @Column
    private String bcontents;
    @Column
    private int bview;

    //회원 번호 fk
    @ManyToOne
    @JoinColumn(name="mno")
    private MemberEntity memberEntity;

    //카테고리 fk??????

}
