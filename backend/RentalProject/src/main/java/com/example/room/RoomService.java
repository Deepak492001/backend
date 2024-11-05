package com.example.room;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface RoomService {
	String saveRoom(Integer numberOfBalconies, Integer bathRooms, String floorNumber, String roomArea,
			MultipartFile roomImage, Double rent, Double securityDeposit, String description, String landmark,
			String state, String city, String address, FurnishedStatus furnishedStatus, String userEmail,
			MultipartFile roomVideo, String preference, Boolean lift, Boolean reservedParking, Boolean security,
			Boolean gym, Boolean maintainanceStaff, Boolean garden, Boolean wifi) throws IOException;

	List<Room> getAllRooms();

	Room getRoomById(Long roomId);

	List<Room> getRoomByUser(String userEmail);

	String updateRoom(Long roomId, Integer numberOfBalconies, Integer bathRooms, String floorNumber, String roomArea,
			MultipartFile roomImage, Double rent, Double securityDeposit, String description, String landmark,
			String state, String city, String address, FurnishedStatus furnishedStatus, String userEmail,
			MultipartFile roomVideo, String preference) throws IOException;

	String deleteRoom(Long roomId);
}
