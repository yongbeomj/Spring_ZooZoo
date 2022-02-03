package ZooZoo.Domain.Entity.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardImgRepository extends JpaRepository<BoardImgEntity,Integer> {
    //이미지 빼오기
    @Query(nativeQuery = true, value = "select * from boardimg where cano = :categoryNumber")
    List<BoardImgEntity> findAllImg(@Param("categoryNumber") int categoryNumber);
}