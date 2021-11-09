package com.cybertek.controller;

import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.RoleService;
import com.cybertek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    RoleService roleService;

    @Autowired
    public UserController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping({"/create", "/add" , "/initialize"})
    public String createUser(Model model){

        model.addAttribute("user", new UserDTO());
        var roles = roleService.findAll().stream().map(RoleDTO::getDescription).toArray();
        model.addAttribute("roles", roles);

        return "/user/create";
    }

    @PostMapping("/create")
    public String saveUser(Model model){



        return "redirect:/user/create";
    }
}
