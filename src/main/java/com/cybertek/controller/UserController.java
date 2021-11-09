package com.cybertek.controller;

import com.cybertek.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping({"/create", "/add" , "/initialize"})
    public String createUser(Model model){

        model.addAttribute("user", new User());

        return "/user/create";
    }
}
