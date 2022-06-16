package com.danielandrej.treasure_hunt.game;

import com.danielandrej.treasure_hunt.player.Player;
import com.danielandrej.treasure_hunt.player.PlayerRepository;
import com.danielandrej.treasure_hunt.task.Task;
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

    public boolean submitTask(Player player, String answer) {
        Game game = player.getGame();
        // if game finished, throw exception
//        if (game.isFinished()) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Game is finished");
//        }
        Optional<Task> completedTask = game.getTasks().stream().filter(t -> t.getQrCodeValue().equals(answer)).findFirst();
        // TODO: Custom exceptions
        if (completedTask.isPresent()) {
            if (player.getCompletedTasks().contains(completedTask.get())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "You already answered this question");
            }

            player.addCompletedTask(completedTask.get());
            playerRepository.save(player);

            //if all tasks are completed, finish game
            if (player.getCompletedTasks().size() == game.getTasks().size()) {
//                game.setFinished(true);
                System.out.println("Game finished");
            }

            return true;
        }

        return false;
    }
}
