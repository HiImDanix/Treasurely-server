package com.danielandrej.treasure_hunt.game;

import com.danielandrej.treasure_hunt.player.Player;
import com.danielandrej.treasure_hunt.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class GameController {


    private final GameService gameService;
    private final PlayerService playerService;

    @Autowired
    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }


    @GetMapping(value="game/guess", params={"answer"}, produces="application/json")
    public boolean answerIsCorrect(@RequestParam String answer, HttpServletRequest request) {
        Optional<Player> player = playerService.getPlayerFromRequest(request);
        if (player.isPresent()) {
            boolean correct = gameService.answerIsCorrect(player.get(), answer);
            if (correct) {
//                if (player.get().getGame().isFinished()) {
//                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Game is finished");
                if (player.get().getAnswers().contains(answer)) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "You already answered this question");
                }
                playerService.addAnswer(player.get(), answer);
                if (player.get().getAnswers().size() == player.get().getGame().getAnswers().size()) {
                    System.out.println("Game is finished");
                }
            }
            return correct;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in a game");
        }
    }

}
