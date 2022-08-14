package com.example.preproj.service.request;

import com.example.preproj.model.Request;

import java.util.List;


public interface RequestService {
    void add(int userId);

    List<Request> reqUsers();

    void update(int userId);

    void delete(int userId);

    public Request show(int id);
}
