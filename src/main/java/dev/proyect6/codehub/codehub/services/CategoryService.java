package dev.proyect6.codehub.codehub.services;
import dev.proyect6.codehub.codehub.repositories.CategoryRepository;
import dev.proyect6.codehub.codehub.models.Category;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ResourceService resourceService;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException("La categoría ya existe");
        }
        return category;
    }

    public void deleteCategory(Long id) {
        resourceService.deleteResourcesByCategory(id);
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
            .map(category -> {
                category.setName(updatedCategory.getName());
                return categoryRepository.save(category);
            })
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
    }
}
