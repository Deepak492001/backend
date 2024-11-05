package com.example.user;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

        String saveUser(String name, String userEmail, String userPassword,
                        Long userPhoneNumber)
                        throws IOException;

        String uploadOrUpdateProfile(MultipartFile userProfile, String userEmail);

        String deleteProfile(String userEmail);

        User getUserByUserEmail(String userEmail);

        List<User> getAllUsers();

        String updateUser(String name, String userEmail, String userPassword, MultipartFile userProfile,
                        Long userPhoneNumber)
                        throws IOException;

        String deleteUser(String userEmail);
}
