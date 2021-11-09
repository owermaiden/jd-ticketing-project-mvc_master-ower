package com.cybertek.bootstrap;

import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.RoleService;
import com.cybertek.service.UserService;
import com.cybertek.utils.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator implements CommandLineRunner {

    RoleService roleService;
    UserService userService;

    @Autowired
    public DataGenerator(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        RoleDTO adminRole = new RoleDTO(1L,"Admin");
        RoleDTO managerRole = new RoleDTO(2L,"Manager");
        RoleDTO employeeRole = new RoleDTO(3L,"Employee");

        roleService.save(adminRole);
        roleService.save(managerRole);
        roleService.save(employeeRole);

        UserDTO user1 = new UserDTO("John", "Kessy", "admin@cybertek.com",
                "abc", true, "7454453458", adminRole, Gender.MALE);
        UserDTO user2 = new UserDTO("John", "Kessy", "admin2@cybertek.com",
                "abc", true, "7454453458", adminRole, Gender.MALE);
        UserDTO user3 = new UserDTO("John", "Kessy", "manager@cybertek.com",
                "abc", true, "7454453457", managerRole, Gender.MALE);
        UserDTO user4 = new UserDTO("John", "Kessy", "employee@cybertek.com",
                "abc", false, "7454453458", employeeRole, Gender.FEMALE);
        UserDTO user5 = new UserDTO("John", "Kessy", "employee2@cybertek.com",
                "abc", false, "7454433458", employeeRole, Gender.MALE);


    }
}
