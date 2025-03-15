package dev.proyect6.codehub.codehub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.proyect6.codehub.codehub.dto.ResourceDTO;
import dev.proyect6.codehub.codehub.models.Category;
import dev.proyect6.codehub.codehub.models.Resource;
import dev.proyect6.codehub.codehub.repositories.CategoryRepository;
import dev.proyect6.codehub.codehub.repositories.ResourceRepository;

@Service
public class ResourceService {

    private ResourceRepository resourceRepository;
    private CategoryRepository categoryRepository;

    public ResourceService(ResourceRepository resourceRepository, CategoryRepository categoryRepository) {
        this.resourceRepository = resourceRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public Resource saveResource(ResourceDTO resourceDTO) {
        Resource resource = validateResource(resourceDTO);
        
        return resourceRepository.save(resource);
    }

    public Resource updateResource(Long id, ResourceDTO resourceDTO) {
        Resource newResource = validateResource(resourceDTO);

        return resourceRepository.findById(id)
                .map(existingResource -> {

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

    private Resource validateResource(ResourceDTO resourceDTO) {        
        Category category = validateAndAssignCategory(resourceDTO.getCategoryId());
        
        Resource resource = new Resource();
        resource.setTitle(resourceDTO.getTitle());
        resource.setFileUrl(resourceDTO.getFileUrl());
        resource.setCategory(category);
        if (resource.getTitle() == null || resource.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (resource.getFileUrl() == null || resource.getFileUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("La URL del archivo no puede estar vacía");
        }     
        
        return resource;
    }

    private Category validateAndAssignCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría con ID " + categoryId + " no encontrada"));
        return category;
    }
}