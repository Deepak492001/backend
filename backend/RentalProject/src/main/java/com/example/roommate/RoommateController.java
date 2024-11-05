package com.example.roommate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.user.User;
import com.example.user.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class RoommateController {
    @Autowired
    private RoommateService roommateService;

    @Autowired
    private UserService userService;

    @PostMapping("/add-roommate")
    public ResponseEntity<Map<String, Object>> createRoommate(
        	@Valid
            @RequestParam(name = "numberOfBalconies") Integer numberOfBalconies,
            @RequestParam(name = "bathRooms") Integer bathRooms,
            @RequestParam(name = "floorNumber") String floorNumber,
            @RequestParam(name = "age") Integer age,
            @RequestParam(name = "roomImage") MultipartFile roomImage,
            @RequestParam(name = "roomVideo") MultipartFile roomVideo,
            @RequestParam(name = "budget") Double budget,
            @RequestParam(name = "occupation") String occupation,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "landmark") String landmark,
            @RequestParam(name = "state") String state,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "availableFrom") LocalDate availableFrom,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "preference", required = false) String preference) {

        User user = this.userService.getUserByUserEmail(userEmail);
        String msg = roommateService.createRoomMate(numberOfBalconies, bathRooms, floorNumber, age, occupation,
                preference, roomImage, roomVideo, budget, description, landmark, state, city, address, availableFrom,
                user);

        return new ResponseEntity<>(Map.of("message", msg), HttpStatus.CREATED);
    }

    @GetMapping("/get-roommie/{userEmail}")
    public ResponseEntity<List<RoomMate>> getRoommatesByUser(@PathVariable String userEmail) {
        List<RoomMate> roommates = roommateService.getRoommatesByUserEmail(userEmail);
        return new ResponseEntity<>(roommates, HttpStatus.OK);
    }

    @GetMapping("/get-all-roommates")
    public ResponseEntity<List<RoomMate>> getAllRoommates() {
        List<RoomMate> roommates = this.roommateService.getAllRoommates();
        return new ResponseEntity<>(roommates, HttpStatus.OK);
    }

    @GetMapping("/get-roommate/{id}")
    public ResponseEntity<RoomMate> getRoommateById(@PathVariable Long id) {
        RoomMate roommate = roommateService.getRoommateById(id);
        return new ResponseEntity<>(roommate, HttpStatus.OK);
    }

    @PutMapping("/update-roommate/{id}")
    public ResponseEntity<Map<String, Object>> updateRoommate(@PathVariable Long id,
            @RequestParam(name = "numberOfBalconies", required = false) Integer numberOfBalconies,
            @RequestParam(name = "bathRooms", required = false) Integer bathRooms,
            @RequestParam(name = "floorNumber", required = false) String floorNumber,
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam(name = "roomImage", required = false) MultipartFile roomImage,
            @RequestParam(name = "roomVideo", required = false) MultipartFile roomVideo,
            @RequestParam(name = "budget", required = false) Double budget,
            @RequestParam(name = "occupation", required = false) String occupation,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "landmark", required = false) String landmark,
            @RequestParam(name = "state", required = false) String state,
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "availableFrom", required = false) LocalDate availableFrom,
            @RequestParam(name = "userEmail", required = false) String userEmail,
            @RequestParam(name = "preference", required = false) String preference) {

        String msg = this.roommateService.updateRoommate(id, numberOfBalconies, bathRooms, floorNumber, age, occupation,
                preference, roomImage, roomVideo, budget, description, landmark, state, city, address, availableFrom);

        return new ResponseEntity<>(Map.of("message", msg), HttpStatus.OK);
    }

    @DeleteMapping("/delete-roommate/{id}")
    public ResponseEntity<Map<String, Object>> deleteRoommate(@PathVariable Long id) {
        String msg = roommateService.deleteRoommate(id);

        return new ResponseEntity<>(Map.of("message", msg), HttpStatus.OK);
    }

}