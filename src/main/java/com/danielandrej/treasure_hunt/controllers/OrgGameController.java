package com.danielandrej.treasure_hunt.controllers;

import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/organizers")
public class OrgGameController {


    private final GameService gameService;

    @Autowired
    public OrgGameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Get all games
     * @return List of games
     */
    @GetMapping(value="games", produces="application/json")
    public List<Game> findGames() {
        return gameService.getRooms();
    }

    /**
     * Get game by code
     * @param code Game code
     * @throws ResponseStatusException 404 if game with given code does not exist
     * @return Game
     */
    @GetMapping(value="games", params="code", produces="application/json")
    public ResponseEntity<Game> findGameByCode(@RequestParam String code) {
        return ResponseEntity.of(gameService.findGameByCode(code));
    }

    /**
     * Get game by id
     * @param gameID Game ID
     * @return Game
     */
    @GetMapping(value="games/{game_id}", produces="application/json")
    public ResponseEntity<Game> findGameByID(@PathVariable("game_id") Long gameID) {
        return ResponseEntity.of(gameService.findGameByID(gameID));
    }

    /**
     * Create new game
     * @param game Game to create
     * @return Game
     */
    @PostMapping(value="games", produces="application/json")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game newGame = gameService.createGame(game);
        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    /**
     * Delete game by id
     * @param gameID Game ID
     * @throws ResponseStatusException 404 if game with given id does not exist
     */
    @DeleteMapping(value="games/{game_id}", produces="application/json")
    public void deleteGame(@PathVariable("game_id") Long gameID) {
        gameService.deleteGameByID(gameID);
    }

    /**
     * Update game by id
     * @param gameID Game ID
     * @param game Game to update
     * @throws ResponseStatusException 404 if game with given id does not exist
     * @return Game
     */
    @PutMapping(value="games/{game_id}", produces="application/json")
    public Game updateGame(@PathVariable("game_id") Long gameID, @RequestBody Game game) {
        return gameService.updateGameByID(gameID, game);
    }

    /**
     * Update game status by game id
     */
    @PatchMapping(value="games/{game_id}/status", produces="application/json")
    public Game updateGame(@PathVariable("game_id") Long gameID,
                                           @RequestBody Game.Status status) {
        return gameService.updateGameStatus(gameID, status);
    }
}
