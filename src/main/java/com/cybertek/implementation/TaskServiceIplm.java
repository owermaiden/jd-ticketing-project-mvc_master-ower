package com.cybertek.implementation;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.Task;
import com.cybertek.enums.Status;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.repository.TaskRepository;
import com.cybertek.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceIplm implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final ProjectMapper projectMapper;

    public TaskServiceIplm(TaskMapper taskMapper, TaskRepository taskRepository, ProjectMapper projectMapper) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public TaskDTO findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(taskMapper::convertToDto).orElse(null);
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO dto) {
        dto.setTaskStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        Task task = taskMapper.convertToEntity(dto);
        taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO dto) {
        Task task = taskRepository.getOne(dto.getId());         // we could also use findById() which returns Optional
        Task convertedTask = taskMapper.convertToEntity(dto);
        convertedTask.setTaskStatus(task.getTaskStatus());
        convertedTask.setAssignedDate(task.getAssignedDate());
        convertedTask.setId(task.getId());
        taskRepository.save(convertedTask);
    }

    @Override
    public void delete(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            task.get().setIsDeleted(true);
            taskRepository.save(task.get());
        }
    }

    @Override
    public int totalNonCompletedTasks(String projectCode) {
        return taskRepository.totalNonCompleteTask(projectCode);
    }

    @Override
    public int totalCompletedTasks(String projectCode) {
        return taskRepository.totalCompleteTask(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO project) {
        listAllByProject(project).forEach(taskDTO -> delete(taskDTO.getId()));
    }

    public List<TaskDTO> listAllByProject(ProjectDTO project){
        List<Task> tasks = taskRepository.findAllByProject(projectMapper.convertToEntity(project));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

}
