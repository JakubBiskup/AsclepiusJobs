package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.errorhandling.ErrorMessage;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class ControllerAdviceExceptionHandler {

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage usernameNotFound(UsernameNotFoundException exception){
        return new ErrorMessage(404, exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage invalidArgument(MethodArgumentNotValidException exception){
        return new ErrorMessage(400, "One or more of your inputs is not what we expected. This error might be caused by entering an invalid email or violating our password policy");
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage badCredentials(BadCredentialsException exception){
        return new ErrorMessage(403, exception.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage disabledOrNotActivated(DisabledException exception){
        return new ErrorMessage(403, "Your account is not active. This might be caused by not verifying your email.");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage exception(Exception exception){
        exception.printStackTrace();    //helpful in development, delete this line later
        return new ErrorMessage(500,exception.getMessage());
    }




}
