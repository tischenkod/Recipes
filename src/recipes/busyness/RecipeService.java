package recipes.busyness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import recipes.persistance.RecipeRepository;

import java.util.Optional;

@Service
public class RecipeService{
    @Autowired
    RecipeRepository recipeRepository;

    public boolean deleteById(Long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe addRecipe(Recipe recipe) {
        if (recipe.getDirections() == null ||
                recipe.getDirections().length == 0 ||
                recipe.getIngredients() == null ||
                recipe.getIngredients().length == 0) {
            return null;
        }
        return recipeRepository.save(recipe);
    }
}
