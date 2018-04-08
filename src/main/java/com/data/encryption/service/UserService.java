package com.data.encryption.service;

import com.data.encryption.entity.User;
import com.data.encryption.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2018/4/2.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUserById(String id){
        return userRepository.getUserById(id);
    }

    public List<User> getAllUser(){
        return userRepository.getAllUser();
    }

}