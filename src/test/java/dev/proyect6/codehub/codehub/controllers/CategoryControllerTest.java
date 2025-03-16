package dev.proyect6.codehub.codehub.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.proyect6.codehub.codehub.config.AuthFilter;
import dev.proyect6.codehub.codehub.models.Category;
import dev.proyect6.codehub.codehub.models.User;
import dev.proyect6.codehub.codehub.services.CategoryService;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CategoryService categoryService;
    @MockitoBean
    AuthFilter authFilter;
    
    @Autowired
    ObjectMapper objectMapper;

    public List<Category> categories;
    @BeforeEach

    void setUp() {
        //GIVEN
        Category category = new Category(); 
        category.setName("Category 1");
        Category category2 = new Category(); 
        category2.setName("Category 2");
        categories= new ArrayList<>();
        categories.add(category);
        categories.add(category2);
    }
    
    @Test
    @DisplayName("Test getAllCategories")
    void testGetAllCategories() throws Exception {

        when(authFilter.preHandle(anyString())).thenReturn(true); 
        when(categoryService.getAllCategories()).thenReturn(categories);

        //WHEN
        MockHttpServletResponse response = mockMvc.perform(
            get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-KEY", "12345abc")
                .accept(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        //THEN
        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), containsString("Category 1"));
        assertThat(response.getContentAsString(), containsString("Category 2"));
    }

    @Test
    @DisplayName("Test getAllCategories with bad apikey")
    void testGetAllCategoriesWithBadApikey() throws Exception {
        //WHEN
        when(authFilter.preHandle(anyString())).thenReturn(false); 
              
        when(categoryService.getAllCategories()).thenReturn(categories);
       //WHEN
        MockHttpServletResponse response = mockMvc.perform(
        get("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-API-KEY", "125abc")
            .accept(MediaType.APPLICATION_JSON)

        )
        .andExpect(status().isUnauthorized())
        .andReturn()
        .getResponse();

        //THEN        
        assertThat(response.getStatus(), is(401));
        assertThat(response.getContentAsString(), containsString("Apikey incorrecta"));
    }
}
