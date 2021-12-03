package com.cybertek.implementation;

import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.Task;
import com.cybertek.enums.Status;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.repository.TaskRepository;
import com.cybertek.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceIplm implements TaskService {

    private TaskMapper taskMapper;
    private TaskRepository taskRepository;

    public TaskServiceIplm(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id).get();
        return taskMapper.convertToDto(task);
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
        Task task = taskRepository.getOne(dto.getId());
        Task convertedTask = taskMapper.convertToEntity(dto);
        convertedTask.setTaskStatus(task.getTaskStatus());
        convertedTask.setAssignedDate(task.getAssignedDate());
        convertedTask.setId(task.getId());
        taskRepository.save(convertedTask);
    }

    @Override
    public void delete(Long id) {
        Task task = taskRepository.getOne(id);
        task.setIsDeleted(true);
        taskRepository.save(task);
    }

}
