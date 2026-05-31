package com.sn.socialnetworkapp.exceptions;

import org.springframework.http.HttpStatus;

public class MyNotFoundException extends MyException {
    public MyNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
