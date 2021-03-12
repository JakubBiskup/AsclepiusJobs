package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.dto.HealthProfessionalDto;
import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.model.VerificationToken;
import com.example.asclepiusjobs.service.UserService;
import com.example.asclepiusjobs.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @PostMapping(value = "/professional/register")
    public ResponseEntity registerAsHealthProfessional(@RequestBody @Valid HealthProfessionalDto healthProfessionalUser) {
        //currently returns 400 on password without digit, special character, lowercase letter or uppercase letter. Handle this better
        try{
            userService.registerNewHealthProfessional(healthProfessionalUser);
        }catch (Exception e){                             // handle this better
            e.printStackTrace();
            return ResponseEntity.status(500).body(e);
        }
        return ResponseEntity.ok("Registration successful"); //?

    }


    /*
    @PostMapping
    public ResponseEntity registerAsHealthEstablishment[...]
     */

    @PostMapping(value = "/account/confirm")
    public ResponseEntity confirmEmailAndActivateAccount(@RequestBody String token){
        try{
            VerificationToken verificationToken=verificationTokenService.findAndValidateVerificationToken(token);
            User user=verificationToken.getUser();
            user.setActive(true);
            userService.saveOrUpdate(user);
            verificationTokenService.deleteById(verificationToken.getId());
            return ResponseEntity.ok("email verified");
        } catch (Exception e) {                                                             //handle this better
            e.printStackTrace();
            return ResponseEntity.status(500).body(e);
        }
    }

}
