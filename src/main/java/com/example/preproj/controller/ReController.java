package com.example.preproj.controller;

import com.example.preproj.service.UserServiceImp;
import com.example.preproj.model.User;
import com.example.preproj.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("actions/")
public class ReController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserServiceImp userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> list() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{role}")
    public ResponseEntity<String> saveUser(@RequestBody User user, @PathVariable("role") String[] roles) {
        userService.save(user, roles);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/{role}")
    public ResponseEntity<String> update(@RequestBody User user, @PathVariable("role") String[] roles) {
        userService.save(user, roles);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable("id") Integer id) {
        User user = userService.show(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
