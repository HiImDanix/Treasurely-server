package com.danielandrej.treasure_hunt.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT r from Game r WHERE r.code = ?1")
    Optional<Game> findGameByCode(String code);

}
