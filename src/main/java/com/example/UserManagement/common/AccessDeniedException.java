package com.example.UserManagement.common;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(String message){
        super(message);
    }
}
