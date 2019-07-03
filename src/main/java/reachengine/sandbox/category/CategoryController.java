package reachengine.sandbox.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("reachengine/sandbox/api/categories")
public class CategoryController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping
    public Collection<Category> listCategories() {
        return categoriesService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable(name = "id") final Long id) {
        return categoriesService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCategory(@PathVariable(name = "id") final Long id) {
        categoriesService.deleteCategory(id);
    }

    @PostMapping
    public Category createCategory(@RequestBody final CategoryRequest category) {
        return categoriesService.createNewCategory(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(
            @PathVariable(name = "id") final Long id,
            @RequestBody final CategoryRequest category) {
        return categoriesService.updateCategory(id, category);
    }
}
