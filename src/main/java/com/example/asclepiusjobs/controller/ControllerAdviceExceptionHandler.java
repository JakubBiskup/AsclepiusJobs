package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.errorhandling.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        System.out.println("Printing stack trace to sout: ");                      //delete this line later
        exception.printStackTrace();                                            //delete this line or move to logs later
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage dataIntegrityViolation(DataIntegrityViolationException exception){
        exception.printStackTrace(); //
        return new ErrorMessage(500, "Something went wrong."); //should I provide a useful message?
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage messageNotReadable(HttpMessageNotReadableException exception){
        exception.printStackTrace(); ///
        return new ErrorMessage(400, "One or more of your inputs is not what we expected.This might be caused by entering date in wrong format.");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage exception(Exception exception){
        exception.printStackTrace();    //helpful in development, delete this line later
        return new ErrorMessage(500,exception.getMessage());
    }




}
