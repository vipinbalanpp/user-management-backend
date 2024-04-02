package com.vipin.usermanagementbackend.controller;

import com.vipin.usermanagementbackend.entity.User;
import com.vipin.usermanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Mycontroller {
    @Autowired
    UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        userRepository.save(user);
        return new ResponseEntity<>("user created", HttpStatus.CREATED);
    }
}
