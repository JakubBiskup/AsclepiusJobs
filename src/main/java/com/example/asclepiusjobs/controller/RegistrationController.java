package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.dto.HealthProfessionalDto;
import com.example.asclepiusjobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/professional/register")
    public ResponseEntity registerAsHealthProfessional(@RequestBody HealthProfessionalDto healthProfessionalUser) {
        //createToken ?
        try{
            HealthProfessionalDto healthProDto=healthProfessionalUser;
            userService.registerNewHealthProfessional(healthProfessionalUser);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(e);             // handle this better
        }
        return ResponseEntity.ok("Registration successful"); //?

    }


    /*
    @PostMapping
    public ResponseEntity registerAsHealthEstablishment[...]
     */

}
