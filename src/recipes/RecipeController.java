package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipeController {
    List<Recipe> recipes = new ArrayList<>();

    @GetMapping("/api/recipe/{id}")
    ResponseEntity<Recipe> getRecipe(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(recipes.get(id), HttpStatus.OK);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/recipe/new")
    ResponseEntity<Id> PostRecipe(@RequestBody Recipe recipe) {
        this.recipes.add(recipe);
        return new ResponseEntity<>(new Id(recipes.indexOf(recipe)), HttpStatus.OK);
    }

}
