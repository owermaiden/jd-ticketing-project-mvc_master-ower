package com.cybertek.repository;

import com.cybertek.entity.Project;
import com.cybertek.entity.Task;
import com.cybertek.entity.User;
import com.cybertek.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByProject(Project project);

    List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, User user);

    List<Task> findAllByProjectAssignedManager(User user);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode = ?1 AND t.taskStatus <> 'COMPLETED' ")
    int totalNonCompleteTask(String projectCode);


    @Query(value = "SELECT COUNT(*) FROM tasks t JOIN projects p on p.id = t.project_id WHERE p.project_code = :projectCode AND t.task_status = 'COMPLETED'", nativeQuery = true)
    int totalCompleteTask(String projectCode);

}
