package ZooZoo.Domain.Entity.Board;

import ZooZoo.Domain.Entity.Category.CategoryEntity;
import ZooZoo.Domain.Entity.DateEntity;
import ZooZoo.Domain.Entity.Member.MemberEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="board")
@ToString (exclude = {"memberEntity","categoryEntity" ,"boardImgEntities"})
public class BoardEntity extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;

    @Column (name="btitle")
    private String btitle;
    @Column (name="bcontents", columnDefinition = "LONGTEXT")
    private String bcontents;
    @Column (name="bview")
    private int bview;

    //회원 번호 fk
    @ManyToOne
    @JoinColumn(name="mno")
    private MemberEntity memberEntity;

    //카테고리 fk??????
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cano")
    private CategoryEntity categoryEntity;

    //게시판 이미지
    @OneToMany(mappedBy="boardEntity", cascade = CascadeType.ALL)
    private List<BoardImgEntity> boardImgEntities = new ArrayList<>();
}