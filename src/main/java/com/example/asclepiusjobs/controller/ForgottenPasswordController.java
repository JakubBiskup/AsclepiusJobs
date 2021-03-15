package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.model.PasswordResetToken;
import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.service.EmailService;
import com.example.asclepiusjobs.service.PasswordResetTokenService;
import com.example.asclepiusjobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
public class ForgottenPasswordController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @PostMapping(value = "/forgotten-password")
    public ResponseEntity sendPasswordResetMail(@RequestBody String email) throws Exception {
        try {
            User user = userService.getUserByEmail(email);
            emailService.sendPasswordResetLink(user, passwordResetTokenService.generatePasswordResetToken(user).getToken());
            return ResponseEntity.ok("Password reset link sent (if there is an account with that email). Check your mailbox.");
        }catch (UsernameNotFoundException exception){
            try{
                TimeUnit.SECONDS.sleep(3); // sleep for 3 seconds to simulate work that would have to be done if the email was found
            }catch (InterruptedException ie){
                System.out.println("Sleep interrupted... "+ ie.getMessage());
            }
            return  ResponseEntity.ok("Password reset link sent (if there is an account with that email). Check your mailbox.");
        }
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity resetPassword(@RequestParam(value = "token") String token, @RequestBody Map<String , String> passwordsMap) throws Exception {
        if(!passwordsMap.get("password").equals(passwordsMap.get("confirmedPassword"))){
            return ResponseEntity.badRequest().body("Passwords are not identical");
        }else {
           PasswordResetToken passwordResetToken = passwordResetTokenService.findAndValidatePasswordResetToken(token);
           User user = passwordResetToken.getUser();
           PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
           String hashedPassword=passwordEncoder.encode(passwordsMap.get("password"));
           user.setPassword(hashedPassword);
           userService.saveOrUpdate(user);
           passwordResetTokenService.deleteById(passwordResetToken.getId());
           return ResponseEntity.ok("Password changed. Log in with your new password.");
        }
    }
}
