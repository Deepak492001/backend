package com.example.room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	Room findByRoomId(Long roomId);
	List<Room> findByUserUserEmail(String userEmail);
}
