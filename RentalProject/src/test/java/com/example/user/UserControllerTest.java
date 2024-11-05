package com.example.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @SuppressWarnings("null")
    @Test
    void testAddUser() throws IOException {
        // Arrange
        String userEmail = "test@example.com";
        String name = "Test User";
        String userPassword = "password";
        Long userPhoneNumber = 1234567890L;

        // Act
        ResponseEntity<Map<String, Object>> response = userController.addUser(userEmail, name, userPassword,
                userPhoneNumber, null);

        // Assert
        assertEquals(HttpStatus.SC_CREATED, response.getStatusCode());
        assertEquals("User Added Successfully", response.getBody().get("message"));
    }

    // Add more test cases for other methods
}
