package ZooZoo.Domain.DTO.Board;

import ZooZoo.Domain.Entity.Board.BoardImgEntity;
import ZooZoo.Domain.Entity.Category.CategoryEntity;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardImgDTO {
    private int bimgno;
    private int bno;
    private String bimg;
    private int cano;

    public BoardImgEntity toBoardImgEntity(){
        return BoardImgEntity.builder()
                .bimg(this.bimg)
                .build();
    }

}