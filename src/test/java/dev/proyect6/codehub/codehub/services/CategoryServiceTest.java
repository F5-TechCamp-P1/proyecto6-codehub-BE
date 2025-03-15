package dev.proyect6.codehub.codehub.services;

import dev.proyect6.codehub.codehub.models.Category;
import dev.proyect6.codehub.codehub.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        Category category1 = new Category();
        category1.setName("Programming");

        Category category2 = new Category();
        category2.setName("Databases");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Category> categories = categoryService.getAllCategories();

        assertThat(categories, hasSize(2));
        assertThat(categories.get(0).getName(), is("Programming"));
        assertThat(categories.get(1).getName(), is("Databases"));
    }

    @Test
    void testGetCategoryById() {
        Category category = new Category();
        category.setName("Web Development");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> foundCategory = categoryService.getCategoryById(1L);

        assertThat(foundCategory.isPresent(), is(true));
        assertThat(foundCategory.get().getName(), is("Web Development"));
    }

    @Test
    void testSaveCategory() {
        Category category = new Category();
        category.setName("Machine Learning");

        when(categoryRepository.save(category)).thenReturn(category);

        Category savedCategory = categoryService.saveCategory(category);

        assertThat(savedCategory.getName(), is("Machine Learning"));
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testDeleteCategory() {
        Long categoryId = 1L;

        doNothing().when(categoryRepository).deleteById(categoryId);

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testUpdateCategory() {
        Category existingCategory = new Category();
        existingCategory.setName("AI");

        Category updatedCategory = new Category();
        updatedCategory.setName("Artificial Intelligence");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any())).thenReturn(updatedCategory);

        Category result = categoryService.updateCategory(1L, updatedCategory);

        assertThat(result.getName(), is("Artificial Intelligence"));
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    void testUpdateCategoryNotFound() {
        Long nonExistentId = 99L;
        Category updatedCategory = new Category();
        updatedCategory.setName("New Category");

        when(categoryRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.updateCategory(nonExistentId, updatedCategory);
        });

        assertThat(exception.getMessage(), is("Category not found with id: " + nonExistentId));
    }
}