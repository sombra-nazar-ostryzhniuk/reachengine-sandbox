package reachengine.sandbox.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import reachengine.sandbox.exception.BadRequest400Exception;
import reachengine.sandbox.exception.Conflict409Exception;
import reachengine.sandbox.exception.NotFound404Exception;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriesService {

    private List<Category> allCategoriesList;

    @PostConstruct
    public void postConstruct() {
        this.allCategoriesList = prepareAllCategories();
        System.out.println("Post construct");
    }

    public List<Category> getAllCategories() {
        return this.allCategoriesList;
    }

    public Category getCategoryById(final Long id) {
        return findCategoryById(id);
    }

    public Category createNewCategory(final CategoryRequest category) {
        validateCategoryFieldsForNull(category);
        if (this.allCategoriesList.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(category.getName()))) {
            throw new Conflict409Exception("Category with such name already exists");
        }
        final Category newCategory = new Category(category);
        newCategory.setId(getIdForNewCategory());
        this.allCategoriesList.add(newCategory);
        return newCategory;
    }

    public Category updateCategory(Long id, CategoryRequest category) {
        final Category categoryById = findCategoryById(id);
        validateCategoryFieldsForNull(category);
        if (this.allCategoriesList.stream()
                .anyMatch(c -> !c.getId().equals(id) && c.getName().equalsIgnoreCase(category.getName()))) {
            throw new Conflict409Exception("Another category with such name already exists");
        }
        categoryById.setDateUpdated(ZonedDateTime.now(ZoneOffset.UTC));
        categoryById.setName(category.getName());
        categoryById.setRoles(category.getRoles());
        categoryById.setDescription(category.getDescription());
        return categoryById;
    }

    public void deleteCategory(final Long id) {
        final Category category = findCategoryById(id);
        this.allCategoriesList.remove(category);
    }

    private List<Category> prepareAllCategories() {
        Category[] categories;
        try {
            try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("AllCategories.json")) {
                categories = new ObjectMapper().readValue(is, Category[].class);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        //TODO check/test for concurrency issues
        return Arrays.stream(categories)
                .peek(c -> {
                    final ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
                    c.setDateCreated(now);
                    c.setDateUpdated(now);
                })
                .collect(Collectors.toList());
    }

    private Category findCategoryById(final Long id) {
        return this.allCategoriesList.stream()
                .filter(c -> c.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NotFound404Exception(String.format("Category with id %s not found", id)));
    }

    private long getIdForNewCategory() {
        //TODO check/test for concurrency issues
        final long maxId = this.allCategoriesList.stream()
                .mapToLong(Category::getId)
                .max()
                .orElseThrow(IllegalStateException::new);
        return maxId + 1;
    }

    private void validateCategoryFieldsForNull(final CategoryRequest category) {
        if (category.getName() == null) {
            throw new BadRequest400Exception("category.name can not be null");
        } else if (category.getRoles() == null || category.getRoles().isEmpty()) {
            throw new BadRequest400Exception("category.roles can not be null or empty");
        } else if (category.getDescription() == null) {
            throw new BadRequest400Exception("category.description can not be null");
        }
    }

}
