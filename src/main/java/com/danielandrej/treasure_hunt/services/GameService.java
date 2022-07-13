package com.danielandrej.treasure_hunt.services;

import com.danielandrej.treasure_hunt.repositories.shared.RandomString;
import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.models.Player;
import com.danielandrej.treasure_hunt.repositories.PlayerRepository;
import com.danielandrej.treasure_hunt.models.Mission;
import com.danielandrej.treasure_hunt.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;

    }

    public List<Game> getRooms() {
        return gameRepository.findAll();
    }

    public Optional<Game> findGameByCode(String code) {
        return gameRepository.findByCode(code);
    }

    public Optional<Game> findGameByID(Long gameID) {
        return gameRepository.findById(gameID);
    }

    public Game createGame(Game game) {
        RandomString gen = new RandomString(8, ThreadLocalRandom.current());
        String code;
        do {
            code = gen.nextString().toUpperCase();
        } while (findGameByCode(code).isPresent());
        game.setCode(code);
        return gameRepository.save(game);
    }

    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }

    public Game updateGame(Game oldGame, Game newGame) {
        oldGame.setCode(newGame.getCode());
        oldGame.setName(newGame.getName());
        return gameRepository.save(oldGame);
    }

    // update status of game
    public Game updateStatus(Game game, Game.Status status) {
        game.setStatus(status);
        return gameRepository.save(game);
    }

    public boolean submitTask(Player player, String answer) {
        Game game = player.getGame();
         // if game not in progress, throw exception
        if (game.getStatus() != Game.Status.IN_PROGRESS) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game is not in progress");
        }

        Optional<Mission> completedTask = game.getMissions().stream().filter(t -> t.getQrCodeValue().equals(answer)).findFirst();
        // TODO: Custom exceptions
        if (completedTask.isPresent()) {
            if (player.getCompletedMissions().contains(completedTask.get())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "You already answered this question");
            }

            player.addCompletedMission(completedTask.get());
            playerRepository.save(player);

            //if all tasks are completed, finish game
            if (player.getCompletedMissions().size() == game.getMissions().size()) {
                updateStatus(game, Game.Status.FINISHED);
            }

            return true;
        }

        return false;
    }
}
