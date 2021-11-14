package com.cybertek.controller;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import com.cybertek.utils.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/task")
public class TaskController {

    TaskService taskService;
    UserService userService;
    ProjectService projectService;

    public TaskController(TaskService taskService, UserService userService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public String taskCreate(Model model){

        model.addAttribute("task", new TaskDTO());
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findEmployees());

        return "task/create";
    }

    @PostMapping("/create")
    public String insertTask(TaskDTO task){

        taskService.save(task);
        task.setTaskStatus(Status.OPEN);
        task.setAssignedDate(LocalDate.now());

        return "redirect:/task/create";

    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id){

        taskService.deleteById(id);
        return "redirect:/task/create";
    }

    @GetMapping("/update/{id}")
    public String editTask(@PathVariable("id") Long id, Model model){

        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findEmployees());

        return "/task/update";

    }

    @PostMapping("/update/{id}")
    public String updateTask(TaskDTO task){

        taskService.update(task);
        return "redirect:/task/create";  // redirect calls the GetMapping instead of view...
    }

}
