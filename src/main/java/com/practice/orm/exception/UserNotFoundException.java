package com.practice.orm.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String error) {
        super("There is no such " + error);
    }
}
