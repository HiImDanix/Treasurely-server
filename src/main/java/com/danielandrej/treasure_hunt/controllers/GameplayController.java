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

    /**
     * Submit a QR code answer.
     * @param qr_code
     * @param player_session_id
     * @throws ResponseStatusException 404 if player is not in game
     * @return
     */
    @PostMapping(value="games/{game_id}/submit", produces="application/json")
    public boolean isAnswerCorrect(@RequestParam String qr_code,
                                   @RequestParam String player_session_id) {
        Optional<Player> player = playerService.findPlayerBySessionID(player_session_id);

        if (!player.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in a game");
        }

        return gameService.submitTask(player.get(), qr_code);
    }

}
