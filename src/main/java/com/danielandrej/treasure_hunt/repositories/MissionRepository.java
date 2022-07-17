package com.danielandrej.treasure_hunt.repositories;

import com.danielandrej.treasure_hunt.models.Mission;
import com.danielandrej.treasure_hunt.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

}
