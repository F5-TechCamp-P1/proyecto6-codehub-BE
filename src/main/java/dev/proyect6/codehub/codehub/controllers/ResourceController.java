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

import dev.proyect6.codehub.codehub.models.Resource;
import dev.proyect6.codehub.codehub.services.AuthService;
import dev.proyect6.codehub.codehub.services.ResourceService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api-endpoint}/resources")
@Validated
public class ResourceController {

    private ResourceService resourceService;
    private AuthService authService;

    public ResourceController(ResourceService resourceService, AuthService authService) {
        this.resourceService = resourceService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<?> getAllResources(@RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        Boolean userExist = false;
        if (apiKey != null) {
            userExist = authService.filterAuth(apiKey);
        }
        if (!userExist) {
            return ResponseEntity.status(401).body("Apikey incorrecta");
        }

        return ResponseEntity.ok(resourceService.getAllResources());
    }

    /*@GetMapping
    public List<Resource> getAllResources() {
        return resourceService.getAllResources();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long id) {
        return resourceService.getResourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createResource(@RequestBody Resource resource) {
        try {
            Resource savedResource = resourceService.saveResource(resource);
            return ResponseEntity.ok(savedResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateResource(@PathVariable Long id, @Valid @RequestBody Resource resource) {
        try {
            Resource updatedResource = resourceService.updateResource(id, resource);
            return ResponseEntity.ok(updatedResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
