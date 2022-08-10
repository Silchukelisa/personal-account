package com.example.preproj.controller;

import com.example.preproj.config.service.UserDetailsService;
import com.example.preproj.model.User;
import com.example.preproj.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailsService userService;


    @PostMapping("/adduser/{role}")
    public ResponseEntity<String> saveUser(@RequestBody User user, @PathVariable("role") String[] roles) {
        userService.save(user, roles);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> list() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        userRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/edit/{role}")
    public ResponseEntity<String> update(@RequestBody User user, @PathVariable("role") String[] roles) {
        userService.save(user, roles);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/eBtn/{id}")
    public ResponseEntity<User> getOne(@PathVariable("id") Integer id) {
        User user = userService.show(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
