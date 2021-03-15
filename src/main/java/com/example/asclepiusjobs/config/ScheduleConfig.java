package com.example.asclepiusjobs.config;

import com.example.asclepiusjobs.service.PasswordResetTokenService;
import com.example.asclepiusjobs.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @Scheduled(fixedRate = 1000*60*60)  //once per hour
    public void deleteExpiredTokens(){
        try {
            System.out.println("deleting expired tokens...");
            verificationTokenService.deleteExpiredTokens();
            passwordResetTokenService.deleteExpiredTokens();
            System.out.println("expired tokens deleted");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("failed to delete expired tokens");
        }

    }
}
