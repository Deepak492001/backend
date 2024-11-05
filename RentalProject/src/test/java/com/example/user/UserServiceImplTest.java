package com.example.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.cloudinary.CloudinaryService;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CloudinaryService cloudinaryService;

    @Test
    void testSaveUser() throws IOException {
        // Arrange
        String name = "Test User";
        String userEmail = "test@example.com";
        String userPassword = "password";
        Long userPhoneNumber = 1234567890L;

        // Act
        String msg = userService.saveUser(name, userEmail, userPassword, userPhoneNumber);

        // Assert
        assertEquals("User Added Successfully", msg);
    }

    // Add more test cases for other methods
}