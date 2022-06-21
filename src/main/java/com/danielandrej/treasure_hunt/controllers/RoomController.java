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

    @PostMapping(value="/games/{game_id}/player")
    public Player joinGame(@PathVariable("game_id") Long gameID,
    		@RequestParam(value="name") String name,
            @RequestParam(value="code") String code) {
        Optional<Game> game = gameService.findGameByID(gameID);

        if (!game.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }

        if (!game.get().getCode().equals(code)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid game code");
        }

        Player player = new Player(name, game.get());
        playerService.savePlayer(player);

        return player;

    }

    @GetMapping(value="/players")
    public Player joinGameAsExistingPlayer(@RequestParam(value="player_session_id") String playerSessionID) {

        Optional<Player> player = playerService.findPlayerBySessionID(playerSessionID);

        if (player.isPresent()) {
            return player.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");

        }
    }

    @DeleteMapping(value="/games/{game_id}/join", produces="application/json")
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
}
