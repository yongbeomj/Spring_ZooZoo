package ZooZoo.Domain.DTO.Category;

import ZooZoo.Domain.Entity.Category.CategoryEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class CategoryDTO {
    private int cano;
    private String caname;

    public CategoryEntity toCategoryEntity(){
        return CategoryEntity.builder()
                .caname(this.caname)
                .build();
    }

}