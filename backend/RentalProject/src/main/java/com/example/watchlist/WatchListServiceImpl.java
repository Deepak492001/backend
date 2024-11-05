package com.example.watchlist;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.room.Room;
import com.example.room.RoomRepository;
import com.example.user.User;
import com.example.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WatchListServiceImpl implements WatchListService {
    @Autowired
    private WatchListRepository watchlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Override
    @Transactional
    public String toggleWatchlist(String userEmail, Long roomId) {
        User user = userRepository.findByUserEmail(userEmail);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with given id: "));

        Watchlist existingEntry = watchlistRepository.findByUserAndRoom(user, room);
        if (existingEntry == null) {
        Watchlist watchlist = new Watchlist(user, room);
        watchlistRepository.save(watchlist);
        return "Added to Watchlist Successfully";
        } else {
            this.watchlistRepository.delete(existingEntry);
            return "Removed from Watchlist Successfully";
        }

    }

    @Override
    public List<Room> getUserWatchlist(String userEmail) {
        if (userEmail == null) {
            log.warn("Null Value Found");
            throw new IllegalArgumentException("Null Value Found");
        }

        User user = userRepository.findByUserEmail(userEmail);
        if (user == null) {
            log.warn("User not found for email: ", userEmail);
            throw new UserNotFoundException("User not found for given email: " );
        }

        List<Watchlist> watchlistEntries = watchlistRepository.findByUser(user);
        List<Room> rooms=new ArrayList<Room>();
        for (Watchlist entry : watchlistEntries) {
            Room room = this.roomRepository.findById(entry.getRoom().getRoomId()).orElse(null);
            if (room != null) {
                rooms.add(room);
            }
        }

        return rooms;
    }
}