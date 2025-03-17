package dev.proyect6.codehub.codehub.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import dev.proyect6.codehub.codehub.models.User;
import dev.proyect6.codehub.codehub.services.AuthService;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {  

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AuthService authService;

    @Autowired
    ObjectMapper objectMapper;
    
    @Test
    @DisplayName("Test login")
    void testLogin() throws Exception {

        User credentials = new User();
        String apiKey = "12345abc";

        User okUser = new User();
        okUser.setApikey(apiKey);
        credentials.setUsername("admin");
        credentials.setPassword("password");

        when(authService.authenticate(any(User.class))).thenReturn(Optional.of(okUser));
        
        MockHttpServletResponse response = mockMvc
        .perform(            
            post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(credentials))
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();
        
        System.out.println(response.getContentAsString());

        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), containsString(apiKey));

    }
    @Test
    @DisplayName("Test login with bad credentials")
    void testLoginWithBadCredentials() throws Exception {

        User credentials = new User();
        credentials.setUsername("admin");
        credentials.setPassword("BADpassword");

        when(authService.authenticate(any(User.class))).thenReturn(Optional.empty());
        
        MockHttpServletResponse response = mockMvc
        .perform(            
            post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(credentials))
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isUnauthorized())
        .andReturn()
        .getResponse();
        
        System.out.println(response.getContentAsString());

        assertThat(response.getStatus(), is(401));
        assertThat(response.getContentAsString(), containsString("Credenciales incorrectas"));

    }
}
