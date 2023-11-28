package com.example.preproj.service;

import com.example.preproj.model.Role;
import com.example.preproj.model.User;
import com.example.preproj.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepo userRepo;


    public void delete(int id) {
        userRepo.deleteById(id);
    }


    public List<User> allUsers() {
       return userRepo.findAll();
    }


    public boolean save(User user, String[] role) {
        User userFromDB = userRepo.findByUserName(user.getUsername());
        Set<Role> set = new HashSet<>();
        for (String s : role) {
            if (s.equals("ROLE_USER")) {
                set.add(new Role(1L, "ROLE_USER"));
            }
            if (s.equals("ROLE_ADMIN")) {
                set.add(new Role(2L, "ROLE_ADMIN"));
            }
        }
        user.setRoles(set);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }


    public boolean updateSave(User user) {
        userRepo.save(user);
        return true;
    }


    public User show(int id) {
        Optional<User> optionalUser = userRepo.findById(id);
        return optionalUser.get();
    }

}
