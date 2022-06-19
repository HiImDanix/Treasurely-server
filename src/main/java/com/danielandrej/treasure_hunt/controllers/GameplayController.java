package com.danielandrej.treasure_hunt.controllers;

import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.services.GameService;
import com.danielandrej.treasure_hunt.models.Player;
import com.danielandrej.treasure_hunt.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class GameplayController {


    private final GameService gameService;
    private final PlayerService playerService;

    @Autowired
    public GameplayController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }


    @GetMapping(value="/current_game", produces="application/json")
    public Game getCurrentGame(HttpServletRequest request) {
        Optional<Player> player = playerService.getPlayerFromRequest(request);
        if (player.isPresent()) {
            return player.get().getGame();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in a game");
        }
    }

    @PostMapping(value="games/{game_id}/submit", params={"qr_code"}, produces="application/json")
    public boolean isAnswerCorrect(@PathVariable Long game_id, @RequestParam String qr_code, HttpServletRequest request) {
        Optional<Player> player = playerService.getPlayerFromRequest(request);

        if (!player.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in a game");
        }

        if (player.get().getGame().getId() != game_id) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in this game");
        }

        return gameService.submitTask(player.get(), qr_code);
    }

}
