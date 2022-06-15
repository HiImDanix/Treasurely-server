package com.danielandrej.treasure_hunt.player;

import com.danielandrej.treasure_hunt.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    @Query("SELECT s from Player s WHERE s.sessionID = ?1")
    Optional<Player> findPlayermBySessionID(String sessionID);

}
