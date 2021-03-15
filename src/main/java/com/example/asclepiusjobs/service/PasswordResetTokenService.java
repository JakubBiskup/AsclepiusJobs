package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.model.PasswordResetToken;
import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class PasswordResetTokenService {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetToken generatePasswordResetToken(User user){
        deleteByUser(user);
        passwordResetTokenRepository.flush();
        String token= UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken=new PasswordResetToken(token,user);
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    public PasswordResetToken findAndValidatePasswordResetToken(String token) throws Exception {
        Optional<PasswordResetToken> passResetTokenOptional=passwordResetTokenRepository.findByToken(token);
        if(passResetTokenOptional.isPresent()){
            PasswordResetToken passResetToken=passResetTokenOptional.get();
            if(isTokenExpired(passResetToken)){
                throw new Exception("Password Reset Token expired");
            }else {
                return passResetToken;
            }
        }else {
            throw new Exception("Token not found. Your password reset link might have been used already");
        }
    }

    public void deleteById(Long id){
        passwordResetTokenRepository.deleteById(id);
    }
    public boolean isTokenExpired(PasswordResetToken passwordResetToken){
        Calendar calendar= Calendar.getInstance();
        Date expirationDate= passwordResetToken.getExpirationDate();
        return !calendar.getTime().before(expirationDate);
    }

    public void deleteExpiredTokens(){
        System.out.println("deleting expired password reset tokens");
        passwordResetTokenRepository.deleteByExpirationDateLessThan(new Date(System.currentTimeMillis()));
    }

    public void deleteByUser(User user){
        passwordResetTokenRepository.deleteByUser(user);
    }
}
