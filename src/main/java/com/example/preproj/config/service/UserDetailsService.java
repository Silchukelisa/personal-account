package com.example.preproj.config.service;


import com.example.preproj.model.Role;
import com.example.preproj.model.User;
import com.example.preproj.repo.RoleRepo;
import com.example.preproj.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private RoleRepo roleRepo;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User myUser = userRepo.findByUserName(username);
        if (myUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return myUser;
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepo.findByUserName(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }
}
