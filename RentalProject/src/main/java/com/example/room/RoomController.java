package com.example.room;

import java.io.IOException;
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

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class RoomController {
	@Autowired
	private RoomService roomService;

	@PostMapping("/add-room")
    public ResponseEntity<Map<String, Object>> addRoom(
		@Valid
            @RequestParam(name = "numberOfBalconies") Integer numberOfBalconies,
            @RequestParam(name = "bathRooms") Integer bathRooms,
            @RequestParam(name = "floorNumber") String floorNumber,
            @RequestParam(name = "roomArea") String roomArea,
            @RequestParam(name = "roomImage") MultipartFile roomImage,
            @RequestParam(name = "roomVideo") MultipartFile roomVideo,
            @RequestParam(name = "rent") Double rent,
            @RequestParam(name = "securityDeposit") Double securityDeposit,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "landmark") String landmark,
            @RequestParam(name = "state") String state,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "furnishedStatus") FurnishedStatus furnishedStatus,
            @RequestParam(name = "userEmail") String userEmail,
            @RequestParam(name = "preference", required = false) String preference,
            @RequestParam(name = "lift", required = false) Boolean lift,
            @RequestParam(name = "reservedParking", required = false) Boolean reservedParking,
            @RequestParam(name = "security", required = false) Boolean security,
            @RequestParam(name = "gym", required = false) Boolean gym,
            @RequestParam(name = "maintainanceStaff", required = false) Boolean maintainanceStaff,
            @RequestParam(name = "garden", required = false) Boolean garden,
            @RequestParam(name = "wifi", required = false) Boolean wifi) throws IOException {

        try {
            String msg = this.roomService.saveRoom(numberOfBalconies, bathRooms, floorNumber, roomArea, roomImage, rent,
                    securityDeposit, description, landmark, state, city, address, furnishedStatus, userEmail,
                    roomVideo, preference, lift, reservedParking, security, gym, maintainanceStaff, garden, wifi);


            return new ResponseEntity<>(Map.of("message", msg), HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Internal Server Error");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all-rooms")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/get-room/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long roomId) {
        Room room = roomService.getRoomById(roomId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

	@GetMapping("/get-room-id/{userEmail}")
	public ResponseEntity<List<Room>> getRoomByUser(@PathVariable String userEmail) {
		List<Room> room = roomService.getRoomByUser(userEmail);
		return new ResponseEntity<>(room, HttpStatus.OK);
	}

    @PutMapping("/update-room/{roomId}")
    public ResponseEntity<Map<String, Object>> updateRoom(
            @RequestParam(name = "numberOfBalconies", required = false) Integer numberOfBalconies,
            @RequestParam(name = "bathRooms", required = false) Integer bathRooms,
            @RequestParam(name = "floorNumber", required = false) String floorNumber,
            @RequestParam(name = "roomArea", required = false) String roomArea,
            @RequestParam(name = "roomImage", required = false) MultipartFile roomImage,
            @RequestParam(name = "roomVideo", required = false) MultipartFile roomVideo,
            @RequestParam(name = "rent", required = false) Double rent,
            @RequestParam(name = "securityDeposit", required = false) Double securityDeposit,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "landmark", required = false) String landmark,
            @RequestParam(name = "state", required = false) String state,
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "furnishedStatus", required = false) FurnishedStatus furnishedStatus,
            @RequestParam(name = "userEmail", required = false) String userEmail,
            @RequestParam(name = "preference", required = false) String preference, @PathVariable("roomId") Long roomId)
            throws IOException {

        try {
            String msg = roomService.updateRoom(roomId, numberOfBalconies, bathRooms, floorNumber, roomArea, roomImage,
                    rent, securityDeposit, description, landmark, state, city, address, furnishedStatus, userEmail,
                    roomVideo, preference);


            return new ResponseEntity<>(Map.of("message", msg), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Bad Request");
            response.put("statusCode", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Internal Server Error");
            // response.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-room/{roomId}")
    public ResponseEntity<Map<String, String>> deleteRoom(@PathVariable Long roomId) {
        String msg = roomService.deleteRoom(roomId);
        return new ResponseEntity<>(Map.of("message",msg), HttpStatus.NO_CONTENT);
    }


}
