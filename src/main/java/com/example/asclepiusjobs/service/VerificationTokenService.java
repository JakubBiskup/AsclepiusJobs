package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.model.VerificationToken;
import com.example.asclepiusjobs.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private EmailService emailService;

    public VerificationToken generateVerificationToken(User user){
        String token= UUID.randomUUID().toString();
        VerificationToken verificationToken= new VerificationToken(token,user);
        emailService.sendEmailConfirmationLink(user,verificationToken.getToken());
        return verificationTokenRepository.save(verificationToken);
    }

    public VerificationToken findAndValidateVerificationToken(String token) throws Exception {
        Optional<VerificationToken> verificationTokenOptional=verificationTokenRepository.findByToken(token);
        if(verificationTokenOptional.isPresent()){
            VerificationToken verificationToken=verificationTokenOptional.get();
            if(isTokenExpired(verificationToken)){
                throw new Exception("Verification token expired");
            }
            return verificationToken;
        }else{
            throw new Exception("Bad Verification Token");
        }
    }

    public void deleteById(Long verificationTokenId){
        verificationTokenRepository.deleteById(verificationTokenId);
    }

    public boolean isTokenExpired(VerificationToken verificationToken){
        Calendar calendar= Calendar.getInstance();
        Date expirationDate= verificationToken.getExpirationDate();
        return !calendar.getTime().before(expirationDate);
    }

    public void deleteExpiredTokens(){
        System.out.println("deleting expired verification tokens");
        verificationTokenRepository.deleteByExpirationDateLessThan(new Date(System.currentTimeMillis()));
    }
}
