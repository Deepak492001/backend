package com.example.roommate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.cloudinary.CloudinaryService;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.user.User;
import com.example.user.UserRepository;

@Service
public class RoommateServiceImpl implements RoommateService {

	@Autowired
	private RoommateRepository roommateRepository;

	@Autowired
	private CloudinaryService cloudinaryService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public String createRoomMate(Integer numberOfBalconies, Integer bathRooms, String floorNumber, Integer age,
			String occupation,
			String preference, MultipartFile roomImage,  MultipartFile roomVideo,
			 Double budget, String description, String landmark, String state, String city,
			String address, LocalDate availableFrom, User user) {
		String roomImageUrl = "", roomVideoUrl = "", roomImagePublicId="", roomVideoPublicId="";
		if (roomImage != null) {
			Map<String, Object> mediaUploadResponse = this.cloudinaryService.uploadMedia(roomImage);
			roomImageUrl = (String) mediaUploadResponse.get("secure_url");
			roomImagePublicId = (String) mediaUploadResponse.get("public_id");
		}
		if (roomVideo != null) {
			Map<String, Object> mediaUploadResponse = this.cloudinaryService.uploadMedia(roomVideo);
			roomVideoUrl = (String) mediaUploadResponse.get("secure_url");
			roomVideoPublicId = (String) mediaUploadResponse.get("public_id");
		}
		RoomMate roomMate = new RoomMate(numberOfBalconies, bathRooms, floorNumber, age, occupation, preference,
				roomImageUrl, roomImagePublicId, roomVideoUrl, roomVideoPublicId, budget, description, landmark, state,
				city, address, availableFrom, user);
		this.roommateRepository.save(roomMate);

		return "RoomMate Added Successfully";
	}

	public List<RoomMate> getRoommatesByUserEmail(String userEmail) {
		if (userEmail == null)
			throw new IllegalArgumentException("Null Value Found");
		User user = this.userRepository.findByUserEmail(userEmail);
		if (user == null)
			throw new UserNotFoundException("User Not Found with given email");
		return roommateRepository.findByUserUserEmail(userEmail);

	}

	@Override
	public List<RoomMate> getAllRoommates() {
		return this.roommateRepository.findAll();
	}

	@Override
	public RoomMate getRoommateById(Long id) {
		return roommateRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Roommate not found with id: " + id));
	}

	@Override
	public String deleteRoommate(Long id) {

		RoomMate roommate = getRoommateById(id);

		if (roommate == null) {
			throw new ResourceNotFoundException("Roommate not found with given id: ");
		}
		if (roommate.getRoomImageUrl() != null) {
			this.cloudinaryService.deleteMedia(roommate.getRoomImagePublicId(), "image");
		}
		if (roommate.getRoomVideoUrl() != null) {
			this.cloudinaryService.deleteMedia(roommate.getRoomVideoPublicId(), "video");
		}
		roommateRepository.delete(roommate);
		return "Deleted Successfully";
	}

	@Override
	public String updateRoommate(Long id, Integer numberOfBalconies, Integer bathRooms, String floorNumber,
			Integer age, String occupation, String preference, MultipartFile roomImage,
			MultipartFile roomVideo, Double budget, String description, String landmark,
			String state, String city, String address, LocalDate availableFrom) {

		RoomMate roomMate = this.roommateRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Roommate not found with given id: "));

		if (roomImage != null && roomMate.getRoomImageUrl() != null && roomMate.getRoomImagePublicId() != null) {
			Map<String, Object> mediaResponse =this.cloudinaryService.updateMedia(roomImage, roomMate.getRoomImagePublicId());
			roomMate.setRoomImageUrl(mediaResponse.get("secure_url").toString());
		}

		if (roomVideo != null && roomMate.getRoomVideoUrl() != null && roomMate.getRoomVideoPublicId() != null) {
			Map<String, Object> mediaResponse =	this.cloudinaryService.updateMedia(roomVideo, roomMate.getRoomVideoPublicId());
				roomMate.setRoomVideoUrl(mediaResponse.get("secure_url").toString());
		}

		if (numberOfBalconies != null) {
			roomMate.setNumberOfBalconies(numberOfBalconies);
		}

		if (bathRooms != null) {
			roomMate.setBathRooms(bathRooms);
		}

		if (floorNumber != null) {
			roomMate.setFloorNumber(floorNumber);
		}

		if (age != null) {
			roomMate.setAge(age);
		}

		if (occupation != null) {
			roomMate.setOccupation(occupation);
		}

		if (preference != null) {
			roomMate.setPreference(preference);
		}

		if (budget != null) {
			roomMate.setBudget(budget);
		}

		if (description != null) {
			roomMate.setDescription(description);
		}

		if (landmark != null) {
			roomMate.setLandmark(landmark);
		}

		if (state != null) {
			roomMate.setState(state);
		}

		if (city != null) {
			roomMate.setCity(city);
		}

		if (address != null) {
			roomMate.setAddress(address);
		}

		if (availableFrom != null) {
			roomMate.setAvailableFrom(availableFrom);
		}

		this.roommateRepository.save(roomMate);

		return "RoomMate updated successfully";
	}
}
