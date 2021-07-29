package recipes.persistance;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import recipes.busyness.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long>{
    boolean existsById(Integer id);

    void deleteById(Integer id);

    Recipe findById(Integer id);
}
