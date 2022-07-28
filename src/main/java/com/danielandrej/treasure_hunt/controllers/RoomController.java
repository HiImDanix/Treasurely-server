package com.danielandrej.treasure_hunt.controllers;

import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.models.Player;
import com.danielandrej.treasure_hunt.services.GameService;
import com.danielandrej.treasure_hunt.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RoomController {

    private final RoomService roomService;
    private final GameService gameService;


    @Autowired
    public RoomController(RoomService roomService, GameService gameService) {
        this.roomService = roomService;
        this.gameService = gameService;
    }

    @GetMapping(value="games", params="code", produces="application/json")
    public ResponseEntity<Game> findGameByCode(@RequestParam String code) {
        Optional<Game> game = gameService.findGameByCode(code);
        return ResponseEntity.of(game);
    }

    @PostMapping(value="/games/{game_id}/player")
    public Player joinGame(@PathVariable("game_id") Long gameID,
            @RequestParam(value="code") String code,
    		@RequestParam(value="name") String name
            ) {
        return roomService.joinGame(gameID, code, name);
    }

    @DeleteMapping(value="/players/{session_id}", produces="application/json")
    public void leaveGame(@PathVariable("session_id") String playerSessionID) {
        roomService.leaveGame(playerSessionID);
    }
}
