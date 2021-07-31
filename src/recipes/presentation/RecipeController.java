package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.busyness.CategoryService;
import recipes.busyness.Id;
import recipes.busyness.Recipe;
import recipes.busyness.RecipeService;
import recipes.persistance.Category;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/api/recipe/{id}")
    ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.findById(id);
        return recipe.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/api/recipe/{id}")
    ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        if (recipeService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/recipe/new")
    ResponseEntity<Id> PostRecipe(@RequestBody Recipe recipe) {
        recipe = recipeService.addRecipe(recipe);
        return recipe == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(new Id(recipe.getId()), HttpStatus.OK);
    }

    @PutMapping("/api/recipe/{id}")
    ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        recipeService.updateRecipe(id, recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/recipe/search")
    ResponseEntity<List<Recipe>> searchRecipe(@RequestParam(required = false) String category, @RequestParam(required = false) String name) {
        List<Recipe> recipes;
        if (category != null) {
            if (name != null)
                throw new ResponseStatusException(BAD_REQUEST, "Invalid search request");
            recipes = recipeService.findByCategory(category);
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        }

        if (name != null) {
            recipes = recipeService.findByName(name);
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        }

        throw new ResponseStatusException(BAD_REQUEST, "Invalid search request");
    }

}
