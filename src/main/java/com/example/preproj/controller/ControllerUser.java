package com.example.preproj.controller;

import com.example.preproj.model.User;
import com.example.preproj.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String edit(@RequestParam("userId")Integer userId, Model model) {
        User user=userRepo.findById(userId).orElseThrow();
        model.addAttribute("usersEdit",user);
        return "usersEdit";
    }


    @PostMapping("/users/edit")
    public String postEdit(@RequestParam("userId")Integer userEditId,@ModelAttribute("userForm") User userForm, Model model) {
        User user=userRepo.findById(userEditId).orElseThrow();
        user.setName(userForm.getName());
        user.setLastName(userForm.getLastName());
        user.setAge(userForm.getAge());
        user.setEmail(userForm.getEmail());
        model.addAttribute("users", userRepo.save(user));
        return "redirect:/users";
    }


}
