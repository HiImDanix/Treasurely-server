package com.danielandrej.treasure_hunt;

import com.danielandrej.treasure_hunt.models.Game;
import com.danielandrej.treasure_hunt.repositories.GameRepository;
import com.danielandrej.treasure_hunt.models.Task;
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

        Task task1 = new Task("answer1");
        Task task2 = new Task("answer2");
        taskRepository.saveAll(
                List.of(task1, task2)
        );

        game2.addTask(task1);
        game2.addTask(task2);
        gameRepository.save(game2);

    }
}