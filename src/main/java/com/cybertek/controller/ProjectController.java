package com.cybertek.controller;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import com.cybertek.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;



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
    public String saveProject(ProjectDTO project){

        projectService.save(project);
        project.setProjectStatus(Status.OPEN);

        return "redirect:/project/create";  // redirect calls the GetMapping instead of view...
    }

    @GetMapping("/delete/{projectCode}")
    public String deleteProjectById(@PathVariable("projectCode") String projectcode, Model model){

        projectService.deleteById(projectcode);
        return "redirect:/project/create";  // redirect calls the GetMapping instead of view...

    }

    @GetMapping("/complete/{projectcode}")
    public String copleteProject(@PathVariable("projectcode") String projectCode, Model model){

        projectService.complete(projectService.findById(projectCode));
        return "redirect:/project/create";
    }


    @GetMapping("/update/{projectCode}")
    public String editProject(@PathVariable("projectCode") String projectCode, Model model){

        model.addAttribute("project", projectService.findById(projectCode));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("managers", userService.findManagers());

        return "/project/update";

    }

    @PostMapping("/update/{projectCode}")
    public String updateProject(ProjectDTO project){

        projectService.update(project);
        return "redirect:/project/create";  // redirect calls the GetMapping instead of view...
    }



    @GetMapping("/manager/status")
    public String getProjectsByManager(Model model){

        UserDTO manager = userService.findById("employee@cybertek.com");
        List<ProjectDTO> projects = getCountedListOfProjectDTO(manager);
        model.addAttribute("projects" , projects);

        return "/manager/project-status";
    }

    List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager){

        List<ProjectDTO> list = projectService
                .findAll()
                .stream()
                .filter(x-> x.getAssignedManager().equals(manager))
                .map(x-> {

                    List<TaskDTO> tasksList = taskService.findByManager(manager);
                    int comlpleteTask = (int) tasksList.stream().filter(t -> t.getProject().equals(x) && t.getTaskStatus() == Status.COMPLETE).count();
                    int inComlpleteTask = (int) tasksList.stream().filter(t -> t.getProject().equals(x) && t.getTaskStatus() != Status.COMPLETE).count();

                    x.setCompleteTaskCounts(comlpleteTask);
                    x.setUnfinishedTaskCounts(inComlpleteTask);


                    return x;

                            /*
                            new ProjectDTO(  x.getProjectName(),
                                            x.getProjectCode(),
                                            userService.findById(x.getAssignedManager().getUserName()),
                                            x.getStartDate(),
                                            x.getEndDate(),
                                            x.getProjectDetail(),
                                            x.getProjectStatus(),
                                            comlpleteTask,
                                            inComlpleteTask);
                            */

                        }).collect(Collectors.toList());
        return list;
    }
}
