package com.example.watchlist;

import java.util.List;

import com.example.room.Room;

public interface WatchListService {
	   public String toggleWatchlist(String userEmail, Long roomId);
	   public List<Room> getUserWatchlist(String userEmail);
}
