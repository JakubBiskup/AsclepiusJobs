package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    private static final String ASCLEPIUS_JOBS_ADDRESS="asclepiusjobs@gmail.com";

    @Autowired
    private JavaMailSender emailSender;

    public void  sendEmailConfirmationLink(User user, String token){         // change the link later
        String text="Hello, "+ user.getFirstName() +"! You have registered to Asclepius Jobs,you just need to click the link to confirm your e-mail address"
                +" https://link-here?token=" + token + " If you haven't created any account on Asclepius Jobs, please ignore this message.";
        sendSimpleEmail(user.getEmail(), "Confirm your account on Asclepius Jobs",text);
    }

    public void sendPasswordResetLink(User user, String token){             // change the link later
        String text="Hello, "+user.getFirstName()+" "+user.getLastName()+", here is a link to reset your password on Asclepius Jobs: https://link-here?token="+token
                + "If you have not asked for a password reset, please ignore this message.";
        sendSimpleEmail(user.getEmail(), "Asclepius Jobs password reset",text);
    }

    public void sendSimpleEmail(String to, String subject, String text){
        SimpleMailMessage simpleEmail=new SimpleMailMessage();
        simpleEmail.setFrom(ASCLEPIUS_JOBS_ADDRESS);
        simpleEmail.setTo(to);
        simpleEmail.setSubject(subject);
        simpleEmail.setText(text);
        emailSender.send(simpleEmail);
    }

}
