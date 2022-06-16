package com.danielandrej.treasure_hunt.repositories;

import com.danielandrej.treasure_hunt.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
