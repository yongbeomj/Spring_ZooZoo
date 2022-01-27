package ZooZoo.Domain.DTO.Board;

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

    //카테고리는??
}
