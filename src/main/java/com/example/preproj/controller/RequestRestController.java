package com.example.preproj.controller;

import com.example.preproj.model.User;
import com.example.preproj.service.EmailService;
import com.example.preproj.service.RequestService;
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

    @Autowired
    private RequestService requestService;

    @GetMapping("/")
    public ResponseEntity<List<User>> list() {
        return new ResponseEntity<>(requestService.allRequest(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boolean> button(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(requestService.showBool(id), HttpStatus.OK);
    }

    @GetMapping("/mail/{id}")
    public ResponseEntity<String> mail(@PathVariable("id") Integer id) {
        requestService.mailAdmin(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Integer id) {
        requestService.acceptedApplication(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        requestService.rejectedApplication(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
