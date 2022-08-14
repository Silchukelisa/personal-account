package com.example.preproj.service;

import com.example.preproj.model.Request;
import com.example.preproj.model.Role;
import com.example.preproj.model.User;
import com.example.preproj.repo.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RequestService {

    @Autowired
    UserService userService;

    @Autowired
    RequestRepo requestRepo;


    public void add(int userId) {
        requestRepo.save(new Request(userId));
    }


    public List<Request> reqUsers() {
        return requestRepo.findAll();
    }


    public void update(int userId) {
        Set<Role> set = new HashSet<>();
        User user = userService.show(userId);
        set.add(new Role(1L, "ROLE_USER"));
        set.add(new Role(2L, "ROLE_ADMIN"));
        user.setRoles(set);
        userService.updateSave(user);
        requestRepo.deleteById(userId);

    }


    public void delete(int userId) {
        requestRepo.deleteById(userId);
    }


    public Request show(int id) {
        Optional<Request> optionalUser = requestRepo.findById(id);
        return optionalUser.get();
    }
}
