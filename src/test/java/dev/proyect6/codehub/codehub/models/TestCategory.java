package dev.proyect6.codehub.codehub.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

class TestCategory {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCategoryCreation() {
        Category category = new Category();
        category.setName("Programming");

        assertThat(category.getName(), is("Programming"));
    }

    @Test
    void testCategoryNameCannotBeBlank() {
        Category category = new Category();
        category.setName("");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations, is(not(empty())));
        assertThat(violations.iterator().next().getMessage(), is("El nombre de la categoría no puede estar vacío"));
    }

    @Test
    void testCategoryNameCannotBeNull() {
        Category category = new Category();
        category.setName(null);

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations, is(not(empty())));
        assertThat(violations.iterator().next().getMessage(), is("El nombre de la categoría no puede estar vacío"));
    }

    @Test
void testCategoryIdAssignment() {
    Category category = new Category();
    Long expectedId = 1L;
    java.lang.reflect.Field field;
    try {
        field = Category.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(category, expectedId);
    } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException(e);
    }

    assertThat(category.getId(), is(expectedId));
}
}