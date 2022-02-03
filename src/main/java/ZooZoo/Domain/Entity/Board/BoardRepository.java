package ZooZoo.Domain.Entity.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity,Integer> {
    //게시판 빼오기
    @Query(nativeQuery = true, value = "select * from board where cano = :categoryNumber")
    List<BoardEntity> findAllBoard(@Param("categoryNumber") int categoryNumber);
}
