package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private final UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String registration() {
        return "RegistrationPage";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        user.setActive(true);
        //Role roles = roleRepository.findByRolename("ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRolename("ADMIN"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/login";
    }

//    @PostMapping("/addroles")
//    public void addRoles() {
//        roleRepository.save(new Role("ADMIN"));
//        roleRepository.save(new Role("USER"));
//    }
}
