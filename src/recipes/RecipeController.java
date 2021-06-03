package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RecipeController {
    Recipe recipe = null;

    @GetMapping("/api/recipe")
    ResponseEntity<Recipe> getRecipe() {
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping("/api/recipe")
    ResponseEntity<Recipe> PostRecipe(@RequestBody Recipe recipe) {
        this.recipe = recipe;
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
