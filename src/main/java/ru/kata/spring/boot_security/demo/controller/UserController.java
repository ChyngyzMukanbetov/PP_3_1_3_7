package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public String getAllUsers(Model model, Principal principal) {
        User user = userService.allUsers().stream().filter(u -> u.getUsername().equals(principal.getName())).findFirst().get();
        model.addAttribute("user", user);
        return "User";
    }

    @GetMapping(value = "/index")
        public String index () {
        return "index";
    }
}
