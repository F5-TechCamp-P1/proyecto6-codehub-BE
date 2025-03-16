package dev.proyect6.codehub.codehub.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void testGetApiKey() {
        User user = new User();
        String expectedApiKey = "test-api-key";
        user.setApikey(expectedApiKey);
        assertEquals(expectedApiKey, user.getApikey());
    }

    @Test
    void testGetId() {
        User user = new User();
        assertNull(user.getId());
        
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void testGetPassword() {
        User user = new User();
        String expectedPassword = "test-password";
        user.setPassword(expectedPassword);
        assertEquals(expectedPassword, user.getPassword());
    }

    @Test
    void testGetUsername() {
        User user = new User();
        String expectedUsername = "test-username";
        user.setUsername(expectedUsername);
        assertEquals(expectedUsername, user.getUsername());
    }

    @Test
    void testSetApikey() {
        User user = new User();
        String expectedApiKey = "test-api-key";
        user.setApikey(expectedApiKey);
        assertEquals(expectedApiKey, user.getApikey());
    }

    @Test
    void testSetPassword() {
        User user = new User();
        String expectedPassword = "test-password";
        user.setPassword(expectedPassword);
        assertEquals(expectedPassword, user.getPassword());
    }

    @Test
    void testSetUsername() {
        User user = new User();
        String expectedUsername = "test-username";
        user.setUsername(expectedUsername);
        assertEquals(expectedUsername, user.getUsername());
    }
}