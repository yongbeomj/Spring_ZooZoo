package ZooZoo.Domain.Entity.Category;

import ZooZoo.Domain.Entity.Board.BoardEntity;
import ZooZoo.Domain.Entity.Board.BoardImgEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category")
@ToString (exclude={"boardEntities","categoryEntity2"})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {

    @Id
    private int cano;
    private String caname;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    private List<BoardEntity> boardEntities = new ArrayList<>();

    @OneToMany(mappedBy = "categoryEntity2", cascade = CascadeType.ALL)
    private List<BoardImgEntity> boardImgEntities = new ArrayList<>();

}