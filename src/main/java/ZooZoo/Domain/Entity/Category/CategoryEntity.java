package ZooZoo.Domain.Entity.Category;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="category")
@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cano;
    private String caname;
}
