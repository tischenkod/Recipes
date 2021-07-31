package recipes.busyness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.persistance.Category;
import recipes.persistance.CategoryRepository;
import recipes.persistance.RecipeRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class RecipeService{
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    CategoryRepository categoryRepository;

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
        validateRecipe(recipe);
        recipe.setChanged();
        recipe.category = getCategory(recipe.category.getName());
        return recipeRepository.save(recipe);
    }

    private void validateRecipe(Recipe recipe) {
        if (recipe.name == null ||
                recipe.name.isBlank() ||
                recipe.description == null ||
                recipe.description.isBlank() ||
                recipe.getDirections() == null ||
                recipe.getDirections().size() == 0 ||
                recipe.getIngredients() == null ||
                recipe.getIngredients().size() == 0 ||
                recipe.category == null ||
                recipe.category.getName().isBlank()) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid recipe");
        }
    }

    public void updateRecipe(Long id, Recipe recipe) {
        validateRecipe(recipe);
        Optional<Recipe> existingRecipe = recipeRepository.findById(id);
        if (existingRecipe.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Recipe not found");
        }
        recipe.id = id;
        recipe.setChanged();
        recipe.category = getCategory(recipe.category.getName());
        recipeRepository.save(recipe);
    }

    private Category getCategory(String categoryName) {
        Optional<Category> existedCategory = categoryRepository.findByName(categoryName);
        return existedCategory.orElseGet(() -> {
            Category category = new Category(categoryName);
            categoryRepository.save(category);
            return category;
        });
    }

    public List<Recipe> findByCategory(String categoryName) {
        List<Category> categories = categoryRepository.findAllByNameIgnoreCase(categoryName);
        if (categories.isEmpty())
            return new LinkedList<>();
//        List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();
//        return recipes;
        return recipeRepository.findAllByCategoryInOrderByDateDesc(categories);
    }

    public List<Recipe> findByName(String name) {
//        List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();
//        return recipes;
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
