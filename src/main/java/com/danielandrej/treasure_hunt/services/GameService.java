package com.danielandrej.treasure_hunt.services;

import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.repositories.GameRepository;
import com.danielandrej.treasure_hunt.repositories.shared.RandomString;
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

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;

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

    public void deleteGameByID(Long gameID) {
        gameRepository.deleteById(gameID);
    }

    public Game updateGame(Game oldGame, Game newGame) {
        oldGame.setCode(newGame.getCode());
        oldGame.setName(newGame.getName());
        return gameRepository.save(oldGame);
    }

    public Game updateGameByID(Long gameID, Game newGame) {
        Optional<Game> game = gameRepository.findById(gameID);
        if (game.isPresent()) {
            return updateGame(game.get(), newGame);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
    }

    public Game updateStatus(Game game, Game.Status status) {
        game.setStatus(status);
        return gameRepository.save(game);
    }

    public Game updateGameStatus(Long gameID, Game.Status status) {
        Optional<Game> game = gameRepository.findById(gameID);
        if (game.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        game.get().setStatus(status);
        return gameRepository.save(game.get());
    }
}
