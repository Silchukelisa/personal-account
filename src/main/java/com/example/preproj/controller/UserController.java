package com.example.preproj.controller;

import com.example.preproj.config.service.UserDetailsService;
import com.example.preproj.model.User;
import com.example.preproj.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

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

        userService.saveUser(userForm);

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
        model.addAttribute("adminUser", user);
        return "adminUser";

    }

    @GetMapping("/admin/users")
    public String users(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    @PostMapping("/admin/users/add")
    public String saveUsers(@ModelAttribute("userForm") User userForm, Model model) {
        userService.saveUser(userForm);
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    @GetMapping("/admin/users/delete")
    public String deleteUser(@RequestParam Integer userId, Model model) {
        userRepo.deleteById(userId);
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }


    @GetMapping("/admin/users/edit")
    public String edit(@RequestParam("userId") Integer userId, Model model) {
        User user = userRepo.findById(userId).orElseThrow();
        model.addAttribute("usersEdit", user);
        return "usersEdit";
    }


    @PostMapping("/admin/users/edit")
    public String postEdit(@RequestParam("userId") Integer userEditId, @ModelAttribute("userForm") User userForm, Model model) {
        User user = userRepo.findById(userEditId).orElseThrow();
        user.setName(userForm.getName());
        user.setLastName(userForm.getLastName());
        user.setAge(userForm.getAge());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUserName(userForm.getUserName());
        model.addAttribute("users", userRepo.save(user));
        return "redirect:/admin/users";
    }


}
