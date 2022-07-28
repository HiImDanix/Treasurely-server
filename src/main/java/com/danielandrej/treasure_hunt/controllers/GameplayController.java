package com.danielandrej.treasure_hunt.controllers;

import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.services.GameplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GameplayController {


    private final GameplayService gameplayService;

    @Autowired
    public GameplayController(GameplayService gameplayService) {
        this.gameplayService = gameplayService;
    }

    @GetMapping(value="/players/{player_session_id}/game")
    public Game getGameBySessionID(@PathVariable("player_session_id") String playerSessionID) {
        return gameplayService.getGameBySessionID(playerSessionID);
    }

    @PostMapping(value="gameplay/submit_answer", produces="application/json")
    public void submitQRAnswer(@RequestParam String qr_code,
                                   @RequestParam String player_session_id) {
        // print player
        gameplayService.submitQRAnswer(qr_code, player_session_id);
    }

}
