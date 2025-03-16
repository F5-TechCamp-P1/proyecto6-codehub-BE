package dev.proyect6.codehub.codehub.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Set;

class ResourceTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testResourceCreation() {
        Resource resource = new Resource();
        resource.setTitle("Spring Boot Guide");
        resource.setFileUrl("https://example.com/guide.pdf");
        resource.setCategory(new Category());

        assertThat(resource.getTitle(), is("Spring Boot Guide"));
        assertThat(resource.getFileUrl(), is("https://example.com/guide.pdf"));
        assertThat(resource.getCategory(), is(notNullValue()));
    }

    @Test
    void testResourceTitleCannotBeBlank() {
        Resource resource = new Resource();
        resource.setTitle("");
        resource.setFileUrl("https://example.com/resource.pdf");

        Set<ConstraintViolation<Resource>> violations = validator.validate(resource);

        assertThat(violations, is(not(empty())));
        assertThat(violations.iterator().next().getMessage(), is("El título no puede estar vacío"));
    }

    @Test
    void testResourceFileUrlCannotBeBlank() {
        Resource resource = new Resource();
        resource.setTitle("Valid Title");
        resource.setFileUrl("");

        Set<ConstraintViolation<Resource>> violations = validator.validate(resource);

        assertThat(violations, is(not(empty())));
        assertThat(violations.iterator().next().getMessage(), is("La URL del archivo no puede estar vacía"));
    }

    @Test
    void testResourceUploadDateIsSetAutomatically() {
        Resource resource = new Resource();
        resource.onCreate();

        assertThat(resource.getUploadDate(), is(notNullValue()));
        assertThat(resource.getUploadDate(), is(LocalDate.now()));
    }

    @Test
    void testResourceIdAssignment() throws NoSuchFieldException, IllegalAccessException {
        Resource resource = new Resource();
        Long expectedId = 1L;

        Field field = Resource.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(resource, expectedId);

        assertThat(resource.getId(), is(expectedId));
    }
}