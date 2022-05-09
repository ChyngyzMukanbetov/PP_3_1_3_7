package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private final UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    Set<Role> roles1 = new HashSet<>();

    @Autowired
    public AdminController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.allUsers());
        return "Users2";
    }

    @GetMapping(value = "/add")
    public String addPage(Model model) {
        return "AddUser";
    }

    @PostMapping(value = "/add")
    public String addUser(
            @RequestParam("roles2") String[] roles2,
            @ModelAttribute("user") User user)
    {
        user.setRoles(null);
        roles1.clear();

        System.out.println(roles2[0]);
        for (String role: roles2)
        {
            System.out.println(role);
            roles1.add(roleRepository.findByRolename(role));
        }
        user.setRoles(roles1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user")
    public String getUser(Model model, Principal principal) {
        User user = userService.allUsers().stream().filter(u -> u.getUsername().equals(principal.getName())).findFirst().get();
        model.addAttribute("user", user);
        return "AdminUser";
    }

//    @ResponseBody
//    @GetMapping(value = "/findUser/{id}")
//    public User findUser(ModelMap model, @PathVariable long id) {
//        User user = userService.getById(id);
//        model.addAttribute("user", user);
//        return user;
//    }

//    @GetMapping(value = "/edit/{id}")
//    public String editPage(@PathVariable("id") long id, Model model) {
//        model.addAttribute("user", userService.getById(id));
//        return "EditUser";
//    }

//    @ResponseBody
//    @GetMapping(value = "/edit/{id}")
//    public User editPage(ModelMap model, @PathVariable("id") long id) {
//        User user = userService.getById(id);
//        return user;
//    }

    @PostMapping(value = "/edit")
    public String editUser (
            @RequestParam("roles3") String[] roles3,
            @ModelAttribute("user") User user) {

        user.setRoles(null);
        roles1.clear();

        System.out.println(roles3[0]);
        for (String role: roles3)
        {
            System.out.println(role);
            roles1.add(roleRepository.findByRolename(role));
        }
        user.setRoles(roles1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(userService.getById(id));
        return "redirect:/admin";
    }

    @GetMapping(value = "/index")
    public String index () {
        return "index";
    }
}
