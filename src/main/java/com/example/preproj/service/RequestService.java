package com.example.preproj.service;

import com.example.preproj.model.Request;
import com.example.preproj.model.Role;
import com.example.preproj.model.User;
import com.example.preproj.repo.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RequestService {

    @Autowired
    UserService userService;

    @Autowired
    RequestRepo requestRepo;

    @Autowired
    private EmailService emailService;

    public void update(int userId) {
        Set<Role> set = new HashSet<>();
        User user = userService.show(userId);
        set.add(new Role(1L, "ROLE_USER"));
        set.add(new Role(2L, "ROLE_ADMIN"));
        user.setRoles(set);
        userService.updateSave(user);
        requestRepo.deleteById(userId);

    }

    public List<User> allRequest() {
        List<Request> list = requestRepo.findAll();
        List<User> userList = new ArrayList<>();
        for (Request request : list) {
            userList.add(userService.show(request.getUserId()));
        }
        return userList;
    }


    public Request show(int id) {
        Optional<Request> optionalUser = requestRepo.findById(id);
        return optionalUser.get();
    }

    public boolean showBool(int id) {
        return show(id) != null;
    }

    public void mailAdmin(int id) {
        requestRepo.save(new Request(id));
        emailService.mailAdmin(id);
    }

    public void acceptedApplication(int id) {
        emailService.acceptedApplication(id);
        update(id);
    }

    public void rejectedApplication(int id) {
        emailService.rejectedApplication(id);
        requestRepo.deleteById(id);
    }
}
