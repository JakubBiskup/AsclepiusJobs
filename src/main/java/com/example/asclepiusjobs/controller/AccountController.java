package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @DeleteMapping(value = "/delete-my-account")
    public ResponseEntity deleteMyAccount(){
        String loggedInUserEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser= userService.getUserByEmail(loggedInUserEmail);
        userService.deleteById(loggedInUser.getId());
        return ResponseEntity.ok("Account deleted.");
    }
}
