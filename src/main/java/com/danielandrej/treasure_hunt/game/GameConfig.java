package com.danielandrej.treasure_hunt.game;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class GameConfig {

    @Bean
    CommandLineRunner commandLineRunner(GameRepository repository) {
        return args -> {
            Game game1 = new Game("My game 1", "A4HK6M5");
            Game game2 = new Game("Jason's game", "B6S6GST");
            game2.setAnswers(Set.of("answer1", "answer2"));
            repository.saveAll(
                    List.of(game1, game2)
            );
        };
    }
}
