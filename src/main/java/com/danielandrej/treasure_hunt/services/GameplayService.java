package com.danielandrej.treasure_hunt.services;

import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.models.Mission;
import com.danielandrej.treasure_hunt.models.Player;
import com.danielandrej.treasure_hunt.models.PlayerGameState;
import com.danielandrej.treasure_hunt.repositories.PlayerGameStateRepository;
import com.danielandrej.treasure_hunt.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class GameplayService {

    private final PlayerRepository playerRepository;
    private final PlayerGameStateRepository playerGameStateRepository;

    @Autowired
    public GameplayService(PlayerRepository playerRepository, PlayerGameStateRepository playerGameStateRepository) {
        this.playerRepository = playerRepository;
        this.playerGameStateRepository = playerGameStateRepository;
    }

    public void submitQRAnswer(String qr_code, String playerSessionID) {
        Optional<Player> player = playerRepository.findBySessionID(playerSessionID);

        if (player.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not in a game!");
        }

        // If in a group, get group's game state else the player's game state
        PlayerGameState gameState;
        if (player.get().getTeam() != null) {
            gameState = player.get().getTeam().getPlayerGameState();
        } else {
            gameState = player.get().getPlayerGameState();
        }

        Game game = player.get().getGame();

        Optional<Mission> completedTask = game.getMissions().stream().filter(t -> t.getQrCodeValue().equals(qr_code)).findFirst();

        if (completedTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "QR code not found");
        }

        // if already completed, throw error
        if (gameState.getCompletedMissions().contains(completedTask.get())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already completed");
        }

        // add to completed missions
        gameState.getCompletedMissions().add(completedTask.get());
        playerGameStateRepository.save(gameState);
    }
}
