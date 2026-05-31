package com.sn.socialnetworkapp.exceptions;

import org.springframework.http.HttpStatus;

public class MyBadRequestException extends MyException {
    public MyBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
