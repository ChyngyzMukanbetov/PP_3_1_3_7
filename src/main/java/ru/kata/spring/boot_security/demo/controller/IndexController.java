package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class IndexController {
    @Autowired
    private final UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("user", userService.getById(16L));
        return "index";
    }
}
