package com.example.user;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.cloudinary.CloudinaryService;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.UserAlreadyExistsException;
import com.example.exception.UserNotFoundException;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

      private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public String saveUser(String name, String userEmail, String userPassword, Long userPhoneNumber)
            throws IOException {
        if (this.userRepository.existsByUserEmail(userEmail)) {
            throw new UserAlreadyExistsException("User with given email is  already exists");
        }

        User user = new User(name, userEmail, encoder.encode(userPassword), userPhoneNumber);
        // User user = new User(name, userEmail, userPassword, userPhoneNumber);
// User user2 = new User
        this.userRepository.save(user);
        return "User Added Successfully";
    }



    @Override
    public String deleteProfile(String userEmail) {
        User user = this.userRepository.findByUserEmail(userEmail);
        if (user.getUserProfileUrl() == null) {
            log.warn("No USer with given email address" + userEmail);
            throw new ResourceNotFoundException("Sorry No Image Found");
        }

        this.cloudinaryService.deleteMedia(user.getUserProfilePublicId(), "image");
  log.info("Setting url and id null");
        user.setUserProfilePublicId(null);
        user.setUserProfileUrl(null);
        System.out.println("Successfully deleted");
        this.userRepository.save(user);
        return "Profile Deleted Successfully";

    }

    @Transactional
    @Override
    public String uploadOrUpdateProfile(MultipartFile userProfile, String userEmail) {
        if (userEmail == null) {
            log.error("User email is null");
            throw new IllegalArgumentException("User email is null");
        }

    if (userProfile == null || userProfile.isEmpty()) {
        log.error("Profile file is empty or null for email: {}", userEmail);
        throw new ResourceNotFoundException("Profile file is empty or null");
    }
        User user = this.userRepository.findByUserEmail(userEmail);
        if (user == null) {
            log.error("User not found with email: " + userEmail);
            throw new UserNotFoundException("User not found with email: " + userEmail);
        }
        boolean isUpdated = false;
        if (user.getUserProfileUrl() != null && user.getUserProfilePublicId() != null && userProfile != null) {

            Map<String, Object> mediaUploadResponse = this.cloudinaryService.updateMedia(userProfile, user.getUserProfilePublicId());
            user.setUserProfileUrl(mediaUploadResponse.get("secure_url").toString());
            isUpdated = true;

        } else {
            Map<String, Object> mediaUploadResponse = this.cloudinaryService.uploadMedia(userProfile);
            user.setUserProfilePublicId(mediaUploadResponse.get("public_id").toString());
            user.setUserProfileUrl(mediaUploadResponse.get("secure_url").toString());

        }
        this.userRepository.save(user);
        return isUpdated ? "Profile Updated Successfully" : "Profile Uploaded Successfully";
    }



    @Override
    public User getUserByUserEmail(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("User not found with provided email: ");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String updateUser(String name, String userEmail, String userPassword, MultipartFile userProfile,
            Long userPhoneNumber) throws IOException {
        User user = this.userRepository.findByUserEmail(userEmail);
        if (userProfile != null && user.getUserProfilePublicId() != null && user.getUserProfileUrl() != null) {
            this.cloudinaryService.updateMedia(userProfile, user.getUserProfilePublicId());
        }


        if (name != null) {
            user.setName(name);
        }

        if (userEmail != null) {
            user.setUserEmail(userEmail);
        }

        if (userPassword != null) {
            user.setUserPassword(userPassword);
        }

        if (userPhoneNumber != null) {
            user.setUserPhoneNumber(userPhoneNumber);
        }

        userRepository.save(user);

        return "User Updated Successfully";
    }

    @Override
    public String deleteUser(String userEmail) {
        User user = getUserByUserEmail(userEmail);
        if (user.getUserProfileUrl() != null) {
            if (user.getUserProfilePublicId() != null) {
                this.cloudinaryService.deleteMedia(user.getUserProfilePublicId(), "image");
            }
        }
        this.userRepository.delete(user);
        return "User deleted successfully";
    }

}
