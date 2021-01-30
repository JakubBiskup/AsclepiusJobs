package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.dto.HealthProfessionalDto;
import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class UserService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenService verificationTokenService;

    public User registerNewHealthProfessional(HealthProfessionalDto healthProfessionalDto) throws Exception {
        if(identifyingExists(healthProfessionalDto.getEmail())){
            throw new Exception("There is an account with that email address:"+healthProfessionalDto.getEmail());
        }
        User newHealthProfessional = new User();
        newHealthProfessional.setEmail(healthProfessionalDto.getEmail());
        newHealthProfessional.setIdentifying(healthProfessionalDto.getEmail());
        newHealthProfessional.setProfession(healthProfessionalDto.getProfession());
        newHealthProfessional.setFirstName(healthProfessionalDto.getFirstName());
        newHealthProfessional.setLastName(healthProfessionalDto.getLastName());
        newHealthProfessional.setFemale(healthProfessionalDto.getFemale());
        newHealthProfessional.setPhone(healthProfessionalDto.getPhone());
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        newHealthProfessional.setPassword(encoder.encode(healthProfessionalDto.getPassword()));
        newHealthProfessional.setRole("ROLE_HEALTH_PROFESSIONAL");
        newHealthProfessional.setCreationDate(new Date());
        newHealthProfessional.setConnectionDate(new Date());
        //location
        //cv
        newHealthProfessional.setActive(false);
        User savedUser=userRepository.save(newHealthProfessional);
        verificationTokenService.generateVerificationToken(savedUser);
        //send confirmation mail
        return savedUser;
    }

    public User saveOrUpdate(User user){
        userRepository.save(user);
        return user;
    }

    private boolean identifyingExists(String identifying){
        return userRepository.findByIdentifying(identifying).isPresent();
    }
}
