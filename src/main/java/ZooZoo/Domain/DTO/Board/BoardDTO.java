package ZooZoo.Domain.DTO.Board;

import ZooZoo.Domain.DTO.Category.CategoryDTO;
import ZooZoo.Domain.Entity.Board.BoardEntity;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private int bno;
    private String btitle;
    private String bcontents;
    private int bview;
    private String bcreateddate;
    private String bfile;
    //카테고리는??
    CategoryDTO categoryDTO = new CategoryDTO();

    public BoardEntity toentity(){
        return BoardEntity.builder()
                .btitle(this.btitle)
                .bcontents(this.bcontents)
                .bview(this.bview)
                .build();
    }

}
