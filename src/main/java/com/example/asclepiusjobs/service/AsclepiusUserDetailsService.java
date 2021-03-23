package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.model.AsclepiusUserDetails;
import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AsclepiusUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> optionalUser=userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            return new AsclepiusUserDetails(optionalUser.get());
        }else {
            throw new UsernameNotFoundException("email was not found");
        }
    }
}
