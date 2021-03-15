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
    public ResponseEntity registerAsHealthProfessional(@RequestBody @Valid HealthProfessionalDto healthProfessionalUser) throws Exception {
        userService.registerNewHealthProfessional(healthProfessionalUser);
        return ResponseEntity.ok("Registration successful");
    }


    /*
    @PostMapping
    public ResponseEntity registerAsHealthEstablishment[...]
     */

    @PostMapping(value = "/account/confirm")
    public ResponseEntity confirmEmailAndActivateAccount(@RequestBody String token) throws Exception{
        VerificationToken verificationToken=verificationTokenService.findAndValidateVerificationToken(token);
        User user=verificationToken.getUser();
        user.setActive(true);
        userService.saveOrUpdate(user);
        verificationTokenService.deleteById(verificationToken.getId());
        return ResponseEntity.ok("email verified");
    }

}
