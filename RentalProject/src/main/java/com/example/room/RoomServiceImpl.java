package com.example.room;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.cloudinary.CloudinaryService;
import com.example.exception.MediaNotUploadException;
import com.example.exception.ResourceNotFoundException;
import com.example.user.User;
import com.example.user.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CloudinaryService cloudinaryService;

	@Override
	public String saveRoom(Integer numberOfBalconies, Integer bathRooms, String floorNumber, String roomArea,
			MultipartFile roomImage, Double rent, Double securityDeposit, String description, String landmark,
			String state, String city, String address, FurnishedStatus furnishedStatus, String userEmail,
			MultipartFile roomVideo, String preference, Boolean lift, Boolean reservedParking, Boolean security,
			Boolean gym, Boolean maintainanceStaff, Boolean garden,
			Boolean wifi) {
		try {
			Map<String, Object> imageObject = this.cloudinaryService.uploadMedia(roomImage);
			Map<String, Object> videoObject = this.cloudinaryService.uploadMedia(roomVideo);
			String roomImageUrl = (String) imageObject.get("secure_url");
			String imagePublicId = (String) imageObject.get("public_id");
			String roomVideoUrl = (String) videoObject.get("secure_url");
			String videoPublicId = (String) videoObject.get("public_id");

			// Fetch the user by email
			User user = this.userRepository.findByUserEmail(userEmail);

			if (user == null) {
				throw new ResourceNotFoundException("User not found with email: " +
						userEmail);
			}

			Room room = new Room(numberOfBalconies, bathRooms, floorNumber, roomArea, preference, roomImageUrl,
					imagePublicId, roomVideoUrl, videoPublicId, rent, securityDeposit, description, landmark, state,
					city,
					address, furnishedStatus, user, lift, reservedParking, security, gym, maintainanceStaff, garden,
					wifi);

			this.roomRepository.save(room);
		} catch (Exception e) {
			log.error("Error Occurred while uploading media ", e.getMessage(), e.getCause());
			throw new MediaNotUploadException("Failed to upload media" + e.getStackTrace() + e.getMessage());
			// TODO: handle exception

		}

		return "Room created successfully";
	}

	@Override
	public List<Room> getAllRooms() {
		return this.roomRepository.findAll();
	}

	@Override
	public List<Room> getRoomByUser(String userEmail) {
		return roomRepository.findByUserUserEmail(userEmail);
	}

	@Override
	public Room getRoomById(Long roomId) {
		return roomRepository.findById(roomId)
				.orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));
	}

	// @Override
	// public String updateRoom(Long roomId, Integer numberOfBalconies, Integer bathRooms, String floorNumber,
	// 		String roomArea, MultipartFile roomImage, Double rent, Double securityDeposit, String description,
	// 		String landmark, String state, String city, String address, FurnishedStatus furnishedStatus,
	// 		String userEmail, MultipartFile roomVideo, String preference) throws IOException {
	// 	Room room = getRoomById(roomId);

	// 	if (room.getRoomImageUrl() != null) {

	// 		if (roomImage != null && room.getRoomImagePublicId() != null) {
	// 			this.cloudinaryService.deleteMedia(room.getRoomImagePublicId(), "image");
	// 			Map<String, Object> uploadResult = this.cloudinaryService.uploadMedia(roomImage);
	// 			room.setRoomImageUrl(uploadResult.get("secure_url").toString());
	// 			room.setRoomImagePublicId(uploadResult.get("public_id").toString());
	// 		}
	// 	}

	// 	if (room.getRoomVideoUrl() != null) {
	// 		if (roomVideo != null && room.getRoomVideoPublicId() != null) {
	// 			this.cloudinaryService.deleteMedia(room.getRoomVideoPublicId(), "video");
	// 			Map<String, Object> uploadResult = this.cloudinaryService.uploadMedia(roomVideo);
	// 			room.setRoomVideoUrl(uploadResult.get("secure_url").toString());
	// 			room.setRoomVideoPublicId(uploadResult.get("public_id").toString());
	// 		}
	// 	}

	// 	room.setNumberOfBalconies(numberOfBalconies);
	// 	room.setBathRooms(bathRooms);
	// 	room.setFloorNumber(floorNumber);
	// 	room.setRoomArea(roomArea);
	// 	room.setRent(rent);
	// 	room.setState(state);
	// 	room.setCity(city);
	// 	room.setAddress(address);
	// 	room.setDescription(description);
	// 	room.setLandmark(landmark);
	// 	room.setPreference(preference);
	// 	room.setSecurityDeposit(securityDeposit);
	// 	room.setFurnishedStatus(furnishedStatus);

	// 	roomRepository.save(room);
	// 	return "Room Updated Successfully";
	// }

	@Override
	public String updateRoom(Long roomId, Integer numberOfBalconies, Integer bathRooms, String floorNumber,
			String roomArea, MultipartFile roomImage, Double rent, Double securityDeposit, String description,
			String landmark, String state, String city, String address, FurnishedStatus furnishedStatus,
			String userEmail, MultipartFile roomVideo, String preference) throws IOException {
		Room room = this.roomRepository.findByRoomId(roomId);

		if (roomImage != null && room.getRoomImagePublicId() != null && room.getRoomImageUrl() != null) {
			log.info("Image is present");
			System.out.println("Image is present");

			Map<String, Object> mediaResponse = this.cloudinaryService.updateMedia(roomImage,
					room.getRoomImagePublicId());
			room.setRoomImageUrl(mediaResponse.get("secure_url").toString());

		}

		if (roomVideo != null && room.getRoomVideoPublicId() != null && room.getRoomVideoUrl() != null) {
			log.info("video is present");

			Map<String, Object> mediaResponse = this.cloudinaryService.updateMedia(roomVideo,
					room.getRoomVideoPublicId());
			room.setRoomVideoUrl(mediaResponse.get("secure_url").toString());
		}

		room.setNumberOfBalconies(numberOfBalconies);
		room.setBathRooms(bathRooms);
		room.setFloorNumber(floorNumber);
		room.setRoomArea(roomArea);
		room.setRent(rent);
		room.setState(state);
		room.setCity(city);
		room.setAddress(address);
		room.setDescription(description);
		room.setLandmark(landmark);
		room.setPreference(preference);
		room.setSecurityDeposit(securityDeposit);
		room.setFurnishedStatus(furnishedStatus);

		roomRepository.save(room);
		return "Room Updated Successfully";
	}


	@Override
	public String deleteRoom(Long roomId) {
		Room room = getRoomById(roomId);
		if (room.getRoomImagePublicId() != null && room.getRoomImageUrl() != null) {
			this.cloudinaryService.deleteMedia(room.getRoomImagePublicId(), "image");
		}
		if (room.getRoomVideoPublicId() != null && room.getRoomVideoUrl() != null) {
			this.cloudinaryService.deleteMedia(room.getRoomVideoPublicId(), "video");
		}
		roomRepository.delete(room);
		return "Room Deleted Successfully";
	}

}
