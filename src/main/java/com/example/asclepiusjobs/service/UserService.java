package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.dto.HealthProfessionalDto;
import com.example.asclepiusjobs.model.Role;
import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private RoleService roleService;

    public User registerNewHealthProfessional(HealthProfessionalDto healthProfessionalDto) throws Exception {
        if(emailExists(healthProfessionalDto.getEmail())){
            throw new Exception("There is an account with that email address:"+healthProfessionalDto.getEmail());
        }
        User newHealthProfessional = new User();
        newHealthProfessional.setEmail(healthProfessionalDto.getEmail());

        newHealthProfessional.setProfession(healthProfessionalDto.getProfession());
        newHealthProfessional.setFirstName(healthProfessionalDto.getFirstName());
        newHealthProfessional.setLastName(healthProfessionalDto.getLastName());
        newHealthProfessional.setSalutation(healthProfessionalDto.getSalutation());
        newHealthProfessional.setPhone(healthProfessionalDto.getPhone());
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        newHealthProfessional.setPassword(encoder.encode(healthProfessionalDto.getPassword()));

        Set<Role> roleSet=new HashSet<>();
        roleSet.add(roleService.getRoleByName("HEALTH_PROFESSIONAL"));
        newHealthProfessional.setRoles(roleSet);

        newHealthProfessional.setLastActivityTime(new Date());
        //location
        //cv
        newHealthProfessional.setActive(false);
        User savedUser=userRepository.save(newHealthProfessional);
        verificationTokenService.generateVerificationToken(savedUser);
        return savedUser;
    }

    public User saveOrUpdate(User user){
        userRepository.save(user);
        return user;
    }

    private boolean emailExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public User getUserByEmail(String email){
        if(emailExists(email)){
            return userRepository.findByEmail(email).get();
        }else{
            throw new UsernameNotFoundException("no user found with email: "+email);
        }
    }
}
