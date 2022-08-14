package com.example.preproj.controller;

import com.example.preproj.model.User;
import com.example.preproj.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("requests/")
public class RequestRestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public ResponseEntity<List<User>> list() {
        return new ResponseEntity<>(emailService.allRequest(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boolean> button(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(emailService.show(id), HttpStatus.OK);
    }

    @GetMapping("/mail/{id}")
    public ResponseEntity<String> mail(@PathVariable("id") Integer id) {
        emailService.request(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Integer id) {
        emailService.acceptedApplication(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        emailService.rejectedApplication(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
