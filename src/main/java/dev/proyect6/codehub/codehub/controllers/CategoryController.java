package dev.proyect6.codehub.codehub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.proyect6.codehub.codehub.models.Category;
import dev.proyect6.codehub.codehub.services.AuthService;
import dev.proyect6.codehub.codehub.services.CategoryService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("${api-endpoint}/categories")
@Validated
public class CategoryController {

    private CategoryService categoryService;
    private AuthService authService;

    public CategoryController(CategoryService categoryService, AuthService authService) {
        this.categoryService = categoryService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories(@RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        Boolean userExist = false;
        if(apiKey!= null){
            userExist = authService.filterAuth(apiKey);
        }
        if (!userExist) {
            return ResponseEntity.status(401).body("Apikey incorrecta");        
        }

        return ResponseEntity.ok(categoryService.getAllCategories());
    }

     @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody Category updatedCategory) {
        try {
            Category updated = categoryService.updateCategory(id, updatedCategory);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
//arreglar el products en category y poner el nombre de la categoria en recursos