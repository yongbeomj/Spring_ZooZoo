package ZooZoo.Domain.DTO.Board;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {
    private int rno;
    private String rcontents;
    private String bupdateDate;
    private LocalDateTime rcreatedDate;
    private String rwriter;
    private Integer rindex;

}
