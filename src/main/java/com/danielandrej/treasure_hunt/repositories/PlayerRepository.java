package com.danielandrej.treasure_hunt.repositories;

import com.danielandrej.treasure_hunt.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    Optional<Player> findBySessionID(String sessionID);
    Optional<Player> findById(Long id);
    Optional<Player> findByName(String name);

}
