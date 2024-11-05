package com.example.watchlist;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.room.Room;

@RestController
@CrossOrigin("*")
public class WatchListController {
  @Autowired
  private WatchListService watchlistService;

  @PostMapping("/toggle-watchlist")
  public ResponseEntity<Map<String, Object>> addToWatchlist(
      @RequestParam String userEmail,
      @RequestParam Long roomId) {

    String msg = watchlistService.toggleWatchlist(userEmail, roomId);

    Map<String, Object> response = new HashMap<>();
    response.put("message", msg);
    // response.put("statusCode", HttpStatus.OK.value());

    return ResponseEntity.ok(response);
  }


  @GetMapping("/get-user-watchlist/{userEmail}")
  public ResponseEntity<List<Room>> getUserWatchlist(@PathVariable String userEmail) {
    List<Room> watchlist = watchlistService.getUserWatchlist(userEmail);
    return ResponseEntity.ok(watchlist);
  }

}
