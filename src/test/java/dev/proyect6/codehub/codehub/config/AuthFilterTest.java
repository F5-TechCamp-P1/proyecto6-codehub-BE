package dev.proyect6.codehub.codehub.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dev.proyect6.codehub.codehub.services.AuthService;

class AuthFilterTest {
    private AuthFilter authFilter;
    private AuthService authService;
    
    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        authFilter = new AuthFilter(authService);
    }
    
    @Test
    void preHandleSuccess() {
        when(authService.filterAuth(anyString())).thenReturn(true);
        boolean result = authFilter.preHandle("valid-api-key");
        assertTrue(result);
    }
    
    @Test
    void preHandleFailure() {
        when(authService.filterAuth(anyString())).thenReturn(false);
        boolean result = authFilter.preHandle("invalid-api-key");
        assertFalse(result);
    }
    
    @Test
    void preHandleNullApiKey() {
        boolean result = authFilter.preHandle(null);
        assertFalse(result);
    }
}