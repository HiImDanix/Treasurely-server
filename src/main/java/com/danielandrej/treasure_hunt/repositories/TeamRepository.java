package com.danielandrej.treasure_hunt.repositories;

import com.danielandrej.treasure_hunt.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // Find PlayerGroup by name
    Team findPlayerGroupByName(String name);


}
