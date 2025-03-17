package dev.proyect6.codehub.codehub.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
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

        MockHttpServletResponse response = mockMvc.perform(
            get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-KEY", "12345abc")
                .accept(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), containsString("Category 1"));
        assertThat(response.getContentAsString(), containsString("Category 2"));
    }

    @Test
    @DisplayName("Test getAllCategories with bad apikey")
    void testGetAllCategoriesWithBadApikey() throws Exception {

        when(authFilter.preHandle(anyString())).thenReturn(false); 

        when(categoryService.getAllCategories()).thenReturn(categories);

        MockHttpServletResponse response = mockMvc.perform(
        get("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-API-KEY", "125abc")
            .accept(MediaType.APPLICATION_JSON)

        )
        .andExpect(status().isUnauthorized())
        .andReturn()
        .getResponse();
    
        assertThat(response.getStatus(), is(401));
        assertThat(response.getContentAsString(), containsString("Apikey incorrecta"));
    }
    @Test
    @DisplayName("Test createCategory")
    void testCreateCategory() throws Exception {

        Category category = new Category();
        category.setName("Category 3");

        when(authFilter.preHandle(anyString())).thenReturn(true); 
        when(categoryService.saveCategory(any(Category.class))).thenReturn(category);

        MockHttpServletResponse response = mockMvc.perform(
            post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
                .header("X-API-KEY", "12345abc")
                .accept(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), containsString("Category 3"));

    }
    @Test
    @DisplayName("Test createCategory with bad apikey")
    void testCreateCategoryWithBadApikey() throws Exception {

        Category category = new Category();
        category.setName("Category 3");

        when(authFilter.preHandle(anyString())).thenReturn(false); 
        when(categoryService.saveCategory(any(Category.class))).thenReturn(category);

        MockHttpServletResponse response = mockMvc.perform(
            post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
                .header("X-API-KEY", "12345abc")
                .accept(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isUnauthorized())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(401));
        assertThat(response.getContentAsString(), containsString("Apikey incorrecta"));
    }
    @Test
    @DisplayName("Test updateCategory")
    void testUpdateCategory() throws Exception {

        Category category = categories.get(0);
        category.setName("Category 3");
        when(authFilter.preHandle(anyString())).thenReturn(true); 
        when(categoryService.updateCategory(anyLong(), any(Category.class))).thenReturn(category);

        MockHttpServletResponse response = mockMvc.perform(
            put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
                .header("X-API-KEY", "12345abc")
                .accept(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), containsString("Category 3"));

    }
    @Test
    @DisplayName("Test updateCategory with bad apikey")
    void testUpdateCategoryWithBadApikey() throws Exception {    
    
        Category category = categories.get(0);
        category.setName("Category 3");
        when(authFilter.preHandle(anyString())).thenReturn(false); 
        when(categoryService.updateCategory(anyLong(), any(Category.class))).thenReturn(category);

        MockHttpServletResponse response = mockMvc.perform(
            put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
                .header("X-API-KEY", "12345abc")
                .accept(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isUnauthorized())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(401));
        assertThat(response.getContentAsString(), containsString("Apikey incorrecta"));
    }
    @Test
    @DisplayName("Test updateCategory with bad id")
    void testUpdateCategoryWithBadId() throws Exception {

        Category category = categories.get(0);
        when(authFilter.preHandle(anyString())).thenReturn(true); 
        when(categoryService.updateCategory(eq(4L), any(Category.class)))
            .thenThrow(new RuntimeException("Category not found"));

        MockHttpServletResponse response = mockMvc.perform(
            put("/api/categories/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
                .header("X-API-KEY", "12345abc")
                .accept(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isNotFound())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(404));        
    }
    @Test
    @DisplayName("Test deleteCategory")
    void testDeleteCategory() throws Exception {

    
        when(authFilter.preHandle(anyString())).thenReturn(true); 

        MockHttpServletResponse response = mockMvc.perform(
            delete("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-KEY", "12345abc")
                .accept(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isOk())            
            .andReturn()
            .getResponse();        


        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), containsString("Categoría borrada exitosamente"));       
    }
    @Test
    @DisplayName("Test deleteCategory with bad apikey")
    void testDeleteCategoryWithBadApikey() throws Exception {

    
        when(authFilter.preHandle(anyString())).thenReturn(false); 

        MockHttpServletResponse response = mockMvc.perform(
            delete("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-KEY", "12345abc")
                .accept(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isUnauthorized())            
            .andReturn()
            .getResponse();        


        assertThat(response.getStatus(), is(401));
        assertThat(response.getContentAsString(), containsString("Apikey incorrecta"));       
    }
    @Test
    @DisplayName("Test getCategoryById")
    void testGetCategoryById() throws Exception {


        when(authFilter.preHandle(anyString())).thenReturn(true); 
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(categories.get(0)));

        MockHttpServletResponse response = mockMvc.perform(
            get("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-KEY", "12345abc")
                .accept(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), containsString("Category 1"));
    }
    
    @Test
    @DisplayName("Test getCategoryById with bad apikey")
    void testGetCategoryByIdWithBadApikey() throws Exception {

        when(authFilter.preHandle(anyString())).thenReturn(false);                
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(categories.get(0)));

        MockHttpServletResponse response = mockMvc.perform(
            get("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-API-KEY", "12345abc")
                .accept(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isUnauthorized())
            .andReturn()
            .getResponse();

        assertThat(response.getStatus(), is(401));
        assertThat(response.getContentAsString(), containsString("Apikey incorrecta"));
    }
}
