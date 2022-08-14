package com.example.preproj.service.user;

import com.example.preproj.model.User;

import java.util.List;

public interface UserService {

     boolean save(User user, String[] role);

     boolean updateSave(User user);

     User show(int id);

     void delete(int id);

     List<User> allUsers();


}
