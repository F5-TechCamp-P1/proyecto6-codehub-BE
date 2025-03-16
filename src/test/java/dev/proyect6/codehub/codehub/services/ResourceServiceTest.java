package dev.proyect6.codehub.codehub.services;

import dev.proyect6.codehub.codehub.dto.ResourceDTO;
import dev.proyect6.codehub.codehub.models.Category;
import dev.proyect6.codehub.codehub.models.Resource;
import dev.proyect6.codehub.codehub.repositories.CategoryRepository;
import dev.proyect6.codehub.codehub.repositories.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Category category = new Category();
        category.setName("Test Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        when(categoryRepository.save(Mockito.any(Category.class))).thenAnswer(invocation -> {
            Category categorySaved = invocation.getArgument(0);
            return categorySaved;
        });
        Resource resource = new Resource();
        resource.setTitle("Test Resource");
        resource.setFileUrl("http://example.com/resource");
        resource.setCategory(category);

        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);
    }

    @Test
    void testGetAllResources() {
        Resource resource1 = new Resource();
        resource1.setTitle("Resource 1");
        resource1.setFileUrl("http://example.com/resource1");

        Resource resource2 = new Resource();
        resource2.setTitle("Resource 2");
        resource2.setFileUrl("http://example.com/resource2");

        when(resourceRepository.findAll()).thenReturn(Arrays.asList(resource1, resource2));

        List<Resource> resources = resourceService.getAllResources();

        assertThat(resources, hasSize(2));
        assertThat(resources.get(0).getTitle(), is("Resource 1"));
        assertThat(resources.get(1).getTitle(), is("Resource 2"));
    }

    @Test
    void testGetResourceById() {
        Resource resource = new Resource();
        resource.setTitle("Resource Test");
        resource.setFileUrl("http://example.com/resource");

        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));

        Optional<Resource> foundResource = resourceService.getResourceById(1L);

        assertThat(foundResource.isPresent(), is(true));
        assertThat(foundResource.get().getTitle(), is("Resource Test"));
    }

    @Test
    void testSaveResource() {
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setTitle("New Resource");
        resourceDTO.setUrl("http://example.com/new-resource");
        resourceDTO.setCategoryId(1L);

        Category category = new Category();
        category.setName("Category 1");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(resourceRepository.save(ArgumentMatchers.<Resource>any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Resource savedResource = resourceService.saveResource(resourceDTO);

        assertThat(savedResource.getTitle(), is("New Resource"));
        assertThat(savedResource.getFileUrl(), is("http://example.com/new-resource"));
        assertThat(savedResource.getCategory(), is(category));
        verify(resourceRepository, times(1)).save(ArgumentMatchers.<Resource>any());

    }

    @Test
    void testUpdateResource() {
        Resource existingResource = new Resource();
        existingResource.setTitle("Old Resource");
        existingResource.setFileUrl("http://example.com/old-resource");

        ResourceDTO updatedResourceDTO = new ResourceDTO();
        updatedResourceDTO.setTitle("Updated Resource");
        updatedResourceDTO.setUrl("http://example.com/updated-resource");
        updatedResourceDTO.setCategoryId(1L);

        Category category = new Category();
        category.setName("Category 1");

        when(resourceRepository.findById(1L)).thenReturn(Optional.of(existingResource));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(resourceRepository.save(ArgumentMatchers.<Resource>any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Resource updatedResource = resourceService.updateResource(1L, updatedResourceDTO);

        assertThat(updatedResource.getTitle(), is("Updated Resource"));
        assertThat(updatedResource.getFileUrl(), is("http://example.com/updated-resource"));
        assertThat(updatedResource.getCategory(), is(category));
        verify(resourceRepository, times(1)).save(existingResource);
    }

    @Test
    void testDeleteResource() {
        Long resourceId = 1L;

        doNothing().when(resourceRepository).deleteById(resourceId);

        resourceService.deleteResource(resourceId);

        verify(resourceRepository, times(1)).deleteById(resourceId);
    }

    @Test
    void testSaveResourceThrowsExceptionForInvalidTitle() {
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setTitle("");
        resourceDTO.setUrl("http://example.com/resource");
        resourceDTO.setCategoryId(1L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.saveResource(resourceDTO);
        });

        assertThat(exception.getMessage(), is("El título no puede estar vacío"));
    }

    @Test
    void testSaveResourceThrowsExceptionForInvalidFileUrl() {
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setTitle("Valid Title");
        resourceDTO.setUrl("");
        resourceDTO.setCategoryId(1L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.saveResource(resourceDTO);
        });

        assertThat(exception.getMessage(), is("La URL del archivo no puede estar vacía"));
    }

    @Test
    void testUpdateResourceThrowsExceptionForNonExistingResource() {
        // Crear DTO para la actualización
        ResourceDTO updatedResourceDTO = new ResourceDTO();
        updatedResourceDTO.setTitle("Updated Title");
        updatedResourceDTO.setUrl("http://example.com/updated");
        updatedResourceDTO.setCategoryId(1L);

        when(resourceRepository.findById(1L)).thenReturn(Optional.empty());

        Category category = new Category();
        category.setName("Category 1");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.updateResource(1L, updatedResourceDTO);
        });

        assertThat(exception.getMessage(), is("Recurso no encontrado"));
    }

    @Test
    void testSaveResourceThrowsExceptionForNonExistingCategory() {
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setTitle("Valid Resource");
        resourceDTO.setUrl("http://example.com/resource");
        resourceDTO.setCategoryId(999L);

        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.saveResource(resourceDTO);
        });

        assertThat(exception.getMessage(), is("Categoría con ID 999 no encontrada"));
    }
}