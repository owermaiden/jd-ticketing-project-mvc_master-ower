package com.cybertek.controller;

import com.cybertek.dto.UserDTO;
import com.cybertek.service.RoleService;
import com.cybertek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    RoleService roleService;
    UserService userService;

    @Autowired
    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping({"/create", "/add" , "/initialize"})
    public String createUser(Model model){

        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("users", userService.findAll());

        return "/user/create";
    }

    @PostMapping("create")
    // public String saveUser(@ModelAttribute("user") UserDTO user, Model model){   // we dont need to use @ModelAttribute anymore......
    public String saveUser(UserDTO user, Model model){

        userService.save(user);

        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("users", userService.findAll());

        return "redirect:/user/create";
    }

    @GetMapping("/update/{username}")
    public String editUser(@PathVariable("username") String username, Model model){

        model.addAttribute("user", userService.findById(username));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.findAll());

        return "/user/update";

    }

    @PostMapping("/update/{username}")
    public String updateUser(@PathVariable("username") String username,UserDTO user, Model model){

        userService.update(user);

        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("users", userService.findAll());

        return "/user/create";
    }

    @GetMapping("/delete/{username}")
    public String deleteUserById(@PathVariable("username") String username, Model model){

        userService.deleteById(username);
        model.addAttribute("user", new UserDTO());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.findAll());

        return "/user/create";

    }








}
