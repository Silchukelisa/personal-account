package com.example.preproj.controller;

import com.example.preproj.model.Request;
import com.example.preproj.model.User;
import com.example.preproj.service.request.RequestService;
import com.example.preproj.service.user.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("requests/")
public class RequestRestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserServiceImp userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> list() {
        List<Request> list = requestService.reqUsers();
        List<User> userList = new ArrayList<>();
        for (Request request : list) {
            userList.add(userService.show(request.getUserId()));
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boolean> button(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(requestService.show(id) != null, HttpStatus.OK);
    }
}
