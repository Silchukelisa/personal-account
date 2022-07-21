package com.example.preproj.controller;

import com.example.preproj.model.User;
import com.example.preproj.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerUser {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    @PostMapping("/users/add")
    public String saveUsers(@ModelAttribute("userForm") User userForm, Model model) {
        model.addAttribute("users", userRepo.save(userForm));
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam Integer userId, Model model) {
        userRepo.deleteById(userId);
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }


    @GetMapping("/users/edit")
    public String edit(@RequestParam Integer userId, @ModelAttribute("userForm") User userForm, Model model) {

        User user = userRepo.findById(userId).get();

        model.addAttribute("usersEdit",user);
        return "usersEdit";
    }


    @PostMapping("/users/edit")
    public String postEdit(@RequestParam(name = "userId", required = false) String userId,@ModelAttribute("userForm") User userForm, Model model) {
        System.out.println(userId);
        System.out.println(userForm);
        return "users";
    }


}
