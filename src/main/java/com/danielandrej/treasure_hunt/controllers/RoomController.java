package com.danielandrej.treasure_hunt.controllers;

import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.models.Player;
import com.danielandrej.treasure_hunt.services.GameService;
import com.danielandrej.treasure_hunt.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    /**
     * Join a game with specified name.
     * @param gameID Game ID
     * @param name Player name
     * @param code Game code
     * @throws ResponseStatusException 404 if game does not exist
     * @throws ResponseStatusException 400 if the code is incorrect
     * @return Player
     */
    @PostMapping(value="/games/{game_id}/player")
    public Player joinGame(@PathVariable("game_id") Long gameID,
    		@RequestParam(value="name") String name,
            @RequestParam(value="code") String code) {
        Optional<Game> game = gameService.findGameByID(gameID);

        if (game.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }

        if (!game.get().getCode().equals(code)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid game code");
        }

        Player player = new Player(name, game.get());
        playerService.savePlayer(player);

        return player;

    }

    /**
     * Get the game belonging to a player (by session ID).
     * @param playerSessionID Player session ID
     * @throws ResponseStatusException 404 if Player not found
     * @throws ResponseStatusException 404 if Game not found
     * @return Player
     */
    @GetMapping(value="/players/{player_session_id}/game")
    public Game getGameBySessionID(@PathVariable("player_session_id") String playerSessionID) {

        Optional<Player> player = playerService.findPlayerBySessionID(playerSessionID);

        if (player.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }

        return player.get().getGame();
    }

    /**
     * Leave a game.
     * @param playerSessionID Player session ID
     * @throws ResponseStatusException 404 if Player not found
     * @return Success status
     */
    @DeleteMapping(value="/players/{session_id}", produces="application/json")
    public String leaveGame(@PathVariable("session_id") String playerSessionID) {

        Optional<Player> player = playerService.findPlayerBySessionID(playerSessionID);

        if (player.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }

        playerService.deletePlayer(player.get());
        return "OK";
    }
}
