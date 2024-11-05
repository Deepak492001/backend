package com.example.watchlist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.room.Room;
import com.example.user.User;

@Repository
public interface WatchListRepository extends JpaRepository<Watchlist, Integer> {
	List<Watchlist> findByUser(User user);

	List<Watchlist> findByRoom(Room room);

	Watchlist findByUserAndRoom(User user, Room room);
}
