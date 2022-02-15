package ZooZoo.Service.Category;

import ZooZoo.Domain.Entity.Category.CategoryEntity;
import ZooZoo.Domain.Entity.Category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategorySerivce {

    @Autowired
    CategoryRepository categoryRepository;
    //카테고리 생성
    public void makeCategory() {
        try{
            CategoryEntity categoryEntity = new CategoryEntity();
            System.out.println(categoryEntity.getCano());
            if(categoryEntity.getCano()==0){
                int [] cano = {1, 2, 3, 4};
                String [] caname = {"유기","분양","병원","자유"};
                for(int i =0; i<4; i++){
                    categoryEntity = CategoryEntity.builder()
                            .cano(cano[i]).caname(caname[i]).build();

                    categoryRepository.save(categoryEntity);
                }
            }else{
                return;
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
