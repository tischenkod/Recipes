package recipes.busyness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.persistance.Category;
import recipes.persistance.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    Category getCategory(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        return category.orElseGet(() -> categoryRepository.save(new Category(name)));
    }
}
