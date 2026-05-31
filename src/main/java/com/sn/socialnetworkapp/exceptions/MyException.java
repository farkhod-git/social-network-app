package com.sn.socialnetworkapp.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class MyException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    public MyException(String message,  HttpStatus status) {
        super(message);
        this.status = status;
    }

}
