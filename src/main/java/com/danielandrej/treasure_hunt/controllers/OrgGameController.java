package com.danielandrej.treasure_hunt.controllers;

import com.danielandrej.treasure_hunt.services.GameService;
import com.danielandrej.treasure_hunt.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class OrgGameController {


    private final GameService gameService;

    @Autowired
    public OrgGameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value="games", produces="application/json")
    public List<Game> findGames() {
        return gameService.getRooms();
    }

    @GetMapping(value="games", params="code", produces="application/json")
    public ResponseEntity<Game> findGameByCode(@RequestParam String code) {
        Optional<Game> game = gameService.findGameByCode(code);
        return ResponseEntity.of(game);
    }

    @GetMapping(value="games/{game_id}", produces="application/json")
    public ResponseEntity<Game> findGameByID(@PathVariable("game_id") Long gameID) {
        Optional<Game> game = gameService.findGameByID(gameID);
        return ResponseEntity.of(game);
    }

    @PostMapping(value="games", produces="application/json")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game newGame = gameService.createGame(game);
        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    @DeleteMapping(value="games/{game_id}", produces="application/json")
    public ResponseEntity<Game> deleteGame(@PathVariable("game_id") Long gameID) {
        Optional<Game> game = gameService.findGameByID(gameID);
        if (game.isPresent()) {
            gameService.deleteGame(game.get());
            return ResponseEntity.of(game);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with this ID was not found");
        }
    }

    @PutMapping(value="games/{game_id}", produces="application/json")
    public ResponseEntity<Game> updateGame(@PathVariable("game_id") Long gameID, @RequestBody Game game) {
        Optional<Game> oldGame = gameService.findGameByID(gameID);
        if (oldGame.isPresent()) {
            Game newGame = gameService.updateGame(oldGame.get(), game);
            return new ResponseEntity<>(newGame, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with this ID was not found");
        }
    }

    @PostMapping(value="games/{game_id}/start", produces="application/json")
    public ResponseEntity<Game> startGame(@PathVariable("game_id") Long gameID) {
        Optional<Game> game = gameService.findGameByID(gameID);
        if (game.isPresent()) {
            if (game.get().getStatus() == Game.Status.IN_PROGRESS) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "The game has already started");
            }
            gameService.updateStatus(game.get(), Game.Status.IN_PROGRESS);
            return new ResponseEntity<>(game.get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with this ID was not found");
        }
    }

}
