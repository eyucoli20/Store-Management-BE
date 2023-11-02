package StoreManagement.itemManagement.category;

import StoreManagement.exceptions.customExceptions.ResourceAlreadyExistsException;
import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.itemManagement.category.dto.CategoryReq;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
       return categoryRepository.findAll(Sort.by(Sort.Order.asc("categoryName")));
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Category createCategory(CategoryReq categoryReq) {
        String categoryName = categoryReq.getCategoryName().toUpperCase();
        if (categoryRepository.existsByCategoryNameIgnoreCase(categoryName))
            throw new ResourceAlreadyExistsException("Category name is already taken");

        Category category = new Category();
        category.setCategoryName(categoryName);
        return categoryRepository.save(category);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Category updateCategory(Integer categoryId, CategoryReq categoryReq) {
        Category category = getCategoryById(categoryId);
        String newCategoryName = categoryReq.getCategoryName();
        // update only if provided Category name is different form existing one
        if (newCategoryName != null && !category.getCategoryName().equalsIgnoreCase(newCategoryName)) {
            // Check if the new Category name is already taken
            if (categoryRepository.existsByCategoryNameIgnoreCase((newCategoryName)))
                throw new ResourceAlreadyExistsException("Category name is already taken");

            category.setCategoryName(newCategoryName.toUpperCase());
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category utilSaveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        if (!categoryRepository.existsById(categoryId))
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);

        categoryRepository.deleteById(categoryId);
    }
}
