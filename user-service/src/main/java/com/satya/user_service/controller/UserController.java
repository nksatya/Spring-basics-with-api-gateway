package com.satya.user_service.controller;

import com.satya.user_service.model.User;
import com.satya.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("get/id/{userId}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Integer userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/email/{userId}")
    public ResponseEntity<String> emailUser(@PathVariable Integer userId){
        return userService.emailUser(userId);
    }

    @PostMapping("create")
    public ResponseEntity<String> createNewUser(@RequestBody User user){
        return userService.createNewUser(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser(){
        return userService.getAllUser();
    }
    @GetMapping("get/{emailId}")
    public ResponseEntity<String> getUserByEmail(@PathVariable String emailId){
        return userService.getPasswordByEmail(emailId);
    }

}
