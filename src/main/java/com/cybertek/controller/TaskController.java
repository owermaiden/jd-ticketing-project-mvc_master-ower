//package com.cybertek.controller;
//
//import com.cybertek.dto.TaskDTO;
//import com.cybertek.utils.Status;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.time.LocalDate;
//import java.util.UUID;
//
//@Controller
//@RequestMapping("/task")
//public class TaskController {
//
//    TaskService taskService;
//    UserService userService;
//    ProjectService projectService;
//
//    public TaskController(TaskService taskService, UserService userService, ProjectService projectService) {
//        this.taskService = taskService;
//        this.userService = userService;
//        this.projectService = projectService;
//    }
//
//    @GetMapping("/create")
//    public String taskCreate(Model model){
//
//        model.addAttribute("task", new TaskDTO());
//        model.addAttribute("tasks", taskService.findAll());
//        model.addAttribute("projects", projectService.findAll());
//        model.addAttribute("employees", userService.findEmployees());
//
//        return "task/create";
//    }
//
//    @PostMapping("/create")
//    public String insertTask(TaskDTO task){
//
//        task.setTaskStatus(Status.OPEN);
//        task.setAssignedDate(LocalDate.now());
//        task.setId(UUID.randomUUID().getMostSignificantBits());  // we let spring generate İD....
//        taskService.save(task);
//
//        return "redirect:/task/create";
//
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteById(@PathVariable("id") Long id){
//
//        taskService.deleteById(id);
//        return "redirect:/task/create";
//    }
//
//    @GetMapping("/update/{id}")
//    public String editTask(@PathVariable("id") Long id, Model model){
//
//        model.addAttribute("task", taskService.findById(id));
//        model.addAttribute("tasks", taskService.findAll());
//        model.addAttribute("projects", projectService.findAll());
//        model.addAttribute("employees", userService.findEmployees());
//
//        return "/task/update";
//
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateTask(TaskDTO task){
//
//        taskService.update(task);
//        return "redirect:/task/create";  // redirect calls the GetMapping instead of view...
//    }
//
//}
