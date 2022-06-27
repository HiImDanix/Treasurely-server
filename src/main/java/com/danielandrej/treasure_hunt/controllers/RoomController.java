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
    
    private boolean checkGameCode(Optional<Game> game, String code) {
    	boolean retVal = true;
    	
    	if (checkGame(game)) {
    		if (!game.get().getCode().equals(code)) {
            	retVal = false;
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid game code");
            }
    	}
        
        return retVal;
    }
    
    private boolean checkGame(Optional<Game> game) {
    	boolean retVal = true;
    	
    	if (!game.isPresent()) {
    		retVal = false;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
    	
    	return retVal;
    }

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

        checkGameCode(game, code);
        

        Player player = new Player(name, game.get());
        playerService.savePlayer(player);

        return player;

    }

    @GetMapping(value="/games/{game_id}/players")
    public Player joinGameAsExistingPlayer(@PathVariable("game_id") Long gameID,
    		@RequestParam(value="player_session_id") String playerSessionID) {

    	Optional<Game> game = gameService.findGameByID(gameID);
        Optional<Player> player = playerService.findPlayerBySessionID(playerSessionID);
        
        checkGame(game);

        if (player.isPresent() && game.get().getPlayers().contains(player.get())) {
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
