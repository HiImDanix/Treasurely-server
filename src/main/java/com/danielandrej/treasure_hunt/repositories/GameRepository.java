package com.danielandrej.treasure_hunt.repositories;

import com.danielandrej.treasure_hunt.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByCode(String code);

}
