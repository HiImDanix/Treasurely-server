package com.danielandrej.treasure_hunt.repositories;

import com.danielandrej.treasure_hunt.models.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

}
