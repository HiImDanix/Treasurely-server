package com.danielandrej.treasure_hunt;

import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.models.LocationHint;
import com.danielandrej.treasure_hunt.models.Mission;
import com.danielandrej.treasure_hunt.repositories.GameRepository;
import com.danielandrej.treasure_hunt.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final GameRepository gameRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public DataLoader(GameRepository gameRepository, TaskRepository taskRepository) {
        this.gameRepository = gameRepository;
        this.taskRepository = taskRepository;
    }

    public void run(ApplicationArguments args) {
        Game game1 = new Game("My game 1", "AH4K3");
        Game game2 = new Game("Jason's game", "EJ4K3");
        gameRepository.saveAll(
                List.of(game1, game2)
        );

        Mission mission1 = new Mission(game2,
                "Found in the outskirts of the city",
                10,
                new LocationHint(-20.54150, -47.4659, 10),
                "answer1");
        Mission mission2 = new Mission(game2, null, 10, null, "answer2");
        taskRepository.saveAll(
                List.of(mission1, mission2)
        );

        gameRepository.save(game2);

    }
}