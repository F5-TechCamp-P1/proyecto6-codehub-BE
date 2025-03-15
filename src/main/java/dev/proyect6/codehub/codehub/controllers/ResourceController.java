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
import dev.proyect6.codehub.codehub.dto.ResourceDTO;
import dev.proyect6.codehub.codehub.models.Resource;
import dev.proyect6.codehub.codehub.services.ResourceService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api-endpoint}/resources")
@Validated
public class ResourceController {

    private ResourceService resourceService;
    private AuthFilter authFilter;

    public ResourceController(ResourceService resourceService, AuthFilter authFilter) {
        this.resourceService = resourceService;
        this.authFilter = authFilter;
    }

   

    @GetMapping
    public ResponseEntity<?> getAllResources(@RequestHeader(name = "X-API-KEY", required = false) String apiKey) {
        if (!authFilter.preHandle(apiKey)) return ResponseEntity.status(401).body("Apikey incorrecta");
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResourceById(@PathVariable Long id, @RequestHeader(name = "X-API-KEY", required = false) String apiKey) {
        if (!authFilter.preHandle(apiKey)) return ResponseEntity.status(401).body("Apikey incorrecta");
        ResponseEntity<?> response = resourceService.getResourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        return response;
    }

    @PostMapping
    public ResponseEntity<?> createResource(@RequestBody ResourceDTO resourceDTO, @RequestHeader(name = "X-API-KEY", required = false) String apiKey) {
        if (!authFilter.preHandle(apiKey)) return ResponseEntity.status(401).body("Apikey incorrecta");
        try {
            Resource savedResource = resourceService.saveResource(resourceDTO);
            return ResponseEntity.ok(savedResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateResource(@PathVariable Long id, @Valid @RequestBody Resource resource, @RequestHeader(name = "X-API-KEY", required = false) String apiKey) {
        if (!authFilter.preHandle(apiKey)) return ResponseEntity.status(401).body("Apikey incorrecta");
        try {
            Resource updatedResource = resourceService.updateResource(id, resource);
            return ResponseEntity.ok(updatedResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResource(@PathVariable Long id, @RequestHeader(name = "X-API-KEY", required = false) String apiKey) {
        if (!authFilter.preHandle(apiKey)) return ResponseEntity.status(401).body("Apikey incorrecta");
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
