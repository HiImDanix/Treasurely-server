package com.danielandrej.treasure_hunt.controllers;

import com.danielandrej.treasure_hunt.services.GameplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GameplayController {


    private final GameplayService gameplayService;

    @Autowired
    public GameplayController(GameplayService gameplayService) {
        this.gameplayService = gameplayService;
    }

    /**
     * Submit a QR code answer.
     * @param qr_code QR code value
     * @param player_session_id Player session ID
     * @throws ResponseStatusException 404 if player is not in game
     */
    @PostMapping(value="gameplay/submit_answer", produces="application/json")
    public void submitQRAnswer(@RequestParam String qr_code,
                                   @RequestParam String player_session_id) {
        // print player
        gameplayService.submitQRAnswer(qr_code, player_session_id);
    }

}
