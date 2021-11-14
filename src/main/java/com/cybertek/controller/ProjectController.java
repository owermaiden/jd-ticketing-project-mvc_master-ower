package com.cybertek.controller;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.service.UserService;
import com.cybertek.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;



    @GetMapping("/create")
    public String createProject(Model model){

        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("managers", userService.findManagers());
        //model.addAttribute("managers", userService.findAll().stream().filter(user -> user.getRole().getDescription().equals("manager")).collect(Collectors.toList()));

        return "/project/create";
    }

    @PostMapping("/create")
    // public String saveUser(@ModelAttribute("user") UserDTO user, Model model){   // we dont need to use @ModelAttribute anymore......
    public String saveProject(ProjectDTO project, Model model){

        projectService.save(project);
        project.setProjectStatus(Status.OPEN);

        return "redirect:/project/create";  // redirect calls the GetMapping instead of view...
    }

    @GetMapping("/update/{projectCode}")
    public String editProject(@PathVariable("projectCode") String projectCode, Model model){

        model.addAttribute("project", projectService.findById(projectCode));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("managers", userService.findManagers());

        return "/project/update";

    }

    @PostMapping("/update/{projectCode}")
    public String updateProject(@PathVariable("projectCode") String projectcode,ProjectDTO project, Model model){

        projectService.update(project);
        return "redirect:/project/create";  // redirect calls the GetMapping instead of view...
    }

    @GetMapping("/delete/{projectCode}")
    public String deleteProjectById(@PathVariable("projectCode") String projectcode, Model model){

        projectService.deleteById(projectcode);
        return "redirect:/project/create";  // redirect calls the GetMapping instead of view...

    }
}
