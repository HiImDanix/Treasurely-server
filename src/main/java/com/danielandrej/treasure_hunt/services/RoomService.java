package com.danielandrej.treasure_hunt.services;

import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.models.Player;
import com.danielandrej.treasure_hunt.repositories.GameRepository;
import com.danielandrej.treasure_hunt.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RoomService {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    @Autowired
    public RoomService(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Get game by code
     * @param code Game code
     * @throws ResponseStatusException 404 if game with given code does not exist
     * @return Game
     */
    @GetMapping(value="games", params="code", produces="application/json")
    public ResponseEntity<Game> findGameByCode(@RequestParam String code) {
        Optional<Game> game = gameRepository.findByCode(code);
        return ResponseEntity.of(game);
    }

    /**
     * Join a game.
     * @param gameID Game ID
     * @param gameCode Game code
     * @param name Player name
     * @throws ResponseStatusException 404 if game does not exist
     * @throws ResponseStatusException 400 if the game code is incorrect
     * @throws ResponseStatusException 400 If name is not provided or is already taken
     * @return Player
     */
    public Player joinGame(Long gameID, String gameCode, String name) {
        // Game ID is valid
        Optional<Game> game = gameRepository.findById(gameID);
        if (game.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        // Game code is valid
        if (!game.get().getCode().equals(gameCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid game code");
        }

        // Name is valid & unique
        if (name == null || name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if (playerRepository.findByName(name).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player name has already been taken");
        }

        // Create & persist player
        Player player = new Player(name, game.get());
        playerRepository.save(player);

        return player;
    }

    /**
     * Leave a game.
     * @param playerSessionID Player session ID
     * @throws ResponseStatusException 404 if Player not found
     */
    public void leaveGame(String playerSessionID) {
        Optional<Player> player = playerRepository.findBySessionID(playerSessionID);
        if (player.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }

        playerRepository.delete(player.get());
    }
}
