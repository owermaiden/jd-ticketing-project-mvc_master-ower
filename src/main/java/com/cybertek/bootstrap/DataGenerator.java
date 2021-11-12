package com.cybertek.bootstrap;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.service.RoleService;
import com.cybertek.service.UserService;
import com.cybertek.utils.Gender;
import com.cybertek.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataGenerator implements CommandLineRunner {

    RoleService roleService;
    UserService userService;
    ProjectService projectService;

    @Autowired
    public DataGenerator(RoleService roleService, UserService userService, ProjectService projectService) {
        this.roleService = roleService;
        this.userService = userService;
        this.projectService = projectService;
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
        UserDTO user2 = new UserDTO("John", "Kessy", "admin2@cybertek.com",
                "abc", true, "7454453458", adminRole, Gender.MALE);
        UserDTO user3 = new UserDTO("Adem", "Kessy", "manager@cybertek.com",
                "abc", true, "7454453457", managerRole, Gender.MALE);
        UserDTO user4 = new UserDTO("John", "Kessy", "employee@cybertek.com",
                "abc", false, "7454453458", employeeRole, Gender.FEMALE);
        UserDTO user5 = new UserDTO("John", "Ozzy", "employee2@cybertek.com",
                "abc", false, "7454433458", employeeRole, Gender.MALE);

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
        userService.save(user4);
        userService.save(user5);

        // List<UserDTO> managers = userService.findAll().stream().filter(user -> user.getRole().getDescription().equals("manager")).collect(Collectors.toList());

        ProjectDTO project1 = new ProjectDTO("Spring", "PR01", user3,
                LocalDate.now(), LocalDate.now().plusDays(25),
                "Creating controllers", Status.IN_PROGRESS);
        ProjectDTO project2 = new ProjectDTO("Ticketing2", "PR02", user3,
                LocalDate.of(2006,01,12), LocalDate.of(2006, 02, 12),
                "Second normal project", Status.COMPLETE);
        ProjectDTO project3 = new ProjectDTO("Ticketing3", "PR03", user3,
                LocalDate.of(2007,01,12), LocalDate.of(2007, 02, 12),
                "Third normal project", Status.OPEN);

        projectService.save(project1);
        projectService.save(project2);
        projectService.save(project3);



    }
}
