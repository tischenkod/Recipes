package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recipes.busyness.Id;
import recipes.busyness.Recipe;
import recipes.busyness.RecipeService;

import java.util.Optional;

@Controller
public class RecipeController {
    @Autowired
    RecipeService recipeService;

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

}
