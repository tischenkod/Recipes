package recipes.busyness;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import recipes.persistance.Category;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;

    @NotNull
    String name;

    @NotNull
    String description;

    @ManyToOne
    @NotNull
    Category category;

    @ElementCollection
    List<String> ingredients;

    @ElementCollection
    List<String> directions;

    LocalDateTime date;

    public Recipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public String getCategory() {
        return category.getName();
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setChanged() {
        this.date = LocalDateTime.now();
    }

   }
