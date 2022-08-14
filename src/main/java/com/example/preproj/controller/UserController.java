package com.example.preproj.controller;

import com.example.preproj.model.User;
import com.example.preproj.repo.UserRepo;
import com.example.preproj.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/regSave")
    public String registration(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
        String[] role  = new String[] {"ROLE_USER"};
        userService.save(userForm, role);

        return "redirect:/user";
    }


    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        User user = userRepo.findByUserName(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        User user = userRepo.findByUserName(principal.getName());
        model.addAttribute("admin", user);
        model.addAttribute("users", userRepo.findAll());
        return "admin";

    }

}
