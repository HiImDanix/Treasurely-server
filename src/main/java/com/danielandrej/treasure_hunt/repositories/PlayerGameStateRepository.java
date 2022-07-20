package com.danielandrej.treasure_hunt.repositories;

import com.danielandrej.treasure_hunt.models.PlayerGameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerGameStateRepository extends JpaRepository<PlayerGameState, Long> {

}
