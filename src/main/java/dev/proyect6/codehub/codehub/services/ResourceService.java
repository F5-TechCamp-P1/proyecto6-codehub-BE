package dev.proyect6.codehub.codehub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.proyect6.codehub.codehub.models.Category;
import dev.proyect6.codehub.codehub.models.Resource;
import dev.proyect6.codehub.codehub.repositories.CategoryRepository;
import dev.proyect6.codehub.codehub.repositories.ResourceRepository;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public Resource saveResource(Resource resource) {
        validateResource(resource);
        validateAndAssignCategory(resource);
        return resourceRepository.save(resource);
    }

    public Resource updateResource(Long id, Resource newResource) {
        return resourceRepository.findById(id)
                .map(existingResource -> {
                    validateResource(newResource);
                    validateAndAssignCategory(newResource);

                    existingResource.setTitle(newResource.getTitle());
                    existingResource.setFileUrl(newResource.getFileUrl());
                    existingResource.setCategory(newResource.getCategory());

                    return resourceRepository.save(existingResource);
                })
                .orElseThrow(() -> new IllegalArgumentException("Recurso no encontrado"));
    }

    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }

    private void validateResource(Resource resource) {
        if (resource.getTitle() == null || resource.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (resource.getFileUrl() == null || resource.getFileUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("La URL del archivo no puede estar vacía");
        }
    }

    private void validateAndAssignCategory(Resource resource) {
        if (resource.getCategory() != null && resource.getCategory().getId() != null) {
            Long categoryId = resource.getCategory().getId();
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría con ID " + categoryId + " no encontrada"));
            resource.setCategory(category);
        }
    }
}