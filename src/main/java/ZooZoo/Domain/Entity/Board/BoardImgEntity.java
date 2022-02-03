package ZooZoo.Domain.Entity.Board;

import ZooZoo.Domain.DTO.Board.BoardImgDTO;
import ZooZoo.Domain.Entity.Category.CategoryEntity;
import ZooZoo.Domain.Entity.DateEntity;
import lombok.*;

import javax.persistence.*;
import java.util.stream.DoubleStream;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="boardimg")
@ToString (exclude = {"boardEntity", "categoryEntity2"})
public class BoardImgEntity extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bimgno")
    private int bimgno;

    @Column(name="bimg")
    private String bimg;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bno")
    private BoardEntity boardEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cano")
    private CategoryEntity categoryEntity2;

}