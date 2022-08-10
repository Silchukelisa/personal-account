package com.example.preproj.service;

import com.example.preproj.model.User;

public interface UserService {

     boolean save(User user, String[] role);

     User show(int id);

     void delete(int id);

}
