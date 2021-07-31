package recipes.persistance;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import recipes.busyness.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long>{
    boolean existsById(Long id);

    void deleteById(Long id);

    List<Recipe> findAllByCategoryInOrderByDateDesc(List<Category> category);

    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
