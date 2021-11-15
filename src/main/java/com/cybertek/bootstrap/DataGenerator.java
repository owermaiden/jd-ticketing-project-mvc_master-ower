package com.cybertek.bootstrap;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.service.RoleService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import com.cybertek.utils.Gender;
import com.cybertek.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataGenerator implements CommandLineRunner {

    RoleService roleService;
    UserService userService;
    ProjectService projectService;
    TaskService taskService;

    @Autowired
    public DataGenerator(RoleService roleService, UserService userService, ProjectService projectService, TaskService taskService) {
        this.roleService = roleService;
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) throws Exception {

        RoleDTO adminRole = new RoleDTO(1L,"Admin");
        RoleDTO managerRole = new RoleDTO(2L,"Manager");
        RoleDTO employeeRole = new RoleDTO(3L,"Employee");

        roleService.save(adminRole);
        roleService.save(managerRole);
        roleService.save(employeeRole);

        UserDTO user1 = new UserDTO("John", "Mary", "admin@cybertek.com",
                "abc", true, "7454453458", adminRole, Gender.MALE);
        UserDTO user2 = new UserDTO("Mike", "Hesta", "manageer@cybertek.com",
                "abc", true, "7454453458", managerRole, Gender.MALE);
        UserDTO user3 = new UserDTO("Adem", "Kessy", "manager@cybertek.com",
                "abc", true, "7454453457", managerRole, Gender.MALE);
        UserDTO user4 = new UserDTO("Ower", "Maiden", "employee@cybertek.com",
                "abc", false, "7454453458", employeeRole, Gender.FEMALE);
        UserDTO user5 = new UserDTO("Yalan", "Dunya", "employee2@cybertek.com",
                "abc", false, "7454433458", managerRole, Gender.MALE);
        UserDTO user6 = new UserDTO("Ozzy", "False", "employee3@cybertek.com",
                "abc", false, "7454433458", employeeRole, Gender.MALE);
        UserDTO user7 = new UserDTO("Enchor", "Mask", "employee4@cybertek.com",
                "abc", false, "7454433458", managerRole, Gender.FEMALE);
        UserDTO user8 = new UserDTO("Create", "Form", "employee5@cybertek.com",
                "abc", false, "7454433458", employeeRole, Gender.MALE);

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
        userService.save(user4);
        userService.save(user5);
        userService.save(user6);
        userService.save(user7);
        userService.save(user8);

        // List<UserDTO> managers = userService.findAll().stream().filter(user -> user.getRole().getDescription().equals("manager")).collect(Collectors.toList());

        ProjectDTO project1 = new ProjectDTO("Spring", "PR01", user3,
                LocalDate.now(), LocalDate.now().plusDays(25),
                "Creating controllers", Status.IN_PROGRESS);
        ProjectDTO project2 = new ProjectDTO("Ticketing2", "PR02", user2,
                LocalDate.of(2006,01,12), LocalDate.of(2006, 02, 12),
                "Second normal project", Status.COMPLETE);
        ProjectDTO project3 = new ProjectDTO("Ticketing3", "PR03", user2,
                LocalDate.of(2007,01,12), LocalDate.of(2007, 02, 12),
                "Third normal project", Status.OPEN);

        projectService.save(project1);
        projectService.save(project2);
        projectService.save(project3);

        TaskDTO task1 = new TaskDTO(project1, user1, "Konusu", "alıstırma", Status.OPEN, LocalDate.of(2006, 02, 12));
        TaskDTO task2 = new TaskDTO(project2, user2, "Acayip", "alıstırma2", Status.OPEN, LocalDate.of(2006, 02, 12));


        taskService.save(task1);
        taskService.save(task2);


    }
}
