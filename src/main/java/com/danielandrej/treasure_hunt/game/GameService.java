package com.danielandrej.treasure_hunt.game;

import com.danielandrej.treasure_hunt.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return gameRepository.findGameByCode(code);
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

    public boolean answerIsCorrect(Player player, String answer) {
        Game game = player.getGame();
        return game.getAnswers().contains(answer);
    }
}
