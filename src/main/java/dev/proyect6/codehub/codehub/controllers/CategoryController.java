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

import dev.proyect6.codehub.codehub.config.AuthFilter;
import dev.proyect6.codehub.codehub.models.Category;
import dev.proyect6.codehub.codehub.services.CategoryService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("${api-endpoint}/categories")
@Validated
public class CategoryController {

    private CategoryService categoryService;
    private AuthFilter authFilter;

    public CategoryController(CategoryService categoryService, AuthFilter authFilter) {
        this.categoryService = categoryService;
        this.authFilter = authFilter;
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories(@RequestHeader(name = "X-API-KEY", required = false) String apiKey) {
        if (!authFilter.preHandle(apiKey)) return ResponseEntity.status(401).body("Apikey incorrecta");
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id, @RequestHeader(name = "X-API-KEY", required = false) String apiKey) {
        if (!authFilter.preHandle(apiKey)) return ResponseEntity.status(401).body("Apikey incorrecta");
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category, @RequestHeader(name = "X-API-KEY", required = false) String apiKey) {
        if (!authFilter.preHandle(apiKey)) return ResponseEntity.status(401).body("Apikey incorrecta");
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id, @RequestHeader(name = "X-API-KEY", required = false) String apiKey) {
        if (!authFilter.preHandle(apiKey)) return ResponseEntity.status(401).body("Apikey incorrecta");
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Categoría borrada exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody Category updatedCategory, @RequestHeader(name = "X-API-KEY", required = false) String apiKey) {
        if (!authFilter.preHandle(apiKey)) return ResponseEntity.status(401).body("Apikey incorrecta");
        try {
            Category updated = categoryService.updateCategory(id, updatedCategory);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}