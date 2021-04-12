package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.model.auth.AuthenticationRequest;
import com.example.asclepiusjobs.model.auth.AuthenticationResponse;
import com.example.asclepiusjobs.service.AsclepiusUserDetailsService;
import com.example.asclepiusjobs.service.JwtService;
import com.example.asclepiusjobs.service.UserService;
import com.example.asclepiusjobs.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AsclepiusUserDetailsService userDetailsService;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    UserService userService;

    @PostMapping(value="/authenticate")
    public ResponseEntity createToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        }catch (DisabledException e){
            User notVerifiedUser=userService.getUserByEmail(authenticationRequest.getUsername());
            if(!verificationTokenService.checkIfUserHasVerificationToken(notVerifiedUser)){
                verificationTokenService.generateVerificationToken(notVerifiedUser);
                return ResponseEntity.status(403).body("You have to confirm your email to log in. We sent a verification link to your email address.");
            }else{
                throw e;
            }
        }

        final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt= jwtService.createToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

    //the two get mappings below are for testing purposes

    @GetMapping(value="/authenticated-only")
    public ResponseEntity sayWelcome(){
        return ResponseEntity.ok("Welcome Authenticated user!");
    }

    @GetMapping(value = "/health-pros-only")
    public ResponseEntity hiHealthPros(){
        return ResponseEntity.ok("This is information for health professionals only");
    }
}
