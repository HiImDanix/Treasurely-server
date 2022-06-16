package com.danielandrej.treasure_hunt.controllers;

import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.models.Player;
import com.danielandrej.treasure_hunt.services.GameService;
import com.danielandrej.treasure_hunt.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class RoomController {

    private final PlayerService playerService;
    private final GameService gameService;

    @Autowired
    public RoomController(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @PostMapping(value="/game/{game_id}/join", params="name", produces="application/json")
    public String joinGame(@PathVariable("game_id") Long gameID, @RequestParam String name) {
        Optional<Game> game = gameService.findGameByID(gameID);
        if (game.isPresent()) {
            String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
            Optional<Player> existingPlayer = playerService.findPlayerBySessionID(sessionID);
            if (existingPlayer.isPresent()) {
                existingPlayer.get().setGame(game.get());
                playerService.savePlayer(existingPlayer.get());
            } else {
                Player player = new Player(sessionID, name, game.get());
                playerService.savePlayer(player);
            }
            return "OK";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
    }

    @DeleteMapping(value="/game/{game_id}/join", produces="application/json")
    public String leaveGame(@PathVariable("game_id") Long gameID) {
        Optional<Game> game = gameService.findGameByID(gameID);
        if (game.isPresent()) {
            String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
            Optional<Player> existingPlayer = playerService.findPlayerBySessionID(sessionID);
            if (existingPlayer.isPresent()) {
                existingPlayer.get().setGame(null);
                playerService.savePlayer(existingPlayer.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
            }
            return "OK";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
    }

    @GetMapping(value="/game/current", produces="application/json")
    public Game getCurrentGame() {
        String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
        Optional<Player> player = playerService.findPlayerBySessionID(sessionID);
        if (player.isPresent()) {
            return player.get().getGame();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in a game");
        }
    }
}
