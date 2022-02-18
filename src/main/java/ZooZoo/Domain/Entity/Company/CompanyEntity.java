package ZooZoo.Domain.Entity.Company;
import ZooZoo.Domain.Entity.Board.BoardEntity;
import ZooZoo.Domain.Entity.BoardLike.BoardLikeEntity;
import ZooZoo.Domain.Entity.DateEntity;
import ZooZoo.Domain.Entity.Reply.ReplyEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commember")
@Getter
@Setter
@ToString(exclude={"boardEntities","replyEntities"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cmmo;
    @Column(name = "cmcompanyno")
    private String cmcompanyno;
    @Column(name = "cmname")
    private String cmname;
    @Column(name = "cmaddress")
    private String cmaddress;
    @Column(name = "cmphone")
    private String cmphone;
    @Column(name = "cmemail")
    private String cmemail;
    @Column(name = "cmpw")
    private String cmpw;

    //게시판 엔티티와 양방향
    @OneToMany(mappedBy = "memberEntity")
    private List<BoardEntity> boardEntities = new ArrayList<>();

    //댓글 엔티티 매핑
    @OneToMany(mappedBy = "memberEntity2", cascade = CascadeType.ALL)
    private List<ReplyEntity> replyEntities = new ArrayList<>();

    //게시판 추천
    @OneToMany(mappedBy="memberBLikeEntity", cascade = CascadeType.ALL)
    private List<BoardLikeEntity> boardLikeEntities = new ArrayList<>();
}
