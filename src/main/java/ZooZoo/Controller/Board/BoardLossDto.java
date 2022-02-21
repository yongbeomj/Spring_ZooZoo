package ZooZoo.Controller.Board;

import ZooZoo.Domain.DTO.Board.BoardDTO;
import lombok.*;

import java.util.Comparator;
import java.util.stream.Collectors;


@ToString
@Builder
@Getter
public class BoardLossDto {
    String date;
    int datecount;

    public BoardLossDto() {

    }

    public BoardLossDto(String date, int datecount) {
        this.date = date;
        this.datecount = datecount;
    }

    public void setDatecount(int datecount) {
        this.datecount = datecount;
    }


}
