package com.danielandrej.treasure_hunt.controllers;

import com.danielandrej.treasure_hunt.services.GameService;
import com.danielandrej.treasure_hunt.models.Player;
import com.danielandrej.treasure_hunt.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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


    @PostMapping(value="games/answer", params={"qr_code"}, produces="application/json")
    public boolean answerIsCorrect(@RequestParam String qr_code, HttpServletRequest request) {
        Optional<Player> player = playerService.getPlayerFromRequest(request);
        if (player.isPresent()) {
            return gameService.submitTask(player.get(), qr_code);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in a game");
        }
    }

}
