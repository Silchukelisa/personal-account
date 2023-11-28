package com.example.preproj.service;

import com.example.preproj.model.User;
import com.example.preproj.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

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
}
