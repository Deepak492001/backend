package com.example.user;
// import org.springframework.security.annotation.csrf.CsrfIgnore;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")

public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/add-user")
    public ResponseEntity<Map<String, Object>> addUser(
        	@Valid
            @RequestParam("userEmail") String userEmail,
            @RequestParam(name = "name") String name,
            @RequestParam("userPassword") String userPassword,
            @RequestParam("userPhoneNumber") Long userPhoneNumber,
            HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        session.setAttribute("userEmail", userEmail);
        String msg = userService.saveUser(name, userEmail, userPassword, userPhoneNumber);

        Map<String, Object> response = new HashMap<>();
        response.put("message", msg);
        // response.put("statusCode", HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/uploadOrUpdate")
    public ResponseEntity<Map<String, Object>> updateUserProfile(@RequestParam("userEmail") String userEmail,
            @RequestParam(name = "userProfile") MultipartFile userProfile) {
        String msg = this.userService.uploadOrUpdateProfile(userProfile, userEmail);

        Map<String, Object> response = new HashMap<>();
        response.put("message", msg);
        // response.put("statusCode", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete-user-profile")
    public ResponseEntity<Map<String, Object>> deleteUserProfile(@RequestParam("userEmail") String userEmail) {
        String msg = this.userService.deleteProfile(userEmail);

        Map<String, Object> response = new HashMap<>();
        response.put("message", msg);
        // response.put("statusCode", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-user/{userEmail}")
    public ResponseEntity<User> getUser(@PathVariable String userEmail) {
        User user = userService.getUserByUserEmail(userEmail);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/update-user/{userEmail}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable String userEmail,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "userPassword", required = false) String userPassword,
            @RequestParam(name = "userProfile", required = false) MultipartFile file,
            @RequestParam(name = "userPhoneNumber", required = false) Long userPhoneNumber) throws IOException {
        String msg = userService.updateUser(name, userEmail, userPassword, file, userPhoneNumber);

        Map<String, Object> response = new HashMap<>();
        response.put("message", msg);
        // response.put("statusCode", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete-user/{userEmail}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String userEmail) {
        String msg = this.userService.deleteUser(userEmail);

        Map<String, Object> response = new HashMap<>();
        response.put("message", msg);
        // response.put("statusCode", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // @PostMapping("/add-user")
    // public ResponseEntity<String> addUser(
    //         @RequestParam("userEmail") String userEmail,
    //         @RequestParam(name = "name") String name,
    //         @RequestParam("userPassword") String userPassword,
    //         @RequestParam("userPhoneNumber") Long userPhoneNumber,
    //         HttpServletRequest request) throws IOException {
    //     HttpSession session = request.getSession();
    //     System.out.println(session.getId());
    //     session.setAttribute("userEmail", userEmail);
    //     String msg = userService.saveUser(name, userEmail, userPassword, userPhoneNumber);
    //     return new ResponseEntity<>(msg, HttpStatus.CREATED);
    // }



    // @PostMapping("/uploadOrUpdate")
    // public ResponseEntity<String> updateUserProfile(@RequestParam("userEmail") String userEmail,
    //         @RequestParam(name = "userProfile") MultipartFile userProfile) {
    //     String msg = this.userService.uploadOrUpdateProfile(userProfile, userEmail);
    //     return new ResponseEntity<>(msg, HttpStatus.OK);
    // }

    // @DeleteMapping("/delete-user-profile")
    // public ResponseEntity<String> deleteUserProfile(@RequestParam("userEmail") String userEmail) {
    //     String msg = this.userService.deleteProfile(userEmail);
    //     return new ResponseEntity<>(msg, HttpStatus.OK);

    // }

    // @GetMapping("/get-user/{userEmail}")
    // public ResponseEntity<User> getUser(@PathVariable String userEmail) {
    //     User user = userService.getUserByUserEmail(userEmail);
    //     return new ResponseEntity<>(user, HttpStatus.OK);
    // }

    // @GetMapping("/get-all-users")
    // public ResponseEntity<List<User>> getAllUsers() {
    //     List<User> users = userService.getAllUsers();


    //     return new ResponseEntity<>(users, HttpStatus.OK);
    // }

    // @PutMapping("/update-user/{userEmail}")
    // public ResponseEntity<String> updateUser(@PathVariable String userEmail,
    //         @RequestParam(name = "name", required = false) String name,
    //         @RequestParam(name = "userPassword", required = false) String userPassword,
    //         @RequestParam(name = "userProfile", required = false) MultipartFile file,
    //         @RequestParam(name = "userPhoneNumber", required = false) Long userPhoneNumber) throws IOException {
    //     String msg = userService.updateUser(name, userEmail, userPassword, file, userPhoneNumber);
    //     return new ResponseEntity<>(msg, HttpStatus.OK);
    // }

    // @DeleteMapping("/delete-user/{userEmail}")
    // public ResponseEntity<String> deleteUser(@PathVariable String userEmail) {
    //     String msg = this.userService.deleteUser(userEmail);
    //     return new ResponseEntity<>(msg, HttpStatus.OK);
    // }

}
