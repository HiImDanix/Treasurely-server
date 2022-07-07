package com.danielandrej.treasure_hunt.services;

import com.danielandrej.treasure_hunt.models.Player;
import com.danielandrej.treasure_hunt.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<Player> findPlayerBySessionID(String sessionID) {
        return playerRepository.findPlayerBySessionID(sessionID);
    }

    public void savePlayer(Player player) {
        playerRepository.save(player);
    }
    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }

}
