package com.cybertek.repository;

import com.cybertek.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode = ?1 AND t.taskStatus <> 'COMPLETED' ")
    int totalNonCompleteTask(String projectCode);


    @Query(value = "SELECT COUNT(*) FROM tasks t JOIN projects p on p.id = t.project_id WHERE p.project_code = :projectCode AND t.task_status = 'COMPLETED'", nativeQuery = true)
    int totalCompleteTask(String projectCode);

}
